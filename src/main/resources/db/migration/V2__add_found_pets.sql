CREATE TABLE found_pets (
  id BIGSERIAL PRIMARY KEY,
  type varchar(10) NOT NULL,
  gender varchar(8),
  additional_info varchar(400),
  picture_url varchar(500),
  longitude decimal(9,6) NOT NULL,
  latitude decimal(9,6) NOT NULL,
  phone varchar(15),
  email varchar(30),
  created_at timestamp NOT NULL,
  when_seen timestamp NOT NULL
);

ALTER TABLE lost_pets ALTER COLUMN longitude decimal(9,6) NOT NULL;
ALTER TABLE lost_pets ALTER COLUMN latitude decimal(9,6) NOT NULL;