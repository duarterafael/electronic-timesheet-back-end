package br.com.electronictimesheet.repository;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.electronictimesheet.dao.ClockinRepository;
import br.com.electronictimesheet.dao.EmployeeRepository;
import br.com.electronictimesheet.model.Clockin;
import br.com.electronictimesheet.model.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ClockinRepositoryTest {

     @Autowired
    private ClockinRepository clockinRepository;
     
     @Autowired
     private EmployeeRepository employeeRepository;
     
    
    public static boolean isSameClockin(Clockin clockin1, Clockin clockin2)
    {
    	return clockin1.getDateTime().equals(clockin1.getDateTime()) &&
    			clockin1.getEmployee().getId().equals(clockin2.getEmployee().getId());
    }
    
    
	@Test
	public void whenSaveClockin_and_findByEmployee_thenReturnClockinEmployee() {
	    
		Employee employee = new Employee("123456789", "123456789", "Rafael Duarte");
		Employee assertEmployee = new Employee(employee.getPis(), employee.getPassword(), employee.getFullNane());
		
		employeeRepository.save(employee);
		
		assertTrue(EmployeeRepositoryTest.isSameEmployee(employee, assertEmployee));
		
		Clockin clockin = new Clockin(employee, LocalDateTime.now());
		Clockin assertClockin = new Clockin(clockin.getEmployee(), clockin.getDateTime());
		
		
		clockinRepository.save(clockin);
	 
	    Optional<Clockin> foundClockin = clockinRepository.findByEmployee(employee);
	 
	    assertTrue(foundClockin.isPresent());
	    
	    assertTrue(isSameClockin(foundClockin.get(), assertClockin));
	}
	
	

}
