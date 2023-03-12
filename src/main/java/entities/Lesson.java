package entities;

import java.util.Objects;

public class Lesson {
    private Integer id;
    private String name;
    private Homework homework;


    public Lesson() {}

    public Lesson(String name, Homework homework) {
        this.name = name;
        this.homework = homework;
    }

    public Lesson(Integer id, String name, Homework homework) {
        this.id = id;
        this.name = name;
        this.homework = homework;
    }

    public Homework getHomework() {
        return homework;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", homework=" + homework +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(name, lesson.name) && Objects.equals(homework.getName(), lesson.homework.getName())
                && Objects.equals(homework.getDescription(), lesson.homework.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, homework);
    }
}