package fr.houseofcode.dap.server.rma.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

/**
 *  @author rma.
 *  5 juil. 2019
 *  Acces to the Google Calendar Api.
 */
@Service
public  class CalendarService {

    /**
     * Instance of Logger.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Default name of the API.
     */
    private static final String APPLICATION_NAME =
            "Google Calendar API Java Quickstart";

    /**
     * Access to services of Google Calendar.
     * @return a list of service.
     * @param userKey accept name of person who use the application
     * @throws GeneralSecurityException exception
     * @throws IOException exception
     */
    private static Calendar getCalendarService(final String userKey) throws GeneralSecurityException, IOException {
        LOG.debug("recuperation d'un acces au service Google calendar for userKey : "
                + userKey);
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new Calendar.Builder(httpTransport, Utils.getJsonFactory(), Utils.getCredentials(userKey))
                .setApplicationName(CalendarService.APPLICATION_NAME)
                .build();
    }

    /**
     * Load next event containing in Google Calendar.
     * @return a String who contains the nextEvent.
     * @param userKey accept name of person who use the application
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    public String getNextEvent(final String userKey)
            throws IOException, GeneralSecurityException {

        LOG.debug("recuperation du prochain event Google calendar for userKey : "
                + userKey);

        String str = "No upcoming events found.";
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = getCalendarService(userKey).events()
                .list("primary").setMaxResults(1).setTimeMin(now)
                .setOrderBy("startTime").setSingleEvents(true).execute();

        List<Event> items = events.getItems();
        if (!items.isEmpty()) {
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                DateTime endEvent = event.getEnd().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                    endEvent = event.getEnd().getDate();
                }
                str = event.getSummary() + start + endEvent;
            }
        }
        return str;
    }
}
