package br.com.electronictimesheet.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.electronictimesheet.dao.ClockinRepository;
import br.com.electronictimesheet.model.Clockin;
import br.com.electronictimesheet.model.Employee;
import br.com.electronictimesheet.util.Utils;
@Service
public class ClockinService {
	@Autowired
	private ClockinRepository clockinRepository;
	
	public List<Clockin> retrieveClockinsBetweenDateTime(Employee employee, LocalDateTime startDateTime, LocalDateTime endDateTime)
	{
		List<Clockin> clocksin = clockinRepository.findByEmployeeAndDateTimeBetweenOrderByDateTimeDesc(employee, startDateTime, endDateTime);
		return clocksin;
 	}
	
	public boolean hasClockinsBetweenDateTime(Employee employee, LocalDateTime startDateTime, LocalDateTime endDateTime)
	{
		List<Clockin> clocksin = retrieveClockinsBetweenDateTime(employee, startDateTime, endDateTime);
    	return clocksin != null && !clocksin.isEmpty();
	}
	
	
	public List<Clockin> retrieveClockinsByEmployee(Long employee_id)
	{
		return clockinRepository.findByemployeeId(employee_id);	
	}
	
	public void save(Clockin clockin) {
		
		Optional<Clockin> optionalLastClockin = clockinRepository.findTop1ByOrderByIdDesc();
		Clockin lastClockin = (Clockin) Utils.returnValueOrDefault(optionalLastClockin);
		Long nextId = lastClockin == null? 1: lastClockin.getId()+1;
		
		clockin.setId(nextId);
		clockinRepository.save(clockin);
	}
	
	
}
