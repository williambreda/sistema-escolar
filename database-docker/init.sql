CREATE DATABASE sistemaescolar;

CREATE TABLE aluno (
    id text COLLATE pg_catalog."default" NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT alunos_pkey PRIMARY KEY (id),
    CONSTRAINT alunos_id_name_key UNIQUE (id, name)
);

CREATE TABLE disciplinas (
    id text COLLATE pg_catalog."default" NOT NULL,
        name text COLLATE pg_catalog."default" NOT NULL,
        CONSTRAINT disciplina_pkey PRIMARY KEY (id)
);

CREATE TABLE nota (
     valor numeric(5,2) NOT NULL,
        alunoid text COLLATE pg_catalog."default" NOT NULL,
        disciplinaid text COLLATE pg_catalog."default" NOT NULL,
        id text COLLATE pg_catalog."default" NOT NULL,
        CONSTRAINT nota_pkey PRIMARY KEY (id),
        CONSTRAINT unique_nome UNIQUE (alunoid, disciplinaid),
        CONSTRAINT nota_alunoid_fkey FOREIGN KEY (alunoid)
            REFERENCES public.aluno (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION,
        CONSTRAINT nota_disciplinaid_fkey FOREIGN KEY (disciplinaid)
            REFERENCES public.disciplinas (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
);

CREATE TABLE users (
     id text COLLATE pg_catalog."default" NOT NULL,
         login text COLLATE pg_catalog."default" NOT NULL,
         password text COLLATE pg_catalog."default" NOT NULL,
         role text COLLATE pg_catalog."default" NOT NULL,
         CONSTRAINT users_pkey PRIMARY KEY (id),
         CONSTRAINT users_login_key UNIQUE (login)
);