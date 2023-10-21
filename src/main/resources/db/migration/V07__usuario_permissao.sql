create TABLE usuario(

    id BIGINT(20) NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(10) NOT NULL
);

create TABLE permissao(

    id BIGINT(20) NOT NULL PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL
);