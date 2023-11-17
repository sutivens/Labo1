package org.example;

import java.time.LocalDate;

public class Date {
    private int jour;
    private int mois;
    private int an;

    static public String tabMois[] = {null, "Janvier", "Février", "Mars", "Avril", "Mai," "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Decembre"};
    static public LocalDate dateActuelle = LocalDate.now();

    Date() {
        jour = mois = 1;
        an = 1990;

    }

    Date(int jour, int mois, int an) {
        super();
        this.jour = jour;
        this.mois = mois;
        this.an = an;
    }

    public int getJour() {
        return jour;
    }

    public int getMois() {
        return mois;
    }

    public int getAn() {
        return an;
    }

    public static String validerDate(int jour, int mois, int an, boolean etat[]) {
        String message = "";
        int nbJours = 0;

        if(mois < 1 || mois > 12) {
            etat[1] = false;
            message += "Mois " + mois + " n'est pas un mois valide [1-12]" + "\n";
        } else {
            etat[1] = true;
        }

        if (etat[1]) {
            nbJours = determinerNbJoursMois(mois, an);
            if (jour > nbJours || jour <= 0) {
                etat[0] = false;
                message += "Jour invalide pour le mois de " + tabMois[mois].toLowerCase() + "\n";
            } else {
                etat[0] = true;
            }

        } else {
            message += "Impossible de valider le jour puisque votre mois est invalide";
        }

        int anneActuelle = dateActuelle.getYear();
        if (an < anneActuelle) {
            etat[2] = false;
            message += "Annee " + an + " ne peut pas etre inferieure a lanne actuelle, soit " + anneActuelle;
        } else {
            etat[2] = true;
        }
        return message;
    }

    public validerDateReservation(Date dateReservation) {
        // Valider si dateReservation est <= dateActuelle;
    }


    public String toString() { // On va voir ca en classe pour la validation
        return this.jour + "/" + this.mois + "/" + this.an;
    }
}
