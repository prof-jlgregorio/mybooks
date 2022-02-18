create table if not exists book (
    id integer not null auto_increment,
    title varchar(50) not null,
    author_id integer not null,
    primary key (id),
    foreign key (author_id) references author(id)
);