package ru.otus.springbatch.domain.nosql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class CommentMongo {

    private String id;

    private String bookId;

    private String entry;
}
