create table if not exists tasks
(
    id       bigint not null AUTO_INCREMENT,
    title    VARCHAR(256) not null,
    memo     longtext,
    checked  boolean not null,
    deadline DATETIME(6) not null,
    primary key (id),
    index idx_checked (checked),
    index idx_deadline (deadline)
) ENGINE=InnoDB default CHARSET=utf8mb4 collate=utf8mb4_bin;
