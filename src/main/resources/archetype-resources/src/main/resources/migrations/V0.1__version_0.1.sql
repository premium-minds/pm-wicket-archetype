create table UserApplication (
    id int4 not null,
    cipheredPassword varchar(255),
    email varchar(255) not null,
    enabled boolean not null,
    name varchar(255) not null,
    profile_id int4 not null,
    primary key (id)
);

create table UserProfile (
    id int4 not null,
    description varchar(255) not null,
    primary key (id)
);

create table UserProfile_permissions (
    UserProfile_id int4 not null,
    permissions varchar(255)
);

create sequence user_application_seq start 1 increment 50;
create sequence user_profile_seq start 1 increment 50;

alter table UserApplication 
    add constraint FK1p2jre37o2xffsb6yqmbuj0dd 
    foreign key (profile_id) 
    references UserProfile;

alter table UserProfile_permissions 
    add constraint FKm4ohewg4nkgjdcei09hvqwpow 
    foreign key (UserProfile_id) 
    references UserProfile;

-- insert profile with permissions
INSERT INTO UserProfile (id, description) VALUES (nextval('user_profile_seq'), 'Users');
INSERT INTO UserProfile_permissions (UserProfile_id, permissions) VALUES (currval('user_profile_seq'), 'ACCESS_HOMEPAGE');

-- insert user template@premium-minds.com with password 'test'
INSERT INTO UserApplication (id, cipheredPassword, email, name, profile_id, enabled) VALUES (nextval('user_application_seq'), '$2a$10$7QsqZ6HFowLBN/xWf8Q.AOY46b.6ETdxQfgarSC.z0IX0ByHPTofO', 'template@premium-minds.com', 'Template User', currval('user_profile_seq'), TRUE);
