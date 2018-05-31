package gob.bancomext.security.impl;

import gob.bancomext.interfaces.SimpleSessionLocal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.Stateless;

/**
 * gob.bancomext.SimpleSessionBean
 * @author Alfredo Estrada
 */
@Stateless( name        = "SimpleSessionBean",
            description = "SimpleSessionBean", 
            mappedName  = "SimpleSessionBean")
public class SimpleSessionBean implements SimpleSessionLocal{

    @Override
    public String getDate() {
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(new Date());
    }   
}
