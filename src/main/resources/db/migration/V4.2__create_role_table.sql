create table if not exists role (
    id int not null auto_increment,
    name varchar(50) not null,
    constraint PK_AUTHORITY primary key (id)
);