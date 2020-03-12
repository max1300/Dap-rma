package fr.houseofcode.dap.server.rma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fr.houseofcode.dap.server.rma.data.AppUser;
import fr.houseofcode.dap.server.rma.data.AppUserRepository;

/**
 * @author rma.
 * 5 august. 2019
 */
@RestController
public class UserController {

    /**
     * object allow to use interface AppUserRepository.
     */
    private AppUserRepository appUserRepo;

    public UserController(AppUserRepository appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    /**
     * method to find a user.
     * @return a user from database
     */
    @GetMapping("user/all")
    public Iterable<AppUser> displayAllUsers() {
        return appUserRepo.findAll();
    }

    /**
     * method to create a new user in database.
     * @param name as name of new user
     */
    @GetMapping("user/add")
    public void addUser(@RequestParam  String name) {
        AppUser entity = new AppUser();
        entity.setName(name);
        appUserRepo.save(entity);
    }

}
