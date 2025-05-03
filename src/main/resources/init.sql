-- PAPÉIS
INSERT INTO TB_PAPEL (id, papel) VALUES (1, 'ADMIN');


-- USUÁRIO
INSERT INTO usuarios (id, nome, email, senha) VALUES
(1, 'Administrador', 'admin@clinic.com', '$2a$10$e0NRKYuPrEDOBqPTWhK1cOoZbm3lYJ1fAO.qepl9Sv4rMjMEWoa92');
-- Senha = admin123

-- VÍNCULO USUÁRIO ↔ PAPEL
INSERT INTO tb_usuario_papel (usuario_id, papel_id) VALUES (1, 1);


