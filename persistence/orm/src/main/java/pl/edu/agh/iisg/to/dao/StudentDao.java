package pl.edu.agh.iisg.to.dao;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Student;

import javax.persistence.PersistenceException;

public class StudentDao extends GenericDao<Student> {

    public Optional<Student> create(final String firstName, final String lastName, final int indexNumber) {
    	//TODO - implement
        Student student = new Student(firstName, lastName, indexNumber);
        try{
            this.save(student);
        } catch (PersistenceException pe){
            pe.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(student);
    }

    public Optional<Student> findByIndexNumber(final int indexNumber) {
    	//TODO - implement
        try{
            Student student = currentSession().createQuery("Select s from Student s where s.indexNumber = :indexNumber", Student.class)
                    .setParameter("indexNumber", indexNumber)
                    .getSingleResult();
            return Optional.of(student);
        }catch (PersistenceException pe){
            pe.printStackTrace();
        }
        return Optional.empty();
    }

    public Map<Course, Float> createReport(final Student student) {
        //TODO additional task
        return Collections.emptyMap();
    }

}
