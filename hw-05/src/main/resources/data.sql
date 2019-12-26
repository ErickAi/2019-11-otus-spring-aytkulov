// @formatter:off
select 1;       // first query is not insert data. I have no idea what's going on.
insert into GENRES (ID, NAME) values ( 1, 'Русская классика');
insert into GENRES (id, name) values ( 2, 'Современная проза');
insert into GENRES (id, name) values ( 3, 'Мировая классика');
insert into GENRES (id, name) values ( 4, 'Драма');
insert into GENRES (id, name) values ( 5, 'Мистика');
insert into GENRES (id, name) values ( 6, 'Юмор');
insert into GENRES (id, name) values ( 7, 'Басня');
insert into GENRES (id, name) values ( 8, 'Фантастика');
insert into GENRES (id, name) values ( 9, 'Военная проза');
insert into GENRES (id, name) values (10, 'Фентези');
insert into GENRES (id, name) values (11, 'Поэма');
insert into GENRES (id, name) values (12, 'Пьеса');
insert into GENRES (id, name) values (13, 'Военная проза');
insert into GENRES (id, name) values (14, 'Сказка');

insert into AUTHORS (id, name) values ( 1, 'Михаил Булгаков');
insert into AUTHORS (id, name) values ( 2, 'Ильф и Петров');
insert into AUTHORS (id, name) values ( 3, 'Федор Достоевский');
insert into AUTHORS (id, name) values ( 4, 'Иван Крылов');
insert into AUTHORS (id, name) values ( 5, 'Михаил Лермонтов');
insert into AUTHORS (id, name) values ( 6, 'Виктор Пелевин');
insert into AUTHORS (id, name) values ( 7, 'Маркус Зусак');
insert into AUTHORS (id, name) values ( 8, 'Слава Сэ');
insert into AUTHORS (id, name) values ( 9, 'Анджей Сапковский');
insert into AUTHORS (id, name) values (10, 'Гомер');
insert into AUTHORS (id, name) values (11, 'Уильям Шекспир');
insert into AUTHORS (id, name) values (12, 'Оскар Уайльд');
insert into AUTHORS (id, name) values (13, 'Фрэнсис Скотт Фицджеральд');
insert into AUTHORS (id, name) values (14, 'Джером Дэвид Сэлинджер');
insert into AUTHORS (id, name) values (15, 'Антуан де Сент-Экзюпери');
insert into AUTHORS (id, name) values (16, 'Ханс Кристиан Андерсен');
insert into AUTHORS (id, name) values (17, 'Братья Гримм');

insert into BOOKS (id, name, author_id) values ( 1, 'Мастер и Маргарита', 1);                               -- Русская классика  -- Драма    -- Мистика
insert into BOOKS (id, name, author_id) values ( 2, 'Двенадцать стульев', 2);                               -- Русская классика  -- Юмор
insert into BOOKS (id, name, author_id) values ( 3, 'Идиот', 3);                                            -- Русская классика  -- Драма
insert into BOOKS (id, name, author_id) values ( 4, 'Мартышка и очки', 4);                                  -- Русская классика  -- Басня
insert into BOOKS (id, name, author_id) values ( 5, 'Мцыри', 5);                                            -- Русская классика  -- Поэма
insert into BOOKS (id, name, author_id) values ( 6, 'Чапаев и Пустота', 6);                                 -- Современная проз  -- Фантастика
insert into BOOKS (id, name, author_id) values ( 7, 'Книжный вор', 7);                                      -- Современная проз  -- Военная проза
insert into BOOKS (id, name, author_id) values ( 8, 'Сантехник, его кот, жена и другие подробности', 8);    -- Современная проз  -- Юмор
insert into BOOKS (id, name, author_id) values ( 9, 'Последнее желание', 9);                                -- Современная проз  -- Фентези
insert into BOOKS (id, name, author_id) values (10, 'Меч Предназначения', 9);                               -- Современная проз  -- Фентези
insert into BOOKS (id, name, author_id) values (11, 'Кровь эльфов', 9);                                     -- Современная проз  -- Фентези
insert into BOOKS (id, name, author_id) values (12, 'Крещение огнём', 9);                                   -- Современная проз  -- Фентези
insert into BOOKS (id, name, author_id) values (13, 'Башня Ласточки', 9);                                   -- Современная проз  -- Фентези
insert into BOOKS (id, name, author_id) values (14, 'Владычица Озера', 9);                                  -- Современная проз  -- Фентези
insert into BOOKS (id, name, author_id) values (15, 'Сезон гроз', 9);                                       -- Современная проз  -- Фентези
insert into BOOKS (id, name, author_id) values (16, 'Илиада', 10);                                          -- Мировая классика  -- Поэма
insert into BOOKS (id, name, author_id) values (17, 'Король Лир', 11);                                      -- Мировая классика  -- Пьеса
insert into BOOKS (id, name, author_id) values (18, 'Портрет Дориана Грея', 12);                            -- Мировая классика  -- Драма
insert into BOOKS (id, name, author_id) values (19, 'Великий Гетсби', 13);                                  -- Мировая классика  -- Драма
insert into BOOKS (id, name, author_id) values (20, 'Над пропастью во ржи', 14);                            -- Мировая классика  -- Драма
insert into BOOKS (id, name, author_id) values (21, 'Маленький принц', 15);                                 -- Мировая классика  -- Сказка --Фэнтези
insert into BOOKS (id, name, author_id) values (22, 'Дюймовочка', 16);                                      -- Мировая классика  -- Сказка
insert into BOOKS (id, name, author_id) values (23, 'Бременские музыканты', 17);                            -- Мировая классика  -- Сказка

