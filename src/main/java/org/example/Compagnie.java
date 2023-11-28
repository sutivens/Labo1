package org.example;

import jdk.jshell.execution.Util;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class Compagnie {
    public final int MAX_PLACES = 0;
    private String nom;
    private ArrayList<Vol> listeVols;
    private static int nombreVolsActifs = 0;

    Compagnie(String nom) throws Exception {
        this.nom = nom;
        this.listeVols = new ArrayList<Vol>();
        chargerFichierTexte();
    }

    private void chargerFichierTexte() throws Exception {
        BufferedReader readFile = new BufferedReader(new FileReader("src/main/java/org/example/donnees/Cie_Air_Relax.txt"));
        String line = readFile.readLine();
        while (line != null) {
            nombreVolsActifs++; // Compter les vols
            String[] elems = line.split(";");
            this.listeVols.add(new Vol(Integer.parseInt(elems[0]), elems[1], new Date(Integer.parseInt(elems[2]), Integer.parseInt(elems[3]), Integer.parseInt(elems[4])), Integer.parseInt(elems[5]), Integer.parseInt(elems[6])));
            line = readFile.readLine();
        }
        readFile.close();
        /* System.out.println(nombreVolsActifs); Test
        for (int i = 0; i < listeVols.size(); i++) {
            System.out.println(listeVols.get(i));
        }
         */
    }

    private int rechercherVol(int numeroDuVol) {
        int idx = 0;

        while (idx < nombreVolsActifs) { // Numéro existant ou pas
            if (numeroDuVol == listeVols.get(idx).getNumeroDuVol()) {
                return idx;
            }
            idx++;
        }
        return -1; // Si le num du vol n'existe pas
    }

    public void insererVol(String inputNumber) {
        try {
            int idx = rechercherVol(Integer.parseInt(inputNumber));
            if (idx != -1) {
                JOptionPane.showMessageDialog(null, "Numéro existant", this.nom, JOptionPane.ERROR_MESSAGE);
            } else {
                String inputName = JOptionPane.showInputDialog(null, "Saisir la destination:", this.nom, JOptionPane.QUESTION_MESSAGE);
                String inputDay = JOptionPane.showInputDialog(null, "Saisir la journée du départ:", this.nom, JOptionPane.QUESTION_MESSAGE);
                String inputMonth = JOptionPane.showInputDialog(null, "Saisir le mois du départ:", this.nom, JOptionPane.QUESTION_MESSAGE);
                String inputYear = JOptionPane.showInputDialog(null, "Saisir l'année du départ:", this.nom, JOptionPane.QUESTION_MESSAGE);

                if (Date.validerDate(Integer.parseInt(inputDay), Integer.parseInt(inputMonth), Integer.parseInt(inputYear)).isEmpty()) { // Si la validation ne donne pas des messages d'erreurs
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
                        Vol vol = new Vol(Integer.parseInt(inputNumber), inputName, new Date(Integer.parseInt(inputDay), Integer.parseInt(inputMonth), Integer.parseInt(inputYear)), Integer.parseInt(inputPlaneNumber), 0);
                        placeAtRightPosition(vol);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Date invalide", this.nom, JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vous avez fait une faute de saisi", this.nom, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void placeAtRightPosition(Vol volAAjouter) {
        int idx = listeVols.size(); // Par défaut à la dernière position

        for (int i = 0; i < this.listeVols.size(); i++) {
            if (volAAjouter.getNumeroDuVol() < listeVols.get(i).getNumeroDuVol()) {
                idx = i;
            }
        }
        listeVols.add(idx, volAAjouter);
    }

    public void retirerVol(String inputNumber) {
        try {
            int idx = rechercherVol(Integer.parseInt(inputNumber));
            if (idx != -1) {
                JTextArea jtaContenu = new JTextArea();
                String resultat = Utilitaires.ajouterEspaces(20, "Destination", 'F') + "\tDate départ\tRéservations\n";

                String extraTabulation = "\t";

                if (listeVols.get(idx).getDestination().length() >= 16) { // La date se décalait quand la destination avait une longueur d'au moins 16 caractères... Alors j'ai du trouver un petit fix temporaire
                    extraTabulation = "";
                }
                resultat += listeVols.get(idx).getDestination() + "\t" + extraTabulation
                        + listeVols.get(idx).getDateDepart() + "\t"
                        + listeVols.get(idx).getReservation() + "\n"
                        + "\nDésirez-vous vraiment retirer ce vol (O/N) ?";

                jtaContenu.setFont(new Font("Courier", Font.PLAIN, 12));
                jtaContenu.setText(resultat);
                String inputConfirm = JOptionPane.showInputDialog(null, jtaContenu, this.nom, JOptionPane.QUESTION_MESSAGE);
                if (inputConfirm.equals("O")) {
                    listeVols.remove(idx);
                    nombreVolsActifs--;
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

    public void listerVols() {
        JTextArea jtaContenu = new JTextArea();
        String resultat = "\t\tLISTE DES VOLS\n";
        resultat += "Numéro\t" + Utilitaires.ajouterEspaces(20, "Destination", 'F') + "\tDate départ\tNumero avion\tRéservations\n";
        for (int i = 0; i < nombreVolsActifs; i++) {

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
