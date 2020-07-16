-- @formatter:off
drop table if exists users;
drop table if exists user_role;
drop table if exists link_book_genre;
drop table if exists genres;
drop table if exists authors;
drop table if exists books;
drop table if exists comments;

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
    foreign key (book_id) references books(id)
);

create table book_genre(
    book_id  bigint not null,
    genre_id bigint not null,
    foreign key (book_id) references books,
    foreign key (genre_id) references genres
);

CREATE TABLE IF NOT EXISTS acl_sid (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  principal tinyint(1) NOT NULL,
  sid varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_1 (sid,principal)
);

CREATE TABLE IF NOT EXISTS acl_class (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  class varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_2 (class)
);

CREATE TABLE IF NOT EXISTS acl_entry (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  acl_object_identity bigint(20) NOT NULL,
  ace_order int(11) NOT NULL,
  sid bigint(20) NOT NULL,
  mask int(11) NOT NULL,
  granting tinyint(1) NOT NULL,
  audit_success tinyint(1) NOT NULL,
  audit_failure tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_4 (acl_object_identity,ace_order)
);

CREATE TABLE IF NOT EXISTS acl_object_identity (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  object_id_class bigint(20) NOT NULL,
  object_id_identity bigint(20) NOT NULL,
  parent_object bigint(20) DEFAULT NULL,
  owner_sid bigint(20) DEFAULT NULL,
  entries_inheriting tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity)
);

ALTER TABLE acl_entry
ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);

ALTER TABLE acl_entry
ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);
