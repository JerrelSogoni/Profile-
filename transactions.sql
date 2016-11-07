-- TASK
-- Add password

-- Users should be able to perform the following general transactions:
-- - Register
INSERT into	userplus( UserID, Password, FirstName, LastName, Address, City, State, ZipCode, Phone, Email, AccountNum, AccountCreationDate, CreditCardNum, Preferences) values ( ?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);

INSERT INTO PagePlus(PageID, PostCount) values(?, ?);
INSERT INTO PersonalPage(PageId, OwnerID) values(?, ?);

INSERT INTO HasAPersonal(UserID, PersonalPageID) values(?, ?);

-- - Sign in and sign out
SELECT * FROM UserPlus WHERE userId=? AND password=?;
-- SIGN OUT N/and
-- ??

-- - Post messages in their personal pages
INSERT INTO POST(PostID, DateCreated, Content, CommentCount, AuthorID) values (?, ?, ?, ?, ?);
-- INSERT INTO MakesPost values (?PostID, ?AuthorID);
INSERT INTO PostedTo(PostID, PageID) values (?, ?);

-- - Send a message
SELECT UserId From UserPlus WHERE UserId=?;

INSERT INTO Message(MessageID, DateSent, ReceiverID, SenderID, Subject, Content) values (?, ?, ?, ?, ?, ?);
INSERT INTO Send(Sender, MessageID) values (?, ?);

-- - Receive a message
SELECT UserId From UserPlus WHERE UserId=?;
INSERT INTO Receive(Receiver, MessageId) values (?, ?);

-- - Delete a message
DELETE FROM Message WHERE MessageID = ?;
-- DELETE FROM Send WHERE MessageId = ?;
-- DELETE FROM Receive WHERE MessageId = ?;

-- Users should be able to perform the following transactions with regard to their own groups:
-- - Create a group
-- Create Group
INSERT INTO GroupPlus(GroupID, GroupName, Owner, Type) values (?, ?, ?, ?);

-- Create Group Page
INSERT INTO PagePlus(PageID, PostCount) values(?, ?);
INSERT INTO GroupPage(PageId, GroupID) values(?, ?);

-- Combind Group and GroupPage
INSERT INTO HasAGroupPage(GroupID, GroupPageID) values(?, ?);
-- INSERT INTO CreatesGroup(UserId, GroupId) values (?, ?); -- Not necessary

-- grant access to creator
INSERT INTO HasAccessToGroup(UserId, PageID, GroupID) values (?, ?, ?);

-- - Search for a user and add him to a group

-- if(SELECT * FROM HasAccessToGroup WHERE Adder_Id = UserId AND GroupId = GroupId);
-- 	INSERT INTO HasAccessToGroup(UserId, PageID, GroupID) values (?/*Target_Id*/, ?, ?);
-- else (
-- 	"You don't have access!!
-- )

SELECT * FROM HasAccessToGroup WHERE AdderID = ? AND GroupID = ?;

-- if above returns non null
INSERT INTO HasAccessToGroup(UserID, PageID, GroupID) values (?/*Target_Id*/, ?, ?);
-- else 
-- do not allow the user to grant Adder_Id access to the group

-- Potential Alternative way
-- INSERT INTO AddToGroup(Adder_Id, Target_Id, GroupId) values (?, ?, ?)
-- WHERE (SELECT * From HasAccessToGroup WHERE Adder_Id = UserId AND GroupId = GroupId);
-- INSERT INTO HasAccessToGroup(UserId, PageID, GroupID) values (?/*Target_Id*/, ?, ?);
-- SELECT UserId from UserPlus WHERE 

-- - Make a post
-- if(postingToPersonalPage)
-- 		INSERT INTO PostedTo
-- 	else 
-- 		if(SELECT * FROM HasAccessToGroup WHERE Adder_Id = ? AND GroupId = ?; != NULL)
-- 			INSERT INTO PostedTo

