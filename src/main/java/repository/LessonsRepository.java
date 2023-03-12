package repository;

import entities.Lesson;

import java.util.List;

public interface LessonsRepository {

    void addLesson(Lesson lesson);
    void deleteLesson(Lesson lesson);

    void deleteLesson(Integer id);
    List<Lesson> getAllLessons();
    Lesson getLesson(Integer id);

}