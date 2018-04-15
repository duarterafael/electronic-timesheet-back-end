package br.com.electronictimesheet.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import br.com.electronictimesheet.model.Employee;

@Component
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByPis(String pis);
}