package pl.polsl.biai_tsp.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TSPGraph {

    private int numberOfCities;
    private float[][] distanceArray;

    public void initializeFromFile(String fileName) {

        ArrayList<String> input = loadFile(fileName);
        input.forEach(System.out::println);

        numberOfCities = extractNumberOfCitiesFromInput(input.get(input.size() - 2));
        distanceArray = new float[numberOfCities][numberOfCities];
        float[][] coordinatesArray = extractCoordinates(input);
        parseCoordinatesArrayToDistanceArray(coordinatesArray);



//        for(int i = 0; i<numberOfCities; i++){
//            for(int j = 0; j<numberOfCities;j++)
//                System.out.print(distanceArray[i][j] + " ");
//            System.out.println();
//        }

    }

    public TSPGraph(String fileName){
        initializeFromFile(fileName);
    }
    /**
     * Method that loads raw TSP file
     *
     * @param fileName name of file
     * @return ArrayList<String> in which each element represent a single line from file
     */
    private ArrayList<String> loadFile(String fileName) {
        ArrayList<String> input = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                input.add(scanner.nextLine());
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return input;
    }

    /**
     * Method that extracts coordinates from source file
     *
     * @param input ArrayList<String> filled using loadFile method
     * @return float[][] with coordinates of each city
     */
    private float[][] extractCoordinates(ArrayList<String> input) {
        float[][] coordinatesArray = new float[numberOfCities][2];
        boolean coordinatesSection = false;
        int coordinatesSectionLineNumber = 0;

        for (int lineNumber = 0; lineNumber < input.size() - 1; lineNumber++) {
            if (coordinatesSection) {
                String[] splitLine = input.get(lineNumber).split(" ");
                coordinatesArray[lineNumber - coordinatesSectionLineNumber - 1][0] =
                        Float.parseFloat(splitLine[1]);
                coordinatesArray[lineNumber - coordinatesSectionLineNumber - 1][1] =
                        Float.parseFloat(splitLine[2]);
            }

            if (!coordinatesSection && input.get(lineNumber).equals("NODE_COORD_SECTION")) {
                coordinatesSection = true;
                coordinatesSectionLineNumber = lineNumber;
            }
        }
        System.out.println(coordinatesArray.length);

        return coordinatesArray;
    }

    /**
     * Calculates a distance between each two cities
     * @param coordinatesArray coordinates of cities
     */
    private void parseCoordinatesArrayToDistanceArray(float[][] coordinatesArray) {
        for (int firstCity = 0; firstCity < numberOfCities; firstCity++) {
            distanceArray[firstCity][firstCity] = 0;

            for (int secondCity = firstCity + 1; secondCity < numberOfCities; secondCity++) {
                float xDifference;
                float yDifference;

                xDifference = coordinatesArray[firstCity][0] - coordinatesArray[secondCity][0];
                yDifference = coordinatesArray[firstCity][1] - coordinatesArray[secondCity][1];
                distanceArray[firstCity][secondCity] = (float) Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
                distanceArray[secondCity][firstCity] = distanceArray[firstCity][secondCity];
            }
        }
    }

    /**
     *
     * @param input second last line of input file
     * @return number of cities in given TSP dataset
     */
    private int extractNumberOfCitiesFromInput(String input){
        int i = 0;
        while (i < input.length() && !Character.isDigit(input.charAt(i))) i++;
        int j = i;
        while (j < input.length() && Character.isDigit(input.charAt(j))) j++;
        return Integer.parseInt(input.substring(i, j));
    }
}
