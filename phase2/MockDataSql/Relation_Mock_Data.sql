-- CommentOn
INSERT INTO CommentOn VALUES(1, 1);
INSERT INTO CommentOn VALUES(2, 1);
INSERT INTO CommentOn VALUES(3, 1);
INSERT INTO CommentOn VALUES(4, 1);
INSERT INTO CommentOn VALUES(5, 1);
INSERT INTO CommentOn VALUES(6, 1);
INSERT INTO CommentOn VALUES(7, 1);
INSERT INTO CommentOn VALUES(8, 1);
INSERT INTO CommentOn VALUES(9, 1);
INSERT INTO CommentOn VALUES(10, 1);
INSERT INTO CommentOn VALUES(11, 1);
INSERT INTO CommentOn VALUES(12, 1);
INSERT INTO CommentOn VALUES(13, 1);
INSERT INTO CommentOn VALUES(14, 1);
INSERT INTO CommentOn VALUES(15, 1);
INSERT INTO CommentOn VALUES(16, 1);
INSERT INTO CommentOn VALUES(17, 1);
INSERT INTO CommentOn VALUES(18, 1);
INSERT INTO CommentOn VALUES(19, 1);
INSERT INTO CommentOn VALUES(20, 1);
INSERT INTO CommentOn VALUES(21, 1);
INSERT INTO CommentOn VALUES(22, 1);
INSERT INTO CommentOn VALUES(23, 1);
INSERT INTO CommentOn VALUES(24, 1);
INSERT INTO CommentOn VALUES(25, 1);

-- CreatesGroup
INSERT INTO CreatesGroup VALUES(1, 26);
INSERT INTO CreatesGroup VALUES(2, 27);
INSERT INTO CreatesGroup VALUES(3, 28);
INSERT INTO CreatesGroup VALUES(4, 29);
INSERT INTO CreatesGroup VALUES(5, 30);
INSERT INTO CreatesGroup VALUES(11, 26);
INSERT INTO CreatesGroup VALUES(12, 27);
INSERT INTO CreatesGroup VALUES(14, 28);
INSERT INTO CreatesGroup VALUES(14, 29);
INSERT INTO CreatesGroup VALUES(15, 30);
INSERT INTO CreatesGroup VALUES(6, 31);
INSERT INTO CreatesGroup VALUES(7, 32);
INSERT INTO CreatesGroup VALUES(8, 33);
INSERT INTO CreatesGroup VALUES(9, 34);
INSERT INTO CreatesGroup VALUES(10, 35);

-- HasAccessToGroup
INSERT INTO HasAccessToGroup(UserID, AdderID, PageID, GroupID) VALUES(1, NULL,26, 26);
INSERT INTO HasAccessToGroup(UserID, AdderID, PageID, GroupID) VALUES(2, NULL,27, 27);
INSERT INTO HasAccessToGroup(UserID, AdderID, PageID, GroupID) VALUES(3, NULL,28, 28);
INSERT INTO HasAccessToGroup(UserID, AdderID, PageID, GroupID) VALUES(4, NULL,29, 29);
INSERT INTO HasAccessToGroup(UserID, AdderID, PageID, GroupID) VALUES(5, NULL,30, 30);
INSERT INTO HasAccessToGroup(UserID, AdderID, PageID, GroupID) VALUES(11, 1,26, 26);
INSERT INTO HasAccessToGroup(UserID, AdderID, PageID, GroupID) VALUES(12, 2,27, 27);
INSERT INTO HasAccessToGroup(UserID, AdderID, PageID, GroupID) VALUES(14, 3,28, 28);
INSERT INTO HasAccessToGroup(UserID, AdderID, PageID, GroupID) VALUES(14, 4,29, 29);
INSERT INTO HasAccessToGroup(UserID, AdderID, PageID, GroupID) VALUES(15, 5,30, 30);
INSERT INTO HasAccessToGroup(UserID, AdderID, PageID, GroupID) VALUES(6, NULL,31, 31);
INSERT INTO HasAccessToGroup(UserID, AdderID, PageID, GroupID) VALUES(7, NULL,32, 32);
INSERT INTO HasAccessToGroup(UserID, AdderID, PageID, GroupID) VALUES(8, NULL,33, 33);
INSERT INTO HasAccessToGroup(UserID, AdderID, PageID, GroupID) VALUES(9, NULL,34, 34);
INSERT INTO HasAccessToGroup(UserID, AdderID, PageID, GroupID) VALUES(10, NULL,35, 35);

