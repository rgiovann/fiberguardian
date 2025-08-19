set foreign_key_checks = 0;

delete from usuario;
delete from fornecedor;
delete from produto;
delete from nota_fiscal;
delete from nota_item;
delete from laboratorio;
delete from engenharia;
delete from pdf_nota_fiscal;

set foreign_key_checks = 1;

alter table usuario           auto_increment=1;
alter table fornecedor        auto_increment=1;
alter table produto           auto_increment=1;
alter table nota_fiscal       auto_increment=1;
alter table nota_item         auto_increment=1;
alter table laboratorio         auto_increment=1;
alter table engenharia        auto_increment=1;
alter table pdf_nota_fiscal   auto_increment=1;

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

INSERT INTO fornecedor (nome_fornecedor, cnpj, data_cadastro, telefone, email) VALUES
('CopperFibra', '12847224000112', utc_timestamp, '(11) 98765-4321', 'contato@copperfibra.com.br'),
('Cocari', '68216077000137', utc_timestamp, '(43) 99876-5432', 'vendas@cocari.com.br'),
('Fiação São Bento', '01883236000186', utc_timestamp, '(47) 99654-3210', 'comercial@fiaosaobento.com.br'),
('Tecelagem Aurora', '42245402000104', utc_timestamp, '(41) 99555-1122', 'contato@tecelagemaurora.com.br'),
('Tinturaria Brasil', '88102681000140', utc_timestamp, '(31) 99444-2233', 'atendimento@tinturariabrasil.com.br'),
('Fibras Unidas', '01008051000122', utc_timestamp, '(51) 99333-3344', 'faleconosco@fibrasunidas.com.br'),
('Algodoeira Nacional', '65151246000100', utc_timestamp, '(61) 99222-4455', 'sac@algodoeiranacional.com.br'),
('Malharia Tropical', '77823630000190', utc_timestamp, '(85) 99111-5566', 'vendas@malhariatropical.com.br'),
('CooperFibra Paraná', '77725062000195', utc_timestamp, '(44) 99988-6677', 'contato@cooperfibrapr.com.br'),
('Fios Catarinenses', '06605107000140', utc_timestamp, '(48) 98877-7788', 'comercial@fioscatarinenses.com.br'),
('Textil Horizonte', '61420443000126', utc_timestamp, '(34) 98777-8899', 'contato@textilhorizonte.com.br'),
('Linhas Sul América', '73274221000121', utc_timestamp, '(21) 98666-9900', 'atendimento@linhassulamerica.com.br'),
('PoliFios Brasil', '53708225000163', utc_timestamp, '(27) 98555-0011', 'comercial@polifiosbrasil.com.br'),
('Tintas e Corantes Alfa', '80338512000100', utc_timestamp, '(11) 98444-1122', 'sac@tintascorantesalfa.com.br'),
('Tecidos Planalto', '38046884000198', utc_timestamp, '(62) 98333-2233', 'contato@tecidosplanalto.com.br'),
('Cooperativa Algodoeira Paulista', '58108258000196', utc_timestamp, '(19) 98222-3344', 'cooperativa@algodoeirapaulista.com.br'),
('Fibras do Vale', '71302884000187', utc_timestamp, '(12) 98111-4455', 'comercial@fibrasdovale.com.br'),
('TexFibra Group', '52838565000146', utc_timestamp, '(13) 98000-5566', 'vendas@texfibragroup.com.br'),
('Malhas Premium', '27416160000172', utc_timestamp, '(14) 97999-6677', 'contato@malhaspremium.com.br'),
('Fiação Imperial', '75208141000101', utc_timestamp, '(15) 97888-7788', 'comercial@fiaacaoimperial.com.br'),
('Corantes Delta', '85234742000134', utc_timestamp, '(16) 97777-8899', 'vendas@corantesdelta.com.br'),
('Têxtil Santa Luzia', '25883184000106', utc_timestamp, '(17) 97666-9900', 'atendimento@textilsantaluzia.com.br'),
('Fios & Tramas', '13524713000104', utc_timestamp, '(18) 97555-0011', 'contato@fiosetramas.com.br'),
('Algodoeira do Cerrado', '52240742000198', utc_timestamp, '(33) 97444-1122', 'vendas@algodoeiradocerrado.com.br'),
('FibraTech Solutions', '58588272000134', utc_timestamp, '(35) 97333-2233', 'comercial@fibratechsolutions.com.br'),
('Indústria de Fios Paulista', '08710381000104', utc_timestamp, '(37) 97222-3344', 'atendimento@fiospaulista.com.br'),
('Trama Forte Ltda', '35005243000105', utc_timestamp, '(39) 97111-4455', 'contato@trmaforte.com.br'),
('ColorFibra', '01687245000100', utc_timestamp, '(11) 97000-5566', 'vendas@colorfibra.com.br'),
('TexBrasil Insumos', '68755877000126', utc_timestamp, '(21) 96999-6677', 'comercial@texbrasilinsumos.com.br'),
('Fiação Modelo', '60417787000113', utc_timestamp, '(41) 96888-7788', 'contato@fiaacaomodelo.com.br');


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
('FCT89004811', '2/40 SOFT PENT 100% CO', 9,utc_timestamp),

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
('FIP82006941', '13/1 PENTZTWIST SOLUCELL', 20,utc_timestamp),

