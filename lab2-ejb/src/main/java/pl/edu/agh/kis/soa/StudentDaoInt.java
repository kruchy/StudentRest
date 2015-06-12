package pl.edu.agh.kis.soa;

/**
 * Created by krzysiek on 6/11/15.
 */
public interface StudentDaoInt  {

    void saveStudent(Student s);
    Student getStudent(int albumNo);
    void updateStudent(int albumNo,Student s);

}
