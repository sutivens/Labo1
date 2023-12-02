package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Compagnie implements Serializable {
    public static final int MAX_PLACES = 500; // Je ne vois pas dans les données le nombre de places maximale... donc je vais lui mettre un nombre aléatoire
    private String nom;
    private ArrayList<Vol> listeVols;
    private static int nombreVolsActifs = 0;

    Compagnie(String nom) throws Exception {
        this.nom = nom;
        this.listeVols = new ArrayList<Vol>();
        chargerVols();
    }

    private void chargerVols() throws Exception {
        File fichierObj = new File("src/main/java/org/example/donnees/Cie_Air_Relax.obj");
        File fichierTxt = new File("src/main/java/org/example/donnees/Cie_Air_Relax.txt");

        if (fichierObj.exists()) {
            Object obj = Utilitaires.chargerFichierObjets("src/main/java/org/example/donnees/Cie_Air_Relax.obj");
            this.listeVols = (ArrayList<Vol>) obj;
        } else {
            chargerFichierTexte("src/main/java/org/example/donnees/Cie_Air_Relax.txt");
        }
    }

    private void chargerFichierTexte(String fichier) throws Exception {
        BufferedReader readFile = new BufferedReader(new FileReader(fichier));
        String line = readFile.readLine();
        while (line != null) {
            nombreVolsActifs++; // Compter les vols
            String[] elems = line.split(";");
            this.listeVols.add(new Vol(Integer.parseInt(elems[0]), elems[1], new Date(Integer.parseInt(elems[2]), Integer.parseInt(elems[3]), Integer.parseInt(elems[4])), Integer.parseInt(elems[5]), Integer.parseInt(elems[6])));
            line = readFile.readLine();
        }
        readFile.close();
    }

    private int rechercherVol(int numeroDuVol) {
        /** int idx = 0;

        while (idx < nombreVolsActifs) { // Numéro existant ou pas
            if (numeroDuVol == listeVols.get(idx).getNumeroDuVol()) {
                return idx;
            }
            idx++;
        }
        return -1; // Si le num du vol n'existe pas
         **/
        Vol volATrouver = new Vol(numeroDuVol, "", null, 0, 0); // compareTo travaille avec le numéro du vol, alors seulement ce numéro va être utilisé parmi les autres attributs dans le binarySearch
        int idx = Collections.binarySearch(listeVols, volATrouver); // Doit etre le meme type dont Vol

        return idx;
    }

    public void insererVol(String inputNumber) {
        try {
            int idx = rechercherVol(Integer.parseInt(inputNumber));
            if (idx >= 0) { // Si la liste n'est pas vide et existe (index positif)
                JOptionPane.showMessageDialog(null, "Numéro existant", this.nom, JOptionPane.ERROR_MESSAGE);
            } else {
                String inputName = JOptionPane.showInputDialog(null, "Saisir la destination:", this.nom, JOptionPane.QUESTION_MESSAGE);
                String inputDay = JOptionPane.showInputDialog(null, "Saisir la journée du départ 'DD':", this.nom, JOptionPane.QUESTION_MESSAGE);
                String inputMonth = JOptionPane.showInputDialog(null, "Saisir le mois du départ 'MM'):", this.nom, JOptionPane.QUESTION_MESSAGE);
                String inputYear = JOptionPane.showInputDialog(null, "Saisir l'année du départ 'YYYY':", this.nom, JOptionPane.QUESTION_MESSAGE);

                String messageValidation = Date.validerDate(Integer.parseInt(inputDay), Integer.parseInt(inputMonth), Integer.parseInt(inputYear));

                if (messageValidation.isEmpty()) { // Si la validation ne donne pas des messages d'erreurs
                    String inputPlaneNumber = JOptionPane.showInputDialog(null, "Saisir le numéro de l'avion:", this.nom, JOptionPane.QUESTION_MESSAGE);
                    idx = 0;
                    boolean valid = true;
                    while (valid && idx < inputPlaneNumber.length()) {
                        if (!Character.isDigit(inputPlaneNumber.charAt(idx))) {
                            JOptionPane.showMessageDialog(null, "Vous n'avez pas saisi un numéro d'avion", this.nom, JOptionPane.ERROR_MESSAGE);
                            valid = false;
                        }
                        idx++;
                    }

                    if (valid) {
                        Vol vol = new Vol(Integer.parseInt(inputNumber), inputName, new Date(Integer.parseInt(inputDay), Integer.parseInt(inputMonth), Integer.parseInt(inputYear)), Integer.parseInt(inputPlaneNumber), 0); // Pour le mettre dans le ArrayList
                        listeVols.add(vol);
                        BufferedWriter writeFile = new BufferedWriter(new FileWriter("src/main/java/org/example/donnees/Cie_Air_Relax.txt", true));
                        writeFile.write(vol.getNumeroDuVol() + ";" + vol.getDestination() + ";" + vol.getDateDepart().getJour() + ";" + vol.getDateDepart().getMois() + ";" + vol.getDateDepart().getAn() + ";" + vol.getNumeroDeAvion() + ";" + vol.getReservation() + "\n"); // Ajout au fichier
                        writeFile.close();
                        Utilitaires.sauvegarderFichierObjets(listeVols, "src/main/java/org/example/donnees/Cie_Air_Relax.obj");
                        nombreVolsActifs++;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, messageValidation, this.nom, JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vous avez fait une faute de saisi", this.nom, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void modifierDate(String inputNumber) {
        int idx = rechercherVol(Integer.parseInt(inputNumber));
        if (idx >= 0) {
            JTextArea jtaContenu = new JTextArea();
            String resultat = Utilitaires.ajouterEspaces(20, "Destination", 'F') + "\tDate départ\t\n";
            resultat += Utilitaires.ajouterEspaces(30, listeVols.get(idx).getDestination(), 'F') + "\t"
                    + listeVols.get(idx).getDateDepart() + "\n";
            resultat += "\nEntrez la nouvelle date (dans la forme JJ/MM/AAAA)";
            jtaContenu.setFont(new Font("Courier", Font.PLAIN, 12));
            jtaContenu.setText(resultat);
            String dateComplete = JOptionPane.showInputDialog(null, jtaContenu, "MODIFICATION DE LA DATE DE DÉPART", JOptionPane.QUESTION_MESSAGE);
            String[] elems = dateComplete.split("/");

            try {
                String message = Date.validerDate(Integer.parseInt(elems[0]), Integer.parseInt(elems[1]), Integer.parseInt(elems[2]));
                if (message.isEmpty()) {
                    this.listeVols.get(idx).setDateDepart(new Date(Integer.parseInt(elems[0]), Integer.parseInt(elems[1]), Integer.parseInt(elems[2])));
                    Utilitaires.sauvegarderFichierObjets(listeVols, "src/main/java/org/example/donnees/Cie_Air_Relax.obj");
                    saveFile();
                    JOptionPane.showMessageDialog(null, "La date de départ a été modifiée", "MODIFICATION DE LA DATE DE DÉPART", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, message, "MODIFICATION DE LA DATE DE DÉPART", JOptionPane.PLAIN_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Date invalide", "MODIFICATION DE LA DATE DE DÉPART", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ce numéro de vol est introuvable", "MODIFICATION DE LA DATE DE DÉPART", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void retirerVol(String inputNumber) {
        try {
            int idx = rechercherVol(Integer.parseInt(inputNumber));
            if (idx >= 0) {
                JTextArea jtaContenu = new JTextArea();
                String resultat = Utilitaires.ajouterEspaces(20, "Destination", 'F') + "\tDate départ\tRéservations\t\t\n";
                resultat += Utilitaires.ajouterEspaces(30, listeVols.get(idx).getDestination(), 'F') + "\t"
                        + listeVols.get(idx).getDateDepart() + "\t"
                        + listeVols.get(idx).getReservation() + "\n"
                        + "\nDésirez-vous vraiment retirer ce vol (O/N) ?";

                jtaContenu.setFont(new Font("Courier", Font.PLAIN, 12));
                jtaContenu.setText(resultat);
                String inputConfirm = JOptionPane.showInputDialog(null, jtaContenu, this.nom, JOptionPane.QUESTION_MESSAGE);
                if (inputConfirm.equals("O")) {
                    listeVols.remove(idx);
                    nombreVolsActifs--;
                    Utilitaires.sauvegarderFichierObjets(listeVols, "src/main/java/org/example/donnees/Cie_Air_Relax.obj");
                    saveFile();

                    JOptionPane.showMessageDialog(null, "Ce vol a été enlevé", this.nom, JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Il n'y a eu aucun changement", this.nom, JOptionPane.PLAIN_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ce numéro de vol est introuvable", this.nom, JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vous avez fait une faute de saisi / Annulé", this.nom, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveFile() {
        try {
            BufferedWriter writeFile = new BufferedWriter(new FileWriter("src/main/java/org/example/donnees/Cie_Air_Relax.txt"));
            for (int i = 0; i < listeVols.size(); i++) {
                String line = listeVols.get(i).getNumeroDuVol() + ";" + listeVols.get(i).getDestination() + ";" + listeVols.get(i).getDateDepart().getJour() + ";" + listeVols.get(i).getDateDepart().getMois() + ";" + listeVols.get(i).getDateDepart().getAn() + ";" + listeVols.get(i).getNumeroDeAvion() + ";" + listeVols.get(i).getReservation() + "\n";
                writeFile.write(line);
            }
            writeFile.close();
        } catch (Exception e) {
            System.out.println("Erreur! Fichier non enregistré");
        }
    }

    public void reserverVol(String inputNumber) {
        try {
            int idx = rechercherVol(Integer.parseInt(inputNumber));
            if (idx >= 0) {
                if (MAX_PLACES <= listeVols.get(idx).getReservation()) {
                    JOptionPane.showMessageDialog(null, "Il n'y a plus de places disponibles", "RÉSERVATION D'UN VOL", JOptionPane.ERROR_MESSAGE);
                } else {
                    JTextArea jtaContenu = new JTextArea();
                    String resultat = Utilitaires.ajouterEspaces(20, "Destination", 'F') + "\tDate départ\tPlaces restantes\t\t\n";
                    int placesRestantes =  MAX_PLACES - listeVols.get(idx).getReservation();
                    resultat += Utilitaires.ajouterEspaces(30, listeVols.get(idx).getDestination(), 'F') + "\t"
                            + listeVols.get(idx).getDateDepart() + "\t"
                            + placesRestantes + "\n"
                            + "\nCombien de places voulez vous réserver?";

                    jtaContenu.setFont(new Font("Courier", Font.PLAIN, 12));
                    jtaContenu.setText(resultat);
                    String inputConfirm = JOptionPane.showInputDialog(null, jtaContenu, this.nom, JOptionPane.QUESTION_MESSAGE);
                    try {
                        if (Integer.parseInt(inputConfirm) < placesRestantes) {
                            this.listeVols.get(idx).setReservation(this.listeVols.get(idx).getReservation() + Integer.parseInt(inputConfirm));
                            Utilitaires.sauvegarderFichierObjets(listeVols, "src/main/java/org/example/donnees/Cie_Air_Relax.obj");
                            saveFile();
                            JOptionPane.showMessageDialog(null, "Vous avez bien réservés vos places", "RÉSERVATION D'UN VOL", JOptionPane.PLAIN_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Il n'y a pas assez de places pour vos réservations", "RÉSERVATION D'UN VOL", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Saisi invalide", "RÉSERVATION D'UN VOL", JOptionPane.ERROR_MESSAGE);
                    }
                }


            } else {
                JOptionPane.showMessageDialog(null, "Numéro introuvable", "RÉSERVATION D'UN VOL", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e ) {
            JOptionPane.showMessageDialog(null, "Vous avez fait une faute de saisi", "RÉSERVATION D'UN VOL", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void listerVols() {
        JTextArea jtaContenu = new JTextArea();
        String resultat = "\t\tLISTE DES VOLS\n";
        resultat += "Numéro\t" + Utilitaires.ajouterEspaces(20, "Destination", 'F') + "\tDate départ\tNumero avion\tRéservations\n";

        for (int i = 0; i < listeVols.size(); i++) {
            resultat += listeVols.get(i).toString() + "\n";
        }

        jtaContenu.setFont(new Font("Courier", Font.PLAIN, 12));
        jtaContenu.setText(resultat);
        JOptionPane.showMessageDialog(null, jtaContenu, this.nom, JOptionPane.PLAIN_MESSAGE);
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Vol> getListeVols() {
        return listeVols;
    }

    public void setListeVols(ArrayList<Vol> listeVols) {
        this.listeVols = listeVols;
    }
}
