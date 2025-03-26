-- Insert Especialidade
INSERT INTO tb_especialidade (id, nome, descricao)
VALUES (0, 'teste', 'teste');

-- Insert Restaurante
INSERT INTO tb_restaurante (id, especialidade_id, capacidade_pessoas, nome, latitude, longitude, endereco_completo, horario_abertura, horario_fechamento, dias_funcionamento)
VALUES (0, 0, 10, 'string', 0, 0, 'string', '17:00:00', '23:00:00', 0);

-- Insert Reserva
INSERT INTO tb_reserva (id, restaurante_id, nome_cliente, quantidade_pessoas, data_reserva, data_saida, status_reserva)
VALUES (0, 0, 'string', 1, '2025-03-23T17:48:47.710', NULL, 0);