INSERT INTO Users (Id, Login, password)
VALUES (1,'Login1', 'pass'),
       (2,'Login2', 'pass'),
	   (3,'Login3', 'pass'),
	   (4,'Login4', 'pass'),
	   (5,'Login5', 'pass'),
	   (6,'Login6', 'pass'),
	   (7,'Login7', 'pass'),
	   (8,'Me', 'pass');

INSERT INTO Chatroom (Id, Name, Owner)
VALUES (1, 'room 1', 1), 
       (2, 'room 2', 2),
	   (3, 'room 3', 3),
	   (4, 'room 4', 4),
	   (5, 'room 5', 5),
	   (6, 'room 6', 6),
	   (7, 'room 7', 7),
	   (8, 'room 8', 1);

INSERT INTO Message (Id, author_id, room_id, text)
VALUES (1,1, 1, 'Hi1'), 
       (2,2, 2, 'Hi2'),
	   (3,3, 3, 'Hi3'),
	   (4,4, 4, 'Hi4'),
	   (5,5, 5, 'Hi5'),
	   (6,6, 6, 'Hi6'),
	   (7,7, 7, 'Hi7'),
	   (8,8, 8, 'Hi8');
