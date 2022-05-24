package com.engeto.lekce05;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String FILENAME = "kvetiny.txt";
    public static final String DELIMITER = "\t";
    public static final int STATUS_CANNOT_READ_FILE = 1;

    public static void main(String[] args) {
        ImportOfPlants plants = null;
        try {
            plants =
                    ImportOfPlants.importFromFile(
                            FILENAME, DELIMITER);
        } catch (PlantException e) {
            System.err.println(
                    "Chyba při čtení souboru "+FILENAME
                            +":\n"+e.getLocalizedMessage());
            System.exit(STATUS_CANNOT_READ_FILE);
        }

        // Výpis:
        System.out.println("Informace o zálivce:");
        for (Plant plant : plants.getListOfAllPlants()) {
            System.out.println(plant.getWateringInfo());
        }
        // Odebrat květinu:
        plants.removePlant(plants.getPlantByIndex(2));

        // Přidat 2 květiny:
        plants.addPlant(new Plant("Kaktus"));
        plants.addPlant(new Plant("Palma"));

        // Uložit nový soubor:
        plants.exportToFile(FILENAME, DELIMITER);
    }
}
