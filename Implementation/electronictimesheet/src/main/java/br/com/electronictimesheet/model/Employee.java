package br.com.electronictimesheet.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String pis;
	
	@NotNull
	private String password;
	
	@NotNull
	private String fullNane;
	
	@OneToMany(mappedBy = "employee")
	private List<Clockin> clocksins = new LinkedList<Clockin>();

	private Employee() {}

	public Employee(String pis, String password, String fullNane) {
		super();
		this.pis = pis;
		this.password = password;
		this.fullNane = fullNane;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullNane() {
		return fullNane;
	}

	public void setFullNane(String fullNane) {
		this.fullNane = fullNane;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Clockin> getClocksin() {
		return clocksins;
	}

	public void setClocksin(List<Clockin> clocksin) {
		this.clocksins = clocksin;
	}

}
