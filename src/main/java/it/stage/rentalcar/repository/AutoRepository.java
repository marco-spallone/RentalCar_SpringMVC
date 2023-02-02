package it.stage.rentalcar.repository;

import it.stage.rentalcar.domain.Auto;

public interface AutoRepository {
    Auto getAutoFromId(int id);
}
