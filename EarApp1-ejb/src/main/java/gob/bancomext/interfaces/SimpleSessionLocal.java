package gob.bancomext.interfaces;

import javax.ejb.Local;

/**
 * gob.bancomext.interfaces.SimpleSessionLocal
 * @author Alfredo Estrada
 */
@Local
public interface SimpleSessionLocal {
    String getDate();
}
