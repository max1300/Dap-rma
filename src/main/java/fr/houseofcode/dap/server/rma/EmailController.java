package fr.houseofcode.dap.server.rma;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.houseofcode.dap.server.rma.google.GmailService;

/**
 * @author rma.
 * 5 august. 2019
 */
@RestController
public class EmailController {

    /**
     *  Instance of Logger.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     Object GmailService.
     */
    private GmailService gmailService;

    //TODO RMA by Djer |JavaDoc| Il manque la JavaDoc de ton constructeur (description, paramètres)
    public EmailController(final GmailService mGmailService) {
        this.gmailService = mGmailService;
    }

    /**
     * Number of unread email on mailbox.
     * @return number of unread email
     * @param userKey accept name of person who use the application
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    @GetMapping(value = "/email/nbUnread")
    public Integer displayNbUnreadEmail(@RequestParam final String userKey)
            throws IOException, GeneralSecurityException {

        LOG.info("recuperation des emails non lus {}.", userKey);
        return gmailService.getNbUnreadEmail(userKey);
    }

    /**
     * Load labels of mailbox.
     * @return label from mailbox
     * @param userKey accept name of person who use the application
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    @GetMapping("/label/print")
    public String displayLabel(@RequestParam final String userKey) throws IOException, GeneralSecurityException {
        //TODO RMA by Djer |Log4J| Contextualise tes messages de log. "... for userKey : " + userKey.
        LOG.info("Recuperation des labels");
        return gmailService.getLabels(userKey);
    }

    /**
     * Setter to modify the gmailService.
     * @param mGmailService
     */
    //TODO RMA by Djer |JavaDoc| Il manque la description du paramètre "mGmailService".
    public void setGmailService(final GmailService mGmailService) {
        this.gmailService = mGmailService;
    }
}
