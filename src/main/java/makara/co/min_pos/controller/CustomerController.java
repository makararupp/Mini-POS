package makara.co.min_pos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import makara.co.min_pos.base.BaseApi;
import makara.co.min_pos.mapper.CustomerMapper;
import makara.co.min_pos.models.DTO.PageDTO;
import makara.co.min_pos.models.entities.Customer;
import makara.co.min_pos.models.request.CustomerRequest;
import makara.co.min_pos.models.response.CustomerResponse;
import makara.co.min_pos.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper itemCustomerMapper;

    @PostMapping
    public BaseApi<?> create(@Valid @RequestBody CustomerRequest request){
        Customer customer = itemCustomerMapper.toEntity(request);
        Customer loadData = customerService.create(customer);
        CustomerResponse response = itemCustomerMapper.toDTO(loadData);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("customer have been saved")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }
    @GetMapping("{id}")
    public BaseApi<?> findById(@Valid @PathVariable("id")Long customerId){
        Customer byId = customerService.getById(customerId);
        CustomerResponse response = itemCustomerMapper.toDTO(byId);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("customer have been found!")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }
    @PutMapping("{id}")
    public BaseApi<?> updateCustomer(@Valid @PathVariable("id") Long customerId,
                                     @RequestBody CustomerRequest  request ){
        Customer customer = itemCustomerMapper.toEntity(request);
        Customer newUpdated = customerService.updates(customerId, customer);
        CustomerResponse responseData = itemCustomerMapper.toDTO(newUpdated);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("customer has been updated!")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }
    @DeleteMapping("{id}")
    public BaseApi<?> deleteCustomer(@Valid @PathVariable("id") Long cusId){
        Customer customer = customerService.delete(cusId);
        CustomerResponse responseData = itemCustomerMapper.toDTO(customer);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("customer has been deleted!")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }
    @GetMapping("list-all")
    public BaseApi<?> getAllCustomer(){
        List<CustomerResponse> responses = customerService.listAllCustomer();

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("customer have been found")
                .timeStamp(LocalDateTime.now())
                .data(responses)
                .build();
    }
    @GetMapping("pagination")
    public BaseApi<?> getCustomerPagination(@Valid @RequestParam Map<String, String> params){
        Page<CustomerResponse> responses = customerService.getPagination(params);
        PageDTO saveData = new PageDTO(responses);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Specification and Pagination successfully")
                .timeStamp(LocalDateTime.now())
                .data(saveData)
                .build();
    }
}
