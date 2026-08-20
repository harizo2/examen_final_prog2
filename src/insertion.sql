INSERT INTO donations (cash_flow_id, comment)
SELECT id, 'Don mensuel de soutien aux étudiants boursiers'
FROM cash_flows WHERE amount = 250000.00 AND type = 'DONATION';

INSERT INTO donations (cash_flow_id, comment)
SELECT id, NULL
FROM cash_flows WHERE amount = 50000.00 AND type = 'DONATION';

INSERT INTO donations (cash_flow_id, comment)
SELECT id, 'Don collecté lors de la campagne de rentrée universitaire'
FROM cash_flows WHERE amount = 180000.00 AND type = 'DONATION';

INSERT INTO expenses (cash_flow_id, domain, frequency)
SELECT id, 'Hébergement serveur AWS', 'MONTHLY'INSERT INTO donations (cash_flow_id, comment)
SELECT id, 'Don pour la construction de la bibliothèque HEI'
FROM cash_flows WHERE amou
    FROM cash_flows WHERE amount = 45000.00 AND type = 'EXPENSE';

INSERT INTO expenses (cash_flow_id, domain, frequency)
SELECT id, 'Achat de fournitures scolaires', 'NONE'
FROM cash_flows WHERE amount = 12000.00 AND type = 'EXPENSE';

INSERT INTO expenses (cash_flow_id, domain, frequency)
SELECT id, 'Licence logicielle annuelle', 'YEARLY'
FROM cash_flows WHERE amount = 90000.00 AND type = 'EXPENSE';

INSERT INTO expenses (cash_flow_id, domain, frequency)
SELECT id, 'Frais de transport hebdomadaire', 'WEEKLY'
FROM cash_flows WHERE amount = 35000.00 AND type = 'EXPENSE';



INSERT INTO users (ref, first_name, last_name, email, phone) VALUES
                                                                 ('USR-2026-006', 'Tojo', 'Rakotonirina', 'tojo.rakotonirina@gmail.com', '+261 34 56 789 01'),
                                                                 ('USR-2026-007', 'Mialy', 'Razafindrakoto', 'mialy.razafindrakoto@gmail.com', '+261 33 21 098 76'),
                                                                 ('USR-2026-008', 'Hery', 'Rabemananjara', 'hery.rabemananjara@yahoo.fr', '+261 32 65 432 10'),
                                                                 ('USR-2026-009', 'Onja', 'Rasoanaivo', 'onja.rasoanaivo@outlook.com', '+261 34 87 123 45'),
                                                                 ('USR-2026-010', 'Lala', 'Andriamahefa', 'lala.andriamahefa@gmail.com', '+261 33 11 222 33');