-- Corantes Delta
('CDL33004591', '49,2 TEX PENT EGIPZTWIST', 21,utc_timestamp),
('CDL55009861', '13/1 PENTZTWIST SOLUCELL', 21,utc_timestamp),

-- Têxtil Santa Luzia
('TSL71001134', '13/1 PENT ZERO TWIST', 22,utc_timestamp),
('TSL88003245', '20/1 100% CO - TG', 22,utc_timestamp),

-- Fios & Tramas
('FET45009871', '12/1 100% CO - TG', 23,utc_timestamp),
('FET69007621', '12/1 PENT 100%CO-TG', 23,utc_timestamp),
('FET69007621', '20/1 100% CO - TG', 23,utc_timestamp),

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
('TFL67009241', '20/1 OE 100%A T.MALHARIA', 27,utc_timestamp),

-- ColorFibra
('CLF22009813', '40/1 PENTEADO 100%ALG', 28,utc_timestamp),
('CLF33004511', '20/1 OE 100%A T.MALHARIA', 28,utc_timestamp),

-- TexBrasil Insumos
('TBI91005432', '30/1 OE 100%CO', 29,utc_timestamp),
('TBI73006720', '16/1 CARD 52%CO + 48%PES', 29,utc_timestamp),

-- Fiação Modelo
('FMD12004581', '2/34 CARD.MERC. 100%CO', 30,utc_timestamp),
('FMD23006711', '16/1 OPEN END 100%ALG', 30,utc_timestamp);

-- Inserção de 10 notas fiscais e seus itens

-- Nota Fiscal 1
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('35764', 1, 52, 13750.00, '2024-11-20', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao,data_cadastro)
VALUES (1, 1, 3000.00, 100, 7500.00, 25.00, NULL,utc_timestamp),
       (1, 2, 2500.00, 80, 6250.00, 25.00, NULL,utc_timestamp);

-- Nota Fiscal 2
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('45934', 3, 48, 22800.00, '2024-10-20', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao,data_cadastro)
VALUES (2, 5, 6000.00, 200, 15000.00, 25.00, NULL,utc_timestamp),
       (2, 6, 3200.00, 100, 7800.00, 24.38, NULL,utc_timestamp);

-- Nota Fiscal 3
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('55231', 5, 37, 9100.00, '2024-09-22', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao,data_cadastro)
VALUES (3, 9, 300.00, 10, 9100.00, 30.33, NULL,utc_timestamp);

-- Nota Fiscal 4
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('65343', 7, 52, 25650.00, '2024-09-21', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao,data_cadastro)
VALUES (4, 13, 4500.00, 150, 11250.00, 25.00, NULL,utc_timestamp),
       (4, 14, 4500.00, 150, 14400.00, 32.00, NULL,utc_timestamp);

