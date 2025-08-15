-- nota fiscal (cabeçalho)
CREATE TABLE nota_fiscal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_nf VARCHAR(100) NOT NULL,
    fornecedor_id BIGINT NOT NULL,
    recebido_por BIGINT NOT NULL,
    valor_total DECIMAL(15,2) NOT NULL CHECK (valor_total >= 0),
    data_recebimento DATE NOT NULL,
    criado_por BIGINT NULL,
    data_cadastro DATETIME NOT NULL,
    data_alteracao DATETIME NULL,
    alterado_por BIGINT NULL,

    CONSTRAINT uk_nota_fiscal_fornecedor_codigo_nf UNIQUE (fornecedor_id, codigo_nf),
    CONSTRAINT fk_nota_fiscal_fornecedor FOREIGN KEY (fornecedor_id) REFERENCES fornecedor(id),
    CONSTRAINT fk_nota_fiscal_usuario_recebeu FOREIGN KEY (recebido_por) REFERENCES usuario(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;