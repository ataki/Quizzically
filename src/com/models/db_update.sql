BEGIN;
CREATE TABLE `Quiz_user` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `username` varchar(130) NOT NULL,
    `email` varchar(130) NOT NULL,
    `password` longtext NOT NULL,
    `description` longtext NOT NULL,
    `salt` integer NOT NULL,
    `admin` bool NOT NULL
)
;
CREATE TABLE `Quiz_category` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `name` varchar(130) NOT NULL
)
;
CREATE TABLE `Quiz_quiz` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `creator_id` integer NOT NULL,
    `name` varchar(130) NOT NULL,
    `description` longtext NOT NULL,
    `single_page` bool NOT NULL,
    `immediate_feedback` bool NOT NULL,
    `random` bool NOT NULL,
    `points` integer NOT NULL,
    `rating` double precision NOT NULL,
    `numRated` integer NOT NULL,
    `timestamp` datetime NOT NULL,
    `category_id` integer NOT NULL
)
;
ALTER TABLE `Quiz_quiz` ADD CONSTRAINT `category_id_refs_id_b76e26ca` FOREIGN KEY (`category_id`) REFERENCES `Quiz_category` (`id`);
ALTER TABLE `Quiz_quiz` ADD CONSTRAINT `creator_id_refs_id_6c7f0713` FOREIGN KEY (`creator_id`) REFERENCES `Quiz_user` (`id`);
CREATE TABLE `Quiz_question` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `index` integer NOT NULL,
    `quiz_id` integer NOT NULL,
    `type` varchar(130) NOT NULL,
    `question` varchar(130) NOT NULL,
    `answers` longtext NOT NULL,
    `url` longtext NOT NULL
)
;
ALTER TABLE `Quiz_question` ADD CONSTRAINT `quiz_id_refs_id_a6c8dcfc` FOREIGN KEY (`quiz_id`) REFERENCES `Quiz_quiz` (`id`);
CREATE TABLE `Quiz_tag_quiz` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `tag_id` integer NOT NULL,
    `quiz_id` integer NOT NULL,
    UNIQUE (`tag_id`, `quiz_id`)
)
;
ALTER TABLE `Quiz_tag_quiz` ADD CONSTRAINT `quiz_id_refs_id_649e480c` FOREIGN KEY (`quiz_id`) REFERENCES `Quiz_quiz` (`id`);
CREATE TABLE `Quiz_tag` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `user_id` integer NOT NULL,
    `tag` varchar(130) NOT NULL
)
;
ALTER TABLE `Quiz_tag` ADD CONSTRAINT `user_id_refs_id_925740f` FOREIGN KEY (`user_id`) REFERENCES `Quiz_user` (`id`);
ALTER TABLE `Quiz_tag_quiz` ADD CONSTRAINT `tag_id_refs_id_cd34b952` FOREIGN KEY (`tag_id`) REFERENCES `Quiz_tag` (`id`);
CREATE TABLE `Quiz_friendship` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `user1_id` integer NOT NULL,
    `user2_id` integer NOT NULL,
    `friendType` integer NOT NULL
)
;
ALTER TABLE `Quiz_friendship` ADD CONSTRAINT `user1_id_refs_id_853509fe` FOREIGN KEY (`user1_id`) REFERENCES `Quiz_user` (`id`);
ALTER TABLE `Quiz_friendship` ADD CONSTRAINT `user2_id_refs_id_853509fe` FOREIGN KEY (`user2_id`) REFERENCES `Quiz_user` (`id`);
CREATE TABLE `Quiz_activity` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `user_id` integer NOT NULL,
    `quiz_id` integer NOT NULL,
    `score` numeric(3, 2) NOT NULL,
    `timestamp` datetime NOT NULL,
    `timeTaken` double precision NOT NULL
)
;
ALTER TABLE `Quiz_activity` ADD CONSTRAINT `user_id_refs_id_43610c11` FOREIGN KEY (`user_id`) REFERENCES `Quiz_user` (`id`);
ALTER TABLE `Quiz_activity` ADD CONSTRAINT `quiz_id_refs_id_1b8924d1` FOREIGN KEY (`quiz_id`) REFERENCES `Quiz_quiz` (`id`);
CREATE TABLE `Quiz_message` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `fromUser_id` integer NOT NULL,
    `toUser_id` integer NOT NULL,
    `message` longtext NOT NULL,
    `read` bool NOT NULL,
    `messageType` varchar(130) NOT NULL,
    `timestamp` datetime NOT NULL
)
;
ALTER TABLE `Quiz_message` ADD CONSTRAINT `fromUser_id_refs_id_50127436` FOREIGN KEY (`fromUser_id`) REFERENCES `Quiz_user` (`id`);
ALTER TABLE `Quiz_message` ADD CONSTRAINT `toUser_id_refs_id_50127436` FOREIGN KEY (`toUser_id`) REFERENCES `Quiz_user` (`id`);
CREATE TABLE `Quiz_announcement` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `user_id` integer NOT NULL,
    `text` longtext NOT NULL,
    `importance` integer NOT NULL,
    `timestamp` datetime NOT NULL
)
;
ALTER TABLE `Quiz_announcement` ADD CONSTRAINT `user_id_refs_id_e8ba085b` FOREIGN KEY (`user_id`) REFERENCES `Quiz_user` (`id`);
CREATE TABLE `Quiz_achievement` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `user_id` integer NOT NULL,
    `award` longtext NOT NULL,
    `description` longtext NOT NULL,
    `url` longtext NOT NULL,
    `timestamp` datetime NOT NULL
)
;
ALTER TABLE `Quiz_achievement` ADD CONSTRAINT `user_id_refs_id_dcba7312` FOREIGN KEY (`user_id`) REFERENCES `Quiz_user` (`id`);
CREATE TABLE `Quiz_friendrecommendation` (
    `id` integer AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `user1_id` integer NOT NULL,
    `user2_id` integer NOT NULL,
    `friendType` integer NOT NULL
)
;
ALTER TABLE `Quiz_friendrecommendation` ADD CONSTRAINT `user1_id_refs_id_35760885` FOREIGN KEY (`user1_id`) REFERENCES `Quiz_user` (`id`);
ALTER TABLE `Quiz_friendrecommendation` ADD CONSTRAINT `user2_id_refs_id_35760885` FOREIGN KEY (`user2_id`) REFERENCES `Quiz_user` (`id`);
COMMIT;
