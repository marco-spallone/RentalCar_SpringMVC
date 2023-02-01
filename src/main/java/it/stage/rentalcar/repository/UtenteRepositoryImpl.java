package it.stage.rentalcar.repository;

import it.stage.rentalcar.config.HibernateUtil;
import it.stage.rentalcar.domain.Utente;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UtenteRepositoryImpl implements UtenteRepository{
    @Override
    public List<Utente> getCustomers(boolean isAdmin) {
        try(Session session=HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT a FROM Utente a WHERE isAdmin=:isAdmin", Utente.class).setParameter("isAdmin", isAdmin).list();
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public void insOrUpCustomer(Utente utente) {
        try(Session session=HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            session.saveOrUpdate(utente);
            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void delCustomer(int id) {
        try(Session session=HibernateUtil.getSessionFactory().openSession()){
            session.createQuery("DELETE Utente u WHERE u.id=:id").setParameter("id", id).executeUpdate();
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
