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
@Service
public  class GmailServiceImpl implements GmailService {

    /**
     * Instance of Logger.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Internal ApplicationName.
     */
    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";

    /**
     * Getter to return the name of the API.
     * @return access to constant ApplicationName.
     */
    public static String getApplicationName() {
        return APPLICATION_NAME;
    }

    /**
     * Secure connection to GmailService.
     * @return a instance of Gmail
     * @param userKey accept name of person who use the application
     * @throws GeneralSecurityException exception
     * @throws IOException exception
     */
    private Gmail getGmailService(final String userKey)
            throws GeneralSecurityException, IOException {
        LOG.debug("connexion au service Gmail "
                + "avec déclenchement possible d'exceptions (IOException "
                + "ou GeneralSecurityException");

        LOG.error("An exception occurred!, in class {}. in method getGmailService.",
                GmailService.class.getName());
         NetHttpTransport hTTPTRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Gmail.Builder(hTTPTRANSPORT, Utils.getJsonFactory(), Utils.getCredentials(userKey))
                .setApplicationName(GmailServiceImpl.getApplicationName())
                .build();
    }


    /**
     *  @param userKey type String
     * @return String containing list of labels
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    @GetMapping("/labels")
    public String getLabels(final String userKey) throws IOException, GeneralSecurityException {
        LOG.debug("recuperation des labels avec déclenchement possible d'exceptions (IOException "
                    + "ou GeneralSecurityException");

        StringBuilder builder = new StringBuilder();
        ListLabelsResponse listResponse = getGmailService(userKey).users().labels().list("me").execute();
        List<Label> labels = listResponse.getLabels();

        if (labels.isEmpty()) {
            builder.append("No Labels");
        } else {
            for (Label label : labels) {
                builder.append(label.getName());
                builder.append(System.getProperty("line.separator"));
            }
        }

        String str = builder.toString();
        LOG.info("nombre de labels gmail : {}.", labels.size());
        LOG.error("An exception occurred!, in class {}. in method getlabels",
                GmailService.class.getName());
        return str;
    }

    /**
     * MOCK : Renvoie le nombre d'email non lu dans la boite principale.
     * @param userKey String return the user.
     * @return number of unread email
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    public int getNbUnreadEmail(final String userKey) throws IOException, GeneralSecurityException {
        LOG.info("Searching number of unread email in mailbox");
        ListMessagesResponse response = getGmailService(userKey)
                .users().messages().list("me").setQ("is:unread in:inbox").execute();

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
