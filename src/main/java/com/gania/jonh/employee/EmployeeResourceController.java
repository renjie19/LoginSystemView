package com.gania.jonh.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gania.jonh.employee.model.Employee;
import com.gania.jonh.license.model.License;
import com.gania.jonh.util.JsonMapper;
import com.gania.jonh.util.ResourceUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeResourceController {

    private static final String API_EMPLOYEE_DELETE_EMPLOYEE = "/api/employee/deleteEmployee";
    private static final String API_EMPLOYEE_UPDATE = "/api/employee/update";
    private static final String API_EMPLOYEE_GET_ALL = "/api/employee/getAll";
    private static final String API_EMPLOYEE_SAVE = "/api/employee/save";



    public void deleteEmployee(Employee employee) {
        try{
            Map<String,String> map = new HashMap<>();
            map.put("id",String.valueOf(employee.getEmployeeId()));
            ResourceUtil.getInstance().delete(API_EMPLOYEE_DELETE_EMPLOYEE,map);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(Employee employee) {
        try {
            String content = JsonMapper.getInstance().writeValueAsString(employee);
            ResourceUtil.getInstance().post(API_EMPLOYEE_UPDATE, content);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> getAll() {
        try {
            String result = ResourceUtil.getInstance().getAllRequest(API_EMPLOYEE_GET_ALL);
            return Arrays.asList(JsonMapper.getInstance().readValue(result, Employee[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Employee createEmployee(Employee employee) {
        try{
            String content = JsonMapper.getInstance().writeValueAsString(employee);
            String employeeResult = ResourceUtil.getInstance().post(API_EMPLOYEE_SAVE,content);
            return JsonMapper.getInstance().readValue(employeeResult,Employee.class);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
