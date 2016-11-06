# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table marker (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  file_name                     varchar(255),
  pattern                       varchar(255),
  constraint pk_marker primary key (id)
);


# --- !Downs

drop table if exists marker;

