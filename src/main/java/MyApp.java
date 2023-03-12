import entities.Homework;
import entities.Lesson;
import repository.LessonDao;
import repository.LessonsRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyApp {
    public final static String url = "jdbc:mysql://localhost:3306/MySQL";
    public final static String username = "root";
    public final static String password = "12345";


    public static void main(String[] args) {

        Connection connection = DataBaseConnection.getConnection(url, username, password);
        LessonsRepository repository = new LessonDao(connection);


        createTable(connection);

        List<Lesson> lessons = createLessonList();


        for (Lesson lesson : lessons) {
            repository.addLesson(lesson);
        }


        List<Lesson> allLessons = repository.getAllLessons();
        allLessons.forEach(System.out::println);

        //Отримання уроку за id
        System.out.println(repository.getLesson(3));

        //Видалення уроку за вказаним id
        repository.deleteLesson(4);


        // Видалення уроку
        Lesson lesson = new Lesson("Lesson1",
                new Homework("Homework1", "11:10"));
        repository.deleteLesson(lesson);


        DataBaseConnection.close(connection);
    }

    public static void createTable(Connection connection) {
        if (connection != null) {
            String homeworkTableQuery = "CREATE TABLE homework (" +
                    " id INT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                    " name VARCHAR(999) NOT NULL," +
                    " description TEXT);";

            String lessonTableQuery = "CREATE TABLE lesson (" +
                    " id INT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                    " name VARCHAR(999) NOT NULL," +
                    " homework_id INT REFERENCES homework(id) ON DELETE CASCADE);";

            try {
                Statement statement = connection.createStatement();

                boolean homework = false, lesson = false;
                ResultSet resultSet = statement.executeQuery("SHOW TABLES;");
                while (resultSet.next()) {
                    String string = resultSet.getString(1);
                    if (string.equals("homework")) homework = true;
                    if (string.equals("lesson")) lesson = true;
                }
                if (!homework) statement.executeLargeUpdate(homeworkTableQuery);
                if (!lesson) statement.executeLargeUpdate(lessonTableQuery);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static List<Lesson> createLessonList() {
        List<Lesson> lessons = new ArrayList<>();

        Lesson lesson1 = new Lesson("Lesson1",
                new Homework("Homework1", "11:10"));

        Lesson lesson2 = new Lesson("Lesson2",
                new Homework("Homework2", "12:15"));


        lessons.add(lesson1);
        lessons.add(lesson2);

        return lessons;
    }
}