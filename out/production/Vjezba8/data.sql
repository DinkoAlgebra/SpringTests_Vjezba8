insert into type(name, description)
values('CPU', 'Central processing unit');

insert into type(name, description)
values('GPU', 'Graphic processing unit');

insert into type(name, description)
values('MBO', 'Motherboard');

insert into type(name, description)
values('RAM', 'Random access memory');

insert into type(name, description)
values('STORAGE', 'Storage ');

insert into type(name, description)
values('OTHER', 'Random access memory');

insert into hardware (code, name, type_id, stock, price)
    values
        ('1234561', 'Asus TUF RTX 3080', 2, 0, 1599.00),
        ('1234562', 'EVGA XC3 RTX 3070 Ti', 2, 0, 1299.00),
        ('1234563', 'AMD Ryzen 5950X', 1, 0, 899.00),
        ('1234564', 'Samsung 980 PRO SSD 1TB', 5, 0, 299.00),
        ('1234565', 'Kingston FURY Beast DDR5 32GB', 4, 0, 699.00);

insert into USERS(id, username, password)
values
    (1, 'user', '$2a$12$h0HcS2QDb/7zPASbLa2GoOTSRP6CWK0oX7pCK.dPjkM6L5N4pNovi'), -- password = user
    (2, 'admin', '$2a$12$INo0nbj40sQrTB7b28KJput/bNltGmFyCfRsUhvy73qcXo5/XdsTG'); -- password = admin

insert into AUTHORITY (id, authority_name)
values
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_USER');

insert into USERS_AUTHORITY (user_id, authority_id)
values
    (1, 2),
    (2, 1);
