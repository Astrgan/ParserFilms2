
drop database if exists films;
CREATE DATABASE films CHARACTER SET utf8 COLLATE utf8_unicode_ci;
use films;
create table films(
  id_film int not null auto_increment,
  rating decimal(2,1),
  description varchar(1000),
  poster varchar(100),
  year_of_release int(4),
  path varchar(100),
  PRIMARY KEY ( id_film )
)ENGINE=InnoDB CHARACTER SET=UTF8;

create table names_film(
  name_film varchar(100) not null,
  id_film int not null,
  primary key(name_film),
  foreign key (id_film) references films(id_film)

)ENGINE=InnoDB CHARACTER SET=UTF8;

create table writers(
  name_writers varchar(100) not null,
  primary key (name_writers)
)ENGINE=InnoDB CHARACTER SET=UTF8;

create table genres(
  genre varchar(100) not null,
  primary key (genre)
)ENGINE=InnoDB CHARACTER SET=UTF8;

create table countries(
  country varchar(100) not null,
  primary key (country)
)ENGINE=InnoDB CHARACTER SET=UTF8;

create table actors(
  name_actor varchar(200) not null,
  portrait varchar(100),
  primary key(name_actor)
)ENGINE=InnoDB CHARACTER SET=UTF8;

create table connections_writers (
  id_connection int not null auto_increment,
  film int not null,
  writers varchar(100) not null,
  foreign key (film) references films(id_film)
    on update cascade
    on delete restrict,
  foreign key (writers) references writers(name_writers)
    on update cascade
    on delete restrict,
  primary key(id_connection)
)ENGINE=InnoDB CHARACTER SET=UTF8;

create table connections_countries (
  id_connection int not null auto_increment,
  film int not null,
  country varchar(100) not null,
  foreign key (film) references films(id_film)
    on update cascade
    on delete restrict,
  foreign key (country) references countries(country)
    on update cascade
    on delete restrict,
  primary key(id_connection)
)ENGINE=InnoDB CHARACTER SET=UTF8;

create table connections_actors(
  id_connection int not null auto_increment,
  film int not null,
  actor varchar(200) not null,
  foreign key (film) references films(id_film)
    on update cascade
    on delete restrict,
  foreign key (actor) references actors(name_actor)
    on update cascade
    on delete restrict,
  primary key(id_connection)
)ENGINE=InnoDB CHARACTER SET=UTF8;

create table connections_genres(
  id_connection int not null auto_increment,
  film int not null,
  genre varchar(100) not null,
  foreign key (film) references films(id_film)
    on update cascade
    on delete restrict,
  foreign key (genre) references genres(genre)
    on update cascade
    on delete restrict,
  primary key(id_connection)
)ENGINE=InnoDB CHARACTER SET=UTF8;


create table users(
  email varchar(100) not null,
  pass varchar(100) not null,
  avatar varchar(100),
  status_user bool DEFAULT false,
  primary key(email)
)ENGINE=InnoDB CHARACTER SET=UTF8;

create table names(
  name_user varchar(100) not null,
  email varchar(100) not null,
  primary key(name_user),
  foreign key (email) references users(email)
)ENGINE=InnoDB CHARACTER SET=UTF8;

create table comments(
  id_comment int not null auto_increment,
  comment varchar(5000) not null,
  name_user varchar(100) not null,
  date DATETIME,
  primary key(id_comment),
  foreign key (name_user) references users(name_user)

)ENGINE=InnoDB CHARACTER SET=UTF8;

create table tokens(
  id_token int not null auto_increment,
  token varchar(100) not null,
  email varchar(100) not null,
  date_token DATETIME,
  primary key(id_token),
  foreign key (email) references users(email)

)ENGINE=InnoDB CHARACTER SET=UTF8;
show tables;
/*
alter table films add year_of_release int(4);
alter table films change year_of_release year_of_release int(4);
*/