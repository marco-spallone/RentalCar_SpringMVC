package it.stage.rentalcar.repository;

import it.stage.rentalcar.config.HibernateUtil;
import it.stage.rentalcar.domain.Utente;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
    public Utente getUserFromId(int id) {
        try(Session session=HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT a FROM Utente a WHERE id=:id", Utente.class).setParameter("id", id).getSingleResult();
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<Utente> filter(String field, String value) {
        try(Session session=HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT a FROM Utente a WHERE a."+field+"=:value", Utente.class).setParameter("value", value).list();
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
            Transaction txn = session.beginTransaction();
            session.createQuery("DELETE Utente u WHERE u.id=:id").setParameter("id", id).executeUpdate();
            txn.commit();
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
