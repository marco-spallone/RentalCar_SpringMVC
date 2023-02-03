package it.stage.rentalcar.service;

import it.stage.rentalcar.domain.Auto;

import java.util.List;

public interface AutoService {
    List<Auto> getCars();
    Auto getCarFromId(int id);
    void insOrUpCar(Auto a);
    void deleteCar(int id);
}