-- HasAGroupPage
INSERT INTO HasAGroupPage VALUES(26, 26);
INSERT INTO HasAGroupPage VALUES(27, 27);
INSERT INTO HasAGroupPage VALUES(28, 28);
INSERT INTO HasAGroupPage VALUES(29, 29);
INSERT INTO HasAGroupPage VALUES(30, 30);
INSERT INTO HasAGroupPage VALUES(31, 31);
INSERT INTO HasAGroupPage VALUES(32, 32);
INSERT INTO HasAGroupPage VALUES(33, 33);
INSERT INTO HasAGroupPage VALUES(34, 34);
INSERT INTO HasAGroupPage VALUES(35, 35);

-- LikesComments
INSERT INTO LikesComment VALUES(1, 1);
INSERT INTO LikesComment VALUES(1, 2);
INSERT INTO LikesComment VALUES(1, 3);
INSERT INTO LikesComment VALUES(1, 4);
INSERT INTO LikesComment VALUES(1, 5);
INSERT INTO LikesComment VALUES(1, 6);
INSERT INTO LikesComment VALUES(1, 7);
INSERT INTO LikesComment VALUES(1, 8);
INSERT INTO LikesComment VALUES(1, 9);
INSERT INTO LikesComment VALUES(1, 10);
INSERT INTO LikesComment VALUES(1, 11);
INSERT INTO LikesComment VALUES(1, 12);
INSERT INTO LikesComment VALUES(1, 13);
INSERT INTO LikesComment VALUES(1, 14);
INSERT INTO LikesComment VALUES(1, 15);
INSERT INTO LikesComment VALUES(1, 16);
INSERT INTO LikesComment VALUES(1, 17);
INSERT INTO LikesComment VALUES(1, 18);
INSERT INTO LikesComment VALUES(1, 19);
INSERT INTO LikesComment VALUES(1, 20);
INSERT INTO LikesComment VALUES(1, 21);
INSERT INTO LikesComment VALUES(1, 22);
INSERT INTO LikesComment VALUES(1, 23);
INSERT INTO LikesComment VALUES(1, 24);
INSERT INTO LikesComment VALUES(1, 25);

-- LikesPost
INSERT INTO LikesPost VALUES(1, 1);
INSERT INTO LikesPost VALUES(1, 2);
INSERT INTO LikesPost VALUES(1, 3);
INSERT INTO LikesPost VALUES(1, 4);
INSERT INTO LikesPost VALUES(1, 5);
INSERT INTO LikesPost VALUES(1, 6);
INSERT INTO LikesPost VALUES(1, 7);
INSERT INTO LikesPost VALUES(1, 8);
INSERT INTO LikesPost VALUES(1, 9);
INSERT INTO LikesPost VALUES(1, 10);
INSERT INTO LikesPost VALUES(1, 11);
INSERT INTO LikesPost VALUES(1, 12);
INSERT INTO LikesPost VALUES(1, 13);
INSERT INTO LikesPost VALUES(1, 14);
INSERT INTO LikesPost VALUES(1, 15);
INSERT INTO LikesPost VALUES(1, 16);
INSERT INTO LikesPost VALUES(1, 17);
INSERT INTO LikesPost VALUES(1, 18);
INSERT INTO LikesPost VALUES(1, 19);
INSERT INTO LikesPost VALUES(1, 20);
INSERT INTO LikesPost VALUES(1, 21);
INSERT INTO LikesPost VALUES(1, 22);
INSERT INTO LikesPost VALUES(1, 23);
INSERT INTO LikesPost VALUES(1, 24);
INSERT INTO LikesPost VALUES(1, 25);

