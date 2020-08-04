-- @formatter:off
drop table if exists user_role CASCADE;
drop table if exists comments CASCADE;
drop table if exists users CASCADE;
drop table if exists book_genre CASCADE;
drop table if exists genres CASCADE;
drop table if exists authors CASCADE;
drop table if exists books CASCADE;

create table users(
    id   bigint generated by default as identity,
    name varchar(512) not null,
    email varchar(128) not null unique,
    password varchar(256),
    primary key (id)
);

create table user_role(
    user_id bigint not null,
    role    VARCHAR(255),
    foreign key (user_id) references users(id)
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

create table genres(
    id   bigint generated by default as identity,
    name varchar(512) not null,
    primary key (id)
);

create table comments(
    id      bigint generated by default as identity,
    book_id bigint        not null,
    user_id bigint        not null,
    entry   text          not null,
    primary key (id),
    foreign key (book_id) references books(id),
    foreign key (user_id) references users(id)
);

create table book_genre(
    book_id  bigint not null,
    genre_id bigint not null,
    foreign key (book_id) references books,
    foreign key (genre_id) references genres
);
