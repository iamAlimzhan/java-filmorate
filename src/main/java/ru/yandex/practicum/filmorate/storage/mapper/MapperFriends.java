package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Friends;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapperFriends implements RowMapper<Friends> {
    @Override
    public Friends mapRow(ResultSet rs, int rowNum) throws SQLException {
        Friends friend = new Friends();
        friend.setId(rs.getInt("user_id"));
        friend.setFriendId(rs.getInt("friend_id"));
        friend.setConfirm(rs.getBoolean("is_confirm"));
        return friend;
    }
}
