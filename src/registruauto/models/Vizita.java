package registruauto.models;
import java.time.LocalDate;
import java.util.Date;

public class Vizita {
    private int id;
    private LocalDate dataVizita;
    private String parcursVizita;
    private String numeAutoService;
    private int costVizita;
    private int idAuto;

    public Vizita() {
    }

    public Vizita(int id, LocalDate dataVizita, String parcursVizita, String numeAutoService, int costVizita, int idAuto) {
        this.id = id;
        this.dataVizita = dataVizita;
        this.parcursVizita = parcursVizita;
        this.numeAutoService = numeAutoService;
        this.costVizita = costVizita;
        this.idAuto = idAuto;
    }

    public Vizita(LocalDate dataVizita, String parcursVizita, String numeAutoService, int costVizita, int idAuto) {
        this.dataVizita = dataVizita;
        this.parcursVizita = parcursVizita;
        this.numeAutoService = numeAutoService;
        this.costVizita = costVizita;
        this.idAuto = idAuto;
    }

    public Vizita(int id) {
        this.id = id;
    }


    public LocalDate getDataVizita() {
        return dataVizita;
    }

    public void setDataVizita(LocalDate dataVizita) {
        this.dataVizita = dataVizita;
    }

    public String getParcursVizita() {
        return parcursVizita;
    }

    public void setParcursVizita(String parcursVizita) {
        this.parcursVizita = parcursVizita;
    }

    public String getNumeAutoService() {
        return numeAutoService;
    }

    public void setNumeAutoService(String numeAutoService) {
        this.numeAutoService = numeAutoService;
    }

    public int getCostVizita() {
        return costVizita;
    }

    public void setCostVizita(int costVizita) {
        this.costVizita = costVizita;
    }

    public int getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(int idAuto) {
        this.idAuto = idAuto;
    }

    @Override
    public String toString() {
        return "Vizita{" + "id=" + id + ", dataVizita=" + dataVizita + ", parcursVizita=" + parcursVizita + ", numeAutoService=" + numeAutoService + ", costVizita=" + costVizita + ", idAuto=" + idAuto + '}';
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   
    
}
