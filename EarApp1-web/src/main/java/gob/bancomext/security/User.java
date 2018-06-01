package gob.bancomext.security;

import java.io.Serializable;

/**
 *
 * @author Alfredo Estrada
 */
public class User implements Serializable{
    private String user;
    private String remoteHost;
    private String email;
    private String name;
    private String userId;
    private long loggedInTime;

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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public String toString() {    
        return new StringBuilder("User{")
                .append("user=").append(user).append(" ,")
                .append("remoteHost=").append(remoteHost).append(" ,")
                .append("email=").append(email).append(" ,")
                .append("name=").append(name).append(" ,")
                .append("userId=").append(userId).append(" ,")
                .append("loggedInTime=").append(loggedInTime)
            .append("}").
            toString();
    }

    
}
