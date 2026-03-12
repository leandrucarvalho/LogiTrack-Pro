CREATE TABLE IF NOT EXISTS veiculos (
    id SERIAL PRIMARY KEY,
    placa VARCHAR(10) UNIQUE NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    tipo VARCHAR(20) CHECK (tipo IN ('LEVE', 'PESADO')),
    ano INTEGER
);

CREATE TABLE IF NOT EXISTS viagens (
    id SERIAL PRIMARY KEY,
    veiculo_id INTEGER REFERENCES veiculos(id) ON DELETE CASCADE,
    data_saida TIMESTAMP NOT NULL,
    data_chegada TIMESTAMP,
    origem VARCHAR(100),
    destino VARCHAR(100),
    km_percorrida DECIMAL(10,2)
);

CREATE TABLE IF NOT EXISTS manutencoes (
    id SERIAL PRIMARY KEY,
    veiculo_id INTEGER REFERENCES veiculos(id) ON DELETE CASCADE,
    data_inicio DATE NOT NULL,
    data_finalizacao DATE,
    tipo_servico VARCHAR(100),
    custo_estimado DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'PENDENTE'
);

CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    senha VARCHAR(100) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario_roles (
    usuario_id INTEGER REFERENCES usuarios(id) ON DELETE CASCADE,
    role_id INTEGER REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (usuario_id, role_id)
);
