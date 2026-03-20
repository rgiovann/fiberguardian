set foreign_key_checks = 0;

delete from usuario;
delete from fornecedor;
delete from produto;
delete from nota_fiscal;
delete from nota_item;
delete from laboratorio;
delete from engenharia;
delete from pdf_nota_fiscal;
delete from fio_tecnico;

set foreign_key_checks = 1;

alter table usuario           auto_increment=1;
alter table fornecedor        auto_increment=1;
alter table produto           auto_increment=1;
alter table nota_fiscal       auto_increment=1;
alter table nota_item         auto_increment=1;
alter table laboratorio       auto_increment=1;
alter table engenharia        auto_increment=1;
alter table pdf_nota_fiscal   auto_increment=1;
alter table fio_tecnico       auto_increment=1;

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

INSERT INTO fio_tecnico (
    fibra_1, percentual_1,
    fibra_2, percentual_2,
    fibra_3, percentual_3,
    fibra_4, percentual_4,
    sistema_titulo, titulo_valor,
    numero_cabos,
    preparacao, sistema_fiacao,
    torcao_direcao, torcao_nominal_tpm,
    observacao_tecnica,
    ativo,
    data_cadastro,
    criado_por
) VALUES
-- 1: Algodão puro penteado ring Ne 30/1 — o fio mais comum em malharia
('CO', 100.00, NULL, NULL, NULL, NULL, NULL, NULL, 'NE', 30.00, 1, 'COMBED', 'RING',    'Z', 820.00, NULL,                                   TRUE, NOW(), 1),
-- 2: Algodão puro cardado OE Ne 20/1 — jeans e tecidos grossos
('CO', 100.00, NULL, NULL, NULL, NULL, NULL, NULL, 'NE', 20.00, 1, 'CARDED', 'OE',      'Z', 480.00, NULL,                                   TRUE, NOW(), 1),
-- 3: Algodão puro penteado compact Ne 40/1 — camisaria fina
('CO', 100.00, NULL, NULL, NULL, NULL, NULL, NULL, 'NE', 40.00, 1, 'COMBED', 'COMPACT', 'Z', 950.00, NULL,                                   TRUE, NOW(), 1),
-- 4: Algodão puro penteado compact Ne 60/1 — artigos de altíssima qualidade
('CO', 100.00, NULL, NULL, NULL, NULL, NULL, NULL, 'NE', 60.00, 1, 'COMBED', 'COMPACT', 'Z', 1100.00, NULL,                                  TRUE, NOW(), 1),
-- 5: Algodão puro cardado OE Ne 16/1 — malha pesada
('CO', 100.00, NULL, NULL, NULL, NULL, NULL, NULL, 'NE', 16.00, 1, 'CARDED', 'OE',      'Z', 380.00, NULL,                                   TRUE, NOW(), 1),
-- 6: Poliéster puro penteado compact Ne 40/1
('PES', 100.00, NULL, NULL, NULL, NULL, NULL, NULL, 'NE', 40.00, 1, 'COMBED', 'COMPACT','Z', 900.00, NULL,                                   TRUE, NOW(), 1),
-- 7: Poliéster puro Ne 30/1 airjet
('PES', 100.00, NULL, NULL, NULL, NULL, NULL, NULL, 'NE', 30.00, 1, 'COMBED', 'AIRJET', 'Z', NULL,   'Fio airjet para tecido plano',         TRUE, NOW(), 1),
-- 8: Poliamida texturizada dtex 78/1
('PA',  100.00, NULL, NULL, NULL, NULL, NULL, NULL, 'DTEX', 78.00, 1, 'N/A',  'OUTRO',  'Z', NULL,   'Fio texturizado falso torção',         TRUE, NOW(), 1),
-- 9: Linho puro Nm 20/1 ring
('LI',  100.00, NULL, NULL, NULL, NULL, NULL, NULL, 'NM', 20.00, 1, 'N/A',   'RING',    'Z', 340.00, NULL,                                   TRUE, NOW(), 1),
-- 10: Viscose pura Nm 40/1 ring
('CV',  100.00, NULL, NULL, NULL, NULL, NULL, NULL, 'NM', 40.00, 1, 'N/A',   'RING',    'S', 720.00, NULL,                                   TRUE, NOW(), 1),
-- 11: Lã pura Nm 28/2 ring — tricô
('WO',  100.00, NULL, NULL, NULL, NULL, NULL, NULL, 'NM', 28.00, 2, 'COMBED', 'RING',   'S', 280.00, 'Lã penteada para tricô',               TRUE, NOW(), 1),
-- 12: PES/CO 65/35 Ne 30/1 ring penteado — blend clássico camisaria
('PES', 65.00, 'CO', 35.00, NULL, NULL, NULL, NULL, 'NE', 30.00, 1, 'COMBED', 'RING',   'Z', 810.00, NULL,                                   TRUE, NOW(), 1),
-- 13: PES/CO 67/33 Ne 20/1 ring cardado — blend popular malha
('PES', 67.00, 'CO', 33.00, NULL, NULL, NULL, NULL, 'NE', 20.00, 1, 'CARDED', 'RING',   'Z', 500.00, NULL,                                   TRUE, NOW(), 1),
-- 14: CO/PES 50/50 Ne 20/1 ring cardado
('CO',  50.00, 'PES', 50.00, NULL, NULL, NULL, NULL, 'NE', 20.00, 1, 'CARDED', 'RING',  'Z', 490.00, NULL,                                   TRUE, NOW(), 1),
-- 15: CO/EL 95/5 Ne 30/1 ring penteado — malha com elastano
('CO',  95.00, 'EL',  5.00, NULL, NULL, NULL, NULL, 'NE', 30.00, 1, 'COMBED', 'RING',   'Z', 780.00, 'Core-spun com elastano',               TRUE, NOW(), 1),
-- 16: CO/PES 85/15 Ne 20/1 ring cardado
('CO',  85.00, 'PES', 15.00, NULL, NULL, NULL, NULL, 'NE', 20.00, 1, 'CARDED', 'RING',  'Z', 470.00, NULL,                                   TRUE, NOW(), 1),
-- 17: WO/PA 80/20 Nm 30/1 ring — lã reforçada com poliamida
('WO',  80.00, 'PA', 20.00, NULL, NULL, NULL, NULL, 'NM', 30.00, 1, 'COMBED', 'RING',   'S', 310.00, 'Reforço PA para meias e tecidos técnicos', TRUE, NOW(), 1),
-- 18: LI/CO 55/45 Nm 28/1 ring — blend linho algodão
('LI',  55.00, 'CO', 45.00, NULL, NULL, NULL, NULL, 'NM', 28.00, 1, 'N/A',   'RING',    'Z', 350.00, NULL,                                   TRUE, NOW(), 1),
-- 19: CV/PES 50/50 Ne 30/1 ring — blend viscose poliéster
('CV',  50.00, 'PES', 50.00, NULL, NULL, NULL, NULL, 'NE', 30.00, 1, 'N/A',  'RING',    'S', 750.00, NULL,                                   TRUE, NOW(), 1),
-- 20: CO/PES/CV 45/35/20 Ne 20/1 ring cardado — blend triplo
('CO',  45.00, 'PES', 35.00, 'CV', 20.00, NULL, NULL, 'NE', 20.00, 1, 'CARDED', 'RING', 'Z', 460.00, 'Blend triplo para malha popular',     TRUE, NOW(), 1);

