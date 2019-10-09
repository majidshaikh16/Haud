package com.haud.mapper;

import com.haud.dto.CustomerDto;
import com.haud.dto.SimDto;
import com.haud.entity.Customer;
import com.haud.entity.Sim;

import java.util.stream.Collectors;

public class DtoMapper {

    public static Customer toCustomer(CustomerDto customerDto) {
        return Customer
                .builder()
                .name(customerDto.getName())
                .build();
    }

    public static CustomerDto toCustomerDto(Customer customer) {
        return CustomerDto
                .builder()
                .id(customer.getId())
                .name(customer.getName())
                .simDtoList(customer.getSims() != null && !customer.getSims().isEmpty() ?
                        customer.getSims()
                                .stream()
                                .map(DtoMapper::toSimDto)
                                .collect(Collectors.toList())
                        : null)
                .build();
    }

    public static SimDto toSimDto(Sim sim) {
        return SimDto
                .builder()
                .id(sim.getId())
                .name(sim.getName())
                .build();
    }


    public static Sim toSim(SimDto simDto) {
        return Sim
                .builder()
                .name(simDto.getName())
                .build();
    }
}
