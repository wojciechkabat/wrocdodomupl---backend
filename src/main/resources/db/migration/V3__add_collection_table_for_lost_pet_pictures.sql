CREATE TABLE lost_pet_pictures (
  pet_id BIGSERIAL,
  picture_url varchar(500)
);

ALTER TABLE lost_pet_pictures ADD CONSTRAINT fk_pet_id
  FOREIGN KEY (pet_id) REFERENCES lost_pets (id);
