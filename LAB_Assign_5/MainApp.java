import java.util.*;
import model.Student;
import service.StudentManager;
import service.StudentNotFoundException;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManager manager = new StudentManager();
        String filename = "students.txt";

        // Load existing records and show a random-read preview
        manager.loadFromFile(filename);
        util.FileUtil.randomRead(filename);

        while (true) {
            System.out.println("\n===== Capstone Student Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search by Name");
            System.out.println("4. Delete by Name");
            System.out.println("5. Sort by Marks");
            System.out.println("6. Save and Exit");
            System.out.print("Enter choice: ");

            int ch;
            try {
                ch = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid choice.");
                continue;
            }

            try {
                switch (ch) {
                    case 1:
                        Student s = new Student();
                        s.inputDetails(sc);
                        try { manager.addStudent(s); }
                        catch (Exception ex) { System.out.println(ex.getMessage()); }
                        break;

                    case 2:
                        manager.viewAllStudents();
                        break;

                    case 3:
                        System.out.print("Enter name to search: ");
                        String name = sc.nextLine().trim();
                        try {
                            Student found = manager.searchStudentByName(name);
                            System.out.println("Student Info:");
                            found.displayInfo();
                        } catch (StudentNotFoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                        break;

                    case 4:
                        System.out.print("Enter name to delete: ");
                        String del = sc.nextLine().trim();
                        try { manager.deleteStudent(del); }
                        catch (StudentNotFoundException ex) { System.out.println(ex.getMessage()); }
                        break;

                    case 5:
                        manager.sortByMarksDescending();
                        manager.viewAllStudents();
                        break;

                    case 6:
                        manager.saveToFile(filename);
                        manager.shutdown();
                        sc.close();
                        System.out.println("Saved and exiting.");
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