-- Nota Fiscal 5
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('75667', 9, 37, 46200.00, '2024-09-20', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao,data_cadastro)
VALUES (5, 17, 6000.00, 200, 18000.00, 30.00, NULL,utc_timestamp),
       (5, 18, 6000.00, 200, 21000.00, 35.00, NULL,utc_timestamp),
       (5, 19, 3000.00, 100, 7200.00, 24.00, NULL,utc_timestamp);

-- Nota Fiscal 6
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('85871', 11, 48, 15200.00, '2024-09-19', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao,data_cadastro)
VALUES (6, 21, 4000.00, 125, 10000.00, 25.00, NULL,utc_timestamp),
       (6, 22, 2000.00, 65, 5200.00, 26.00, NULL,utc_timestamp);

-- Nota Fiscal 7
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('95000', 13, 52, 8400.00, '2024-09-18', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao,data_cadastro)
VALUES (7, 25, 3000.00, 100, 8400.00, 28.00, NULL,utc_timestamp);

-- Nota Fiscal 8
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('105000', 15, 52, 31500.00, '2024-11-18', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao,data_cadastro)
VALUES (8, 29, 6000.00, 200, 18000.00, 30.00, NULL,utc_timestamp),
       (8, 30, 4500.00, 150, 13500.00, 30.00, NULL,utc_timestamp);

-- Nota Fiscal 9
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('115435', 17, 37, 39600.00, '2024-09-28', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao,data_cadastro)
VALUES (9, 33, 6000.00, 200, 18000.00, 30.00, NULL,utc_timestamp),
       (9, 34, 6000.00, 200, 21600.00, 36.00, NULL,utc_timestamp);

-- Nota Fiscal 10
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('125675', 19, 48, 31200.00, '2024-10-18', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao,data_cadastro)
VALUES (10, 37, 4500.00, 150, 13500.00, 30.00, NULL,utc_timestamp),
       (10, 38, 6000.00, 200, 17700.00, 29.50, NULL,utc_timestamp);

