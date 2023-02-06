package it.stage.rentalcar.service;

import it.stage.rentalcar.domain.Auto;
import it.stage.rentalcar.domain.Prenotazione;

import java.util.List;

public interface AutoService {
    List<Auto> getCars();
    Auto getCarFromId(int id);
    List<Auto> getFreeCars(List<Prenotazione> reservations);
    void insOrUpCar(Auto a);
    void deleteCar(int id);
}
