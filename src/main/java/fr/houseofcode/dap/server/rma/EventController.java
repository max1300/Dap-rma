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

import fr.houseofcode.dap.server.rma.google.CalendarService;

/**
 * @author houseofcode
 *
 */
@RestController
public class EventController {

    @Autowired
    private CalendarService calendarService;

    @RequestMapping("/event/next")
    public String displayNextEvent(@RequestParam final String userKey) throws IOException, GeneralSecurityException {

        return calendarService.getNextEvent(userKey);
    }

}
