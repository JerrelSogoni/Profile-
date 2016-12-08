CREATE TABLE UserPlus (
    UserId INTEGER AUTO_INCREMENT,
    FirstName CHAR(20),
    LastName CHAR(20),
    Sex CHAR(2),
    Email CHAR(50),
    Password CHAR(20),
    DOB DATE,
    Address CHAR(50),
    City CHAR(60),
    State CHAR(20),
    ZipCode INTEGER,
    Phone CHAR(10),
    Preferences VARCHAR(1000),
    AccountNum INTEGER,
    PRIMARY KEY (UserId)
);

CREATE TABLE Comment (
    CommentId INTEGER AUTO_INCREMENT,
    DateCreated DATETIME,
    Content VARCHAR(100),
    AuthorId INTEGER,
    PRIMARY KEY (CommentId),
    FOREIGN KEY (AuthorId)
        REFERENCES UserPlus (UserId)
);

CREATE TABLE Post (
    PostId INTEGER AUTO_INCREMENT,
    DateCreated DATETIME,
    Content VARCHAR(100),
    CommentCount INTEGER,
    AuthorId INTEGER,
    PRIMARY KEY (PostId),
    FOREIGN KEY (AuthorId)
        REFERENCES UserPlus (UserId)
);

CREATE TABLE Message (
    MessageId INTEGER AUTO_INCREMENT,
    DateSent DATETIME,
    ReceiverId INTEGER,
    SenderId INTEGER,
    Subject VARCHAR(100),
    Content VARCHAR(100),
    PRIMARY KEY (MessageId),
    FOREIGN KEY (ReceiverId)
        REFERENCES UserPlus (UserId),
    FOREIGN KEY (SenderId)
        REFERENCES UserPlus (UserId)
);

CREATE TABLE PagePlus (
    PageId INTEGER AUTO_INCREMENT,
    PostCount INTEGER,
    PRIMARY KEY (PageId)
);

CREATE TABLE PersonalPage (
    PageId INTEGER AUTO_INCREMENT,
    OwnerId INTEGER,
    PRIMARY KEY (PageId),
    FOREIGN KEY (OwnerId)
        REFERENCES UserPlus (UserId)
);

CREATE TABLE GroupPlus (
    GroupId INTEGER AUTO_INCREMENT,
    GroupName VARCHAR(50),
    Owner INTEGER,
    Type SET('Club', 'Organization', 'Event', 'News', 'Friends'),
    PRIMARY KEY (GroupId),
    FOREIGN KEY (Owner)
        REFERENCES UserPlus (UserId)
);

