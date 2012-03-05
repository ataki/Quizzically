BEGIN;
CREATE TABLE `Quiz_quiz` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
	`name` varchar(100) NOT NULL,
	`description` longtext NOT NULL,
    `author` varchar(100) NOT NULL,
    `timestamp` datetime NOT NULL,
    `category` longtext NOT NULL,
    `randomness` bool NOT NULL,
    `rating` integer NOT NULL
)
;

CTRATE TABLE `Quiz_tags` (
)
;


CREATE TABLE `Quiz_question` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `questionType` varchar(100) NOT NULL,
    `question` varchar(100) NOT NULL,
    `answers` longtext NOT NULL,
    `quiz_id` integer NOT NULL,
    `url` longtext NOT NULL
)
;
ALTER TABLE `Quiz_question` ADD CONSTRAINT `quiz_id_refs_id_a6c8dcfc` FOREIGN KEY (`quiz_id`) REFERENCES `Quiz_quiz` (`id`);
CREATE TABLE `Quiz_category` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `name` varchar(100) NOT NULL
)
;
CREATE TABLE `Quiz_user` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `name` varchar(100) NOT NULL,
    `password` longtext NOT NULL,
    `salt` integer NOT NULL,
    `access` bool NOT NULL,
    `achievements` longtext NOT NULL
)
;
CREATE TABLE `Quiz_friendship` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `user1_id` integer NOT NULL,
    `user2_id` integer NOT NULL,
    `friendType` integer NOT NULL
)
;
ALTER TABLE `Quiz_friendship` ADD CONSTRAINT `user1_id_refs_id_853509fe` FOREIGN KEY (`user1_id`) REFERENCES `Quiz_user` (`id`);
ALTER TABLE `Quiz_friendship` ADD CONSTRAINT `user2_id_refs_id_853509fe` FOREIGN KEY (`user2_id`) REFERENCES `Quiz_user` (`id`);
CREATE TABLE `Quiz_history` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `userID_id` integer NOT NULL,
    `quizID_id` integer NOT NULL,
    `score` numeric(3, 2) NOT NULL,
    `timestamp` datetime NOT NULL,
    `timeTaken` double precision NOT NULL
)
;
ALTER TABLE `Quiz_history` ADD CONSTRAINT `quizID_id_refs_id_1a7bb21b` FOREIGN KEY (`quizID_id`) REFERENCES `Quiz_quiz` (`id`);
ALTER TABLE `Quiz_history` ADD CONSTRAINT `userID_id_refs_id_1d6f93ad` FOREIGN KEY (`userID_id`) REFERENCES `Quiz_user` (`id`);
CREATE TABLE `Quiz_message` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `fromUser_id` integer NOT NULL,
    `toUser_id` integer NOT NULL,
    `message` longtext NOT NULL,
    `read` bool NOT NULL,
    `messageType` varchar(100) NOT NULL,
    `timestamp` datetime NOT NULL
)
;
ALTER TABLE `Quiz_message` ADD CONSTRAINT `fromUser_id_refs_id_50127436` FOREIGN KEY (`fromUser_id`) REFERENCES `Quiz_user` (`id`);
ALTER TABLE `Quiz_message` ADD CONSTRAINT `toUser_id_refs_id_50127436` FOREIGN KEY (`toUser_id`) REFERENCES `Quiz_user` (`id`);
CREATE TABLE `Quiz_announcement` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `text` longtext NOT NULL,
	'importance' integer NOT NULL,
    `timestamp` datetime NOT NULL
)
;
COMMIT;
