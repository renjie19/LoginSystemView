package com.gania.jonh.report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gania.jonh.employee.EmployeeResourceController;
import com.gania.jonh.employee.model.Employee;
import com.gania.jonh.report.model.Report;
import com.gania.jonh.util.JsonMapper;
import com.gania.jonh.util.ResourceUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReportResourceController {

    private static final String API_REPORT_VIEW_BY_EMPLOYEE_ID = "/api/report/viewByEmployeeId";
    private static final String API_EMPLOYEE_GET_ALL = "/api/employee/getAll";
    private EmployeeResourceController employeeResourceController;

    private EmployeeResourceController getEmployeeResourceController() {
        if(employeeResourceController==null){
            employeeResourceController = new EmployeeResourceController();
        }
        return employeeResourceController;
    }

    public List<Report> getReports(int id, String startDate, String endDate) {
        try{
            Map<String,String> map = new HashMap<>();
            map.put("id",String.valueOf(id));
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            String content = ResourceUtil.getInstance().get(API_REPORT_VIEW_BY_EMPLOYEE_ID,map);
            List<Report> reports = Arrays.asList(JsonMapper.getInstance().readValue(content, Report[].class));
            for(Report report : reports) {
                report.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(report.getTimeInLog().getTime())));
            }
            return reports;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        return getEmployeeResourceController().getAll();
    }
}
