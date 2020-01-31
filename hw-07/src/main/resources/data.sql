select 1; -- first query is not insert data. I have no idea what's going on. @formatter:off
insert into GENRES (ID, NAME) values ( 1, 'g');
insert into GENRES (ID, NAME) values ( 2, 'genre');
insert into GENRES (ID, NAME) values ( 3, 'Русская классика');
insert into GENRES (id, name) values ( 4, 'Современная проза');
insert into GENRES (id, name) values ( 5, 'Мировая классика');
insert into GENRES (id, name) values ( 6, 'Драма');
insert into GENRES (id, name) values ( 7, 'Мистика');
insert into GENRES (id, name) values ( 8, 'Юмор');
insert into GENRES (id, name) values ( 9, 'Басня');
insert into GENRES (id, name) values (10, 'Фантастика');
insert into GENRES (id, name) values (11, 'Военная проза');
insert into GENRES (id, name) values (12, 'Фентези');
insert into GENRES (id, name) values (13, 'Поэма');
insert into GENRES (id, name) values (14, 'Пьеса');
insert into GENRES (id, name) values (15, 'Военная проза');
insert into GENRES (id, name) values (16, 'Сказка');

insert into AUTHORS (id, name) values ( 1, 'a');
insert into AUTHORS (id, name) values ( 2, 'author');
insert into AUTHORS (id, name) values ( 3, 'Михаил Булгаков');
insert into AUTHORS (id, name) values ( 4, 'Ильф и Петров');
insert into AUTHORS (id, name) values ( 5, 'Федор Достоевский');
insert into AUTHORS (id, name) values ( 6, 'Иван Крылов');
insert into AUTHORS (id, name) values ( 7, 'Михаил Лермонтов');
insert into AUTHORS (id, name) values ( 8, 'Виктор Пелевин');
insert into AUTHORS (id, name) values ( 9, 'Маркус Зусак');
insert into AUTHORS (id, name) values (10, 'Слава Сэ');
insert into AUTHORS (id, name) values (11, 'Анджей Сапковский');
insert into AUTHORS (id, name) values (12, 'Гомер');
insert into AUTHORS (id, name) values (13, 'Уильям Шекспир');
insert into AUTHORS (id, name) values (14, 'Оскар Уайльд');
insert into AUTHORS (id, name) values (15, 'Фрэнсис Скотт Фицджеральд');
insert into AUTHORS (id, name) values (16, 'Джером Дэвид Сэлинджер');
insert into AUTHORS (id, name) values (17, 'Антуан де Сент-Экзюпери');
insert into AUTHORS (id, name) values (18, 'Ханс Кристиан Андерсен');
insert into AUTHORS (id, name) values (19, 'Братья Гримм');

insert into BOOKS (id, name, author_id) values ( 1, 'b', 1);                                                -- g
insert into BOOKS (id, name, author_id) values ( 2, 'book', 2);                                             -- g  -- genre
insert into BOOKS (id, name, author_id) values ( 3, 'b book', 2);                                           -- g  -- genre
insert into BOOKS (id, name, author_id) values ( 4, 'Мастер и Маргарита', 3);                               -- Русская классика  -- Драма    -- Мистика
insert into BOOKS (id, name, author_id) values ( 5, 'Двенадцать стульев', 4);                               -- Русская классика  -- Юмор
insert into BOOKS (id, name, author_id) values ( 6, 'Идиот', 5);                                            -- Русская классика  -- Драма
insert into BOOKS (id, name, author_id) values ( 7, 'Мартышка и очки', 6);                                  -- Русская классика  -- Басня
insert into BOOKS (id, name, author_id) values ( 8, 'Мцыри', 7);                                            -- Русская классика  -- Поэма
insert into BOOKS (id, name, author_id) values ( 9, 'Чапаев и Пустота', 8);                                 -- Современная проз  -- Фантастика
insert into BOOKS (id, name, author_id) values (10, 'Книжный вор', 9);                                      -- Современная проз  -- Военная проза
insert into BOOKS (id, name, author_id) values (11, 'Сантехник, его кот, жена и другие подробности', 10);   -- Современная проз  -- Юмор
insert into BOOKS (id, name, author_id) values (12, 'Ведьмак. Последнее желание', 11);                      -- Современная проз  -- Фентези
insert into BOOKS (id, name, author_id) values (13, 'Ведьмак. Меч Предназначения', 11);                     -- Современная проз  -- Фентези
insert into BOOKS (id, name, author_id) values (14, 'Ведьмак. Кровь эльфов', 11);                           -- Современная проз  -- Фентези
insert into BOOKS (id, name, author_id) values (15, 'Ведьмак. Крещение огнём', 11);                         -- Современная проз  -- Фентези
insert into BOOKS (id, name, author_id) values (16, 'Ведьмак. Башня Ласточки', 11);                         -- Современная проз  -- Фентези
insert into BOOKS (id, name, author_id) values (17, 'Ведьмак. Владычица Озера', 11);                        -- Современная проз  -- Фентези
insert into BOOKS (id, name, author_id) values (18, 'Ведьмак. Сезон гроз', 11);                             -- Современная проз  -- Фентези
insert into BOOKS (id, name, author_id) values (19, 'Илиада', 12);                                          -- Мировая классика  -- Поэма
insert into BOOKS (id, name, author_id) values (20, 'Король Лир', 13);                                      -- Мировая классика  -- Пьеса
insert into BOOKS (id, name, author_id) values (21, 'Портрет Дориана Грея', 14);                            -- Мировая классика  -- Драма
insert into BOOKS (id, name, author_id) values (22, 'Великий Гетсби', 15);                                  -- Мировая классика  -- Драма
insert into BOOKS (id, name, author_id) values (23, 'Над пропастью во ржи', 16);                            -- Мировая классика  -- Драма
insert into BOOKS (id, name, author_id) values (24, 'Маленький принц', 17);                                 -- Мировая классика  -- Сказка --Фэнтези
insert into BOOKS (id, name, author_id) values (25, 'Дюймовочка', 18);                                      -- Мировая классика  -- Сказка
insert into BOOKS (id, name, author_id) values (26, 'Бременские музыканты', 19);                            -- Мировая классика  -- Сказка

