package com.hamch.orderserviceb.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamch.orderserviceb.entities.Order;
import com.hamch.orderserviceb.entities.OrderItem;
import com.hamch.orderserviceb.model.Customer;
import com.hamch.orderserviceb.model.Product;
import com.hamch.orderserviceb.repository.OrderItemRepository;
import com.hamch.orderserviceb.repository.OrderRepository;
import com.hamch.orderserviceb.security.JwtContextHolder;
import com.hamch.orderserviceb.services.CustomerRestClientService;
import com.hamch.orderserviceb.services.ProductRestClientService;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@RestController
@RequestMapping("/api/order")
// @RequiredArgsConstructor
@Slf4j
// @CrossOrigin("http://localhost:4200")
public class OrderController {

    public OrderController(OrderRepository orderRepository,
            com.hamch.orderserviceb.services.ProductRestClientService productRestClient,
            com.hamch.orderserviceb.services.CustomerRestClientService customerRestClient,
            org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory r4jcircuitBreakerFactory,
            com.hamch.orderserviceb.repository.OrderItemRepository orderItemRepository, StreamBridge streamBridge) {
        this.objectMapper = new ObjectMapper();
        this.orderRepository = orderRepository;
        this.productRestClient = productRestClient;
        this.customerRestClient = customerRestClient;
        this.r4jcircuitBreakerFactory = r4jcircuitBreakerFactory;
        this.streamBridge = streamBridge;
        this.orderItemRepository = orderItemRepository;
    }

    private final ObjectMapper objectMapper;

