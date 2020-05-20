package fr.houseofcode.dap.server.rma.google;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author rma
 * 5 juil. 2019
 * class utils :
 * tools to load correctly the access
 * to google API
 */
public final class Utils {

    /**
     * Instance of Logger.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Default JSON_FACTORY.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Default TOKENS DIRECTORY PATH.
     */
    private static final String TOKENS_DIRECTORY_PATH = System.getProperty("user.home")
                                                            + "\\Dap\\tokens" + File.separator;
    /**
     * Getter to return the JSON_FACTORY.
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

    /**
     * Constructor of class Utils
     */
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
        LOG.debug("Chargement du client secret "
                + "avec déclenchement possible d'exceptions (IOException)");
        SCOPES.add(CalendarScopes.CALENDAR_READONLY);
        SCOPES.add(GmailScopes.GMAIL_READONLY);

        File appClientSecretFile = new File(System.getProperty("user.home")
                + "\\Dap\\credentials.json" + File.separator);

       return GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(new FileInputStream(
                      appClientSecretFile), StandardCharsets.UTF_8));
    }

    /**
     * Loads the credential of the given user ID from the credential store.
     * @return credential
     * @param userKey accept name of person who use the application
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    static Credential getCredentials(final String userKey) throws IOException, GeneralSecurityException {
        LOG.debug("Obtention de la credential for userKey : " + userKey);
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
        LOG.debug("Flow et trigger pour requete d'authorisation de l'utilisateur");
        final NetHttpTransport hTTPTRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        return new GoogleAuthorizationCodeFlow.Builder(hTTPTRANSPORT, JSON_FACTORY, loadClientSecret(), SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("online").build();
    }
}
