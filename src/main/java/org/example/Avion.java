package org.example;

import java.io.Serializable;

// Je ne vois pas des instructions pour la manipulation de cette classe...
public class Avion implements Serializable {
    private int numeroAvion;
    private String typeAvion;
    private int nombrePlaces;

    Avion(int numeroAvion, String typeAvion, int nombrePlaces) {
        this.numeroAvion = numeroAvion;
        this.typeAvion = typeAvion;
        this.nombrePlaces = nombrePlaces;
    }
}
