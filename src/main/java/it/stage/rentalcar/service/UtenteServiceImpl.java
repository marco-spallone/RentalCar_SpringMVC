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
    public Utente getUserFromUsername(String username) {
        return utenteRepository.getUserFromUsername(username);
    }

    @Override
    public List<Utente> filter(String field, String value) {
        return utenteRepository.filter(field, value);
    }
    public void insOrUpCustomer(Utente utente) throws Exception {
        boolean validUsername = true;
        List<Utente> customers = utenteRepository.getCustomers(false);
        if(utente.getUsername().equals("ADMIN")){
            throw new Exception("L'username ADMIN non può essere utilizzato");
        }
        for (Utente u:customers) {
            if(u.getUsername().equals(utente.getUsername())){
                validUsername=false;
            }
        }
        if(!validUsername){
            throw new Exception("Username già esistente");
        }
        utenteRepository.insOrUpCustomer(utente);
    }
    @Override
    public void delCustomer(int id) {
        utenteRepository.delCustomer(id);
    }
}
