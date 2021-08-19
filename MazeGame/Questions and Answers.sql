BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Questions" (
	"Questions"	TEXT,
	"Answer"	TEXT
);
INSERT INTO "Questions" VALUES ('Who was the ancient Greek goddess of love and beauty','Aphrodite');
INSERT INTO "Questions" VALUES ('Which U.S. president said, “government of the people, by the people, for the people” in his Gettysburg Address','Abraham Lincoln');
INSERT INTO "Questions" VALUES ('In which U.S. city is NASA’s Mission Control Center located',' Houston');
INSERT INTO "Questions" VALUES ('In meteorology, what name is given to a line of equal pressure on a map','Isobar');
INSERT INTO "Questions" VALUES ('What does the Q in IQ stand for','Quotient');
COMMIT;
