package com.example.frontendDemo.service;

import com.example.frontendDemo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private RestTemplate restTemplate;

    @Value("${rest.api.url}")
    private String url;



    @Override
    public List<Employee> findAll() {

        // make REST call
        ResponseEntity<List<Employee>> responseEntity = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {});

        // get the list of customers from response
        List<Employee> employees = responseEntity.getBody();

        return employees;
    }

    @Override
    public Employee findById(int id) {

        // make REST call
        Employee employee = restTemplate.getForObject(url + "/" + id, Employee.class);

        return employee;
    }

    @Override
    public void save(Employee employee) {

        int id = employee.getId();

        // make REST call
        if (id == 0) {
            // add employee
            restTemplate.postForEntity(url, employee, String.class);
        } else {
            // update employee
            restTemplate.put(url, employee);
        }
    }

    @Override
    public void deleteById(int id) {

        // make REST call
        restTemplate.delete(url + "/" + id);
    }
}
