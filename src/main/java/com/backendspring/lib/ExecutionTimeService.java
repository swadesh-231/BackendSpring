package com.backendspring.lib;


import com.backendspring.entity.Manager;
import com.backendspring.entity.Student;
import com.backendspring.service.ManagerService;
import com.backendspring.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class ExecutionTimeService implements ManagerService {

    private final LoggingDecorator loggingDecorator;


    @Override
    public void createManager(Manager manager) {
        long start = System.currentTimeMillis();

        loggingDecorator.createManager(manager);

        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }
}