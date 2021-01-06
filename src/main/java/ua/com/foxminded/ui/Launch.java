package ua.com.foxminded.ui;

import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.dao.StudentsCoursesDAO;
import ua.com.foxminded.dao.TablesCreatorDAO;
import ua.com.foxminded.domain.Service;
import ua.com.foxminded.ui.data_generator.DataGenerator;
import ua.com.foxminded.ui.data_generator.generator_parts.CoursesGenerator;
import ua.com.foxminded.ui.data_generator.generator_parts.GroupsGenerator;
import ua.com.foxminded.ui.data_generator.generator_parts.StudentsGenerator;
import ua.com.foxminded.ui.data_generator.generator_parts.TablesCreator;
import ua.com.foxminded.ui.menu.ConsoleMenu;

public class Launch {
    public static void main (String [] args) {
        Service service = new Service(new GroupDAO(), new  StudentDAO(), new CourseDAO(), new StudentsCoursesDAO()); 
        DataGenerator datagenerator = new DataGenerator(new TablesCreator(new TablesCreatorDAO()),
                new GroupsGenerator(service), new CoursesGenerator(service), new StudentsGenerator(service));
        datagenerator.generateRandomData(); 

        System.out.println("Hello. Welcome to jdbc-school!");
        ConsoleMenu consoleMenu = new ConsoleMenu(service);
        boolean isContinue = true;
        while (isContinue) {
            consoleMenu.getMenu();
            consoleMenu.makeChoosenAction();
            isContinue = consoleMenu.isContinue();
        }
        System.out.println("Good bye!");
    } 
}
