package fr.houseofcode.dap.server.rma;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.houseofcode.dap.server.rma.data.AppUser;
import fr.houseofcode.dap.server.rma.data.AppUserRepository;

/**
 * @author rma.
 * 5 august. 2019
 */
@RestController
public class UserController {

    //TODO RMA by Djer |JavaDoc| Evite de décrir ce que c'est (on le voit déja grace au typage), essaye de décrire ce que cela fait (ou à quoi ca sert). "Acces to users informations" par exemple.
    /** Object allow to use interface AppUserRepository. */
    private AppUserRepository appUserRepo;

    /**
     * Constructor for UserController class.
     * @param mAppUserRepo
     */
    //TODO RMA by Djer |JavaDoc| Il manque la description du paramètre "mAppUserRepo"
    public UserController(final AppUserRepository mAppUserRepo) {
        this.appUserRepo = mAppUserRepo;
    }

    /**
     * Method to find a user.
     * @return a user from database
     */
    @GetMapping("user/all")
    public Iterable<AppUser> displayAllUsers() {
        return appUserRepo.findAll();
    }

    //TODO RMA by Djer |JavaDoc| Parler de BDD dans un Controller est maladroit (cela pourrait changer et cette JavaDoc deviendrait faux)
    /**
     * Method to create a new user in database.
     * @param name as name of new user
     */
    @GetMapping("user/add")
    public void addUser(@RequestParam final String name) {
        AppUser entity = new AppUser();
        entity.setName(name);
        appUserRepo.save(entity);
    }

}
