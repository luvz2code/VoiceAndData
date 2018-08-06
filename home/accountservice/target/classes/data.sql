--Primary keys start from 100 to avoid any conflict with hibernate created keys.

insert into account (account_id, first_Name, last_Name, hire_Date, created_At, updated_At)
values(100, 'Rajeev','Ayyar',PARSEDATETIME('01/01/2005', 'MM/dd/yyyy', 'en', 'GMT'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into address (account_id, address_id, city, country, effective_date, line1, line2, line3, state, type, zipcode)
values(100, 100, 'Redwood City','USA',PARSEDATETIME('01/01/2005', 'MM/dd/yyyy', 'en', 'GMT'), '4500 Bohannan Drive', '', '', 'California', 'HOME', '94061');

insert into address (account_id, address_id, city, country, effective_date, line1, line2, line3, state, type, zipcode)
values(100, 101, 'Menlo Park','USA',PARSEDATETIME('01/01/2005', 'MM/dd/yyyy', 'en', 'GMT'), '4500 Bohannan Drive', '', '', 'California', 'WORK', '94025');

insert into account (account_id, first_Name, last_Name, hire_Date, created_At, updated_At)
values(101, 'Sanah','Ayyar',PARSEDATETIME('01/01/2009', 'MM/dd/yyyy', 'en', 'GMT'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