-- Nota Fiscal 11
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('135801', 2, 2, 36750.00, '2024-09-18', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (11, 3, 4500.00, 150, 13500.00, 30.00, NULL, utc_timestamp),
       (11, 4, 4200.00, 140, 23250.00, 27.50, NULL, utc_timestamp);

-- Nota Fiscal 12
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('145923', 4, 5, 28600.00, '2024-11-18', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (12, 7, 3000.00, 100, 9300.00, 31.00, NULL, utc_timestamp),
       (12, 8, 3800.00, 125, 12540.00, 33.00, NULL, utc_timestamp);

-- Nota Fiscal 13
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('156045', 6, 9, 19800.00, '2024-12-09', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (13, 11, 1800.00, 60, 5400.00, 30.00, NULL, utc_timestamp),
       (13, 12, 4800.00, 160, 14400.00, 30.00, NULL, utc_timestamp);

-- Nota Fiscal 14
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('166178', 8, 15, 24750.00, '2024-12-08', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (14, 15, 3500.00, 115, 10500.00, 30.00, NULL, utc_timestamp),
       (14, 16, 4200.00, 140, 14250.00, 33.93, NULL, utc_timestamp);

-- Nota Fiscal 15
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('176289', 10, 21, 17150.00, '2024-11-08', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (15, 20, 5500.00, 180, 17150.00, 31.18, NULL, utc_timestamp);

-- Nota Fiscal 16
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('186390', 12, 35, 32400.00, '2024-11-08', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (16, 23, 3600.00, 120, 10800.00, 30.00, NULL, utc_timestamp),
       (16, 24, 4200.00, 140, 11760.00, 28.00, NULL, utc_timestamp);

-- Nota Fiscal 17
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('196501', 14, 42, 24750.00, '2024-10-19', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (17, 27, 2700.00, 90, 8370.00, 31.00, NULL, utc_timestamp),
       (17, 28, 4800.00, 160, 16380.00, 34.13, NULL, utc_timestamp);

-- Nota Fiscal 18
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('206612', 16, 2, 33900.00, '2024-10-09', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (18, 31, 3900.00, 130, 11700.00, 30.00, NULL, utc_timestamp),
       (18, 32, 4500.00, 150, 13950.00, 31.00, NULL, utc_timestamp);

-- Nota Fiscal 19
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('216723', 18, 5, 38400.00, '2024-09-12', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (19, 35, 4800.00, 160, 14400.00, 30.00, NULL, utc_timestamp),
       (19, 36, 3600.00, 120, 12240.00, 34.00, NULL, utc_timestamp);

-- Nota Fiscal 20
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('226834', 20, 9, 51750.00, '2024-11-23', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (20, 39, 4200.00, 140, 12600.00, 30.00, NULL, utc_timestamp),
       (20, 40, 3900.00, 130, 12870.00, 33.00, NULL, utc_timestamp),
       (20, 41, 2700.00, 90, 8370.00, 31.00, NULL, utc_timestamp);

-- Nota Fiscal 21
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('236945', 22, 15, 29100.00, '2024-08-23', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (21, 43, 3300.00, 110, 9900.00, 30.00, NULL, utc_timestamp),
       (21, 44, 4500.00, 150, 13950.00, 31.00, NULL, utc_timestamp);

-- Nota Fiscal 22
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('247056', 24, 21, 38400.00, '2024-11-10', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (22, 47, 4500.00, 150, 13950.00, 31.00, NULL, utc_timestamp),
       (22, 48, 4200.00, 140, 12600.00, 30.00, NULL, utc_timestamp);

-- Nota Fiscal 23
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('257167', 26, 35, 22800.00, '2024-09-03', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (23, 51, 2400.00, 80, 7200.00, 30.00, NULL, utc_timestamp),
       (23, 52, 3900.00, 130, 12870.00, 33.00, NULL, utc_timestamp);

-- Nota Fiscal 24
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('267278', 28, 42, 32400.00, '2024-10-03', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (24, 55, 4500.00, 150, 13500.00, 30.00, NULL, utc_timestamp),
       (24, 56, 3600.00, 120, 12240.00, 34.00, NULL, utc_timestamp);

-- Nota Fiscal 25
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('277389', 30, 2, 19950.00, '2025-01-03', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (25, 59, 2700.00, 90, 8370.00, 31.00, NULL, utc_timestamp),
       (25, 60, 3900.00, 130, 11580.00, 29.69, NULL, utc_timestamp);

-- Nota Fiscal 26
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('287490', 1, 5, 33600.00, '2024-12-13', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (26, 1, 4800.00, 160, 14400.00, 30.00, NULL, utc_timestamp),
       (26, 2, 3900.00, 130, 12870.00, 33.00, NULL, utc_timestamp);

-- Nota Fiscal 27
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('297601', 3, 9, 35100.00, '2024-11-23', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (27, 5, 3600.00, 120, 10800.00, 30.00, NULL, utc_timestamp),
       (27, 6, 4200.00, 140, 13440.00, 32.00, NULL, utc_timestamp);

-- Nota Fiscal 28
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('307712', 5, 15, 26550.00, '2024-12-03', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (28, 9, 900.00, 30, 2790.00, 31.00, NULL, utc_timestamp),
       (28, 10, 4500.00, 150, 14400.00, 32.00, NULL, utc_timestamp);

-- Nota Fiscal 29
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('317823', 7, 21, 33750.00, '2024-11-03', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (29, 13, 3600.00, 120, 10800.00, 30.00, NULL, utc_timestamp),
       (29, 14, 4500.00, 150, 14850.00, 33.00, NULL, utc_timestamp);

-- Nota Fiscal 30
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('327934', 9, 35, 53400.00, '2024-08-03', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (30, 17, 4800.00, 160, 14400.00, 30.00, NULL, utc_timestamp),
       (30, 18, 3900.00, 130, 13650.00, 35.00, NULL, utc_timestamp),
       (30, 19, 3300.00, 110, 8580.00, 26.00, NULL, utc_timestamp);

-- Nota registros laboratorio por item de nota fiscal

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (2, '9TK', 12.50, 45, 30, 120, 5.75, 15.20, 8.50, 6.25, 450, 'APROVADO', 17, utc_timestamp(),'2025-03-24');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (3, '202100', 18.75, 60, 80, 250, 3.20, 12.90, 4.30, 15.80, 780, 'REPROVADO', 13, utc_timestamp(),'2025-07-24');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (5, '8', 10.25, 20, 15, 300, 7.80, 19.50, 12.10, 8.40, 120, 'APROVADO', 10, utc_timestamp(),'2025-08-03');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (7, '225', 15.90, 70, 90, 400, 2.15, 11.75, 3.90, 12.60, 950, 'REPROVADO', 31, utc_timestamp(),'2025-06-17');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (9, '10TK5', 13.40, 30, 40, 150, 6.50, 16.80, 9.20, 10.05, 300, 'APROVADO', 17, utc_timestamp(),'2025-07-30');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (12, '18J600', 17.10, 55, 60, 350, 4.90, 14.30, 6.70, 7.35, 600, 'REPROVADO', 13, utc_timestamp(),'2025-08-24');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (13, '5KL', 11.80, 25, 20, 200, 8.30, 18.60, 11.50, 14.70, 150, 'APROVADO', 10, utc_timestamp(),'2025-08-04');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (15, '300200', 19.60, 80, 110, 450, 1.95, 10.40, 2.80, 18.90, 850, 'REPROVADO', 31, utc_timestamp(),'2025-08-24');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (16, '12XY', 14.30, 40, 50, 180, 5.10, 13.70, 7.90, 9.15, 400, 'APROVADO', 17, utc_timestamp(),'2025-08-02');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (18, '7PZ', 16.90, 65, 70, 320, 3.70, 17.20, 10.30, 5.85, 720, 'REPROVADO', 13, utc_timestamp(),'2025-08-01');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (1, '15AB', 12.70, 15, 10, 100, 6.25, 15.50, 8.70, 11.20, 200, 'APROVADO', 10, utc_timestamp(),'2025-08-04');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (4, '400300', 18.20, 90, 120, 280, 2.80, 12.10, 4.50, 16.40, 900, 'REPROVADO', 31, utc_timestamp(),'2025-07-24');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (5, '20CD', 10.90, 35, 45, 130, 7.60, 19.80, 12.90, 7.80, 350, 'APROVADO', 17, utc_timestamp(),'2025-07-14');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (6, '50EF', 15.50, 75, 85, 370, 4.30, 11.30, 3.10, 13.70, 650, 'REPROVADO', 13, utc_timestamp(),'2025-08-01');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (8, '8GH', 13.10, 10, 25, 160, 6.90, 16.40, 9.80, 17.10, 100, 'APROVADO', 10, utc_timestamp(),'2025-08-03');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (11, '25IJ', 17.80, 85, 95, 410, 3.50, 14.90, 6.20, 6.50, 800, 'REPROVADO', 31, utc_timestamp(),'2025-07-26');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (13, '30KL', 11.30, 50, 60, 220, 8.10, 18.10, 11.30, 19.20, 250, 'REPROVADO', 17, utc_timestamp(),'2025-07-28');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (14, '40MN', 19.40, 30, 40, 190, 5.70, 10.80, 2.90, 9.90, 500, 'APROVADO', 13, utc_timestamp(),'2025-07-25');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (17, '60OP', 14.70, 45, 55, 340, 4.20, 13.20, 7.50, 12.30, 750, 'REPROVADO', 10, utc_timestamp(),'2025-07-28');

INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao)
VALUES (19, '70PQ', 16.20, 20, 15, 270, 6.80, 17.70, 10.70, 5.20, 300, 'APROVADO', 31, utc_timestamp(),'2025-07-21');
