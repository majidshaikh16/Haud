package com.haud.utils;

import com.haud.api.impl.CustomerService;
import com.haud.api.impl.SimService;
import com.haud.dto.CustomerDto;
import com.haud.dto.SimDto;
import com.haud.exception.ClientException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import static com.haud.utils.Errors.Error;

/**
 * Validating utilities for incoming requests
 *
 * @author webwerks
 */
public class Request {

    public static CustomerService customerService;
    public static SimService simService;

    public static void verifyCustomerPostRequest(CustomerDto customerDto) {
        Errors errors = new Errors();
        if (customerDto.getId() != null) {
            errors.addError(Error.builder().title("Invalid Post Request").detail("Customer creation cannot accept id.").build());
        }
        if (StringUtils.isEmpty(customerDto.getName())) {
            errors.addError(Error.builder().title("Invalid Post Request").detail("Customer name required!").build());
        }
        if (errors.getErrors().size() > 0) {
            throw new ClientException(HttpStatus.BAD_REQUEST, errors);
        }

    }

    public static void verifyCustomeSimLinkrPostRequest(CustomerDto customerDto) {
        Errors errors = new Errors();
        if (customerDto.getSimId() == null) {
            errors.addError(Error.builder().title("Invalid Post Request").detail("Customer sim linking cannot be accepted without sim id.").build());
        }
        if (customerDto.getId() == null) {
            errors.addError(Error.builder().title("Invalid Post Request").detail("Customer sim linking cannot be accepted without customer id.").build());
        }

        verifyCustomerId(errors, customerDto.getId());
        verifySimId(errors, customerDto.getSimId());

        if (errors.getErrors().size() > 0) {
            throw new ClientException(HttpStatus.BAD_REQUEST, errors);
        }

    }

    public static void verifySimPostRequest(SimDto simDto) {
        Errors errors = new Errors();
        if (simDto.getId() != null) {
            errors.addError(Error.builder().title("Invalid Post Request").detail("Sim creation cannot accept id.").build());
        }
        if (StringUtils.isEmpty(simDto.getName())) {
            errors.addError(Error.builder().title("Invalid Post Request").detail("Sim name required!").build());
        }
        if (errors.getErrors().size() > 0) {
            throw new ClientException(HttpStatus.BAD_REQUEST, errors);
        }

    }

    public static void verifyCustomerExists(long custId) {
        Errors errors = new Errors();
        verifyCustomerId(errors, custId);
        if (errors.getErrors().size() > 0) {
            throw new ClientException(HttpStatus.BAD_REQUEST, errors);
        }
    }

    private static void verifyCustomerId(Errors errors, long custId) {
        if (!customerService.isCustomerExists(custId))
            errors.addError(Error.builder().title("Invalid Request").detail("Customer does not exist with the given id.").build());
    }

    private static void verifySimId(Errors errors, long simId) {
        if (!simService.isSimExists(simId))
            errors.addError(Error.builder().title("Invalid Request").detail("Sim does not exist with the given id.").build());
    }
}
