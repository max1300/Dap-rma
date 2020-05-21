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

    /**
     * Injection of AppUserRepository.
     */
    private AppUserRepository appUserRepo;

    /**
     * Constructor for UserController class.
     * @param mAppUserRepo as the respository related to entity AppUser
     */
    //TODO RMA by Djer |JavaDoc| Il manque la description du param�tre "mAppUserRepo"
    public UserController(final AppUserRepository mAppUserRepo) {
        this.appUserRepo = mAppUserRepo;
    }

    /**
     * Find all the user.
     * @return a user
     */
    @GetMapping("user/all")
    public Iterable<AppUser> displayAllUsers() {
        return appUserRepo.findAll();
    }

    //TODO RMA by Djer |JavaDoc| Parler de BDD dans un Controller est maladroit (cela pourrait changer et cette JavaDoc deviendrait faux)
    /**
     * Create a new user.
     * @param name as name of new user
     */
    @GetMapping("user/add")
    public void addUser(@RequestParam final String name) {
        AppUser entity = new AppUser();
        entity.setName(name);
        appUserRepo.save(entity);
    }

}
