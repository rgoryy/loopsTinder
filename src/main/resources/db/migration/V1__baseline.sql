CREATE TYPE tonality AS ENUM ('Cminor', 'Dminor', 'Eminor');

CREATE TABLE Usr (
                     id BIGSERIAL NOT NULL PRIMARY KEY,
                     login VARCHAR(100) NOT NULL,
                     password VARCHAR(50),
                     telegram_id BIGINT UNIQUE NOT NULL
);


CREATE TABLE Tag (
                     id BIGSERIAL NOT NULL PRIMARY KEY,
                     tag_itself VARCHAR(50)
);

CREATE TABLE Loop (
                      id BIGSERIAL NOT NULL PRIMARY KEY,
                      user_id BIGINT NOT NULL,
                      file_path VARCHAR,
                      tonality tonality,
                      bpm BIGINT,
                      FOREIGN KEY (user_id) REFERENCES Usr (id)
);

CREATE TABLE Request (
                         id BIGSERIAL NOT NULL PRIMARY KEY,
                         user_id BIGINT NOT NULL,
                         creation_date DATE NOT NULL,
                         bpm_min BIGINT,
                         bpm_max BIGINT,
                         tonality tonality,
                         FOREIGN KEY (user_id) REFERENCES Usr (id)
);

CREATE TABLE Request_tags (
                              request_id BIGINT NOT NULL,
                              tag_id BIGINT NOT NULL,
                              FOREIGN KEY (request_id) REFERENCES Request (id),
                              FOREIGN KEY (tag_id) REFERENCES Tag (id)
);

CREATE TABLE Loop_tags (
                           loop_id BIGINT NOT NULL,
                           tag_id BIGINT NOT NULL,
                           FOREIGN KEY (loop_id) REFERENCES Loop (id),
                           FOREIGN KEY (tag_id) REFERENCES Tag (id)
);