package registruauto.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import registruauto.dao.VizitaDaoIntf;
import registruauto.db.MyDataSource;
import registruauto.models.Auto;
import registruauto.models.Vizita;

public class VizitaDaoImpl implements VizitaDaoIntf {

    private static final Logger LOG = Logger.getLogger(VizitaDaoImpl.class.getName());
    private MyDataSource ds = MyDataSource.getInstance();

    @Override
    public void save(Vizita vizita) throws SQLException {
        String sql = "INSERT INTO vizita VALUES (null, ?, ?, ?, ?, ?)";

        try (
                Connection conn = ds.getConnection();
                PreparedStatement stat = conn.prepareStatement(sql);) {

            stat.setDate(1, java.sql.Date.valueOf(vizita.getDataVizita()));
            stat.setString(2, vizita.getParcursVizita());
            stat.setString(3, vizita.getNumeAutoService());
            stat.setInt(4, vizita.getCostVizita());
            stat.setInt(5, vizita.getIdAuto());

            stat.executeUpdate();

        } catch (Exception ex) {
            LOG.severe(ex.toString());
            throw new SQLException(ex.getMessage());
        }
    }

    @Override
    public Vizita retrieveLastVisitId(int autoId) {
        Vizita v = null;

        String sql = "SELECT id FROM vizita WHERE id_auto= "+autoId+" ORDER BY id DESC LIMIT 1";
        try (
                Connection conn = ds.getConnection();
                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery(sql);) {

            while (rs.next()) {
                v = new Vizita(rs.getInt(1));
            }
            conn.close();
            rs.close();

        } catch (Exception e) {
            LOG.severe(e.toString());
        }
        return v;
    }

}
