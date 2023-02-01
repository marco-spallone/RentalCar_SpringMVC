package it.stage.rentalcar.service;

import it.stage.rentalcar.config.HibernateUtil;
import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.repository.UtenteRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteServiceImpl implements UtenteService {

    @Autowired
    UtenteRepository utenteRepository;

    @Override
    public List<Utente> getCustomers(boolean isAdmin) {
        return utenteRepository.getCustomers(isAdmin);
    }
    public void insOrUpCustomer(Utente utente) {
        utenteRepository.insOrUpCustomer(utente);
    }
    @Override
    public void delCustomer(int id) {
        utenteRepository.delCustomer(id);
    }
}
