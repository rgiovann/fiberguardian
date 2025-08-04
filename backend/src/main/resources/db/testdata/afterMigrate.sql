set foreign_key_checks = 0;

delete from usuario;
delete from fornecedor;
delete from produto;

set foreign_key_checks = 1;

alter table usuario auto_increment = 1;
alter table fornecedor auto_increment = 1;
alter table produto auto_increment =1;

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

INSERT INTO fornecedor (nome_fornecedor, cnpj,data_cadastro) VALUES
('CopperFibra', '12.345.678/0001-90',utc_timestamp),
('Cocari', '23.456.789/0001-01',utc_timestamp),
('Fiação São Bento', '34.567.890/0001-12',utc_timestamp),
('Tecelagem Aurora', '45.678.901/0001-23',utc_timestamp),
('Tinturaria Brasil', '56.789.012/0001-34',utc_timestamp),
('Fibras Unidas', '67.890.123/0001-45',utc_timestamp),
('Algodoeira Nacional', '78.901.234/0001-56',utc_timestamp),
('Malharia Tropical', '89.012.345/0001-67',utc_timestamp),
('CooperFibra Paraná', '90.123.456/0001-78',utc_timestamp),
('Fios Catarinenses', '11.223.344/0001-89',utc_timestamp),
('Textil Horizonte', '22.334.455/0001-90',utc_timestamp),
('Linhas Sul América', '33.445.566/0001-01',utc_timestamp),
('PoliFios Brasil', '44.556.677/0001-12',utc_timestamp),
('Tintas e Corantes Alfa', '55.667.788/0001-23',utc_timestamp),
('Tecidos Planalto', '66.778.899/0001-34',utc_timestamp),
('Cooperativa Algodoeira Paulista', '77.889.900/0001-45',utc_timestamp),
('Fibras do Vale', '88.990.011/0001-56',utc_timestamp),
('TexFibra Group', '99.100.200/0001-67',utc_timestamp),
('Malhas Premium', '12.210.310/0001-78',utc_timestamp),
('Fiação Imperial', '23.320.430/0001-89',utc_timestamp),
('Corantes Delta', '34.430.540/0001-90',utc_timestamp),
('Têxtil Santa Luzia', '45.540.650/0001-01',utc_timestamp),
('Fios & Tramas', '56.650.760/0001-12',utc_timestamp),
('Algodoeira do Cerrado', '67.760.870/0001-23',utc_timestamp),
('FibraTech Solutions', '78.870.980/0001-34',utc_timestamp),
('Indústria de Fios Paulista', '89.980.090/0001-45',utc_timestamp),
('Trama Forte Ltda', '90.090.100/0001-56',utc_timestamp),
('ColorFibra', '11.101.210/0001-67',utc_timestamp),
('TexBrasil Insumos', '22.212.320/0001-78',utc_timestamp),
('Fiação Modelo', '33.323.430/0001-89',utc_timestamp);

INSERT INTO produto (codigo_produto, descricao_produto, fornecedor_id,data_cadastro) VALUES
-- CopperFibra
('CF110018411', '36/1 OE 100%CO', 1,utc_timestamp),
('CF220045210', '14/1 OE 100%CO', 1,utc_timestamp),

-- Cocari
('CCB1800411', '40/1 PENTEADO 100%ALG', 2,utc_timestamp),
('CCB13270067', '20/1 OE 100%A T.MALHARIA', 2,utc_timestamp),

-- Fiação São Bento
('FSB520018562', '30/1 OE 100%CO', 3,utc_timestamp),
('FSB32008765', '16/1 CARD 52%CO + 48%PES', 3,utc_timestamp),

-- Tecelagem Aurora
('TAU41009873', '2/34 CARD.MERC. 100%CO', 4,utc_timestamp),
('TAU65005421', '16/1 OPEN END 100%ALG', 4,utc_timestamp),

-- Tinturaria Brasil
('TBR31002751', '12/1 PENT.EGIPCIO 100%A', 5,utc_timestamp),
('TBR92006732', '12/1 CARD 100%ALG', 5,utc_timestamp),

-- Fibras Unidas
('FBU54009210', '150/48 HIM 100%PES', 6,utc_timestamp),
('FBU72001156', '150D048FX2 TEXT AE 100%', 6,utc_timestamp),

-- Algodoeira Nacional
('ALN87009843', '10/1 OE 100% ALG', 7,utc_timestamp),
('ALN66005412', '6/1 OE COCARI 100% CO', 7,utc_timestamp),

-- Malharia Tropical
('MTB45002317', '6/1 OE 100%CO', 8,utc_timestamp),
('MTB78005643', '30/1 PENT 100% ALG', 8,utc_timestamp),

