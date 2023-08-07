package com.example.testingproject.service;

import com.example.testingproject.entity.Employee;
import com.example.testingproject.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
        if (existingEmployee != null) {
            // Update the fields you want to allow updating
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            // Update other fields as needed
            return employeeRepository.save(existingEmployee);
        } else {
            return null;
        }
    }

    public boolean deleteEmployee(Long id) {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
        if (existingEmployee != null) {
            employeeRepository.delete(existingEmployee);
            return true;
        } else {
            return false;
        }
    }
}
