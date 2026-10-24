CREATE TABLE metas_ambientais (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario INT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    id_indicador INT NOT NULL REFERENCES indicadores_ambientais(id) ON DELETE CASCADE,
    descricao VARCHAR(200) NOT NULL,
    valor_meta DECIMAL(10,2) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    status VARCHAR(100) NOT NULL DEFAULT 'pendente',
    CHECK (data_fim >= data_inicio)
);