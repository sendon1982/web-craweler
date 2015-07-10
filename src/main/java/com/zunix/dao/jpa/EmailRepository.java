package com.zunix.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.zunix.entity.Email;

@Repository
public class EmailRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Insert Email into DB
     */
    @Transactional
    public void insert(Email email) {
        entityManager.persist(email);
    }

    public void insertList(List<Email> emails) {
        for (Email email : emails) {
            insert(email);
        }
    }

    public Email getEmailById(Long id) {
        return entityManager.find(Email.class, id);
    }

    public Email getEmailByEmail(Email email) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Query for a List of objects.
        CriteriaQuery<Email> cq = cb.createQuery(Email.class);
        Root<Email> e = cq.from(Email.class);
        cq.where(cb.equal(e.get("email"), email.getEmail()));

        if(entityManager.createQuery(cq).getResultList().size() == 0)
        {
            return Email.newEmptyEmail();
        }
        
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Transactional
    public void delete(Email email) {
        entityManager.remove(entityManager.merge(email));
    }
}
