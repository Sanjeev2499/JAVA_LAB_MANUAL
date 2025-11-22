package service;

import java.util.HashMap;
import java.util.Map;
import model.Student;

public class StudentManager implements RecordActions {

    private Map<Integer, Student> studentMap = new HashMap<>();

    @Override
    public void addStudent(Student s) {
        if (studentMap.containsKey(s.getRollNo())) {
            System.out.println("Duplicate roll number! Cannot add student.");
            return;
        }
        studentMap.put(s.getRollNo(), s);
        System.out.println("Student added successfully.");
    }

    @Override
    public void deleteStudent(int rollNo) {
        if (studentMap.remove(rollNo) != null)
            System.out.println("Student removed.");
        else
            System.out.println("Student not found.");
    }

    @Override
    public void updateStudent(int rollNo, Student updated) {
        if (studentMap.containsKey(rollNo)) {
            studentMap.put(rollNo, updated);
            System.out.println("Student updated.");
        } else {
            System.out.println("Student not found.");
        }
    }

    @Override
    public Student searchStudent(int rollNo) {
        return studentMap.get(rollNo);
    }

    @Override
    public void viewAllStudents() {
        if (studentMap.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        for (Student s : studentMap.values()) {
            s.displayInfo();
        }
    }
}
