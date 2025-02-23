package com.hamch.orderserviceb.controller;

import com.hamch.orderserviceb.entities.Order;
import com.hamch.orderserviceb.entities.OrderItem;
import com.hamch.orderserviceb.repository.OrderItemRepository;
import com.hamch.orderserviceb.services.CustomerRestClientService;
import com.hamch.orderserviceb.services.ProductRestClientService;
import com.hamch.orderserviceb.model.Customer;
import com.hamch.orderserviceb.model.Product;
import com.hamch.orderserviceb.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
////////import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
///////import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
///////import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("http://localhost:4200")
public class OrderController {

    private final OrderRepository orderRepository;
    private final ProductRestClientService productRestClient;
    private final CustomerRestClientService customerRestClient;
    private final Resilience4JCircuitBreakerFactory r4jcircuitBreakerFactory;
///////    private final StreamBridge streamBridge;
 /////////   private final ExecutorService traceableExecutorService;
    private final OrderItemRepository orderItemRepository;

    @GetMapping(path="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Order> getById(@PathVariable("id") Long id) {
        System.out.println("IIIIIDDDD"+ id);
        return orderRepository.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> findAll() {
        System.out.println("XXXXXXXXXXXXXXXXXXXX");
        return orderRepository.findAll();
    }
/*    @CrossOrigin(origins="*")
    public String home() {

        return "Helloo";
    }*/
    @PostMapping("/add")
    public Order saveOrder(@RequestBody OrderForm orderForm){
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+ orderForm);

        Customer customer= customerRestClient.findByUsernameoOrEmailOrMobile(
                orderForm.getCustomer().getName(),
                orderForm.getCustomer().getEmail(),
                orderForm.getCustomer().getMobile()
        );
//        System.out.println("CCCCCBBBB"+ customer.getCustomer_id());

        if(customer==null) {
            customer=new Customer();
            customer.setName(orderForm.getCustomer().getName());
            customer.setEmail(orderForm.getCustomer().getEmail());
            customer.setAdress(orderForm.getCustomer().getAdress());
            customer.setMobile(orderForm.getCustomer().getMobile());
            customer.setUsername(orderForm.getCustomer().getUsername());
            //iciiii
            customer=customerRestClient.save(customer);
            System.out.println((customer) + "RRR" + customer.getMobile() + "rrr" + orderForm.getProducts());


            Long IdCustomer=this.customerRestClient.customerByUsername(customer.getUsername());
            System.out.println("YYY" + IdCustomer);
            customer.setCustomer_id(IdCustomer);
            System.out.println("YYY" + customer);

            Order order=new Order();
            order.setDate(new Date());
            order.setCustomerId(IdCustomer);
            order=orderRepository.save(order);
            double total=0;
            for (OrderProduct p : orderForm.getProducts()) {
                OrderItem orderItem=new OrderItem();
                orderItem.setOrder(order);
                System.out.println("PPPPPP" + p.getId());

                //Product product=productRestClient.productById(p.getProduct().getId()).get();
                Product product=productRestClient.isInStock(p.getProduct().getId());

                System.out.println(product.getStock() + "SSSSTTTTTKKKK" + product.getId());
                orderItem.setProductId(product.getId());
                orderItem.setPrice(product.getPrice());
                orderItem.setQuantity(p.getQuantity());
                System.out.println("HHHHH" + product.getId());

                this.orderItemRepository.save(orderItem);
                total+=p.getQuantity() * product.getPrice();
            }
            order.setTotalAmount(total);
            return orderRepository.save(order);
        }else{

            System.out.println((customer)+"RRR222"+customer.getMobile()+"rrr"+ orderForm.getProducts());


            Long IdCustomer= this.customerRestClient.customerByUsername( customer.getUsername()) ;
            System.out.println("YYY222"+ IdCustomer);
            customer.setCustomer_id( IdCustomer);
            System.out.println("YYY222"+ customer);

            Order order=new Order();
            order.setDate(new Date());
            order.setCustomerId(IdCustomer);
            order=orderRepository.save(order);

            double total=0;
            for(OrderProduct p:orderForm.getProducts()){
                OrderItem orderItem=new OrderItem();
                orderItem.setOrder(order);
                System.out.println(p.getQuantity() + "PPPPPP2222"+p.getId());

                //Product product=productRestClient.productById(p.getProduct().getId()).get();
                Product product=productRestClient.productById(p.getProduct().getId());

                System.out.println(product.getStock() + "SSSSTTTTTKKKK" + product.getId());

                System.out.println("HHHHH"+ product.getId());
                orderItem.setProductId(product.getId());
                orderItem.setPrice(product.getPrice());
                orderItem.setQuantity(p.getQuantity());

             //   Product productN=productRestClient.productUpStock(product.getId(), p.getQuantity());

                this.orderItemRepository.save(orderItem);
                total+=p.getQuantity()*product.getPrice();
            }
            order.setTotalAmount(total);
            System.out.println("OOOO"+ order);

            return orderRepository.save(order);

        }

    }
    /*@PostMapping("/addddd")
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
    }*/
    private Boolean handleErrorCase() {
        return false;
    }
}
