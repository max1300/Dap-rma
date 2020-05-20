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

//TODO RMA by Djer |JavaDoc| Description pas très utiles "Acces to the Google calendars" serait plus utile.
/**
 *  class of CalendarService.
 */
@Service
public class CalendarService {
    /** LOG4J. */
    private static final Logger LOG = LogManager.getLogger();

    //TODO RMA by Djer |POO| Devraient être une constante (il manque "final") et écrit en majuscules.
    /** The internal APPLICATION_NAME.*/
    private static String applicationName = "Google Calendar API Java Quickstart";

    //TODO RMA by Djer |POO| attention à l'ordre. ordre attendu : Constantes, attributs, initialisateurs statics, constrcuteurs, méthodes métier, méthodes "utilitaires" (toString, hashCode,...) getter/setter, (éventuellement classes internes).
    //TODO RMA by Djer |POO| Ce getter n'est appelé QUE dans cette classe. Pour les constantent on y acès en générale directement (sans getter/setters). Si tu souhaites garder ce getter, il devrait être **private**
    /**
     * Getter for constant APPLICATION_NAME.
     * @return constant APPLICATION_NAME
     */
    public static String getApplicationName() {
        return applicationName;
    }

    /**
     * Access to services of Google Calendar.
     * @return a list of service.
     * @param userKey accept name of person who use the application
     * @throws GeneralSecurityException exception
     * @throws IOException exception
     */
    private static Calendar getCalendarService(final String userKey) throws GeneralSecurityException, IOException {
        //TODO RMA by Djer |Log4J| Contextualise tes messages de log. "for userKey : " + userKey.
        //TODO RMA by Djer |Log4J| Il n'est pas très utile d'indiquer que la méthode peut lever des exceptions dans la log. Si une execption était levée, la personne lisant la log le verra très bien.
        LOG.debug("recuperation d'un acces au service Google calendar"
                + " avec dÃ©clenchement possible d'exceptions (IOException " + "ou GeneralSecurityException");
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new Calendar.Builder(httpTransport, Utils.getJsonFactory(), Utils.getCredentials(userKey))
                .setApplicationName(CalendarService.getApplicationName()).build();
    }

    /**
     * Load next event containing in Google Calendar.
     * @return a String who contains the nextEvent.
     * @param userKey accept name of person who use the application
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    public String getNextEvent(final String userKey) throws IOException, GeneralSecurityException {
        //TODO RMA by Djer |POO| Ce commentaire est devenu obsolète.
        // Build a new authorized API client service.

        LOG.debug("recuperation du prochain event Google calendar"
                + " avec dÃ©clenchement possible d'exceptions (IOException " + "ou GeneralSecurityException");

        String str = "No upcoming events found.";
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = getCalendarService(userKey).events().list("primary").setMaxResults(1).setTimeMin(now)
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
                //TODO RMA by Djer |MVC| Ne formate pas les messages sur le serveur d'une API REST. Renvoie les données et laisse le client (ou thymeLeaf) effectuer le formatage.
                str = "Evenement Ã  venir =" + " " + event.getSummary() + " pour le : " + start
                        + " et fin de l'evenement pour le : " + endEvent;
            }
        }
        return str;
    }
}
