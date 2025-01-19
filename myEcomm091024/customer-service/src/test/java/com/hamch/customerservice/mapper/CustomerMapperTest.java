package com.hamch.customerservice.mapper;

import com.hamch.customerservice.dto.CustomerDTO;
import com.hamch.customerservice.entities.Customer;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerMapperTest {
    private CustomerMapper customerMapper = new CustomerMapper();

    @Test
    void shouldMapCustomerToCustomerDTO() {
        Customer givenCustomer = Customer.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build();
        CustomerDTO expected = CustomerDTO.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build();

        CustomerDTO result = customerMapper.fromCustomer(givenCustomer);
        assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    void shouldMapCustomerDTOtoCustomer() {
        CustomerDTO givenCustomerDTO = CustomerDTO.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build();
        Customer    expected         = Customer.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build();
        Customer result = customerMapper.fromCustomerDTO(givenCustomerDTO);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    void shouldMapListOfCustomersToListCustomerDTOs() {
        List<Customer> givenCustomers=List.of(
               Customer.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build() ,
               Customer.builder().id(2L).firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build()
        );
        List<CustomerDTO> expected=List.of(
               CustomerDTO.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build() ,
               CustomerDTO.builder().id(2L).firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build()
        );
     //   System.out.println("EEEEE"+ expected);
        
        List<CustomerDTO> result = customerMapper.fromListCustomers(givenCustomers);
      //  System.out.println("RRRRR"+ result);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }
    @Test
    void shouldNotMapNullCustomerToCustomerDTO() {
        AssertionsForClassTypes.assertThatThrownBy( ()->customerMapper.fromCustomer(null)).isInstanceOf(IllegalArgumentException.class);
    }
}