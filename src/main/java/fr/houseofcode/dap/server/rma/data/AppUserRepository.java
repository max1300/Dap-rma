package fr.houseofcode.dap.server.rma.data;

import org.springframework.data.repository.CrudRepository;

/**
 * @author rma.
 * 5 august. 2019
 *
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    /**
     * Retrieve a User by is name.
     * @param name
     * @return if exist in database, the user(s) with the name value
     */
    AppUser findByName(String name);

}
