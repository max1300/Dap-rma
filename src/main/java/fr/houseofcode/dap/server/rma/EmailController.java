package fr.houseofcode.dap.server.rma;

import java.io.IOException;
import java.security.GeneralSecurityException;

import fr.houseofcode.dap.server.rma.google.GmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rma.
 * 5 august. 2019
 */
@RestController
public class EmailController {
    /** Log4J. */
    private static final Logger LOG = LogManager.getLogger();

    /** Object GmailService. */
    private GmailService gmailService;

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
        LOG.info("Recuperation des labels");
        return gmailService.getLabels(userKey);
    }


    /**
     * Setter of attribute gmailService.
     * @param mGmailService
     */
    public void setGmailService(final GmailService mGmailService) {
        this.gmailService = mGmailService;
    }
}
