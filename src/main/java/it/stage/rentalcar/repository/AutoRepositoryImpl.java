package it.stage.rentalcar.repository;

import it.stage.rentalcar.config.HibernateUtil;
import it.stage.rentalcar.domain.Auto;
import it.stage.rentalcar.domain.Utente;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class AutoRepositoryImpl implements AutoRepository{
    @Override
    public Auto getAutoFromId(int id) {
        try(Session session=HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("SELECT a FROM Auto a WHERE id=:id", Auto.class).setParameter("id", id).getSingleResult();
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
