package com.haud.api;

import com.haud.api.impl.CustomerService;
import com.haud.dto.CreateCustomerRequestDto;
import com.haud.dto.CustomerDto;
import com.haud.dto.GetCustomerRequestDto;
import com.haud.entity.Customer;
import com.haud.mapper.DtoMapper;
import com.haud.utils.Request;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author webwerks
 */

@RestController
@RequestMapping(path = "/customer")
@Api(tags = "Customer api")
public class CustomerApi {

    private CustomerService customerService;
    public CustomerApi(@Autowired CustomerService customerService) {
        Request.customerService = customerService;
        this.customerService=customerService;
    }

    /**
     * Creates a new customer entity
     *
     * @param customerDto
     * @return
     */
    @PostMapping(path = "/create")
    @ApiOperation(value = "Create a customer entity", response = CreateCustomerRequestDto.class)
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        Request.verifyCustomerPostRequest(customerDto);
        Customer customer = DtoMapper.toCustomer(customerDto);
        return new ResponseEntity<>(DtoMapper.toCustomerDto(customerService.createCustomer(customer)), HttpStatus.OK);
    }

    /**
     * Links and existing customer with a existing sim id
     *
     * @param simId
     * @param custId
     * @return
     */
    @PostMapping(path = "/link/sim/{simId}/{custId}")
    @ApiOperation(value = "Link a sim to an existing customer", response = GetCustomerRequestDto.class)
    public ResponseEntity<CustomerDto> linkSimToCustomer(@PathVariable long simId, @PathVariable long custId) {
        Request.verifyCustomeSimLinkrPostRequest(CustomerDto.builder().id(custId).simId(simId).build());
        return new ResponseEntity<>(DtoMapper.toCustomerDto(customerService.linkSim(custId, simId)), HttpStatus.OK);
    }

    /**
     * Fetch an existing customer by customer id
     *
     * @param custId
     * @return
     */
    @GetMapping(path = "/{custId}")
    @ApiOperation(value = "Fetches an existing customer with it sim details", response = GetCustomerRequestDto.class)
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable long custId) {
        Request.verifyCustomerExists(custId);
        return new ResponseEntity<>(DtoMapper.toCustomerDto(customerService.getCustomer(custId)), HttpStatus.OK);
    }

}
