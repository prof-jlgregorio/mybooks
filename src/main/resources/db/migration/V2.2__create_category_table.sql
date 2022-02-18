create table if not exists category (
    id integer not null auto_increment,
    name varchar(50) not null,
    description varchar(100),
    primary key(id)
);