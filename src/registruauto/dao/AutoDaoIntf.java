package registruauto.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import registruauto.gui.util.NotificationType;
import registruauto.models.Auto;

public interface AutoDaoIntf {

    void save(Auto auto) throws SQLException;

    void remove(Auto auto) throws SQLException;

    List<Auto> retrieve();

    public String returnLogo(int idAuto) throws SQLException;

    public Auto findById(int idAuto) throws SQLException;

    public Auto findByVin(String vin) throws SQLException;

    public void update(Auto auto) throws SQLException;

    public Auto retrieveNotifications(int idAuto) throws SQLException;

    public void saveNotification(Auto auto, NotificationType notificationType) throws SQLException;

    public int countAuto() throws SQLException;
    
    //Nu mai e nevoie. Implementat prin counter
    //public int countNotificari() throws SQLException;
    
    //Nu mai e nevoie. Implementat prin counter
    //public int countNotificariAuto(int idAuto) throws SQLException;

    public LocalDate getLastVisitServiceDate(int idAuto) throws SQLException;

    public int getNrViziteService(int idAuto) throws SQLException;

}
