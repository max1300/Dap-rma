package fr.houseofcode.dap.server.rma.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author rma.
 * 5 august. 2019
 */
@Entity
public class AppUser {

    /**
     * Attribute id of database.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Attribute name of database.
     */
    private String name;



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
