set foreign_key_checks = 0;

delete from usuario;

set foreign_key_checks = 1;

alter table usuario auto_increment = 1;

-- afterMigrate.sql
-- Insere 14 usuários com diferentes roles e estados ativo/inativo para testes no sistema FiberGuardian

INSERT INTO usuario (nome, email, senha, ativo, role) VALUES
('Ana Carolina Souza', 'ana.souza@fiberguardian.com', 'senha123', TRUE, 'ADMIN'),
('Bruno Lima', 'bruno.lima@fiberguardian.com', 'senha456', TRUE, 'USUARIO'),
('Clara Mendes', 'clara.mendes@fiberguardian.com', 'senha789', FALSE, 'LABORATORIO'),
('Diego Ferreira', 'diego.ferr@fiberguardian.com', 'senha101', TRUE, 'ENGENHARIA'),
('Elisa Costa', 'elisa.costa@fiberguardian.com', 'senha202', TRUE, 'USUARIO'),
('Felipe Santos', 'felipe.santos@fiberguardian.com', 'senha303', TRUE, 'LABORATORIO'),
('Gabriela Silva', 'gabriela.silva@fiberguardian.com', 'senha404', FALSE, 'ENGENHARIA'),
('Henrique Almeida', 'henrique.almeida@fiberguardian.com', 'senha505', TRUE, 'ADMIN'),
('Isabela Pereira', 'isabela.pereira@fiberguardian.com', 'senha606', TRUE, 'USUARIO'),
('João Oliveira', 'joao.oliveira@fiberguardian.com', 'senha707', TRUE, 'LABORATORIO'),
('Karina Rocha', 'karina.rocha@fiberguardian.com', 'senha808', FALSE, 'USUARIO'),
('Lucas Martins', 'lucas.martins@fiberguardian.com', 'senha909', TRUE, 'ENGENHARIA'),
('Marina Ribeiro', 'marina.ribeiro@fiberguardian.com', 'senha010', TRUE, 'LABORATORIO'),
('Pedro Gonçalves', 'pedro.goncalves@fiberguardian.com', 'senha111', FALSE, 'USUARIO');
