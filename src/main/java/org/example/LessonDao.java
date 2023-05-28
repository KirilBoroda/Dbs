package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDao {
    public void addHomework(Homework homework) throws SQLException {
        String query = "INSERT INTO Homework (name, description) VALUES (?, ?)";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, homework.getName());
            statement.setString(2, homework.getDescription());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error adding homework: " + e.getMessage());
        }
    }

    public void addLesson(Lesson lesson) throws SQLException {
        String query = "INSERT INTO Lesson (name, homework_id) VALUES (?, ?)";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, lesson.getName());
            statement.setInt(2, lesson.getHomework().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error adding lesson: " + e.getMessage());
        }
    }

    public void deleteLesson(int lessonId) throws SQLException {
        String query = "DELETE FROM Lesson WHERE id = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, lessonId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting lesson: " + e.getMessage());
        }
    }

    public List<Lesson> getAllLessons() throws SQLException {
        List<Lesson> lessons = new ArrayList<>();
        String query = "SELECT Lesson.id, Lesson.name, Homework.id, Homework.name, Homework.description " +
                "FROM Lesson " +
                "JOIN Homework ON Lesson.homework_id = Homework.id";

        try (Connection connection = DataBaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int lessonId = resultSet.getInt(1);
                String lessonName = resultSet.getString(2);
                int homeworkId = resultSet.getInt(3);
                String homeworkName = resultSet.getString(4);
                String homeworkDescription = resultSet.getString(5);

                Homework homework = new Homework(homeworkId, homeworkName, homeworkDescription);
                Lesson lesson = new Lesson(lessonId, lessonName, homework);

                lessons.add(lesson);
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving lessons: " + e.getMessage());
        }

        return lessons;
    }

    public Lesson getLessonById(int lessonId) throws SQLException {
        String query = "SELECT Lesson.id, Lesson.name, Homework.id, Homework.name, Homework.description " +
                "FROM Lesson " +
                "JOIN Homework ON Lesson.homework_id = Homework.id " +
                "WHERE Lesson.id = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, lessonId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int lessonIdResult = resultSet.getInt(1);
                    String lessonName = resultSet.getString(2);
                    int homeworkId = resultSet.getInt(3);
                    String homeworkName = resultSet.getString(4);
                    String homeworkDescription = resultSet.getString(5);

                    Homework homework = new Homework(homeworkId, homeworkName, homeworkDescription);
                    return new Lesson(lessonIdResult, lessonName, homework);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving lesson: " + e.getMessage());
        }

        return null;
    }
}
