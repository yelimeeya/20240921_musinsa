-- brand 테이블 생성
CREATE TABLE IF NOT EXISTS brand
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
    );

-- category 테이블 생성
CREATE TABLE IF NOT EXISTS category
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
    );

-- product 테이블 생성 (brand, category 외래키)
CREATE TABLE IF NOT EXISTS product
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    price INT NOT NULL,
    brand_id BIGINT,
    category_id BIGINT,
    CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES brand(id) ON DELETE CASCADE,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
    );
