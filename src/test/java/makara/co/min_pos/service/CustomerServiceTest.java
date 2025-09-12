package makara.co.min_pos.service;

import makara.co.min_pos.exception.ApiException;
import makara.co.min_pos.mapper.CustomerMapper;
import makara.co.min_pos.models.entities.Customer;
import makara.co.min_pos.repository.CustomerRepository;
import makara.co.min_pos.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    CustomerMapper customerMapper;
    CustomerService customerService;
    @Test
    public void whenCreateCustomer_thenSaveCustomer(){
        //given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCustomerLocalName("rupp");
        customer.setCustomerEngName("Sam");
        customer.setCustomerEmail("makara@gmail.com");
        customer.setCustomerPhone("0976965304");
        customer.setCustomerAddress("SR");
        //when
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        Customer customerCreated = customerServiceImpl.create(customer);
        //then
        assertNotNull(customerCreated);
        assertEquals("Sam",customerCreated.getCustomerEngName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void whenGetById_thenReturnCustomer(){
        Long customerId = 3L;
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setCustomerEngName("Sam");
        customer.setCustomerLocalName("rupp");
        customer.setCustomerEmail("makara@gmail.com");
        customer.setCustomerPhone("0976965304");
        customer.setCustomerAddress("SR");

        when(customerRepository.findByIdAndIsDeletedFalse(customerId)).thenReturn(Optional.of(customer));
        Customer foundCustomer = customerServiceImpl.getById(customerId);

        assertNotNull(foundCustomer);
        assertEquals("Sam", foundCustomer.getCustomerEngName());
        assertEquals("rupp", foundCustomer.getCustomerLocalName());
        assertEquals("makara@gmail.com", foundCustomer.getCustomerEmail());
        verify(customerRepository, times(1)).findByIdAndIsDeletedFalse(customerId);
    }

    @Test
    public void shouldThrowNotFoundWhenGivenInvalidIdWhileGetCustomerById() {
        Long invalidCustomerId = 2L;

        when(customerRepository.findByIdAndIsDeletedFalse(anyLong())).thenReturn(Optional.empty());
        //when
        //given
        assertThatThrownBy(() -> customerServiceImpl.getById(invalidCustomerId));
        verify(customerRepository, times(1)).findByIdAndIsDeletedFalse(invalidCustomerId);
    }

    @Test
    public void shouldUpdateCustomerWhenCustomerExists() {
        Long customerId = 1L;
        Customer existingCustomer = new Customer();
        existingCustomer.setId(customerId);
        existingCustomer.setCustomerLocalName("Test Updated");
        existingCustomer.setCustomerEngName("Makara Updated");
        existingCustomer.setCustomerEmail("makaraDevKh@gmail.com");
        existingCustomer.setCustomerPhone("01322456");
        existingCustomer.setCustomerAddress("Phnom Penh");

        Customer newCustomer = new Customer();
        newCustomer.setId(customerId);
        newCustomer.setCustomerLocalName("New Updated");
        newCustomer.setCustomerEngName("New Updated");
        newCustomer.setCustomerEmail("makaraDev@gmail.com");
        newCustomer.setCustomerPhone("01322456");
        newCustomer.setCustomerAddress("Siem Reap");
        //when mock
        when(customerRepository.findByIdAndIsDeletedFalse(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(existingCustomer);

        Customer update = customerServiceImpl.updates(customerId, newCustomer);
        //then
        assertNotNull(update);
        assertEquals("New Updated",update.getCustomerLocalName());
        assertEquals("New Updated", update.getCustomerEngName());
        assertEquals("makaraDev@gmail.com",update.getCustomerEmail());
        assertEquals("01322456",update.getCustomerPhone());
        assertEquals("Siem Reap",update.getCustomerAddress());
        //verify reactions
        verify(customerRepository,times(1)).findByIdAndIsDeletedFalse(customerId);
        verify(customerRepository, times(1)).save(existingCustomer);

    }

    @Test
    public void shouldThrowNotFoundWhenGivenInvalidIDWhileUpdateCustomer(){
        Long invalidId = 999L;
        Customer request = new Customer();
        request.setCustomerLocalName("Makara");
        request.setCustomerEngName("Sam");
        request.setCustomerEmail("makara777@gamil.com");
        request.setCustomerPhone("0976965304");
        request.setCustomerAddress("Siem Reap");
        //When
        when(customerRepository.findByIdAndIsDeletedFalse(invalidId))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(
                ApiException.class,
                () -> customerServiceImpl.updates(invalidId, request)
        );
        assertEquals("Customer with id =" + invalidId + " not found", exception.getMessage());
        verify(customerRepository, times(1)).findByIdAndIsDeletedFalse(invalidId);

    }

    @Test
    public void shouldOnlyUpdateCustomerName(){
        // Given: Mocking repository to return the existing customer
        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setCustomerLocalName("Old Name");
        existingCustomer.setCustomerEngName("Old Eng Name");

        when(customerRepository.findByIdAndIsDeletedFalse(1L))
                .thenReturn(Optional.of(existingCustomer));
        String newName = "Updated Name";
        existingCustomer.setCustomerLocalName(newName);
        customerServiceImpl.updates(1L, existingCustomer);

        assertEquals(newName, existingCustomer.getCustomerLocalName());
        assertEquals("Old Eng Name", existingCustomer.getCustomerEngName()); // Remains unchanged

        verify(customerRepository, times(1)).save(existingCustomer);
    }

   @Test
    public void shouldUpdateOnlyCustomerAddress(){
        Customer existingCustomer = new Customer();
        existingCustomer.setId(2L);
        existingCustomer.setCustomerAddress("Siem Reap");

        when(customerRepository.findByIdAndIsDeletedFalse(2L))
                .thenReturn(Optional.of(existingCustomer));

        //update Customer
       Customer update = new Customer();
       update.setId(2L);
       update.setCustomerAddress("Takeo");

       customerServiceImpl.updates(2L,update);
       assertEquals("Takeo",existingCustomer.getCustomerAddress());
       assertEquals(update, existingCustomer.getCustomerAddress());

       verify(customerRepository, times(1)).findByIdAndIsDeletedFalse(2L);
       verify(customerRepository,times(1)).save(existingCustomer);

   }

}

