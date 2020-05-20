package fr.houseofcode.dap.server.rma.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

/**
 * class GmailService.
 * @author rma
 * 5 juil. 2019
 */
//TODO RMA by Djer |POO| Tu peux reprendre toutes les remarques du "ClaendarService".
@Service
public class GmailServiceImpl implements GmailService {

    /** LOG4J. */
    private static final Logger LOG = LogManager.getLogger();

    /** Internal ApplicationName. */
    private static String applicationName = "Gmail API Java Quickstart";

    /**
     * Getter for constant ApplicationName.
     * @return access to constant ApplicationName.
     */
    public static String getApplicationName() {
        return applicationName;
    }

    /**
     * Secure connection to GmailService.
     * @return a instance of Gmail
     * @param userKey accept name of person who use the application
     * @throws GeneralSecurityException exception
     * @throws IOException exception
     */
    private Gmail getGmailService(final String userKey) throws GeneralSecurityException, IOException {
        LOG.debug("connexion au service Gmail " + "avec d√©clenchement possible d'exceptions (IOException "
                + "ou GeneralSecurityException");

        //TODO RMA by Djer |Log4J| Cette log est fausse !
        LOG.error("An exception occurred!, in class {}. in method getGmailService.", GmailService.class.getName());
        NetHttpTransport hTTPTRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Gmail.Builder(hTTPTRANSPORT, Utils.getJsonFactory(), Utils.getCredentials(userKey))
                .setApplicationName(GmailServiceImpl.getApplicationName()).build();
    }

    /**
     *  @param userKey type String
     * @return String containing list of labels
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    @Override
    @GetMapping("/labels")
    public String getLabels(final String userKey) throws IOException, GeneralSecurityException {
        LOG.debug("recuperation des labels avec d√©clenchement possible d'exceptions (IOException "
                + "ou GeneralSecurityException");

        StringBuilder builder = new StringBuilder();
        ListLabelsResponse listResponse = getGmailService(userKey).users().labels().list("me").execute();
        List<Label> labels = listResponse.getLabels();

        if (labels.isEmpty()) {
            builder.append("No Labels");
        } else {
            for (Label label : labels) {
                builder.append(label.getName());
                //TODO RMA by Djer |MVC| Ne formate pas les messages sur le serveur d'une API REST. Renvoie les donnÈes et laisse le client (ou thymeLeaf) effectuer le formatage. Ici tu peux renvoyer une List<String> (chacun Ètant le "name" du Label), ou bien une List<Label>.
                builder.append(System.getProperty("line.separator"));
            }
        }

        //TODO RMA by Djer |POO| Nom de variable pas trs claire. Tu peux t'en passer ("return builder.toString()") ou nommer "allLabels" par exemple
        String str = builder.toString();
        LOG.info("nombre de labels gmail : {}.", labels.size());
        //TODO RMA by Djer |Log4J| Cette log est fausse ! Il n'y a pas eut d'exception ‡ cette endroit. 
        LOG.error("An exception occurred!, in class {}. in method getlabels", GmailService.class.getName());
        return str;
    }

    /**
     * MOCK : Renvoie le nombre d'email non lu dans la boite principale.
     * @param userKey String return the user.
     * @return number of unread email
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    @Override
    public int getNbUnreadEmail(final String userKey) throws IOException, GeneralSecurityException {
        //TODO RMA by Djer |Log4J| Contextualise tes messages de log. "... for userKey : " + userKey.
        LOG.info("Searching number of unread email in mailbox");
        ListMessagesResponse response = getGmailService(userKey).users().messages().list("me")
                .setQ("is:unread in:inbox").execute();

        List<Message> messages = new ArrayList<>();

        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());

            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = getGmailService(userKey).users().messages().list("me").setQ("is:unread")
                        .setPageToken(pageToken).execute();
            } else {
                break;
            }
        }
        return messages.size();
    }
}
