package it.stage.rentalcar.repository;

import it.stage.rentalcar.config.HibernateUtil;
import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.domain.Utente;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrenotazioneRepositoryImpl implements PrenotazioneRepository {
    @Override
    public List<Prenotazione> reservationsList(int id) {
        try(Session session= HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT a FROM Prenotazione a WHERE a.utente.id=:id", Prenotazione.class)
                    .setParameter("id", id).list();
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public void insOrUpReservation(Prenotazione p) {
        try(Session session=HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            session.saveOrUpdate(p);
            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
