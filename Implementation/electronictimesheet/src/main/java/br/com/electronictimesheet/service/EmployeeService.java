package br.com.electronictimesheet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.electronictimesheet.dao.EmployeeRepository;
import br.com.electronictimesheet.model.Employee;
import br.com.electronictimesheet.util.CustomErrorType;
import br.com.electronictimesheet.util.Utils;
@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public List<Employee> findAll() {
		
		return employeeRepository.findAll();
	}
	
	public CustomErrorType validateFields(Employee employee)
	{
		if(employee.getFullNane() == null || employee.getFullNane().isEmpty())
		{
			return new CustomErrorType("The field 'fullNane' is mandatory.");
		}else if(employee.getPis() == null || employee.getPis().isEmpty())
		{
			return new CustomErrorType("The field 'pis' is mandatory.");
		}else if(employee.getPassword() == null || employee.getPassword().isEmpty())
		{
			return new CustomErrorType("The field 'password' is mandatory.");
		}
		return null;
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
