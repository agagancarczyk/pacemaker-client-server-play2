# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table my_activity (
  id                        bigint not null,
  user_id                   bigint not null,
  category                  varchar(255),
  location                  varchar(255),
  distance                  decimal,
  date                      timestamp,
  duration                  varchar(255),
  average_speed             decimal,
  calories_burned           integer,
  constraint pk_my_activity primary key (id))
;

create table my_friendship (
  id                        bigint not null,
  source_user_id            bigint,
  target_user_id            bigint,
  constraint pk_my_friendship primary key (id))
;

create table my_location (
  id                        bigint not null,
  activity_id               bigint not null,
  latitude                  float,
  longtitude                float,
  constraint pk_my_location primary key (id))
;

create table my_user (
  id                        bigint not null,
  firstname                 varchar(255),
  lastname                  varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  nationality               varchar(255),
  token                     varchar(255),
  constraint pk_my_user primary key (id))
;

create sequence my_activity_seq;

create sequence my_friendship_seq;

create sequence my_location_seq;

create sequence my_user_seq;

alter table my_activity add constraint fk_my_activity_my_user_1 foreign key (user_id) references my_user (id) on delete restrict on update restrict;
create index ix_my_activity_my_user_1 on my_activity (user_id);
alter table my_friendship add constraint fk_my_friendship_sourceUser_2 foreign key (source_user_id) references my_user (id) on delete restrict on update restrict;
create index ix_my_friendship_sourceUser_2 on my_friendship (source_user_id);
alter table my_friendship add constraint fk_my_friendship_targetUser_3 foreign key (target_user_id) references my_user (id) on delete restrict on update restrict;
create index ix_my_friendship_targetUser_3 on my_friendship (target_user_id);
alter table my_location add constraint fk_my_location_my_activity_4 foreign key (activity_id) references my_activity (id) on delete restrict on update restrict;
create index ix_my_location_my_activity_4 on my_location (activity_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists my_activity;

drop table if exists my_friendship;

drop table if exists my_location;

drop table if exists my_user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists my_activity_seq;

drop sequence if exists my_friendship_seq;

drop sequence if exists my_location_seq;

drop sequence if exists my_user_seq;

