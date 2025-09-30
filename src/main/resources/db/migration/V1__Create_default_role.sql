-- Crea il ruolo di default se non esiste
INSERT INTO ruoli (id, ruolo_permessi) VALUES (1, 'USER') ON CONFLICT DO NOTHING;

-- Imposta default sulla colonna role_id
ALTER TABLE Utenti ALTER COLUMN role_id SET DEFAULT 1;
