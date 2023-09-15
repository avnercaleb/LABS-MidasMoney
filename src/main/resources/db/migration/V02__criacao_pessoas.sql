CREATE TABLE pessoas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    logradouro VARCHAR(255),
    numero VARCHAR(50),
    complemento VARCHAR(255),
    bairro VARCHAR(255),
    cep VARCHAR(10),
    cidade VARCHAR(255),
    estado VARCHAR(2)

)