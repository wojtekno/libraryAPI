DROP TABLE IF EXISTS BOOK_DAO;

CREATE TABLE BOOK_DAO (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  isbn VARCHAR,
  book_id VARCHAR,
  title VARCHAR,
  subtitle VARCHAR,
  publisher VARCHAR,
  published_date BIGINT(20),
  description TEXT,
  page_count BIGINT(20),
  thumbnail_url VARCHAR,
  _language VARCHAR,
  preview_link VARCHAR,
  average_rating FLOAT(4));