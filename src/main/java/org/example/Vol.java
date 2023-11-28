package org.example;

public class Vol {
    private int numeroDuVol = 0;
    private String destination;
    private Date dateDepart;
    private int numeroAvion;
    private int reservation;

    Vol(int numeroDuVol, String destination, Date dateDepart, int numeroAvion, int reservation) {
        this.numeroDuVol = numeroDuVol;
        this.destination = destination;
        this.dateDepart = dateDepart;
        this.numeroAvion = numeroAvion;
        this.reservation = reservation;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public void setReservation(int reservation) {
        this.reservation = reservation;
    }

    public int getNumeroDuVol() {
        return numeroDuVol;
    }

    public String getDestination() {
        return destination;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public int getNumeroDeAvion() {
        return numeroAvion;
    }

    public int getReservation() {
        return reservation;
    }

    public String toString() {
        return this.numeroDuVol + "\t" + Utilitaires.ajouterEspaces(30,this.destination,'F') + "\t" + this.dateDepart + "\t" + this.numeroAvion + "\t" + this.reservation;
    }
}
