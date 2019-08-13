package fr.houseofcode.dap.server.rma.data;

import org.springframework.data.repository.CrudRepository;

/**
 * @author rma.
 * 5 august. 2019
 *
 */
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
    public AppUser findByName(String name);

}
