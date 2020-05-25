package registruauto.models;

public class Lucrare {
    private int id;
    private String lucrare;
    private int costLucrare;
    private int idVizita;

    public Lucrare() {
    }

    public Lucrare(int id, String lucrare, int costLucrare, int idVizita) {
        this.id = id;
        this.lucrare = lucrare;
        this.costLucrare = costLucrare;
        this.idVizita = idVizita;
    }
    
    public Lucrare(String lucrare, int costLucrare) {
        this.lucrare = lucrare;
        this.costLucrare = costLucrare;
    }

    public Lucrare(String lucrare, int costLucrare, int idVizita) {
        this.lucrare = lucrare;
        this.costLucrare = costLucrare;
        this.idVizita = idVizita;
    }

    public int getCostLucrare() {
        return costLucrare;
    }

    public void setCostLucrare(int costLucrare) {
        this.costLucrare = costLucrare;
    }

    public String getLucrare() {
        return lucrare;
    }

    public void setLucrare(String lucrare) {
        this.lucrare = lucrare;
    }

    @Override
    public String toString() {
        return "Lucrare{" + "id=" + id + ", lucrare=" + lucrare + ", costLucrare=" + costLucrare + ", idVizita=" + idVizita + '}';
    }

        
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVizita() {
        return idVizita;
    }

    public void setIdVizita(int idVizita) {
        this.idVizita = idVizita;
    }


    
    
    
    
    
    
}
