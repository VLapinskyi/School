package ua.com.foxminded.ui.menu;

import static org.junit.jupiter.api.Assertions.*;
import static java.lang.System.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Service;
import ua.com.foxminded.domain.Student;

class ConsoleMenuTest {
    private final PrintStream standardOut = out;
    private ByteArrayOutputStream outputStreamCaptor;

    @Mock private Service service;
    @Mock private BufferedReader reader;
    private ConsoleMenu consoleMenu;
    private ConsoleMenu spyConsoleMenu;
    private StringJoiner expectedText;
    private StringJoiner actualText;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        consoleMenu = new ConsoleMenu(service);
        spyConsoleMenu = spy(consoleMenu);
        spyConsoleMenu.setReader(reader);
        outputStreamCaptor = new ByteArrayOutputStream();
        setOut(new PrintStream(outputStreamCaptor));
        expectedText = new StringJoiner(lineSeparator(), "", lineSeparator());
        actualText = new StringJoiner(lineSeparator());
    }

    @AfterEach
    void tearDown() {
        setOut(standardOut);
    }

    @Test
    void shouldGetMenu() {
        expectedText.add("Please, type the number of the menu item, which you want:");
        expectedText.add("1 - Find all groups with less or equals student count");
        expectedText.add("2 - Find all students related to course with given name");
        expectedText.add("3 - Add new student");
        expectedText.add("4 - Delete student by STUDENT_ID");
        expectedText.add("5 - Add a student to the course");
        expectedText.add("6 - Remove the student from one of his or her courses");
        consoleMenu.getMenu();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString());
    }

    @Test
    void shouldReturnTrueWhenIsContinue() throws IOException {
        boolean expectedResult = true;
        when(reader.readLine()).thenReturn("1");
        assertEquals(expectedResult, spyConsoleMenu.isContinue());
    }

    @Test
    void shouldReturnFalseWhenIsContinue() throws IOException {
        boolean expectedResult = false;
        when(reader.readLine()).thenReturn("0");
        assertEquals(expectedResult, spyConsoleMenu.isContinue());
    }

    @Test
    void shouldPrintExpectedTextWhenIsContinueCorrect() throws IOException {
        String correctOption = "0";
        when(reader.readLine()).thenReturn(correctOption);
        expectedText.add("Do you want to do something else?");
        expectedText.add("1 - Yes.");
        expectedText.add("0 - No.");
        spyConsoleMenu.isContinue();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString());
    }

    @Test
    void shouldPrintExpectedTextWhenIsContinueIncorrect() throws IOException {
        String incorrectOption = "3";
        String correctOption = "0";
        when(reader.readLine()).thenReturn(incorrectOption).thenReturn(correctOption);
        expectedText.add("Do you want to do something else?");
        expectedText.add("1 - Yes.");
        expectedText.add("0 - No.");
        expectedText.add("You type incorrect data");
        expectedText.add("Please, make your decision");
        spyConsoleMenu.isContinue();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString()); 
    }

    @Test
    void shouldGetNumberFromStream() throws IOException {
        int expectedNumber = 10;
        when(reader.readLine()).thenReturn("10");
        int actualNumber = spyConsoleMenu.getNumberFromConsole();
        assertEquals(expectedNumber, actualNumber);
    }

    @Test
    void shouldGetTextFromStream() throws IOException {
        String expectedText = "Test";
        when(reader.readLine()).thenReturn("Test");
        String actualText = spyConsoleMenu.getTextFromConsole();
        assertEquals(expectedText, actualText);
    }

    @Test
    void shouldInvokeMakeFirstAction() throws IOException {
        String choosenAction = "1";
        when(reader.readLine()).thenReturn(choosenAction);
        doNothing().when(spyConsoleMenu).makeFirstAction();
        spyConsoleMenu.makeChoosenAction();
        verify(spyConsoleMenu).makeFirstAction();
    }

    @Test
    void shouldInvokeMakeSecondAction() throws IOException {
        String choosenAction = "2";
        when(reader.readLine()).thenReturn(choosenAction);
        doNothing().when(spyConsoleMenu).makeSecondAction();
        spyConsoleMenu.makeChoosenAction();
        verify(spyConsoleMenu).makeSecondAction();
    }

    @Test
    void shouldInvokeMakeThirdAction() throws IOException {
        String choosenAction = "3";
        when(reader.readLine()).thenReturn(choosenAction);
        doNothing().when(spyConsoleMenu).makeThirdAction();
        spyConsoleMenu.makeChoosenAction();
        verify(spyConsoleMenu).makeThirdAction();
    }

    @Test
    void shouldInvokeMakeFourthAction() throws IOException {
        String choosenAction = "4";
        when(reader.readLine()).thenReturn(choosenAction);
        doNothing().when(spyConsoleMenu).makeFourthAction();
        spyConsoleMenu.makeChoosenAction();
        verify(spyConsoleMenu).makeFourthAction();
    }

    @Test
    void shouldInvokeMakeFifthAction() throws IOException {
        String choosenAction = "5";
        when(reader.readLine()).thenReturn(choosenAction);
        doNothing().when(spyConsoleMenu).makeFifthAction();
        spyConsoleMenu.makeChoosenAction();
        verify(spyConsoleMenu).makeFifthAction();
    }

    @Test
    void shouldInvokeMakeSixthAction() throws IOException {
        String choosenAction = "6";
        when(reader.readLine()).thenReturn(choosenAction);
        doNothing().when(spyConsoleMenu).makeSixthAction();
        spyConsoleMenu.makeChoosenAction();
        verify(spyConsoleMenu).makeSixthAction();
    }

    @Test
    void shouldPrintWarningWhenIncorectNumber() throws IOException {
        String choosenCorrectAction = "6";
        String choosenIncorectAction = "7";
        when(reader.readLine()).thenReturn(choosenIncorectAction).thenReturn(choosenCorrectAction);
        doNothing().when(spyConsoleMenu).makeSixthAction();
        expectedText.add("You type incorect data");
        expectedText.add("Please, try again");
        spyConsoleMenu.makeChoosenAction();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString());
    }

    @Test
    void shouldInvokeMethodForFirstAction() throws IOException {
        String studentsNumber = "10";
        when(reader.readLine()).thenReturn(studentsNumber);
        spyConsoleMenu.makeFirstAction();
        verify(service).getGroupsWithNoMoreStudentsThan(anyInt());
    }

    @Test
    void shouldPrintExpectedTextWhenNoGroupsForFirstAction() throws IOException {
        expectedText.add("Please, type the number of students");
        expectedText.add("There are no such groups");
        when(reader.readLine()).thenReturn("1");
        spyConsoleMenu.makeFirstAction();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString());
    }

    @Test
    void shouldPrintExpectedGroupsForFirstAction() throws IOException {
        ArrayList<Group> expectedGroups = new ArrayList<>(Arrays.asList(
                new Group(1, "QW-01"),
                new Group(2, "AS-02")));
        expectedText.add("Please, type the number of students");
        expectedGroups.stream().forEach(group -> expectedText.add(group.toString()));
        when(reader.readLine()).thenReturn("1");
        when(service.getGroupsWithNoMoreStudentsThan(1)).thenReturn(expectedGroups);
        spyConsoleMenu.makeFirstAction();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString());
    }

    @Test
    void shouldInvokeMethodForSecondAction() throws IOException {
        String courseName = "math";
        when(reader.readLine()).thenReturn(courseName);
        spyConsoleMenu.makeSecondAction();
        verify(service).getStudentsFromCourseByCourseName(anyString());
    }


    @Test
    void shouldPrintExpectedTextWhenNoStudentsForSecondAction() throws IOException {
        expectedText.add("Please, type the course name");
        expectedText.add("There are no students from this course");
        when(reader.readLine()).thenReturn("1");
        spyConsoleMenu.makeSecondAction();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString());
    }

    @Test
    void shouldPrintExpectedStudentsForSecondAction() throws IOException {
        ArrayList<Student> expectedStudents = new ArrayList<>(Arrays.asList(
                new Student(1, "Olha", "Skladenko"),
                new Student(2, "Ivan", "Zakharchuk")));
        expectedText.add("Please, type the course name");
        expectedStudents.stream().forEach(student -> expectedText.add(student.toString()));
        when(reader.readLine()).thenReturn("math");
        when(service.getStudentsFromCourseByCourseName("math")).thenReturn(expectedStudents);
        spyConsoleMenu.makeSecondAction();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString());
    }

    @Test
    void shouldInvokeMethodForThirdAction() throws IOException {
        String firsName = "Valentyn";
        String lastName = "Lapinskyi";
        when(reader.readLine()).thenReturn(firsName).thenReturn(lastName);
        spyConsoleMenu.makeThirdAction();
        verify(service).addStudent(anyString(), anyString());
    }

    @Test
    void shouldPrintExpectedTextForThirdAction() throws IOException {
        expectedText.add("Please, type student's first name");
        expectedText.add("Please, type student's last name");
        expectedText.add("Thank you, the student was successfully added");
        when(reader.readLine()).thenReturn("Valentyn").thenReturn("Lapinskyi");
        spyConsoleMenu.makeThirdAction();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString());
    }

    @Test
    void shouldInvokeMethodForFourthAction() throws IOException {
        String studentId = "10";
        when(reader.readLine()).thenReturn(studentId);
        spyConsoleMenu.makeFourthAction();
        verify(service).deleteStudentById(anyInt());
    }

    @Test
    void shouldPrintExpectedTextForFourthAction() throws IOException {
        expectedText.add("Please, type student's id");
        expectedText.add("Thank you, the student was successfully deleted");
        when(reader.readLine()).thenReturn("1");
        spyConsoleMenu.makeFourthAction();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString());
    }

    @Test
    void shouldInvokeMethodForFifthAction() throws IOException {
        String studentId = "10";
        String courseId = "9";
        when(reader.readLine()).thenReturn(studentId).thenReturn(courseId);
        spyConsoleMenu.makeFifthAction();
        verify(service).addStudentToCourse(anyInt(), anyInt());;
    }

    @Test
    void shouldPrintExpectedTextForFifthAction() throws IOException {
        expectedText.add("Please, type student's id");
        expectedText.add("Please, type course's id");
        expectedText.add("Thank you, the student was successfully added to the course");
        when(reader.readLine()).thenReturn("1").thenReturn("2");
        spyConsoleMenu.makeFifthAction();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString());
    }

    @Test
    void shouldInvokeMethodForSixthAction() throws IOException {
        String studentId = "10";
        String courseId = "9";
        when(reader.readLine()).thenReturn(studentId).thenReturn(courseId);
        spyConsoleMenu.makeSixthAction();
        verify(service).deleteStudentFromCourse(anyInt(), anyInt());
    }

    @Test
    void shouldPrintExpectedTextForSixthAction() throws IOException {
        expectedText.add("Please, type student's id");
        expectedText.add("Please, type course's id");
        expectedText.add("Thank you, the student was successfully removed from the course");
        when(reader.readLine()).thenReturn("1").thenReturn("2");
        spyConsoleMenu.makeSixthAction();
        actualText.add(outputStreamCaptor.toString());
        assertEquals(expectedText.toString(), actualText.toString());
    }
}
