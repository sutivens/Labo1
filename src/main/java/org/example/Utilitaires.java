package org.example;

import javax.swing.*;

public class Utilitaires {
    public static String ajouterEspaces(int tailleColonne, String donnee, char posEspaces) {
        String donneeAvecEspaces = "";
        int nbEspaces = tailleColonne - donnee.length();
        for (int i = 0; i < nbEspaces; i++) {
            donneeAvecEspaces += " ";
        }
        if (posEspaces == 'F'){// Espaces à la fin de la donnée
            return donnee+donneeAvecEspaces;
        }else{ // Espaces au début de la donnée
            return donneeAvecEspaces + donnee;
        }
    }

    public static String ajouterEspacesFin(int tailleColMax, String chaine) {
        int nbEspaces = tailleColMax - chaine.length();
        String espaces = "";
        for (int i = 0; i < nbEspaces; i++) {
            espaces += " ";
        }
        return espaces;
    }

    public static String ajouterEspacesDebut(int tailleColMax, String chaine) {
        int nbEspaces = (tailleColMax - chaine.length()) / 2;
        String espaces = "";
        for (int i = 0; i < nbEspaces; i++) {
            espaces += " ";
        }
        return espaces;
    }

    public static String ajouterCaractereGauche(char car, int longueur, String ch) {
        String rep = "";
        int nbCar = longueur - ch.length();
        for (int i = 1; i <= nbCar; i++) {
            rep += car;
        }
        return rep + ch;
    }

    public static void displayMessage(String message, String nomCompagnie) {
        JOptionPane.showMessageDialog(null, message, nomCompagnie, JOptionPane.PLAIN_MESSAGE);
    }
}

