package br.com.electronictimesheet.controller;

import java.time.LocalDateTime;
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

import br.com.electronictimesheet.model.Clockin;
import br.com.electronictimesheet.model.Employee;
import br.com.electronictimesheet.service.ClockinService;
import br.com.electronictimesheet.service.EmployeeService;
import br.com.electronictimesheet.util.Constant;
import br.com.electronictimesheet.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class ClockinController {
	
	public static final Logger logger = LoggerFactory.getLogger(ClockinController.class);
	@Autowired
	private ClockinService clockinService;
	@Autowired
	private EmployeeService employeeService;
	/**
     * 
     * @param employee
     * @param ucBuilder
     * @return
     */
    @RequestMapping(value = "employee/{id}/clockin", method = RequestMethod.POST)
    public ResponseEntity<?> createEmployee(@PathVariable("id") Long employeeId, UriComponentsBuilder ucBuilder) {
    	Employee employee = employeeService.findById(employeeId);
    	if(null == employee)
    	{
    		String errorMsg = String.format("Unable to save. Employee with id %s not found.", employeeId);
    		logger.error(errorMsg);
    		return new ResponseEntity(new CustomErrorType(errorMsg), HttpStatus.NOT_FOUND);
    	}
    	LocalDateTime now = LocalDateTime.now();
    	
    	if(!clockinService.hasClockinsBetweenDateTime(employee, now.minusMinutes(Constant.TIME_INTERVAL_INVALID_SAVE_CLOCK_IN_MINUTES), now))
    	{
    		Clockin clockin = new Clockin(employee, now);
        	
        	logger.info("Creating clockin: {}", clockin);

            clockinService.save(clockin);
     
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/clockin/{id}").buildAndExpand(clockin.getId()).toUri());
            return new ResponseEntity<Clockin>(clockin, headers, HttpStatus.CREATED);
    	}else
    	{
    		String errorMsg = String.format("Unable to save. It has been registered a timestamp in the last %s minute(s).", Constant.TIME_INTERVAL_INVALID_SAVE_CLOCK_IN_MINUTES);
    		logger.error(errorMsg);
    		return new ResponseEntity(new CustomErrorType(errorMsg), HttpStatus.BAD_REQUEST);
    	}
    	
    	
    }
    
    @RequestMapping(value = "employee/{id}/clockin", method = RequestMethod.GET)
    public ResponseEntity<?> retrieveClockinsById(@PathVariable("id") Long employeeId, UriComponentsBuilder ucBuilder) {
    	Employee employee = employeeService.findById(employeeId);
    	if(null == employee)
    	{
    		String errorMsg = String.format("Unable to save. Employee with id %s not found.", employeeId);
    		logger.error(errorMsg);
    		return new ResponseEntity(new CustomErrorType(errorMsg), HttpStatus.NOT_FOUND);
    	}
    	
    	List<Clockin> clockins = clockinService.retrieveClockinsByEmployee(employeeId);
    	 
        return new ResponseEntity<List<Clockin>>(clockins, HttpStatus.CREATED);
    }    

 
	//	 /**
//	  * 
//	  * @return
//	  */
//    @RequestMapping(value = "/employee", method = RequestMethod.GET)
//    public ResponseEntity<List<Employee>> listAllEmployees() {
//    	logger.info("Retrieve all  employee");
//    	List<Employee> employees = clockinService.findAll();
//        if (employees.isEmpty()) {
//            return new ResponseEntity(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
//    }
    
//    /**
//     * 
//     * @param id
//     * @return
//     */
//    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
//    public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id ) {
//        logger.info("Fetching employee with id {}", id);
//        Employee employee = clockinService.findById(id);
//        if (employee == null) {
//            logger.error("employee with pis {} not found.", id);
//            return new ResponseEntity(new CustomErrorType(String.format("Employee with id %s not found", id)), HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
//    }
    
       
}