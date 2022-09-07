package com.commerce.updatesservice;

import com.commerce.updatesservice.dto.EmployeeDto;
import com.commerce.updatesservice.service.EmployeeValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;

@SpringBootApplication
@Slf4j
public class UpdatesServiceApplication implements CommandLineRunner {

    @Autowired
    private ReactiveKafkaConsumerTemplate<Integer, EmployeeDto> reactiveKafkaConsumerTemplate;
    @Autowired
    private ReactiveKafkaProducerTemplate<Integer,EmployeeDto> reactiveKafkaProducerTemplate;
    @Autowired
    private EmployeeValidationService employeeValidationService;

    public static void main(String[] args) {

        SpringApplication.run(UpdatesServiceApplication.class, args);

    }

    @Override
    public void run(String... args) {
       var a = reactiveKafkaConsumerTemplate.receiveAutoAck()
                .doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset()))
                .flatMap(consumerRecord -> {
                    EmployeeDto employeeDto = consumerRecord.value();
                   return employeeValidationService.checkEmployeeDtoIfIsNull(employeeDto)
                            .flatMap(result -> {
                                if(result) {
                                  return reactiveKafkaProducerTemplate.send("employee_DLQ",employeeDto);
                                }
                                else {
                                    return reactiveKafkaProducerTemplate.send("employee_updates",employeeDto);
                                }
                            });
                })
                .subscribe();

    }
}
