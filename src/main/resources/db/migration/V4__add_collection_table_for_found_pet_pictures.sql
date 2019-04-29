CREATE TABLE found_pet_pictures (
  pet_id BIGSERIAL,
  picture_url varchar(500)
);

ALTER TABLE found_pet_pictures ADD CONSTRAINT fk_found_pet_id
  FOREIGN KEY (pet_id) REFERENCES found_pets (id);
