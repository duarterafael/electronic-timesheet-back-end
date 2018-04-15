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
	  * 
	  * @return
	  */
	 
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> listAllEmployees() {
    	logger.info("Retrieve all  employee");
    	List<Employee> employees = employeeService.findAll();
        if (employees.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }
    /**
     * 
     * @param pis
     * @return
     */
    
    @RequestMapping(value = "/employee/{pis}", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployee(@PathVariable("pis") String pis) {
        logger.info("Fetching employee with pis {}", pis);
        Employee employee = employeeService.findByPis(pis);
        if (employee == null) {
            logger.error("employee with pis {} not found.", pis);
            return new ResponseEntity(new CustomErrorType("Employee with pis " + pis 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }
    
    /**
     * 
     * @param employee
     * @param ucBuilder
     * @return
     */
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee, UriComponentsBuilder ucBuilder) {
        logger.info("Creating employee : {}", employee);
 
//        if (!employeeService.isEmployeeExist(employee)) {
//        	String errorMsg = String.format("Unable to create. A employee with pis %s already exist.", employee.getPis());
//            logger.error(errorMsg);
//            return new ResponseEntity(new CustomErrorType(errorMsg),HttpStatus.CONFLICT);
//        }
        employeeService.save(employee);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/employee/{id}").buildAndExpand(employee.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
//    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
//    public ResponseEntity<?> updateEmployee(@PathVariable("id") int id, @RequestBody Employee employee) {
//        logger.info("Updating employee with id {}", id);
// 
//        Employee currentEmployee = employeeService.findById(id);
// 
//        if (currentUser == null) {
//            logger.error("Unable to update. User with id {} not found.", id);
//            return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
//                    HttpStatus.NOT_FOUND);
//        }
// 
//        currentUser.setName(user.getName());
//        currentUser.setAge(user.getAge());
//        currentUser.setSalary(user.getSalary());
// 
//        userService.updateUser(currentUser);
//        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
//    }
    
}