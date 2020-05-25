package registruauto.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Logger;
import registruauto.dao.UtilDaoIntf;
import registruauto.db.MyDataSource;

public class UtilDaoImpl implements UtilDaoIntf {

    private static final Logger LOG = Logger.getLogger(AutoDaoImpl.class.getName());
    private MyDataSource ds = MyDataSource.getInstance();

    @Override
    public void setLastVisitDate() throws SQLException {
        String sql = "UPDATE util SET app_lastvisit_date=?";
        try (
                Connection conn = ds.getConnection();
                PreparedStatement pstat = conn.prepareStatement(sql);) {
            //setam data de azi
            pstat.setDate(1, java.sql.Date.valueOf(LocalDate.now()));

            pstat.executeUpdate();
        } catch (Exception e) {

            LOG.severe(e.toString());
            throw new SQLException(e.getMessage());

        }

    }

    @Override
    public LocalDate getLastVisitDate() throws SQLException {
        LocalDate date = null;
        String sql = "SELECT app_lastvisit_date FROM util";

        try (
                Connection conn = ds.getConnection();
                PreparedStatement stat = conn.prepareStatement(sql);
                ResultSet rs = stat.executeQuery(sql);) {

            if (rs.next()) {
                date = rs.getDate(1).toLocalDate();
            }

        } catch (Exception ex) {
            LOG.severe(ex.toString());
            throw new SQLException(ex.getMessage());
        }
        return date;
    }


}
