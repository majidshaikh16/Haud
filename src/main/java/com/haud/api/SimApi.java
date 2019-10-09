package com.haud.api;

import com.haud.api.impl.SimService;
import com.haud.dto.CreateSimRequestDto;
import com.haud.dto.SimDto;
import com.haud.entity.Sim;
import com.haud.mapper.DtoMapper;
import com.haud.utils.Request;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author webwerks
 */

@RestController
@RequestMapping(path = "/sim")
@Api(tags = "Sim api")
public class SimApi {

    private SimService simService;

    public SimApi(@Autowired SimService simService) {
        Request.simService = simService;
        this.simService = simService;
    }

    /**
     * Creates a new sim entity
     *
     * @param simDto
     * @return
     */
    @PostMapping(path = "/create")
    @ApiOperation(value = "Create a sim entity", response = CreateSimRequestDto.class)
    public ResponseEntity<SimDto> createCustomer(@RequestBody SimDto simDto) {
        Request.verifySimPostRequest(simDto);
        Sim sim = DtoMapper.toSim(simDto);
        return new ResponseEntity<>(DtoMapper.toSimDto(simService.createSim(sim)), HttpStatus.OK);
    }
}