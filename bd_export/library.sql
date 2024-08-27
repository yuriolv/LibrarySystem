-- Table: public.comentario

-- DROP TABLE IF EXISTS public.comentario;

CREATE TABLE IF NOT EXISTS public.comentario
(
    id_livro integer NOT NULL,
	"ID" integer NOT NULL,
    matricula character varying(255) COLLATE pg_catalog."default" NOT NULL,
    "avaliação" character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT comentario_pkey PRIMARY KEY ("ID"),
    CONSTRAINT comentario_id_livro_fkey FOREIGN KEY (id_livro)
        REFERENCES public.livro ("ID") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT comentario_matricula_fkey FOREIGN KEY (matricula)
        REFERENCES public.usuario (matricula) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.comentario
    OWNER to postgres;