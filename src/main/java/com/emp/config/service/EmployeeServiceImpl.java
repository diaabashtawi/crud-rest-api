package com.emp.config.service;

import com.emp.config.dao.EmployeeDAO;
import com.emp.config.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeDAO employeeDAO;

    @Override
    @Transactional
    public List<Employee> getEmployees() {
        return employeeDAO.getEmployees();
    }

    @Override
    @Transactional
    public void saveEmployee(Employee employee) {
        employeeDAO.saveEmployee(employee);
    }

    @Override
    @Transactional
    public Employee getEmployee(int Id) {
        return employeeDAO.getEmployee(Id);
    }

    @Override
    @Transactional
    public void deleteEmployee(int Id) {
        employeeDAO.deleteEmployee(Id);
    }
}
