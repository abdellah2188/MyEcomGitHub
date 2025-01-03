package com.hamch.productserviceb;

import com.hamch.productserviceb.entities.Category;
import com.hamch.productserviceb.entities.Product;
import com.hamch.productserviceb.repository.CategoryRepository;
import com.hamch.productserviceb.repository.ProductRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
/*
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
*/
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Type;

@SpringBootApplication
@EnableDiscoveryClient
@Configurable
@EnableJpaRepositories
public class ProductServicebApplication implements CommandLineRunner {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	
	//private RepositoryRestConfiguration repositoryRestConfiguration;

	public static void main(String[] args) {

		SpringApplication.run(ProductServicebApplication.class, args);

	}
	
	@Bean
    public RepositoryRestConfigurer repositoryRestConfigurer(EntityManager entityManager) {
        return RepositoryRestConfigurer.withConfig(config -> {
            config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream().map(entityType -> entityType.getJavaType()).toArray(Class[]::new));
        });

    }

	/*
	 * @Bean @Profile("test") CommandLineRunner commandLineRunner(ProductRepository
	 * productRepository){ //
	 * log.info("================= Initialization ================"); return args ->
	 * {
	 * 
	 * List<Product> products = List.of(
	 * 
	 * Product.builder().name("xxxxe").description("xxxxxx").price(111).promotion(
	 * false).selected(false).available(false).photoName(null).stock(68).build(),
	 * Product.builder().name("yyyyye").description("yyyyyy").price(111).promotion(
	 * false).selected(false).available(false).photoName(null).stock(68).build(),
	 * 
	 * Product.builder().name("zzzzzc").description("zzzzzz").price(111).promotion(
	 * false).selected(false).available(false).photoName(null).stock(68).build() );
	 * productRepository.saveAll(products);
	 * 
	 * }; }
	 */

	@Override
	@Profile("test")
	public void run(String... args) throws Exception {

//	repositoryRestConfiguration.exposeIdsFor(Product.class, Category.class);

	/*
	 * categoryRepository.save(new Category(null, "Bidons", null)); //
	 * categoryRepository.save(new Category(null,"Printers", null)); //
	 * categoryRepository.save(new Category(null,"Smart phones", null)); Random rnd
	 * = new Random(); categoryRepository.findAll().forEach(c -> { for (int i = 0; i
	 * < 0; i++) { Product p = new Product(); p.setName(RandomString.make(18));
	 * p.setPrice(100 + rnd.nextInt(10000)); p.setAvailable(rnd.nextBoolean());
	 * p.setPromotion(rnd.nextBoolean()); p.setSelected(rnd.nextBoolean());
	 * p.setCategory(c); p.setPhotoName("unknown.png"); p.setStock(5);
	 * productRepository.save(p); } });
	 */

	}

}
