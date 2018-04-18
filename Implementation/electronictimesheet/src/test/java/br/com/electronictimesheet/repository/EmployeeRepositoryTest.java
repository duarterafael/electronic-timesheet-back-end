package br.com.electronictimesheet.repository;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.electronictimesheet.dao.EmployeeRepository;
import br.com.electronictimesheet.model.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EmployeeRepositoryTest {

     @Autowired
    private EmployeeRepository employeeRepository;
    
    public static boolean isSameEmployee(Employee employee1, Employee employee2)
    {
    	return employee1.getFullNane().equals(employee2.getFullNane()) &&
    			employee1.getPassword().equals(employee2.getPassword()) &&
    			employee1.getPis().equals(employee2.getPis());
    }
    
    
	@Test
	public void whenSaveEmployee_and_FindByPis_thenReturnEmployee() {
	    
		Employee employee = new Employee("123456789", "123456789", "Rafael Duarte");
		Employee assertEmployee = new Employee(employee.getPis(), employee.getPassword(), employee.getFullNane());
		
		employeeRepository.save(employee);
		
		assertTrue(isSameEmployee(employee, assertEmployee));
		
	 
	    Optional<Employee> foundEmployee = employeeRepository.findByPis(employee.getPis());
	 
	    assertTrue(foundEmployee.isPresent());
	    
	    assertTrue(isSameEmployee(foundEmployee.get(), employee));
	}
	
	@Test
	public void whenSave3Employees_and_FindAll_thenReturnEmployeeCreated() {
	    
		Employee employee1 = new Employee("123", "2", "Rafael Duarte1");
		Employee assertEmployee1 = new Employee(employee1.getPis(), employee1.getPassword(), employee1.getFullNane());
		employeeRepository.save(employee1);
		
		Employee employee2 = new Employee("456", "456", "Rafael Duarte2");
		Employee assertEmployee2 = new Employee(employee2.getPis(), employee2.getPassword(), employee2.getFullNane());
		employeeRepository.save(employee2);
		
		Employee employee3 = new Employee("789", "789", "Rafael Duarte3");
		Employee assertEmployee3 = new Employee(employee3.getPis(), employee3.getPassword(), employee3.getFullNane());
		employeeRepository.save(employee3);
		
	    List<Employee> allEmployees = employeeRepository.findAll();
	 
	    assertTrue(isSameEmployee(allEmployees.get(0), assertEmployee3));
	    assertTrue(isSameEmployee(allEmployees.get(1), assertEmployee2));
	    assertTrue(isSameEmployee(allEmployees.get(2), assertEmployee1));
	}

}