CREATE TABLE GroupPage (
    PageId INTEGER AUTO_INCREMENT,
    GroupId INTEGER,
    PRIMARY KEY (PageId),
    FOREIGN KEY (GroupId)
        REFERENCES GroupPlus (GroupId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- Jerrel Part
CREATE TABLE FriendsWith (
    UserId INTEGER,
    FriendId INTEGER,
    PRIMARY KEY (UserId , FriendId),
    FOREIGN KEY (UserId)
        REFERENCES UserPlus (UserId)
        ON UPDATE CASCADE ON DELETE NO ACTION,
    FOREIGN KEY (FriendId)
        REFERENCES UserPlus (UserId)
        ON UPDATE CASCADE ON DELETE NO ACTION
);

CREATE TABLE CreatesGroup (
    UserId INTEGER,
    GroupId INTEGER,
    PRIMARY KEY (UserId , GroupId),
    FOREIGN KEY (UserId)
        REFERENCES UserPlus (UserId)
        ON UPDATE CASCADE ON DELETE NO ACTION,
    FOREIGN KEY (GroupId)
        REFERENCES GroupPlus (GroupId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE HasAccessToGroup (
    UserId INTEGER,
    AdderId INTEGER,
    PageId INTEGER,
    GroupId INTEGER,
    PRIMARY KEY (UserId , PageId , GroupId),
    FOREIGN KEY (UserId)
        REFERENCES UserPlus (UserId)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (PageId)
        REFERENCES PagePlus (PageId)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (GroupId)
        REFERENCES GroupPlus (GroupId)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (AdderId)
        REFERENCES UserPlus (UserId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE HasAPersonal (
    UserId INTEGER,
    PersonalPageId INTEGER,
    PRIMARY KEY (UserId , PersonalPageId),
    FOREIGN KEY (UserId)
        REFERENCES UserPlus (UserId)
        ON UPDATE CASCADE ON DELETE NO ACTION,
    FOREIGN KEY (PersonalPageId)
        REFERENCES PersonalPage (PageId)
        ON UPDATE CASCADE ON DELETE NO ACTION
);

CREATE TABLE HasAGroupPage (
    GroupId INTEGER,
    GroupPageId INTEGER,
    PRIMARY KEY (GroupId , GroupPageId),
    FOREIGN KEY (GroupId)
        REFERENCES GroupPlus (GroupId)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (GroupPageId)
        REFERENCES GroupPage (PageId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- Jane Part
CREATE TABLE LikesComment (
    CommentId INTEGER,
    UserId INTEGER,
    PRIMARY KEY (CommentId , UserId),
    FOREIGN KEY (CommentId)
        REFERENCES Comment (CommentId)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (UserId)
        REFERENCES UserPlus (UserId)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE LikesPost (
    PostId INTEGER,
    UserId INTEGER,
    PRIMARY KEY (PostId , UserId),
    FOREIGN KEY (PostId)
        REFERENCES Post (PostId)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (UserId)
        REFERENCES UserPlus (UserId)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE PostedTo (
    PostId INTEGER,
    PageId INTEGER,
    PRIMARY KEY (PageId , PostId),
    FOREIGN KEY (PageId)
        REFERENCES PagePlus (PageId)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (PostId)
        REFERENCES Post (PostId)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE CommentOn (
    CommentId INTEGER,
    PostId INTEGER,
    PRIMARY KEY (CommentId , PostId),
    FOREIGN KEY (CommentId)
        REFERENCES Comment (CommentId)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (PostId)
        REFERENCES Post (PostId)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Employee (
    SSN CHAR(11),
    LastName CHAR(30),
    FirstName CHAR(10),
    Address CHAR(50),
    City CHAR(60),
    State CHAR(20),
    ZipCode CHAR(16),
    Telephone CHAR(20),
    StartDate DATE,
    HourlyRate FLOAT,
    PRIMARY KEY (SSN)
);

CREATE TABLE AdData (
    AdId INTEGER AUTO_INCREMENT,
    EmpId CHAR(11) NOT NULL,
    Type CHAR(20),
    Company CHAR(50),
    ItemName CHAR(50),
    Content VARCHAR(200),
    UnitPrice FLOAT,
    NumOfAvaUnits INTEGER,
    PRIMARY KEY (AdId),
    FOREIGN KEY (EmpId)
        REFERENCES Employee (SSN)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE Sales (
    TransId INTEGER AUTO_INCREMENT,
    TransDate DATE,
    TransTime TIME,
    AdId INTEGER,
    NumOfUnits INTEGER,
    AccountNum INTEGER,
    PRIMARY KEY (TransId),
    FOREIGN KEY (AdId)
        REFERENCES AdData (AdId)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

-- create table Posted not necessary, using foreign key relationship between AdData and Employee will be enough

CREATE TABLE Buy (
    TransId INTEGER,
    EmpId CHAR(11) NOT NULL,
    UserId INTEGER,
    PRIMARY KEY (TransId , EmpId , UserId),
    FOREIGN KEY (TransId)
        REFERENCES Sales (TransId)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (EmpId)
        REFERENCES Employee (SSN)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (UserId)
        REFERENCES UserPlus (UserId)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE IsIn (
    UserId INTEGER,
    GroupId INTEGER,
    PRIMARY KEY (UserId , GroupId),
    FOREIGN KEY (UserId)
        REFERENCES UserPlus (UserId)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (GroupId)
        REFERENCES GroupPlus (GroupId)
        ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE Send (
    Sender INTEGER,
    MessageId INTEGER,
    PRIMARY KEY (Sender , MessageId),
    FOREIGN KEY (Sender)
        REFERENCES UserPlus (UserId)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (MessageId)
        REFERENCES Message (MessageId)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Receive (
    Receiver INTEGER,
    MessageId INTEGER,
    PRIMARY KEY (Receiver , MessageId),
    FOREIGN KEY (Receiver)
        REFERENCES UserPlus (UserId)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (MessageId)
        REFERENCES Message (MessageId)
        ON DELETE CASCADE ON UPDATE CASCADE
);