package it.stage.rentalcar.service;


import it.stage.rentalcar.domain.Auto;
import it.stage.rentalcar.repository.AutoRepository;
import org.springframework.stereotype.Service;

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
    public void insOrUpCar(Auto a) {
        autoRepository.insOrUpCar(a);
    }

    @Override
    public void deleteCar(int id) {
        autoRepository.deleteCar(id);
    }
}
