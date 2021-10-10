CREATE TABLE football_team (
    id SERIAL,
    name varchar(255) not null,
    country varchar(255) not null,
    budget bigint not null,
    stadium_capacity bigint,
    primary key(id)
);

CREATE TABLE players (
    id SERIAL,
    name varchar(255) not null,
    surname varchar(255),
    country varchar(255) not null,
    age bigint not null,
    football_team_id bigint not null,
    PRIMARY KEY (id),
    FOREIGN KEY (football_team_id) REFERENCES football_team(id)
);

CREATE TABLE coach (
    id SERIAL,
    name varchar(255) not null,
    surname varchar(255),
    age bigint not null,
    position varchar(255),
    category varchar(255) not null,
    football_team_id bigint not null,
    PRIMARY KEY(id),
    FOREIGN KEY(football_team_id) REFERENCES football_team(id)
);