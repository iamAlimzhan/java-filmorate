# java-filmorate

dbdiagram.io: https://dbdiagram.io/d/6511bbc2ffbf5169f0765bf6

![ER diagram](![Diagram](https://github.com/iamAlimzhan/java-filmorate/assets/120095829/299d930b-85c4-48f1-acf0-87dcd0066b8f)
)


***Эта диаграмма описывает структуру базы данных, которая может использоваться в приложении для хранения информации
о фильмах, жанрах, пользователях и их действиях, таких как лайки и дружбы.***

-Table film: Эта таблица содержит информацию о фильмах, включая их название, описание, дату выпуска, продолжительность и 
идентификатор MPA (Motion Picture Association).

-Table likes: В этой таблице хранится информация о лайках, которые пользователи могут ставить фильмам. Она связана с 
таблицей film по идентификатору фильма (film_id) и с таблицей user по идентификатору пользователя (user_id).

-Table filmGenres: Эта таблица представляет собой связующую таблицу между фильмами и жанрами. Она позволяет установить 
связь между фильмом и одним или несколькими жанрами.

-Table genre: В этой таблице хранится информация о жанрах фильмов. Она связана с таблицей filmGenres по идентификатору 
жанра (genre_id).

-Table mpa: Здесь хранится информация о рейтинге MPA фильмов (Motion Picture Association). Она связана с таблицей film 
по идентификатору MPA (MpaId).

-Table user: Эта таблица содержит информацию о пользователях, включая их электронную почту, логин, имя и дату рождения.

-Table friends: В этой таблице хранятся связи между пользователями, представляя дружеские отношения. Она связана с 
таблицей user по идентификатору пользователя (user_id).

**Примеры запросов:**
```
SELECT *
FROM film;
```
```
SELECT *
FROM user;
```

**Получение списка фильмов, которые понравились определенному пользователю:**
```
SELECT film.name
FROM film
INNER JOIN likes ON film.film_id = likes.film_id
WHERE likes.user_id = 123;
```

**Получение списка друзей пользователя:**
```
SELECT u.name
FROM user u
INNER JOIN friends f ON u.user_id = f.friend_id
WHERE f.user_id = 123;
```
