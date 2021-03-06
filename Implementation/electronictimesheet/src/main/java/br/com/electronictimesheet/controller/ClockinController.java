package br.com.electronictimesheet.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.electronictimesheet.model.Clockin;
import br.com.electronictimesheet.model.DailyReport;
import br.com.electronictimesheet.model.Employee;
import br.com.electronictimesheet.service.ClockinService;
import br.com.electronictimesheet.service.ReportService;
import br.com.electronictimesheet.service.EmployeeService;
import br.com.electronictimesheet.util.Constant;
import br.com.electronictimesheet.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class ClockinController {

	public static final Logger logger = LoggerFactory.getLogger(ClockinController.class);
	@Autowired
	private ClockinService clockinService;
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ReportService reportService;

	/**
	 * Save a new a timestamp to a specific employee.
	 * 
	 * @param employeeId:
	 *            Employee identifier (primary key).
	 * @param ucBuilder:
	 *            Assing the url to find the new timestamp in header, If save with
	 *            sucess.
	 * @return: NOT_FOUND(404): Employee not found for id informed. Returns a error
	 *          message. BAD_REQUEST(400): It has been registered a timestamp in the
	 *          last 1 minute(s). Returns a error message. CREATED(201): The
	 *          timestamp was created with success. Returns timestamp datas.
	 */
	@RequestMapping(value = "employee/{id}/clockin", method = RequestMethod.POST)
	public ResponseEntity<?> createClockin(@PathVariable("id") Long employeeId, UriComponentsBuilder ucBuilder) {
		Employee employee = employeeService.findById(employeeId);
		if (null == employee) {
			String errorMsg = String.format("Unable to save. Employee with id %s not found.", employeeId);
			logger.error(errorMsg);
			return new ResponseEntity(new CustomErrorType(errorMsg), HttpStatus.NOT_FOUND);
		}
		LocalDateTime now = LocalDateTime.now();

		if (!clockinService.hasClockinsBetweenDateTime(employee,
				now.minusMinutes(Constant.TIME_INTERVAL_INVALID_SAVE_CLOCK_IN_MINUTES), now)) {
			Clockin clockin = new Clockin(employee, now);

			logger.info("Creating clockin: {}", clockin);

			clockinService.save(clockin);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/api/clockin/{id}").buildAndExpand(clockin.getId()).toUri());
			return new ResponseEntity<Clockin>(clockin, headers, HttpStatus.CREATED);
		} else {
			String errorMsg = String.format(
					"Unable to save. It has been registered a timestamp in the last %s minute(s).",
					Constant.TIME_INTERVAL_INVALID_SAVE_CLOCK_IN_MINUTES);
			logger.error(errorMsg);
			return new ResponseEntity(new CustomErrorType(errorMsg), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Retrieve the list of timestamp relative a specific employee.
	 * 
	 * @param employeeId:
	 *            Employee identifier (foreign key).
	 * @return: NOT_FOUND(404): Employee not found for id informed. Returns a error.
	 *          message. OK(200): Timestamp registers found successfully. Returns a
	 *          list of timestamp registers datas.
	 */
	@RequestMapping(value = "employee/{id}/clockin", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveClockinsById(@PathVariable("id") Long employeeId, UriComponentsBuilder ucBuilder) {
		Employee employee = employeeService.findById(employeeId);
		if (null == employee) {
			String errorMsg = String.format("Unable to save. Employee with id %s not found.", employeeId);
			logger.error(errorMsg);
			return new ResponseEntity(new CustomErrorType(errorMsg), HttpStatus.NOT_FOUND);
		}

		List<Clockin> clockins = clockinService.retrieveClockinsByEmployee(employeeId);

		return new ResponseEntity<List<Clockin>>(clockins, HttpStatus.OK);
	}

	/**
	 * Retrieve daily report to a specific employee.
	 * @param employeeId:
	 * 			 Employee identifier (foreign key).
	 * @param inputDate:
	 * 			Target data of daily report. Format: yyyy-MM-dd.
	 * @return:
	 * 		NOT_FOUND(404): Employee not found for id informed. Returns a error.
	 * 		OK(200): The daily report was generated successfully. Returns daily report datas.
	 */
	@RequestMapping(value = "employee/{id}/clockin/dailyreport/{datetime}", method = RequestMethod.GET)
	public ResponseEntity<?> dailyReport(@PathVariable("id") Long employeeId,
			@PathVariable("datetime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date inputDate,
			UriComponentsBuilder ucBuilder) {
		Employee employee = employeeService.findById(employeeId);
		if (null == employee) {
			String errorMsg = String.format("Unable to get daily report. Employee with id %s not found.", employeeId);
			logger.error(errorMsg);
			return new ResponseEntity(new CustomErrorType(errorMsg), HttpStatus.NOT_FOUND);
		}

		LocalDateTime startDateTime = LocalDateTime.ofInstant(inputDate.toInstant(), ZoneId.systemDefault());
		LocalDateTime endDateTime = LocalDateTime.ofInstant(inputDate.toInstant(), ZoneId.systemDefault()).plusHours(23)
				.plusMinutes(59).plusSeconds(59).plusNanos(999999999);

		List<Clockin> dailyClocksin = clockinService.retrieveClockinsBetweenDateTime(employee, startDateTime,
				endDateTime);

		LocalDate localInputDate = inputDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		DailyReport dailyReport = reportService.generateDailyReport(dailyClocksin, localInputDate);
		return new ResponseEntity<DailyReport>(dailyReport, HttpStatus.OK);

	}

	/**
	 * Retrieve monthly report to a specific employee.
	 * @param employeeId:
	 * 			 Employee identifier (foreign key).
	 * @param year:
	 * 			Target year of monthly report. Range 1970 until current year.
	 * @param monthCode:
	 * 			Target month of monthly report.
	 * 			Possible values:
	 * 			1 – January
	 *			2 – February
	 *			3 – March
	 *			4 – April
	 *			5 – May
	 *			6 – June
	 *			7 – July
	 *			8 – August
	 *			9 – September
	 *			10 – October
	 *			11 – November
	 *			12 – December
	 * @return:
	 * 		NOT_FOUND(404): Employee not found for id informed. Returns a error.
	 * 		OK(200): The daily report was generated successfully. Returns daily report datas.
	 */
	@RequestMapping(value = "employee/{id}/clockin/monthlyreport/year/{year}/month/{monthCode}", method = RequestMethod.GET)
	public ResponseEntity<?> monthlyReport(@PathVariable("id") Long employeeId, @PathVariable("year") int year,
			@PathVariable("monthCode") int monthCode, UriComponentsBuilder ucBuilder) {
		Employee employee = employeeService.findById(employeeId);
		if (null == employee) {
			String errorMsg = String.format("Unable to get monthly report. Employee with id %s not found.", employeeId);
			logger.error(errorMsg);
			return new ResponseEntity(new CustomErrorType(errorMsg), HttpStatus.NOT_FOUND);
		}

		LocalDate firstDate = LocalDate.ofEpochDay(0);
		LocalDate curretDate = LocalDate.now();
		if (year < firstDate.getYear() || year > curretDate.getYear()) {
			String errorMsg = String.format(
					"Unable to get monthly report. The 'year' parameter must be between the values ​​%S and %S.",
					firstDate.getYear(), curretDate.getYear());
			logger.error(errorMsg);
			return new ResponseEntity(new CustomErrorType(errorMsg), HttpStatus.BAD_REQUEST);
		}

		if (monthCode < Constant.JANUARY_MONTH_CODE || monthCode > Constant.DECEMBER_MONTH_CODE) {
			String errorMsg = "Unable to get monthly report. The 'monthCode' parameter must be between the values ​​1 (referring January) and 12 (referring to December).";
			logger.error(errorMsg);
			return new ResponseEntity(new CustomErrorType(errorMsg), HttpStatus.BAD_REQUEST);
		}

		LocalDate firstDayOfMonth = LocalDate.of(year, monthCode, 1);
		LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());

		List<DailyReport> monthlyReport = new LinkedList<>();
		for (int currentDayIndex = firstDayOfMonth.getDayOfMonth(); currentDayIndex <= lastDayOfMonth
				.getDayOfMonth(); currentDayIndex++) {
			LocalDateTime startDateTime = LocalDateTime.of(year, monthCode, currentDayIndex, 0, 0);
			LocalDateTime endDateTime = startDateTime.plusHours(23).plusMinutes(59).plusSeconds(59)
					.plusNanos(999999999);
			List<Clockin> dailyClocksin = clockinService.retrieveClockinsBetweenDateTime(employee, startDateTime,
					endDateTime);

			monthlyReport.add(reportService.generateDailyReport(dailyClocksin, startDateTime.toLocalDate()));
		}

		return new ResponseEntity<List<DailyReport>>(monthlyReport, HttpStatus.OK);

	}

}