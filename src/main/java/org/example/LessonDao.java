package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDao {
    public void addLesson(Lesson lesson) {
        String query = "INSERT INTO Lesson (name, homework_id) VALUES (?, ?)";
        int nameIndex = 1;
        int homeworkIdIndex = 2;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(nameIndex, lesson.getName());

            if (lesson.getHomework() != null) {
                statement.setInt(homeworkIdIndex, lesson.getHomework().getId());
            } else {
                statement.setNull(homeworkIdIndex, java.sql.Types.INTEGER);
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding lesson: " + e.getMessage());
        }
    }


    public void deleteLesson(int lessonId) {
        String query = "DELETE FROM Lesson WHERE id = ?";
        int idIndex = 1;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(idIndex, lessonId);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting lesson: " + e.getMessage());
        }
    }


    public List<Lesson> getAllLessons() {
        List<Lesson> lessons = new ArrayList<>();
        String query = "SELECT Lesson.id, Lesson.name, Homework.id, Homework.name, Homework.description " +
                "FROM Lesson " +
                "JOIN Homework ON Lesson.homework_id = Homework.id";

        try (Connection connection = DataBaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int lessonIdIndex = 1;
                int lessonNameIndex = 2;
                int homeworkIdIndex = 3;
                int homeworkNameIndex = 4;
                int homeworkDescriptionIndex = 5;

                int lessonId = resultSet.getInt(lessonIdIndex);
                String lessonName = resultSet.getString(lessonNameIndex);
                int homeworkId = resultSet.getInt(homeworkIdIndex);
                String homeworkName = resultSet.getString(homeworkNameIndex);
                String homeworkDescription = resultSet.getString(homeworkDescriptionIndex);

                Homework homework = new Homework(homeworkId, homeworkName, homeworkDescription);
                Lesson lesson = new Lesson(lessonId, lessonName, homework);

                lessons.add(lesson);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving lessons: " + e.getMessage());
        }

        return lessons;
    }


    public Lesson getLessonById(int lessonId) {
        String query = "SELECT Lesson.id, Lesson.name, Homework.id, Homework.name, Homework.description " +
                "FROM Lesson " +
                "JOIN Homework ON Lesson.homework_id = Homework.id " +
                "WHERE Lesson.id = ?";
        Lesson lesson = null;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, lessonId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int lessonIdIndex = 1;
                    int lessonNameIndex = 2;
                    int homeworkIdIndex = 3;
                    int homeworkNameIndex = 4;
                    int homeworkDescriptionIndex = 5;

                    int lessonIdResult = resultSet.getInt(lessonIdIndex);
                    String lessonName = resultSet.getString(lessonNameIndex);
                    int homeworkId = resultSet.getInt(homeworkIdIndex);
                    String homeworkName = resultSet.getString(homeworkNameIndex);
                    String homeworkDescription = resultSet.getString(homeworkDescriptionIndex);

                    Homework homework = new Homework(homeworkId, homeworkName, homeworkDescription);
                    lesson = new Lesson(lessonIdResult, lessonName, homework);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving lesson: " + e.getMessage());
        }

        return lesson;
    }
}
