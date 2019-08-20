package fr.houseofcode.dap.server.rma.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

/**
 *  class of CalendarService.
 */

@Service
public  class CalendarService {

    /**the internal APPLICATION_NAME.*/
    private static  String APPLICATION_NAME =
            "Google Calendar API Java Quickstart";

    /**
     * constant APPLICATION_NAME.
     * @return constant APPLICATION_NAME
     */
    public static String getApplicationName() {
        return APPLICATION_NAME;
    }

    /**
     * @return a list of service.
     * @param userKey accept name of person who use the application
     * @throws GeneralSecurityException exception
     * @throws IOException exception
     */
    private static Calendar getCalendarService( String userKey)
            throws GeneralSecurityException, IOException {
         NetHttpTransport hTTPTRANSPORT =
                GoogleNetHttpTransport.newTrustedTransport();

        Calendar service = new Calendar.Builder(hTTPTRANSPORT,
                Utils.getJsonFactory(), Utils.getCredentials(userKey))
                .setApplicationName(CalendarService.getApplicationName())
                .build();
        return service;

    }

    /**
     * @return a String who contains the nextEvent.
     * @param userKey accept name of person who use the application
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */

    public String getNextEvent( String userKey)
            throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.

        String str = "No upcoming events found.";
        DateTime now = new DateTime(System.currentTimeMillis());
//        DateTime end = new DateTime(System.currentTimeMillis());
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
                str = "Evenement à venir =" + " "
                + event.getSummary() + " pour le : " + start + " et fin de l'evenement pour le : " + endEvent;
            }

        }

        return str;
    }

}
