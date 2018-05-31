package gob.bancomext.security;

import java.util.LinkedHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Alfredo Estrada
 */
public class SecurityManager {
    private static Log logger = LogFactory.getLog(SecurityManager.class);
    private LinkedHashMap<String,SecurityToken> tokens = new LinkedHashMap<String,SecurityToken>();
    
    private static SecurityManager instance;
    private SecurityManager(){
    }

    public static SecurityManager getInstance() {
        if(instance == null){
            instance = new SecurityManager();
        }
        return instance;
    }
    
    public synchronized SecurityToken getSecurityToken(){        
        final SecurityToken securityToken = new SecurityToken();
        tokens.put(securityToken.getTokenId(), securityToken);
        return securityToken;
    }
    
    public SecurityToken getSecurityToken(String tokenId){        
        return tokens.get(tokenId);
    }
    
    public synchronized boolean isValidToken(final String token){
        return tokens.containsKey(token);
    }

    void invalidateSecurityToken(String tokenId) {
        tokens.remove(tokenId);
    }

    public LinkedHashMap<String, SecurityToken> getTokens() {
        return tokens;
    }
    
    
}
