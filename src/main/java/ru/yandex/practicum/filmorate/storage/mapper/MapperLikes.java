package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Likes;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapperLikes implements RowMapper<Likes> {
    @Override
    public Likes mapRow(ResultSet rs, int rowNum) throws SQLException {
        Likes likes = new Likes();
        likes.setFilmId(rs.getInt("film_id"));
        likes.setUserId(rs.getInt("user_id"));
        return likes;
    }
}
