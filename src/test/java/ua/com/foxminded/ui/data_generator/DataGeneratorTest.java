package ua.com.foxminded.ui.data_generator;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.com.foxminded.ui.data_generator.generator_parts.CoursesGenerator;
import ua.com.foxminded.ui.data_generator.generator_parts.GroupsGenerator;
import ua.com.foxminded.ui.data_generator.generator_parts.StudentsGenerator;
import ua.com.foxminded.ui.data_generator.generator_parts.TablesCreator;

class DataGeneratorTest {
    @Mock private TablesCreator tablesCreator;
    @Mock private GroupsGenerator groupsGenerator;
    @Mock private CoursesGenerator coursesGenerator;
    @Mock private StudentsGenerator studentsGenerator;
    private DataGenerator dataGenerator;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        dataGenerator = new DataGenerator(tablesCreator, groupsGenerator, coursesGenerator, studentsGenerator);
    }

    @Test
    void shouldInvokeMethodsForGeneratingData() {
        dataGenerator.generateRandomData();
        verify(tablesCreator).createTables();
        verify(groupsGenerator).createTenGroups();
        verify(coursesGenerator).createTenCourses();
        verify(studentsGenerator).createTwoHundredRandomStudents();
        verify(studentsGenerator).setRandomGroupsForStudents();
        verify(studentsGenerator).setRandomCoursesForStudents();
    }
}
