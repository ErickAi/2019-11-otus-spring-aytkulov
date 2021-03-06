INSERT INTO PUBLIC.SYSTEM_MESSAGE (ID, CONTENT) VALUES
(1, 'New admin message'),
(2, 'New user message first'),
(3, 'New user message second');

INSERT INTO PUBLIC.ACL_CLASS (ID, CLASS) VALUES (1, 'ru.otus.acl.domain.NoticeMessage');

INSERT INTO PUBLIC.ACL_SID (ID, PRINCIPAL, SID) VALUES
(1, 1, 'admin'),
(2, 0, 'ROLE_GUEST'),
(3, 0, 'ROLE_USER'),
(4, 0, 'ROLE_EDITOR'),
(5, 1, 'user');

INSERT INTO PUBLIC.ACL_OBJECT_IDENTITY
(ID, OBJECT_ID_CLASS, OBJECT_ID_IDENTITY, PARENT_OBJECT, OWNER_SID, ENTRIES_INHERITING) VALUES
(1, 1, 1, null, 1, 1),
(2, 1, 2, null, 5, 1),
(3, 1, 3, null, 5, 1);

INSERT INTO PUBLIC.ACL_ENTRY (ID, ACL_OBJECT_IDENTITY, ACE_ORDER, SID, MASK, GRANTING, AUDIT_SUCCESS, AUDIT_FAILURE) VALUES
(1, 1, 0, 2, 1, 1, 0, 0),
(2, 1, 1, 3, 1, 1, 0, 0),
(3, 1, 2, 3, 2, 1, 0, 0),
(4, 1, 3, 1, 16, 1, 0, 0),
(5, 1, 4, 4, 16, 1, 0, 0),
(6, 2, 0, 2, 1, 1, 0, 0),
(7, 2, 1, 3, 1, 1, 0, 0),
(8, 2, 2, 3, 2, 1, 0, 0),
(9, 2, 3, 5, 16, 1, 0, 0),
(10, 2, 4, 4, 16, 1, 0, 0),
(11, 3, 0, 2, 1, 1, 0, 0),
(12, 3, 1, 3, 1, 1, 0, 0),
(13, 3, 2, 3, 2, 1, 0, 0),
(14, 3, 3, 5, 16, 1, 0, 0),
(15, 3, 4, 4, 16, 1, 0, 0);
