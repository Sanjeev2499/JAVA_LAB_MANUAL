package model;

public class Student extends Person {
    private int rollNo;
    private String course;
    private double marks;
    private char grade;

    public Student() {}

    public Student(int rollNo, String name, String email, String course, double marks) {
        super(name, email);
        this.rollNo = rollNo;
        this.course = course;
        this.marks = marks;
        calculateGrade();
    }

    public void calculateGrade() {
        if (marks >= 90) grade = 'A';
        else if (marks >= 75) grade = 'B';
        else if (marks >= 60) grade = 'C';
        else grade = 'D';
    }

    // Overloaded method
    public void displayInfo(boolean showMarks) {
        System.out.println("Student Info:");
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        if (showMarks) {
            System.out.println("Course: " + course);
            System.out.println("Marks: " + marks);
            System.out.println("Grade: " + grade);
        }
        System.out.println("------------------------");
    }

    @Override
    public void displayInfo() {
        displayInfo(true);
    }

    public int getRollNo() {
        return rollNo;
    }
}
