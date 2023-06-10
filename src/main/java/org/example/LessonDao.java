package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDao {
    public void addLesson(Lesson lesson) throws LessonException {
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
            throw new LessonException("Error adding lesson: " + e.getMessage());
        }
    }

    public void deleteLesson(int lessonId) throws LessonException {
        String query = "DELETE FROM Lesson WHERE id = ?";
        int idIndex = 1;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(idIndex, lessonId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new LessonException("Error deleting lesson: " + e.getMessage());
        }
    }

    public List<Lesson> getAllLessons() throws LessonException {
        List<Lesson> lessons = new ArrayList<>();
        String query = "SELECT Lesson.id, Lesson.name, Homework.id, Homework.name, Homework.description " +
                "FROM Lesson " +
                "JOIN Homework ON Lesson.homework_id = Homework.id";

        try (Connection connection = DataBaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int index = 1;

                int lessonId = resultSet.getInt(index++);
                String lessonName = resultSet.getString(index++);
                int homeworkId = resultSet.getInt(index++);
                String homeworkName = resultSet.getString(index++);
                String homeworkDescription = resultSet.getString(index++);

                Homework homework = new Homework(homeworkId, homeworkName, homeworkDescription);
                Lesson lesson = new Lesson(lessonId, lessonName, homework);

                lessons.add(lesson);
            }
        } catch (SQLException e) {
            throw new LessonException("Error retrieving lessons: " + e.getMessage());
        }

        return lessons;
    }

    public Lesson getLessonById(int lessonId) throws LessonException {
        String query = "SELECT Lesson.id, Lesson.name, Homework.id, Homework.name, Homework.description " +
                "FROM Lesson " +
                "JOIN Homework ON Lesson.homework_id = Homework.id " +
                "WHERE Lesson.id = ?";
        Lesson lesson = null;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, lessonId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int index = 1;

                int lessonIdResult = resultSet.getInt(index++);
                String lessonName = resultSet.getString(index++);
                int homeworkId = resultSet.getInt(index++);
                String homeworkName = resultSet.getString(index++);
                String homeworkDescription = resultSet.getString(index++);

                Homework homework = new Homework(homeworkId, homeworkName, homeworkDescription);
                lesson = new Lesson(lessonIdResult, lessonName, homework);
            }

        } catch (SQLException e) {
            throw new LessonException("Error retrieving lesson: " + e.getMessage());
        }

        return lesson;
    }

}
