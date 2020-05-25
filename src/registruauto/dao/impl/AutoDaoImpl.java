package registruauto.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import registruauto.dao.AutoDaoIntf;
import registruauto.db.MyDataSource;
import registruauto.gui.util.NotificationType;
import static registruauto.gui.util.NotificationType.ASIGURARE;
import static registruauto.gui.util.NotificationType.DESERVIRE;
import static registruauto.gui.util.NotificationType.TESTARE;
import registruauto.models.Auto;

public class AutoDaoImpl implements AutoDaoIntf {

    private static final Logger LOG = Logger.getLogger(AutoDaoImpl.class.getName());

    private MyDataSource ds = MyDataSource.getInstance();

    @Override
    public void save(Auto auto) throws SQLException {
        String sql = "INSERT INTO auto VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, null, null, null)";
        //Prepared Statement

        try (
                Connection conn = ds.getConnection();
                PreparedStatement stat = conn.prepareStatement(sql);) {
            stat.setString(1, auto.getModel());
            stat.setInt(2, auto.getAnulProd());
            stat.setString(3, auto.getParcurs());
            stat.setString(4, auto.getCuloare());
            stat.setString(5, auto.getNrInmatr());
            stat.setString(6, auto.getVin());
            stat.setBoolean(7, auto.isActive());
            stat.setString(8, auto.getLogo());

            stat.executeUpdate();

        } catch (Exception ex) {
            LOG.severe(ex.toString());
            throw new SQLException(ex.getMessage());
        }

    }

    @Override
    public void remove(Auto auto) throws SQLException {
        String sql = "UPDATE auto SET active=0 WHERE id=?";

        try (
                Connection conn = ds.getConnection();
                PreparedStatement stat = conn.prepareStatement(sql);) {
            stat.setInt(1, auto.getId());
            stat.executeUpdate();

        } catch (Exception ex) {
            LOG.severe(ex.toString());
            throw new SQLException(ex.getMessage());
        }

    }

    @Override
    public List<Auto> retrieve() {
        //Working solution 1
//        List<String> options = new ArrayList<>();
//        String sql = "SELECT model, vin FROM auto WHERE active=1";
//        try (
//                Connection conn = ds.getConnection();
//                Statement stat = conn.createStatement();
//                ResultSet rs = stat.executeQuery(sql);) {
//
//            while (rs.next()) {
//                options.add(rs.getString("model"));
//            }
//            conn.close();
//            rs.close();
//
//        } catch (Exception e) {
//            LOG.severe(e.toString());
//        }
//        return options;

        //Solution 2
        ObservableList<Auto> autos = FXCollections.observableArrayList();
        //List<String> options = new ArrayList<>();
        String sql = "SELECT id, model, data_prod, parcurs, culoare, nr_inmatr, vin, logo, active FROM auto WHERE active=1";
        try (
                Connection conn = ds.getConnection();
                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery(sql);) {

            while (rs.next()) {
                Auto at = new Auto(rs.getInt(1), rs.getString(2));
                autos.addAll(at);
            }
            conn.close();
            rs.close();

        } catch (Exception e) {
            LOG.severe(e.toString());
        }

        return autos;

    }

    @Override
    public String returnLogo(int idAuto) throws SQLException {
        String logo = null;
        String sql = "SELECT logo FROM auto WHERE id=" + idAuto;

        try (
                Connection conn = ds.getConnection();
                PreparedStatement stat = conn.prepareStatement(sql);
                ResultSet rs = stat.executeQuery(sql);) {

            //stat.setInt(1, auto.getId());
            //ResultSet rs = stat.executeQuery(sql);
            if (rs.next()) {
                logo = rs.getString(1);
            }

        } catch (Exception ex) {
            LOG.severe(ex.toString());
            throw new SQLException(ex.getMessage());
        }
        return logo;
    }

