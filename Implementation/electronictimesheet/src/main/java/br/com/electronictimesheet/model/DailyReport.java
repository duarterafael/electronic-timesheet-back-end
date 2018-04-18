package br.com.electronictimesheet.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import br.com.electronictimesheet.util.RestType;

public class DailyReport {
	
	private List<Clockin> clocksin;
	private LocalDate date;
	private LocalTime amountWorkTime;
	private LocalTime amountOuverTime;
	private RestType restType;
	
	public DailyReport(List<Clockin> clocksin, LocalDate date, LocalTime amountWorkTime, LocalTime amountOuverTime,
			RestType restType) {
		super();
		this.clocksin = clocksin;
		this.date = date;
		this.amountWorkTime = amountWorkTime;
		this.amountOuverTime = amountOuverTime;
		this.restType = restType;
	}

	public List<Clockin> getClocksin() {
		return clocksin;
	}

	public LocalTime getAmountWorkTime() {
		return amountWorkTime;
	}

	public LocalTime getAmountOuverTime() {
		return amountOuverTime;
	}

	public RestType getRestType() {
		return restType;
	}

	public LocalDate getDate() {
		return date;
	}
	
}
