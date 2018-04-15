package br.com.electronictimesheet.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.electronictimesheet.dao.EmployeeRepository;
import br.com.electronictimesheet.model.Employee;
@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> findAll() {
		
		return employeeRepository.findAll();
	}

	public Employee findByPis(String pis) {
		Employee employee = employeeRepository.findByPis(pis).get();
		return employee;
	}

	public boolean isEmployeeExist(Employee employee) {
		return findByPis(employee.getPis()) != null;
	}

	public void save(Employee employee) {
		employeeRepository.save(employee);
	}

	public Employee findById(int id) {
		Employee employee = new Employee("123", "123", "Rafael Duarte");
		return employee;

	}

}
