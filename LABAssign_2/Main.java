import java.util.Scanner;
import model.Student;
import service.StudentManager;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManager manager = new StudentManager();

        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Update Student");
            System.out.println("4. Search Student");
            System.out.println("5. View All Students");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Roll No: ");
                    int r = Integer.parseInt(sc.nextLine());

                    System.out.print("Name: ");
                    String n = sc.nextLine();

                    System.out.print("Email: ");
                    String e = sc.nextLine();

                    System.out.print("Course: ");
                    String c = sc.nextLine();

                    System.out.print("Marks: ");
                    double m = Double.parseDouble(sc.nextLine());

                    manager.addStudent(new Student(r, n, e, c, m));
                    break;

                case 2:
                    System.out.print("Enter Roll No to delete: ");
                    manager.deleteStudent(Integer.parseInt(sc.nextLine()));
                    break;

                case 3:
                    System.out.print("Enter Roll No to update: ");
                    int ur = Integer.parseInt(sc.nextLine());
                    System.out.println("Enter new student details:");
                    System.out.print("Name: ");
                    String nn = sc.nextLine();
                    System.out.print("Email: ");
                    String em = sc.nextLine();
                    System.out.print("Course: ");
                    String co = sc.nextLine();
                    System.out.print("Marks: ");
                    double mk = Double.parseDouble(sc.nextLine());

                    manager.updateStudent(ur, new Student(ur, nn, em, co, mk));
                    break;

                case 4:
                    System.out.print("Enter Roll No to search: ");
                    Student s = manager.searchStudent(Integer.parseInt(sc.nextLine()));
                    if (s != null) s.displayInfo();
                    else System.out.println("Student not found.");
                    break;

                case 5:
                    manager.viewAllStudents();
                    break;

                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
