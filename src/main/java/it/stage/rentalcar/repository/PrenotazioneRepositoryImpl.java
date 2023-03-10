package it.stage.rentalcar.repository;

import it.stage.rentalcar.config.HibernateUtil;
import it.stage.rentalcar.domain.Prenotazione;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import javax.persistence.criteria.*;
import javax.persistence.criteria.CriteriaQuery;
import java.text.ParseException;
import java.time.LocalDate;
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
    public List<Prenotazione> getReservationsBetweenDates(LocalDate inizio, LocalDate fine) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Prenotazione> criteria = builder.createQuery(Prenotazione.class);
        Root<Prenotazione> root = criteria.from(Prenotazione.class);
        Predicate p1 = builder.between(root.get("dataInizio"), inizio, fine);
        Predicate p2 = builder.between(root.get("dataFine"), inizio, fine);
        Predicate p3 = builder.and(builder.lessThanOrEqualTo(root.get("dataInizio"), inizio), builder.greaterThanOrEqualTo(root.get("dataFine"), fine));
        criteria.select(root).where(builder.or(p1, p2, p3));
        return session.createQuery(criteria).list();
    }

    @Override
    public List<Prenotazione> filter(String field, String value) throws ParseException {
        if(field.equals("auto.targa")){
            try(Session session= HibernateUtil.getSessionFactory().openSession()){
                return session.createQuery("SELECT a FROM Prenotazione a WHERE a."+field+"=:value", Prenotazione.class)
                        .setParameter("value", value).list();
            } catch (Exception e){
                System.out.println(e);
            }
        } else {
            try(Session session= HibernateUtil.getSessionFactory().openSession()){
                return session.createQuery("SELECT a FROM Prenotazione a WHERE a."+field+"=:filter", Prenotazione.class)
                        .setParameter("filter", LocalDate.parse(value)).list();
            } catch (Exception e){
                System.out.println(e);
            }
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
    public void updateStatus(boolean valid, int id) {
        try(Session session=HibernateUtil.getSessionFactory().openSession()){
            Transaction txn = session.beginTransaction();
            if(valid){
                session.createQuery("UPDATE Prenotazione p SET confermata=:valid WHERE p.id=:id").setParameter("valid", valid)
                        .setParameter("id", id).executeUpdate();
            } else {
                session.createQuery("DELETE FROM Prenotazione p WHERE p.id=:id").setParameter("id", id).executeUpdate();
            }
            txn.commit();
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
