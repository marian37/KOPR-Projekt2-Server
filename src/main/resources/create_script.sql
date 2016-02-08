CREATE TABLE recepty (
    uuid VARCHAR(36) NOT NULL PRIMARY KEY,
    nazovReceptu VARCHAR(255),
    menoAutora VARCHAR(255),
    postupPripravy VARCHAR(10000)
);

CREATE TABLE ingrediencie (    
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nazov VARCHAR(255)
);

CREATE TABLE ingrediencieRecepty (
    uuidReceptu VARCHAR(36),
    idIngrediencie BIGINT,
    mnozstvo VARCHAR(255)
)

DROP TABLE recepty;
DROP TABLE ingrediencie;
DROP TABLE ingrediencieRecepty;

DELETE FROM recepty;
DELETE FROM ingrediencie;
DELETE FROM ingrediencieRecepty;

SELECT * FROM recepty;
SELECT * FROM ingrediencie;
SELECT * FROM ingrediencieRecepty;

INSERT INTO recepty VALUES
('0cbeef36-b07a-4bd5-ae89-ee32a7683805', 'Recept1', 'Autor1', 'Spagety povar v osolenej vode asi 10 minut. Nasledne dochut kecupom.'),
('bc46e9b9-26fc-4a79-84e0-a845824b603b', 'Recept2', 'Autor1', 'Parky povar vo vode asi 5 minut.'),
('2a653742-7e39-46df-ad22-fcc4e9453e2c', 'Recept3', 'Autor2', 'Puding v prasku vysypeme do mlieka, osladime a za neustaleho miesania varime asi tak 5 minut.');

INSERT INTO ingrediencie(nazov) VALUES
('voda'),
('kecup'),
('sol'),
('spagety'),
('parky'),
('mlieko'),
('puding'),
('cukor');

INSERT INTO ingrediencieRecepty VALUES
('bc46e9b9-26fc-4a79-84e0-a845824b603b', 5, '3 ks'),
('bc46e9b9-26fc-4a79-84e0-a845824b603b', 1, '1 l'),
('2a653742-7e39-46df-ad22-fcc4e9453e2c', 6, '0.5 l'),
('2a653742-7e39-46df-ad22-fcc4e9453e2c', 7, '37 g'),
('2a653742-7e39-46df-ad22-fcc4e9453e2c', 8, '2-3 polievkove lyzice'),
('0cbeef36-b07a-4bd5-ae89-ee32a7683805', 1, '5 l'),
('0cbeef36-b07a-4bd5-ae89-ee32a7683805', 3, 'stipka'),
('0cbeef36-b07a-4bd5-ae89-ee32a7683805', 4, '300 g'),
('0cbeef36-b07a-4bd5-ae89-ee32a7683805', 2, '30 g');