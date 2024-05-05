CREATE TABLE users (
     id text COLLATE pg_catalog."default" NOT NULL,
         login text COLLATE pg_catalog."default" NOT NULL,
         password text COLLATE pg_catalog."default" NOT NULL,
         role text COLLATE pg_catalog."default" NOT NULL,
         CONSTRAINT users_pkey PRIMARY KEY (id),
         CONSTRAINT users_login_key UNIQUE (login)
);