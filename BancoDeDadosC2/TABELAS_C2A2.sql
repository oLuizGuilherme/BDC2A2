create table contas (
id int not null,
tipo int not null,
data_quitacao date,
constraint conta_pk
    primary key(id)
);

create table parcelas (
id int not null,
id_conta int not null,
data_vencimento date not null,
data_pagamento date,
numero_parcela int not null,
valor number(10, 2) not null,
constraint parcela_pk
    primary key(id),
constraint parcela_conta_fk
    foreign key (id_conta)
        references contas(id)
);
