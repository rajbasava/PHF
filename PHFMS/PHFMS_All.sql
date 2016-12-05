DROP TABLE IF EXISTS phk_seat;
DROP TABLE IF EXISTS phk_history;
DROP TABLE IF EXISTS phk_eventpmt;
DROP TABLE IF EXISTS phk_eventregstrn;
DROP TABLE IF EXISTS phk_eventfee;
DROP TABLE IF EXISTS phk_participant;
DROP TABLE IF EXISTS phk_reference;
DROP TABLE IF EXISTS phk_rowmeta;
DROP TABLE IF EXISTS phk_foundation;
DROP TABLE IF EXISTS phk_volkit;
DROP TABLE IF EXISTS phk_coursetype;
DROP TABLE IF EXISTS phk_partipantcourse;
DROP TABLE IF EXISTS phk_trainer;
DROP TABLE IF EXISTS phk_trainercourse;
DROP TABLE IF EXISTS phk_vollogin;
DROP TABLE IF EXISTS phk_volunteer;
DROP TABLE IF EXISTS phk_kit;
DROP TABLE IF EXISTS phk_event;
DROP TABLE IF EXISTS phk_mail;

CREATE TABLE phk_event (
	id INT PRIMARY KEY auto_increment,
	name VARCHAR(100),
	eventtype int,
	coursetype int,
	venue VARCHAR(100),
	city VARCHAR(50),
	state VARCHAR(50),
    primaryeligibility int,
    secondaryeligibility int,
    primarytrainer int,
    secondarytrainer int,
    foundation int,
	startdate TIMESTAMP null,
	enddate TIMESTAMP null,
	isseatperlvl VARCHAR(1),
	seatingtype VARCHAR(5),
	seatallocated VARCHAR(1),
	rowmetaname varchar(50),
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1)
);

CREATE TABLE phk_eventfee (
	id INT PRIMARY KEY auto_increment,
	eventid int,
	name VARCHAR(100),
	amount int,
	cutoffdate date null,
	review VARCHAR(1),
    coursetype int,
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1),
	FOREIGN KEY (eventid) REFERENCES phk_event(id)
                          ON DELETE CASCADE
);

CREATE TABLE phk_participant (
	id INT PRIMARY KEY auto_increment,
	name VARCHAR(300),
	mobile VARCHAR(15),
	home VARCHAR(15),
	email VARCHAR(50),
	vip VARCHAR(1),
	vipdesc VARCHAR(300),
	address VARCHAR(300),
	city VARCHAR(50),
	state VARCHAR(50),
	country VARCHAR(50),
	zipcode VARCHAR(15),
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1)
);

CREATE TABLE phk_reference (
	id INT PRIMARY KEY auto_increment,
	uniquename VARCHAR(100) not null,
	email VARCHAR(50),
	mobile VARCHAR(15),
	remarks VARCHAR(300),
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1),
	CONSTRAINT uniquename UNIQUE (uniquename)
);

CREATE TABLE phk_eventregstrn (
	id INT PRIMARY KEY auto_increment,
	eventid int,
	participantid int,
	amountpayable int,
	totalamountpaid int,
	amountdue int,
	pendingpdc int,
	foundation int,
	eventfee int,
	reference VARCHAR(100),
	review VARCHAR(1),
	foodcoupon varchar(1),
	eventkit varchar(1),
	application varchar(1),
	certificates varchar(1),
	status varchar(25),
	registrationdate TIMESTAMP null,
    coursetype int,
	reforder int DEFAULT 1,
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1),
    FOREIGN KEY (eventid) REFERENCES phk_event(id),
	FOREIGN KEY (participantid) REFERENCES phk_participant(id)
);

CREATE TABLE phk_eventpmt (
	id INT PRIMARY KEY auto_increment,
	eventregstrnid int,
	amountpaid int,
	pmtmode varchar(15),
	receiptinfo varchar(100),
	receiptdate TIMESTAMP null,
	remarks VARCHAR(300),
	postdtchq VARCHAR(25),
	postdtchqdate TIMESTAMP null,
	pdcnotclear VARCHAR(1),
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1),
	FOREIGN KEY (eventregstrnid) REFERENCES phk_eventregstrn(id)
);

CREATE TABLE phk_volunteer (
	id INT PRIMARY KEY auto_increment,
	name VARCHAR(300),
	email VARCHAR(50),
	password VARCHAR(15),
	mobile VARCHAR(15),
	activity VARCHAR(50),
	permission varchar(30),
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	CONSTRAINT email UNIQUE (email)
);

CREATE TABLE phk_history
(
    id INT PRIMARY KEY auto_increment,
	objectid int,
	objecttype varchar(75),
	comment varchar(500),
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null
);

CREATE TABLE phk_seat
(
    id INT PRIMARY KEY auto_increment,
	eventregstrnid int,
	eventid int,
	coursetype int,
	seat int,
	alpha VARCHAR(5),
	suffix VARCHAR(5),
	custom VARCHAR(1),
	FOREIGN KEY (eventid) REFERENCES phk_event(id),
	FOREIGN KEY (eventregstrnid) REFERENCES phk_eventregstrn(id)
);

