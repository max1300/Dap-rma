package fr.houseofcode.dap.server.rma.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

//TODO RMA by Djer |JavaDoc| Il manque la description de la classe.
public interface GmailService {

    //TODO RMA by Djer |JavaDoc| C'est faux de parler de "mock" ici, cette interface **permet** de créer un bouchon (mock) mais elle n'en est pas un.
    /**
     * MOCK : Renvoie le nombre d'email non lu dans la boite principale.
     * @param userKey
     * @throws IOException
     * @throws GeneralSecurityException
     * @return the number of unread emails
     */
    //TODO RMA by Djer |JavaDoc| Il manque la description de l'attribut "userKey".
    int getNbUnreadEmail(String userKey) throws IOException, GeneralSecurityException;

    //TODO RMA by Djer |JavaDoc| JavaDoc va automatiquement récupèrer et afficher le type du paramètre (userKey), il n'est pas utile de l'indiquer dans ta description du paramètre.
    /**
     * * MOCK : Renvoie les labels de la boite principale.
     * @param userKey type String
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     * @return String containing list of labels
     */
    String getLabels(String userKey) throws IOException, GeneralSecurityException;
}
