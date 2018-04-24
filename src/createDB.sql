
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


create table genres(
  genre varchar(100) not null,
  primary key (genre)
)ENGINE=InnoDB CHARACTER SET=UTF8;

create table countrys(
  country varchar(100) not null,
  primary key (country)
)ENGINE=InnoDB CHARACTER SET=UTF8;

create table actors(
  name_actor varchar(200) not null,
  portrait varchar(100),
  primary key(name_actor)
)ENGINE=InnoDB CHARACTER SET=UTF8;

create table connections_countrys(
  id_connection int not null auto_increment,
  film int not null,
  country varchar(100) not null,
  foreign key (film) references films(id_film)
    on update cascade
    on delete restrict,
  foreign key (country) references countrys(country)
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
show tables;

/*
SELECT name_film FROM names_film;

select *, (select group_concat(names_film.name_film SEPARATOR ' / ') from names_film where names_film.id_film = films.id_film)  as name_film  from films;


SELECT * FROM films left join names_film on films.id_film = names_film.id_film;

select  JSON_ARRAY(GROUP_CONCAT(names_film.name_film SEPARATOR ' / ')) as name_film from names_film where names_film.id_film = 10;

SELECT * FROM films where id_film = (select id_film from names_film where name_film = "Пассажиры");
SELECT name_film FROM names_film;


alter table films add year_of_release int(4);
alter table films change year_of_release year_of_release int(4);
describe films;

insert into films(name_film, rating, description, poster, path, year_of_release) values ();


select *, (select group_concat(names_film.name_film  SEPARATOR ' / ') from names_film where names_film.id_film = films.id_film) as name_film FROM films WHERE true and films.id_film = (select names_film.id_film from names_film where names_film.name_film = Антропоид)
