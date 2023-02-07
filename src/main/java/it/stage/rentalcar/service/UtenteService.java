package it.stage.rentalcar.service;

import it.stage.rentalcar.domain.Utente;

import java.util.List;

public interface UtenteService {
    List<Utente> getCustomers(boolean isAdmin);
    Utente getUserFromId(int id);
    List<Utente> filter(String field, String value);
    void insOrUpCustomer(Utente utente);
    void delCustomer(int id);
}
