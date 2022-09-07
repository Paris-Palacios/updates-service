package com.commerce.updatesservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class EmployeeDto {
    @JsonProperty("emp_id")
    public int emp_id;
    @JsonProperty("emp_name")
    public String emp_name;
    @JsonProperty("emp_city")
    public String emp_city;
    @JsonProperty("emp_phone")
    public String emp_phone;
    @JsonProperty("java_exp")
    public Double java_exp;
    @JsonProperty("spring_exp")
    public Double spring_exp;
}
