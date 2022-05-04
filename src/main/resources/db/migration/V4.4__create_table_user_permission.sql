create table if not exists user_permission(
    id_user bigint(20) not null,
    id_permission bigint(20) not null,
    primary key (id_user, id_permission),
    constraint fk_user_permission foreign key (id_user) references user (id),
    constraint fk_user_permission_permission foreign key (id_permission) references permission (id)
);