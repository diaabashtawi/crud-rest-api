package com.emp.config.service;

import com.emp.config.entity.Employee;

import java.util.List;

public interface EmployeeService {

    public List<Employee> getEmployees();

    public void saveEmployee(Employee employee);

    public Employee getEmployee(int Id);

    public void deleteEmployee(int Id);

}
