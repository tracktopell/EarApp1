package gob.bancomext.security;

import java.io.Serializable;

/**
 *
 * @author Alfredo Estrada
 */
public class User implements Serializable{
    private String user;
    private String password;
    private String remoteHost;
    private long loggedInTime;

    public User(String user, String password, String remoteHost, long loggedInTime) {
        this.user = user;
        this.password = password;
        this.remoteHost = remoteHost;
        this.loggedInTime = loggedInTime;
    }

    public User() {
    }
        
    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the remoteHost
     */
    public String getRemoteHost() {
        return remoteHost;
    }

    /**
     * @param remoteHost the remoteHost to set
     */
    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    /**
     * @return the loggedInTime
     */
    public long getLoggedInTime() {
        return loggedInTime;
    }

    /**
     * @param loggedInTime the loggedInTime to set
     */
    public void setLoggedInTime(long loggedInTime) {
        this.loggedInTime = loggedInTime;
    }

}
