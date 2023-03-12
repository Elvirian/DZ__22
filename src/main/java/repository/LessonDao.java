package repository;

import entities.Homework;
import entities.Lesson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDao implements LessonsRepository{
    private final Connection connection;

    public LessonDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addLesson(Lesson lesson) {
        PreparedStatement preparedStatement;
        try {
            String insertHomework = "INSERT INTO homework (name, description) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(insertHomework);
            preparedStatement.setString(1, lesson.getHomework().getName());
            preparedStatement.setString(2, lesson.getHomework().getDescription());
            preparedStatement.executeUpdate();
            Integer idHomework = getLastInsertId();

            String insertLesson = "INSERT INTO lesson (name, homework_id) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(insertLesson);
            preparedStatement.setString(1, lesson.getName());
            preparedStatement.setInt(2, idHomework);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private int getLastInsertId() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id FROM homework;");
        Integer id = null ;
        while (resultSet.next()) {
            id = resultSet.getInt("id");
        }
        statement.close();
        return id;
    }


    @Override
    public void deleteLesson(Lesson lesson) {
        List<Lesson> lessons = getAllLessons();
        for (Lesson l: lessons) {
            if (l.equals(lesson)) {
                deleteLesson(l.getId());
                return;
            }
        }
    }

    @Override
    public void deleteLesson(Integer id) {
        String sql = "DELETE FROM lesson " +
                "WHERE id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Lesson> getAllLessons() {
        List<Lesson> lessons = new ArrayList<>();
        ResultSet resultSet;

        String sql = "SELECT * FROM lesson " +
                "JOIN homework " +
                "ON homework.id = lesson.homework_id;";
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
            resultSet = statement.getResultSet();

            while (resultSet.next()) {
                Homework homework = new Homework(resultSet.getInt("homework.id"),
                        resultSet.getString("homework.name"),
                        resultSet.getString("homework.description"));

                Lesson lesson = new Lesson(resultSet.getInt("lesson.id"),
                        resultSet.getString("lesson.name"), homework);

                lessons.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    @Override
    public Lesson getLesson(Integer id) {
        ResultSet resultSet;
        Lesson lesson;
        String sql = "SELECT * FROM lesson " +
                "JOIN homework " +
                "ON homework.id = lesson.homework_id " +
                "WHERE lesson.id= ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            resultSet.next();

            lesson = new Lesson(resultSet.getInt("lesson.id"), resultSet.getString("lesson.name"),
                    new Homework(resultSet.getInt("homework.id"),
                            resultSet.getString("homework.name"),
                            resultSet.getString("homework.description")));
            return lesson;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}