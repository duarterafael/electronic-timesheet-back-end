package br.com.electronictimesheet.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.electronictimesheet.dao.ClockinRepository;
import br.com.electronictimesheet.model.Clockin;
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
		clockin.setId(counterID.incrementAndGet());
		clockinRepository.save(clockin);
	}

	
}
