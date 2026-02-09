-- auto-generated definition
create type user_status as enum ('ACTIVE', 'INACTIVE', 'PENDING', 'BLOCKED', 'DELETED', 'SUSPENDED');
alter type user_status owner to postgres;

-- auto-generated definition
create type user_role as enum ('USER', 'ADMIN', 'MANAGER');
alter type user_role owner to postgres;

