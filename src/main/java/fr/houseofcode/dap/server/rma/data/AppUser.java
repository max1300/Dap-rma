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

    /**
     * Identifier of a User.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Name of a User.
     */
    private String name;


    /**
     * List of googleAccount Entity.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<GoogleAccount> googleAccounts;

    /**
     * Getter to return the User's identifier.
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter to modify the User's identifier.
     * @param mId the id to set
     */
    public void setId(final Integer mId) {
        this.id = mId;
    }

    /**
     * Getter to return the User's name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter to modify the User's name.
     * @param mName the name to set
     */
    public void setName(final String mName) {
        this.name = mName;
    }

    /**
     * Getter to return the list of the Google accounts.
     * @return the googleAccounts
     */
    public List<GoogleAccount> getGoogleAccounts() {
        return googleAccounts;
    }

    /**
     * Setter to modify the list of Google accounts.
     * @param mGoogleAccounts the googleAccounts to set.
     */
    public void setGoogleAccounts(final List<GoogleAccount> mGoogleAccounts) {
        this.googleAccounts = mGoogleAccounts;
    }

    /**
     * Adding a Google account in the list of Google accounts.
     * @param account as the account to add
     */
    public void adGoogleAccount(final GoogleAccount account) {
        account.setOwner(this);
        this.getGoogleAccounts().add(account);
    }

}
