package com.hamch.productserviceb.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;

import com.hamch.productserviceb.entities.Product;


@ActiveProfiles("test")
@DataJpaTest
@RunWith(SpringRunner.class)
//@AutoConfigureTestDatabase
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

//@SpringBootTest(properties = { "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE","spring.datasource.driverClassName=org.h2.Driver","spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect" })
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	

	@BeforeEach
	void setUp() throws Exception {
		
		System.out.println("-----------------------------------------------");
       productRepository.save(Product.builder().name("xxxxe").description("xxxxxx").price(111).promotion(false).selected(true).available(true).photoName(null).stock(68).category(null).build());
	          		
       productRepository.save(Product.builder().name("yyyyye").description("yyyyyy").price(111).promotion(false).selected(false).available(true).photoName(null).stock(68).category(null).build());
        
       productRepository.save(Product.builder().name("zzzzzc").description("zzzzzz").price(111).promotion(true).selected(true).available(true).photoName(null).stock(68).category(null).build());
	}
	
	 @Test
	    void shouldFindFindByNameContains(){
			
			  List<Product> expected = List.of(
			       Product.builder().name("xxxxe").description("xxxxxx").price(111).promotion(false).selected(true).available(true).photoName(null).stock(68).build(),
			       Product.builder().name("yyyyye").description("yyyyyy").price(111).promotion(false).selected(false).available(true).photoName(null).stock(68).build()
			  
			  );
			 
		 
		 
	      List<Product> result = productRepository.findByNameContains("e");
	      System.out.println("HHHHHHHHHH"+ result);
	      assertEquals(result.size(),2);
	      assertThat(result).isNotNull();
	      assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result);
	    }
	 
	 @Test
	 void shouldFindBySelectedIsTrue() {
		 
		 List<Product> expected = List.of(
			       Product.builder().name("xxxxe").description("xxxxxx").price(111).promotion(false).selected(true).available(true).photoName(null).stock(68).build(),
			       Product.builder().name("zzzzzc").description("zzzzzz").price(111).promotion(true).selected(true).available(true).photoName(null).stock(68).build()
			  
			  );
		 
		 List<Product> result = productRepository.findBySelectedIsTrue();
	      System.out.println("HHHHHHHHHH"+ result);
	      assertEquals(result.size(),2);
	      assertThat(result).isNotNull();
	      assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result);
	 }
	 
	 @Test
	 void shouldFindByPromotionIsTrue() {
		 
		 List<Product> expected = List.of(
				 Product.builder().name("zzzzzc").description("zzzzzz").price(111).promotion(true).selected(true).available(true).photoName(null).stock(68).build()
			  
			  );
		 
		 List<Product> result = productRepository.findByPromotionIsTrue();
	      System.out.println("HHHHHHHHHH"+ result);
	      assertEquals(result.size(),1);
	      assertThat(result).isNotNull();
	      assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result);
	 }
	 
	 @Test
	 void shouldFindByAvailableIsTrue() {
		 
		 List<Product> expected = List.of(
				 Product.builder().name("xxxxe").description("xxxxxx").price(111).promotion(false).selected(true).available(true).photoName(null).stock(68).build(),
			     Product.builder().name("yyyyye").description("yyyyyy").price(111).promotion(false).selected(false).available(true).photoName(null).stock(68).build(),
				 Product.builder().name("zzzzzc").description("zzzzzz").price(111).promotion(true).selected(true).available(true).photoName(null).stock(68).build()
				 
			  
			  );
		 
		 List<Product> result = productRepository.findByAvailableIsTrue();
	      System.out.println("HHHHHHHHHH"+ result);
	      assertEquals(result.size(),3);
	      assertThat(result).isNotNull();
	      assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result);
	 }

	@AfterEach
	void tearDown() throws Exception {
	}

	

}
