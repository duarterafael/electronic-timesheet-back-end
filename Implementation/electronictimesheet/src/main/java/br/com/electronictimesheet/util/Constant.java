package br.com.electronictimesheet.util;

public interface Constant {

	public static final int TIME_INTERVAL_INVALID_SAVE_CLOCK_IN_MINUTES = 1;
	
	public static final int MINIMUM_HOUR_FOR_OUVERTIME_NIGHT_SHIFT = 22;
	public static final int MINIMUM_MINUTE_FOR_OUVERTIME_NIGHT_SHIFT = 00;

	public static final int MAXNIMUM_HOUR_FOR_OUVERTIME_NIGHT_SHIFT = 06;
	public static final int MAXIMUM_MINUTE_FOR_OUVERTIME_NIGHT_SHIFT = 00;
	
	
	public static final int MINIMUM_HOUR_FOR_NO_REST_REQUIRED = 0;
	public static final int MAXIMUM_HOUR_FOR_NO_REST_REQUIRED = 4;
	public static final int MINIMUM_HOUR_FOR_REST_OF_15_MINUTES_IS_REQUIRED = 6;
	
	public static final double PERCERT_SATURDAY_OUVERTIME_AMOUNT = 1.5;
	public static final double PERCERT_SUNDAY_OUVERTIME_AMOUNT = 2.0;
	public static final double PERCERT_NIGHT_SHIFT_OUVERTIME_AMOUNT = 0.2;
	
	public static final int JANUARY_MONTH_CODE = 1;
	public static final int DECEMBER_MONTH_CODE = 12;
	
	
}