    @Override
    public Auto findById(int idAuto) throws SQLException {
        Auto auto = null;
        String sql = "SELECT * FROM auto WHERE id=" + idAuto;
        try (
                Connection conn = ds.getConnection();
                PreparedStatement stat = conn.prepareStatement(sql);
                ResultSet rs = stat.executeQuery(sql);) {

            if (rs.next()) {
                String model = rs.getString(2);
                int dataProd = rs.getInt(3);
                String parcurs = rs.getString(4);
                String culoare = rs.getString(5);
                String nrInmatr = rs.getString(6);
                String vin = rs.getString(7);

                auto = new Auto(model, dataProd, parcurs, culoare, nrInmatr, vin, true);
            }

            if (auto == null) {
                throw new Exception("Gresit " + idAuto);
            }

        } catch (Exception ex) {
            LOG.severe(ex.toString());
            throw new SQLException(ex.getMessage());
        }
        return auto;
    }

//    @Override
//    public Auto findByVin(String vinNr) throws SQLException {
//        Auto auto = null;
//        
//        //Verificam mai intai daca VIN codul nu este null
//        //if (vinNr != null) {
//            String sql = "SELECT * FROM auto WHERE vin=" + "\"" + vinNr + "\"";
//
//            try (
//                    Connection conn = ds.getConnection();
//                    PreparedStatement stat = conn.prepareStatement(sql);
//                    ResultSet rs = stat.executeQuery(sql);) {
//
//                if (rs.next()) {
//                    int id = rs.getInt(1);
//                    String model = rs.getString(2);
//                    int dataProd = rs.getInt(3);
//                    String parcurs = rs.getString(4);
//                    String culoare = rs.getString(5);
//                    String nrInmatr = rs.getString(6);
//                    String logo = rs.getString(9);
//
//                    auto = new Auto(id, model, dataProd, parcurs, culoare, nrInmatr, vinNr, logo, true);
//                } else
//
////            if (auto == null) {
//                throw new Exception("VIN cod inexistent in baza de date: " + vinNr);
////            }
//            } catch (Exception ex) {
//            LOG.severe(ex.toString());
//            throw new SQLException(ex.getMessage());
//            }
//          //daca este null afisam in 
////        } else {
////            throw new SQLException("VIN cod inexistent in baza de date: " + vinNr);
////            //LOG.log(Level.INFO, "VIN cod inexistent in baza de date: " + vinNr);
////        }
//
//        System.out.println(auto);
//        return auto;
//    }
    @Override
    public Auto findByVin(String vinNr) throws SQLException {
        Auto auto = null;

        //Verificam mai intai daca VIN codul nu este null
        if (vinNr != null) {
            String sql = "SELECT * FROM auto WHERE vin=" + "\"" + vinNr + "\"";

            try (
                    Connection conn = ds.getConnection();
                    PreparedStatement stat = conn.prepareStatement(sql);
                    ResultSet rs = stat.executeQuery(sql);) {

                if (rs.next()) {
                    int id = rs.getInt(1);
                    String model = rs.getString(2);
                    int dataProd = rs.getInt(3);
                    String parcurs = rs.getString(4);
                    String culoare = rs.getString(5);
                    String nrInmatr = rs.getString(6);
                    String logo = rs.getString(9);

                    auto = new Auto(id, model, dataProd, parcurs, culoare, nrInmatr, vinNr, logo, true);
                }

                if (auto == null) {
                    //throw new Exception("VIN cod inexistent in baza de date: " + vinNr);
                    LOG.log(Level.INFO, "VIN cod inexistent in baza de date: {0}", vinNr);
                }
            } catch (Exception ex) {
                LOG.severe(ex.toString());
                throw new SQLException(ex.getMessage());
            }
            //daca este null afisam in 
        } else {
            throw new SQLException("VIN cod inexistent in baza de date: " + vinNr);
            //LOG.log(Level.INFO, "VIN cod inexistent in baza de date: " + vinNr);
        }
        return auto;
    }

    @Override
    public void update(Auto auto) throws SQLException {
        String sql = "UPDATE auto SET model=?, data_prod=?, parcurs=?, culoare=?, nr_inmatr=?, vin=?, logo=? WHERE id=?";
        try (
                Connection conn = ds.getConnection();
                PreparedStatement pstat = conn.prepareStatement(sql);) {

            pstat.setString(1, auto.getModel());
            pstat.setInt(2, auto.getAnulProd());
            pstat.setString(3, auto.getParcurs());
            pstat.setString(4, auto.getCuloare());
            pstat.setString(5, auto.getNrInmatr());
            pstat.setString(6, auto.getVin());
            pstat.setString(7, auto.getLogo());
            pstat.setInt(8, auto.getId());

            pstat.executeUpdate();
        } catch (Exception e) {

            LOG.severe(e.toString());
            throw new SQLException(e.getMessage());

        }

    }

    @Override
    public Auto retrieveNotifications(int idAuto) throws SQLException {
        Auto auto = null;
        LocalDate deservireAlert;
        LocalDate asigurareAlert;
        LocalDate testareAlert;

        String sql = "SELECT id, deservire_alert, asigurare_alert, testare_alert FROM auto WHERE id=" + idAuto;
        try (
                Connection conn = ds.getConnection();
                PreparedStatement stat = conn.prepareStatement(sql);
                ResultSet rs = stat.executeQuery(sql);) {

            if (rs.next()) {
                int id = rs.getInt(1);

                Date da = rs.getDate(2);
                if (rs.wasNull()) {
                    deservireAlert = null;
                } else {
                    deservireAlert = da.toLocalDate();
                }

                Date aa = rs.getDate(3);
                if (rs.wasNull()) {
                    asigurareAlert = null;
                } else {
                    asigurareAlert = aa.toLocalDate();
                }

                Date ta = rs.getDate(4);
                if (rs.wasNull()) {
                    testareAlert = null;
                } else {
                    testareAlert = ta.toLocalDate();
                }

                auto = new Auto(idAuto, deservireAlert, asigurareAlert, testareAlert);
                //System.out.println(auto);
            }

        } catch (Exception ex) {
            //LOG.info(ex.toString());
            throw new SQLException(ex.getMessage());
        }
        return auto;
    }

