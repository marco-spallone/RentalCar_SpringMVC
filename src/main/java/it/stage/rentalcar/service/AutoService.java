package it.stage.rentalcar.service;

import it.stage.rentalcar.domain.Auto;
import org.springframework.stereotype.Service;

public interface AutoService {
    Auto getAutoFromId(int id);
}
