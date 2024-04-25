CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";-- liberar o uso da funçao UUID generate
CREATE TABLE cliente
(
    id      UUID PRIMARY KEY,
    nome    VARCHAR(50),
    email VARCHAR(255),
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE item
(
    id      UUID PRIMARY KEY,
    nome    VARCHAR(50),
    estoque INT,
    valor   DECIMAL(10, 2)
);

CREATE TABLE venda
(
    id          UUID PRIMARY KEY,
    total       FLOAT,
    client_id   UUID,
    dia         DATE,
    FOREIGN KEY (client_id) REFERENCES client (id)
);

CREATE TABLE venda_item
(
    id              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    venda_id        UUID,
    item_vendido_id UUID,
    FOREIGN KEY (venda_id) REFERENCES Venda (id),
    FOREIGN KEY (item_vendido_id) REFERENCES Itens (id)
);
CREATE TABLE usuario (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          nome TEXT NOT NULL,
                          email VARCHAR(255) UNIQUE NOT NULL,
                          senha VARCHAR(255) NOT NULL
);