    private final OrderRepository orderRepository;
    private final ProductRestClientService productRestClient;
    private final CustomerRestClientService customerRestClient;
    private final Resilience4JCircuitBreakerFactory r4jcircuitBreakerFactory;
    @Autowired
    private final StreamBridge streamBridge;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate; // Utilisez UNIQUEMENT ceci pour l'envoi

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Order> getById(@PathVariable("id") Long id) {
        System.out.println("IIIIIDDDD" + id);
        return orderRepository.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> findAll() {
        System.out.println("XXXXXXXXXXXXXXXXXXXX");
        return orderRepository.findAll();
    }

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<Map<String, String>> placeOrder(@RequestBody OrderForm orderForm) throws JsonProcessingException {
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXaaaXXXXXXXXXXXXXXXXXXXXXX" + orderForm.getCustomer());

        Customer customer = customerRestClient.findByUsernameOrEmailOrMobile(orderForm.getCustomer().getUsername(),
                orderForm.getCustomer().getEmail(), orderForm.getCustomer().getMobile());
        // Customer customer=
        // customerRestClient.findByUsernameOrEmailOrMobile("user2","abdellah_h2001@yahoo.fr","0666666666");

        if (customer == null) {
            System.out.println("CCCCCBBBBxxxxxxxxxxxxxxxxxxxx" + customer);
            customer = orderForm.getCustomer();

            // iciiii
            customer = customerRestClient.save(customer);
            System.out.println((customer) + "RRR" + customer.getMobile() + "rrr" + orderForm.getProducts());

            Long IdCustomer = this.customerRestClient.customerByUsername(customer.getUsername());
            System.out.println("YYY" + IdCustomer);
            customer.setCustomer_id(IdCustomer);
            System.out.println("YYY" + customer);

            org.springframework.cloud.client.circuitbreaker.CircuitBreaker circuitBreaker = r4jcircuitBreakerFactory
                    .create("product");

            String currentToken = null;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth instanceof org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken jwtAuth) {
                currentToken = jwtAuth.getToken().getTokenValue();
            }

            // 2. Rendre le token accessible au Supplier qui s'exécutera dans l'autre thread
            final String tokenToPass = currentToken;

            java.util.function.Supplier<Boolean> booleanSupplier = () -> {

                try {
                    JwtContextHolder.setToken(tokenToPass);
                    return orderForm.getProducts().parallelStream().allMatch(lineItem -> {
                        log.info("Making Call to Product-serviceb for Verification {}", lineItem.getProduct().getId());
                        System.out.println("AAAAAAAAAAA");
                        Product product = productRestClient.isInStock(lineItem.getProduct().getId(),
                                lineItem.getProduct().getQuantity());
                        return (product.getStock() - lineItem.getProduct().getQuantity()) > 0;

                    });
                } finally {
                    JwtContextHolder.clear(); // Nettoyage du thread du pool
                }
            };

            Boolean productsInStock = circuitBreaker.run(booleanSupplier, throwable -> handleErrorCase(throwable));
            System.out.println("PPPPPPinnnnS" + productsInStock);

            if (productsInStock) {
                //var order = new Order();
                Order order = new Order();
                order.setDate(new Date());
                order.setCustomerId(IdCustomer);
               // order = orderRepository.save(order);
                final Order savedOrder = orderRepository.save(order);

                double total = 0;
                
                List<OrderItem> savedItems = new ArrayList<>();

                for (OrderProduct p : orderForm.getProducts()) {
                    OrderItem orderItem = new OrderItem();
                    //orderItem.setOrder(order);
                    orderItem.setOrder(savedOrder);
                    // Product product=productRestClient.productById(p.getProduct().getId()).get();
                    //// Product product = productRestClient.isInStock(p.getProduct().getId());
                    // Product product=productRestClient.isInStock(p.getId());
                    System.out.println(p.getProduct().getStock() + "SSSSTTTTTKKKK" + p);
                    orderItem.setProductId(p.getProduct().getId());
                    orderItem.setPrice(p.getProduct().getPrice());
                    orderItem.setQuantity(p.getQuantity());
                    System.out.println("HHHHH" + p.getProduct().getId());

                    this.orderItemRepository.save(orderItem);
                    total += p.getQuantity() * p.getProduct().getPrice();
                    savedItems.add(orderItem);
                }
                order.setTotalAmount(total);
                order.setOrderItems(savedItems);
               // orderRepository.save(order);
                
                //log.info("Sending Order Details with Order Id {} to Notification Service", order);
                //String orderJson = objectMapper.writeValueAsString(order);
                //streamBridge.send("orderNotification-out-0", MessageBuilder.withPayload(orderJson).build());

                try {
                        log.info("Sending Order Details to Notification Service for Order ID: {}", savedOrder.getId());
                        
                        Map<String, Object> orderPayload = new HashMap<>();
                        orderPayload.put("orderId", savedOrder.getId());
                        orderPayload.put("customerId", savedOrder.getCustomerId());
                        orderPayload.put("totalAmount", savedOrder.getTotalAmount());
                        orderPayload.put("date", savedOrder.getDate());

                        String orderJson = objectMapper.writeValueAsString(orderPayload);
                        
                        MessageProperties messageProperties = new MessageProperties();
                        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON); // Définit application/json
                            
                        Message message = new Message(orderJson.getBytes(), messageProperties);
                            
                        // Envoi du message typé
                        rabbitTemplate.send("order-notification-exchange", "my.routing.key", message);            
                        log.info("Message déposé avec succès sur RabbitMQ.");
                    } catch (Exception e) {
                        log.error("Erreur lors de l'envoi de la notification", e);
                    }

                Map<String, String> response = new HashMap<>();
                response.put("message", "Order Placed Successfully");
                response.put("status", "SUCCESS");

                return ResponseEntity.ok(response); 
            } else {
                throw new RuntimeException("Order Failed - One of the Product in your Order is out of stock");
            }
        } else {

            System.out.println((customer) + "RRR222" + customer.getMobile() + "rrr" + orderForm.getProducts());

            Long IdCustomer = this.customerRestClient.customerByUsername(customer.getUsername());
            System.out.println("YYY222" + IdCustomer);
            customer.setCustomer_id(IdCustomer);
            System.out.println("YYY222" + customer);

            Order order = new Order();
            order.setDate(new Date());
            order.setCustomerId(IdCustomer);
            order = orderRepository.save(order);

            double total = 0;
            for (OrderProduct p : orderForm.getProducts()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                System.out.println(p.getQuantity() + "PPPPPP2222" + p.getId());

                // Product product=productRestClient.productById(p.getProduct().getId()).get();
                Product product = productRestClient.productById(p.getProduct().getId());

                System.out.println(product.getStock() + "SSSSTTTTTKKKK" + product.getId());

                System.out.println("HHHHH" + product.getId());
                orderItem.setProductId(product.getId());
                orderItem.setPrice(product.getPrice());
                orderItem.setQuantity(p.getQuantity());

                // Product productN=productRestClient.productUpStock(product.getId(),
                // p.getQuantity());

                this.orderItemRepository.save(orderItem);
                total += p.getQuantity() * product.getPrice();
            }
            order.setTotalAmount(total);
            System.out.println("OOOO" + order);

            orderRepository.save(order);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Order Placed Successfully");
            response.put("status", "SUCCESS");

            return ResponseEntity.ok(response); 

        }

    }

    private boolean handleErrorCase(Throwable throwable) {
        // 1. Logger l'erreur pour la maintenance (ex: service indisponible, timeout)
        log.error("Échec de la vérification des stocks via le Circuit Breaker. Cause : {}", throwable.getMessage());

        // 2. Optionnel : lever une exception personnalisée si vous voulez bloquer la commande avec un message HTTP 503
        // throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Le service produit est indisponible.");

        // 3. Retourner false par sécurité (on refuse la commande si on ne peut pas vérifier le stock)
        return false;
    }
    /* @PostMapping("/add")
    public Order saveOrder(@RequestBody OrderForm orderForm) {
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXaaaXXXXXXXXXXXXXXXXXXXXXX" + orderForm.getCustomer());
    
        Customer customer = customerRestClient.findByUsernameOrEmailOrMobile(orderForm.getCustomer().getUsername(),
                orderForm.getCustomer().getEmail(), orderForm.getCustomer().getMobile());
        // Customer customer=
        // customerRestClient.findByUsernameOrEmailOrMobile("user2","abdellah_h2001@yahoo.fr","0666666666");
    
        if (customer == null) {
            System.out.println("CCCCCBBBBxxxxxxxxxxxxxxxxxxxx" + customer);
            customer = orderForm.getCustomer();
    
            // iciiii
            customer = customerRestClient.save(customer);
            System.out.println((customer) + "RRR" + customer.getMobile() + "rrr" + orderForm.getProducts());
    
            Long IdCustomer = this.customerRestClient.customerByUsername(customer.getUsername());
            System.out.println("YYY" + IdCustomer);
            customer.setCustomer_id(IdCustomer);
            System.out.println("YYY" + customer);
    
            Order order = new Order();
            order.setDate(new Date());
            order.setCustomerId(IdCustomer);
            order = orderRepository.save(order);
            double total = 0;
            for (OrderProduct p : orderForm.getProducts()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                System.out.println("PPPPPP" + p.getId());
    
                // Product product=productRestClient.productById(p.getProduct().getId()).get();
                Product product = productRestClient.isInStock(p.getProduct().getId());
                // Product product=productRestClient.isInStock(p.getId());
                System.out.println(product.getStock() + "SSSSTTTTTKKKK" + product.getId());
                orderItem.setProductId(product.getId());
                orderItem.setPrice(product.getPrice());
                orderItem.setQuantity(p.getQuantity());
                System.out.println("HHHHH" + product.getId());
    
                this.orderItemRepository.save(orderItem);
                total += p.getQuantity() * product.getPrice();
            }
            order.setTotalAmount(total);
            return orderRepository.save(order);
        } else {
    
            System.out.println((customer) + "RRR222" + customer.getMobile() + "rrr" + orderForm.getProducts());
    
            Long IdCustomer = this.customerRestClient.customerByUsername(customer.getUsername());
            System.out.println("YYY222" + IdCustomer);
            customer.setCustomer_id(IdCustomer);
            System.out.println("YYY222" + customer);
    
            Order order = new Order();
            order.setDate(new Date());
            order.setCustomerId(IdCustomer);
            order = orderRepository.save(order);
    
            double total = 0;
            for (OrderProduct p : orderForm.getProducts()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                System.out.println(p.getQuantity() + "PPPPPP2222" + p.getId());
    
                // Product product=productRestClient.productById(p.getProduct().getId()).get();
                Product product = productRestClient.productById(p.getProduct().getId());
    
                System.out.println(product.getStock() + "SSSSTTTTTKKKK" + product.getId());
    
                System.out.println("HHHHH" + product.getId());
                orderItem.setProductId(product.getId());
                orderItem.setPrice(product.getPrice());
                orderItem.setQuantity(p.getQuantity());
    
                // Product productN=productRestClient.productUpStock(product.getId(),
                // p.getQuantity());
    
                this.orderItemRepository.save(orderItem);
                total += p.getQuantity() * product.getPrice();
            }
            order.setTotalAmount(total);
            System.out.println("OOOO" + order);
    
            return orderRepository.save(order);
    
        }
    
    } */

    /*    @PostMapping("/addddd")
    public String placeOrder(@RequestBody OrderDto orderDto) {
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    
        r4jcircuitBreakerFactory.configureExecutorService(traceableExecutorService);
        Resilience4JCircuitBreaker circuitBreaker = r4jcircuitBreakerFactory.create("inventory");
        java.util.function.Supplier<Boolean> booleanSupplier = () -> orderDto.getOrderLineItemsList().stream()
                .allMatch(lineItem -> {
                    log.info("Making Call to Inventory Service for SkuCode {}", lineItem.getSkuCode());
                    log.info("Making Call to Inventory Service for SkuCode {}", lineItem.getId());
    
                    return inventoryClient.checkStock(lineItem.getSkuCode());
                });
        boolean productsInStock = circuitBreaker.run(booleanSupplier, throwable -> handleErrorCase());
    
        if (productsInStock) {
            Order order = new Order();
            order.setOrderItems(orderDto.getOrderLineItemsList());
            order.setId(Long.valueOf(UUID.randomUUID().toString()));
    
            orderRepository.save(order);
            log.info("Sending Order Details with Order Id {} to Notification Service", order.getId());
            streamBridge.send("notificationEventSupplier-out-0", MessageBuilder.withPayload(order.getId()).build());
            return "Order Place Successfully";
        } else {
            return "Order Failed - One of the Product in your Order is out of stock";
        }
    } */

}
