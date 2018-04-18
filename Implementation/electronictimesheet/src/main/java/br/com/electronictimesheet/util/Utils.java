package br.com.electronictimesheet.util;

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

	
	

}