SELECT * FROM HasAPersonal WHERE UserID = ? AND PersonalPageID = ?; -- check if it is a personal page

SELECT * FROM HasAccessToGroup WHERE AdderID = ? AND GroupId = ?; -- check if has access to group

INSERT INTO PostedTo(PostID, PageID) values (?, ?); -- insertion

-- - Comment on a post
-- 	if(SELECT * FROM HasAccessToGroup WHERE Adder_Id = ? AND GroupId = ?; != NULL)
-- 		INSERTION

INSERT INTO Comment(CommentID, DateCreated, Content, AuthorID) VALUES (?, ?, ?, ?);
INSERT INTO CommentOn(CommentID, PostID) VALUES (?, ?);

-- - Like a post
-- 	if(SELECT * FROM HasAccessToGroup WHERE Adder_Id = ? AND GroupId = ?; != NULL)
-- 		INSERTION
INSERT INTO LikesPost(PostID, UserID) VALUES(?, ?);

-- - Like a comment
-- 	if(SELECT * FROM HasAccessToGroup WHERE Adder_Id = ? AND GroupId = ?; != NULL)
-- 		INSERTION
INSERT INTO LikesComment(CommentID, UserID) VALUES(?, ?);

-- - Remove a user from a group
DELETE FROM HasAccessToGroup WHERE UserID = ? AND GroupID = ?; -- check permission to do so

-- - Remove a post
DELETE FROM PostedTo WHERE PostID = ? AND PageID = ?;
-- CASCADE will delete the post 

-- - Remove a comment
DELETE FROM CommentOn WHERE CommentID = ? AND PostID = ?;

-- - Unlike a post
DELETE FROM LikesPost WHERE PostID = ? AND UserID = ?;

-- - Unlike a comment
DELETE FROM LikesComment WHERE CommentID = ? AND UserID = ?;

-- - Unlike a post
DELETE FROM LikesPost WHERE PostId = ? AND UserId = ?;

-- - Unlike a comment
DELETE FROM LikesComment WHERE CommentId = ? AND UserId = ?;

-- - Modify a post
UPDATE Post 
SET DateCreated = ?, Content = ?, CommentCount = ?
Where PostId = ?;

-- - Modify a comment
-- - Delete a group
DELETE FROM GroupPlus WHERE GroupId = ?;

-- - Rename a group
UPDATE GroupPlus SET GroupName = ? WHERE GroupId = ?;

-- Users should also be able to perform the following transactions with regard to other users' groups:
-- - Join a group
-- - Unjoin a group
-- - Make a post on a group page
SELECT * FROM HasAPersonal WHERE UserId = ? AND PersonalPageId = ?; -- check if it is a personal page

SELECT * FROM HasAccessToGroup WHERE Adder_Id = ? AND GroupId = ?; -- check if has access to group

INSERT INTO PostTo(PostId, PageId) values (?, ?); -- insertion

-- - Comment on a post on a group page
INSERT INTO Comment(CommentId, DateCreated, Content, AuthorId) VALUES (?, ?, ?, ?);
INSERT INTO CommentOn(CommentId, PostId) VALUES (?, ?);

-- - Like a post on a group page
INSERT INTO LikesPost(PostId, UserId) VALUES(?, ?);

-- - Like a comment on a group page
INSERT INTO LikesComment(CommentId, UserId) VALUES(?, ?);

-- - Remove one of their posts on a group page
DELETE FROM PostedTo WHERE PostId = ? AND PageId = ?;

-- - Remove a comment
DELETE FROM CommentOn WHERE CommentId = ? AND PostId = ?;

-- - Unlike a post
DELETE FROM LikesPost WHERE CommentId = ? AND UserId = ?;

-- - Unlike a comment
DELETE FROM LikesComment WHERE CommentId = ? AND UserId = ?;

--- - Modify a post
--- - Modify a comment

-- Manager-Level Transactions
-- The manager should be able to:
-- - Add, Edit and Delete information for an employee

