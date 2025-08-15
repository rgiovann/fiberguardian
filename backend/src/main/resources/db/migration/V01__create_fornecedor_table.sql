-- Tabela de fornecedor
CREATE TABLE fornecedor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_fornecedor VARCHAR(255) NOT NULL,
    cnpj VARCHAR(45) NOT NULL,
    email VARCHAR(45) NOT NULL,
    telefone VARCHAR(45) NOT NULL,
    -- outros atributos se houver
    data_cadastro DATETIME NOT NULL,
    data_alteracao DATETIME NULL,
    criado_por BIGINT NULL,
    alterado_por BIGINT NULL,
    UNIQUE KEY uq_fornecedor_nome (nome_fornecedor),
    UNIQUE KEY uq_fornecedor_cnpj (cnpj)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
