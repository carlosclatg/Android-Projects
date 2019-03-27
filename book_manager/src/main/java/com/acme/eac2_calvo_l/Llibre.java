package com.acme.eac2_calvo_l;

public class Llibre {

    private String titol;
    private String autors;
    private String editorial;
    private String tipus;
    private String preu;

    public Llibre ( String titol, String autors, String editorial, String tipus, String preu){
        this.titol=titol;
        this.autors = autors;
        this.editorial= editorial;
        this.tipus = tipus;
        this.preu = preu;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getAutors() {
        return autors;
    }

    public void setAutors(String autors) {
        this.autors = autors;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getPreu() {
        return preu;
    }

    public void setPreu(String preu) {
        this.preu = preu;
    }
}
