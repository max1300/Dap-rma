package fr.houseofcode.dap.server.rma;

import fr.houseofcode.dap.server.rma.google.GmailService;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class GmailServiceMock implements GmailService {

    /**
     * MOCK : Renvoie le nombre d'email non lu dans la boite principale
     * @param userKey String return the user.
     * @return number of unread email
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */

    public int getNbUnreadEmail( String userKey) throws IOException, GeneralSecurityException {
        return 12;
    }

    @Override
    public String getLabels(String userKey) throws IOException, GeneralSecurityException {
        return "bouchon, liste, label";
    }
}
