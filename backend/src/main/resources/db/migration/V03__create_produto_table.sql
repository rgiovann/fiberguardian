CREATE TABLE produto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_produto VARCHAR(100) NOT NULL,
    descricao_produto VARCHAR(255),
    fornecedor_id BIGINT NOT NULL,
    data_cadastro DATETIME NOT NULL,
    data_alteracao DATETIME NULL,
    criado_por BIGINT NULL,
    alterado_por BIGINT NULL,
    CONSTRAINT fk_produto_fornecedor FOREIGN KEY (fornecedor_id)
        REFERENCES fornecedor(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    UNIQUE KEY uq_produto_fornecedor_codigo_desc (fornecedor_id, codigo_produto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;