package com.emp.config.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emp.config.entity.Employee;

import java.util.List;


@Repository
public class EmployeeDaoImpl implements EmployeeDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Employee> getEmployees() {

        // get the current hibernate session
        Session session = sessionFactory.getCurrentSession();

        // create a query  ... sort by last name
        Query<Employee> query =
                session.createQuery("from Employee order by lastName",
                        Employee.class);
        List<Employee> employee = query.getResultList();

        return employee;
    }

    @Override
    public void saveEmployee(Employee employee) {

        // get current hibernate session
        Session session = sessionFactory.getCurrentSession();

        // save/update the customer ... finally LOL
        session.saveOrUpdate(employee);

    }

    @Override
    public Employee getEmployee(int empId) {

        Session session = sessionFactory.getCurrentSession();

        Employee employee = session.get(Employee.class, empId);

        return employee;
    }

    @Override
    public void deleteEmployee(int empId) {

        Session session = sessionFactory.getCurrentSession();

        Query query =
                session.createQuery("delete from Employee where id=:empId");
        query.setParameter("empId", empId);

        query.executeUpdate();

    }
}
