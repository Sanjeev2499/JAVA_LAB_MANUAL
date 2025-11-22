package service;

import model.Student;
import util.FileUtil;
import util.Loader;

import java.util.*;
import java.util.concurrent.*;

public class StudentManager implements RecordActions {
    private final List<Student> list = new ArrayList<>();
    private final Map<Integer, Student> mapByRoll = new HashMap<>();
    private final Map<String, Student> mapByName = new HashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public void addStudent(Student s) throws Exception {
        if (s == null || mapByRoll.containsKey(s.getRollNo())) {
            throw new Exception("Invalid or duplicate roll number.");
        }
        Future<?> f = executor.submit(new Loader("Adding", 600));
        try { f.get(); } catch (Exception ignored) {}
        list.add(s);
        mapByRoll.put(s.getRollNo(), s);
        mapByName.put(s.getName().toLowerCase(), s);
        System.out.println("Student added.");
    }

    @Override
    public void deleteStudent(String name) throws StudentNotFoundException {
        Student s = mapByName.remove(name.toLowerCase());
        if (s == null) throw new StudentNotFoundException("Student not found: " + name);
        list.remove(s);
        mapByRoll.remove(s.getRollNo());
        System.out.println("Student record deleted.");
    }

    @Override
    public void updateStudent(int rollNo, Student updated) throws StudentNotFoundException {
        if (!mapByRoll.containsKey(rollNo)) throw new StudentNotFoundException("Student not found with roll: " + rollNo);
        Student old = mapByRoll.get(rollNo);
        list.remove(old);
        mapByName.remove(old.getName().toLowerCase());
        list.add(updated);
        mapByRoll.put(rollNo, updated);
        mapByName.put(updated.getName().toLowerCase(), updated);
        System.out.println("Student record updated.");
    }

    @Override
    public Student searchStudentByName(String name) throws StudentNotFoundException {
        Student s = mapByName.get(name.toLowerCase());
        if (s == null) throw new StudentNotFoundException("Student not found: " + name);
        return s;
    }

    @Override
    public void viewAllStudents() {
        Iterator<Student> it = list.iterator();
        while (it.hasNext()) it.next().displayInfo();
    }

    @Override
    public void sortByMarksDescending() {
        list.sort((a, b) -> Double.compare(b.getMarks(), a.getMarks()));
        System.out.println("Sorted by marks (descending).");
    }

    @Override
    public void loadFromFile(String filename) {
        List<Student> loaded = FileUtil.readStudents(filename);
        list.clear();
        mapByRoll.clear();
        mapByName.clear();
        for (Student s : loaded) {
            list.add(s);
            mapByRoll.put(s.getRollNo(), s);
            mapByName.put(s.getName().toLowerCase(), s);
        }
        System.out.println("Loaded " + list.size() + " students from file.");
    }

    @Override
    public void saveToFile(String filename) {
        Future<?> f = executor.submit(new Loader("Saving", 600));
        try { f.get(); } catch (Exception ignored) {}
        FileUtil.writeStudents(filename, list);
        System.out.println("Saved students to file.");
    }

    public void shutdown() { executor.shutdownNow(); }
}
