drop table if exists product;

create table product (id bigint not null auto_increment, amount integer, product_name varchar(255) not null, product_uuid varchar(255) not null, primary key (id)) engine=MyISAM;
alter table product add constraint UK_rbmeg5iwg3tnxa75y9yecux5g unique (product_uuid);

INSERT INTO product (id, amount, product_name, product_uuid) VALUES
(1, 15, 'product1', '1-1-1');
