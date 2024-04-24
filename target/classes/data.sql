CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";-- liberar o uso da funçao UUID generate
CREATE TABLE Vendedor
(
    id      UUID PRIMARY KEY,
    nome    VARCHAR(50),
    contato VARCHAR(15)
);

CREATE TABLE Itens
(
    id      UUID PRIMARY KEY,
    nome    VARCHAR(50),
    estoque INT,
    valor   DECIMAL(10, 2)
);

CREATE TABLE Venda
(
    id          UUID PRIMARY KEY,
    total       FLOAT,
    vendedor_id UUID,
    dia         DATE,
    FOREIGN KEY (vendedor_id) REFERENCES Vendedor (id)
);

CREATE TABLE venda_item
(
    id              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    venda_id        UUID,
    item_vendido_id UUID,
    FOREIGN KEY (venda_id) REFERENCES Venda (id),
    FOREIGN KEY (item_vendido_id) REFERENCES Itens (id)
);

