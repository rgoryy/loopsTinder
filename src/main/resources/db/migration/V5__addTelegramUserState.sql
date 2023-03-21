CREATE TABLE Telegram_user_state
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    user_state VARCHAR(30),
    FOREIGN KEY (user_id) REFERENCES Usr (id)
)