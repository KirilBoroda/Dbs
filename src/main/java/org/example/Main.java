package org.example;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        LessonDao lessonDao = new LessonDao();
        Homework homework = new Homework(1, "Homework 1", "Description 1");
        Lesson lesson = new Lesson(12, "Lesson 123", homework);
        HomeworkDao homeworkDao = new HomeworkDao();
        homeworkDao.addHomework(homework);
        lessonDao.addLesson(lesson);
        System.out.println("Lesson added: " + lesson);

        List<Lesson> lessons = lessonDao.getAllLessons();
        System.out.println("All lessons:");
        for (Lesson l : lessons) {
            System.out.println(l);
        }

        int lessonId = 9;
        Lesson lessonById = lessonDao.getLessonById(lessonId);
        System.out.println("Lesson with ID " + lessonId + ": " + lessonById);


        lessonDao.deleteLesson(lessonId);
        System.out.println("Lesson with ID " + lessonId + " deleted.");


        Lesson deletedLesson = lessonDao.getLessonById(lessonId);
        if (deletedLesson == null) {
            System.out.println("Lesson with ID " + lessonId + " does not exist.");
        }
    }
}
