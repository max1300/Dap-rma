package fr.houseofcode.dap.server.rma.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author rma.
 * 5 august. 2019
 */
@Entity
public class GoogleAccount {

    /**
     * Identifier of a Google account.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Identifier of the owner of a Google account.
     * Reference to AppUser entity
     */
    @ManyToOne
    private AppUser owner;

    /**
     * Getter to return the account's identifier.
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Getter to return the owner.
     * @return the owner
     */
    public AppUser getOwner() {
        return owner;
    }

    /**
     * Setter to modify the owner.
     * @param mOwner the owner to set
     */
    public void setOwner(final AppUser mOwner) {
        //pas compris l'histoire du retro lien
        this.owner = mOwner;
    }
}
