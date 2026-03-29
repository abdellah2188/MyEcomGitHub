package com.hamch.customerservice.repository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hamch.customerservice.entities.Customer;


//DataJpaTest
//@ContextConfiguration(classes = {CustomerServiceApplication.class})
//@RunWith(SpringRunner.class)
//@TestPropertySource("classpath:application-test.properties")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {
	
	@Autowired
	private CustomerRepository customerRepository;
	

	
	@Test
        @SuppressWarnings("unused")
    void shouldFindCustomersByEmail(){
        String givenEmail="xxxx@gmail.com";
        Customer expected=Customer.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").username("XXX").build();
        Customer result = customerRepository.findByEmail(givenEmail);
      //  System.out.println("MMMMMMMMMMMMM"+ expected);

     //   System.out.println("MMMMMMMMMMMMM"+ result);
        assertThat(result).isNotNull();
        assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result);
    }
	
	@Test
        @SuppressWarnings("unused")
    void shouldFindCustomersByUsername(){
        String givenUsername="ZZZ";
        Customer expected=Customer.builder().firstName("zzzzz").lastName("zzzzz").adress("addddrrrssszzzz").email("zzzz@gmail.com").mobile("33333333").username("ZZZ").build();
        Customer result = customerRepository.findByUsername(givenUsername);
      //  System.out.println("ZZZZZZZZZZZZZZZZZ"+ result);
        assertThat(result).isNotNull();
        assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result);
    }
	
	@Test
        @SuppressWarnings("unused")
    void shoudFindByUsernameOrEmailOrMobile(){
        String givenEmail="xxxx@gmail.com";
        String givenUsername="YYY";
        String givenMobile="2222222";
        List<Customer> expected=List.of(
        		Customer.builder().firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").username("XXX").build(),
        		Customer.builder().firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").username("YYY").build()
        );
        Customer result = customerRepository.findByUsernameOrEmailOrMobile(givenUsername, givenEmail, givenMobile);
      //  System.out.println("LLLLLLLLLLLLLLL"+ result);
        assertThat(result).isNotNull();
        assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result);
    }
    /*
     * @Test void test() { //fail("Not yet implemented"); }
     */

}
