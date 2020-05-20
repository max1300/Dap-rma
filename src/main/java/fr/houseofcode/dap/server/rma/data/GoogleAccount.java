package fr.houseofcode.dap.server.rma.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class GoogleAccount {
    //TODO RMA by Djer |JavaDoc| Il n'est pas utile de préciser que c'est un "attribute"
    /** Attribute id of database. */
    @Id
    @GeneratedValue
    private Integer id;

    /** Attribute for the owner of the account.  */
    @ManyToOne
    private AppUser owner;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    //TODO RMA by Djer |JPA| Evite de laisser le setter, comme c'est un @GeneratedValue, un developpeur pourrait faire des bétise en definissant lui même un ID.
    /**
     * @param mId the id to set
     */
    public void setId(final Integer mId) {
        this.id = mId;
    }

    /**
     * @return the owner
     */
    public AppUser getOwner() {
        return owner;
    }

    /**
     * @param mOwner the owner to set
     */
    public void setOwner(final AppUser mOwner) {
        this.owner = mOwner;
        //TODO RMA by Djer |JPA| Tu devrais faire le "retro-lien" de "owner -> this", si ce lien n'existe pas déja, pour éviter des "bug de synchro" des entitées.
    }
}