-- Add Employee
INSERT INTO Employee(SSN ,LastName , FirstName, Address, City, State, ZipCode, Telephone, StartDate, HourlyRate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
-- Edit Employ
UPDATE Employee
  SET SSN = ? ,LastName = ?, FirstName = ?, Address = ?, City = ?, State = ?, ZipCode = ?, Telephone = ?, StartDate = ?, HourlyRate = ?;
-- Delete Information for an Employee

DELETE FROM Employee
  WHERE SSN = ?;

-- - Obtain a sales report for a particular month
SELECT * FROM Sales
    WHERE MONTH(TransDate) = ?;
-- - Produce a comprehensive listing of all items being advertised on the site



-- - Produce a list of transactions by item name or by user name
SELECT * FROM Sales S
  WHERE
    (SELECT  * FROM AdData A
      WHERE A.ItemName  = ? AND A.AdId = S.AdId OR (SELECT * FROM Buy B
                                                      WHERE B.UserId = ? AND B.TransId = A.AdId) );

-- - Produce a summary listing of revenue generated by a particular item, item type, or customer
SELECT * FROM Sales
    WHERE (SELECT * FROM AdData
            WHERE Type = ? OR ItemName = ?) OR AccountNum = ?;

-- - Determine which customer representative generated most total revenue

SELECT EmpId, MAX(SUM(A.UnitPrice * S.NumOfUnits)) AS TotalRevenue FROM AdData A, Sales S;

-- - Determine which customer generated most total revenue
SELECT UserId, MAX(SUM(A.UnitPrice * S.NumOfUnits)) AS TotalRevenue FROM AdData A, Sales S, UserPlus U;

-- - Produce a list of most active items?
CREATE VIEW ActiveItems AS 
  SELECT A.ItemName FROM AdData A
    WHERE (SELECT COUNT (*) FROM Sales S 
          WHERE S.AdId = A.AdId) > 20;

-- - Produce a list of all customers who have purchased a particular item
CREATE VIEW ItemPurchasers AS 
  SELECT * FROM UserPlus U
    WHERE (SELECT * FROM AdData A,  Buy B, Sales S
           WHERE A.AdId = ? AND A.AdId = S.AdId AND S.TransId = B.TransId 
           AND U.UserId = B.UserId);
-- - Produce a list of all items for a given company
CREATE VIEW CompanyItems AS 
  SELECT A.ItemName 
    FROM AdData A
      WHERE A.Company = ?;

-- Customer-Representative-Level Transactions
-- Customer Representatives should be thought of as sales agents and should be able to:
-- - Create an advertisement
-- - Delete an advertisement
-- - Record a transaction
-- - Add, Edit and Delete information for a customer
-- - Produce customer mailing lists
-- - Produce a list of item suggestions for a given customer (based on that customer's past transactions)
--  
-- Customers should also be able to perform the following transactions with regard to advertisements:
-- - Purchase one or more copies of an advertised item
--  
-- While customers (users) will not be permitted to access the database directly, they should be able to retrieve the following information:
-- - A customer's current groups
CREATE VIEW UserGroups AS
  SELECT G.GroupName
    FROM GroupPlus G
      WHERE (SELECT * FROM HasAccessToGroup H, 
             WHERE H.UserID = ? AND H.GroupID = G.GroupID);
-- - For each of a customer's accounts, the account history
CREATE VIEW AcctHistory AS
	SELECT A.ItemName, S.NumOfUnits
		FROM AdData A, Sales S
			WHERE (SELECT * FROM Buy B 
				WHERE B.UserID = ? AND S.TransId = B.TransId
				AND S.AdId = A.AdId);
-- - Best-Seller list of items
CREATE VIEW BestSellers AS
	SELECT A.ItemName
	FROM AdId A
		WHERE (SELECT COUNT (*) FROM Sales S
			WHERE S.AdId = A.AdId) > 20;
-- - Personalized item suggestion list