insert into BOOK_GENRE(book_id, genre_id) values ( 1, 1);
insert into BOOK_GENRE(book_id, genre_id) values ( 1, 4);
insert into BOOK_GENRE(book_id, genre_id) values ( 1, 5);
insert into BOOK_GENRE(book_id, genre_id) values ( 2, 1);
insert into BOOK_GENRE(book_id, genre_id) values ( 2, 6);
insert into BOOK_GENRE(book_id, genre_id) values ( 3, 1);
insert into BOOK_GENRE(book_id, genre_id) values ( 3, 4);
insert into BOOK_GENRE(book_id, genre_id) values ( 4, 1);
insert into BOOK_GENRE(book_id, genre_id) values ( 4, 7);
insert into BOOK_GENRE(book_id, genre_id) values ( 5, 1);
insert into BOOK_GENRE(book_id, genre_id) values ( 5, 11);
insert into BOOK_GENRE(book_id, genre_id) values ( 6, 2);
insert into BOOK_GENRE(book_id, genre_id) values ( 6, 8);
insert into BOOK_GENRE(book_id, genre_id) values ( 7, 2);
insert into BOOK_GENRE(book_id, genre_id) values ( 7, 9);
insert into BOOK_GENRE(book_id, genre_id) values ( 8, 2);
insert into BOOK_GENRE(book_id, genre_id) values ( 8, 6);
insert into BOOK_GENRE(book_id, genre_id) values ( 9, 2);
insert into BOOK_GENRE(book_id, genre_id) values ( 9, 10);
insert into BOOK_GENRE(book_id, genre_id) values (10, 2);
insert into BOOK_GENRE(book_id, genre_id) values (10, 10);
insert into BOOK_GENRE(book_id, genre_id) values (11, 2);
insert into BOOK_GENRE(book_id, genre_id) values (11, 10);
insert into BOOK_GENRE(book_id, genre_id) values (12, 2);
insert into BOOK_GENRE(book_id, genre_id) values (12, 10);
insert into BOOK_GENRE(book_id, genre_id) values (13, 2);
insert into BOOK_GENRE(book_id, genre_id) values (13, 10);
insert into BOOK_GENRE(book_id, genre_id) values (14, 2);
insert into BOOK_GENRE(book_id, genre_id) values (14, 10);
insert into BOOK_GENRE(book_id, genre_id) values (15, 2);
insert into BOOK_GENRE(book_id, genre_id) values (15, 10);
insert into BOOK_GENRE(book_id, genre_id) values (16, 3);
insert into BOOK_GENRE(book_id, genre_id) values (16, 11);
insert into BOOK_GENRE(book_id, genre_id) values (17, 3);
insert into BOOK_GENRE(book_id, genre_id) values (17, 12);
insert into BOOK_GENRE(book_id, genre_id) values (18, 3);
insert into BOOK_GENRE(book_id, genre_id) values (18, 4);
insert into BOOK_GENRE(book_id, genre_id) values (19, 3);
insert into BOOK_GENRE(book_id, genre_id) values (19, 4);
insert into BOOK_GENRE(book_id, genre_id) values (20, 3);
insert into BOOK_GENRE(book_id, genre_id) values (20, 4);
insert into BOOK_GENRE(book_id, genre_id) values (21, 3);
insert into BOOK_GENRE(book_id, genre_id) values (21, 10);
insert into BOOK_GENRE(book_id, genre_id) values (21, 14);
insert into BOOK_GENRE(book_id, genre_id) values (22, 3);
insert into BOOK_GENRE(book_id, genre_id) values (22, 14);
insert into BOOK_GENRE(book_id, genre_id) values (23, 3);
insert into BOOK_GENRE(book_id, genre_id) values (23, 14);
