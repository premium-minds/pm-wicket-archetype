    create table UserApplication (
        id int4 not null,
        cipheredPassword varchar(255),
        email varchar(255),
        name varchar(255),
        enabled boolean not null,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        primary key (id)
    );

    create sequence user_application_seq;

    alter table UserApplication 
        add column profile_id int4 not null;

    create table UserProfile (
        id int4 not null,
        description varchar(255),
        primary key (id)
    );

    create table UserProfile_permissions (
        UserProfile_id int4 not null,
        permissions varchar(255),
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

    alter table UserApplication 
        add constraint FK_osisissuunph1t4fso6g9kili 
        foreign key (profile_id) 
        references UserProfile;

    alter table UserProfile_permissions 
        add constraint FK_k0q1kxhr5utwhsxq7xckj9re3 
        foreign key (UserProfile_id) 
        references UserProfile;

    create sequence user_profile_seq;

-- insert profile with permissions
INSERT INTO UserProfile (id, description) VALUES (nextval('user_profile_seq'), 'Utilizadores');
INSERT INTO UserProfile_permissions (UserProfile_id, permissions) VALUES
(currval('user_profile_seq'), 'ACCESS_HOMEPAGE');

-- insert user template@premium-minds.com with password 'teste'
INSERT INTO UserApplication (id, cipheredPassword, email, name, profile_id, enabled) VALUES (nextval('user_application_seq'), '$2a$10$7QsqZ6HFowLBN/xWf8Q.AOY46b.6ETdxQfgarSC.z0IX0ByHPTofO', 'template@premium-minds.com', 'Template User', currval('user_profile_seq'), TRUE);

