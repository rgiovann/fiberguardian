set foreign_key_checks = 0;

delete from usuario;

set foreign_key_checks = 1;

alter table usuario auto_increment = 1;

-- afterMigrate.sql
-- Insere usuários com diferentes valores paras os campos (testes de desenvolvimento)

INSERT INTO usuario (nome, email, senha, ativo, role, data_cadastro, setor, turno) VALUES
('Ana Carolina Souza', 'ana.souza@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ADMIN', utc_timestamp, 'FIACAO', 'PRIMEIRO'),
('Bruno Lima', 'bruno.lima@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'USUARIO', utc_timestamp, 'TECELAGEM', 'SEGUNDO'),
('Clara Mendes', 'clara.mendes@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'ENG_LAB', utc_timestamp, 'PREPARACAO', 'GERAL'),
('Diego Ferreira', 'diego.ferr@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ENGENHARIA', utc_timestamp, 'BENEFICIAMENTO', 'PRIMEIRO'),
('Elisa Costa', 'elisa.costa@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'USUARIO', utc_timestamp, 'FIACAO', 'SEGUNDO'),
('Felipe Santos', 'felipe.santos@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'LABORATORIO', utc_timestamp, 'TECELAGEM', 'GERAL'),
('Gabriela Silva', 'gabriela.silva@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'ENG_LAB', utc_timestamp, 'PREPARACAO', 'PRIMEIRO'),
('Henrique Almeida', 'henrique.almeida@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ADMIN', utc_timestamp, 'BENEFICIAMENTO', 'SEGUNDO'),
('Isabela Pereira', 'isabela.pereira@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'USUARIO', utc_timestamp, 'FIACAO', 'GERAL'),
('João Oliveira', 'joao.oliveira@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'LABORATORIO', utc_timestamp, 'TECELAGEM', 'PRIMEIRO'),
('Karina Rocha', 'karina.rocha@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'USUARIO', utc_timestamp, 'PREPARACAO', 'SEGUNDO'),
('Lucas Martins', 'lucas.martins@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ENGENHARIA', utc_timestamp, 'BENEFICIAMENTO', 'GERAL'),
('Marina Ribeiro', 'marina.ribeiro@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'LABORATORIO', utc_timestamp, 'FIACAO', 'PRIMEIRO'),
('Pedro Gonçalves', 'pedro.goncalves@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'USUARIO', utc_timestamp, 'TECELAGEM', 'SEGUNDO');

INSERT INTO usuario (nome, email, senha, ativo, role, data_cadastro, setor, turno) VALUES
('Renata Azevedo', 'renata.azevedo@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'USUARIO', utc_timestamp, 'PREPARACAO', 'GERAL'),
('Tiago Barbosa', 'tiago.barbosa@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'ENGENHARIA', utc_timestamp, 'BENEFICIAMENTO', 'PRIMEIRO'),
('Vanessa Torres', 'vanessa.torres@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'LABORATORIO', utc_timestamp, 'FIACAO', 'SEGUNDO'),
('Eduardo Lima', 'eduardo.lima@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ADMIN', utc_timestamp, 'TECELAGEM', 'GERAL'),
('Fernanda Reis', 'fernanda.reis@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'ENG_LAB', utc_timestamp, 'PREPARACAO', 'PRIMEIRO'),
('Rodrigo Cunha', 'rodrigo.cunha@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ENGENHARIA', utc_timestamp, 'BENEFICIAMENTO', 'SEGUNDO'),
('Juliana Andrade', 'juliana.andrade@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'USUARIO', utc_timestamp, 'FIACAO', 'GERAL'),
('Maurício Prado', 'mauricio.prado@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ADMIN', utc_timestamp, 'TECELAGEM', 'PRIMEIRO'),
('Nathalia Nunes', 'nathalia.nunes@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'LABORATORIO', utc_timestamp, 'PREPARACAO', 'SEGUNDO'),
('Otávio Moreira', 'otavio.moreira@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'ENG_LAB', utc_timestamp, 'BENEFICIAMENTO', 'GERAL');

INSERT INTO usuario (nome, email, senha, ativo, role, data_cadastro, setor, turno) VALUES
('Patrícia Lopes', 'patricia.lopes@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'USUARIO', utc_timestamp, 'FIACAO', 'PRIMEIRO'),
('Rafael Teixeira', 'rafael.teixeira@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ENGENHARIA', utc_timestamp, 'TECELAGEM', 'SEGUNDO'),
('Simone Castro', 'simone.castro@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'ENG_LAB', utc_timestamp, 'PREPARACAO', 'GERAL'),
('Túlio Braga', 'tulio.braga@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'LABORATORIO', utc_timestamp, 'BENEFICIAMENTO', 'PRIMEIRO'),
('Viviane Rocha', 'viviane.rocha@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'USUARIO', utc_timestamp, 'FIACAO', 'SEGUNDO'),
('Wagner Neves', 'wagner.neves@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ENGENHARIA', utc_timestamp, 'TECELAGEM', 'GERAL'),
('Xênia Lopes', 'xenia.lopes@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ENG_LAB', utc_timestamp, 'PREPARACAO', 'PRIMEIRO'),
('Yuri Cardoso', 'yuri.cardoso@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'USUARIO', utc_timestamp, 'BENEFICIAMENTO', 'SEGUNDO'),
('Zuleika Amaral', 'zuleika.amaral@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ADMIN', utc_timestamp, 'FIACAO', 'GERAL'),
('Alan Tavares', 'alan.tavares@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'LABORATORIO', utc_timestamp, 'TECELAGEM', 'PRIMEIRO');

INSERT INTO usuario (nome, email, senha, ativo, role, data_cadastro, setor, turno) VALUES
('Beatriz Farias', 'beatriz.farias@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'ENG_LAB', utc_timestamp, 'PREPARACAO', 'SEGUNDO'),
('Carlos Mendes', 'carlos.mendes@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ENGENHARIA', utc_timestamp, 'BENEFICIAMENTO', 'GERAL'),
('Daniela Sousa', 'daniela.sousa@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'USUARIO', utc_timestamp, 'FIACAO', 'PRIMEIRO'),
('Elias Rocha', 'elias.rocha@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'ADMIN', utc_timestamp, 'TECELAGEM', 'SEGUNDO'),
('Fabiana Costa', 'fabiana.costa@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'LABORATORIO', utc_timestamp, 'PREPARACAO', 'GERAL'),
('Guilherme Dias', 'guilherme.dias@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ENGENHARIA', utc_timestamp, 'BENEFICIAMENTO', 'PRIMEIRO'),
('Helena Vieira', 'helena.vieira@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ENG_LAB', utc_timestamp, 'FIACAO', 'SEGUNDO'),
('Igor Martins', 'igor.martins@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'USUARIO', utc_timestamp, 'TECELAGEM', 'GERAL'),
('Janaina Silva', 'janaina.silva@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ENGENHARIA', utc_timestamp, 'PREPARACAO', 'PRIMEIRO'),
('Kelvin Torres', 'kelvin.torres@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'ENG_LAB', utc_timestamp, 'BENEFICIAMENTO', 'SEGUNDO');

INSERT INTO usuario (nome, email, senha, ativo, role, data_cadastro, setor, turno) VALUES
('Lívia Mendes', 'livia.mendes@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ADMIN', utc_timestamp, 'FIACAO', 'GERAL'),
('Marcelo Lima', 'marcelo.lima@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ENGENHARIA', utc_timestamp, 'TECELAGEM', 'PRIMEIRO'),
('Natalia Borges', 'natalia.borges@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'LABORATORIO', utc_timestamp, 'PREPARACAO', 'SEGUNDO'),
('Oscar Freitas', 'oscar.freitas@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'USUARIO', utc_timestamp, 'BENEFICIAMENTO', 'GERAL'),
('Paula Assis', 'paula.assis@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ENG_LAB', utc_timestamp, 'FIACAO', 'PRIMEIRO'),
('Quintino Lemos', 'quintino.lemos@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'ENGENHARIA', utc_timestamp, 'TECELAGEM', 'SEGUNDO'),
('Rita Moura', 'rita.moura@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ENG_LAB', utc_timestamp, 'PREPARACAO', 'GERAL'),
('Samuel Pires', 'samuel.pires@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'USUARIO', utc_timestamp, 'BENEFICIAMENTO', 'PRIMEIRO'),
('Talita Ramos', 'talita.ramos@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', FALSE, 'ADMIN', utc_timestamp, 'FIACAO', 'SEGUNDO'),
('Ulisses Andrade', 'ulisses.andrade@fiberguardian.com', '$2a$10$my8JCEHmZNTGtSI9zJoOmOA40mmTtEEFKGBydGzz6PGn.fUpVCoha', TRUE, 'ENGENHARIA', utc_timestamp, 'TECELAGEM', 'GERAL');