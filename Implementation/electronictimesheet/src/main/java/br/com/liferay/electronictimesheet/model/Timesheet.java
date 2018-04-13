package br.com.liferay.electronictimesheet.model;

public class Timesheet {
	
	 private final long id;
	private final String pis;

	    public Timesheet(long id, String pis) {
	        this.id = id;
	        this.pis = pis;
	    }

	    public long getId() {
	        return id;
	    }

	    public String getPis() {
	        return pis;
	    }

}
