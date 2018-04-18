package br.com.electronictimesheet.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import br.com.electronictimesheet.enums.RestTypeCode;
import br.com.electronictimesheet.model.Clockin;
import br.com.electronictimesheet.model.DailyReport;
import br.com.electronictimesheet.util.Range;
import br.com.electronictimesheet.util.RestType;
import br.com.electronictimesheet.util.Utils;

@Service
public class DailyReportService {
	
	public DailyReport generateDailyReport(List<Clockin> clocksin, LocalDate date)
	{
		Long amountWorkTime = Utils.getAmountdifferenceTimeInSeconds(clocksin);
		RestType restType = selectRestType(getRangeRestTypes(), amountWorkTime);
		
		LocalTime amountWorkLocalTime = LocalTime.ofSecondOfDay(amountWorkTime);
		LocalTime amountExtraTime = LocalTime.ofSecondOfDay(amountWorkTime);
		
		return new DailyReport(clocksin,date, amountWorkLocalTime, amountExtraTime, restType);
	}

	
	private List<Range<RestType>> getRangeRestTypes()
	{
		List<Range<RestType>> restTypes = new LinkedList<>();
		
		//If the law changes in the future, 
		//just create new ranges or change existing ones.
		//Please, add the range of values ​​in ascending order. 
		restTypes.add(new Range<RestType>( new RestType(RestTypeCode.NO_REST_REQUIRED, "No rest required."), TimeUnit.HOURS.toSeconds(0), TimeUnit.HOURS.toSeconds(4)));
		restTypes.add(new Range<RestType>( new RestType(RestTypeCode.MINIMUM_REST_OF_15_MINUTES_IS_REQUIRED, "A minimum rest of 15 minutes is required."), TimeUnit.HOURS.toSeconds(4), TimeUnit.HOURS.toSeconds(6)));
		restTypes.add(new Range<RestType>( new RestType(RestTypeCode.MINIMUM_REST_OF_60_MINUTES_IS_REQUIRED, "A minimum rest of 60 minutes is required."), TimeUnit.HOURS.toSeconds(6), Long.MAX_VALUE));
		
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
