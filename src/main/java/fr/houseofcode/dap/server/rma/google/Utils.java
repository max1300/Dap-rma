package fr.houseofcode.dap.server.rma.google;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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

/**
 * @author adminHOC.
 *
 */
public class Utils {
    /**constant PORT. */
    private static final int PORT = 8888;

    /** the default JSON_FACTORY.*/
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    /**
     * constant TOKENS DIRECTORY PATH.
     */
    private static final String TOKENS_DIRECTORY_PATH = System.getProperty("user.home") + "\\Dap\\tokens";

    /**
     * method getJsonFactory().
     * @return constant JSON_FACTORY
     */
    public static JsonFactory getJsonFactory() {
        return JSON_FACTORY;
    }

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = new ArrayList<String>();

    /**
     * String CREDENTIALS_FILE_PATH.
     */
    //private static final String CREDENTIALS_FILE_PATH = System.getProperty("user.home") + "\\Dap\\credentials.json";

    private static GoogleClientSecrets loadClientSecret() throws IOException {
        SCOPES.add(CalendarScopes.CALENDAR_READONLY);
        SCOPES.add(GmailScopes.GMAIL_READONLY);

        //        InputStream in = Utils.class.getResourceAsStream(CREDENTIALS_FILE_PATH);

        File appClientSecretFile = new File(System.getProperty("user.home") + "\\Dap\\credentials.json");

        //        if (in == null) {
        //            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        //        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(new FileInputStream(appClientSecretFile), Charset.forName("UTF-8")));

        return clientSecrets;
    }

    /**
     * load client secret.
     * @param hTTPtRANSPORT transport.
     * @return client secret
     * @throws IOException exception
     * @throws GeneralSecurityException 
     */

    static Credential getCredentials(String userKey) throws IOException, GeneralSecurityException {
        GoogleAuthorizationCodeFlow flow = getFlow();
        return flow.loadCredential(userKey);
    }

    public static GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
        // Build flow and trigger user authorization request.
        final NetHttpTransport hTTPTRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(hTTPTRANSPORT, JSON_FACTORY,
                loadClientSecret(), SCOPES)
                        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                        .setAccessType("offline").build();

        return flow;

    }

}
