delete from CARDS;
delete from ACCOUNTS;
delete from CLIENTS;

insert into CLIENTS (ID, NAME) values
                                      ( '1', 'Ivan' ),
                                      ( '2', 'Petr' ),
                                      ( '3', 'Alex' );

insert into ACCOUNTS (ID, AMOUNT, NUMBER, CLIENT_ID) values
                                                            ( '10', '100', '1234567890', '1' ),
                                                            ( '20', '200', '7894561230', '2' ),
                                                            ( '30', '300', '7418520963', '3' );

insert into CARDS (ID, NUMBER, ACCOUNT_ID) values
                                                  ( '111', '1234 1234 1234 1234', '10' ),
                                                  ( '222', '1234 3214 4563 7895', '10' ),
                                                  ( '333', '7894 8520 3245 0245', '20' ),
                                                  ( '444', '1234 4564 0987 3454', '30' );
