create table img_amostra_laboratorio (
  engenharia_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome_arquivo VARCHAR(150) NOT NULL,
  descricao VARCHAR(150),
  content_type VARCHAR(80) NOT NULL,
  tamanho BIGINT NOT NULL,
  data_cadastro DATETIME NOT NULL,
  data_alteracao DATETIME NULL,
  criado_por BIGINT NULL,
  alterado_por BIGINT NULL,
  CONSTRAINT fk_img_amostra_laboratorio_engenharia FOREIGN KEY (engenharia_id) references engenharia(id)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;