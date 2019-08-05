package fr.houseofcode.dap.server.rma;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rma.
 * 5 august. 2019
 */
@RestController
public class HelloController {

    /**
     * simple test to use Spring data.
     * @return salut les gens
     */
    @RequestMapping("/")
    public String index() {
        return "Salut les gens";
    }
}
