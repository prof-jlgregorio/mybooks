create table if not exists permission (
    id int not null auto_increment,
    user_id int not null,
    role_id int not null,
    primary key (id),
    unique (user_id, role_id),
    foreign key (user_id) references user (id),
    foreign key (role_id) references role (id)
);