PGDMP      "                |            Library    16.4    16.4 $    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16399    Library    DATABASE     �   CREATE DATABASE "Library" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE "Library";
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   pg_database_owner    false    4            �            1259    16604    aluguel    TABLE       CREATE TABLE public.aluguel (
    "ID" integer NOT NULL,
    matricula character varying(255) NOT NULL,
    id_livro integer NOT NULL,
    data_aluguel date NOT NULL,
    "data_devolução" date GENERATED ALWAYS AS ((data_aluguel + '15 days'::interval)) STORED NOT NULL
);
    DROP TABLE public.aluguel;
       public         heap    postgres    false    4            �            1259    16603    aluguel_ID_seq    SEQUENCE     �   CREATE SEQUENCE public."aluguel_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public."aluguel_ID_seq";
       public          postgres    false    4    221            �           0    0    aluguel_ID_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public."aluguel_ID_seq" OWNED BY public.aluguel."ID";
          public          postgres    false    220            �            1259    16576 
   comentario    TABLE     �   CREATE TABLE public.comentario (
    id_livro integer NOT NULL,
    matricula character varying(255) NOT NULL,
    "avaliação" character varying(255) NOT NULL
);
    DROP TABLE public.comentario;
       public         heap    postgres    false    4            �            1259    16543    livro    TABLE     �  CREATE TABLE public.livro (
    "ID" integer NOT NULL,
    autor character varying(255) NOT NULL,
    titulo character varying(255) NOT NULL,
    assunto character varying(255),
    qtd_estoque integer NOT NULL,
    capa_livro bytea NOT NULL,
    colecao character varying(255) NOT NULL,
    CONSTRAINT livros_colecao CHECK (((colecao)::text = ANY (ARRAY[('Comum'::character varying)::text, ('Especial'::character varying)::text])))
);
    DROP TABLE public.livro;
       public         heap    postgres    false    4            �            1259    16542    livros_ID_seq    SEQUENCE     �   CREATE SEQUENCE public."livros_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public."livros_ID_seq";
       public          postgres    false    4    217            �           0    0    livros_ID_seq    SEQUENCE OWNED BY     B   ALTER SEQUENCE public."livros_ID_seq" OWNED BY public.livro."ID";
          public          postgres    false    216            �            1259    16554    telefone    TABLE     |   CREATE TABLE public.telefone (
    matricula character varying(255) NOT NULL,
    numero character varying(255) NOT NULL
);
    DROP TABLE public.telefone;
       public         heap    postgres    false    4            �            1259    16478    usuario    TABLE     T  CREATE TABLE public.usuario (
    matricula character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    tipo character varying(255),
    senha character varying(255) NOT NULL,
    CONSTRAINT tipo_usuario CHECK (((tipo)::text = ANY (ARRAY[('Discente'::character varying)::text, ('Docente'::character varying)::text])))
);
    DROP TABLE public.usuario;
       public         heap    postgres    false    4            ,           2604    16607 
   aluguel ID    DEFAULT     l   ALTER TABLE ONLY public.aluguel ALTER COLUMN "ID" SET DEFAULT nextval('public."aluguel_ID_seq"'::regclass);
 ;   ALTER TABLE public.aluguel ALTER COLUMN "ID" DROP DEFAULT;
       public          postgres    false    220    221    221            +           2604    16546    livro ID    DEFAULT     i   ALTER TABLE ONLY public.livro ALTER COLUMN "ID" SET DEFAULT nextval('public."livros_ID_seq"'::regclass);
 9   ALTER TABLE public.livro ALTER COLUMN "ID" DROP DEFAULT;
       public          postgres    false    216    217    217            �          0    16604    aluguel 
   TABLE DATA           J   COPY public.aluguel ("ID", matricula, id_livro, data_aluguel) FROM stdin;
    public          postgres    false    221   F*       �          0    16576 
   comentario 
   TABLE DATA           H   COPY public.comentario (id_livro, matricula, "avaliação") FROM stdin;
    public          postgres    false    219   c*       �          0    16543    livro 
   TABLE DATA           _   COPY public.livro ("ID", autor, titulo, assunto, qtd_estoque, capa_livro, colecao) FROM stdin;
    public          postgres    false    217   �*       �          0    16554    telefone 
   TABLE DATA           5   COPY public.telefone (matricula, numero) FROM stdin;
    public          postgres    false    218   �*       �          0    16478    usuario 
   TABLE DATA           ?   COPY public.usuario (matricula, nome, tipo, senha) FROM stdin;
    public          postgres    false    215   �*       �           0    0    aluguel_ID_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public."aluguel_ID_seq"', 1, false);
          public          postgres    false    220            �           0    0    livros_ID_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public."livros_ID_seq"', 1, false);
          public          postgres    false    216            =           2606    16610    aluguel aluguel_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.aluguel
    ADD CONSTRAINT aluguel_pkey PRIMARY KEY ("ID");
 >   ALTER TABLE ONLY public.aluguel DROP CONSTRAINT aluguel_pkey;
       public            postgres    false    221            9           2606    16584 ,   comentario comentario_id_livro_matricula_key 
   CONSTRAINT        ALTER TABLE ONLY public.comentario
    ADD CONSTRAINT comentario_id_livro_matricula_key UNIQUE (id_livro) INCLUDE (matricula);
 V   ALTER TABLE ONLY public.comentario DROP CONSTRAINT comentario_id_livro_matricula_key;
       public            postgres    false    219    219            ;           2606    16582    comentario comentario_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY public.comentario
    ADD CONSTRAINT comentario_pkey PRIMARY KEY (id_livro, matricula);
 D   ALTER TABLE ONLY public.comentario DROP CONSTRAINT comentario_pkey;
       public            postgres    false    219    219            3           2606    16553    livro livros_ID_key 
   CONSTRAINT     P   ALTER TABLE ONLY public.livro
    ADD CONSTRAINT "livros_ID_key" UNIQUE ("ID");
 ?   ALTER TABLE ONLY public.livro DROP CONSTRAINT "livros_ID_key";
       public            postgres    false    217            5           2606    16551    livro livros_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.livro
    ADD CONSTRAINT livros_pkey PRIMARY KEY ("ID");
 ;   ALTER TABLE ONLY public.livro DROP CONSTRAINT livros_pkey;
       public            postgres    false    217            7           2606    16560    telefone telefones_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.telefone
    ADD CONSTRAINT telefones_pkey PRIMARY KEY (matricula, numero);
 A   ALTER TABLE ONLY public.telefone DROP CONSTRAINT telefones_pkey;
       public            postgres    false    218    218            1           2606    16485    usuario usuarios_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (matricula);
 ?   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuarios_pkey;
       public            postgres    false    215            A           2606    16616    aluguel aluguel_id_livro_fkey    FK CONSTRAINT        ALTER TABLE ONLY public.aluguel
    ADD CONSTRAINT aluguel_id_livro_fkey FOREIGN KEY (id_livro) REFERENCES public.livro("ID");
 G   ALTER TABLE ONLY public.aluguel DROP CONSTRAINT aluguel_id_livro_fkey;
       public          postgres    false    221    217    4661            B           2606    16611    aluguel aluguel_matricula_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.aluguel
    ADD CONSTRAINT aluguel_matricula_fkey FOREIGN KEY (matricula) REFERENCES public.usuario(matricula);
 H   ALTER TABLE ONLY public.aluguel DROP CONSTRAINT aluguel_matricula_fkey;
       public          postgres    false    4657    215    221            ?           2606    16585 #   comentario comentario_id_livro_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.comentario
    ADD CONSTRAINT comentario_id_livro_fkey FOREIGN KEY (id_livro) REFERENCES public.livro("ID");
 M   ALTER TABLE ONLY public.comentario DROP CONSTRAINT comentario_id_livro_fkey;
       public          postgres    false    4661    217    219            @           2606    16590 $   comentario comentario_matricula_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.comentario
    ADD CONSTRAINT comentario_matricula_fkey FOREIGN KEY (matricula) REFERENCES public.usuario(matricula);
 N   ALTER TABLE ONLY public.comentario DROP CONSTRAINT comentario_matricula_fkey;
       public          postgres    false    4657    219    215            >           2606    16561 !   telefone telefones_matricula_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.telefone
    ADD CONSTRAINT telefones_matricula_fkey FOREIGN KEY (matricula) REFERENCES public.usuario(matricula);
 K   ALTER TABLE ONLY public.telefone DROP CONSTRAINT telefones_matricula_fkey;
       public          postgres    false    4657    218    215            �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �     