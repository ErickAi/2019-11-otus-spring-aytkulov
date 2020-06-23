package ru.otus.springbatch.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.springbatch.domain.jpa.BookRdb;
import ru.otus.springbatch.repository.rdb.BookRepositoryRdb;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final BookRepositoryRdb repositoryRdb;

    @ShellMethod(value = "RepositoryTest", key = "a")
    public String repositoryTest(Long id) {
        BookRdb bookRdb = repositoryRdb.findById(id).orElse(new BookRdb());
        return bookRdb.toString();
    }

}
