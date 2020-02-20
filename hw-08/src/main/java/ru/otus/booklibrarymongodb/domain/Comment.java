package ru.otus.booklibrarymongodb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    private Book book;

    private String entry;

    public Comment(String entry, Book book) {
        this.book = book;
        this.entry = entry;
    }
}
