package makara.co.min_pos.service;

import makara.co.min_pos.models.entities.Customer;
import makara.co.min_pos.models.response.CustomerResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    Customer create(Customer customer);
    Customer getById(Long id);
    Customer updates(Long customerId, Customer newCustomer);
    Customer delete(Long id);
    List<CustomerResponse> listAllCustomer();

    Page<CustomerResponse> getPagination(Map<String, String> params);
}
