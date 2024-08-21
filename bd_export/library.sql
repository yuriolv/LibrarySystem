PGDMP                      |            Library    16.4    16.4                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16397    Library    DATABASE     �   CREATE DATABASE "Library" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE "Library";
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false            �            1259    16460    books    TABLE       CREATE TABLE public.books (
    "ID" integer NOT NULL,
    autor character varying(255),
    titulo character varying(255) NOT NULL,
    assunto character varying(255) NOT NULL,
    qtd_estoque integer NOT NULL,
    capa_livro bytea,
    colecao character varying(255)
);
    DROP TABLE public.books;
       public         heap    postgres    false    4            �            1259    16459    books_ID_seq    SEQUENCE     �   CREATE SEQUENCE public."books_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public."books_ID_seq";
       public          postgres    false    219    4            	           0    0    books_ID_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public."books_ID_seq" OWNED BY public.books."ID";
          public          postgres    false    218            �            1259    16478    comments    TABLE     �   CREATE TABLE public.comments (
    "ID" integer NOT NULL,
    titulo_livro character varying(255) NOT NULL,
    id_livro integer NOT NULL,
    "comentário" character varying(255) NOT NULL
);
    DROP TABLE public.comments;
       public         heap    postgres    false    4            �            1259    16477    comments_ID_seq    SEQUENCE     �   CREATE SEQUENCE public."comments_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public."comments_ID_seq";
       public          postgres    false    4    221            
           0    0    comments_ID_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public."comments_ID_seq" OWNED BY public.comments."ID";
          public          postgres    false    220            �            1259    16447    rents    TABLE     
  CREATE TABLE public.rents (
    "ID" integer NOT NULL,
    matricula integer NOT NULL,
    titulo_livro character varying(255) NOT NULL,
    data_aluguel date NOT NULL,
    "data_devolução" date GENERATED ALWAYS AS ((data_aluguel + '15 days'::interval)) STORED
);
    DROP TABLE public.rents;
       public         heap    postgres    false    4            �            1259    16446    rents_ID_seq    SEQUENCE     �   CREATE SEQUENCE public."rents_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public."rents_ID_seq";
       public          postgres    false    217    4                       0    0    rents_ID_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public."rents_ID_seq" OWNED BY public.rents."ID";
          public          postgres    false    216            �            1259    16427    users    TABLE     <  CREATE TABLE public.users (
    matricula integer NOT NULL,
    nome character varying(255) NOT NULL,
    tipo character varying(255),
    senha character varying(255) NOT NULL,
    CONSTRAINT tipo_usuario CHECK (((tipo)::text = ANY ((ARRAY['Aluno'::character varying, 'Professor'::character varying])::text[])))
);
    DROP TABLE public.users;
       public         heap    postgres    false    4            `           2604    16463    books ID    DEFAULT     h   ALTER TABLE ONLY public.books ALTER COLUMN "ID" SET DEFAULT nextval('public."books_ID_seq"'::regclass);
 9   ALTER TABLE public.books ALTER COLUMN "ID" DROP DEFAULT;
       public          postgres    false    219    218    219            a           2604    16481    comments ID    DEFAULT     n   ALTER TABLE ONLY public.comments ALTER COLUMN "ID" SET DEFAULT nextval('public."comments_ID_seq"'::regclass);
 <   ALTER TABLE public.comments ALTER COLUMN "ID" DROP DEFAULT;
       public          postgres    false    220    221    221            ^           2604    16450    rents ID    DEFAULT     h   ALTER TABLE ONLY public.rents ALTER COLUMN "ID" SET DEFAULT nextval('public."rents_ID_seq"'::regclass);
 9   ALTER TABLE public.rents ALTER COLUMN "ID" DROP DEFAULT;
       public          postgres    false    216    217    217                       0    16460    books 
   TABLE DATA                 public          postgres    false    219   �                  0    16478    comments 
   TABLE DATA                 public          postgres    false    221   �        �          0    16447    rents 
   TABLE DATA                 public          postgres    false    217   !       �          0    16427    users 
   TABLE DATA                 public          postgres    false    215   !                  0    0    books_ID_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public."books_ID_seq"', 1, false);
          public          postgres    false    218                       0    0    comments_ID_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public."comments_ID_seq"', 1, false);
          public          postgres    false    220                       0    0    rents_ID_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public."rents_ID_seq"', 1, false);
          public          postgres    false    216            h           2606    16467    books books_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pkey PRIMARY KEY ("ID");
 :   ALTER TABLE ONLY public.books DROP CONSTRAINT books_pkey;
       public            postgres    false    219            j           2606    16485    comments comments_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY ("ID");
 @   ALTER TABLE ONLY public.comments DROP CONSTRAINT comments_pkey;
       public            postgres    false    221            f           2606    16453    rents rents_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.rents
    ADD CONSTRAINT rents_pkey PRIMARY KEY ("ID");
 :   ALTER TABLE ONLY public.rents DROP CONSTRAINT rents_pkey;
       public            postgres    false    217            d           2606    16433    users users_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (matricula);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    215            l           2606    16486    comments comments_id_livro_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_id_livro_fkey FOREIGN KEY (id_livro) REFERENCES public.books("ID");
 I   ALTER TABLE ONLY public.comments DROP CONSTRAINT comments_id_livro_fkey;
       public          postgres    false    4712    221    219            k           2606    16454    rents rents_matricula_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.rents
    ADD CONSTRAINT rents_matricula_fkey FOREIGN KEY (matricula) REFERENCES public.users(matricula);
 D   ALTER TABLE ONLY public.rents DROP CONSTRAINT rents_matricula_fkey;
       public          postgres    false    215    217    4708                
   x���             
   x���          �   
   x���          �   |   x���
�  ���⿹�.��i�U�Pht�!$8:߿:}���p�R+���ݼ+٦�z1[rs�"��Rln�ن�i����0�f�C�8� ������?��s�@I�J^F�k���7!�����]#:     