-- CooperFibra Paraná
('CFP21008761', '14/1 OE 50% CO + 50% PES', 9,utc_timestamp),
('CFP33004520', '12/1 PENTEADO 100%ALG', 9,utc_timestamp),

-- Fios Catarinenses
('FCT71001194', '14/1 OE 100% CO', 10,utc_timestamp),
('FCT89004511', '2/40 SOFT PENT 100% CO', 10,utc_timestamp),

-- Textil Horizonte
('THO31004591', '12/1 100% CO', 11,utc_timestamp),
('THO56007841', '12/1 PENT 100%CO', 11,utc_timestamp),

-- Linhas Sul América
('LSA22008931', '14/1 CARD 100% CO', 12,utc_timestamp),
('LSA33002114', '16/1 100% CO', 12,utc_timestamp),

-- PoliFios Brasil
('PFB91007741', '20/1 100% CO', 13,utc_timestamp),
('PFB64003217', '24/1 100% CO', 13,utc_timestamp),

-- Tintas e Corantes Alfa
('TCA81004311', '24/1 PENT 100%CO', 14,utc_timestamp),
('TCA93006720', '14/1 CARD.COMP 100% CO', 14,utc_timestamp),

-- Tecidos Planalto
('TPL51008971', '36/1 PENT COMP 100% CO', 15,utc_timestamp),
('TPL72005411', '16/1 OE F 100% CO', 15,utc_timestamp),

-- Cooperativa Algodoeira Paulista
('CAP31004510', '6/1 OE 100% CO', 16,utc_timestamp),
('CAP42006780', '10/1 OE 100% CO', 16,utc_timestamp),

-- Fibras do Vale
('FDV86009214', '20/1 OE 100%CO', 17,utc_timestamp),
('FDV97001122', '10/2 OE R 100% CO', 17,utc_timestamp),

-- TexFibra Group
('TFG44007851', '20/2 OE 100%CO', 18,utc_timestamp),
('TFG55003291', '20/2 R 100%CO', 18,utc_timestamp),

-- Malhas Premium
('MPM31009832', '20/2 R 100% CO TRAMA', 19,utc_timestamp),
('MPM42001233', '24/2 R 100% CO', 19,utc_timestamp),

-- Fiação Imperial
('FIP91004573', '24/2 PENT.A.T.100%CO-TG', 20,utc_timestamp),
('FIP82006741', '24/2 PENT.A.T. 100%CO', 20,utc_timestamp),

-- Corantes Delta
('CDL33004591', '49,2 TEX PENT EGIPZTWIST', 21,utc_timestamp),
('CDL55009861', '13/1 PENTZTWIST SOLUCELL', 21,utc_timestamp),

-- Têxtil Santa Luzia
('TSL71001134', '13/1 PENT ZERO TWIST', 22,utc_timestamp),
('TSL88003245', '20/1 100% CO - TG', 22,utc_timestamp),

-- Fios & Tramas
('FET45009871', '12/1 100% CO - TG', 23,utc_timestamp),
('FET69007621', '12/1 PENT 100%CO-TG', 23,utc_timestamp),

-- Algodoeira do Cerrado
('ADC21005431', '14/1 CARD 100%CO TG', 24,utc_timestamp),
('ADC34007890', '20/2 R 100% CO - TG', 24,utc_timestamp),

-- FibraTech Solutions
('FTS92004512', '24/2 R 100% CO - TG', 25,utc_timestamp),
('FTS61003219', '6/1 OE 100%CO', 25,utc_timestamp),

-- Indústria de Fios Paulista
('IFP81004516', '2/34 MERC.GAZEADO 100%CO', 26,utc_timestamp),
('IFP93006781', '120/2 FIADO 100% PES', 26,utc_timestamp),

-- Trama Forte Ltda
('TFL52001139', '36/1 OE 100%CO', 27,utc_timestamp),
('TFL67003241', '14/1 OE 100%CO', 27,utc_timestamp),

-- ColorFibra
('CLF22009813', '40/1 PENTEADO 100%ALG', 28,utc_timestamp),
('CLF33004511', '20/1 OE 100%A T.MALHARIA', 28,utc_timestamp),

-- TexBrasil Insumos
('TBI91005432', '30/1 OE 100%CO', 29,utc_timestamp),
('TBI73006720', '16/1 CARD 52%CO + 48%PES', 29,utc_timestamp),

-- Fiação Modelo
('FMD12004581', '2/34 CARD.MERC. 100%CO', 30,utc_timestamp),
('FMD23006711', '16/1 OPEN END 100%ALG', 30,utc_timestamp);
