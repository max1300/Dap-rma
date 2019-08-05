package fr.houseofcode.dap.server.rma.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

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
public final class GmailService {

    /**
     * @return access to constant LOG.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * @return the internal APPLICATION_NAME.
     */
    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";

    /**
     * @return access to constant APPLICATION_NAME.
     */
    public static String getApplicationName() {
        return APPLICATION_NAME;
    }

    /**
     * secure connection to GmailService.
     * @return a instance of Gmail
     * @param userKey accept name of person who use the application
     * @throws GeneralSecurityException exception
     * @throws IOException exception
     */
    private Gmail getGmailService(final String userKey)
            throws GeneralSecurityException, IOException {
        final NetHttpTransport hTTPTRANSPORT =
                GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(hTTPTRANSPORT,
                Utils.getJsonFactory(), Utils.getCredentials(userKey))
                .setApplicationName(GmailService.getApplicationName()).build();
        return service;
    }

    // ========================METHODE LABEL=========================
    /**
     *  @param userKey type String
     * @return String containing list of labels
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    @RequestMapping("/labels")
    public String getLabels(final String userKey)
            throws IOException, GeneralSecurityException {
        LOG.debug(
                "recuperation des labels avec déclenchement "
                + "possible d'exceptions (IOException "
                + "ou GeneralSecurityException");

        String str = "";
        ListLabelsResponse listResponse = getGmailService(userKey)
                .users().labels().list("me").execute();
        List<Label> labels = listResponse.getLabels();

        if (labels.isEmpty()) {
            str = "No Labels";

        } else {

            for (Label label : labels) {
                str += label.getName() + "\n";

            }
        }

        LOG.info("nombre de labels gmail : " + labels.size());
        return str;
    }

    /**
     * @param userKey String return the user.
     * @return number of unread email
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */

    public int getNbUnreadEmail(final String userKey)
            throws IOException, GeneralSecurityException {

        ListMessagesResponse response = getGmailService(userKey)
                .users().messages().list("me").setQ("is:unread")
                .execute();

        List<Message> messages = new ArrayList<Message>();
        while (response.getMessages() != null) {

            messages.addAll(response.getMessages());

            if (response.getNextPageToken() != null) {

                String pageToken = response.getNextPageToken();
                response = getGmailService(userKey)
                        .users().messages().list("me").setQ("is:unread")
                        .setPageToken(pageToken).execute();

            } else {
                break;
            }
        }

        int number = messages.size();

        return number;

    }

}
