package registruauto.dao;

import java.sql.SQLException;
import registruauto.models.Lucrare;

public interface LucrareDaoIntf {
    
    void adauga(Lucrare lucrare) throws SQLException;
    void retrieve(Lucrare lucrare) throws SQLException;
    void modifica(Lucrare lucrare) throws SQLException;
    
    
}
