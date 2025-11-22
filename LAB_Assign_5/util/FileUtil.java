package util;

import model.Student;
import java.io.*;
import java.util.*;

public class FileUtil {

    // Read students from CSV file: roll,name,email,course,marks
    public static List<Student> readStudents(String filename) {
        List<Student> list = new ArrayList<>();
        File f = new File(filename);
        if (!f.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length < 5) continue;
                int roll = Integer.parseInt(p[0].trim());
                String name = p[1].trim();
                String email = p[2].trim();
                String course = p[3].trim();
                double marks = Double.parseDouble(p[4].trim());
                list.add(new Student(roll, name, email, course, marks));
            }
        } catch (Exception e) {
            System.out.println("Read error: " + e.getMessage());
        }
        return list;
    }

    // Write students to CSV file
    public static void writeStudents(String filename, List<Student> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Student s : list) {
                bw.write(s.getRollNo() + "," + s.getName() + "," + s.getEmail() + "," + s.getCourse() + "," + s.getMarks());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Write error: " + e.getMessage());
        }
    }

    // Demonstrate RandomAccessFile reading
    public static void randomRead(String filename) {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
            if (raf.length() > 0) {
                raf.seek(0);
                System.out.println("Random first line: " + raf.readLine());
            }
        } catch (Exception e) {
            System.out.println("RandomRead error: " + e.getMessage());
        }
    }
}
