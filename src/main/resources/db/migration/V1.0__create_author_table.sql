create table if not exists author (
    id integer not null auto_increment,
    name varchar(50) not null,
    gender char(1) not null,
    primary key (id)
);