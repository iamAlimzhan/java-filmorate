drop table if exists friends;
drop table if exists likes;
drop table if exists users;
drop table if exists filmGenres;
drop table if exists film;
CREATE TABLE IF NOT EXISTS users (
    user_id INT GENERATED BY DEFAULT AS IDENTITY,
    email VARCHAR NOT NULL,
    login VARCHAR NOT NULL,
    name VARCHAR NOT NULL,
    birthday DATE NOT NULL,
    CONSTRAINT PK_user PRIMARY KEY (user_id),
    CONSTRAINT UQ_user_email UNIQUE (email),
    CONSTRAINT UQ_user_login UNIQUE (login)
);


CREATE TABLE IF NOT EXISTS mpa (
    mpa_id INT,
    mpa VARCHAR NOT NULL,
    CONSTRAINT PK_mpa PRIMARY KEY (mpa_id)
);

CREATE TABLE IF NOT EXISTS friends (
    user_id INT NOT NULL,
    friend_id INT NOT NULL,
    is_confirm BOOLEAN NOT NULL,
    CONSTRAINT PK_unique_friends PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS film (
    film_id INT GENERATED BY DEFAULT AS IDENTITY,
    name VARCHAR NOT NULL,
    description VARCHAR,
    release_date DATE NOT NULL,
    duration INTEGER NOT NULL,
    mpa_id INT NOT NULL,
    CONSTRAINT PK_film PRIMARY KEY (film_id)
);

CREATE TABLE IF NOT EXISTS filmGenres (
    film_id INT NOT NULL,
    genre_id INT NOT NULL,
    CONSTRAINT PK_unique_filmGenre PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS genre (
    genre_id INT,
    genre VARCHAR NOT NULL,
    CONSTRAINT PK_genre PRIMARY KEY (genre_id)
);

CREATE TABLE IF NOT EXISTS likes(
	film_id INT NOT NULL,
	user_id INT NOT NULL,
	CONSTRAINT PK_unique_likes PRIMARY KEY (film_id, user_id)
);