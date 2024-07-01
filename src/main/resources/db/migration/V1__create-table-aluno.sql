CREATE TABLE alunos (
    id text COLLATE pg_catalog."default" NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT alunos_pkey PRIMARY KEY (id),
    CONSTRAINT alunos_id_name_key UNIQUE (id, name)
);