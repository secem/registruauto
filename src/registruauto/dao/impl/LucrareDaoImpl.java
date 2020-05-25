package registruauto.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;
import registruauto.dao.LucrareDaoIntf;
import registruauto.db.MyDataSource;
import registruauto.models.Lucrare;

public class LucrareDaoImpl implements LucrareDaoIntf {

    private static final Logger LOG = Logger.getLogger(VizitaDaoImpl.class.getName());
    private MyDataSource ds = MyDataSource.getInstance();

    @Override
    public void adauga(Lucrare lucrare) throws SQLException {
        String sql = "INSERT INTO lucrare VALUES (null, ?, ?, ?)";

        try (
                Connection conn = ds.getConnection();
                PreparedStatement stat = conn.prepareStatement(sql);) {
            stat.setString(1, lucrare.getLucrare());
            stat.setInt(2, lucrare.getCostLucrare());
            stat.setInt(3, lucrare.getIdVizita());

            stat.executeUpdate();

        } catch (Exception ex) {
            LOG.severe(ex.toString());
            throw new SQLException(ex.getMessage());
        }
    }

    @Override
    public void retrieve(Lucrare lucrare) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifica(Lucrare lucrare) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
