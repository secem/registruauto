package registruauto.gui.archive;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import registruauto.gui.archive.NotificareDaoIntf;
import registruauto.dao.impl.VizitaDaoImpl;
import registruauto.db.MyDataSource;

public class NotificareDaoImpl implements NotificareDaoIntf {
    
    private static final Logger LOG = Logger.getLogger(VizitaDaoImpl.class.getName());
    private MyDataSource ds = MyDataSource.getInstance();
    
    @Override
    public void save(Notificare notificare) throws SQLException {
        String sql = "INSERT INTO notificare VALUES (?, ?, ?, ?, ?)";
        try (
                Connection conn = ds.getConnection();
                PreparedStatement stat = conn.prepareStatement(sql);) {
            
            stat.setInt(1, notificare.getId());
            stat.setBoolean(2, notificare.isActive());
            stat.setString(3, notificare.getDesc());
            stat.setDate(1, java.sql.Date.valueOf(notificare.getDate()));
            stat.setInt(5, notificare.getIdAuto());
            
            stat.executeUpdate();
            
        } catch (Exception ex) {
            LOG.severe(ex.toString());
            throw new SQLException(ex.getMessage());
        }
        
    }
    
    @Override
    public Notificare retrieve(int id) throws SQLException {
        
        Notificare n = null;
        
        String sql = "SELECT id, active, date, id_auto FROM notificare where id="+id;
        try (
                Connection conn = ds.getConnection();
                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery(sql);) {
            
            while (rs.next()) {
                n = new Notificare(rs.getInt(1), rs.getBoolean(2), rs.getDate(3).toLocalDate(), rs.getInt(4));
            }
            conn.close();
            rs.close();
            
        } catch (Exception e) {
            LOG.severe(e.toString());
        }
        return n;
    }
    
}
