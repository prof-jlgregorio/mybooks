create table if not exists user (
    id int not null auto_increment,
    full_name varchar(50) not null,
    user_name varchar(100) not null,
    password varchar(100) not null,
    account_non_expired tinyint not null,
    account_non_locked tinyint not null,
    credentials_non_expired tinyint not null,
    enabled tinyint not null,
    primary key(id),
    unique (user_name)
);