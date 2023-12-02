package org.example;

import javax.swing.*;
import java.io.*;


public class Utilitaires implements Serializable{
    static ObjectInputStream tmpReadObj;
    static ObjectOutputStream tmpWriteObj;

    static Object obj;

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

    public static void sauvegarderFichierObjets(Object obj, String fichier) throws IOException {
        try {
            tmpWriteObj = new ObjectOutputStream(new FileOutputStream(fichier));
            tmpWriteObj.writeObject(obj);
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable. Vérifiez le chemin et nom du fichier.");
        } catch (IOException e) {
            System.out.println("Un probléme est arrivé lors de la manipulation du fichier. Vérifiez vos données.");
        } catch (Exception e) {
            System.out.println("Un probléme est arrivé lors du chargement du fichier. Contactez l'administrateur.");
        } finally {// Exécuté si erreur ou pas
            if (tmpWriteObj != null) {
                tmpWriteObj.close();
            }
        }
    }

    public static Object chargerFichierObjets(String fichier) throws Exception {
        try {
            tmpReadObj = new ObjectInputStream(new FileInputStream(fichier));
            obj = tmpReadObj.readObject();
            return obj;
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable. Vérifiez le chemin et nom du fichier.");
        } catch (IOException e) {
            System.out.println("Un probléme est arrivé lors de la manipulation du fichier. Vérifiez vos données.");
        } catch (Exception e) {
            System.out.println("Un probléme est arrivé lors du chargement du fichier. Contactez l'administrateur.");
        } finally {// Exécuté si erreur ou pas
            if (tmpReadObj != null) {
                tmpReadObj.close();
            }
        }
        return null;
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

