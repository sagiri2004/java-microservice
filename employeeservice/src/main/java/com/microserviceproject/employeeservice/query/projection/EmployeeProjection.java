package com.microserviceproject.employeeservice.query.projection;

import com.microserviceproject.commonservice.model.EmployeeResponseCommonModel;
import com.microserviceproject.employeeservice.command.data.Employee;
import com.microserviceproject.employeeservice.command.data.EmployeeRepository;
import com.microserviceproject.employeeservice.query.model.EmployeeResponseModel;
import com.microserviceproject.employeeservice.query.queries.GetAllEmployeeQuery;
import com.microserviceproject.commonservice.queries.GetDetailEmployeeQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeProjection {
	@Autowired
	private EmployeeRepository employeeRepository;

	@QueryHandler
	public List<EmployeeResponseModel> handle(GetAllEmployeeQuery query) {
		List<Employee> listEmployee = employeeRepository.findAllByIsDisciplined(query.getIsDisciplined());
		return listEmployee.stream().map(employee -> {
			EmployeeResponseModel model = new EmployeeResponseModel();
			BeanUtils.copyProperties(employee, model);
			return model;
		}).toList();
	}

	@QueryHandler
	public EmployeeResponseCommonModel handle(GetDetailEmployeeQuery query) throws Exception {
		Employee employee = employeeRepository.findById(query.getId()).orElseThrow(() -> new Exception("Employee not found"));
		EmployeeResponseCommonModel model = new EmployeeResponseCommonModel();
		BeanUtils.copyProperties(employee, model);
		return model;
	}
}
