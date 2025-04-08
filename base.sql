create database prevision;

use prevision;

create table credit (
    id int primary key auto_increment,
    libelle varchar(50) not null,
    date_debut TIMESTAMP  not null,
    date_fin TIMESTAMP  not null,
    montant NUMERIC(10, 2)  not null
);

create table depense (
    id int primary key auto_increment,
    id_credit int  not null,
    montant NUMERIC(10, 2)  not null,
    date TIMESTAMP  not null,
    foreign key (id_credit) references credit(id)
);


SELECT tab.id_credit,tab.libelle,tab.montant,tab.total as total, (tab.montant-tab.total) as reste FROM (SELECT d.id_credit,c.libelle,c.montant,sum(d.montant) as total FROM depense d JOIN credit c on c.id=d.id_credit  GROUP BY d.id_credit) as tab;


SELECT (tab.montant-tab.total) as reste FROM (SELECT c.montant,sum(d.montant) as total FROM depense d JOIN credit c on c.id=d.id_credit WHERE d.id_credit = 1) as tab;

SELECT sum(montant) as total FROM depense WHERE id_credit = 2;



SELECT (tab.montant-tab.total) as reste FROM (SELECT c.montant,sum(d.montant) as total FROM depense d JOIN credit c on c.id=d.id_credit WHERE d.id_credit = 1) as tab;