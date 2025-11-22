import java.io.*;
import java.util.*;

// Student class with Comparable (sort by name)
class Student implements Comparable<Student> {
    int rollNo;
    String name;
    String email;
    String course;
    double marks;

    public Student(int rollNo, String name, String email, String course, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
        this.course = course;
        this.marks = marks;
    }

    @Override
    public int compareTo(Student other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public String toString() {
        return "Roll No: " + rollNo +
                "\nName: " + name +
                "\nEmail: " + email +
                "\nCourse: " + course +
                "\nMarks: " + marks + "\n----------------";
    }
}

// Utility class for reading/writing files
class FileUtil {

    public static ArrayList<Student> readStudents(String filename) {
        ArrayList<Student> list = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists())
            return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                int roll = Integer.parseInt(p[0]);
                String name = p[1];
                String email = p[2];
                String course = p[3];
                double marks = Double.parseDouble(p[4]);
                list.add(new Student(roll, name, email, course, marks));
            }

        } catch (Exception e) {
            System.out.println("Error reading file.");
        }
        return list;
    }

    public static void writeStudents(String filename, ArrayList<Student> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Student s : list) {
                bw.write(s.rollNo + "," + s.name + "," + s.email + "," + s.course + "," + s.marks);
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error writing file.");
        }
    }

    public static void readRandomAccess(String filename) {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
            raf.seek(0);
            System.out.println("First Line (RandomAccessFile): " + raf.readLine());
        } catch (Exception e) {
            System.out.println("RandomAccess read error.");
        }
    }
}

// Manages students using ArrayList & HashMap
class StudentManager {

    ArrayList<Student> list = new ArrayList<>();
    HashMap<String, Student> mapByName = new HashMap<>();

    public void loadFromFile(String f) {
        list = FileUtil.readStudents(f);
        for (Student s : list) mapByName.put(s.name.toLowerCase(), s);
    }

    public void saveToFile(String f) {
        FileUtil.writeStudents(f, list);
    }

    public void displayFileInfo(String f) {
        File file = new File(f);
        if (file.exists()) {
            System.out.println("File Name: " + file.getName());
            System.out.println("Size: " + file.length() + " bytes");
            System.out.println("----------------");
        }
    }

    public void addStudent(Student s) {
        list.add(s);
        mapByName.put(s.name.toLowerCase(), s);
    }

    public void viewStudents() {
        Iterator<Student> it = list.iterator();
        while (it.hasNext()) System.out.println(it.next());
    }

    public Student searchByName(String name) {
        return mapByName.get(name.toLowerCase());
    }

    public void deleteByName(String name) {
        Student s = mapByName.remove(name.toLowerCase());
        if (s != null) list.remove(s);
    }

    public void sortByMarks() {
        list.sort(Comparator.comparingDouble(s -> s.marks));
    }
}

public class LAB_Assign_4 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManager manager = new StudentManager();

        String filename = "students.txt";
        manager.loadFromFile(filename);

        System.out.println("Loaded students from file:");
        manager.viewStudents();
        System.out.println();

        FileUtil.readRandomAccess(filename);
        manager.displayFileInfo(filename);

        while (true) {
            System.out.println("\n===== Capstone Student Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search by Name");
            System.out.println("4. Delete by Name");
            System.out.println("5. Sort by Marks");
            System.out.println("6. Save and Exit");
            System.out.print("Enter choice: ");

            int ch = Integer.parseInt(sc.nextLine());

            switch (ch) {

                case 1:
                    System.out.print("Enter Roll No: ");
                    int r = Integer.parseInt(sc.nextLine());

                    System.out.print("Enter Name: ");
                    String n = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String e = sc.nextLine();

                    System.out.print("Enter Course: ");
                    String c = sc.nextLine();

                    System.out.print("Enter Marks: ");
                    double m = Double.parseDouble(sc.nextLine());

                    manager.addStudent(new Student(r, n, e, c, m));
                    break;

                case 2:
                    manager.viewStudents();
                    break;

                case 3:
                    System.out.print("Enter Name: ");
                    String sn = sc.nextLine();
                    Student found = manager.searchByName(sn);
                    if (found != null) System.out.println(found);
                    else System.out.println("Not found.");
                    break;

                case 4:
                    System.out.print("Enter Name: ");
                    manager.deleteByName(sc.nextLine());
                    break;

                case 5:
                    manager.sortByMarks();
                    System.out.println("Sorted Student List by Marks:");
                    manager.viewStudents();
                    break;

                case 6:
                    manager.saveToFile(filename);
                    System.out.println("Saved. Exiting.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
