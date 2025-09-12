package makara.co.min_pos.service.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import makara.co.min_pos.exception.ResourceNotFoundException;
import makara.co.min_pos.mapper.CustomerMapper;
import makara.co.min_pos.models.entities.Customer;
import makara.co.min_pos.models.response.CustomerResponse;
import makara.co.min_pos.repository.CustomerRepository;
import makara.co.min_pos.service.CustomerService;
import makara.co.min_pos.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper itemCustomerMapper;

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.findByIdAndIsDeletedFalse(id).orElseThrow(()->
                new ResourceNotFoundException("Customer",id));
    }

    @Override
    public Customer updates(Long customerId, Customer newCustomer) {
        Customer customer = getById(customerId);
        //don't use like this not good when you are working with real projects in above...
        customer.setCustomerLocalName(newCustomer.getCustomerLocalName());
        customer.setCustomerEngName(newCustomer.getCustomerEngName());
        customer.setCustomerEmail(newCustomer.getCustomerEmail());
        customer.setCustomerPhone(newCustomer.getCustomerPhone());
        customer.setCustomerAddress(newCustomer.getCustomerAddress());
        return customerRepository.save(customer);
    }

    @Override
    public Customer delete(Long id) {
        Customer getId = getById(id);
        getId.setIsDeleted(true);
        return customerRepository.save(getId);
    }

    @Override
    public List<CustomerResponse> listAllCustomer() {
        return customerRepository.findByIsDeletedIsFalseOrderByIdDesc()
                .stream()
                .map(itemCustomerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CustomerResponse> getPagination(Map<String, String> params) {

        int pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
        if(params.containsKey(PageUtil.PAGE_LIMIT)){
            pageLimit = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }
        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if(params.containsKey(PageUtil.PAGE_NUMBER)){
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }

        Pageable pageable =  PageUtil.getPageable(pageNumber,pageLimit);
        Page<CustomerResponse> page = customerRepository
                .findByIsDeletedIsFalseOrderByIdDesc(pageable)
                .map(itemCustomerMapper::toDTO);

        return page;
    }
}
