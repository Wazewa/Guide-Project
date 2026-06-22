CREATE EXTENSION IF NOT EXISTS postgis;

ALTER TABLE Landmark
    ALTER COLUMN coordinates
        SET DATA TYPE geometry(Point, 4326)
        USING coordinates::geometry(Point, 4326);