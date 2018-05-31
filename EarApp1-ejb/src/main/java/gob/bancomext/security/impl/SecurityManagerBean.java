package gob.bancomext.security.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Alfredo Estrada
 */
@Startup
@Singleton( name        = "SecurityManagerBean",
            description = "SecurityManagerBean", 
            mappedName  = "SecurityManagerBean")
public class SecurityManagerBean {

    @PostConstruct
    void init() {
        System.out.println("########>> SecurityManagerBean");
    }
    public String getDate(){
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(new Date());
    }
}
