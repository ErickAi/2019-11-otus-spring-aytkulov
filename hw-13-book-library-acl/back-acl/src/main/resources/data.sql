-- select 1; -- first query does not insert data. I have no idea what's going on. @formatter:off
INSERT INTO USERS (ID, EMAIL, NAME, PASSWORD) VALUES
       (1, 'a@a.a', 'Admin Admin', '{noop}password'),
       (2, 'u@u.u', 'User User', '{noop}password');

INSERT INTO USER_ROLE (ROLE, USER_ID) VALUES
       ('ROLE_ADMIN', 1),
       ('ROLE_USER', 1),
       ('ROLE_USER', 2);

insert into GENRES (ID, NAME) values
( 1, 'g'),
( 2, 'genre'),
( 3, 'Русская классика'),
( 4, 'Современная проза'),
( 5, 'Мировая классика'),
( 6, 'Драма'),
( 7, 'Мистика'),
( 8, 'Юмор'),
( 9, 'Басня'),
(10, 'Фантастика'),
(11, 'Военная проза'),
(12, 'Фентези'),
(13, 'Поэма'),
(14, 'Пьеса'),
(15, 'Сказка');

insert into AUTHORS (id, name) values
( 1, 'a'),
( 2, 'author'),
( 3, 'Михаил Булгаков'),
( 4, 'Ильф и Петров'),
( 5, 'Федор Достоевский'),
( 6, 'Иван Крылов'),
( 7, 'Михаил Лермонтов'),
( 8, 'Виктор Пелевин'),
( 9, 'Маркус Зусак'),
(10, 'Слава Сэ'),
(11, 'Анджей Сапковский'),
(12, 'Гомер'),
(13, 'Уильям Шекспир'),
(14, 'Оскар Уайльд'),
(15, 'Фрэнсис Скотт Фицджеральд'),
(16, 'Джером Дэвид Сэлинджер'),
(17, 'Антуан де Сент-Экзюпери'),
(18, 'Ханс Кристиан Андерсен'),
(19, 'Братья Гримм');

insert into BOOKS (id, name, author_id) values
( 1, 'b', 1),                                                -- g
( 2, 'book', 2),                                             -- g  -- genre
( 3, 'b book', 2),                                           -- g  -- genre
( 4, 'Мастер и Маргарита', 3),                               -- Русская классика  -- Драма    -- Мистика
( 5, 'Двенадцать стульев', 4),                               -- Русская классика  -- Юмор
( 6, 'Идиот', 5),                                            -- Русская классика  -- Драма
( 7, 'Мартышка и очки', 6),                                  -- Русская классика  -- Басня
( 8, 'Мцыри', 7),                                            -- Русская классика  -- Поэма
( 9, 'Чапаев и Пустота', 8),                                 -- Современная проз  -- Фантастика
(10, 'Книжный вор', 9),                                      -- Современная проз  -- Военная проза
(11, 'Сантехник, его кот, жена и другие подробности', 10),   -- Современная проз  -- Юмор
(12, 'Ведьмак. Последнее желание', 11),                      -- Современная проз  -- Фентези
(13, 'Ведьмак. Меч Предназначения', 11),                     -- Современная проз  -- Фентези
(14, 'Ведьмак. Кровь эльфов', 11),                           -- Современная проз  -- Фентези
(15, 'Ведьмак. Крещение огнём', 11),                         -- Современная проз  -- Фентези
(16, 'Ведьмак. Башня Ласточки', 11),                         -- Современная проз  -- Фентези
(17, 'Ведьмак. Владычица Озера', 11),                        -- Современная проз  -- Фентези
(18, 'Ведьмак. Сезон гроз', 11),                             -- Современная проз  -- Фентези
(19, 'Илиада', 12),                                          -- Мировая классика  -- Поэма
(20, 'Король Лир', 13),                                      -- Мировая классика  -- Пьеса
(21, 'Портрет Дориана Грея', 14),                            -- Мировая классика  -- Драма
(22, 'Великий Гетсби', 15),                                  -- Мировая классика  -- Драма
(23, 'Над пропастью во ржи', 16),                            -- Мировая классика  -- Драма
(24, 'Маленький принц', 17),                                 -- Мировая классика  -- Сказка --Фэнтези
(25, 'Дюймовочка', 18),                                      -- Мировая классика  -- Сказка
(26, 'Бременские музыканты', 19);                            -- Мировая классика  -- Сказка

