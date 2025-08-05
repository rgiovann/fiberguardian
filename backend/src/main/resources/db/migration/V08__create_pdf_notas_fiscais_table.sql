create table pdf_nota_fiscal (
  nota_fiscal_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome_arquivo VARCHAR(150) NOT NULL,
  descricao VARCHAR(150),
  content_type VARCHAR(80) NOT NULL,
  tamanho INT NOT NULL,
  data_cadastro DATETIME NOT NULL,
  data_alteracao DATETIME NULL,
  criado_por BIGINT NULL,
  alterado_por BIGINT NULL,
  CONSTRAINT fk_pdf_nota_fiscal_nota_fiscal FOREIGN KEY (nota_fiscal_id) references nota_fiscal (id)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;