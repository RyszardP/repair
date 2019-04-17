package com.htp.repository.impl;

import com.htp.domain.Employee;
import com.htp.repository.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@Transactional
public class EmployeeDaoImpl implements EmployeeDao {

    public static final String EMPLOYEE_ID = "employee_id";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String EMAIL = "email";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String SPECIALITY = "specialty";
    public static final String ROLE = "role";
    public static final String EMPLOYEESECTOR_ID = "employeeSector_id";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Employee getEmployeeRowMapper(ResultSet resultSet, int i) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployee_id(resultSet.getInt(EMPLOYEE_ID));
        employee.setName(resultSet.getString(NAME));
        employee.setSurname(resultSet.getString(SURNAME));
        employee.setEmail(resultSet.getString(EMAIL));
        employee.setLogin(resultSet.getString(LOGIN));
        employee.setPassword(resultSet.getString(PASSWORD));
        employee.setSpecialty(resultSet.getString(SPECIALITY));
        employee.setRole(resultSet.getString(ROLE));
        employee.setEmployeeSector_id(resultSet.getString(EMPLOYEESECTOR_ID));

        return employee;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Integer> batchUpdate(List<Employee> employees) {
        final String createQuery = "UPDATE employee set name = :name, surname = :surname";

        List<SqlParameterSource> batch = new ArrayList<>();
        for (Employee employee : employees){
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("name", employee.getName());
            params.addValue("surname", employee.getSurname());
           batch.add(params);
        }

        namedParameterJdbcTemplate.batchUpdate(createQuery, batch.toArray(new SqlParameterSource[batch.size()]));
        return employees.stream().map(Employee::getEmployee_id).collect(Collectors.toList());
    }

    @Override
    public List<Employee> search(String query) {
        final String searchQuery = "select * from user where lower(name) LIKE lower(:query) or" +
                "lower(surname) LIKE lower(:query)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("query", "%" + query + "%");

        return namedParameterJdbcTemplate.query(searchQuery, params, this::getEmployeeRowMapper);
    }

    @Override
    public List<Employee> findAll() {
        final String findAllQuery = "select * from employee";
        return namedParameterJdbcTemplate.query(findAllQuery, this::getEmployeeRowMapper);

    }

    @Override
    public Employee findById(int id) {
        final String findById = "select from employee where employee_id =:employee_id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("employee_id", id);
        return namedParameterJdbcTemplate.queryForObject(findById,params,this::getEmployeeRowMapper);
    }

    @Override
    public void delete(Long id) {
        final String delete = "delete from employee where employee_id = :employee_id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("employee_id", id);

        namedParameterJdbcTemplate.update(delete, params);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public Employee save(Employee entity) {
        final String createQuery = "INSERT INTO employee(name, surname, employee_sector) VALUES (:name, :surname, :employeeSector_id);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name",entity.getName());
        params.addValue("surname",entity.getSurname());
        params.addValue("employeeSector_id",entity.getEmployeeSector_id());

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder);

        long createdEmployeeID = Objects.requireNonNull(keyHolder.getKey().longValue());
        return findById((int) createdEmployeeID);
    }

    @Override
    public Employee update(Employee entity) {
        final String createQuery = "UPDATE employee set name = :name, surname = :surname, " +
                "email =:email, specialty =:speciality,   role =:role where employee_id =:employee_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name",entity.getName());
        params.addValue("surname",entity.getSurname());
        params.addValue("email",entity.getEmail());
        params.addValue("specialty",entity.getSpecialty());
        params.addValue("role",entity.getRole());

        namedParameterJdbcTemplate.update(createQuery, params);
        return findById(entity.getEmployee_id());
    }

    @Override
    public List<Employee> search(Employee entity) {
        return null;
    }
}
