package registruauto.gui.archive;

import java.sql.SQLException;

public interface NotificareDaoIntf {

    void save(Notificare notificare) throws SQLException;
    Notificare retrieve(int id) throws SQLException;

}
