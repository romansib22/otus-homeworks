create table accounts (
                          id varchar(36) primary key,
                          number varchar(12),
                          client_id varchar(10),
                          balance int,
                          locked boolean
);

insert into accounts values('141750ee-5810-4e41-bd4e-27822497882c', '100000000001', '1000000001', 100, false);
insert into accounts values('fedbf4e5-7140-4349-a072-89808493f18e', '100000000002', '1000000002', 100, false);
insert into accounts values('bbd96dbf-4d72-4563-8d97-6746ed2c5f31', '100000000003', '1000000002', 100, false);


