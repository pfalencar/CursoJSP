CREATE DATABASE "curso-jsp"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Portuguese_Brazil.1252'
       LC_CTYPE = 'Portuguese_Brazil.1252'
       CONNECTION LIMIT = -1;
       
       
   CREATE SEQUENCE public.usuario_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 37
	  CACHE 1;
	ALTER TABLE public.usuario_id_seq
	  OWNER TO postgres;
	  
	  
	CREATE TABLE public.usuario
	(
	  id integer NOT NULL DEFAULT nextval('usuario_id_seq'::regclass),
	  login character varying(255) NOT NULL,
	  senha character varying(255) NOT NULL,
	  nome character varying(500),
	  CONSTRAINT usuario_id PRIMARY KEY (id)
	)
	WITH (
	  OIDS=FALSE
	);
	ALTER TABLE public.usuario
	  OWNER TO postgres;
	  
	INSERT INTO usuario (id, login, senha, nome) VALUES (1, 'admin', 'admin', 'Administrador');
	INSERT INTO usuario (id, login, senha, nome) VALUES (2, 'pati', 'ppp', 'Patricia');
	
	
	CREATE TABLE public.produto
	(
	  id integer NOT NULL DEFAULT nextval('produto_id_seq'::regclass),
	  nome character varying(255) NOT NULL,
	  quantidade numeric(10,2) NOT NULL,
	  valor numeric(10,2) NOT NULL,
	  CONSTRAINT produto_id PRIMARY KEY (id)
	)
	WITH (
	  OIDS=FALSE
	);
	ALTER TABLE public.produto
	  OWNER TO postgres;
	  
	CREATE SEQUENCE public.produto_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 14
	  CACHE 1;
	ALTER TABLE public.produto_id_seq
	  OWNER TO postgres;
	