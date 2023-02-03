package it.stage.rentalcar.repository;

import it.stage.rentalcar.config.HibernateUtil;
import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.domain.Utente;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class PrenotazioneRepositoryImpl implements PrenotazioneRepository {
    @Override
    public List<Prenotazione> getReservationsForUser(int id) {
        try(Session session= HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT a FROM Prenotazione a WHERE a.utente.id=:id", Prenotazione.class)
                    .setParameter("id", id).list();
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Prenotazione getReservationFromId(int id) {
        try(Session session= HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT a FROM Prenotazione a WHERE a.id=:id", Prenotazione.class)
                    .setParameter("id", id).getSingleResult();
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<Prenotazione> getReservationsBetweenDates(Date inizio, Date fine) {
        try(Session session= HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT a FROM Prenotazione a WHERE (a.dataInizio BETWEEN :inizio AND :fine) OR (a.dataFine BETWEEN " +
                            ":inizio AND :fine) OR (a.dataInizio<=:inizio AND a.dataFine>=:fine)",
                    Prenotazione.class).setParameter("inizio", inizio).setParameter("fine", fine).list();
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

    @Override
    public void delReservation(int id) {
        try(Session session=HibernateUtil.getSessionFactory().openSession()){
            Transaction txn = session.beginTransaction();
            session.createQuery("DELETE Prenotazione p WHERE p.id=:id").setParameter("id", id).executeUpdate();
            txn.commit();
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
