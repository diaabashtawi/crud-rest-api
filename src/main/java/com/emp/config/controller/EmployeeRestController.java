package com.emp.config.controller;


import com.emp.config.entity.Employee;
import com.emp.config.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    private List<Employee> getEmployees(){
        return employeeService.getEmployees();
    }

    @GetMapping("/employee/{empId}")
    private Employee getEmployee(@PathVariable int empId){
        Employee employee = employeeService.getEmployee(empId);

        if (employee == null){
            throw new EmployeeNotFoundException("Employee ID NOT FOUND - " + empId);
        }
        return employee;
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee){

        // also just in case the pass an id in JSON ... set id to 0
        // this is force a save of new item ... instead of update
        employee.setId(0);

        employeeService.saveEmployee(employee);

        return employee;
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee){
        employeeService.saveEmployee(employee);

        return employee;
    }

    @DeleteMapping("/employee/{empId}")
    public String deleteEmployee(@PathVariable int empId){

        Employee employee = employeeService.getEmployee(empId);

        if (employee == null){
            throw new EmployeeNotFoundException("Employee ID NOT FOUND - " + empId);
        }

        employeeService.deleteEmployee(empId);

        return "Employee with ID : " + empId + " Deleted Successfully";

    }
}
