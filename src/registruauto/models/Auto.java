package registruauto.models;

import java.time.LocalDate;

public class Auto {

    private int id;
    private String model;
    private int anulProd;
    private String parcurs;
    private String culoare;
    private String nrInmatr;
    private String vin;
    private String logo;
    private boolean active;
    private LocalDate deservireAlert;
    private LocalDate asigurareAlert;
    private LocalDate testareAlert;

    public Auto() {
    }

    public Auto(int id, String model, int anulProd, String parcurs, String culoare, String nrInmatr, String vin, String logo, boolean active, LocalDate deservireAlert, LocalDate asigurareAlert, LocalDate testareAlert) {
        this.id = id;
        this.model = model;
        this.anulProd = anulProd;
        this.parcurs = parcurs;
        this.culoare = culoare;
        this.nrInmatr = nrInmatr;
        this.vin = vin;
        this.logo = logo;
        this.active = active;
        this.deservireAlert = deservireAlert;
        this.asigurareAlert = asigurareAlert;
        this.testareAlert = testareAlert;
    }

    public Auto(int id, String model, int anulProd, String parcurs, String culoare, String nrInmatr, String vin, String logo, boolean active) {
        this.id = id;
        this.model = model;
        this.anulProd = anulProd;
        this.parcurs = parcurs;
        this.culoare = culoare;
        this.nrInmatr = nrInmatr;
        this.vin = vin;
        this.logo = logo;
        this.active = active;
    }

    public Auto(String model, int anulProd, String parcurs, String culoare, String nrInmatr, String vin, boolean active) {
        this.model = model;
        this.anulProd = anulProd;
        this.parcurs = parcurs;
        this.culoare = culoare;
        this.nrInmatr = nrInmatr;
        this.vin = vin;
        this.active = active;
    }

    public Auto(String model, int anulProd, String parcurs, String culoare, String nrInmatr, String vin, String logo, boolean active) {
        this.model = model;
        this.anulProd = anulProd;
        this.parcurs = parcurs;
        this.culoare = culoare;
        this.nrInmatr = nrInmatr;
        this.vin = vin;
        this.logo = logo;
        this.active = active;
    }

    public Auto(int id, String model) {
        this.id = id;
        this.model = model;
    }

    public Auto(int id, LocalDate deservireAlert, LocalDate asigurareAlert, LocalDate testareAlert) {
        this.id = id;
        this.deservireAlert = deservireAlert;
        this.asigurareAlert = asigurareAlert;
        this.testareAlert = testareAlert;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getAnulProd() {
        return anulProd;
    }

    public void setAnulProd(int anulProd) {
        this.anulProd = anulProd;
    }

    public String getParcurs() {
        return parcurs;
    }

    public void setParcurs(String parcurs) {
        this.parcurs = parcurs;
    }

    public String getCuloare() {
        return culoare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }

    public String getNrInmatr() {
        return nrInmatr;
    }

    public void setNrInmatr(String nrInmatr) {
        this.nrInmatr = nrInmatr;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public LocalDate getDeservireAlert() {
        return deservireAlert;
    }

    public void setDeservireAlert(LocalDate deservireAlert) {
        this.deservireAlert = deservireAlert;
    }

    public LocalDate getAsigurareAlert() {
        return asigurareAlert;
    }

    public void setAsigurareAlert(LocalDate asigurareAlert) {
        this.asigurareAlert = asigurareAlert;
    }

    public LocalDate getTestareAlert() {
        return testareAlert;
    }

    public void setTestareAlert(LocalDate testareAlert) {
        this.testareAlert = testareAlert;
    }

    @Override
    public String toString() {
        return "Auto{" + "id=" + id + ", model=" + model + ", anulProd=" + anulProd + ", parcurs=" + parcurs + ", culoare=" + culoare + ", nrInmatr=" + nrInmatr + ", vin=" + vin + ", logo=" + logo + ", active=" + active + ", deservireAlert=" + deservireAlert + ", asigurareAlert=" + asigurareAlert + ", testareAlert=" + testareAlert + '}';
    }

}
