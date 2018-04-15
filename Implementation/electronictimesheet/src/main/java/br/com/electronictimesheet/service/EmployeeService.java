package br.com.electronictimesheet.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.electronictimesheet.dao.EmployeeRepository;
import br.com.electronictimesheet.model.Employee;
import br.com.electronictimesheet.util.Utils;
@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> findAll() {
		
		return employeeRepository.findAll();
	}

	public Employee findByPis(String pis) {
		Optional<Employee> optionalEmployee = employeeRepository.findByPis(pis);
		return (Employee) Utils.returnValueOrDefault(optionalEmployee);
	}

	public boolean isEmployeeExist(Employee employee) {
		return findByPis(employee.getPis()) != null;
	}

	public void save(Employee employee) {
		employeeRepository.save(employee);
	}

	public Employee findById(Long id) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		return (Employee) Utils.returnValueOrDefault(optionalEmployee);

	}
	

}
