package model;

import java.util.Scanner;

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

    public int getRollNo() { return rollNo; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCourse() { return course; }
    public double getMarks() { return marks; }

    // Read student details from Scanner
    public void inputDetails(Scanner sc) {
        System.out.print("Enter Roll No: ");
        rollNo = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Enter Name: ");
        name = sc.nextLine().trim();

        System.out.print("Enter Email: ");
        email = sc.nextLine().trim();

        System.out.print("Enter Course: ");
        course = sc.nextLine().trim();

        System.out.print("Enter Marks: ");
        marks = Double.parseDouble(sc.nextLine().trim());

        calculateGrade();
    }

    // Determine grade from marks
    public void calculateGrade() {
        if (marks >= 90) grade = 'A';
        else if (marks >= 75) grade = 'B';
        else if (marks >= 60) grade = 'C';
        else grade = 'D';
    }

    @Override
    public void displayInfo() {
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name   : " + name);
        System.out.println("Email  : " + email);
        System.out.println("Course : " + course);
        System.out.println("Marks  : " + marks);
        System.out.println("Grade  : " + grade);
        System.out.println("------------------------");
    }
}
