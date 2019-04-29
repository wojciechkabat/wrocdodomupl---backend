CREATE TABLE lost_pet_pictures (
  picture_id varchar(60) PRIMARY KEY,
  picture_url varchar(500),
  pet_id BIGSERIAL
);

ALTER TABLE lost_pet_pictures ADD CONSTRAINT fk_pet_id
  FOREIGN KEY (pet_id) REFERENCES lost_pets (id);
