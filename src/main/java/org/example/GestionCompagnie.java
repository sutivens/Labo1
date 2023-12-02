package org.example;

import javax.swing.*;
import java.awt.*;

public class GestionCompagnie {
    public static void main(String[] args) throws Exception {
        String nomCompagnie = JOptionPane.showInputDialog(null, "Entrez le nom de la compagnie:", "", JOptionPane.QUESTION_MESSAGE);

        if (nomCompagnie.equals("Cie Air Relax")) {
            Compagnie compagnie = new Compagnie(nomCompagnie);
            displayMenu(compagnie);
        } else {
            Utilitaires.displayMessage("Veuillez taper: <<Cie Air Relax>>","");
            System.exit(0);
        }
    }

    public static void displayMenu(Compagnie compagnie) {
        boolean end = false;
        String content = "GESTION DES VOLS\n1. Lister des vols\n2. Ajout d'un vol\n3. Retrait d'un vol\n4. Modification de la date de départ\n5. Réservation d'un vol\n0. Terminer\nFaites votre choix: : ";

        while (!end) {
            String input = JOptionPane.showInputDialog(null, content, compagnie.getNom(), JOptionPane.QUESTION_MESSAGE);

            switch (input) {
                case "1":
                    compagnie.listerVols();
                    break;
                case "2":
                    input = JOptionPane.showInputDialog(null,"Numéro du vol:","Ajout d'un vol", JOptionPane.QUESTION_MESSAGE);
                    compagnie.insererVol(input);
                    break;
                case "3":
                    input = JOptionPane.showInputDialog(null,"Numéro du vol:","Retrait d'un vol", JOptionPane.QUESTION_MESSAGE);
                    compagnie.retirerVol(input);
                    break;
                case "4":
<<<<<<< Updated upstream
                    modifierDate();
                    break;
                case "5":
                    reserverVol();
=======
                    input = JOptionPane.showInputDialog(null,"Numéro du vol:","MODIFICATION DE LA DATE DE DÉPART", JOptionPane.QUESTION_MESSAGE);
                    compagnie.modifierDate(input);
                    break;
                case "5":
                    input = JOptionPane.showInputDialog(null,"Numéro du vol:","RÉSERVATION D'UN VOL", JOptionPane.QUESTION_MESSAGE);
                    compagnie.reserverVol(input);
>>>>>>> Stashed changes
                    break;
                case "0":
                    Utilitaires.displayMessage("Merci d'avoir utilisé notre application", compagnie.getNom());
                    end = true;
                    break;
                default:
                    Utilitaires.displayMessage("Erreur! Choix invalide", compagnie.getNom());
                    break;
            }
        }
    }

    public static void reserverVol() {
    }

    public static void modifierDate() {
    }


}

