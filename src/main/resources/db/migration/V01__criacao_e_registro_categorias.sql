CREATE TABLE categorias (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categorias (nome) VALUES ('Lazer');
INSERT INTO categorias (nome) VALUES ('Alimentacao');
INSERT INTO categorias (nome) VALUES ('Mercado');
INSERT INTO categorias (nome) VALUES ('Farmacia');
INSERT INTO categorias (nome) VALUES ('Outros');