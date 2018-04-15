package br.com.electronictimesheet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.UniqueElements;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@UniqueElements
	private String pis;
	
	private String password;
	private String fullNane;
	
	@OneToMany(mappedBy = "employee")
    private List<Clockin> clocksin = new LinkedList<Clockin>();

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
		return clocksin;
	}

	public void setClocksin(List<Clockin> clocksin) {
		this.clocksin = clocksin;
	}

}
