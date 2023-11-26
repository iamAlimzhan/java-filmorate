package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.DaoMpa;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final DaoMpa daoMpa;

    public List<Mpa> getMpaList() {
        return daoMpa.getMpaList();
    }

    public Mpa getById(int id) {
        return daoMpa.getById(id);
    }
}
