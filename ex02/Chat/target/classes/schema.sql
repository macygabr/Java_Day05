
DROP TABLE IF EXISTS Message;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Chatroom;

CREATE TABLE IF NOT EXISTS Users(ID INTEGER, Login TEXT, Password TEXT);
CREATE TABLE IF NOT EXISTS Chatroom(ID INTEGER, Name TEXT, Owner INTEGER);
CREATE TABLE IF NOT EXISTS Message(ID INTEGER, author_id INTEGER, room_id INTEGER, text TEXT, time TIME);
