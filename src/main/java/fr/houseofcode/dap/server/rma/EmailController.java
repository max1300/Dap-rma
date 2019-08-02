/**
 * 
 */
package fr.houseofcode.dap.server.rma;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.houseofcode.dap.server.rma.google.GmailService;

/**
 * @author houseofcode
 *
 */
@RestController
public class EmailController {

    /**
     * attribut.
     */
    @Autowired
    private GmailService gmailService;

    /**
     * nbemail.
     * @return nbemail
     * @throws IOException
     * @throws GeneralSecurityException
     */
    @RequestMapping("/email/nbUnread")
    public Integer displayNbUnreadEmail(@RequestParam final String userKey)
            throws IOException, GeneralSecurityException {

        return gmailService.getNbUnreadEmail(userKey);

    }

    @RequestMapping("/label/print")
    public String displayLabel(@RequestParam final String userKey) throws IOException, GeneralSecurityException {
        return gmailService.getLabels(userKey);

    }

}
