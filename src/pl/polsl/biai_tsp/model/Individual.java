package pl.polsl.biai_tsp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Individual {
    List<Integer> solution = new ArrayList<>();
    Float routeLength;
    TSPGraph graph;


    public List<Integer> getSolution() {
        return solution;
    }

    public void setGraph(TSPGraph graph) {
        this.graph = graph;
    }

    public Individual(TSPGraph graph) {
        this.graph = graph;
    }

    public void createRandomSolution() {
        solution.clear();
        for (int i = 0; i < graph.getNumberOfCities(); i++) {
            solution.add(i);
        }
        Collections.shuffle(solution);
        solution.add(solution.get(0));
    }

    public Float calculateRouteLength() {
        routeLength = 0f;
        for (int i = 1; i < solution.size(); i++) {
            routeLength += graph.getDistanceBetweenCities(solution.get(i), solution.get(i - 1));
        }
        return routeLength;
    }

}
