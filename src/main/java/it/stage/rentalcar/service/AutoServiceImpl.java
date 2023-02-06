package it.stage.rentalcar.service;


import it.stage.rentalcar.domain.Auto;
import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.repository.AutoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AutoServiceImpl implements AutoService {
    private final AutoRepository autoRepository;

    public AutoServiceImpl(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    @Override
    public List<Auto> getCars() {
        return autoRepository.getCars();
    }

    @Override
    public Auto getCarFromId(int id) {
        return autoRepository.getCarFromId(id);
    }

    @Override
    public List<Auto> getFreeCars(List<Prenotazione> reservations) {
        List<Auto> free = autoRepository.getCars();
        List<Auto> alreadyReserved = new ArrayList<>();
        for (Prenotazione p:reservations) {
            alreadyReserved.add(p.getAuto());
        }
        Iterator<Auto> iter1 = free.iterator();
        Iterator<Auto> iter2 = alreadyReserved.iterator();
        while (iter1.hasNext()) {
            Auto auto1 = iter1.next();
            while (iter2.hasNext()) {
                Auto auto2 = iter2.next();
                if (auto1.getTarga().equals(auto2.getTarga())) {
                    iter1.remove();
                    break;
                }
            }
        }
        return free;
    }

    @Override
    public void insOrUpCar(Auto a) {
        autoRepository.insOrUpCar(a);
    }

    @Override
    public void deleteCar(int id) {
        autoRepository.deleteCar(id);
    }
}
