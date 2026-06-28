ALTER TABLE Landmark
    ALTER COLUMN description
        SET DATA TYPE varchar(2048);

ALTER TABLE Landmark
    ALTER COLUMN coordinates
        SET DATA TYPE geography(Point, 4326);