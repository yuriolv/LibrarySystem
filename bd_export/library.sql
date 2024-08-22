PGDMP      2                |            Library    16.4    16.4 $               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16397    Library    DATABASE     �   CREATE DATABASE "Library" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE "Library";
                postgres    false            �            1259    16460    books    TABLE     �  CREATE TABLE public.books (
    "ID" integer NOT NULL,
    autor character varying(255),
    titulo character varying(255) NOT NULL,
    assunto character varying(255) NOT NULL,
    qtd_estoque integer NOT NULL,
    capa_livro bytea,
    colecao character varying(255),
    CONSTRAINT tipo_colecao CHECK (((colecao)::text = ANY ((ARRAY['Comum'::character varying, 'Especial'::character varying])::text[])))
);
    DROP TABLE public.books;
       public         heap    postgres    false            �            1259    16459    books_ID_seq    SEQUENCE     �   CREATE SEQUENCE public."books_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public."books_ID_seq";
       public          postgres    false    219                       0    0    books_ID_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public."books_ID_seq" OWNED BY public.books."ID";
          public          postgres    false    218            �            1259    16478    comments    TABLE     �   CREATE TABLE public.comments (
    "ID" integer NOT NULL,
    titulo_livro character varying(255) NOT NULL,
    id_livro integer NOT NULL,
    "comentário" character varying(255) NOT NULL
);
    DROP TABLE public.comments;
       public         heap    postgres    false            �            1259    16477    comments_ID_seq    SEQUENCE     �   CREATE SEQUENCE public."comments_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public."comments_ID_seq";
       public          postgres    false    221                       0    0    comments_ID_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public."comments_ID_seq" OWNED BY public.comments."ID";
          public          postgres    false    220            �            1259    16447    rents    TABLE     
  CREATE TABLE public.rents (
    "ID" integer NOT NULL,
    matricula integer NOT NULL,
    titulo_livro character varying(255) NOT NULL,
    data_aluguel date NOT NULL,
    "data_devolução" date GENERATED ALWAYS AS ((data_aluguel + '15 days'::interval)) STORED
);
    DROP TABLE public.rents;
       public         heap    postgres    false            �            1259    16446    rents_ID_seq    SEQUENCE     �   CREATE SEQUENCE public."rents_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public."rents_ID_seq";
       public          postgres    false    217                       0    0    rents_ID_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public."rents_ID_seq" OWNED BY public.rents."ID";
          public          postgres    false    216            �            1259    16427    users    TABLE     =  CREATE TABLE public.users (
    matricula integer NOT NULL,
    nome character varying(255) NOT NULL,
    tipo character varying(255),
    senha character varying(255) NOT NULL,
    CONSTRAINT tipo_usuario CHECK (((tipo)::text = ANY ((ARRAY['Discente'::character varying, 'Docente'::character varying])::text[])))
);
    DROP TABLE public.users;
       public         heap    postgres    false            `           2604    16463    books ID    DEFAULT     h   ALTER TABLE ONLY public.books ALTER COLUMN "ID" SET DEFAULT nextval('public."books_ID_seq"'::regclass);
 9   ALTER TABLE public.books ALTER COLUMN "ID" DROP DEFAULT;
       public          postgres    false    218    219    219            a           2604    16481    comments ID    DEFAULT     n   ALTER TABLE ONLY public.comments ALTER COLUMN "ID" SET DEFAULT nextval('public."comments_ID_seq"'::regclass);
 <   ALTER TABLE public.comments ALTER COLUMN "ID" DROP DEFAULT;
       public          postgres    false    221    220    221            ^           2604    16450    rents ID    DEFAULT     h   ALTER TABLE ONLY public.rents ALTER COLUMN "ID" SET DEFAULT nextval('public."rents_ID_seq"'::regclass);
 9   ALTER TABLE public.rents ALTER COLUMN "ID" DROP DEFAULT;
       public          postgres    false    217    216    217                      0    16460    books 
   TABLE DATA           _   COPY public.books ("ID", autor, titulo, assunto, qtd_estoque, capa_livro, colecao) FROM stdin;
    public          postgres    false    219   �(                 0    16478    comments 
   TABLE DATA           O   COPY public.comments ("ID", titulo_livro, id_livro, "comentário") FROM stdin;
    public          postgres    false    221   j)                 0    16447    rents 
   TABLE DATA           L   COPY public.rents ("ID", matricula, titulo_livro, data_aluguel) FROM stdin;
    public          postgres    false    217   �)       	          0    16427    users 
   TABLE DATA           =   COPY public.users (matricula, nome, tipo, senha) FROM stdin;
    public          postgres    false    215   �)                  0    0    books_ID_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public."books_ID_seq"', 18, true);
          public          postgres    false    218                       0    0    comments_ID_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public."comments_ID_seq"', 1, false);
          public          postgres    false    220                       0    0    rents_ID_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public."rents_ID_seq"', 1, false);
          public          postgres    false    216            o           2606    16500    books books_ID_titulo_key 
   CONSTRAINT     ^   ALTER TABLE ONLY public.books
    ADD CONSTRAINT "books_ID_titulo_key" UNIQUE ("ID", titulo);
 E   ALTER TABLE ONLY public.books DROP CONSTRAINT "books_ID_titulo_key";
       public            postgres    false    219    219            q           2606    16467    books books_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pkey PRIMARY KEY ("ID");
 :   ALTER TABLE ONLY public.books DROP CONSTRAINT books_pkey;
       public            postgres    false    219            s           2606    16497    books books_titulo_key 
   CONSTRAINT     S   ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_titulo_key UNIQUE (titulo);
 @   ALTER TABLE ONLY public.books DROP CONSTRAINT books_titulo_key;
       public            postgres    false    219            u           2606    16506    comments comments_ID_key 
   CONSTRAINT     U   ALTER TABLE ONLY public.comments
    ADD CONSTRAINT "comments_ID_key" UNIQUE ("ID");
 D   ALTER TABLE ONLY public.comments DROP CONSTRAINT "comments_ID_key";
       public            postgres    false    221            w           2606    16485    comments comments_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY ("ID");
 @   ALTER TABLE ONLY public.comments DROP CONSTRAINT comments_pkey;
       public            postgres    false    221            i           2606    16502    rents rents_ID_key 
   CONSTRAINT     O   ALTER TABLE ONLY public.rents
    ADD CONSTRAINT "rents_ID_key" UNIQUE ("ID");
 >   ALTER TABLE ONLY public.rents DROP CONSTRAINT "rents_ID_key";
       public            postgres    false    217            k           2606    16504    rents rents_ID_key1 
   CONSTRAINT     P   ALTER TABLE ONLY public.rents
    ADD CONSTRAINT "rents_ID_key1" UNIQUE ("ID");
 ?   ALTER TABLE ONLY public.rents DROP CONSTRAINT "rents_ID_key1";
       public            postgres    false    217            m           2606    16453    rents rents_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.rents
    ADD CONSTRAINT rents_pkey PRIMARY KEY ("ID");
 :   ALTER TABLE ONLY public.rents DROP CONSTRAINT rents_pkey;
       public            postgres    false    217            e           2606    16508    users users_matricula_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_matricula_key UNIQUE (matricula);
 C   ALTER TABLE ONLY public.users DROP CONSTRAINT users_matricula_key;
       public            postgres    false    215            g           2606    16433    users users_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (matricula);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    215            y           2606    16486    comments comments_id_livro_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_id_livro_fkey FOREIGN KEY (id_livro) REFERENCES public.books("ID");
 I   ALTER TABLE ONLY public.comments DROP CONSTRAINT comments_id_livro_fkey;
       public          postgres    false    221    219    4721            x           2606    16454    rents rents_matricula_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.rents
    ADD CONSTRAINT rents_matricula_fkey FOREIGN KEY (matricula) REFERENCES public.users(matricula);
 D   ALTER TABLE ONLY public.rents DROP CONSTRAINT rents_matricula_fkey;
       public          postgres    false    217    4711    215               b   x�34��,K,�t>�9%3=_!�4+�3�(?�(17����|NC�?N����\. �1/��'3� �8%%-;�3(5%�����ص� 5931�+F��� � �            x������ � �            x������ � �      	      x������ � �     