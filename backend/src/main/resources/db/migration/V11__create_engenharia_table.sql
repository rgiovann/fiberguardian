CREATE TABLE engenharia (
    id                              BIGINT      AUTO_INCREMENT PRIMARY KEY,

    -- Vínculo obrigatório com laudo laboratorial reprovado
    -- Regra: laudo_laboratorio_id deve referenciar laudo com decisao = 'REPROVADO' e situacao = 'ATIVO'
    -- Validação: responsabilidade da aplicação antes do INSERT
    laudo_laboratorio_id            BIGINT          NOT NULL,

    -- Responsabilidade técnica
    engenheiro                      BIGINT          NOT NULL,

    -- Testes complementares realizados
    teste_amostra_fisica_tecelagem  BOOLEAN         NOT NULL DEFAULT FALSE,
    teste_acabamento                BOOLEAN         NOT NULL DEFAULT FALSE,

    -- Decisão técnica
    -- APROVADO_PARCIAL: uso permitido com restrição → restricao_uso obrigatório na aplicação
    -- REPROVADO:        lote rejeitado              → restricao_uso dispensável
    decisao                         ENUM(
                                        'APROVADO_PARCIAL',
                                        'REPROVADO'
                                    ) NOT NULL,
    restricao_uso                   VARCHAR(250)    NULL,

    -- Observações
    observacao                      TEXT            NULL,
    -- Quando avaliou fisicamente
    data_realizacao                 DATE            NOT NULL,
    -- Auditoria
    data_cadastro DATETIME NOT NULL,
    data_alteracao DATETIME NULL,
    criado_por BIGINT NULL,
    alterado_por BIGINT NULL,

    -- Ciclo de vida do documento
    situacao                        ENUM(
                                        'ATIVO',
                                        'SUBSTITUIDO',
                                        'INVALIDADO'
                                    ) NOT NULL DEFAULT 'ATIVO',
    substituido_por                 BIGINT          NULL DEFAULT NULL,  -- sem FK
    substitui                       BIGINT          NULL DEFAULT NULL,  -- com FK
    motivo_substituicao             VARCHAR(255)    NULL DEFAULT NULL,

    CONSTRAINT fk_eng_laudo_laboratorio
        FOREIGN KEY (laudo_laboratorio_id)
        REFERENCES laboratorio(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_eng_engenheiro
        FOREIGN KEY (engenheiro)
        REFERENCES usuario(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_eng_criado_por
        FOREIGN KEY (criado_por)
        REFERENCES usuario(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_eng_substitui
        FOREIGN KEY (substitui)
        REFERENCES engenharia(id)
        ON UPDATE CASCADE ON DELETE RESTRICT

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
-- ------------
-- IMPORTANTE
-- ------------
-- ## O Que o Backend Deve Validar Antes do INSERT
--
--1. laudo_laboratorio.decisao  = 'REPROVADO'  → só cria engenharia para laudo reprovado
--2. laudo_laboratorio.situacao = 'ATIVO'      → não cria para laudo já substituído
--3. COUNT(engenharia) WHERE laudo_laboratorio_id = ? AND situacao = 'ATIVO' = 0
--                                             → não existe outro laudo ativo para o mesmo laudo lab
