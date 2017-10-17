# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table session (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  constraint pk_session primary key (id)
);

create table session_user (
  id                            bigint auto_increment not null,
  user_id                       bigint,
  is_host                       tinyint(1) default 0,
  session_id                    bigint,
  constraint uq_session_user_user_id unique (user_id),
  constraint pk_session_user primary key (id)
);

create table structure (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  session_id                    bigint,
  definition                    LONGTEXT,
  constraint uq_structure_session_id unique (session_id),
  constraint pk_structure primary key (id)
);

create table sync_history (
  id                            bigint auto_increment not null,
  structure_id                  bigint,
  session_id                    bigint,
  sender_id                     bigint,
  activity_type                 varchar(255),
  active                        tinyint(1) default 0,
  constraint pk_sync_history primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  device_id                     varchar(255),
  token                         varchar(255),
  constraint pk_user primary key (id)
);

alter table session_user add constraint fk_session_user_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table session_user add constraint fk_session_user_session_id foreign key (session_id) references session (id) on delete restrict on update restrict;
create index ix_session_user_session_id on session_user (session_id);

alter table structure add constraint fk_structure_session_id foreign key (session_id) references session (id) on delete restrict on update restrict;

alter table sync_history add constraint fk_sync_history_structure_id foreign key (structure_id) references structure (id) on delete restrict on update restrict;
create index ix_sync_history_structure_id on sync_history (structure_id);

alter table sync_history add constraint fk_sync_history_session_id foreign key (session_id) references session (id) on delete restrict on update restrict;
create index ix_sync_history_session_id on sync_history (session_id);

alter table sync_history add constraint fk_sync_history_sender_id foreign key (sender_id) references user (id) on delete restrict on update restrict;
create index ix_sync_history_sender_id on sync_history (sender_id);


# --- !Downs

alter table session_user drop foreign key fk_session_user_user_id;

alter table session_user drop foreign key fk_session_user_session_id;
drop index ix_session_user_session_id on session_user;

alter table structure drop foreign key fk_structure_session_id;

alter table sync_history drop foreign key fk_sync_history_structure_id;
drop index ix_sync_history_structure_id on sync_history;

alter table sync_history drop foreign key fk_sync_history_session_id;
drop index ix_sync_history_session_id on sync_history;

alter table sync_history drop foreign key fk_sync_history_sender_id;
drop index ix_sync_history_sender_id on sync_history;

drop table if exists session;

drop table if exists session_user;

drop table if exists structure;

drop table if exists sync_history;

drop table if exists user;

