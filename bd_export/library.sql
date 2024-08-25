PGDMP  :    0                |            library    16.4    16.4 *    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16399    library    DATABASE     ~   CREATE DATABASE library WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE library;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   pg_database_owner    false    4            �            1259    16400    books    TABLE     �  CREATE TABLE public.books (
    "ID" integer NOT NULL,
    autor character varying(255),
    titulo character varying(255) NOT NULL,
    assunto character varying(255) NOT NULL,
    qtd_estoque integer NOT NULL,
    capa_livro bytea,
    colecao character varying(255),
    CONSTRAINT tipo_colecao CHECK (((colecao)::text = ANY (ARRAY[('Comum'::character varying)::text, ('Especial'::character varying)::text])))
);
    DROP TABLE public.books;
       public         heap    postgres    false    4            �            1259    16406    books_ID_seq    SEQUENCE     �   CREATE SEQUENCE public."books_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public."books_ID_seq";
       public          postgres    false    215    4            �           0    0    books_ID_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public."books_ID_seq" OWNED BY public.books."ID";
          public          postgres    false    216            �            1259    16407    comments    TABLE     �   CREATE TABLE public.comments (
    "ID" integer NOT NULL,
    titulo_livro character varying(255) NOT NULL,
    id_livro integer NOT NULL,
    "comentário" character varying(255) NOT NULL
);
    DROP TABLE public.comments;
       public         heap    postgres    false    4            �            1259    16412    comments_ID_seq    SEQUENCE     �   CREATE SEQUENCE public."comments_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public."comments_ID_seq";
       public          postgres    false    4    217            �           0    0    comments_ID_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public."comments_ID_seq" OWNED BY public.comments."ID";
          public          postgres    false    218            �            1259    16413    rents    TABLE     
  CREATE TABLE public.rents (
    "ID" integer NOT NULL,
    matricula integer NOT NULL,
    titulo_livro character varying(255) NOT NULL,
    data_aluguel date NOT NULL,
    "data_devolução" date GENERATED ALWAYS AS ((data_aluguel + '15 days'::interval)) STORED
);
    DROP TABLE public.rents;
       public         heap    postgres    false    4            �            1259    16417    rents_ID_seq    SEQUENCE     �   CREATE SEQUENCE public."rents_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public."rents_ID_seq";
       public          postgres    false    219    4            �           0    0    rents_ID_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public."rents_ID_seq" OWNED BY public.rents."ID";
          public          postgres    false    220            �            1259    16467    telefone    TABLE     o   CREATE TABLE public.telefone (
    matricula integer NOT NULL,
    telefone character varying(255) NOT NULL
);
    DROP TABLE public.telefone;
       public         heap    postgres    false    4            �            1259    16418    users    TABLE     C  CREATE TABLE public.users (
    matricula integer NOT NULL,
    nome character varying(255) NOT NULL,
    tipo character varying(255),
    senha character varying(255) NOT NULL,
    CONSTRAINT tipo_usuario CHECK (((tipo)::text = ANY (ARRAY[('Discente'::character varying)::text, ('Docente'::character varying)::text])))
);
    DROP TABLE public.users;
       public         heap    postgres    false    4            ,           2604    16457    books ID    DEFAULT     h   ALTER TABLE ONLY public.books ALTER COLUMN "ID" SET DEFAULT nextval('public."books_ID_seq"'::regclass);
 9   ALTER TABLE public.books ALTER COLUMN "ID" DROP DEFAULT;
       public          postgres    false    216    215            -           2604    16458    comments ID    DEFAULT     n   ALTER TABLE ONLY public.comments ALTER COLUMN "ID" SET DEFAULT nextval('public."comments_ID_seq"'::regclass);
 <   ALTER TABLE public.comments ALTER COLUMN "ID" DROP DEFAULT;
       public          postgres    false    218    217            .           2604    16459    rents ID    DEFAULT     h   ALTER TABLE ONLY public.rents ALTER COLUMN "ID" SET DEFAULT nextval('public."rents_ID_seq"'::regclass);
 9   ALTER TABLE public.rents ALTER COLUMN "ID" DROP DEFAULT;
       public          postgres    false    220    219            �          0    16400    books 
   TABLE DATA           _   COPY public.books ("ID", autor, titulo, assunto, qtd_estoque, capa_livro, colecao) FROM stdin;
    public          postgres    false    215   1/       �          0    16407    comments 
   TABLE DATA           O   COPY public.comments ("ID", titulo_livro, id_livro, "comentário") FROM stdin;
    public          postgres    false    217   �/       �          0    16413    rents 
   TABLE DATA           L   COPY public.rents ("ID", matricula, titulo_livro, data_aluguel) FROM stdin;
    public          postgres    false    219   �/       �          0    16467    telefone 
   TABLE DATA           7   COPY public.telefone (matricula, telefone) FROM stdin;
    public          postgres    false    222   �/       �          0    16418    users 
   TABLE DATA           =   COPY public.users (matricula, nome, tipo, senha) FROM stdin;
    public          postgres    false    221   �/       �           0    0    books_ID_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public."books_ID_seq"', 18, true);
          public          postgres    false    216            �           0    0    comments_ID_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public."comments_ID_seq"', 1, false);
          public          postgres    false    218            �           0    0    rents_ID_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public."rents_ID_seq"', 1, false);
          public          postgres    false    220            3           2606    16428    books books_ID_titulo_key 
   CONSTRAINT     ^   ALTER TABLE ONLY public.books
    ADD CONSTRAINT "books_ID_titulo_key" UNIQUE ("ID", titulo);
 E   ALTER TABLE ONLY public.books DROP CONSTRAINT "books_ID_titulo_key";
       public            postgres    false    215    215            5           2606    16430    books books_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pkey PRIMARY KEY ("ID");
 :   ALTER TABLE ONLY public.books DROP CONSTRAINT books_pkey;
       public            postgres    false    215            7           2606    16432    books books_titulo_key 
   CONSTRAINT     S   ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_titulo_key UNIQUE (titulo);
 @   ALTER TABLE ONLY public.books DROP CONSTRAINT books_titulo_key;
       public            postgres    false    215            9           2606    16434    comments comments_ID_key 
   CONSTRAINT     U   ALTER TABLE ONLY public.comments
    ADD CONSTRAINT "comments_ID_key" UNIQUE ("ID");
 D   ALTER TABLE ONLY public.comments DROP CONSTRAINT "comments_ID_key";
       public            postgres    false    217            ;           2606    16436    comments comments_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY ("ID");
 @   ALTER TABLE ONLY public.comments DROP CONSTRAINT comments_pkey;
       public            postgres    false    217            =           2606    16438    rents rents_ID_key 
   CONSTRAINT     O   ALTER TABLE ONLY public.rents
    ADD CONSTRAINT "rents_ID_key" UNIQUE ("ID");
 >   ALTER TABLE ONLY public.rents DROP CONSTRAINT "rents_ID_key";
       public            postgres    false    219            ?           2606    16440    rents rents_ID_key1 
   CONSTRAINT     P   ALTER TABLE ONLY public.rents
    ADD CONSTRAINT "rents_ID_key1" UNIQUE ("ID");
 ?   ALTER TABLE ONLY public.rents DROP CONSTRAINT "rents_ID_key1";
       public            postgres    false    219            A           2606    16442    rents rents_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.rents
    ADD CONSTRAINT rents_pkey PRIMARY KEY ("ID");
 :   ALTER TABLE ONLY public.rents DROP CONSTRAINT rents_pkey;
       public            postgres    false    219            G           2606    16471    telefone telefone_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY public.telefone
    ADD CONSTRAINT telefone_pkey PRIMARY KEY (matricula, telefone);
 @   ALTER TABLE ONLY public.telefone DROP CONSTRAINT telefone_pkey;
       public            postgres    false    222    222            C           2606    16444    users users_matricula_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_matricula_key UNIQUE (matricula);
 C   ALTER TABLE ONLY public.users DROP CONSTRAINT users_matricula_key;
       public            postgres    false    221            E           2606    16446    users users_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (matricula);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    221            H           2606    16447    comments comments_id_livro_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_id_livro_fkey FOREIGN KEY (id_livro) REFERENCES public.books("ID");
 I   ALTER TABLE ONLY public.comments DROP CONSTRAINT comments_id_livro_fkey;
       public          postgres    false    217    4661    215            I           2606    16452    rents rents_matricula_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.rents
    ADD CONSTRAINT rents_matricula_fkey FOREIGN KEY (matricula) REFERENCES public.users(matricula);
 D   ALTER TABLE ONLY public.rents DROP CONSTRAINT rents_matricula_fkey;
       public          postgres    false    219    221    4675            J           2606    16472     telefone telefone_matricula_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.telefone
    ADD CONSTRAINT telefone_matricula_fkey FOREIGN KEY (matricula) REFERENCES public.users(matricula);
 J   ALTER TABLE ONLY public.telefone DROP CONSTRAINT telefone_matricula_fkey;
       public          postgres    false    222    221    4675            �   b   x�34��,K,�t>�9%3=_!�4+�3�(?�(17����|NC�?N����\. �1/��'3� �8%%-;�3(5%�����ص� 5931�+F��� � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �     