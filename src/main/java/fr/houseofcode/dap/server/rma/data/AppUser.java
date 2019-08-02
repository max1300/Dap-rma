/**
 * 
 */
package fr.houseofcode.dap.server.rma.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author houseofcode
 *
 */
@Entity
public class AppUser {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    //    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appUser")
    //    private List<GoogleAccount> googleAccounts;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
