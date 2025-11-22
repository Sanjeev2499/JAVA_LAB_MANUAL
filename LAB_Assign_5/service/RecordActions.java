package service;

import model.Student;
import java.util.List;

public interface RecordActions {
    void addStudent(Student s) throws Exception;
    void deleteStudent(String name) throws StudentNotFoundException;
    void updateStudent(int rollNo, Student updated) throws StudentNotFoundException;
    Student searchStudentByName(String name) throws StudentNotFoundException;
    void viewAllStudents();
    void sortByMarksDescending();
    void loadFromFile(String filename);
    void saveToFile(String filename);
}
