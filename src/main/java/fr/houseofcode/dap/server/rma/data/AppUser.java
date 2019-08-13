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
     * Attribute id of database.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Attribute name of database.
     */
    private String name;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<GoogleAccount> googleAccounts;

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

    /**
     * @return the googleAccounts
     */
    public List<GoogleAccount> getGoogleAccounts() {
        return googleAccounts;
    }

    /**
     * @param googleAccounts the googleAccounts to set
     */
    public void setGoogleAccounts(List<GoogleAccount> googleAccounts) {
        this.googleAccounts = googleAccounts;
    }
    
    public void adGoogleAccount(GoogleAccount account){

        account.setOwner(this);

        this.getGoogleAccounts().add(account);

    }

}
