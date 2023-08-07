package com.example.testingproject.IntegrationTestCases;

import com.example.testingproject.entity.Employee;
import com.example.testingproject.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port + "/api/employees";
    }

    @Test
    public void testCreateEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("mayank");
        employee.setLastName("tomar");

        ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootUrl(), employee, Employee.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());

        Employee savedEmployee = postResponse.getBody();
        assertNotNull(savedEmployee.getId());
        assertEquals("mayank", savedEmployee.getFirstName());
    }

    @Test
    public void testGetEmployeeById() {
        Employee employee = employeeRepository.save(new Employee("mayank", "tomar"));

        Employee fetchedEmployee = restTemplate.getForObject(getRootUrl() + "/" + employee.getId(), Employee.class);
        assertNotNull(fetchedEmployee);
        assertEquals("mayank", fetchedEmployee.getFirstName());
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = employeeRepository.save(new Employee("mayank", "tomar"));

        employee.setFirstName("ajay");

        restTemplate.put(getRootUrl() + "/" + employee.getId(), employee);

        Employee updatedEmployee = employeeRepository.findById(employee.getId()).orElse(null);
        assertNotNull(updatedEmployee);
        assertEquals("ajay", updatedEmployee.getFirstName());
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = employeeRepository.save(new Employee("mayank", "tomar"));

        restTemplate.delete(getRootUrl() + "/" + employee.getId());

        Employee deletedEmployee = employeeRepository.findById(employee.getId()).orElse(null);
        assertNull(deletedEmployee);
    }
}
