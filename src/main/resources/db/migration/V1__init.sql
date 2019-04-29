CREATE TABLE lost_pets (
  id BIGSERIAL PRIMARY KEY,
  name varchar(100) NOT NULL,
  type varchar(10) NOT NULL,
  gender varchar(8) NOT NULL,
  additional_info varchar(400),
  longitude decimal(9,6) NOT NULL,
  latitude decimal(9,6) NOT NULL,
  phone varchar(15),
  email varchar(30),
  created_at timestamp NOT NULL,
  last_seen timestamp NOT NULL
);