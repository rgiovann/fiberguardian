ALTER TABLE laboratorio
    ADD COLUMN situacao ENUM('ATIVO','SUBSTITUIDO','INVALIDADO')
        NOT NULL DEFAULT 'ATIVO',

    ADD COLUMN substituido_por BIGINT NULL,

    ADD COLUMN substitui BIGINT NULL,

    ADD COLUMN motivo_substituicao VARCHAR(255) NULL,

    ADD CONSTRAINT fk_lab_substitui
        FOREIGN KEY (substitui)
        REFERENCES laboratorio(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT;

-- FK na direção que aponta para o passado, onde o registro já existe
-- substituido_por não tem FK pois o registro futuro ainda não existe no momento do INSERT
-----------------------------------------------------------
-- caso se altere campos do fornecedor ou produto (prox fase)
------------------------------------------------------------
--ALTER TABLE `laboratorio`
--    ADD COLUMN `snapshot_fornecedor`    VARCHAR(255) NOT NULL,
--    ADD COLUMN `snapshot_codigo_produto` VARCHAR(100) NOT NULL,
--    ADD COLUMN `snapshot_descricao_produto` VARCHAR(255) NULL,
--    ADD COLUMN `snapshot_numero_nf`     VARCHAR(100) NOT NULL,
--    ADD COLUMN `snapshot_data_recebimento` DATE       NOT NULL;

-------------------------
-- indices para prox fase
--------------------------
-- CREATE INDEX `idx_lab_situacao_txhash`
--    ON `laboratorio` (`situacao`, `polygon_tx_hash`);

--CREATE INDEX `idx_lab_situacao_root_txhash`
--    ON `laboratorio` (`situacao`, `merkle_root`, `polygon_tx_hash`);


    -- auditoria via blockchain merkle tree (prox fase)
    -- ADD COLUMN `merkle_path`         TEXT            NULL,
    -- ADD COLUMN `merkle_root`         VARCHAR(66)     NULL,
    -- ADD COLUMN `merkle_semana`       VARCHAR(10)     NULL,
    -- ADD COLUMN `polygon_tx_hash`     VARCHAR(66)     NULL,
    -- ADD COLUMN `merkle_posicao`      BIGINT          NULL,
    -- ciclo vida documento