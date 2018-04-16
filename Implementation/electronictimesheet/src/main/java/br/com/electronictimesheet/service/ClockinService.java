package br.com.electronictimesheet.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.electronictimesheet.dao.ClockinRepository;
import br.com.electronictimesheet.model.Clockin;
import br.com.electronictimesheet.util.Utils;
@Service
public class ClockinService {
	@Autowired
	private ClockinRepository clockinRepository;
	
	private final AtomicLong counterID = new AtomicLong();
	
	
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
