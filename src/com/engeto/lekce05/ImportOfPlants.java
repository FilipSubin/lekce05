package com.engeto.lekce05;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ImportOfPlants {
    private List<Plant> plantList = new ArrayList<>();

    public static ImportOfPlants importFromFile(
            String filename, String delimiter)
            throws PlantException {
        String nextLine = "";
        String[] items = new String[1];
        String name;
        String notes;
        LocalDate watering;
        LocalDate planted;
        int frequency;
        int lineNumber = 0;

        ImportOfPlants result = new ImportOfPlants();

        try (Scanner scanner =
                     new Scanner(new BufferedReader(
                             new FileReader(filename)))) {
            while (scanner.hasNextLine()) {
                lineNumber++;
                nextLine = scanner.nextLine();
                items = nextLine.split(delimiter);

                name = items[0];
                notes = items[1];

                if (Integer.parseInt(items[2]) <= 0) {
                    throw new PlantException("Frekvence zalévání je menší nebo rovna nule!");
                }else {
                    frequency = Integer.parseInt(items[2]);
                }
                if (LocalDate.parse(items[3]).isBefore(LocalDate.parse(items[4])) || LocalDate.parse(items[3]) == LocalDate.parse(items[4])) {
                    throw new PlantException("Datum posledního zalití je před datumem zasazení!");
                }else {
                    watering = LocalDate.parse(items[3]);
                }

                planted = LocalDate.parse(items[4]);
                result.addPlant(new Plant(name,notes, planted, watering, frequency));
            }
        } catch (FileNotFoundException e) {
            throw new PlantException(
                    "Soubor "+filename+" nebyl nalezen!\n"+e.getLocalizedMessage());
        }
        return result;
    }

    public void exportToFile(String filename, String delimiter) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Plant plant : plantList) {
                writer.println(
                        plant.getName()+delimiter+plant.getNotes()+delimiter+plant.getFrequencyOfWatering()+delimiter+plant.getPlanted()+delimiter+plant.getWatering()
                );

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPlant(Plant plant) {
        plantList.add(plant);
    }

    public void removePlant(Plant plant) {
        plantList.remove(plant);
    }

    public List<Plant> getListOfAllPlants() {
        List<Plant> result = new ArrayList<>();
        for (Plant purchase : plantList) {
                result.add(purchase);
        }
        return result;
    }

    public Plant getPlantByIndex(int index) {
        return plantList.get(index);
    }
}
