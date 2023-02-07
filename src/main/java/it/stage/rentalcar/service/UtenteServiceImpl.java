package it.stage.rentalcar.service;

import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.repository.UtenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteServiceImpl implements UtenteService {

    private final UtenteRepository utenteRepository;

    public UtenteServiceImpl(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Override
    public List<Utente> getCustomers(boolean isAdmin) {
        return utenteRepository.getCustomers(isAdmin);
    }
    @Override
    public Utente getUserFromId(int id){ return utenteRepository.getUserFromId(id); }

    @Override
    public List<Utente> filter(String field, String value) {
        return utenteRepository.filter(field, value);
    }

    public void insOrUpCustomer(Utente utente) {
        utenteRepository.insOrUpCustomer(utente);
    }
    @Override
    public void delCustomer(int id) {
        utenteRepository.delCustomer(id);
    }
}
