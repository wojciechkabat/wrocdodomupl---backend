CREATE TABLE confirm_tokens (
  token UUID PRIMARY KEY,
  pet_id UUID
);

ALTER TABLE pet_pictures ADD CONSTRAINT fk_confirm_pet_id
  FOREIGN KEY (pet_id) REFERENCES pets (id);
