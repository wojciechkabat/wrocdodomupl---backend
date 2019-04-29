CREATE TABLE pet_pictures (
  picture_id varchar(60) PRIMARY KEY,
  picture_url varchar(500),
  pet_id UUID
);

ALTER TABLE pet_pictures ADD CONSTRAINT fk_pet_id
  FOREIGN KEY (pet_id) REFERENCES pets (id);
