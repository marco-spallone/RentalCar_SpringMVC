package it.stage.rentalcar.repository;

import it.stage.rentalcar.domain.Auto;


import java.util.List;

public interface AutoRepository {
    List<Auto> getCars();
    Auto getCarFromId(int id);
    void insOrUpCar(Auto a);
    void deleteCar(int id);
}
