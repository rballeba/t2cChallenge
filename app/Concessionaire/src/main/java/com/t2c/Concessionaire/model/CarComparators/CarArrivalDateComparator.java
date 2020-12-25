package com.t2c.Concessionaire.model.CarComparators;

import com.t2c.Concessionaire.model.Car;

import java.util.Comparator;

public class CarArrivalDateComparator implements Comparator<Car> {
    @Override
    public int compare(Car car1, Car car2) {
        if(car1.getArrivalDate() == null)
            return -1;
        else if(car2.getArrivalDate() == null)
            return 1;
        else {
            return -car1.getArrivalDate().compareTo(car2.getArrivalDate());
        }
    }
}
