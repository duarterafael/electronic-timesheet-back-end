package br.com.electronictimesheet.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Clockin {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
    @JoinColumn(name = "employee_id")
	@JsonIgnore
	private Employee employee;

	@NotNull
	private LocalDateTime dateTime;
	
	private Clockin() {}
	
	public Clockin(final Employee employee, LocalDateTime dateTime) {
		this.employee = employee;
		this.dateTime = dateTime;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
}
