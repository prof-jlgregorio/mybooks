create table if not exists user (
    id bigint(20) not null auto_increment,
    user_name varchar(255) default null,
    full_name varchar(255) default null,
    password varchar(255) default null,
    account_non_expired bit(1) default null,
    account_non_locked bit(1) default null,
    credentials_non_expired bit(1) default null,
    enabled bit(1) default null,
    primary key (id),
    unique key (user_name)
);