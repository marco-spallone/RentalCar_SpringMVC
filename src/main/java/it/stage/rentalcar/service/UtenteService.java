package it.stage.rentalcar.service;

import it.stage.rentalcar.domain.Utente;

import java.util.List;

public interface UtenteService {
    List<Utente> getCustomers(boolean isAdmin);
    void insOrUpCustomer(Utente utente);
    void delCustomer(int id);
}
