#User database
CREATE TABLE UserPlus(
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

#Comment Database
CREATE TABLE Comment(
  CommentID INTEGER,
  DateCreated DATETIME,
  Content   VARCHAR(100),
  AuthorID  INTEGER,
  PRIMARY KEY (CommentID),
  FOREIGN KEY (AuthorID) REFERENCES UserPlus(UserID)
);

#Post database
CREATE TABLE Post(
  PostID INTEGER,
  DateCreated DATETIME,
  Content     VARCHAR(100),
  CommentCount INTEGER,
  AuthorID      INTEGER,
  PRIMARY KEY (PostID),
  FOREIGN KEY (AuthorID) REFERENCES UserPlus(UserID)
);

#Message database
CREATE TABLE Message(
  MessageID INTEGER,
  DateSent DATETIME,
  ReceiverID INTEGER,
  SenderID INTEGER,
  Subject VARCHAR(100),
  Content VARCHAR(100),
  PRIMARY KEY (MessageID),
  FOREIGN KEY (ReceiverID) REFERENCES UserPlus(UserID),
  FOREIGN KEY (SenderID) REFERENCES UserPlus(UserID)
);

#Page database
CREATE TABLE PagePlus(
  PageID INTEGER,
  PostCount INTEGER,
  PRIMARY KEY (PageID)
);

#Personal page database
CREATE TABLE PersonalPage(
  PageID INTEGER,
  OwnerID INTEGER,
  PRIMARY KEY (PageID),
  FOREIGN KEY (OwnerID) REFERENCES UserPlus(UserID)
);

#Group Page Database
CREATE TABLE GroupPage(
  PageID INTEGER,
  AccociatedID INTEGER,
  PRIMARY KEY (PageID),
  FOREIGN KEY (AccociatedID) REFERENCES UserPlus(UserID)
);

#Group database
CREATE TABLE GroupPlus(
  GroupID INTEGER,
  GroupName VARCHAR(50),
  Owner INTEGER,
  Type SET('Club', 'Organization', 'Event', 'News'),
  PRIMARY KEY (GroupID),
  FOREIGN KEY (GroupID) REFERENCES UserPlus(UserID)
);
-- Jerrel Part
CREATE TABLE FriendsWith(
  UserID INTEGER,
  FriendID INTEGER,
  PRIMARY KEY (UserID, FriendID),
  FOREIGN KEY (UserID) REFERENCES UserPlus(UserID)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,
  FOREIGN KEY (FriendID) REFERENCES UserPlus(UserID)
    ON UPDATE CASCADE
    ON DELETE NO ACTION




);
# Assertions do not wokr try to replace with triggers
# CREATE ASSERTION NoFriendsWithSelf
# CHECK NOT EXISTS(
# Select U.UserID, F.FriendID FROM UserPlus U, FriendsWith F
# WHERE U.UserID == F.FriendID
# )
#

#Creates group relationship
CREATE TABLE CreatesGroup(
  UserID INTEGER,
  GroupID INTEGER,
  PRIMARY KEY (UserID, GroupID),
  FOREIGN KEY (UserID) REFERENCES UserPlus(UserID)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,
  FOREIGN KEY (GroupID) REFERENCES GroupPlus(GroupID)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);





#Has access between page and group and user relationship
CREATE TABLE HasAccess (
  UserID INTEGER,
  PageID INTEGER,
  GroupID INTEGER,
  PRIMARY KEY (UserID, PageID, GroupID),
  FOREIGN KEY (UserID) REFERENCES UserPlus(UserID)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,
  FOREIGN KEY (PageID) REFERENCES PagePlus(PageID)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,
  FOREIGN KEY (GroupID) REFERENCES GroupPlus(GroupID)
    ON UPDATE CASCADE
    ON DELETE NO ACTION

);

#Assertions do not work, replace with trigger
# CREATE ASSERTION UserCanSeeOnlyGroup
# CHECK NOT EXISTS(
# SELECT * FROM User M
# WHERE NOT EXISTS(
# SELECT * FROM HasAccess H
# WHERE M.UserID == H.UserID
# )
# )

#Personal Page Database check
CREATE TABLE HasAPersonal(
  UserID INTEGER,
  PersonalPageID INTEGER,
  PRIMARY KEY (UserID, PersonalPageID),
  FOREIGN KEY (UserID) REFERENCES UserPlus(UserID)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,
  FOREIGN KEY (PersonalPageID) REFERENCES PersonalPage(PageID)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);

#Has group page check
CREATE TABLE HasAGroupPage(
  GroupID INTEGER,
  GroupPageID INTEGER,
  PRIMARY KEY (GroupID, GroupPageID),
  FOREIGN KEY (GroupID) REFERENCES GroupPlus(GroupID)
    ON UPDATE CASCADE
    ON DELETE NO ACTION,
  FOREIGN KEY (GroupPageID) REFERENCES GroupPage(PageID)
    ON UPDATE CASCADE
    ON DELETE NO ACTION
);

-- Jane Part
CREATE TABLE MakesComment(
  CommentID INTEGER,
  UserID INTEGER,
  PRIMARY KEY (CommentID, UserID),
  FOREIGN KEY (CommentID) REFERENCES Comment(CommentID)
    ON DELETE NO ACTION ON UPDATE CASCADE,
  FOREIGN KEY (UserID) REFERENCES UserPlus(UserID)
    ON DELETE NO ACTION ON UPDATE CASCADE
);


CREATE TABLE LikesComment(
  CommentID INTEGER,
  UserID INTEGER,
  PRIMARY KEY (CommentID, UserID),
  FOREIGN KEY (CommentID) REFERENCES Comment(CommentID)
    ON DELETE NO ACTION ON UPDATE CASCADE,
  FOREIGN KEY (UserID) REFERENCES UserPlus(UserID)
    ON DELETE NO ACTION ON UPDATE CASCADE
);


CREATE TABLE MakesPost(
  PostID INTEGER,
  UserID INTEGER,
  PRIMARY KEY (PostID, UserID),
  FOREIGN KEY (PostID) REFERENCES Post(PostID)
    ON DELETE NO ACTION ON UPDATE CASCADE,
  FOREIGN KEY (UserID) REFERENCES UserPlus(UserID)
    ON DELETE NO ACTION ON UPDATE CASCADE
);


CREATE TABLE LikesPost(
  PostID INTEGER,
  UserID INTEGER,
  PRIMARY KEY (PostID, UserID),
  FOREIGN KEY (PostID) REFERENCES Post(PostID)
    ON DELETE NO ACTION ON UPDATE CASCADE,
  FOREIGN KEY (UserID) REFERENCES UserPlus(UserID)
    ON DELETE NO ACTION ON UPDATE CASCADE
);


CREATE TABLE PostedTo(
  PostID INTEGER,
  PageID INTEGER,
  PRIMARY KEY (PageID, PostID),
  FOREIGN KEY (PageID) REFERENCES PagePlus(PageID)
    ON DELETE NO ACTION ON UPDATE CASCADE,
  FOREIGN KEY (PostID) REFERENCES Post(PostID)
    ON DELETE NO ACTION ON UPDATE CASCADE
);


CREATE TABLE CommentOn(
  CommentID INTEGER,
  PostID INTEGER,
  PRIMARY KEY (CommentID, PostID),
  FOREIGN KEY (CommentID) REFERENCES Comment(CommentID)
    ON DELETE NO ACTION ON UPDATE CASCADE,
  FOREIGN KEY (PostID) REFERENCES Post(PostID)
    ON DELETE NO ACTION ON UPDATE CASCADE
);


# CREATE ASSERTION comment_exists CHECK
# (EXISTS (SELECT * FROM CommentPlus c WHERE
# (SELECT * FROM LikesComment l WHERE c.CommentID = l.CommentID)));
#
#
# CREATE ASSERTION post_exists CHECK
# (EXISTS (SELECT * FROM Post p WHERE
# (SELECT * FROM LikesPost l WHERE p.PostID = l.PostID)));
#
#
# CREATE ASSERTION comment_is_posted CHECK
# ((SELECT COUNT * FROM MakesComment m WHERE
# (SELECT * FROM CommentOn o WHERE m.CommentID = o.CommentID)) = 1);
#
#
# CREATE ASSERTION post_is_posted CHECK
# ((SELECT COUNT * FROM PostedTo p WHERE
# (SELECT * FROM MakesPost m WHERE p.PostID = m.PostID)) = 1);

-- Daniel Part
CREATE TABLE Employee(
  SSN char(11),
  LastName char(30),
  FirstName char(10),
  Address char(50),
  City char(60),
  State char(20),
  ZipCode char(16),
  Telephone char(20),
  StartDate date,
  HourlyRate float,
  primary key (SSN)
);

CREATE TABLE AdData(
  AdId INTEGER,
  EmpId char(11) not null,
  Type char(20),
  Company char(50),
  ItemName char(50),
  Content VARCHAR(200),
  UnitPrice float,
  NumOfAvaUnits integer, -- number of available units
  primary key(AdId),
  foreign key(EmpId) references Employee(SSN)
    ON DELETE NO ACTION ON UPDATE CASCADE
);


CREATE TABLE Sales(
  TransId integer,
  TransDate date,
  TransTime time,
  AdId integer,
  NumOfUnits integer,
  AccountNum integer,
  primary key (TransId),
  foreign key (AdId) references AdData(AdId)
    ON DELETE NO ACTION ON UPDATE CASCADE
);


-- create table Posted not necessary, using foreign key relationship between AdData and Employee will be enough


-- create table Affected( same reason not necessary


-- When transaction buys something, we have to keep track of which transaction occured using which Ad. But this is handled with Sales.AdId
-- What is not in the Sales table is that it does not keep track of who bought it. In order to keep track of this, we add Buy table to do that.


CREATE TABLE Buy(
  TransId integer,
  EmpId char(11) not null,
  UserId integer,
  primary key(TransId, EmpId, UserId),
  foreign key(TransId) references Sales(TransId)
    ON DELETE NO ACTION ON UPDATE CASCADE,
  foreign key(EmpId) references Employee(SSN)
    ON DELETE NO ACTION ON UPDATE CASCADE,
  foreign key(UserId) references UserPlus(UserId)
    ON DELETE NO ACTION ON UPDATE CASCADE
);




CREATE TABLE IsIn(
  UserId integer,
  GroupId integer,
  primary key (UserId, GroupId),
  foreign key (UserId) references UserPlus(UserId)
    ON DELETE NO ACTION ON UPDATE CASCADE,
  foreign key (GroupId) references GroupPlus(GroupId)
    ON DELETE NO ACTION ON UPDATE CASCADE
);


# CREATE ASSERTION GroupHaveOwner
# CHECK NOT EXISTS(
# SELECT * FROM Group IsIn I
# WHERE (NOT EXISTS
# SELECT * FROM Group G
# WHERE G.GroupId = I.GroupId
# )
# );

CREATE TABLE Send(
  Sender integer,
  MessageId integer,
  primary key (Sender, MessageId),
  foreign key (Sender) references UserPlus(UserId)
    ON DELETE NO ACTION ON UPDATE CASCADE,
  foreign key (MessageId) references Message(MessageId)
    ON DELETE NO ACTION ON UPDATE CASCADE
);


CREATE TABLE Receive(
  Receiver integer,
  MessageId integer,
  primary key (Receiver, MessageId),
  foreign key (Receiver) references UserPlus(UserId)
    ON DELETE NO ACTION ON UPDATE CASCADE,
  foreign key (MessageId) references Message(MessageId)
    ON DELETE NO ACTION ON UPDATE CASCADE
);

#
# CREATE ASSERTION AccessControl
# CHECK NOT EXISTS(
# SELECT * FROM MakesPost MP,
# WHERE NOT EXISTS (
# SELECT * FROM HasAccess AC,
# WHERE AC.PageId = MP.PageId and AC.UserId = MP.UserId)
# );

CREATE TABLE FriendsWith(
  UserID INTEGER,
  FriendID INTEGER,
  PRIMARY KEY (UserID, FriendID),
  FOREIGN KEY (UserID) REFERENCES UserPlus(UserID),
  FOREIGN KEY (FriendID) REFERENCES UserPlus(UserID)
);

#
# CREATE ASSERTION NoFriendsWithSelf
# CHECK NOT EXISTS(
# Select U.UserID, F.FriendID FROM UserPlus U, FriendsWith F
# WHERE U.UserID == F.FriendID
# )


CREATE TABLE CreatesGroup(
  UserID INTEGER,
  GroupID INTEGER,
  PRIMARY KEY (UserID, GroupID),
  FOREIGN KEY (UserID) REFERENCES UserPlus(UserID),
  FOREIGN KEY (GroupID) REFERENCES GroupPlus(GroupID)
);


CREATE TABLE HasAccess (
  UserID INTEGER,
  PageID INTEGER,
  GroupID INTEGER,
  PRIMARY KEY (UserID, PageID, GroupID),
  FOREIGN KEY (UserID) REFERENCES UserPlus(UserID),
  FOREIGN KEY (PageID) REFERENCES PagePlus(PageID),
  FOREIGN KEY (GroupID) REFERENCES GroupPlus(GroupID)
);


CREATE TABLE HasAPersonal(
  UserID INTEGER,
  PersonalPageID INTEGER,
  PRIMARY KEY (UserID, PersonalPageID),
  FOREIGN KEY (UserID) REFERENCES UserPlus(UserID),
  FOREIGN KEY (PersonalPageID) REFERENCES PersonalPage(PageID)
);


CREATE TABLE HasAGroupPage(
  GroupID INTEGER,
  GroupPageID INTEGER,
  PRIMARY KEY (GroupID, GroupPageID),
  FOREIGN KEY (GroupID) REFERENCES GroupPlus(GroupID),
  FOREIGN KEY (GroupPageID) REFERENCES GroupPage(PageID)
);






