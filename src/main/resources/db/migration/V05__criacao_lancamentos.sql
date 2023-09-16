create table lancamentos (

	id bigint primary key auto_increment not null,
    descricao varchar(255) not null,
    data_vencimento date not null,
    data_pagamento date,
    valor decimal(10, 2) not null,
    observacao varchar(100),
    tipo varchar(20) not null,
    id_categoria bigint not null,
    id_pessoa bigint not null,
    foreign key(id_categoria) references categorias(id),
    foreign key(id_pessoa) references pessoas(id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;