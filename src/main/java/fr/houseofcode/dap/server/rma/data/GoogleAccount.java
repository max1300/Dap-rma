package fr.houseofcode.dap.server.rma.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class GoogleAccount {
    /**
     * Attribute id of database.
     */
    @Id
    @GeneratedValue
    private Integer id;
    
    /**
     * Attribute for the owner of the account.
     */
    @ManyToOne
    private AppUser owner;

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
     * @return the owner
     */
    public AppUser getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(AppUser owner) {
        this.owner = owner;
    }
    

}
