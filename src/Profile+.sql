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
