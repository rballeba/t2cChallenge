package com.t2c.Concessionaire.model.CarComparators;

import com.t2c.Concessionaire.model.Car;

import java.util.Comparator;

public class CarSaleDateComparator implements Comparator<Car> {
    @Override
    public int compare(Car car1, Car car2) {
        if(car1.getSaleDate() == null)
            return -1;
        else if(car2.getSaleDate() == null)
            return 1;
        else {
            return -car1.getSaleDate().compareTo(car2.getSaleDate());
        }
    }
}
