CREATE TABLE nota_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nota_fiscal_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade_recebida DECIMAL(15,2) NULL CHECK (quantidade_recebida > 0),
    numero_caixas INT NOT NULL CHECK (numero_caixas >= 0),
    valor_total_item DECIMAL(15,2) NOT NULL CHECK (valor_total_item >= 0),
    preco_unitario DECIMAL(15,2) NULL CHECK (preco_unitario >= 0),
    observacao VARCHAR(255) NULL,
    data_cadastro DATETIME NOT NULL,
    data_alteracao DATETIME NULL,
    criado_por BIGINT NULL,
    alterado_por BIGINT NULL,
    CONSTRAINT fk_nota_item_nota FOREIGN KEY (nota_fiscal_id) REFERENCES nota_fiscal(id) ON DELETE CASCADE,
    CONSTRAINT fk_nota_item_produto FOREIGN KEY (produto_id) REFERENCES produto(id),
    CONSTRAINT uk_nota_item_unico_produto_por_nota UNIQUE (nota_fiscal_id, produto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
