CREATE TABLE fio_tecnico (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,

    -- Composição fibrosa
    -- Regra: ordenar por percentual decrescente antes do INSERT (responsabilidade da aplicação)
    -- fibra_1/percentual_1 obrigatórios — todo fio tem ao menos uma fibra com percentual
    fibra_1         ENUM('CO','PES','CV','PA','WO','LI','AC','EL') NOT NULL,
    percentual_1    DECIMAL(5,2)                                   NOT NULL,

    fibra_2         ENUM('CO','PES','CV','PA','WO','LI','AC','EL') NULL,
    percentual_2    DECIMAL(5,2)                                   NULL,

    fibra_3         ENUM('CO','PES','CV','PA','WO','LI','AC','EL') NULL,
    percentual_3    DECIMAL(5,2)                                   NULL,

    fibra_4         ENUM('CO','PES','CV','PA','WO','LI','AC','EL') NULL,
    percentual_4    DECIMAL(5,2)                                   NULL,

    -- Titulagem
    sistema_titulo  ENUM('NE','NM','DTEX','DEN')                   NOT NULL DEFAULT 'NE',
    titulo_valor    DECIMAL(6,2)                                   NOT NULL DEFAULT 0.00,

    -- Estrutura
    numero_cabos    TINYINT UNSIGNED                               NOT NULL DEFAULT 1,

    -- Processo
    preparacao      ENUM('CARDED','COMBED','N/A')                  NOT NULL DEFAULT 'N/A',
    sistema_fiacao  ENUM('RING','COMPACT','OE','AIRJET','VORTEX','OUTRO') NOT NULL DEFAULT 'OUTRO',

    -- Torção
    torcao_direcao      ENUM('S','Z')   NOT NULL DEFAULT 'Z',
    torcao_nominal_tpm  DECIMAL(8,2)   NULL,       -- não participa da UNIQUE, pode ser NULL

    -- Metadados
    observacao_tecnica  VARCHAR(255)   NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    -- Auditoria
    data_cadastro   DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_alteracao  DATETIME  NULL,
    criado_por      BIGINT    NULL,
    alterado_por    BIGINT    NULL,

    -- Unicidade técnica
    CONSTRAINT uq_fio_tecnico UNIQUE (
        fibra_1, percentual_1,
        fibra_2, percentual_2,
        fibra_3, percentual_3,
        fibra_4, percentual_4,
        sistema_titulo,
        titulo_valor,
        numero_cabos,
        preparacao,
        sistema_fiacao,
        torcao_direcao
    ),

    -- Integridade dos pares: fibra e percentual devem ser preenchidos juntos
    CONSTRAINT chk_par_2 CHECK (
        (fibra_2 IS NULL AND percentual_2 IS NULL) OR
        (fibra_2 IS NOT NULL AND percentual_2 IS NOT NULL)
    ),
    CONSTRAINT chk_par_3 CHECK (
        (fibra_3 IS NULL AND percentual_3 IS NULL) OR
        (fibra_3 IS NOT NULL AND percentual_3 IS NOT NULL)
    ),
    CONSTRAINT chk_par_4 CHECK (
        (fibra_4 IS NULL AND percentual_4 IS NULL) OR
        (fibra_4 IS NOT NULL AND percentual_4 IS NOT NULL)
    ),

    -- Integridade de sequência: não pode pular posição
    CONSTRAINT chk_seq_3 CHECK (fibra_3 IS NULL OR fibra_2 IS NOT NULL),
    CONSTRAINT chk_seq_4 CHECK (fibra_4 IS NULL OR fibra_3 IS NOT NULL),

    -- Percentuais válidos
    CONSTRAINT chk_pct_1 CHECK (percentual_1 > 0 AND percentual_1 <= 100),
    CONSTRAINT chk_pct_2 CHECK (percentual_2 IS NULL OR (percentual_2 > 0 AND percentual_2 <= 100)),
    CONSTRAINT chk_pct_3 CHECK (percentual_3 IS NULL OR (percentual_3 > 0 AND percentual_3 <= 100)),
    CONSTRAINT chk_pct_4 CHECK (percentual_4 IS NULL OR (percentual_4 > 0 AND percentual_4 <= 100)),

    INDEX idx_titulo  (sistema_titulo, titulo_valor),
    INDEX idx_fibra_1 (fibra_1)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;