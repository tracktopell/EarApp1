package gob.bancomext.security;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 * @author Alfredo Estrada
 */
public class SecurityToken implements Serializable{
    
    private String tokenId;
    private String tokenFieldId;
    private String userFieldId;
    private String passFieldId;
    private long   creationTime;

    SecurityToken() {
        this.creationTime  = System.currentTimeMillis();
        this.tokenId       = encodeSHA256_HEX(String.valueOf(this.creationTime));
        this.tokenFieldId  = encodeSHA256_HEX("_t"+this.tokenId);
        this.userFieldId   = encodeSHA256_HEX("_u"+this.tokenId);
        this.passFieldId   = encodeSHA256_HEX("_p"+this.tokenId);        
    }
    
    /**
     * @return the tokenId
     */
    public String getTokenId() {
        return tokenId;
    }

    /**
     * @return the userFieldId
     */
    public String getUserFieldId() {
        return userFieldId;
    }
    
    /**
     * @return the tokenFieldId
     */
    public String getTokenFieldId() {
        return tokenFieldId;
    }

    /**
     * @return the passFieldId
     */
    public String getPassFieldId() {
        return passFieldId;
    }

    /**
     * @return the creationTime
     */
    public long getCreationTime() {
        return creationTime;
    }

    public static String encodeSHA256_HEX(String msg){
        byte[] digested=null;
        String encoded=null;
        MessageDigest digest = null;
        try {
            digest   = MessageDigest.getInstance("SHA-256");
            digested = digest.digest(msg.getBytes(StandardCharsets.UTF_8));
            StringBuilder sbEncodded = new StringBuilder();
            for(byte b:digested){
                final String hs = Integer.toHexString(0xFF & b);
                if(hs.length()==1){
                    sbEncodded.append('0');
                }
                sbEncodded.append(hs);
            }                        
            encoded  = sbEncodded.toString().toUpperCase();
        }catch(NoSuchAlgorithmException nsa){
            
        }
        return encoded;
    }
    
    public static String encodeSHA256_base64(String msg){
        byte[] digested=null;
        String encoded=null;
        MessageDigest digest = null;
        try {
            digest   = MessageDigest.getInstance("SHA-256");
            digested = digest.digest(msg.getBytes(StandardCharsets.UTF_8));
            encoded  = Base64.getEncoder().encodeToString(digested);
        }catch(NoSuchAlgorithmException nsa){
            
        }
        return encoded;
    }    

    @Override
    public String toString() {
        return new StringBuilder("SecurityToken{")
                            .append("tokenId")      .append("=").append(tokenId)
                .append(",").append("tokenFieldId") .append("=").append(tokenFieldId)
                .append(",").append("userFieldId")  .append("=").append(userFieldId)
                .append(",").append("passFieldId")  .append("=").append(passFieldId)
                .append(",").append("creationTime") .append("=").append(creationTime)
                .append("}").toString();
    }
    
}
