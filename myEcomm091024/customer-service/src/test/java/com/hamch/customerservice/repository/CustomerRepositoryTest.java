package com.hamch.customerservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

//import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;

import com.hamch.customerservice.CustomerServiceApplication;
import com.hamch.customerservice.entities.Customer;
import com.hamch.customerservice.repository.CustomerRepository;


@DataJpaTest
//@ContextConfiguration(classes = {CustomerServiceApplication.class})
@RunWith(SpringRunner.class)
//@TestPropertySource("classpath:application-test.properties")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {
	
	@Autowired
	private CustomerRepository customerRepository;
	

	@BeforeAll
	static void setUp(@Autowired CustomerRepository customerRepository2) throws Exception {
		
	//	System.out.println("-----------------kkkkk------------------------------");
        customerRepository2.save(Customer.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build());
        customerRepository2.save(Customer.builder().firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build());
        customerRepository2.save(Customer.builder().firstName("zzzzz").lastName("zzzzz").adress("addddrrrssszzzz").email("zzzz@gmail.com").mobile("33333333").userName("ZZZ").build());
     //   System.out.println("-----------------llllll------------------------------");
	}
	
	@Test
    void shouldFindCustomersByEmail(){
        String givenEmail="xxxx@gmail.com";
        Customer expected=Customer.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build();
        Customer result = customerRepository.findByEmail(givenEmail);
      //  System.out.println("MMMMMMMMMMMMM"+ expected);

     //   System.out.println("MMMMMMMMMMMMM"+ result);
        assertThat(result).isNotNull();;
        assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result);
    }
	
	@Test
    void shouldFindCustomersByUsername(){
        String givenUsername="ZZZ";
        Customer expected=Customer.builder().firstName("zzzzz").lastName("zzzzz").adress("addddrrrssszzzz").email("zzzz@gmail.com").mobile("33333333").userName("ZZZ").build();
        Customer result = customerRepository.findByUserName(givenUsername);
      //  System.out.println("ZZZZZZZZZZZZZZZZZ"+ result);
        assertThat(result).isNotNull();
        assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result);
    }
	
	@Test
    void shoudFindByUsernameOrEmailOrMobile(){
        String givenEmail="xxxx@gmail.com";
        String givenUsername="YYY";
        String givenMobile="2222222";
        List<Customer> expected=List.of(
        		Customer.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build(),
        		Customer.builder().firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build()
        );
        List<Customer> result = customerRepository.findByUserNameOrEmailOrMobile(givenUsername, givenEmail, givenMobile);
      //  System.out.println("LLLLLLLLLLLLLLL"+ result);
        assertThat(result).isNotNull();
        assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result);
    }

	@AfterEach
	void tearDown() throws Exception {
	}

	/*
	 * @Test void test() { //fail("Not yet implemented"); }
	 */

}
