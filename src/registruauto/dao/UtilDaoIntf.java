package registruauto.dao;

import java.sql.SQLException;
import java.time.LocalDate;

public interface UtilDaoIntf {
    void setLastVisitDate() throws SQLException;
    public LocalDate getLastVisitDate() throws SQLException;

}
