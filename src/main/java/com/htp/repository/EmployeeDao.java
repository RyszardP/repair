package com.htp.repository;

import com.htp.domain.Employee;

import java.util.List;

public interface EmployeeDao extends GenericDao<Employee, Long> {
    List<Integer> batchUpdate(List<Employee> employees);

    List<Employee> search(String query);
}
