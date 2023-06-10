package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HomeworkDao {
    public void addHomework(Homework homework) throws HomeworkException {
        String query = "INSERT INTO Homework (name, description) VALUES (?, ?)";
        int nameIndex = 1;
        int descriptionIndex = 2;

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(nameIndex, homework.getName());
            statement.setString(descriptionIndex, homework.getDescription());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new HomeworkException("Error adding homework: " + e.getMessage());
        }
    }


}
