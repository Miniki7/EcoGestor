CREATE TABLE indicadores_ambientais (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario INT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    consumo_agua DECIMAL(10,2) NOT NULL,
    consumo_energia DECIMAL(10,2) NOT NULL,
    residuos_gerados DECIMAL(10,2) NOT NULL,
    residuos_reciclados DECIMAL(10,2) NOT NULL,
    mes_referencia DATE NOT NULL,
    observacao TEXT,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (id_usuario, mes_referencia)
);