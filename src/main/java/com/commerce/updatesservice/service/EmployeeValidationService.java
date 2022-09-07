package com.commerce.updatesservice.service;

import com.commerce.updatesservice.dto.EmployeeDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;

@Service
public class EmployeeValidationService {

    public Mono<Boolean> checkEmployeeDtoIfIsNull(EmployeeDto employeeDto) {
        if(checkNull(employeeDto)){
            return Mono.just(true);
        }
        return Mono.just(false);
    }

    public boolean checkNull(EmployeeDto employeeDto)  {
        for (Field f : employeeDto.getClass().getDeclaredFields()) {
            try {
                if (f.get(employeeDto) == null) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
