package com.haud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCustomerRequestDto {
    private Long id;
    private String name;
    @JsonProperty(required = false, value = "sims")
    private List<SimDto> simDtoList;
}
