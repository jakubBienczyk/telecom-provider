insert into customer
values ('1', 'John'),
       ('2', 'Mark');

insert into phone_number(id, number, active, customer_id)
values ('1', '111-1', FALSE, '1'),
       ('2', '111-2', false, '1'),
       ('3', '222-1', false, '2'),
       ('4', '222-2', true, '2'),
       ('5', '222-3', false, '2');