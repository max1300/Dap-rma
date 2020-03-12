package fr.houseofcode.dap.server.rma;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.houseofcode.dap.server.rma.google.CalendarService;

/**
 * @author rma.
 * 5 august. 2019
 */
@RestController
public class EventController {

    private static final Logger LOG = LogManager.getLogger();

    /**
     * object CalendarService.
     */
    private CalendarService calendarService;

    public EventController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    /**
     * next event on googleCalendar.
     * @return the next event event on googleCalendar
     * @param userKey accept name of person who use the application
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    @GetMapping("/event/next")
    public String displayNextEvent(@RequestParam String userKey) throws IOException, GeneralSecurityException {
        LOG.info("recuperation des next events");
        return calendarService.getNextEvent(userKey);
    }

}
