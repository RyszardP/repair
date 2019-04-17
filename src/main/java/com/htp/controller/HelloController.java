package com.htp.controller;

import com.htp.domain.Employee;
import com.htp.repository.EmployeeDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@Controller

public class HelloController {
    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String printHello(ModelMap model) {
        model.addAttribute("message", "Hello Spring MVC Framework!");
        return "hello";
    }

    @RequestMapping(value = "/user/search", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String printHello(@RequestParam("query") String query, ModelMap model) {
        List<Employee> search = employeeDao.search(query);
        model.addAttribute("userName",
                StringUtils.join(search.stream().map(Employee::getName).collect(Collectors.toList()), ","));
        return "hello";
    }
}