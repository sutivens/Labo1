package org.example;

import java.util.ArrayList;

public class Compagnie {
    public final int MAX_PLACES;
    private String nom;
    private ArrayList<Vol> listeVols;
    private static int nombreVolsActifs = 0;


    Compagnie(String nom) {
    this.listeVols = new ArrayList<Vol>();
    this.nom = nom;
    Utilitaires.chargerFichierTexte();
    }
}
