package fr.houseofcode.dap.server.rma;

import java.io.IOException;
import java.security.GeneralSecurityException;

import fr.houseofcode.dap.server.rma.google.GmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import fr.houseofcode.dap.server.rma.google.GmailServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author rma.
 * 5 august. 2019
 */
@Controller
public class EmailController {
    /** Log4J. */
    private static final Logger LOG = LogManager.getLogger();

    /** Object GmailService. */
    private GmailService gmailService;

    public EmailController(final GmailServiceImpl service){
        this.gmailService = service;
    }

    /**
     * number of unread email on mailbox.
     * @return number of unread email
     * @param userKey accept name of person who use the application
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    @GetMapping(value = "/email/nbUnread")
    public int displayNbUnreadEmail(@RequestParam String userKey, Model model)
            throws IOException, GeneralSecurityException {

        LOG.info("recuperation des emails non lus {}.", userKey);
        String user = userKey;
        int nbemail = gmailService.getNbUnreadEmail(userKey);
        model.addAttribute("emails", nbemail);
        model.addAttribute("user", user);
        LOG.info("nombre de message {}.",  gmailService.getNbUnreadEmail(userKey));

//        return "Gmail";
        return nbemail;
    }

    /**
     * @return label from mailbox.
     * @param userKey accept name of person who use the application
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    @GetMapping("/label/print")
    public String displayLabel(@RequestParam String userKey) throws IOException, GeneralSecurityException {
        LOG.info("Recuperation des labels");
        return gmailService.getLabels(userKey);
    }


    /**
     * Setter of attribute gmailService
     * @param gmailService
     */
    public void setGmailService(GmailService gmailService) {
        this.gmailService = gmailService;
    }
}
