package fr.houseofcode.dap.server.rma.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GmailService {

    /**
     * MOCK : Renvoie le nombre d'email non lu dans la boite principale.
     * @param userKey
     * @throws IOException
     * @throws GeneralSecurityException
     * @return the number of unread emails
     */
    int getNbUnreadEmail(String userKey)throws IOException, GeneralSecurityException;

    /**
     * @param userKey type String
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     * @return String containing list of labels
     */
    String getLabels(String userKey)throws IOException, GeneralSecurityException;
}
