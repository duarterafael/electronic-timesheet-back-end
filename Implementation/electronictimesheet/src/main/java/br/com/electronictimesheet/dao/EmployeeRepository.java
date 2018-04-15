package br.com.electronictimesheet.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import br.com.electronictimesheet.model.Employee;
import javax.transaction.Transactional;
import java.util.Optional;

@Component
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByPis(String pis);
}