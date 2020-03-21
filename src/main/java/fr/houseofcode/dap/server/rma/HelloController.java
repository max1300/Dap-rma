package fr.houseofcode.dap.server.rma;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rma.
 * 5 august. 2019
 */
@RestController
public class HelloController {

    /**
     * Simple test to use Spring data.
     * @return salut les gens
     */
    @GetMapping("/")
    public String index() {
        return "Salut les gens";
    }
}
