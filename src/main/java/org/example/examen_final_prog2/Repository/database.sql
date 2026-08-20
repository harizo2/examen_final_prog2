DROP TABLE IF EXISTS expenses CASCADE;
DROP TABLE IF EXISTS donations CASCADE;
DROP TABLE IF EXISTS cash_flows CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TYPE IF EXISTS expense_frequency CASCADE;
DROP TYPE IF EXISTS cash_flow_type CASCADE;



CREATE TYPE expense_frequency AS ENUM ('NONE', 'MONTHLY', 'WEEKLY', 'YEARLY');
CREATE TYPE cash_flow_type AS ENUM ('DONATION', 'EXPENSE');

CREATE TABLE users (
                       id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       ref         VARCHAR(50)  NOT NULL UNIQUE,
                       first_name  VARCHAR(100) NOT NULL,
                       last_name   VARCHAR(100) NOT NULL,
                       email       VARCHAR(255) NOT NULL UNIQUE,
                       phone       VARCHAR(30)
);



CREATE TABLE cash_flows (
                            id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            user_id     UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                            created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
                            amount      NUMERIC(14,2) NOT NULL CHECK (amount >= 0),
                            type        cash_flow_type NOT NULL
);



CREATE INDEX idx_cash_flows_user_id ON cash_flows(user_id);
CREATE INDEX idx_cash_flows_type    ON cash_flows(type);


CREATE TABLE donations (
                           cash_flow_id UUID PRIMARY KEY REFERENCES cash_flows(id) ON DELETE CASCADE,
                           comment      TEXT
);



CREATE TABLE expenses (
                          cash_flow_id UUID PRIMARY KEY REFERENCES cash_flows(id) ON DELETE CASCADE,
                          domain       VARCHAR(150) NOT NULL,
                          frequency    expense_frequency NOT NULL DEFAULT 'NONE'
);


CREATE OR REPLACE VIEW v_balance AS
SELECT
    COALESCE(SUM(CASE WHEN type = 'DONATION' THEN amount ELSE 0 END), 0)
        - COALESCE(SUM(CASE WHEN type = 'EXPENSE' THEN amount ELSE 0 END), 0) AS balance
FROM cash_flows;

CREATE OR REPLACE VIEW v_cash_flows AS
SELECT
    cf.id,
    cf.user_id,
    cf.created_at,
    cf.amount,
    cf.type,
    d.comment,
    e.domain,
    e.frequency
FROM cash_flows cf
         LEFT JOIN donations d ON d.cash_flow_id = cf.id
         LEFT JOIN expenses  e ON e.cash_flow_id = cf.id;


INSERT INTO donations (cash_flow_id, comment)
SELECT id, 'Don pour la construction de la bibliothèque HEI'
FROM cash_flows WHERE amount = 100000.00 AND type = 'DONATION';

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
SELECT id, 'Hébergement serveur AWS', 'MONTHLY'
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