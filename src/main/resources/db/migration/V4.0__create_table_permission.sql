create table if not exists permission (
    id bigint(20) not null auto_increment,
    description varchar(255) default null,
    primary key (id)
);