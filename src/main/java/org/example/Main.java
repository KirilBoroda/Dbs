package org.example;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws HomeworkException, LessonException {
        LessonDao lessonDao = new LessonDao();
        Homework homework = new Homework(1, "Homework 1", "Description 1");
        Lesson lesson = new Lesson(12, "Lesson 123", homework);
        HomeworkDao homeworkDao = new HomeworkDao();
        homeworkDao.addHomework(homework);
        lessonDao.addLesson(lesson);
        LOGGER.info("Lesson added: " + lesson);

        List<Lesson> lessons = lessonDao.getAllLessons();
        LOGGER.info("All lessons:");
        for (Lesson l : lessons) {
            LOGGER.info(l.toString());
        }

        int lessonId = 9;
        Lesson lessonById = lessonDao.getLessonById(lessonId);
        LOGGER.info("Lesson with ID " + lessonId + ": " + lessonById);

        lessonDao.deleteLesson(lessonId);
        LOGGER.info("Lesson with ID " + lessonId + " deleted.");

        Lesson deletedLesson = lessonDao.getLessonById(lessonId);
        if (deletedLesson == null) {
            LOGGER.info("Lesson with ID " + lessonId + " does not exist.");
        }
    }
}
