package com.backendspring.lib;


import com.backendspring.entity.Manager;
import com.backendspring.service.ManagerService;
import com.backendspring.service.impl.ManagerServiceImpl;
import com.backendspring.service.impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoggingDecorator implements ManagerService {
    private final ManagerServiceImpl managerServiceImpl;
    @Override
    public void createManager(Manager manager) {
        LoggingServiceUtil.logStart(
                "ManagerServiceImpl", "createManager");

        managerServiceImpl.createManager(manager);

        LoggingServiceUtil.logEnd(
                "ManagerServiceImpl", "createManager");
    }
}