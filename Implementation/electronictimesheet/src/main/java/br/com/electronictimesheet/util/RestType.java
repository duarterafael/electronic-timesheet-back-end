package br.com.electronictimesheet.util;

import br.com.electronictimesheet.enums.RestTypeCode;

public class RestType {
	
	private RestTypeCode restTypeCode;
	private int valueRestTypeCode;
	private String restTypeMessage;
	
	public RestType(RestTypeCode restTypeCode, String restTypeMessage) {
		super();
		this.restTypeCode = restTypeCode;
		this.restTypeMessage = restTypeMessage;
		this.valueRestTypeCode = restTypeCode.ordinal();
	}

	public RestTypeCode getRestTypeCode() {
		return restTypeCode;
	}

	public void setRestTypeCode(RestTypeCode restTypeCode) {
		this.restTypeCode = restTypeCode;
	}

	public String getRestTypeMessage() {
		return restTypeMessage;
	}

	public void setRestTypeMessage(String restTypeMessage) {
		this.restTypeMessage = restTypeMessage;
	}

	public int getValueRestTypeCode() {
		return valueRestTypeCode;
	}

	
	
}
