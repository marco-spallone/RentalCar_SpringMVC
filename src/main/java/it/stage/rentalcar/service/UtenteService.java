package it.stage.rentalcar.service;

import it.stage.rentalcar.domain.Utente;

import java.util.List;

public interface UtenteService {
    List<Utente> getCustomers(boolean isAdmin);
    Utente getUserFromId(int id);
    Utente getUserFromUsername(String username);
    List<Utente> filter(String field, String value);
    void insOrUpCustomer(Utente utente, String action) throws Exception;
    void delCustomer(int id);
}
