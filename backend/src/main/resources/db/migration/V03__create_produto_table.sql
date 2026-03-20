CREATE TABLE produto (
    id                  BIGINT          AUTO_INCREMENT PRIMARY KEY,
    codigo_produto      VARCHAR(100)    NOT NULL,
    descricao_produto   VARCHAR(255)    NULL,
    fornecedor_id       BIGINT          NOT NULL,
    fio_tecnico_id      BIGINT          NULL,
    data_cadastro       DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_alteracao      DATETIME        NULL,
    criado_por          BIGINT          NULL,
    alterado_por        BIGINT          NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_produto_fornecedor
        FOREIGN KEY (fornecedor_id)
        REFERENCES fornecedor(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_produto_fio_tecnico
        FOREIGN KEY (fio_tecnico_id)
        REFERENCES fio_tecnico(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    UNIQUE KEY uq_produto_fornecedor_codigo (fornecedor_id, codigo_produto),

    INDEX idx_produto_fio_tecnico (fio_tecnico_id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;