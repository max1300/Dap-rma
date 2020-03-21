package fr.houseofcode.dap.server.rma;


import fr.houseofcode.dap.server.rma.data.AppUser;
import fr.houseofcode.dap.server.rma.data.AppUserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rma.
 * 5 august. 2019
 */
@RestController
public class UserController {

    /**
     * Object allow to use interface AppUserRepository.
     */
    private AppUserRepository appUserRepo;

    /**
     * Constructor for UserController class.
     * @param mAppUserRepo
     */
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
