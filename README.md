-----------------------------------------------------------------------------------------
########### Runtime formats #############################################################
-----------------------------------------------------------------------------------------
Hi,

In order to run both client applications; please use the following run formats outlined below.
There is also validation to check whether the received arguments are correct.


MyUserClient.java:

	java MyUserClient currentRate <currency_code>;
	
	java MyUserClient listCurrencies;

	java MyUserClient convert <from_currency> <to_currency> <rate_amount>;

	
MyUserAdmin.java:

	java MyUserAdmin addCurr <user_name> <password> <currency_code> <amount>;

	java MyUserAdmin setRate <user_name> <password> <currency_code> <amount>;

	java MyUserAdmin callCount <user_name> <password>;


-----------------------------------------------------------------------------------------
########### Please note #################################################################
-----------------------------------------------------------------------------------------
The SERVER_SERVICE constant located in the MyCEAdmin.jws will need to be edited to contain 
the web accessible path to the MyCEServer.jws service.

	Example
	private static final String SERVER_SERVICE = "http://localhost:8080/axis/ce/MyCEServer.jws";

-----------------------------------------------------------------------------------------
