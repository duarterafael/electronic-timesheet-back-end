package br.com.electronictimesheet.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import br.com.electronictimesheet.enums.RestTypeCode;
import br.com.electronictimesheet.model.Clockin;
import br.com.electronictimesheet.model.DailyReport;
import br.com.electronictimesheet.util.Constant;
import br.com.electronictimesheet.util.Range;
import br.com.electronictimesheet.util.RestType;
import br.com.electronictimesheet.util.Utils;

@Service
public class ReportService {
	
	
	
	
	public DailyReport generateDailyReport(List<Clockin> clocksin, LocalDate date)
	{
		Long amountWorkTime = Utils.getAmountdifferenceTimeInSeconds(clocksin);
		RestType restType = selectRestType(getRangeRestTypes(), amountWorkTime);
		
		LocalTime amountWorkLocalTime = LocalTime.ofSecondOfDay(amountWorkTime);
		LocalTime amountOuverTime = LocalTime.ofSecondOfDay(calculateOuverTimeAmount(clocksin));
		
		return new DailyReport(clocksin,date, amountWorkLocalTime, amountOuverTime, restType);
	}
	
	private Long calculateOuverTimeAmount(List<Clockin> clocksin) {
		Long ouverTimeAmount = 0L;

		for (int i = 0, j = i + 1; j < clocksin.size(); i = i + 2, j = i + 1) {
			LocalDateTime startDatetime = clocksin.get(i).getDateTime();
			LocalDateTime endDateTime = clocksin.get(j).getDateTime();

			if (Utils.isWorkDay(startDatetime)) {
				ouverTimeAmount += Utils.diffDatesInSeconds(startDatetime, endDateTime);
			} else if (startDatetime.getDayOfWeek() == DayOfWeek.SATURDAY) {
				ouverTimeAmount += (long) (Constant.PERCERT_SATURDAY_OUVERTIME_AMOUNT *  Utils.diffDatesInSeconds(startDatetime, endDateTime));
			} else {
				ouverTimeAmount += (long) (Constant.PERCERT_SUNDAY_OUVERTIME_AMOUNT *  Utils.diffDatesInSeconds(startDatetime, endDateTime));
			}

			ouverTimeAmount += calculateOuverTimeNightshift(startDatetime, endDateTime);


		}
		return ouverTimeAmount;
	}
	
	private Long calculateOuverTimeNightshift(LocalDateTime startDate, LocalDateTime endDate) {
		boolean isNextDay = startDate.getDayOfYear() == endDate.getDayOfYear() + 1;
		Long diffDateInSeconds = 0L;
		LocalDateTime endDate22Hour = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(),
				Constant.MINIMUM_HOUR_FOR_OUVERTIME_NIGHT_SHIFT, Constant.MINIMUM_MINUTE_FOR_OUVERTIME_NIGHT_SHIFT);
		
		LocalDateTime startDate6Hour = LocalDateTime.of(startDate.getYear(), startDate.getMonth(),
				startDate.getDayOfMonth(), Constant.MAXNIMUM_HOUR_FOR_OUVERTIME_NIGHT_SHIFT, Constant.MAXIMUM_MINUTE_FOR_OUVERTIME_NIGHT_SHIFT);

		if (isNextDay) {
			if ((startDate.getHour() <= Constant.MAXNIMUM_HOUR_FOR_OUVERTIME_NIGHT_SHIFT && endDate.getHour() >= Constant.MINIMUM_HOUR_FOR_OUVERTIME_NIGHT_SHIFT)) {
				diffDateInSeconds = Utils.diffDatesInSeconds(startDate, endDate);
			} else if (startDate.getHour() >= Constant.MAXNIMUM_HOUR_FOR_OUVERTIME_NIGHT_SHIFT && endDate.getHour() <= Constant.MINIMUM_HOUR_FOR_OUVERTIME_NIGHT_SHIFT) {
				diffDateInSeconds = Utils.diffDatesInSeconds(startDate6Hour, endDate22Hour);
			}

		} else if (Utils.isSameDay(startDate, endDate)) {
			if (endDate.getHour() <= Constant.MINIMUM_HOUR_FOR_OUVERTIME_NIGHT_SHIFT && startDate.getHour() >= Constant.MINIMUM_HOUR_FOR_OUVERTIME_NIGHT_SHIFT) {
				diffDateInSeconds = Utils.diffDatesInSeconds(startDate, endDate22Hour);
			} else if (endDate.getHour() <= Constant.MAXNIMUM_HOUR_FOR_OUVERTIME_NIGHT_SHIFT && startDate.getHour() >= Constant.MAXNIMUM_HOUR_FOR_OUVERTIME_NIGHT_SHIFT) {
				diffDateInSeconds = Utils.diffDatesInSeconds(startDate6Hour, endDate);
			}

		}

		Long ouverTimeNightshift = (long) (Constant.PERCERT_NIGHT_SHIFT_OUVERTIME_AMOUNT * diffDateInSeconds);
		return ouverTimeNightshift;
	}

	
	public static List<Range<RestType>> getRangeRestTypes() {
		List<Range<RestType>> restTypes = new LinkedList<>();

		// If the law changes in the future, simply create new ranges or change existing
		// ones.
		// Please, add the range of values â€‹â€‹in ascending order.
		restTypes.add(new Range<RestType>(new RestType(RestTypeCode.NO_REST_REQUIRED, "No rest required."),
				TimeUnit.HOURS.toSeconds(Constant.MINIMUM_HOUR_FOR_NO_REST_REQUIRED), 
				TimeUnit.HOURS.toSeconds(Constant.MAXIMUM_HOUR_FOR_NO_REST_REQUIRED)));
		
		restTypes.add(new Range<RestType>(
				new RestType(RestTypeCode.MINIMUM_REST_OF_15_MINUTES_IS_REQUIRED,
						"A minimum rest of 15 minutes is required."),
				TimeUnit.HOURS.toSeconds(Constant.MAXIMUM_HOUR_FOR_NO_REST_REQUIRED), 
				TimeUnit.HOURS.toSeconds(Constant.MINIMUM_HOUR_FOR_REST_OF_15_MINUTES_IS_REQUIRED)));
		
		restTypes.add(new Range<RestType>(
				new RestType(RestTypeCode.MINIMUM_REST_OF_60_MINUTES_IS_REQUIRED,
				"A minimum rest of 60 minutes is required."), 
				TimeUnit.HOURS.toSeconds(Constant.MINIMUM_HOUR_FOR_REST_OF_15_MINUTES_IS_REQUIRED), 
				Long.MAX_VALUE));

		return restTypes;
	}
	
	private RestType selectRestType(List<Range<RestType>> rangeRestTypes, Long value)
	{
		for (Range<RestType> range : rangeRestTypes) {
			if(range.includes(value))
			{
				return range.getValue();
			}
		}
		return null;
	}
	
	
}
