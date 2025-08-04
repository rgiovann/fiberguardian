-- V02__create_usuario_table.sql
-- DEFAULT CHARSET=utf8mb4: Suporta caracteres Unicode, ideal para nomes e emails com acentos ou caracteres especiais.
-- COLLATE=utf8mb4_unicode_ci: Garante ordenação e comparação case-insensitive.
-- SENHA VARCHAR(100) porque é criptografada (hash BCrypt)
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    role ENUM('ADMIN', 'USUARIO', 'LABORATORIO', 'ENGENHARIA','ENG_LAB') NOT NULL DEFAULT 'USUARIO',
    turno ENUM('PRIMEIRO', 'SEGUNDO', 'GERAL') NOT NULL DEFAULT 'GERAL',
    setor ENUM('FIACAO', 'TECELAGEM', 'PREPARACAO','BENEFICIAMENTO') NOT NULL DEFAULT 'FIACAO',
    data_cadastro DATETIME NOT NULL,
    data_alteracao DATETIME NULL,
    criado_por BIGINT NULL,
    alterado_por BIGINT NULL,
    CONSTRAINT uk_usuario_email UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;