CREATE TABLE engenharia  (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  engenheiro BIGINT NOT NULL,
  teste_amostra_fisica_tecelagem BOOLEAN NOT NULL DEFAULT FALSE,
  teste_acabamento BOOLEAN NOT NULL DEFAULT FALSE,
  restricao_uso VARCHAR(250) NOT NULL,
  restricao_total BOOLEAN NOT NULL DEFAULT FALSE,
  observacao VARCHAR(500) NULL,
  teste_lab BIGINT NOT NULL,
  data_realizacao DATE NOT NULL,
  data_cadastro DATETIME NOT NULL,
  data_alteracao DATETIME NULL,
  criado_por BIGINT NULL,
  alterado_por BIGINT NULL,
    CONSTRAINT fk_engenharia_usuario_engenharia FOREIGN KEY (engenheiro) REFERENCES usuario(id),
    CONSTRAINT fk_engenharia_teste_laboratorio FOREIGN KEY (teste_lab) REFERENCES laboratorio(id),
    CONSTRAINT uk_teste_lab UNIQUE (teste_lab)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;