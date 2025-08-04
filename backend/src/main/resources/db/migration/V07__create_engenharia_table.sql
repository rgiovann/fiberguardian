CREATE TABLE engenharia  (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  engenheiro BIGINT NOT NULL,
  tipo_teste_realizado VARCHAR(45) NOT NULL,
  restricao_uso VARCHAR(45) NOT NULL,
  observacao VARCHAR(200) NULL,
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