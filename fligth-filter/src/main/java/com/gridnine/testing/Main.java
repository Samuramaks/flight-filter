package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        FlightFilterEngine flightFilterEngine = new FlightFilterEngine();
        printFilter("До фильтрации", flights);
        printFilter("Правило 1", flightFilterEngine.applyFilter(flights, new DepartureBeforeNowFilter()));
        printFilter("Правило 2", flightFilterEngine.applyFilter(flights, new InvalidSegmentsFilter()));
        printFilter("Правило 3", flightFilterEngine.applyFilter(flights, new LongGroundTimeFilter()));
    }

    public static void printFilter(String title, List<Flight> flights){
        System.out.println("--------" + title + "--------");
        System.out.println("Найдено перелетов: " + flights.size());
        for (int i = 0; i < flights.size(); i++) {
            System.out.printf("%2d. %s%n", i + 1, flights.get(i));
        }
        System.out.println();
    }
}