insert into BOOK_GENRE(book_id, genre_id) values
( 1, 1),
( 2, 1),
( 2, 2),
( 3, 1),
( 3, 2),
( 4, 3),
( 4, 6),
( 4, 7),
( 5, 3),
( 5, 8),
( 6, 3),
( 6, 6),
( 7, 3),
( 7, 9),
( 8, 3),
( 8, 13),
( 9, 4),
( 9, 10),
(10, 4),
(10, 2),
(10, 11),
(11, 4),
(11, 8),
(12, 4),
(12, 12),
(13, 4),
(13, 12),
(14, 4),
(14, 12),
(15, 4),
(15, 12),
(16, 4),
(16, 12),
(17, 4),
(17, 12),
(18, 4),
(18, 2),
(18, 12),
(19, 5),
(19, 13),
(20, 5),
(20, 14),
(21, 5),
(21, 6),
(22, 5),
(22, 6),
-- (20, 3),
-- (20, 4),
(24, 5),
(24, 12),
(24, 2),
(24, 15),
(25, 5),
(25, 15),
(26, 5),
(26, 15);

insert into COMMENTS(id, book_id, user_id, ENTRY) values (1, 1, 1, 'Small comment for book "b".');
insert into COMMENTS(id, book_id, user_id, ENTRY) values (2, 1, 2, 'Another small comment for book "b".');
insert into COMMENTS(id, book_id, user_id, ENTRY) values (3, 2, 1, 'Small comment for book "book".');
insert into COMMENTS(id, book_id, user_id, ENTRY) values (4, 2, 2, 'Big review and critique for little book "book". 6000 characters except this sentences.
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ac ipsum felis. Donec tincidunt vehicula libero quis luctus. Duis ac aliquet diam, at congue enim. Nunc vulputate, quam vitae luctus vestibulum, lorem elit consectetur tellus, eget lacinia ipsum nisl eu enim. Cras sodales lectus eget gravida laoreet. Integer iaculis ante id tempus sodales. Maecenas et tincidunt quam, ut commodo odio. Phasellus ornare mi non dui semper interdum. Praesent in magna bibendum, imperdiet neque ut, sagittis metus. Mauris sollicitudin blandit viverra. Cras sed pulvinar mauris. Nam vel eros viverra, ornare felis ac, malesuada nibh. Phasellus et turpis laoreet dui suscipit eleifend eget vel sem.
Nunc imperdiet sapien augue, ut dignissim erat pulvinar nec. Nunc nibh erat, porta id enim nec, lacinia mattis risus. Duis quis euismod arcu. Proin nec enim id arcu fringilla imperdiet nec vitae enim. Donec in sem lacus. Fusce bibendum, est in pretium molestie, libero ipsum dapibus massa, in accumsan orci tellus quis erat. Etiam feugiat mauris id nisl feugiat maximus. Integer nec venenatis nunc. Aliquam ultricies luctus leo id scelerisque. Fusce id aliquet nisi. Donec luctus justo quis leo pharetra, id gravida erat rhoncus. Maecenas sagittis quis mauris in viverra. Vestibulum et luctus elit, dictum venenatis eros. Morbi pretium nunc ut mauris luctus, eu cursus nulla ornare. Nulla eget accumsan nulla, a convallis dolor.
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam venenatis aliquet mollis. Nulla bibendum tincidunt odio id sagittis. Fusce convallis convallis risus, nec sollicitudin sapien scelerisque sagittis. Proin a rhoncus ex. Ut in porttitor quam. Donec molestie purus eget massa hendrerit luctus.
Vestibulum accumsan mollis finibus. Praesent consectetur volutpat elementum. Mauris ipsum nisi, vehicula ac quam vel, semper elementum sem. Fusce sagittis, nibh sit amet tempus vulputate, arcu odio scelerisque ipsum, eget feugiat ligula diam eu dui. Aenean vitae pretium elit. Etiam metus arcu, vulputate ac urna ut, fringilla varius augue. Fusce at mi venenatis, varius libero et, maximus augue. Maecenas vel semper lectus.
Nam in erat ac enim varius sollicitudin quis at purus. Praesent id est convallis, consectetur neque vitae, interdum velit. Nam a egestas enim, at tempus neque. Aenean vitae risus enim. Duis mollis erat sit amet rhoncus molestie. Duis nec lobortis nibh, at malesuada leo. Praesent vitae mattis magna. Aliquam vulputate risus ut turpis viverra blandit.
In quam velit, ornare et interdum sit amet, volutpat non nunc. In in pellentesque sem, ut interdum purus. Nam purus est, pretium sit amet feugiat ac, pretium et sapien. Morbi orci dui, iaculis sed sem ut, placerat placerat felis. Sed vel felis eleifend, ornare nunc mollis, ultrices arcu. Duis et nisl libero. Vestibulum suscipit nisi quis diam tincidunt fermentum. Vestibulum aliquam tempus eros, ac finibus nisi varius ornare. Etiam augue odio, suscipit a varius vitae, dapibus convallis orci. Curabitur quis neque pulvinar, tincidunt justo in, commodo nulla. Etiam pharetra metus eget elit mollis, sit amet ornare nisi commodo. Suspendisse vel augue sit amet orci viverra faucibus ac pretium erat. Mauris nec vehicula sem. Maecenas eu purus eros. Vivamus condimentum augue metus, et tempus nibh auctor sit amet. Nam faucibus ultrices dui, eget mollis est pharetra in.
Aenean a fermentum lorem. Vestibulum eleifend arcu ut elit rhoncus tincidunt. Donec mollis ipsum vel sapien volutpat, eget imperdiet ipsum consectetur. Vestibulum nec hendrerit ante. Sed scelerisque auctor urna, vitae scelerisque lorem suscipit faucibus. Sed nisi massa, rutrum quis imperdiet ut, semper id ex. Nam nec mauris faucibus, tempor libero non, luctus purus. Suspendisse porttitor molestie velit, et vulputate metus vulputate eu.
Integer mollis massa velit. Curabitur dictum lectus id nibh convallis, sed vestibulum justo consectetur. Cras eu viverra lorem. Sed viverra imperdiet ultrices. Ut pellentesque nunc sed metus tempor faucibus. Aliquam vel molestie augue. Cras aliquam, orci sit amet molestie ultricies, magna purus tempor lacus, nec ultricies leo neque ut ante. Vivamus at elementum purus. Mauris vitae lobortis tellus. Suspendisse rutrum nunc nec suscipit bibendum.
Nam maximus quam ex, vitae pellentesque enim convallis non. Aenean pharetra nunc sed enim tincidunt, ornare lobortis diam consequat. Nam vulputate elit felis, sed ornare neque lobortis et. Aenean a tincidunt mauris. Curabitur nec justo urna. Aliquam blandit tellus sed imperdiet gravida. Donec egestas nunc et porttitor laoreet.
In hac habitasse platea dictumst. Sed molestie, lectus non viverra vestibulum, metus ipsum tristique libero, eget elementum felis ligula luctus nibh. Pellentesque felis dui, luctus vitae lacus non, ultrices gravida felis. In pharetra, est et fermentum bibendum, elit risus varius tellus, at faucibus urna ipsum non ex. Fusce rhoncus lobortis lorem at viverra. Pellentesque suscipit ornare est, vel congue elit vulputate vitae. Donec sed tempor nunc. Duis maximus ante et ipsum pharetra, eu pulvinar ligula dapibus. Nam vulputate, enim nec tincidunt vehicula, leo tellus semper dui, et tempor mauris ex pulvinar nulla.
Morbi feugiat fermentum luctus. Praesent ipsum nisi, volutpat quis lacus vitae, finibus rutrum orci. Fusce varius risus et leo semper commodo. Nullam ac scelerisque lectus. Vivamus vitae cursus sapien, et tempus leo. Nulla facilisi. Curabitur suscipit, neque vitae placerat facilisis, nisl massa porttitor nulla, non condimentum magna elit sit amet tortor. Ut tortor dolor, ullamcorper nec dictum eget, dignissim in enim. Sed turpis nisi, lacinia a leo sed, volutpat gravida erat. Mauris lacinia nisl turpis, id lacinia tortor placerat non. Nulla fermentum tortor massa, quis aliquam elit efficitur pellentesque.
Cras gravida et tortor ac faucibus. Duis iaculis lacus sit amet lacinia viverra. Aliquam vestibulum, nunc sed porta sodales, libero dui ultricies neque, ut rutrum sem eros quis sem volutpat.');
