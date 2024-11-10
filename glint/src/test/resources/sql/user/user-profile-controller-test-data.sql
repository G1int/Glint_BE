insert into `user` (id, email, created_date, modified_date, archived, name, provider, role)
values (1, 'test@kakao.com', '2021-07-01 00:00:00', '2021-07-01 00:00:00', 0, 'test', 'KAKAO', 'USER');

insert into `user` (id, email, created_date, modified_date, archived, name, provider, role)
values (2, 'test2@kakao.com', '2021-07-01 00:00:00', '2021-07-01 00:00:00', 0, 'test2', 'KAKAO', 'USER');

insert into `user_detail` (id, created_date, modified_date, birthdate, gender, height, nickname, profile_image, user_id)
values(1, '2021-07-01 00:00:00', '2021-07-01 00:00:00', '1990-01-01', 'MALE', '180', 'test', 'test.jpg', 1);

insert into `user_detail` (id, created_date, modified_date, birthdate, gender, height, nickname, profile_image, user_id)
values(2, '2021-07-01 00:00:00', '2021-07-01 00:00:00', '1990-01-01', 'MALE', '180', 'test2', 'test.jpg', 2);


insert into `drinking`(drinking_id, created_date, modified_date, drinking_name)
values (1, '2021-07-01 00:00:00', '2021-07-01 00:00:00', '마시지 않음');

insert into `location` (location_id, created_date, modified_date, city, state)
values(1, '2021-07-01 00:00:00', '2021-07-01 00:00:00', '종로구', '서울');

insert into `religion` (religion_id, created_date, modified_date, religion_name)
values (1, '2021-07-01 00:00:00', '2021-07-01 00:00:00', '무교');

insert into `smoking` (smoking_id, created_date, modified_date, smoking_name)
values (1, '2021-07-01 00:00:00', '2021-07-01 00:00:00', '비흡연');

insert into `university_category` (university_category_id, created_date, modified_date, university_category_name)
values (1, '2021-07-01 00:00:00', '2021-07-01 00:00:00', '명문대');

insert into `university` (university_id, created_date, modified_date, university_department, university_name, university_category_id)
values (1, '2021-07-01 00:00:00', '2021-07-01 00:00:00', '컴퓨터공학과', '서울대학교', 1);

insert into `work` (work_id, created_date, modified_date, work_name, work_category_id)
values (1, '2021-07-01 00:00:00', '2021-07-01 00:00:00', '개발자', null);

insert into `user_profile` (id, created_date, modified_date, self_introduction, user_id, drinking_id, location_id, religion_id, smoking_id, university_id, work_id)
values (1, '2021-07-01 00:00:00', '2021-07-01 00:00:00', '안녕하세요 자기소개입니다.', 1, 1, 1, 1, 1, 1, 1);

insert into user_profile_hashtags (user_profile_id, hashtag) values (1, 'INTJ');
