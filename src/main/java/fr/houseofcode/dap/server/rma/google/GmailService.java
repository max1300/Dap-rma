package fr.houseofcode.dap.server.rma.google;

import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GmailService {

    /**
     * MOCK : Renvoie le nombre d'email non lu dans la boite principale
     * @param userKey
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    int getNbUnreadEmail( String userKey) throws IOException, GeneralSecurityException;

    /**
     *  @param userKey type String
     * @return String containing list of labels
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    String getLabels( String userKey) throws IOException, GeneralSecurityException;
}