-- FriendsWith
INSERT INTO FriendsWith VALUES(1, 2);
INSERT INTO FriendsWith VALUES(2, 1);
INSERT INTO FriendsWith VALUES(1, 3);
INSERT INTO FriendsWith VALUES(3, 1);
INSERT INTO FriendsWith VALUES(4, 6);
INSERT INTO FriendsWith VALUES(6, 4);
INSERT INTO FriendsWith VALUES(10, 25);
INSERT INTO FriendsWith VALUES(25, 10);
INSERT INTO FriendsWith VALUES(1, 25);
INSERT INTO FriendsWith VALUES(25, 1);
INSERT INTO FriendsWith VALUES(1, 20);
INSERT INTO FriendsWith VALUES(20, 1);

-- PostedTo
INSERT INTO PostedTo VALUES(1, 1);
INSERT INTO PostedTo VALUES(2, 1);
INSERT INTO PostedTo VALUES(3, 1);
INSERT INTO PostedTo VALUES(4, 1);
INSERT INTO PostedTo VALUES(5, 1);
INSERT INTO PostedTo VALUES(6, 1);
INSERT INTO PostedTo VALUES(7, 1);
INSERT INTO PostedTo VALUES(8, 1);
INSERT INTO PostedTo VALUES(9, 1);
INSERT INTO PostedTo VALUES(10, 1);
INSERT INTO PostedTo VALUES(11, 1);
INSERT INTO PostedTo VALUES(12, 1);
INSERT INTO PostedTo VALUES(13, 1);
INSERT INTO PostedTo VALUES(14, 1);
INSERT INTO PostedTo VALUES(15, 1);
INSERT INTO PostedTo VALUES(16, 1);
INSERT INTO PostedTo VALUES(17, 1);
INSERT INTO PostedTo VALUES(18, 1);
INSERT INTO PostedTo VALUES(19, 1);
INSERT INTO PostedTo VALUES(20, 1);

-- HasAPersonal
INSERT INTO HasAPersonal VALUES(1, 1);
INSERT INTO HasAPersonal VALUES(2, 2);
INSERT INTO HasAPersonal VALUES(3, 3);
INSERT INTO HasAPersonal VALUES(4, 4);
INSERT INTO HasAPersonal VALUES(5, 5);
INSERT INTO HasAPersonal VALUES(6, 6);
INSERT INTO HasAPersonal VALUES(7, 7);
INSERT INTO HasAPersonal VALUES(8, 8);
INSERT INTO HasAPersonal VALUES(9, 9);
INSERT INTO HasAPersonal VALUES(10, 10);
INSERT INTO HasAPersonal VALUES(11, 11);
INSERT INTO HasAPersonal VALUES(12, 12);
INSERT INTO HasAPersonal VALUES(13, 13);
INSERT INTO HasAPersonal VALUES(14, 14);
INSERT INTO HasAPersonal VALUES(15, 15);
INSERT INTO HasAPersonal VALUES(16, 16);
INSERT INTO HasAPersonal VALUES(17, 17);
INSERT INTO HasAPersonal VALUES(18, 18);
INSERT INTO HasAPersonal VALUES(19, 19);
INSERT INTO HasAPersonal VALUES(20, 20);
INSERT INTO HasAPersonal VALUES(21, 21);
INSERT INTO HasAPersonal VALUES(22, 22);
INSERT INTO HasAPersonal VALUES(23, 23);
INSERT INTO HasAPersonal VALUES(24, 24);
INSERT INTO HasAPersonal VALUES(25, 25);

