create table if not exists permission (
    id int not null auto_increment,
    user_id int not null,
    role_id int not null,
    constraint PK_PERMISSION primary key (id),
    constraint UN_USER_ROLE unique (user_id, role_id),
    constraint FK_PERMISION_USER foreign key (user_id) references user (id),
    constraint FK_PERMISSION_ROLE foreign key (role_id) references role (id)
);