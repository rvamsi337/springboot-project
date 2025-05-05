CREATE TABLE IF NOT EXISTS professor(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    department VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS course(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    credits INT,
    professorid INT,
    FOREIGN KEY (professorid) REFERENCES professor(id)
);

CREATE TABLE IF NOT EXISTS student(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS course_student(
    studentid INT,
    courseid INT,
    PRIMARY KEY(courseid, studentid),
    FOREIGN KEY (courseid) REFERENCES course(id),
    FOREIGN KEY (studentid) REFERENCES student(id)
);