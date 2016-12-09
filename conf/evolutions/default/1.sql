# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table marker (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  file_name                     varchar(255),
  pattern                       TEXT,
  structure_id                  bigint,
  constraint uq_marker_structure_id unique (structure_id),
  constraint pk_marker primary key (id)
);

create table structure (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  marker_id                     bigint,
  color_r                       float,
  color_g                       float,
  color_b                       float,
  position_x                    double,
  position_y                    double,
  definition                    TEXT,
  constraint uq_structure_marker_id unique (marker_id),
  constraint pk_structure primary key (id)
);

alter table marker add constraint fk_marker_structure_id foreign key (structure_id) references structure (id) on delete restrict on update restrict;

alter table structure add constraint fk_structure_marker_id foreign key (marker_id) references marker (id) on delete restrict on update restrict;


# --- !Downs

alter table marker drop foreign key fk_marker_structure_id;

alter table structure drop foreign key fk_structure_marker_id;

drop table if exists marker;

drop table if exists structure;

