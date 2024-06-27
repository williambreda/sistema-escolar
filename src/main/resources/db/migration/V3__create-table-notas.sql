CREATE TABLE nota (
     valor numeric(5,2) NOT NULL,
        alunoid text COLLATE pg_catalog."default" NOT NULL,
        disciplinaid text COLLATE pg_catalog."default" NOT NULL,
        id text COLLATE pg_catalog."default" NOT NULL,
        CONSTRAINT nota_pkey PRIMARY KEY (id),
        CONSTRAINT unique_nome UNIQUE (alunoid, disciplinaid),
        CONSTRAINT nota_alunoid_fkey FOREIGN KEY (alunoid)
            REFERENCES public.alunos (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION,
        CONSTRAINT nota_disciplinaid_fkey FOREIGN KEY (disciplinaid)
            REFERENCES public.disciplinas (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
);