INSERT INTO role (role_name, del_flag) VALUES ('管理员', 0);
INSERT INTO question (category, question_title, question_content, right_answer,created_by, del_flag) VALUES ('Java', 'xxx', 'yyy', 'zzz',1, 0);
INSERT INTO account (role_id, nick_name, login_account, password, del_flag) VALUES (1, 'Administer', 'admin', 'admin', 0);