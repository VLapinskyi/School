DROP TABLE IF EXISTS
	courses,
	groups,
	students,
	students_courses
	CASCADE;

CREATE TABLE courses (
    course_id serial NOT NULL,
    course_name character varying NOT NULL,
    course_description character varying,
    PRIMARY KEY (course_id)
);

CREATE TABLE groups (
	group_id serial NOT NULL,
	group_name character (5) NOT NULL,
	PRIMARY KEY (group_id)
);
    
CREATE TABLE students (
	student_id serial NOT NULL,
	group_id integer,
	first_name character varying,
	last_name character varying,
	PRIMARY KEY (student_id),
	FOREIGN KEY (group_id) REFERENCES public.groups (group_id) ON DELETE CASCADE
);
    
CREATE TABLE students_courses (
	student_id integer NOT NULL,
	course_id integer NOT NULL,
	PRIMARY KEY (student_id, course_id),
	FOREIGN KEY (student_id) REFERENCES public.students (student_id) ON DELETE CASCADE,
	FOREIGN KEY (course_id) REFERENCES public.courses (course_id) ON DELETE CASCADE
);