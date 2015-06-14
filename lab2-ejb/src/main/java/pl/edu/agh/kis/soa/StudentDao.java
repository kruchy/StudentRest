package pl.edu.agh.kis.soa;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by krzysiek on 6/11/15.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Local(StudentDaoInt.class)
public class StudentDao implements StudentDaoInt {
    @PersistenceContext(name="primary")
    private EntityManager em;

    public void saveStudent(Student s)
    {
        em.persist(s);
    }

    public Student getStudent(int albumNo)
    {
        return em.find(Student.class,albumNo);
    }

    public void updateStudent(Student s)
    {
        em.merge(s);
    }

    @Override
    public boolean deleteStudent(int albumNo) {
        Student s = em.find(Student.class, albumNo);
        if (s != null) {
            em.remove(getStudent(albumNo));
            return true;
        } else {
            return false;
        }
    }


    }

