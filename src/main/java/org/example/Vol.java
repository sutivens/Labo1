package org.example;

public class Vol {
    private int numeroDuVol;
    private String destination;
    private Date dateDepart;
    private int numeroAvion;
    private int reservation;

    Vol(String destination, Date dateDepart, int numeroAvion, int reservation) {
        this.numeroDuVol = 0;
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
        return this.numeroDuVol + "\t" + this.destination + "\t" + this.dateDepart + "\t" + this.numeroAvion + "\t" + this.reservation;
    }
}
