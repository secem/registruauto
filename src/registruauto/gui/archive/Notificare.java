package registruauto.gui.archive;

import java.time.LocalDate;
import java.util.logging.Logger;

public class Notificare {
    private int id;
    private boolean active;
    private String desc;
    private LocalDate date;
    private int idAuto;

    private static final Logger LOG = Logger.getLogger(Notificare.class.getName());
    
    public Notificare(int id, boolean active, String desc, LocalDate date, int idAuto) {
        this.id = id;
        this.active = active;
        this.desc = desc;
        this.date = date;
        this.idAuto = idAuto;
    }

    public Notificare(int id) {
        this.id = id;
    }

    public Notificare(int id, LocalDate date) {
        this.id = id;
        this.date = date;
    }

    public Notificare(int id, boolean active, LocalDate date, int idAuto) {
        this.id = id;
        this.active = active;
        this.date = date;
        this.idAuto = idAuto;
    }
    
    
    
    public int getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(int idAuto) {
        this.idAuto = idAuto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Notificare{" + "id=" + id + ", active=" + active + ", desc=" + desc + ", date=" + date + ", idAuto=" + idAuto + '}';
    }
    
    
}
