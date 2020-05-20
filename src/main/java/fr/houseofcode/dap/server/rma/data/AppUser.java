package fr.houseofcode.dap.server.rma.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author rma.
 * 5 august. 2019
 */
@Entity
public class AppUser {

    /** Attribute id of database. */
    @Id
    @GeneratedValue
    private Integer id;

    /** Attribute name of database. */
    private String name;

    /** List of googleAccount Entity. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<GoogleAccount> googleAccounts;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param mId the id to set
     */
    public void setId(final Integer mId) {
        this.id = mId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param mName the name to set
     */
    public void setName(final String mName) {
        this.name = mName;
    }

    /**
     * @return the googleAccounts
     */
    public List<GoogleAccount> getGoogleAccounts() {
        return googleAccounts;
    }

    /**
     * Mehtod to modify a googleAccount.
     * @param mGoogleAccounts the googleAccounts to set.
     */
    public void setGoogleAccounts(final List<GoogleAccount> mGoogleAccounts) {
        this.googleAccounts = mGoogleAccounts;
    }

    /**
     * Method to add a googleAccount.
     * @param account
     */
    //TODO RMA by Djer |JavaDoc| Il manque la description du paramètre "account"
    public void adGoogleAccount(final GoogleAccount account) {
        account.setOwner(this);
        this.getGoogleAccounts().add(account);
    }

}
