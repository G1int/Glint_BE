insert into `user` (id, email, created_date, modified_date, archived, name, provider, role)
values (1, 'test@kakao.com', '2021-07-01 00:00:00', '2021-07-01 00:00:00', 0, 'test', 'KAKAO', 'USER');

insert into `user_detail` (id, created_date, modified_date, birthdate, gender, height, nickname, profile_image, user_id)
values(1, '2021-07-01 00:00:00', '2021-07-01 00:00:00', '1990-01-01', 'MALE', '180', 'test', 'test.jpg', 1);


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


INSERT INTO meeting (meeting_id, created_date, modified_date, description, leader_user_id, man_age_max_age, man_age_min_age, man_height_max_height, man_height_min_height, meeting_image, people_capacity, status, title, woman_age_max_age, woman_age_min_age, woman_height_max_height, woman_height_min_height)
VALUES (1, '2024-07-18 04:52:15.521588', '2024-07-18 04:52:15.521588', '모두 모여모라', 1, 30, 20, 200, 140, null, 4, 'WAITING', '다모여라', 30, 20, 200, 140);

insert into meeting_drinking_ids (man_drinking_id, meeting_meeting_id, woman_drinking_id)
values (1,1,1);
insert into meeting_join_user_ids (join_user_id, meeting_meeting_id)
values (1,1);
insert into meeting_location_ids (location_id, meeting_meeting_id)
values (1,1);
insert into meeting_religion_ids (man_religion_id, meeting_meeting_id, woman_religion_id)
values (1,1,1);
insert into meeting_select_conditions (meeting_meeting_id, man_select_conditions, woman_select_conditions)
values (1,1,1);
insert into meeting_smoking_ids (man_smoking_id, meeting_meeting_id, woman_smoking_id)
values (1,1,1);
insert into join_meeting (create_at, created_date, join_date_time, join_meeting_id, meeting_id, modified_date, user_id, status)
values ('2021-07-01 00:00:00',null,'2021-07-01 00:00:00',1,1,'2021-07-01 00:00:00',1,'WAITING');

INSERT INTO glint.meeting_affiliation (meeting_meeting_id, woman_affiliation, man_affiliation) VALUES (1, null, '삼성전자');
INSERT INTO glint.meeting_affiliation (meeting_meeting_id, woman_affiliation, man_affiliation) VALUES (1, null, '서울대학교');
INSERT INTO glint.meeting_affiliation (meeting_meeting_id, woman_affiliation, man_affiliation) VALUES (1, '삼성전자', null);
INSERT INTO glint.meeting_affiliation (meeting_meeting_id, woman_affiliation, man_affiliation) VALUES (1, '서울대학교', null);


