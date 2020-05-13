package ru.otus.booklibrary.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.booklibrary.domain.Author;
import ru.otus.booklibrary.repo.AuthorDao;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public Author getByName(String authorName) {
        return authorDao.getByName(authorName);
    }
}
