package registruauto.dao;
import java.sql.SQLException;
import registruauto.models.Auto;
import registruauto.models.Vizita;

public interface VizitaDaoIntf {
    
    void save(Vizita vizita) throws SQLException;
    Vizita retrieveLastVisitId(int autoId) throws SQLException;
    
    
}
