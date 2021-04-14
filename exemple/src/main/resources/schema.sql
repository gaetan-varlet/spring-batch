
CREATE TABLE public.produit (
    id bigint NOT NULL,
    availability integer,
    description character varying(255),
    hauteur real NOT NULL,
    largeur real NOT NULL,
    longueur real NOT NULL,
    nom character varying(255),
    prix_unitaire real,
    reference character varying(255),
    fournisseur_id bigint
);
