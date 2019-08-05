package fr.houseofcode.dap.server.rma;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.houseofcode.dap.server.rma.google.CalendarService;

/**
 * @author rma.
 * 5 august. 2019
 */
@RestController
public class EventController {

    /**
     * object CalendarService.
     */
    @Autowired
    private CalendarService calendarService;

    /**
     * next event on googleCalendar.
     * @return the next event event on googleCalendar
     * @param userKey accept name of person who use the application
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    @RequestMapping("/event/next")
    public String displayNextEvent(@RequestParam final String userKey)
            throws IOException, GeneralSecurityException {

        return calendarService.getNextEvent(userKey);
    }

}
