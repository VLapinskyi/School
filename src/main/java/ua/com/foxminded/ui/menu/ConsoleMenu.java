package ua.com.foxminded.ui.menu;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Service;
import ua.com.foxminded.domain.Student;

public class ConsoleMenu {
    private static Logger logger = LogManager.getLogger(ConsoleMenu.class);
    private Service service;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    public ConsoleMenu(Service service) {
        this.service = service;
    }
    
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public void getMenu () {
        out.println("Please, type the number of the menu item, which you want:");
        this.generateMenu().stream().forEach(out :: println);
    }
    
    public void makeChoosenAction () {
        for (int i = 0; i < 1; i++) {
            int choosenOption = getNumberFromConsole();
            switch (choosenOption) {
                case 1:
                    makeFirstAction();
                    break;
                case 2:
                    makeSecondAction();
                    break;
                case 3:
                    makeThirdAction();
                    break;
                case 4:
                    makeFourthAction();
                    break;
                case 5:
                    makeFifthAction();
                    break;
                case 6:
                    makeSixthAction();
                    break;
                default:
                    i--;
                    out.println("You type incorect data");
                    out.println("Please, try again");
            }
        }
    }
    
    public boolean isContinue() {
        out.println("Do you want to do something else?");
        out.println("1 - Yes.");
        out.println("0 - No.");
        boolean result = false;
        for (int i = 0; i < 1; i++) {
            int choosenNumber = getNumberFromConsole();
            switch (choosenNumber) {
                case 1:
                    result = true;
                    break;
                case 0:
                    result = false;
                    break;
            default:
                i--;
                out.println("You type incorrect data");
                out.println("Please, make your decision");
            }
        }
        
        return result;
    }
    
    ArrayList<String> generateMenu () {
        ArrayList<String> menu = new ArrayList<>();
        menu.add("1 - Find all groups with less or equals student count");
        menu.add("2 - Find all students related to course with given name");
        menu.add("3 - Add new student");
        menu.add("4 - Delete student by STUDENT_ID");
        menu.add("5 - Add a student to the course");
        menu.add("6 - Remove the student from one of his or her courses");
        return menu;
    }
    
    int getNumberFromConsole () {
        StringBuilder result = new StringBuilder();
        try {
           result.append(reader.readLine());
        } catch (IOException e) {
            logger.error("cannot read data from console", e);
        }
        return Integer.parseInt(result.toString());
    }
    
    String getTextFromConsole () {
        StringBuilder result = new StringBuilder();
        try {
           result.append(reader.readLine());
        } catch (IOException e) {
            logger.error("cannot read data from console", e);
        }
        return result.toString();
    }
    
    void makeFirstAction() {
        out.println("Please, type the number of students");
        List<Group> groupsList = service.getGroupsWithNoMoreStudentsThan(getNumberFromConsole());
        if (groupsList.isEmpty()) {
            out.println("There are no such groups");
        }
        else {
            groupsList.stream().forEach(out :: println);
        }
    }
    
    void makeSecondAction() {
        out.println("Please, type the course name");
        List<Student> studentsList = service.getStudentsFromCourseByCourseName(getTextFromConsole());
        if (studentsList.isEmpty()) {
            out.println("There are no students from this course");
        }
        else {
            studentsList.stream().forEach(out :: println);
        }
    }
    
    void makeThirdAction() {
        out.println("Please, type student's first name");
        String firstName = getTextFromConsole();
        out.println("Please, type student's last name");
        String lastName = getTextFromConsole();
        service.addStudent(firstName, lastName);
        out.println("Thank you, the student was successfully added");
    }
    
    void makeFourthAction() {
        out.println("Please, type student's id");
        service.deleteStudentById(getNumberFromConsole());
        out.println("Thank you, the student was successfully deleted");
    }
    
    void makeFifthAction() {
        out.println("Please, type student's id");
        int studentId = getNumberFromConsole();
        out.println("Please, type course's id");
        int courseId = getNumberFromConsole();
        service.addStudentToCourse(studentId, courseId);
        out.println("Thank you, the student was successfully added to the course");
    }
    
    void makeSixthAction() {
        out.println("Please, type student's id");
        int studentId = getNumberFromConsole();
        out.println("Please, type course's id");
        int courseId = getNumberFromConsole();
        service.deleteStudentFromCourse(studentId, courseId);
        out.println("Thank you, the student was successfully removed from the course");
    }
}
