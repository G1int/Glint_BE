insert into `user` (id, email, username, created_date, modified_date, archived, name, provider, role)
values (1, 'test@kakao.com', 'kakao', '2021-07-01 00:00:00', '2021-07-01 00:00:00', 0, 'test', 'KAKAO', 'USER');


insert into `user_detail` (id, created_date, modified_date, birthdate, gender, height, nickname, profile_image, user_id)
values(1, '2021-07-01 00:00:00', '2021-07-01 00:00:00', '1990-01-01', 'MALE', '180', 'test', 'test.jpg', 1);

