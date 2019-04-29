CREATE TABLE pets (
  id UUID PRIMARY KEY,
  name varchar(100),
  type varchar(10) NOT NULL,
  gender varchar(8),
  additional_info varchar(400),
  longitude decimal(9,6) NOT NULL,
  latitude decimal(9,6) NOT NULL,
  phone varchar(15),
  email varchar(30) NOT NULL,
  created_at timestamp NOT NULL,
  last_seen timestamp NOT NULL,
  status varchar(5) NOT NULL
);