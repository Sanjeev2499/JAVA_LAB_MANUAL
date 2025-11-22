package model;

public abstract class Person {
    protected String name;
    protected String email;

    public Person() {}

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Display basic info — implemented by subclasses
    public abstract void displayInfo();
}
