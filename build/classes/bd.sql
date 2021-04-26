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
	  
	CREATE TABLE public.usuario (
	  id integer NOT NULL DEFAULT nextval('usuario_id_seq'::regclass),
	  login character varying(255) NOT NULL,
	  senha character varying(255) NOT NULL,
	  nome character varying(500),
	  cep character varying(200),
	  rua character varying(200),
	  bairro character varying(200),
	  cidade character varying(200),
	  uf character varying(200),
	  ibge character varying(200),
	  fotobase64 text,
	  contenttype text,
	  curriculobase64 text,
	  contenttypecurriculo text,
	  fotominiatura text,
	  CONSTRAINT usuario_id PRIMARY KEY (id)
	)
	WITH (OIDS=FALSE);
	ALTER TABLE public.usuario OWNER TO postgres;


	  
	CREATE SEQUENCE public.produto_id_seq
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 14
	  CACHE 1;
	ALTER TABLE public.produto_id_seq OWNER TO postgres;	  
	  
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
	ALTER TABLE public.produto OWNER TO postgres;
	 
	ALTER TABLE produto ALTER COLUMN quantidade TYPE integer;
	
	  
	CREATE SEQUENCE public.fonesequence
	  INCREMENT 1
	  MINVALUE 1
	  MAXVALUE 9223372036854775807
	  START 1
	  CACHE 1;
	ALTER TABLE public.fonesequence OWNER TO postgres;

	CREATE TABLE telefone (
	  id bigint NOT NULL DEFAULT nextval('fonesequence'::regclass),
	  numero character varying(200) NOT NULL,
	  tipo character varying(200) NOT NULL,
	  usuario bigint NOT NULL,
	  CONSTRAINT fone_pkey PRIMARY KEY (id)
	)
	WITH (OIDS=FALSE);
	ALTER TABLE telefone OWNER TO postgres;

	INSERT INTO telefone (numero, tipo, usuario) VALUES ('2038 9384', 'Casa', '79');
	SELECT * FROM telefone;
	DELETE FROM telefone WHERE id = 1;
	
	
	
	