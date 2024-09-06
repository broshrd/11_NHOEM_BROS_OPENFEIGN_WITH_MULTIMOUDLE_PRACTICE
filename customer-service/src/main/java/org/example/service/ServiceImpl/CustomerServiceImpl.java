package org.example.service.ServiceImpl;

import org.example.model.Customer;
import org.example.model.dto.request.CustomerRequest;
import org.example.model.dto.response.ApiResponse;
import org.example.model.dto.response.CustomerDto;
import org.example.repository.CustomerRepository;
import org.example.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public ApiResponse<CustomerDto> createCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());
         customerRepository.save(customer);
         CustomerDto customerDto = new CustomerDto(
                  customer.getId(),
                 customer.getName(),
                 customer.getEmail()
         );
        return new ApiResponse<>("Successful saved customer",customerDto, HttpStatus.CREATED,LocalDateTime.now());
    }

    @Override
    public ApiResponse<CustomerDto> getCustomer(Long id) {

      Optional<Customer> customer =  customerRepository.findById(id);
        if (customer.isPresent()){
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customer.get().getId());
            customerDto.setName(customer.get().getName());
            customerDto.setEmail(customer.get().getEmail());
            return new ApiResponse<>(" get customer Successful",customerDto, HttpStatus.CREATED,LocalDateTime.now());
        }
        return new ApiResponse<>("customer not found",null,HttpStatus.NOT_FOUND,LocalDateTime.now());
    }

    @Override
    public ApiResponse<CustomerDto> updateCustomer(Long id, CustomerRequest customerRequest) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()){
            customer.get().setName(customerRequest.getName());
            customer.get().setEmail(customerRequest.getEmail());
            customerRepository.save(customer.get());
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customer.get().getId());
            customerDto.setName(customer.get().getName());
            customerDto.setEmail(customer.get().getEmail());
            return new ApiResponse<>(" update customer Successful",customerDto, HttpStatus.OK,LocalDateTime.now());
        }
        return new ApiResponse<>("customer "+ id +"not found",null, HttpStatus.CREATED,LocalDateTime.now());
    }

    @Override
    public ApiResponse<CustomerDto> deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()){
            customerRepository.delete(customer.get());
            return new ApiResponse<>("delete customer Successful",null, HttpStatus.OK,LocalDateTime.now());
        }
        return new ApiResponse<>("delete customer "+id+ "Successful",null, HttpStatus.NOT_FOUND,LocalDateTime.now());

    }
    @Override
    public ApiResponse<List<CustomerDto>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customerDtos = customers.stream().map(customer -> new CustomerDto(customer.getId(),customer.getName(),customer.getEmail())).toList();
        return new ApiResponse<>("get all customers Successful",customerDtos, HttpStatus.OK,LocalDateTime.now());
    }
}