insert into BOOK_GENRE(book_id, genre_id) values ( 1, 1);
insert into BOOK_GENRE(book_id, genre_id) values ( 2, 1);
insert into BOOK_GENRE(book_id, genre_id) values ( 2, 2);
insert into BOOK_GENRE(book_id, genre_id) values ( 3, 1);
insert into BOOK_GENRE(book_id, genre_id) values ( 3, 2);
insert into BOOK_GENRE(book_id, genre_id) values ( 4, 3);
insert into BOOK_GENRE(book_id, genre_id) values ( 4, 6);
insert into BOOK_GENRE(book_id, genre_id) values ( 4, 7);
insert into BOOK_GENRE(book_id, genre_id) values ( 5, 3);
insert into BOOK_GENRE(book_id, genre_id) values ( 5, 8);
insert into BOOK_GENRE(book_id, genre_id) values ( 6, 3);
insert into BOOK_GENRE(book_id, genre_id) values ( 6, 6);
insert into BOOK_GENRE(book_id, genre_id) values ( 7, 3);
insert into BOOK_GENRE(book_id, genre_id) values ( 7, 9);
insert into BOOK_GENRE(book_id, genre_id) values ( 8, 3);
insert into BOOK_GENRE(book_id, genre_id) values ( 8, 13);
insert into BOOK_GENRE(book_id, genre_id) values ( 9, 4);
insert into BOOK_GENRE(book_id, genre_id) values ( 9, 10);
insert into BOOK_GENRE(book_id, genre_id) values (10, 4);
insert into BOOK_GENRE(book_id, genre_id) values (10, 2);
insert into BOOK_GENRE(book_id, genre_id) values (10, 11);
insert into BOOK_GENRE(book_id, genre_id) values (11, 4);
insert into BOOK_GENRE(book_id, genre_id) values (11, 8);
insert into BOOK_GENRE(book_id, genre_id) values (12, 4);
insert into BOOK_GENRE(book_id, genre_id) values (12, 12);
insert into BOOK_GENRE(book_id, genre_id) values (13, 4);
insert into BOOK_GENRE(book_id, genre_id) values (13, 12);
insert into BOOK_GENRE(book_id, genre_id) values (14, 4);
insert into BOOK_GENRE(book_id, genre_id) values (14, 12);
insert into BOOK_GENRE(book_id, genre_id) values (15, 4);
insert into BOOK_GENRE(book_id, genre_id) values (15, 12);
insert into BOOK_GENRE(book_id, genre_id) values (16, 4);
insert into BOOK_GENRE(book_id, genre_id) values (16, 12);
insert into BOOK_GENRE(book_id, genre_id) values (17, 4);
insert into BOOK_GENRE(book_id, genre_id) values (17, 12);
insert into BOOK_GENRE(book_id, genre_id) values (18, 4);
insert into BOOK_GENRE(book_id, genre_id) values (18, 2);
insert into BOOK_GENRE(book_id, genre_id) values (18, 12);
insert into BOOK_GENRE(book_id, genre_id) values (19, 5);
insert into BOOK_GENRE(book_id, genre_id) values (19, 13);
insert into BOOK_GENRE(book_id, genre_id) values (20, 5);
insert into BOOK_GENRE(book_id, genre_id) values (20, 14);
insert into BOOK_GENRE(book_id, genre_id) values (21, 5);
insert into BOOK_GENRE(book_id, genre_id) values (21, 6);
insert into BOOK_GENRE(book_id, genre_id) values (22, 5);
insert into BOOK_GENRE(book_id, genre_id) values (22, 6);
-- insert into BOOK_GENRE(book_id, genre_id) values (20, 3);
-- insert into BOOK_GENRE(book_id, genre_id) values (20, 4);
insert into BOOK_GENRE(book_id, genre_id) values (24, 5);
insert into BOOK_GENRE(book_id, genre_id) values (24, 12);
insert into BOOK_GENRE(book_id, genre_id) values (24, 2);
insert into BOOK_GENRE(book_id, genre_id) values (24, 16);
insert into BOOK_GENRE(book_id, genre_id) values (25, 5);
insert into BOOK_GENRE(book_id, genre_id) values (25, 16);
insert into BOOK_GENRE(book_id, genre_id) values (26, 5);
insert into BOOK_GENRE(book_id, genre_id) values (26, 16);
