-- @formatter:off
drop table if exists genres;
drop table if exists authors;
drop table if exists books;
drop table if exists comments;
drop table if exists link_book_genre;

create table genres(
    id   bigint generated by default as identity,
    name varchar(512) not null,
    primary key (id)
);

create table authors(
    id   bigint generated by default as identity,
    name varchar(512) not null,
    primary key (id)
);

create table books(
    id        bigint generated by default as identity,
    name      varchar(512) not null,
    author_id bigint       not null,
    primary key (id),
    foreign key (author_id) references authors(id)
);

create table comments(
    id      bigint generated by default as identity,
    book_id bigint        not null,
    entry   text          not null,
    primary key (id),
    foreign key (book_id) references books(id)
);

create table book_genre(
    book_id  bigint not null,
    genre_id bigint not null,
    foreign key (book_id) references books,
    foreign key (genre_id) references genres
);