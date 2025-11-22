import java.util.*;
import java.util.concurrent.*;

// Custom exception for missing student records
class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String message) {
        super(message);
    }
}

// Runnable used for simulating loading using threads
class Loader implements Runnable {
    private String message;
    private int durationMs;

    public Loader(String message, int durationMs) {
        this.message = message;
        this.durationMs = durationMs;
    }

    @Override
    public void run() {
        try {
            System.out.print(message);
            for (int i = 0; i < durationMs / 300; i++) {
                Thread.sleep(300);
                System.out.print(".");
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Student class with wrapper types and overloaded display methods
class Student {
    private Integer rollNo;
    private String name;
    private String email;
    private String course;
    private Double marks;
    private char grade;

    public Student(Integer rollNo, String name, String email, String course, Double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
        this.course = course;
        this.marks = marks;
        calculateGrade();
    }

    public Integer getRollNo() { return rollNo; }

    private void calculateGrade() {
        double m = marks;
        if (m >= 90) grade = 'A';
        else if (m >= 75) grade = 'B';
        else if (m >= 60) grade = 'C';
        else grade = 'D';
    }

    public void displayInfo() {
        displayInfo(true);
    }

    public void displayInfo(boolean showMarks) {
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name   : " + name);
        System.out.println("Email  : " + email);
        System.out.println("Course : " + course);
        if (showMarks) {
            System.out.println("Marks  : " + marks);
            System.out.println("Grade  : " + grade);
        }
        System.out.println("---------------------------");
    }
}

// CRUD operations interface
interface RecordActions {
    void addStudent(Student s);
    void deleteStudent(Integer rollNo) throws StudentNotFoundException;
    void updateStudent(Integer rollNo, Student updated) throws StudentNotFoundException;
    Student searchStudent(Integer rollNo) throws StudentNotFoundException;
    void viewAllStudents();
}

// Student manager implementing CRUD + exception handling + multithreading
class StudentManager implements RecordActions {
    private Map<Integer, Student> studentMap = new HashMap<>();
    private ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public void addStudent(Student s) {
        if (studentMap.containsKey(s.getRollNo())) {
            System.out.println("Duplicate roll number.");
            return;
        }

        runLoader("Loading", 900);
        studentMap.put(s.getRollNo(), s);
        System.out.println("Student added.");
    }

    @Override
    public void deleteStudent(Integer rollNo) throws StudentNotFoundException {
        if (studentMap.remove(rollNo) == null)
            throw new StudentNotFoundException("Student not found.");
        System.out.println("Student deleted.");
    }

    @Override
    public void updateStudent(Integer rollNo, Student updated) throws StudentNotFoundException {
        if (!studentMap.containsKey(rollNo))
            throw new StudentNotFoundException("Student not found.");

        runLoader("Updating", 600);
        studentMap.put(rollNo, updated);
        System.out.println("Student updated.");
    }

    @Override
    public Student searchStudent(Integer rollNo) throws StudentNotFoundException {
        Student s = studentMap.get(rollNo);
        if (s == null)
            throw new StudentNotFoundException("Student not found.");
        return s;
    }

    @Override
    public void viewAllStudents() {
        if (studentMap.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        studentMap.values().forEach(Student::displayInfo);
    }

    private void runLoader(String msg, int ms) {
        try {
            executor.submit(new Loader(msg, ms)).get();
        } catch (Exception ignored) {}
    }

    public void close() {
        executor.shutdownNow();
    }
}

// Main class with exception handling and wrapper parsing
public class LAB_Assign_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManager manager = new StudentManager();

        while (true) {
            System.out.println("\n===== Enhanced Student Management =====");
            System.out.println("1. Add Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Update Student");
            System.out.println("4. Search Student");
            System.out.println("5. View All Students");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            Integer choice;
            try {
                choice = Integer.valueOf(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid choice.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Roll No (Integer): ");
                        Integer roll = Integer.valueOf(sc.nextLine());

                        System.out.print("Enter Name: ");
                        String n = sc.nextLine();

                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();

                        System.out.print("Enter Course: ");
                        String c = sc.nextLine();

                        System.out.print("Enter Marks: ");
                        Double m = Double.valueOf(sc.nextLine());

                        if (m < 0 || m > 100) throw new IllegalArgumentException("Marks must be 0–100.");

                        manager.addStudent(new Student(roll, n, email, c, m));
                        break;

                    case 2:
                        System.out.print("Enter Roll No to delete: ");
                        manager.deleteStudent(Integer.valueOf(sc.nextLine()));
                        break;

                    case 3:
                        System.out.print("Enter Roll No to update: ");
                        Integer uroll = Integer.valueOf(sc.nextLine());

                        System.out.print("Enter Name: ");
                        String un = sc.nextLine();

                        System.out.print("Enter Email: ");
                        String ue = sc.nextLine();

                        System.out.print("Enter Course: ");
                        String uc = sc.nextLine();

                        System.out.print("Enter Marks: ");
                        Double um = Double.valueOf(sc.nextLine());

                        if (um < 0 || um > 100) throw new IllegalArgumentException("Marks must be 0–100.");

                        manager.updateStudent(uroll, new Student(uroll, un, ue, uc, um));
                        break;

                    case 4:
                        System.out.print("Enter Roll No to search: ");
                        manager.searchStudent(Integer.valueOf(sc.nextLine())).displayInfo();
                        break;

                    case 5:
                        manager.viewAllStudents();
                        break;

                    case 6:
                        System.out.println("Program execution completed.");
                        manager.close();
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                System.out.println("Operation completed.");
            }
        }
    }
}
