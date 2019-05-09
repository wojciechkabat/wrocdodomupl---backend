ALTER TABLE confirm_tokens RENAME to pet_tokens;
ALTER TABLE pet_tokens ADD COLUMN type varchar(10);
