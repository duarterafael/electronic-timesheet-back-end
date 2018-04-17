package br.com.electronictimesheet.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import br.com.electronictimesheet.model.Clockin;
import br.com.electronictimesheet.model.Employee;

@Component
public interface ClockinRepository extends JpaRepository<Clockin, Long> {
    public Optional<Clockin> findByEmployee(Employee employee);
    
    public Optional<Clockin> findByEmployeeAndDateTimeBetween(Employee employee, LocalDateTime startDateTime, LocalDateTime endDateTime);
    
    public List<Clockin> findByemployeeId(Long employee_id);
    
    public Optional<Clockin> findTop1ByOrderByIdDesc();
    
    
}