USE examseating;

CREATE TABLE admin (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE exam (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    room VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE student (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    registration_no VARCHAR(255) NOT NULL,
    exam_id INT NOT NULL,
    seat_no INT,
    PRIMARY KEY (id),
    FOREIGN KEY (exam_id) REFERENCES exam(id)
);
