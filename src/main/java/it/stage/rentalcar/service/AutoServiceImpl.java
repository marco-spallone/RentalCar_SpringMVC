package it.stage.rentalcar.service;


import it.stage.rentalcar.domain.Auto;
import it.stage.rentalcar.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoServiceImpl implements AutoService {
    @Autowired
    AutoRepository autoRepository;
    @Override
    public Auto getAutoFromId(int id) {
        return autoRepository.getAutoFromId(id);
    }
}
