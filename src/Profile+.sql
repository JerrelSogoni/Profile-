CREATE TABLE Comment(
  CommentID INTEGER,
  DateCreated DATETIME,
  Content   VARCHAR(100),
  AuthorID  INTEGER,
  PRIMARY KEY (CommentID),
  FOREIGN KEY (AuthorID) REFERENCES User(UserID)

);
CREATE TABLE Post(
  PostID INTEGER,
  DateCreated DATETIME,
  Content     VARCHAR(100),
  CommentCount INTEGER,
  AuthorID      INTEGER,
  PRIMARY KEY (PostID),
  FOREIGN KEY (AuthorID) REFERENCES User(UserID)

);

CREATE TABLE Messenge(
  MessageID INTEGER,
  DateSent DATETIME,
  ReceiverID INTEGER,
  SenderID INTEGER,
  Subject VARCHAR(100),
  Content VARCHAR(100),
  PRIMARY KEY (MessageID),
  FOREIGN KEY (ReceiverID) REFERENCES User(UserID),
  FOREIGN KEY (SenderID) REFERENCES User(UserID)




);

CREATE TABLE Page(
  PageID INTEGER,
  PostCount INTEGER,
  PRIMARY KEY (PageID)
);
CREATE TABLE PersonalPage(
  PageID INTEGER,
  OwnerID INTEGER,
  PRIMARY KEY (PageID),
  FOREIGN KEY (OwnerID) REFERENCES User(UserID)
);
CREATE TABLE GroupPage(
  PageID INTEGER,
  AccociatedID INTEGER,
  PRIMARY KEY (PageID),
  FOREIGN KEY (AccociatedID) REFERENCES User(UserID)
);



CREATE TABLE User(
  UserID INTEGER,
  FirstName VARCHAR(20),
  LastName VARCHAR(20),
  Address VARCHAR(20),
  City VARCHAR(20),
  State VARCHAR(2),
  ZipCode INTEGER,
  Phone INTEGER,
  Email VARCHAR(20),
  AccountNum INTEGER,
  AccountCreationDate DATE,
  CreditCardNum INTEGER,
  Preferences VARCHAR(50),
  PRIMARY KEY (UserID)
);


CREATE TABLE GroupPlus(
  GroupID INTEGER,
  GroupName VARCHAR(50),
  Owner INTEGER,
  Type SET('Club', 'Organization', 'Event', 'News'),
  PRIMARY KEY (GroupID),
  FOREIGN KEY (GroupID) REFERENCES User(UserID)
);

CREATE TABLE FriendsWith(
  UserID INTEGER,
  FriendID INTEGER,
  PRIMARY KEY (UserID, FriendID),
  FOREIGN KEY (UserID) REFERENCES User(UserID),
  FOREIGN KEY (FriendID) REFERENCES User(UserID)


);

CREATE ASSERTION NoFriendsWithSelf
      CHECK NOT EXISTS(
      Select U.UserID, F.FriendID FROM UserPlus U, FriendsWith F
      WHERE U.UserID == F.FriendID
)


CREATE TABLE CreatesGroup(
  UserID INTEGER,
  GroupID INTEGER,
  PRIMARY KEY (UserID, GroupID),
  FOREIGN KEY (UserID) REFERENCES User(UserID),
  FOREIGN KEY (GroupID) REFERENCES GroupPlus(GroupID)

);


CREATE ASSERTION NoFriendsWithSelf
CHECK NOT EXISTS(
Select U.UserID, F.FriendID FROM UserPlus U, FriendsWith F
WHERE U.UserID == F.FriendID
)

CREATE TABLE HasAccess (
  UserID INTEGER,
  PageID INTEGER,
  GroupID INTEGER,
  PRIMARY KEY (UserID, PageID, GroupID),
  FOREIGN KEY (UserID) REFERENCES User(UserID),
  FOREIGN KEY (PageID) REFERENCES Page(PageID),
  FOREIGN KEY (GroupID) REFERENCES GroupPlus(GroupID)


);




CREATE TABLE HasAPersonal(
  UserID INTEGER,
  PersonalPageID INTEGER,
  PRIMARY KEY (UserID, PersonalPageID),
  FOREIGN KEY (UserID) REFERENCES User(UserID),
  FOREIGN KEY (PersonalPageID) REFERENCES PersonalPage(PageID)
);

CREATE TABLE HasAGroupPage(
  GroupID INTEGER,
  GroupPageID INTEGER,
  PRIMARY KEY (GroupID, GroupPageID),
  FOREIGN KEY (GroupID) REFERENCES GroupPlus(GroupID),
  FOREIGN KEY (GroupPageID) REFERENCES GroupPage(PageID)
)
