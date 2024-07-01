


-- 생성한 테이블 목록

create table user (
    id int primary key auto_increment,
    name varchar(100) not null,
    email varchar(100) not null,
    password varchar(100) not null,
    created_at timestamp default current_timestamp
);