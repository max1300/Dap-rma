package fr.houseofcode.dap.server.rma.google;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;

/**
 * class utils : tools to load correctly the access to google API.
 * 
 * @author rma
 * 5 juil. 2019
 */
public final class Utils {
    /** LOG4J. */
    private static final Logger LOG = LogManager.getLogger();
    /** The default JSON_FACTORY.*/
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    //TODO RMA by Djer |Log4J| "Folder to store user credentials" serait plus claire.
    //TODO RMA by Djer |POO| Utilise "System.getProperty(\"user.home\")" y compris pour les sous-dossiers
    /** Constant TOKENS DIRECTORY PATH. */
    private static final String TOKENS_DIRECTORY_PATH = System.getProperty("user.home") + "\\Dap\\tokens"
            + File.separator;

    /**
     * Method getJsonFactory().
     * @return constant JSON_FACTORY
     */
    public static JsonFactory getJsonFactory() {
        return JSON_FACTORY;
    }

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = new ArrayList<>();

    //TODO RMA by Djer |JavaDoc| Commentaire javaDoc manquant
    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Load client secret.
     * @return client secret
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    private static GoogleClientSecrets loadClientSecret() throws IOException {
        LOG.debug("Chargement du client secret " + "avec dÃ©clenchement possible d'exceptions (IOException)");
        //TODO RMA by Djer |POO| Evite d'ajouter les autorisations à demander dans cette méthode. Deux entrée seront ajouter dans la liste à chaque appel de la méthode. Fait cette action dans le construteur, comme ca il ne serat fait qu'une seul fois.
        SCOPES.add(CalendarScopes.CALENDAR_READONLY);
        SCOPES.add(GmailScopes.GMAIL_READONLY);

        //TODO RMA by Djer |POO| "System.getProperty("user.home") + "\\Dap\\credentials.json" + File.separator)" devrait être en constante.
        //TODO RMA by Djer |POO| Utilise "System.getProperty(\"user.home\")" y compris pour les sous-dossiers
        File appClientSecretFile = new File(
                System.getProperty("user.home") + "\\Dap\\credentials.json" + File.separator);

        return GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(new FileInputStream(appClientSecretFile), StandardCharsets.UTF_8));
    }

    /**
     * Loads the credential of the given user ID from the credential store.
     * @return credential
     * @param userKey accept name of person who use the application
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    static Credential getCredentials(final String userKey) throws IOException, GeneralSecurityException {
        LOG.debug("Obtention de la credential " + "avec dÃ©clenchement possible d'exceptions (IOException "
                + "ou GeneralSecurityException");
        GoogleAuthorizationCodeFlow flow = getFlow();
        return flow.loadCredential(userKey);
    }

    /**
     * Build flow and trigger user authorization request.
     * @return a googleAuthorization flow
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    public static GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
        //TODO RMA by Djer |Log4J| Contextualise tes messages de log. "... for userKey : " + userKey.
        //TODO RMA by Djer |Log4J| Evite d'iniquer les exception potentiellement lévée. Si une exception est levé, la personne lisant la log la verra très bien ! 
        LOG.debug("Flow et trigger pour requete d'authorisation de l'utilisateur "
                + "avec dÃ©clenchement possible d'exceptions (IOException " + "ou GeneralSecurityException");
        final NetHttpTransport hTTPTRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        return new GoogleAuthorizationCodeFlow.Builder(hTTPTRANSPORT, JSON_FACTORY, loadClientSecret(), SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("online").build();
    }

}
