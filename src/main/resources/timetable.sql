
CREATE TABLE IF NOT EXISTS faculties (
    faculty_id  SERIAL PRIMARY KEY,
    faculty_name varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS groups (
    group_id  SERIAL PRIMARY KEY,
    faculty_id int,
    group_name varchar(50) NOT NULL,
	FOREIGN KEY (faculty_id) REFERENCES faculties(faculty_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS lectors (
	lector_id  SERIAL PRIMARY KEY,
	faculty_id int,
	first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
	 FOREIGN KEY (faculty_id) REFERENCES faculties(faculty_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS lessons(
	lesson_id  SERIAL PRIMARY KEY ,
	lector_id int,
	lesson_name varchar(50) NOT NULL,
	CONSTRAINT fk_lector_id FOREIGN KEY (lector_id) REFERENCES lectors(lector_id)
);


CREATE TABLE IF NOT EXISTS time_slots(
	timeslot_id  SERIAL PRIMARY KEY,
	lesson_id int,
	group_id int,
	start_lesson timestamp,
	end_lesson timestamp,
	CONSTRAINT fk_lesson_id FOREIGN KEY (lesson_id) REFERENCES lessons(lesson_id),
	CONSTRAINT fk_group_id FOREIGN KEY (group_id) REFERENCES groups(group_id)
);











