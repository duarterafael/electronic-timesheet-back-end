package br.com.electronictimesheet.util;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import br.com.electronictimesheet.model.Clockin;

public class Utils {
	
	public static <T> Object returnValueOrDefault(Optional<T> optional)
	{
		return optional.isPresent() ?  optional.get() : null;
	}
	
	public static ZoneOffset getSystemDefaultZoneOffset()
	{
		Instant instant = Instant.now(); 
		ZoneId systemZone = ZoneId.systemDefault(); 
		ZoneOffset currentOffsetForMyZone = systemZone.getRules().getOffset(instant);
		return currentOffsetForMyZone;
	}
	
	public static LocalDateTime second2LocalDateTime(Long seconds, ZoneOffset zoneOffset)
	{
		return LocalDateTime.ofEpochSecond(seconds, 0, zoneOffset);
	}
	
	public static Long diffDatesInSeconds(LocalDateTime startDate, LocalDateTime endDate)
	{
		return  ChronoUnit.SECONDS.between(endDate, startDate);
	}
	
	public static Long getAmountdifferenceTimeInSeconds(List<Clockin> clocksin)
	{
		Long amount = 0L;
		for(int i = 0, j = i+1; j < clocksin.size(); i=i+2, j=i+1)
		{
			amount += Utils.diffDatesInSeconds(clocksin.get(i).getDateTime(), clocksin.get(j).getDateTime());
		}
		return  amount;
	}
	public static boolean isSameDay(LocalDateTime datetime1, LocalDateTime datetime2) {
		return datetime1.getYear() == datetime2.getYear() && datetime1.getMonth() == datetime2.getMonth()
				&& datetime1.getDayOfYear() == datetime2.getDayOfYear();
	}
	
	public static boolean isWorkDay(LocalDateTime dateTime) {
		return dateTime.getDayOfWeek() != DayOfWeek.SATURDAY && dateTime.getDayOfWeek() != DayOfWeek.SUNDAY;
	}

	

}
