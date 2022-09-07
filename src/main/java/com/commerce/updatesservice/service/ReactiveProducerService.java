package com.commerce.updatesservice.service;

import com.commerce.updatesservice.dto.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReactiveProducerService {

    @Autowired
    private ReactiveKafkaProducerTemplate<Integer, EmployeeDto> reactiveKafkaTemplate;

    public void send(String topic,EmployeeDto employeeDto) {
        reactiveKafkaTemplate.send(topic,employeeDto.getEmp_id() ,employeeDto).subscribe();
    }
}
