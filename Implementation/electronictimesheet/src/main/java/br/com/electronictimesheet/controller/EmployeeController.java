package br.com.electronictimesheet.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.electronictimesheet.model.Employee;
import br.com.electronictimesheet.service.EmployeeService;
import br.com.electronictimesheet.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	public static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	private EmployeeService employeeService;

	/**
	 * Retrieve all employees.
	 * 
	 * @return OK(200): Returns a list of all employees.
	 */
	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> listAllEmployees() {
		logger.info("Retrieve all  employee");
		List<Employee> employees = employeeService.findAll();
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}

	/**
	 * Retrieve a employee by id.
	 * 
	 * @param id:
	 *            Employee identifier (primary key).
	 * @return: NOT_FOUND(404): Employee not found for id informed. Returns a error
	 *          message. OK(200): Employee found successfully. Returns employee
	 *          datas.
	 */
	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id) {
		logger.info("Fetching employee with id {}", id);
		Employee employee = employeeService.findById(id);
		if (null == employee) {
			logger.error("employee with pis {} not found.", id);
			return new ResponseEntity(new CustomErrorType(String.format("Employee with id %s not found", id)),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	/**
	 * Save a new Employee.
	 * 
	 * @param employee:
	 *            New employee to be save.
	 * @param ucBuilder:
	 *            Assing the url to find the new employee in header, If save with
	 *            sucess.
	 * @return: BAD_REQUEST(400):Some mandatory field was not informed. Returns a
	 *          error message. CONFLICT(409): There is a employee with the same as
	 *          'pis' field in database. Returns a error message. CREATED(201): The
	 *          employee was created with success. Returns employee datas.
	 */
	@RequestMapping(value = "/employee", method = RequestMethod.POST)
	public ResponseEntity<?> createEmployee(@RequestBody Employee employee, UriComponentsBuilder ucBuilder) {
		logger.info("Creating employee : {}", employee);

		CustomErrorType validateFiledsError = employeeService.validateFields(employee);
		if (validateFiledsError != null) {
			logger.error(validateFiledsError.getErrorMessage());
			return new ResponseEntity(validateFiledsError, HttpStatus.BAD_REQUEST);
		}

		if (employeeService.isEmployeeExist(employee)) {
			String errorMsg = String.format("Unable to create. A employee with pis %s already exist.",
					employee.getPis());
			logger.error(errorMsg);
			return new ResponseEntity(new CustomErrorType(errorMsg), HttpStatus.CONFLICT);
		}

		employeeService.save(employee);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/employee/{id}").buildAndExpand(employee.getId()).toUri());
		return new ResponseEntity<Employee>(employee, headers, HttpStatus.CREATED);
	}

}