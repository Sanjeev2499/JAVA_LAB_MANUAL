import java.util.ArrayList;
import java.util.Scanner;

// Base class
class Person {
    protected String name;
}

// Student class inheriting Person
class Student extends Person {
    private int rollNo;
    private String course;
    private double marks;
    private char grade;

    public Student() {}

    public Student(int rollNo, String name, String course, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.course = course;
        this.marks = marks;
        calculateGrade();
    }

    public void inputDetails(Scanner sc) {
        System.out.print("Enter Roll No: ");
        rollNo = Integer.parseInt(sc.nextLine());

        System.out.print("Enter Name: ");
        name = sc.nextLine();

        System.out.print("Enter Course: ");
        course = sc.nextLine();

        while (true) {
            System.out.print("Enter Marks: ");
            try {
                marks = Double.parseDouble(sc.nextLine());
                if (marks >= 0 && marks <= 100) break;
                System.out.println("Marks must be between 0 and 100.");
            } catch (Exception e) {
                System.out.println("Enter valid numeric marks.");
            }
        }

        calculateGrade();
    }

    public void calculateGrade() {
        if (marks >= 90) grade = 'A';
        else if (marks >= 75) grade = 'B';
        else if (marks >= 60) grade = 'C';
        else grade = 'D';
    }

    public void displayDetails() {
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name   : " + name);
        System.out.println("Course : " + course);
        System.out.println("Marks  : " + marks);
        System.out.println("Grade  : " + grade);
        System.out.println("---------------------------------");
    }
}

// Main class — must be public
public class StudentRecordApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> list = new ArrayList<>();

        while (true) {
            System.out.println("\n===== Student Record Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid choice.");
                continue;
            }

            switch (choice) {
                case 1:
                    Student s = new Student();
                    s.inputDetails(sc);
                    list.add(s);
                    break;

                case 2:
                    if (list.isEmpty()) {
                        System.out.println("No student records available.");
                    } else {
                        for (Student st : list) st.displayDetails();
                    }
                    break;

                case 3:
                    System.out.println("Exiting the application. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
