package main.system;

import javafx.collections.ObservableList;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import javax.naming.*;
import javax.naming.directory.*;

import static main.system.IUsers.*;

public class ActiveDirectory{
	// Logger
	private static final Logger LOG = Logger.getLogger(ActiveDirectory.class.getName());

    //required private variables   
    private Properties properties;
    private DirContext dirContext;
    private SearchControls searchCtls;
	private String[] returnAttributes = { CN, TITLE, DEPARTMENT, COMPANY, INFO, DISTINGUISHED_NAME };
    private String domainBase;
    private String baseFilter = "(&((&(objectCategory=Person)(objectClass=User)))";
    private ModificationItem[] mods = new ModificationItem[4];
    private ObservableList<User> adUsersList;
    private Settings settings = new Settings();


    /**
     * constructor with parameter for initializing a LDAP context
     * 
     * @param domainController a {@link String} object - domain controller name for LDAP connection
     */
    public ActiveDirectory(String domainController) throws IOException {
        properties = new Properties();        

        properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        properties.put(Context.PROVIDER_URL, "LDAP://" + domainController);
        properties.put(Context.SECURITY_PRINCIPAL, settings.getLogin());
        properties.put(Context.SECURITY_CREDENTIALS, settings.getPassword());
        properties.put(Context.REFERRAL, "follow");

        //initializing active directory LDAP connection
        try {
			dirContext = new InitialDirContext(properties);
		} catch (AuthenticationException e){
            LOG.severe(e.getMessage());
        } catch (NamingException e) {
			LOG.severe(e.getMessage());
		}

        //default domain base for search
        domainBase = getDomainBase(domainController);
        
        //initializing search controls
        searchCtls = new SearchControls();
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchCtls.setReturningAttributes(returnAttributes);

    }
    
    /**
     * search the Active directory by username/email id for given search base
     * 
     * @param searchValue a {@link String} object - search value used for AD search for eg. username or email
     * @param searchBy a {@link String} object - scope of search by username or by email id
     * @param searchBase a {@link String} object - search base value for scope tree
     * @return search result a {@link NamingEnumeration} object - active directory search result
     * @throws NamingException
     */
    public NamingEnumeration<SearchResult> searchUser(String searchValue, String searchBy, String searchBase) throws NamingException {
    	String filter = getFilter(searchValue, searchBy);    	
    	String base = (null == searchBase) ? domainBase : getDomainBase(searchBase);
    	
		return this.dirContext.search(base, filter, this.searchCtls);
    }

    public DirContext getDirContext() {
        return dirContext;
    }


    public void replaceAttributes(User selectedUser){

        try {
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(TITLE, selectedUser.getPosition()));
            mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(DEPARTMENT, selectedUser.getDepartment()));
            mods[2] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(COMPANY, selectedUser.getCompany()));
            mods[3] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(INFO, selectedUser.getBirthday()));

            dirContext.modifyAttributes(selectedUser.getDistinguishedName(), mods);

        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

    /**
     * closes the LDAP connection with Domain controller
     */
    public void closeLdapConnection(){
        try {
            if(dirContext != null)
                dirContext.close();
        }
        catch (NamingException e) {
        	LOG.severe(e.getMessage());            
        }
    }
    
    /**
     * active directory filter string value
     * 
     * @param searchValue a {@link String} object - search value of username/email id for active directory
     * @param searchBy a {@link String} object - scope of search by username or email id
     * @return a {@link String} object - filter string
     */
    private String getFilter(String searchValue, String searchBy) {
    	String filter = this.baseFilter;    	
    	if(searchBy.equals(CN)) {
    		filter += "(cn=" + searchValue + "))";
    	} else if(searchBy.equals("username")) {
    		filter += "(samaccountname=" + searchValue + "))";
    	}
		return filter;
	}
    
    /**
     * creating a domain base value from domain controller name
     * 
     * @param base a {@link String} object - name of the domain controller
     * @return a {@link String} object - base name
     */
	private static String getDomainBase(String base) {
		char[] namePair = base.toUpperCase().toCharArray();
		String dn = "DC=";
		for (int i = 0; i < namePair.length; i++) {
			if (namePair[i] == '.') {
				dn += ",DC=" + namePair[++i];
			} else {
				dn += namePair[i];
			}
		}
		return dn;
	}


}
