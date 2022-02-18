alter table book
    add column category_id integer,
    add foreign key (category_id) references category(id);
