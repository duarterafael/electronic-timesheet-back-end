package br.com.electronictimesheet.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import br.com.electronictimesheet.model.Clockin;
import br.com.electronictimesheet.model.Employee;

@Component
public interface ClockinRepository extends JpaRepository<Clockin, Long> {
    Optional<Clockin> findByEmployee(Employee employee);
    
//    @Query("SELECT c FROM clockin c WHERE c.employee_id = employee_id")
    public List<Clockin> findByemployeeId(Long employee_id);
}