INSERT INTO fio_tecnico (
    fibra_1, percentual_1,
    fibra_2, percentual_2,
    sistema_titulo, titulo_valor,
    numero_cabos,
    preparacao, sistema_fiacao,
    torcao_direcao, torcao_nominal_tpm,
    observacao_tecnica,
    ativo, data_cadastro, criado_por
) VALUES
-- id=21: Ne 36/1 CO OE
('CO',100.00,NULL,NULL,'NE',36.00,1,'CARDED','OE','Z',NULL,NULL,TRUE,NOW(),1),

-- id=22: Ne 14/1 CO OE
('CO',100.00,NULL,NULL,'NE',14.00,1,'CARDED','OE','Z',NULL,NULL,TRUE,NOW(),1),

-- id=23: Ne 30/1 CO OE — difere do id=1 que é COMBED RING
('CO',100.00,NULL,NULL,'NE',30.00,1,'CARDED','OE','Z',NULL,NULL,TRUE,NOW(),1),

-- id=24: Ne 16/1 CO 52% PES 48% CARDED RING
('CO',52.00,'PES',48.00,'NE',16.00,1,'CARDED','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=25: Ne 34/2 CO CARDED RING
('CO',100.00,NULL,NULL,'NE',34.00,2,'CARDED','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=26: Ne 12/1 CO COMBED RING
('CO',100.00,NULL,NULL,'NE',12.00,1,'COMBED','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=27: Ne 12/1 CO CARDED RING
('CO',100.00,NULL,NULL,'NE',12.00,1,'CARDED','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=28: PES DEN 150 filamento contínuo, 1 cabo
('PES',100.00,NULL,NULL,'DEN',150.00,1,'N/A','AIRJET','Z',NULL,
    '150 DEN / 48 filamentos',TRUE,NOW(),1),

-- id=29: Ne 10/1 CO CARDED OE
('CO',100.00,NULL,NULL,'NE',10.00,1,'CARDED','OE','Z',NULL,NULL,TRUE,NOW(),1),

-- id=30: Ne 6/1 CO CARDED OE
('CO',100.00,NULL,NULL,'NE',6.00,1,'CARDED','OE','Z',NULL,NULL,TRUE,NOW(),1),

-- id=31: Ne 14/1 CO 50% PES 50% CARDED OE
-- Percentuais iguais: CO listado primeiro por convenção
('CO',50.00,'PES',50.00,'NE',14.00,1,'CARDED','OE','Z',NULL,NULL,TRUE,NOW(),1),

-- id=32: Ne 40/2 CO COMBED RING — fio 2/40
('CO',100.00,NULL,NULL,'NE',40.00,2,'COMBED','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=33: Ne 12/1 CO N/A RING — processo não especificado
('CO',100.00,NULL,NULL,'NE',12.00,1,'N/A','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=34: Ne 20/1 CO N/A RING — processo não especificado
('CO',100.00,NULL,NULL,'NE',20.00,1,'N/A','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=35: Ne 16/1 CO N/A RING — processo não especificado
('CO',100.00,NULL,NULL,'NE',16.00,1,'N/A','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=36: Ne 20/2 CO N/A RING
('CO',100.00,NULL,NULL,'NE',20.00,2,'N/A','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=37: Ne 24/2 CO N/A RING
('CO',100.00,NULL,NULL,'NE',24.00,2,'N/A','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=38: Ne 20/2 CO CARDED OE — fio OE retorcido
('CO',100.00,NULL,NULL,'NE',20.00,2,'CARDED','OE','Z',NULL,NULL,TRUE,NOW(),1),

-- id=39: Ne 10/2 CO CARDED OE
('CO',100.00,NULL,NULL,'NE',10.00,2,'CARDED','OE','Z',NULL,NULL,TRUE,NOW(),1),

-- id=40: Ne 14/1 CO CARDED RING
('CO',100.00,NULL,NULL,'NE',14.00,1,'CARDED','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=41: Ne 24/1 CO N/A RING — processo não especificado
('CO',100.00,NULL,NULL,'NE',24.00,1,'N/A','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=42: Ne 24/1 CO COMBED RING
('CO',100.00,NULL,NULL,'NE',24.00,1,'COMBED','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=43: Ne 36/1 CO COMBED RING — difere do id=21 que é CARDED OE
('CO',100.00,NULL,NULL,'NE',36.00,1,'COMBED','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=44: Ne 24/2 CO COMBED RING
('CO',100.00,NULL,NULL,'NE',24.00,2,'COMBED','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=45: Ne 20/2 CO COMBED RING
('CO',100.00,NULL,NULL,'NE',20.00,2,'COMBED','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=46: Ne 13/1 CO COMBED RING Z — ZTwist convencional
('CO',100.00,NULL,NULL,'NE',13.00,1,'COMBED','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=47: Ne 13/1 CO COMBED RING S — Zero Twist
-- Direção S aplicada por convenção para fios zero twist
('CO',100.00,NULL,NULL,'NE',13.00,1,'COMBED','RING','S',NULL,
    'Zero Twist: direção S aplicada por convenção',TRUE,NOW(),1),

-- id=48: DTEX 49.2/1 CO COMBED RING Z — algodão egípcio título em dtex
('CO',100.00,NULL,NULL,'DTEX',49.20,1,'COMBED','RING','Z',NULL,
    'Algodão egípcio penteado ZTwist título em dtex',TRUE,NOW(),1),

-- id=49: Ne 34/2 CO COMBED RING — difere do id=25 que é CARDED
('CO',100.00,NULL,NULL,'NE',34.00,2,'COMBED','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=50: PES NE 120/2 N/A RING
('PES',100.00,NULL,NULL,'NE',120.00,2,'N/A','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=51: Ne 30/2 CO COMBED RING — produto descontinuado CCB9900001
('CO',100.00,NULL,NULL,'NE',30.00,2,'COMBED','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=52: Ne 20/1 CO CARDED RING — difere do id=2 (CARDED OE) e id=34 (N/A RING)
('CO',100.00,NULL,NULL,'NE',20.00,1,'CARDED','RING','Z',NULL,NULL,TRUE,NOW(),1),

-- id=53: PES DEN 75 filamento contínuo, 1 cabo
('PES',100.00,NULL,NULL,'DEN',75.00,1,'N/A','AIRJET','Z',NULL,
    '75 DEN / 36 filamentos',TRUE,NOW(),1),

-- id=54: Ne 8/1 CO CARDED OE — produto descontinuado ALN9900001
('CO',100.00,NULL,NULL,'NE',8.00,1,'CARDED','OE','Z',NULL,NULL,TRUE,NOW(),1);

INSERT INTO produto (
    codigo_produto, descricao_produto,
    fornecedor_id, fio_tecnico_id,
    ativo, data_cadastro, criado_por
) VALUES

-- CopperFibra (fornecedor_id=1)
('CF110018411','36/1 OE 100%CO',              1, 21, TRUE, NOW(), 1),
('CF220045210','14/1 OE 100%CO',              1, 22, TRUE, NOW(), 1),

-- Cocari (fornecedor_id=2)
('CCB1800411', '40/1 PENTEADO 100%ALG',       2,  3, TRUE, NOW(), 1),
('CCB13270067','20/1 OE 100%A T.MALHARIA',    2,  2, TRUE, NOW(), 1),

-- Fiação São Bento (fornecedor_id=3)
('FSB520018562','30/1 OE 100%CO',             3, 23, TRUE, NOW(), 1),
('FSB32008765', '16/1 CARD 52%CO + 48%PES',  3, 24, TRUE, NOW(), 1),

-- Tecelagem Aurora (fornecedor_id=4)
('TAU41009873','2/34 CARD.MERC. 100%CO',      4, 25, TRUE, NOW(), 1),
('TAU65005421','16/1 OPEN END 100%ALG',        4,  5, TRUE, NOW(), 1),

-- Tinturaria Brasil (fornecedor_id=5)
('TBR31002751','12/1 PENT.EGIPCIO 100%A',     5, 26, TRUE, NOW(), 1),
('TBR92006732','12/1 CARD 100%ALG',            5, 27, TRUE, NOW(), 1),

-- Fibras Unidas (fornecedor_id=6)
('FBU54009210','150/48 HIM 100%PES',           6, 28, TRUE, NOW(), 1),
('FBU72001156','150D048FX2 TEXT AE 100%',      6, 28, TRUE, NOW(), 1),

-- Algodoeira Nacional (fornecedor_id=7)
('ALN87009843','10/1 OE 100% ALG',             7, 29, TRUE, NOW(), 1),
('ALN66005412','6/1 OE COCARI 100% CO',        7, 30, TRUE, NOW(), 1),

-- Malharia Tropical (fornecedor_id=8)
('MTB45002317','6/1 OE 100%CO',                8, 30, TRUE, NOW(), 1),
('MTB78005643','30/1 PENT 100% ALG',           8,  1, TRUE, NOW(), 1),

-- CooperFibra Paraná (fornecedor_id=9)
('CFP21008761','14/1 OE 50% CO + 50% PES',    9, 31, TRUE, NOW(), 1),
('CFP33004520','12/1 PENTEADO 100%ALG',        9, 26, TRUE, NOW(), 1),
('FCT89004811','2/40 SOFT PENT 100% CO',       9, 32, TRUE, NOW(), 1),

-- Fios Catarinenses (fornecedor_id=10)
('FCT71001194','14/1 OE 100% CO',             10, 22, TRUE, NOW(), 1),
('FCT89004511','2/40 SOFT PENT 100% CO',      10, 32, TRUE, NOW(), 1),

-- Textil Horizonte (fornecedor_id=11)
('THO31004591','12/1 100% CO',                11, 33, TRUE, NOW(), 1),
('THO56007841','12/1 PENT 100%CO',            11, 26, TRUE, NOW(), 1),

-- Linhas Sul América (fornecedor_id=12)
('LSA22008931','14/1 CARD 100% CO',           12, 40, TRUE, NOW(), 1),
('LSA33002114','16/1 100% CO',                12, 35, TRUE, NOW(), 1),

-- PoliFios Brasil (fornecedor_id=13)
('PFB91007741','20/1 100% CO',                13, 34, TRUE, NOW(), 1),
('PFB64003217','24/1 100% CO',                13, 41, TRUE, NOW(), 1),

-- Tintas e Corantes Alfa (fornecedor_id=14)
('TCA81004311','24/1 PENT 100%CO',            14, 42, TRUE, NOW(), 1),
('TCA93006720','14/1 CARD.COMP 100% CO',      14, 40, TRUE, NOW(), 1),

-- Tecidos Planalto (fornecedor_id=15)
('TPL51008971','36/1 PENT COMP 100% CO',      15, 43, TRUE, NOW(), 1),
('TPL72005411','16/1 OE F 100% CO',           15,  5, TRUE, NOW(), 1),

-- Cooperativa Algodoeira Paulista (fornecedor_id=16)
('CAP31004510','6/1 OE 100% CO',              16, 30, TRUE, NOW(), 1),
('CAP42006780','10/1 OE 100% CO',             16, 29, TRUE, NOW(), 1),

-- Fibras do Vale (fornecedor_id=17)
('FDV86009214','20/1 OE 100%CO',              17,  2, TRUE, NOW(), 1),
('FDV97001122','10/2 OE R 100% CO',           17, 39, TRUE, NOW(), 1),

-- TexFibra Group (fornecedor_id=18)
('TFG44007851','20/2 OE 100%CO',              18, 38, TRUE, NOW(), 1),
('TFG55003291','20/2 R 100%CO',               18, 36, TRUE, NOW(), 1),

-- Malhas Premium (fornecedor_id=19)
('MPM31009832','20/2 R 100% CO TRAMA',        19, 36, TRUE, NOW(), 1),
('MPM42001233','24/2 R 100% CO',              19, 37, TRUE, NOW(), 1),

-- Fiação Imperial (fornecedor_id=20)
('FIP91004573','24/2 PENT.A.T.100%CO-TG',    20, 44, TRUE, NOW(), 1),
('FIP82006741','24/2 PENT.A.T. 100%CO',       20, 44, TRUE, NOW(), 1),
('FIP82006941','13/1 PENTZTWIST SOLUCELL',    20, 46, TRUE, NOW(), 1),
('FIP82006771','20/2 PENT.A.T. 100%CO',       20, 45, TRUE, NOW(), 1),

-- Corantes Delta (fornecedor_id=21)
('CDL33004591','49,2 TEX PENT EGIPZTWIST',    21, 48, TRUE, NOW(), 1),
('CDL55009861','13/1 PENTZTWIST SOLUCELL',    21, 46, TRUE, NOW(), 1),

-- Têxtil Santa Luzia (fornecedor_id=22)
('TSL71001134','13/1 PENT ZERO TWIST',         22, 47, TRUE, NOW(), 1),
('TSL88003245','20/1 100% CO - TG',            22, 34, TRUE, NOW(), 1),

-- Fios & Tramas (fornecedor_id=23)
('FET45009871','12/1 100% CO - TG',            23, 33, TRUE, NOW(), 1),
('FET69007621','12/1 PENT 100%CO-TG',          23, 26, TRUE, NOW(), 1),
('FET69007622','20/1 100% CO - TG',            23, 34, TRUE, NOW(), 1),

-- Algodoeira do Cerrado (fornecedor_id=24)
('ADC21005431','14/1 CARD 100%CO TG',          24, 40, TRUE, NOW(), 1),
('ADC34007890','20/2 R 100% CO - TG',          24, 36, TRUE, NOW(), 1),

-- FibraTech Solutions (fornecedor_id=25)
('FTS92004512','24/2 R 100% CO - TG',          25, 37, TRUE, NOW(), 1),
('FTS61003219','6/1 OE 100%CO',                25, 30, TRUE, NOW(), 1),

-- Indústria de Fios Paulista (fornecedor_id=26)
('IFP81004516','2/34 MERC.GAZEADO 100%CO',    26, 49, TRUE, NOW(), 1),
('IFP93006781','120/2 FIADO 100% PES',         26, 50, TRUE, NOW(), 1),

-- Trama Forte (fornecedor_id=27)
('TFL52001139','36/1 OE 100%CO',               27, 21, TRUE, NOW(), 1),
('TFL67003241','14/1 OE 100%CO',               27, 22, TRUE, NOW(), 1),
('TFL67009241','20/1 OE 100%A T.MALHARIA',    27,  2, TRUE, NOW(), 1),

-- ColorFibra (fornecedor_id=28)
('CLF22009813','40/1 PENTEADO 100%ALG',        28,  3, TRUE, NOW(), 1),
('CLF33004511','20/1 OE 100%A T.MALHARIA',    28,  2, TRUE, NOW(), 1),

-- TexBrasil (fornecedor_id=29)
('TBI91005432','30/1 OE 100%CO',               29, 23, TRUE, NOW(), 1),
('TBI73006720','16/1 CARD 52%CO + 48%PES',    29, 24, TRUE, NOW(), 1),

-- Fiação Modelo (fornecedor_id=30)
('FMD12004581','2/34 CARD.MERC. 100%CO',       30, 25, TRUE, NOW(), 1),
('FMD23006711','16/1 OPEN END 100%ALG',         30,  5, TRUE, NOW(), 1),

-- -------------------------------------------------------
-- 5 registros INATIVOS — produtos descontinuados
-- -------------------------------------------------------
('CCB9900001', '30/2 PENT 100%CO DESCONT',     2, 51, FALSE, NOW(), 1),
('FSB9900001', '24/1 PENT 100%CO DESCONT',     3, 42, FALSE, NOW(), 1),
('TAU9900001', '20/1 CARD 100%CO DESCONT',     4, 52, FALSE, NOW(), 1),
('FBU9900001', '75D036 TEXT 100%PES DESCONT',  6, 53, FALSE, NOW(), 1),
('ALN9900001', '8/1 OE 100%CO DESCONT',        7, 54, FALSE, NOW(), 1);


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
VALUES (6, 22, 4000.00, 125, 10000.00, 25.00, NULL,utc_timestamp),
       (6, 23, 2000.00, 65, 5200.00, 26.00, NULL,utc_timestamp);

-- Nota Fiscal 7
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('95000', 13, 52, 8400.00, '2024-09-18', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao,data_cadastro)
VALUES (7, 26, 3000.00, 100, 8400.00, 28.00, NULL,utc_timestamp);

-- Nota Fiscal 8
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('105000', 15, 52, 31500.00, '2024-11-18', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao,data_cadastro)
VALUES (8, 30, 6000.00, 200, 18000.00, 30.00, NULL,utc_timestamp),
       (8, 31, 4500.00, 150, 13500.00, 30.00, NULL,utc_timestamp);

-- Nota Fiscal 9
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('115435', 17, 37, 39600.00, '2024-09-28', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao,data_cadastro)
VALUES (9, 34, 6000.00, 200, 18000.00, 30.00, NULL,utc_timestamp),
       (9, 35, 6000.00, 200, 21600.00, 36.00, NULL,utc_timestamp);

-- Nota Fiscal 10
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('125675', 19, 48, 31200.00, '2024-10-18', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao,data_cadastro)
VALUES (10, 38, 4500.00, 150, 13500.00, 30.00, NULL,utc_timestamp),
       (10, 39, 6000.00, 200, 17700.00, 29.50, NULL,utc_timestamp);

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
VALUES (16, 24, 3600.00, 120, 10800.00, 30.00, NULL, utc_timestamp),
       (16, 25, 4200.00, 140, 11760.00, 28.00, NULL, utc_timestamp);

-- Nota Fiscal 17
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('196501', 14, 42, 24750.00, '2024-10-19', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (17, 28, 2700.00, 90, 8370.00, 31.00, NULL, utc_timestamp),
       (17, 29, 4800.00, 160, 16380.00, 34.13, NULL, utc_timestamp);

-- Nota Fiscal 18
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('206612', 16, 2, 33900.00, '2024-10-09', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (18, 32, 3900.00, 130, 11700.00, 30.00, NULL, utc_timestamp),
       (18, 33, 4500.00, 150, 13950.00, 31.00, NULL, utc_timestamp);

-- Nota Fiscal 19
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('216723', 18, 5, 38400.00, '2024-09-12', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (19, 36, 4800.00, 160, 14400.00, 30.00, NULL, utc_timestamp),
       (19, 37, 3600.00, 120, 12240.00, 34.00, NULL, utc_timestamp);

-- Nota Fiscal 20
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('226834', 20, 9, 51750.00, '2024-11-23', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (20, 40, 4200.00, 140, 12600.00, 30.00, NULL, utc_timestamp),
       (20, 41, 3900.00, 130, 12870.00, 33.00, NULL, utc_timestamp),
       (20, 42, 2700.00, 90, 8370.00, 31.00, NULL, utc_timestamp);

-- Nota Fiscal 21
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('236945', 22, 15, 29100.00, '2024-08-23', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (21, 46, 3300.00, 110, 9900.00, 30.00, NULL, utc_timestamp),
       (21, 47, 4500.00, 150, 13950.00, 31.00, NULL, utc_timestamp);

-- Nota Fiscal 22
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('247056', 24, 21, 38400.00, '2024-11-10', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (22, 51, 4500.00, 150, 13950.00, 31.00, NULL, utc_timestamp),
       (22, 52, 4200.00, 140, 12600.00, 30.00, NULL, utc_timestamp);

-- Nota Fiscal 23
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('257167', 26, 35, 22800.00, '2024-09-03', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (23, 55, 2400.00, 80, 7200.00, 30.00, NULL, utc_timestamp),
       (23, 56, 3900.00, 130, 12870.00, 33.00, NULL, utc_timestamp);

-- Nota Fiscal 24
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('267278', 28, 42, 32400.00, '2024-10-03', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (24, 60, 4500.00, 150, 13500.00, 30.00, NULL, utc_timestamp),
       (24, 61, 3600.00, 120, 12240.00, 34.00, NULL, utc_timestamp);

-- Nota Fiscal 25
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('277389', 30, 2, 19950.00, '2025-01-03', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (25, 64, 2700.00, 90, 8370.00, 31.00, NULL, utc_timestamp),
       (25, 65, 3900.00, 130, 11580.00, 29.69, NULL, utc_timestamp);

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

-- essas NFs não tem laudos
-- Nota Fiscal 31 (Fornecedor 5 - Tinturaria Brasil, produtos 9 e 10)
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('338045', 5, 17, 23500.00, '2025-01-15', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (31, 9, 2500.00, 100, 7750.00, 3.10, NULL, utc_timestamp),
       (31, 10, 5000.00, 200, 15750.00, 3.15, NULL, utc_timestamp);

-- Nota Fiscal 32 (Fornecedor 9 - CooperFibra Paraná, produtos 17 e 18)
INSERT INTO nota_fiscal (codigo_nf, fornecedor_id, recebido_por, valor_total, data_recebimento, criado_por, data_cadastro, data_alteracao, alterado_por)
VALUES ('348156', 9, 21, 42200.00, '2025-01-20', NULL, utc_timestamp(), NULL, NULL);

INSERT INTO nota_item (nota_fiscal_id, produto_id, quantidade_recebida, numero_caixas, valor_total_item, preco_unitario, observacao, data_cadastro)
VALUES (32, 17, 4000.00, 150, 16800.00, 4.20, NULL, utc_timestamp),
       (32, 18, 5000.00, 180, 25400.00, 5.08, NULL, utc_timestamp);

-- ============================================================
-- Populacao tabela laboratorio
-- ============================================================
INSERT INTO laboratorio (
    item_nota_fiscal_id, numero_lote,
    cvm, pontos_finos, pontos_grossos, neps,
    h_pilosidade, resistencia, alongamento,
    titulo_ne, torcao_t_m,
    decisao, liberado_por,
    data_cadastro, data_realizacao,
    observacao_laudo,
    situacao, substituido_por, substitui, motivo_substituicao
) VALUES
(2,  '9TK',    12.50, 45,  30,  120, 5.75, 15.20, 8.50,  6.25,  450, 'APROVADO',  17, utc_timestamp(), '2025-03-24', 'Texto observacao relativo ao lote 9TK',    'ATIVO', NULL, NULL, NULL),
(3,  '202100', 18.75, 60,  80,  250, 3.20, 12.90, 4.30,  15.80, 780, 'REPROVADO', 13, utc_timestamp(), '2025-07-24', 'Texto observacao relativo ao lote 202100', 'ATIVO', NULL, NULL, NULL),
(5,  '8',      10.25, 20,  15,  300, 7.80, 19.50, 12.10, 8.40,  120, 'APROVADO',  10, utc_timestamp(), '2025-08-03', 'Texto observacao relativo ao lote 8',      'ATIVO', NULL, NULL, NULL),
(7,  '225',    15.90, 70,  90,  400, 2.15, 11.75, 3.90,  12.60, 950, 'REPROVADO', 31, utc_timestamp(), '2025-06-17', 'Texto observacao relativo ao lote 225',    'ATIVO', NULL, NULL, NULL),
(9,  '10TK5',  13.40, 30,  40,  150, 6.50, 16.80, 9.20,  10.05, 300, 'APROVADO',  17, utc_timestamp(), '2025-07-30', 'Texto observacao relativo ao lote 10TK5',  'ATIVO', NULL, NULL, NULL),
(12, '18J600', 17.10, 55,  60,  350, 4.90, 14.30, 6.70,  7.35,  600, 'REPROVADO', 13, utc_timestamp(), '2025-08-24', 'Texto observacao relativo ao lote 18J600', 'ATIVO', NULL, NULL, NULL),
(13, '5KL',    11.80, 25,  20,  200, 8.30, 18.60, 11.50, 14.70, 150, 'APROVADO',  10, utc_timestamp(), '2025-08-04', 'Texto observacao relativo ao lote 5KL',    'ATIVO', NULL, NULL, NULL),
(15, '300200', 19.60, 80,  110, 450, 1.95, 10.40, 2.80,  18.90, 850, 'REPROVADO', 31, utc_timestamp(), '2025-08-24', 'Texto observacao relativo ao lote 300200', 'ATIVO', NULL, NULL, NULL),
(16, '12XY',   14.30, 40,  50,  180, 5.10, 13.70, 7.90,  9.15,  400, 'APROVADO',  17, utc_timestamp(), '2025-08-02', 'Texto observacao relativo ao lote 12XY',   'ATIVO', NULL, NULL, NULL),
(18, '7PZ',    16.90, 65,  70,  320, 3.70, 17.20, 10.30, 5.85,  720, 'REPROVADO', 13, utc_timestamp(), '2025-08-01', 'Texto observacao relativo ao lote 7PZ',    'ATIVO', NULL, NULL, NULL),
(1,  '15AB',   12.70, 15,  10,  100, 6.25, 15.50, 8.70,  11.20, 200, 'APROVADO',  10, utc_timestamp(), '2025-08-04', 'Texto observacao relativo ao lote 15AB',   'ATIVO', NULL, NULL, NULL),
(4,  '400300', 18.20, 90,  120, 280, 2.80, 12.10, 4.50,  16.40, 900, 'REPROVADO', 31, utc_timestamp(), '2025-07-24', 'Texto observacao relativo ao lote 400300', 'ATIVO', NULL, NULL, NULL),
(5,  '20CD',   10.90, 35,  45,  130, 7.60, 19.80, 12.90, 7.80,  350, 'APROVADO',  17, utc_timestamp(), '2025-07-14', 'Texto observacao relativo ao lote 20CD',   'ATIVO', NULL, NULL, NULL),
(6,  '50EF',   15.50, 75,  85,  370, 4.30, 11.30, 3.10,  13.70, 650, 'REPROVADO', 13, utc_timestamp(), '2025-08-01', 'Texto observacao relativo ao lote 50EF',   'ATIVO', NULL, NULL, NULL),
(8,  '8GH',    13.10, 10,  25,  160, 6.90, 16.40, 9.80,  17.10, 100, 'APROVADO',  10, utc_timestamp(), '2025-08-03', 'Texto observacao relativo ao lote 8GH',    'ATIVO', NULL, NULL, NULL),
(11, '25IJ',   17.80, 85,  95,  410, 3.50, 14.90, 6.20,  6.50,  800, 'REPROVADO', 31, utc_timestamp(), '2025-07-26', 'Texto observacao relativo ao lote 25IJ',   'ATIVO', NULL, NULL, NULL),
(13, '30KL',   11.30, 50,  60,  220, 8.10, 18.10, 11.30, 19.20, 250, 'REPROVADO', 17, utc_timestamp(), '2025-07-28', 'Texto observacao relativo ao lote 30KL',   'ATIVO', NULL, NULL, NULL),
(14, '40MN',   19.40, 30,  40,  190, 5.70, 10.80, 2.90,  9.90,  500, 'APROVADO',  13, utc_timestamp(), '2025-07-25', 'Texto observacao relativo ao lote 40MN',   'ATIVO', NULL, NULL, NULL),
(17, '60OP',   14.70, 45,  55,  340, 4.20, 13.20, 7.50,  12.30, 750, 'REPROVADO', 10, utc_timestamp(), '2025-07-28', 'Texto observacao relativo ao lote 60OP',   'ATIVO', NULL, NULL, NULL),
(19, '70PQ',   16.20, 20,  15,  270, 6.80, 17.70, 10.70, 5.20,  300, 'APROVADO',  31, utc_timestamp(), '2025-07-21', 'Texto observacao relativo ao lote 70PQ',   'ATIVO', NULL, NULL, NULL);
-- Laudo 1 (sobre o item da Nota 31, produto 9)
-- INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao, observacao_laudo)
-- VALUES (61, 'LQ2025A', 14.80, 40, 55, 210, 5.20, 14.90, 7.10, 9.80, 420, 'APROVADO', 17, utc_timestamp(),'2025-09-02', 'Texto observacao relativo ao lote LQ2025A');

--  Laudo 2 (sobre o item da Nota 32, produto 17)
-- INSERT INTO laboratorio (item_nota_fiscal_id, numero_lote, cvm, pontos_finos, pontos_grossos, neps, h_pilosidade, resistencia, alongamento, titulo_ne, torcao_t_m, status, liberado_por, data_cadastro, data_realizacao, observacao_laudo)
-- VALUES (63, 'LQ2025B', 12.30, 25, 30, 150, 6.70, 18.40, 9.30, 10.90, 350, 'REPROVADO', 13, utc_timestamp(),'2025-09-02', 'Texto observacao relativo ao lote LQ2025B');


INSERT INTO engenharia (
    laudo_laboratorio_id,
    engenheiro,
    teste_amostra_fisica_tecelagem,
    teste_acabamento,
    decisao,
    restricao_uso,
    observacao,
    data_realizacao,
    data_cadastro,
    data_alteracao,
    criado_por,
    alterado_por,
    situacao,
    substituido_por,
    substitui,
    motivo_substituicao
) VALUES

-- lote 202100 — reprovado por variação de título e neps elevado
(2,  7,  TRUE,  FALSE, 'REPROVADO',       NULL,
    'Lote reprovado: neps acima do limite aceitável e título fora da tolerância.',
    '2025-07-26', utc_timestamp(), NULL, 16, NULL, 'ATIVO', NULL, NULL, NULL),

-- lote 225 — aprovado parcial, restrito a artigos de menor exigência
(4,  16, FALSE, TRUE,  'APROVADO_PARCIAL', 'Uso restrito a artigos de malha grossa categoria C. Proibido uso em malharia fina.',
    'Resistência abaixo do especificado. Aprovado parcialmente após teste em tecelagem.',
    '2025-06-20', utc_timestamp(), NULL, 3,  NULL, 'ATIVO', NULL, NULL, NULL),

-- lote 18J600 — reprovado, pilosidade e pontos grossos fora do limite
(6,  3,  TRUE,  TRUE,  'REPROVADO',       NULL,
    'Pilosidade e pontos grossos excedem limite máximo. Reprovado sem possibilidade de uso.',
    '2025-08-27', utc_timestamp(), NULL, 7,  NULL, 'ATIVO', NULL, NULL, NULL),

-- lote 300200 — reprovado, múltiplos parâmetros fora da especificação
(8,  7,  TRUE,  FALSE, 'REPROVADO',       NULL,
    'Resistência, alongamento e torção fora dos limites. Lote rejeitado integralmente.',
    '2025-08-27', utc_timestamp(), NULL, 16, NULL, 'ATIVO', NULL, NULL, NULL),

-- lote 7PZ — aprovado parcial com restrição de volume
(10, 16, FALSE, FALSE, 'APROVADO_PARCIAL', 'Uso permitido somente em ordens de produção de até 500kg. Monitoramento obrigatório no processo.',
    'Torção com desvio moderado. Amostra física aprovada em teste de tecelagem com restrição de volume.',
    '2025-08-04', utc_timestamp(), NULL, 3,  NULL, 'ATIVO', NULL, NULL, NULL),

-- lote 400300 — reprovado, composição suspeita no blend
(12, 3,  TRUE,  TRUE,  'REPROVADO',       NULL,
    'Blend CO/PES com proporção fora do especificado. Reprovado após teste de composição.',
    '2025-07-27', utc_timestamp(), NULL, 7,  NULL, 'ATIVO', NULL, NULL, NULL),

-- lote 50EF — aprovado parcial restrito a cor escura
(14, 7,  FALSE, TRUE,  'APROVADO_PARCIAL', 'Uso restrito a artigos em cores escuras. Vedado uso em artigos brancos ou pastéis.',
    'CVM elevado pode gerar irregularidade visual em cores claras. Aprovado para cores escuras após avaliação.',
    '2025-08-04', utc_timestamp(), NULL, 16, NULL, 'ATIVO', NULL, NULL, NULL),

-- lote 25IJ — reprovado, pontos finos e grossos acima do limite
(16, 16, TRUE,  FALSE, 'REPROVADO',       NULL,
    'Pontos finos e grossos muito acima do limite. Uniformidade comprometida.',
    '2025-07-29', utc_timestamp(), NULL, 3,  NULL, 'ATIVO', NULL, NULL, NULL),

-- lote 30KL — reprovado, título fora de especificação
(17, 3,  FALSE, TRUE,  'REPROVADO',       NULL,
    'Título Ne medido diverge do especificado em mais de 5%. Lote reprovado.',
    '2025-07-31', utc_timestamp(), NULL, 7,  NULL, 'ATIVO', NULL, NULL, NULL),

-- lote 60OP — aprovado parcial com restrição de processo
(19, 7,  TRUE,  TRUE,  'APROVADO_PARCIAL', 'Uso permitido somente em processo de malharia circular. Proibido uso em teares de pinça.',
    'Resistência à tração limítrofe. Aprovado para malharia circular após teste de amostra física.',
    '2025-07-31', utc_timestamp(), NULL, 16, NULL, 'ATIVO', NULL, NULL, NULL);