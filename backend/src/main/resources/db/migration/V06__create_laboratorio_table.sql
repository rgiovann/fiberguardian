CREATE TABLE laboratorio (
    id                  BIGINT          AUTO_INCREMENT PRIMARY KEY,
    item_nota_fiscal_id BIGINT          NOT NULL,
    produto_id          BIGINT          NOT NULL,
    numero_lote         VARCHAR(45)     NOT NULL,

    -- Resultados dos ensaios
    cvm                 DECIMAL(15,2)   NOT NULL CHECK (cvm >= 0),
    pontos_finos        INTEGER         NOT NULL,
    pontos_grossos      INTEGER         NOT NULL,
    neps                INTEGER         NOT NULL CHECK (neps >= 0),
    h_pilosidade        DECIMAL(15,2)   NOT NULL CHECK (h_pilosidade > 0),
    resistencia         DECIMAL(15,2)   NOT NULL CHECK (resistencia > 0),
    alongamento         DECIMAL(15,2)   NOT NULL CHECK (alongamento > 0),
    titulo_ne           DECIMAL(15,2)   NOT NULL CHECK (titulo_ne >= 0),
    torcao_t_m          INTEGER         NOT NULL CHECK (torcao_t_m >= 0),

    -- Decisão e responsabilidade
    decisao             ENUM('APROVADO','REPROVADO') NOT NULL,
    liberado_por        BIGINT          NOT NULL,
    observacao_laudo    VARCHAR(255)    NULL,

    -- Datas
    data_realizacao     DATE            NOT NULL,
    data_cadastro       DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_alteracao      DATETIME        NULL,

    -- Auditoria
    criado_por          BIGINT          NULL,
    alterado_por        BIGINT          NULL,

    -- Ciclo de vida do documento
    -- Regra: SUBSTITUIDO e INVALIDADO são estados terminais — transições irreversíveis
    -- Regra: motivo_substituicao obrigatório quando situacao != ATIVO (validação na camada de serviço)
    situacao            ENUM('ATIVO','SUBSTITUIDO','INVALIDADO') NOT NULL DEFAULT 'ATIVO',
    substituido_por     BIGINT          NULL DEFAULT NULL,  -- sem FK: aponta para o futuro, registro ainda não existe no INSERT
    substitui           BIGINT          NULL DEFAULT NULL,  -- com FK: aponta para o passado, registro já existe
    motivo_substituicao VARCHAR(255)    NULL DEFAULT NULL,

    -- Unicidade
    CONSTRAINT uk_laboratorio_numero_lote
        UNIQUE (item_nota_fiscal_id, numero_lote),

    -- Chaves estrangeiras
    CONSTRAINT fk_laboratorio_item_nota_fiscal
        FOREIGN KEY (item_nota_fiscal_id)
        REFERENCES nota_item(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_laboratorio_produto
        FOREIGN KEY (produto_id)
        REFERENCES produto(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_laboratorio_liberado_por
        FOREIGN KEY (liberado_por)
        REFERENCES usuario(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_laboratorio_substitui
        FOREIGN KEY (substitui)
        REFERENCES laboratorio(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    INDEX idx_laboratorio_produto    (produto_id),
    INDEX idx_laboratorio_situacao   (situacao)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;