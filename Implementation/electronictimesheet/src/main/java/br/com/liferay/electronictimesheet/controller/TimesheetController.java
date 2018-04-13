package br.com.liferay.electronictimesheet.controller;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import br.com.liferay.electronictimesheet.model.*;

@RestController
public class TimesheetController {

    private static final String template = "Hello123, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/timesheet")
    public Timesheet retrieveTimesheet(@RequestParam(value="name", defaultValue="World") String pis) {
        return new Timesheet(counter.incrementAndGet(),
                            String.format(template, pis));
    }
}