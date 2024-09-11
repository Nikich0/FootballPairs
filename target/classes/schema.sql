CREATE TABLE players (
                         id SERIAL PRIMARY KEY,
                         teamNum INT,
                         position VARCHAR(255),
                         fullName VARCHAR(255),
                         teamId INT
);