CREATE TABLE phk_vollogin (
	id INT PRIMARY KEY auto_increment,
	volunteerid INT,
	counter VARCHAR(5),
	loggedin TIMESTAMP null,
	loggedout TIMESTAMP null,
	FOREIGN KEY (volunteerid) REFERENCES phk_volunteer(id)
);

CREATE TABLE phk_rowmeta (
	id INT PRIMARY KEY auto_increment,
	name varchar(50),
	sortorder int,
	rowname VARCHAR(3),
	rowmax int,
	rowfull varchar(1),
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1),
	unique key(name, rowname)
);

CREATE TABLE phk_foundation (
	id INT PRIMARY KEY auto_increment,
	name varchar(300),
	shortname varchar(30),
	city VARCHAR(50),
	state VARCHAR(50),
	country VARCHAR(50),
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1)
);

CREATE TABLE phk_kit (
	id INT PRIMARY KEY auto_increment,
	name VARCHAR(50),
    stock int,
    eventid int,
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1),
	FOREIGN KEY (eventid) REFERENCES phk_event(id)
);

CREATE TABLE phk_volkit (
	id INT PRIMARY KEY auto_increment,
	kitid int,
	volloginid int,
    kitcount int,
    kitsgiven int,
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1),
	FOREIGN KEY (kitid) REFERENCES phk_kit(id),
	FOREIGN KEY (volloginid) REFERENCES phk_vollogin(id)
);

CREATE TABLE phk_coursetype (
	id INT PRIMARY KEY auto_increment,
	name VARCHAR(100),
	shortName VARCHAR(25),
    primaryeligibility int,
    secondaryeligibility int,
	version int,
    description VARCHAR(1000),
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1)
);

CREATE TABLE phk_partipantcourse (
	id INT PRIMARY KEY auto_increment,
	participant int,
	coursetype int,
	startdate TIMESTAMP null,
	enddate TIMESTAMP null,
    primarytrainer int,
    secondarytrainer int,
    foundation int,
    city VARCHAR(50),
    state VARCHAR(50),
    coursecertificate VARCHAR(50),
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1)
);

CREATE TABLE phk_trainer (
	id INT PRIMARY KEY auto_increment,
	participant int,
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1)
);

CREATE TABLE phk_trainercourse (
	id INT PRIMARY KEY auto_increment,
	trainer int,
	coursetype int,
	foundation int,
	apprentice int,
	contractstartdate TIMESTAMP null,
	contractenddate TIMESTAMP null,
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1)
);

CREATE TABLE phk_mail (
	id INT PRIMARY KEY auto_increment,
	message mediumtext,
	subject VARCHAR(100),
	toaddr varchar(50),
	fromaddr varchar(50),
	retry int,
	templateName VARCHAR(50),
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1)
);

CREATE INDEX idx_part_name ON phk_participant(name(250));
CREATE INDEX idx_part_mbl ON phk_participant (mobile);
CREATE INDEX idx_part_email ON phk_participant (email);

CREATE INDEX idx_partcrs_part ON phk_partipantcourse (participant);

CREATE INDEX idx_trn_part ON phk_trainer(participant);

CREATE INDEX idx_trncrs_trn ON phk_trainercourse(trainer);
CREATE INDEX idx_trncrs_crs ON phk_trainercourse(coursetype);

#--- 03/29/2015 --------
DROP TABLE IF EXISTS phk_workshoplevel;

CREATE TABLE phk_workshoplevel (
	id INT PRIMARY KEY auto_increment,
	event int,
	name VARCHAR(50),
	coursetype int,
	levelorder int,
	start int,
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1)
);

ALTER TABLE `phk_eventregstrn` CHANGE column `coursetype` `workshoplevel` int;
ALTER TABLE phk_eventregstrn add foodtype int;
ALTER TABLE phk_eventregstrn add attend int NOT NULL DEFAULT 0;

CREATE INDEX idx_evregs_attend ON phk_eventregstrn (attend);
CREATE INDEX idx_evregs_fnd ON phk_eventregstrn (foundation);

ALTER TABLE `phk_eventfee` CHANGE column `coursetype` `workshoplevel` int;

ALTER TABLE phk_seat ADD gate varchar(100);
ALTER TABLE phk_seat ADD foodcounter varchar(100);

ALTER TABLE phk_rowmeta ADD gate varchar(100);
ALTER TABLE phk_rowmeta ADD foodcounter varchar(100);

DROP TABLE IF EXISTS phk_accessfilter;

CREATE TABLE phk_accessfilter (
	id INT PRIMARY KEY auto_increment,
	volunteer int,
	event int,
	foundation int,
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1)
);

DROP TABLE IF EXISTS phk_accesscontrol;

CREATE TABLE phk_accesscontrol (
	id INT PRIMARY KEY auto_increment,
	volunteer int,
    permission VARCHAR(30),
	preparedby VARCHAR(50),
	timecreated TIMESTAMP null,
	timeupdated TIMESTAMP null,
	active VARCHAR(1)
);

ALTER TABLE phk_vollogin ADD sessionid varchar(500);