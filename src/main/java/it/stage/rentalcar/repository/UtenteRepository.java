package it.stage.rentalcar.repository;

import it.stage.rentalcar.domain.Utente;

import java.util.List;

public interface UtenteRepository {
    List<Utente> getCustomers(boolean isAdmin);
    Utente getUserFromId(int id);
    Utente getUserFromUsername(String username);
    List<Utente> filter(String field, String value);
    void insOrUpCustomer(Utente utente);
    void delCustomer(int id);
}
