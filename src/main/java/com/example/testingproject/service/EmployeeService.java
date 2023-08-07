package com.example.testingproject.service;

import com.example.testingproject.entity.Employee;
import org.springframework.stereotype.Service;


public interface EmployeeService {

    Employee createEmployee(Employee employee);
    Employee getEmployeeById(Long id);
    Employee updateEmployee(Long id, Employee employee);
    boolean deleteEmployee(Long id);
}
