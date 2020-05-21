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

    //TODO RMA by Djer |JavaDoc| Il est courrant décrire la javaDoc des "attributs" sur une seul ligne (car souvent ils n'ont aucun paramètres). Je t'ai fait la modification pour l'exemple.
    /** Instance of Logger to use in class. */
    private static final Logger LOG = LogManager.getLogger();

    /** Object CalendarService. */
    private CalendarService calendarService;

    /**
     * Constructor for eventController class.
     * @param mCalendarService
     */
    //TODO RMA by Djer |JavaDoc| Il manque la description du paramètre "mCalendarService"
    public EventController(final CalendarService mCalendarService) {
        this.calendarService = mCalendarService;
    }

    /**
     * Next event on googleCalendar.
     * @return the next event event on googleCalendar
     * @param userKey accept name of person who use the application
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    @GetMapping("/event/next")
    public String displayNextEvent(@RequestParam final String userKey) throws IOException, GeneralSecurityException {
        //TODO RMA by Djer |JavaDoc| Contextualise tes messages de log. "... for userKey : " + userKey.
        LOG.info("recuperation des next events");
        return calendarService.getNextEvent(userKey);
    }

}