-- Buy
INSERT INTO Buy VALUE(1, 153581835, 1);
INSERT INTO Buy VALUE(2, 168375077, 2);
INSERT INTO Buy VALUE(3, 168375077, 3);
INSERT INTO Buy VALUE(4, 264939771, 4);
INSERT INTO Buy VALUE(5, 153581835, 5);
INSERT INTO Buy VALUE(6, 264939771, 6);
INSERT INTO Buy VALUE(7, 153581835, 7);
INSERT INTO Buy VALUE(8, 269768789, 8);
INSERT INTO Buy VALUE(9, 269768789, 9);
INSERT INTO Buy VALUE(10, 269768789, 10);
INSERT INTO Buy VALUE(11, 269768789, 100001);
INSERT INTO Buy VALUE(12, 269768789, 100002);
INSERT INTO Buy VALUE(13, 269768789, 100002);
INSERT INTO Buy VALUE(14, 269768789, 100003);
INSERT INTO Buy VALUE(15, 269768789, 100003);
INSERT INTO Buy VALUE(16, 269768789, 100004);
INSERT INTO Buy VALUE(17, 269768789, 100004);
INSERT INTO Buy VALUE(18, 269768789, 100005);
INSERT INTO Buy VALUE(19, 269768789, 100001);
INSERT INTO Buy VALUE(20, 269768789, 100001);

-- IsIn
INSERT INTO IsIn VALUE(1, 26);
INSERT INTO IsIn VALUE(2, 26);
INSERT INTO IsIn VALUE(3, 26);
INSERT INTO IsIn VALUE(4, 26);
INSERT INTO IsIn VALUE(5, 26);
INSERT INTO IsIn VALUE(6, 26);
INSERT INTO IsIn VALUE(2, 27);
INSERT INTO IsIn VALUE(3, 28);
INSERT INTO IsIn VALUE(4, 29);
INSERT INTO IsIn VALUE(5, 30);
INSERT INTO IsIn VALUE(6, 31);
INSERT INTO IsIn VALUE(7, 32);
INSERT INTO IsIn VALUE(8, 33);
INSERT INTO IsIn VALUE(9, 34);
INSERT INTO IsIn VALUE(10, 35);

-- Send/Receive
INSERT INTO Send VALUE(1, 1);
INSERT INTO Receive VALUE(2, 1);
INSERT INTO Send VALUE(2, 2);
INSERT INTO Receive VALUE(3, 2);
INSERT INTO Send VALUE(3, 3);
INSERT INTO Receive VALUE(4, 3);
INSERT INTO Send VALUE(4, 4);
INSERT INTO Receive VALUE(5, 4);
INSERT INTO Send VALUE(5, 5);
INSERT INTO Receive VALUE(6, 5);
INSERT INTO Send VALUE(6, 6);
INSERT INTO Receive VALUE(7, 6);
INSERT INTO Send VALUE(7, 7);
INSERT INTO Receive VALUE(8, 7);
INSERT INTO Send VALUE(8, 8);
INSERT INTO Receive VALUE(9, 8);
INSERT INTO Send VALUE(9, 9);
INSERT INTO Receive VALUE(10, 9);
INSERT INTO Send VALUE(10, 10);
INSERT INTO Receive VALUE(11, 10);
INSERT INTO Send VALUE(11, 11);
INSERT INTO Receive VALUE(12, 11);
INSERT INTO Send VALUE(12, 12);
INSERT INTO Receive VALUE(13, 12);
INSERT INTO Send VALUE(13, 13);
INSERT INTO Receive VALUE(14, 13);
INSERT INTO Send VALUE(14, 14);
INSERT INTO Receive VALUE(15, 14);
INSERT INTO Send VALUE(15, 15);
INSERT INTO Receive VALUE(16, 15);
INSERT INTO Send VALUE(16, 16);
INSERT INTO Receive VALUE(17, 16);
INSERT INTO Send VALUE(17, 17);
INSERT INTO Receive VALUE(18, 17);
INSERT INTO Send VALUE(18, 18);
INSERT INTO Receive VALUE(19, 18);
INSERT INTO Send VALUE(19, 19);
INSERT INTO Receive VALUE(20, 19);
INSERT INTO Send VALUE(20, 20);
INSERT INTO Receive VALUE(21, 20);
INSERT INTO Send VALUE(21, 21);
INSERT INTO Receive VALUE(22, 21);
INSERT INTO Send VALUE(22, 22);
INSERT INTO Receive VALUE(23, 22);
INSERT INTO Send VALUE(23, 23);
INSERT INTO Receive VALUE(24, 23);
INSERT INTO Send VALUE(24, 24);
INSERT INTO Receive VALUE(25, 24);