    @Override
    public void saveNotification(Auto auto, NotificationType notificationType) throws SQLException {
        String sql = null;
        //String sql = "UPDATE auto SET deservire_alert=?, asigurare_alert=?, testare_alert=? WHERE id=?";

        switch (notificationType) {
            case DESERVIRE:
                sql = "UPDATE auto SET deservire_alert=? WHERE id=?";
                break;
            case ASIGURARE:
                sql = "UPDATE auto SET asigurare_alert=? WHERE id=?";
                break;
            case TESTARE:
                sql = "UPDATE auto SET testare_alert=? WHERE id=?";
                break;
        }

        try (
                Connection conn = ds.getConnection();
                PreparedStatement pstat = conn.prepareStatement(sql);) {

            if (notificationType.equals(DESERVIRE) && auto.getDeservireAlert() != null) {
                pstat.setDate(1, java.sql.Date.valueOf(auto.getDeservireAlert()));
            } else if (auto.getDeservireAlert() == null) {   //daca stergem notificarea atunci setam valoarea din DB in NULL
                pstat.setNull(1, Types.DATE);
            }

            if (notificationType.equals(ASIGURARE) && auto.getAsigurareAlert() != null) {
                pstat.setDate(1, java.sql.Date.valueOf(auto.getAsigurareAlert()));
            } else if (auto.getAsigurareAlert() == null) {
                pstat.setNull(1, Types.DATE);
            }

            if (notificationType.equals(TESTARE) && auto.getTestareAlert() != null) {
                pstat.setDate(1, java.sql.Date.valueOf(auto.getTestareAlert()));
            } else if (auto.getTestareAlert() == null) {
                pstat.setNull(1, Types.DATE);
            }

            pstat.setInt(2, auto.getId());

            pstat.executeUpdate();
        } catch (Exception e) {

            LOG.severe(e.toString());
            throw new SQLException(e.getMessage());

        }

    }

    @Override
    public int countAuto() throws SQLException {
        int nrAuto = 0;
        String sql = "SELECT count(id) FROM auto WHERE active=1";

        try (
                Connection conn = ds.getConnection();
                PreparedStatement stat = conn.prepareStatement(sql);
                ResultSet rs = stat.executeQuery(sql);) {

            if (rs.next()) {
                nrAuto = rs.getInt(1);
            }
        } catch (Exception ex) {
            LOG.severe(ex.toString());
            throw new SQLException(ex.getMessage());
        }
        return nrAuto;
    }

    //Nu mai e nevoie. Implementat prin counter
//    @Override
//    public int countNotificari() throws SQLException {
//        int nrNotificari = 0;
//        String sql = "SELECT count(deservire_alert), count(asigurare_alert), count(testare_alert) FROM registruauto.auto WHERE active=1";
//
//        try (
//                Connection conn = ds.getConnection();
//                PreparedStatement stat = conn.prepareStatement(sql);
//                ResultSet rs = stat.executeQuery(sql);) {
//
//            if (rs.next()) {
//                nrNotificari = rs.getInt(1) + rs.getInt(2) + rs.getInt(3);
//            }
//        } catch (Exception ex) {
//            LOG.severe(ex.toString());
//            throw new SQLException(ex.getMessage());
//        }
//        return nrNotificari;
//
//    }
    //Nu mai e nevoie. Implementat prin counter
//    @Override
//    public int countNotificariAuto(int idAuto) throws SQLException {
//        int nrNotificari = 0;
//        String sql = "SELECT count(deservire_alert), count(asigurare_alert), count(testare_alert) FROM registruauto.auto WHERE id=" + idAuto;
//
//        try (
//                Connection conn = ds.getConnection();
//                PreparedStatement stat = conn.prepareStatement(sql);
//                ResultSet rs = stat.executeQuery(sql);) {
//
//            if (rs.next()) {
//                nrNotificari = rs.getInt(1) + rs.getInt(2) + rs.getInt(3);
//            }
//        } catch (Exception ex) {
//            LOG.severe(ex.toString());
//            throw new SQLException(ex.getMessage());
//        }
//        return nrNotificari;
//    }
    @Override
    public LocalDate getLastVisitServiceDate(int idAuto) throws SQLException {
        LocalDate date = null;
        String sql = "SELECT data FROM vizita WHERE id_auto=" + idAuto + " ORDER BY data DESC LIMIT 1";

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

    @Override
    public int getNrViziteService(int idAuto) throws SQLException {
        int nrViziteService = 0;
        String sql = "SELECT count(id) FROM vizita WHERE id_auto=" + idAuto;

        try (
                Connection conn = ds.getConnection();
                PreparedStatement stat = conn.prepareStatement(sql);
                ResultSet rs = stat.executeQuery(sql);) {

            if (rs.next()) {
                nrViziteService = rs.getInt(1);
            }
        } catch (Exception ex) {
            LOG.severe(ex.toString());
            throw new SQLException(ex.getMessage());
        }
        return nrViziteService;
    }

}
