package com.hamch.customerservice.controller;

import com.hamch.customerservice.dto.CustomerDTO;
import com.hamch.customerservice.exceptions.CustomerNotFoundException;
import com.hamch.customerservice.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@ActiveProfiles("test")
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @MockBean
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    List<CustomerDTO> customersDTO;

    @BeforeEach
    void setUp() {
        this.customersDTO = List.of(
                CustomerDTO.builder().id(1L).firstName("xxxxx").lastName("xxxxx").adress("addddrrrsssxxxx").email("xxxx@gmail.com").mobile("1111111").userName("XXX").build() ,
                CustomerDTO.builder().id(2L).firstName("yyyyy").lastName("yyyyy").adress("addddrrrsssyyyy").email("yyyy@gmail.com").mobile("2222222").userName("YYY").build(),
                CustomerDTO.builder().id(3L).firstName("zzzz").lastName("zzzzz").adress("addddrrrssszzzz").email("zzzz@gmail.com").mobile("33333333").userName("ZZZ").build()
        );
    }

    @Test
    void shouldGetAllCustomers() throws Exception {
        Mockito.when(customerService.getAllCustomers()).thenReturn(customersDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customersDTO)));
    }

    @Test
    void shouldGetCustomerById() throws Exception {
        Long id = 1L;
        Mockito.when(customerService.findCustomerById(id)).thenReturn(customersDTO.get(0));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customersDTO.get(0))));
    }

    @Test
    void shouldNotGetCustomerByInvalidId() throws Exception {
        Long id = 9L;
        Mockito.when(customerService.findCustomerById(id)).thenThrow(CustomerNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void searchCustomers() throws Exception {
        String keyword="m";
        Mockito.when(customerService.getAllCustomers()).thenReturn(customersDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers?keyword="+keyword))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customersDTO)));
    }

    @Test
    void shouldSaveCustomer() throws Exception {
        CustomerDTO customerDTO= customersDTO.get(0);
        String expected = """
                {
                  "id":1, "firstName":"xxxxx", "lastName":"xxxxx", "adress":"addddrrrsssxxxx","email":"med@gmail.com", "mobile":"1111111","userName":"XXX"

                }
                """;
        Mockito.when(customerService.saveNewCustomer(Mockito.any())).thenReturn(customersDTO.get(0));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/customer/add")
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .content(objectMapper.writeValueAsString(customerDTO)))
               .andExpect(MockMvcResultMatchers.status().isCreated())
               .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Long customerId=1L;
        CustomerDTO customerDTO= customersDTO.get(0);
        Mockito.when(customerService.updateCustomer(Mockito.eq(customerId),Mockito.any())).thenReturn(customersDTO.get(0));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customerDTO)));
    }
    @Test
    void shouldDeleteCustomer() throws Exception {
        Long customerId=1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/{id}",customerId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}