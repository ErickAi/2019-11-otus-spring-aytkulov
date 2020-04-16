package ru.otus.booklibrarymongodb.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.booklibrarymongodb.domain.Author;
import ru.otus.booklibrarymongodb.domain.Book;
import ru.otus.booklibrarymongodb.domain.Comment;
import ru.otus.booklibrarymongodb.domain.Genre;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Map<String, Author> authors = new HashMap<>();
    private Map<String, Genre> genres = new HashMap<>();
    private Map<String, Book> books = new HashMap<>();

    @ChangeSet(order = "000", id = "dropDB", author = "e.aytkulov", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "addAuthors", author = "e.aytkulov", runAlways = true)
    public void initAuthors(MongoTemplate template){
        Set<Author> authorSet = Set.of(
                new Author("Михаил Булгаков"),
                new Author("Ильф и Петров"),
                new Author("Федор Достоевский"),
                new Author("Иван Крылов"),
                new Author("Михаил Лермонтов"),
                new Author("Виктор Пелевин"),
                new Author("Маркус Зусак"),
                new Author("Слава Сэ"),
                new Author("Анджей Сапковский")
        );
        for (Author author : authorSet) {
            template.save(author);
            authors.put(author.getName(), author);
        }
    }

    @ChangeSet(order = "002", id = "addGenres", author = "e.aytkulov", runAlways = true)
    public void initGenres(MongoTemplate template){
        Set<Genre> genresSet = Set.of(
                new Genre("Русская классика"),
                new Genre("Современная проза"),
                new Genre("Мировая классика"),
                new Genre("Драма"),
                new Genre("Мистика"),
                new Genre("Юмор"),
                new Genre("Басня"),
                new Genre("Поэма"),
                new Genre("Фентези"),
                new Genre("Фантастика"),
                new Genre("Военная проза")
        );
        for (Genre genre : genresSet) {
            template.save(genre);
            genres.put(genre.getName(), genre);
        }
    }

    @ChangeSet(order = "003", id = "addBooks", author = "e.aytkulov", runAlways = true)
    public void initBooks(MongoTemplate template){
        Set<Book> bookSet = Set.of(
                new Book("Мастер и Маргарита", authors.get("Михаил Булгаков"), 
                        getGenresByName("Русская классика", "Драма", "Мистика")),
                new Book("Двенадцать стульев", authors.get("Ильф и Петров"), 
                        getGenresByName("Русская классика", "Юмор")),
                new Book("Идиот", authors.get("Федор Достоевский"), 
                        getGenresByName("Русская классика", "Драма")),
                new Book("Мартышка и очки", authors.get("Иван Крылов"), 
                        getGenresByName("Русская классика", "Басня")),
                new Book("Мцыри", authors.get("Михаил Лермонтов"), 
                        getGenresByName("Русская классика", "Поэма")),
                new Book("Чапаев и Пустота", authors.get("Виктор Пелевин"), 
                        getGenresByName("Современная проза", "Фантастика")),
                new Book("Книжный вор", authors.get("Маркус Зусак"), 
                        getGenresByName("Современная проза", "Военная проза")),
                new Book("Сантехник, его кот, жена и другие подробности", authors.get("Слава Сэ"), 
                        getGenresByName("Современная проза", "Юмор")),
                new Book("Ведьмак. Последнее желание", authors.get("Анджей Сапковский"), 
                        getGenresByName("Современная проза", "Фентези")),
                new Book("Ведьмак. Меч Предназначения", authors.get("Анджей Сапковский"), 
                        getGenresByName("Современная проза", "Фентези")),
                new Book("Ведьмак. Кровь эльфов", authors.get("Анджей Сапковский"), 
                        getGenresByName("Современная проза", "Фентези")),
                new Book("Ведьмак. Крещение огнём", authors.get("Анджей Сапковский"), 
                        getGenresByName("Современная проза", "Фентези")),
                new Book("Ведьмак. Башня Ласточки", authors.get("Анджей Сапковский"), 
                        getGenresByName("Современная проза", "Фентези")),
                new Book("Ведьмак. Владычица Озера", authors.get("Анджей Сапковский"), 
                        getGenresByName("Современная проза", "Фентези")),
                new Book("Ведьмак. Сезон гроз", authors.get("Анджей Сапковский"), 
                        getGenresByName("Современная проза", "Фентези"))
        );
        for (Book book : bookSet) {
            template.save(book);
            books.put(book.getName(), book);
        }
    }

    @ChangeSet(order = "004", id = "addComments", author = "e.aytkulov", runAlways = true)
    public void initComments(MongoTemplate template){
        template.save(new Comment(books.get("Мастер и Маргарита").getId(),
                "Бестселлер русской классики. Читать всем!!!"));
        template.save(new Comment(books.get("Сантехник, его кот, жена и другие подробности").getId(),
                "Забавный уютный бытовой юмор."));
        template.save(new Comment(books.get("Мартышка и очки").getId(),
                "Если кто-то не учил ее в школе, то уж точно читал)))"));
        template.save(new Comment(books.get("Чапаев и Пустота").getId(),
                "У Пелевина все книги такие"));
        template.save(new Comment(books.get("Мастер и Маргарита").getId(),
                "Вдруг кто-нибудь захочет опубликовать свое эссе по одному из произведений. 6000 characters except this sentences.\n" +
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ac ipsum felis. Donec tincidunt vehicula libero quis luctus. Duis ac aliquet diam, at congue enim. Nunc vulputate, quam vitae luctus vestibulum, lorem elit consectetur tellus, eget lacinia ipsum nisl eu enim. Cras sodales lectus eget gravida laoreet. Integer iaculis ante id tempus sodales. Maecenas et tincidunt quam, ut commodo odio. Phasellus ornare mi non dui semper interdum. Praesent in magna bibendum, imperdiet neque ut, sagittis metus. Mauris sollicitudin blandit viverra. Cras sed pulvinar mauris. Nam vel eros viverra, ornare felis ac, malesuada nibh. Phasellus et turpis laoreet dui suscipit eleifend eget vel sem.\n" +
                        "Nunc imperdiet sapien augue, ut dignissim erat pulvinar nec. Nunc nibh erat, porta id enim nec, lacinia mattis risus. Duis quis euismod arcu. Proin nec enim id arcu fringilla imperdiet nec vitae enim. Donec in sem lacus. Fusce bibendum, est in pretium molestie, libero ipsum dapibus massa, in accumsan orci tellus quis erat. Etiam feugiat mauris id nisl feugiat maximus. Integer nec venenatis nunc. Aliquam ultricies luctus leo id scelerisque. Fusce id aliquet nisi. Donec luctus justo quis leo pharetra, id gravida erat rhoncus. Maecenas sagittis quis mauris in viverra. Vestibulum et luctus elit, dictum venenatis eros. Morbi pretium nunc ut mauris luctus, eu cursus nulla ornare. Nulla eget accumsan nulla, a convallis dolor.\n" +
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam venenatis aliquet mollis. Nulla bibendum tincidunt odio id sagittis. Fusce convallis convallis risus, nec sollicitudin sapien scelerisque sagittis. Proin a rhoncus ex. Ut in porttitor quam. Donec molestie purus eget massa hendrerit luctus.\n" +
                        "Vestibulum accumsan mollis finibus. Praesent consectetur volutpat elementum. Mauris ipsum nisi, vehicula ac quam vel, semper elementum sem. Fusce sagittis, nibh sit amet tempus vulputate, arcu odio scelerisque ipsum, eget feugiat ligula diam eu dui. Aenean vitae pretium elit. Etiam metus arcu, vulputate ac urna ut, fringilla varius augue. Fusce at mi venenatis, varius libero et, maximus augue. Maecenas vel semper lectus.\n" +
                        "Nam in erat ac enim varius sollicitudin quis at purus. Praesent id est convallis, consectetur neque vitae, interdum velit. Nam a egestas enim, at tempus neque. Aenean vitae risus enim. Duis mollis erat sit amet rhoncus molestie. Duis nec lobortis nibh, at malesuada leo. Praesent vitae mattis magna. Aliquam vulputate risus ut turpis viverra blandit.\n" +
                        "In quam velit, ornare et interdum sit amet, volutpat non nunc. In in pellentesque sem, ut interdum purus. Nam purus est, pretium sit amet feugiat ac, pretium et sapien. Morbi orci dui, iaculis sed sem ut, placerat placerat felis. Sed vel felis eleifend, ornare nunc mollis, ultrices arcu. Duis et nisl libero. Vestibulum suscipit nisi quis diam tincidunt fermentum. Vestibulum aliquam tempus eros, ac finibus nisi varius ornare. Etiam augue odio, suscipit a varius vitae, dapibus convallis orci. Curabitur quis neque pulvinar, tincidunt justo in, commodo nulla. Etiam pharetra metus eget elit mollis, sit amet ornare nisi commodo. Suspendisse vel augue sit amet orci viverra faucibus ac pretium erat. Mauris nec vehicula sem. Maecenas eu purus eros. Vivamus condimentum augue metus, et tempus nibh auctor sit amet. Nam faucibus ultrices dui, eget mollis est pharetra in.\n" +
                        "Aenean a fermentum lorem. Vestibulum eleifend arcu ut elit rhoncus tincidunt. Donec mollis ipsum vel sapien volutpat, eget imperdiet ipsum consectetur. Vestibulum nec hendrerit ante. Sed scelerisque auctor urna, vitae scelerisque lorem suscipit faucibus. Sed nisi massa, rutrum quis imperdiet ut, semper id ex. Nam nec mauris faucibus, tempor libero non, luctus purus. Suspendisse porttitor molestie velit, et vulputate metus vulputate eu.\n" +
                        "Integer mollis massa velit. Curabitur dictum lectus id nibh convallis, sed vestibulum justo consectetur. Cras eu viverra lorem. Sed viverra imperdiet ultrices. Ut pellentesque nunc sed metus tempor faucibus. Aliquam vel molestie augue. Cras aliquam, orci sit amet molestie ultricies, magna purus tempor lacus, nec ultricies leo neque ut ante. Vivamus at elementum purus. Mauris vitae lobortis tellus. Suspendisse rutrum nunc nec suscipit bibendum.\n" +
                        "Nam maximus quam ex, vitae pellentesque enim convallis non. Aenean pharetra nunc sed enim tincidunt, ornare lobortis diam consequat. Nam vulputate elit felis, sed ornare neque lobortis et. Aenean a tincidunt mauris. Curabitur nec justo urna. Aliquam blandit tellus sed imperdiet gravida. Donec egestas nunc et porttitor laoreet.\n" +
                        "In hac habitasse platea dictumst. Sed molestie, lectus non viverra vestibulum, metus ipsum tristique libero, eget elementum felis ligula luctus nibh. Pellentesque felis dui, luctus vitae lacus non, ultrices gravida felis. In pharetra, est et fermentum bibendum, elit risus varius tellus, at faucibus urna ipsum non ex. Fusce rhoncus lobortis lorem at viverra. Pellentesque suscipit ornare est, vel congue elit vulputate vitae. Donec sed tempor nunc. Duis maximus ante et ipsum pharetra, eu pulvinar ligula dapibus. Nam vulputate, enim nec tincidunt vehicula, leo tellus semper dui, et tempor mauris ex pulvinar nulla.\n" +
                        "Morbi feugiat fermentum luctus. Praesent ipsum nisi, volutpat quis lacus vitae, finibus rutrum orci. Fusce varius risus et leo semper commodo. Nullam ac scelerisque lectus. Vivamus vitae cursus sapien, et tempus leo. Nulla facilisi. Curabitur suscipit, neque vitae placerat facilisis, nisl massa porttitor nulla, non condimentum magna elit sit amet tortor. Ut tortor dolor, ullamcorper nec dictum eget, dignissim in enim. Sed turpis nisi, lacinia a leo sed, volutpat gravida erat. Mauris lacinia nisl turpis, id lacinia tortor placerat non. Nulla fermentum tortor massa, quis aliquam elit efficitur pellentesque.\n" +
                        "Cras gravida et tortor ac faucibus. Duis iaculis lacus sit amet lacinia viverra. Aliquam vestibulum, nunc sed porta sodales, libero dui ultricies neque, ut rutrum sem eros quis sem volutpat.');\n"));
    }

    private Set<Genre> getGenresByName(String... name) {
        Set<Genre> genresSet = new HashSet<>();
        for (String s : name) {
            genresSet.add(genres.get(s));
        }
        return genresSet;
    }
}
