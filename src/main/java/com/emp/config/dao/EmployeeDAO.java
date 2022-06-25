package com.emp.config.dao;


import com.emp.config.entity.Employee;

import java.util.List;

public interface EmployeeDAO {

    public List<Employee> getEmployees();

    public void saveEmployee(Employee employee);

    public Employee getEmployee(int Id);

    public void deleteEmployee(int Id);
}
