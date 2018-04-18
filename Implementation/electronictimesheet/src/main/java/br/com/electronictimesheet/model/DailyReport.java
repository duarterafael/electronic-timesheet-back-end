package br.com.electronictimesheet.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import br.com.electronictimesheet.util.RestType;

public class DailyReport {
	
	private List<Clockin> clocksin;
	private LocalDate date;
	private LocalTime amountWorkTime;
	private LocalTime amountExtraTime;
	private RestType restType;
	
	public DailyReport(List<Clockin> clocksin, LocalDate date, LocalTime amountWorkTime, LocalTime amountExtraTime,
			RestType restType) {
		super();
		this.clocksin = clocksin;
		this.date = date;
		this.amountWorkTime = amountWorkTime;
		this.amountExtraTime = amountExtraTime;
		this.restType = restType;
	}

	public List<Clockin> getClocksin() {
		return clocksin;
	}

	public LocalTime getAmountWorkTime() {
		return amountWorkTime;
	}

	public LocalTime getAmountExtraTime() {
		return amountExtraTime;
	}

	public RestType getRestType() {
		return restType;
	}

	public LocalDate getDate() {
		return date;
	}
	
}
