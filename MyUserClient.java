//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// Student name: Lance Baker 
// Course: SENG3400 (Network & Distributed Computing)
// Student number: c3128034
// Assignment title: SENG3400 Assignment 2 
// File name: MyUserClient.java
// Created: 12-09-2010
// Last Change: 12-09-2010
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

import localhost.axis.ce.MyCEServer_jws.*;

public class MyUserClient {
	public enum Method {currentRate, listCurrencies, convert}; // The MyCEServer webservice methods.
	private static String DEFAULT_ERROR = "ERROR: Invalid arguments received";
	
	// Checks whether the received String value is a positive Double.
	// Returns a boolean value indicating success.
	private static boolean isDouble(String input) {
		try {
			return (Double.parseDouble(input) > 0); 
		} catch (Exception ex) {}
		return false;
	}
	
	// The sendRequest method is used to perform the actions corresponding to the desired service method.
	// The method receives a MyCEServer stub instance, the service method (Converted to a Enumeration type), 
	// and the String[] args inputted from the CLI.
	// It returns a String value, which is the output from the webservice requests. 
	// Throws a Exception in events where a communication error has occurred (required by the MyCEServer stub).
	public static String sendRequest(MyCEServer server, Method method, String[] args) throws Exception {
		switch(method) { // The desired service method.
			case currentRate: 
				// Ensures that the correct arguments have been inputted.
				// First element being the method, and the second being the currency code.
				if (args.length == 2) {
					// Returns a String representation of the Double value received 
					// from the webservices currentRate method (based on the currency code)
					return Double.toString(server.currentRate(args[1]));
				}
				break;
			case listCurrencies:
				// Invokes the listCurrencies webservice method.
				// Outputs a list of the available currencies.
				return server.listCurrencies();
			case convert:
				// The webservices conversion method requires the FROM currency code (args[1]), the TO currency code (args[2]), 
				// and the currency amount desired (args[3]) for the conversion to take place.
				if ((args.length == 4) && (isDouble(args[3]))) { // Checks the args length, and whether the received amount is a Double.
					// Invokes the convert webservice method.
					// Returns a formatted String of the conversion. Eg (10.50 AUD = 7.31 EUR).
					return server.convert(args[1], args[2], Double.parseDouble(args[3]));
				}
				break;
		}
		return DEFAULT_ERROR; // The CLI arguments were incorrectly entered.
	}
	
	public static void main(String[] args) {
		try {
			// If the received arguments have at least the method specified, and the first args element
			// is not null - then it converts the first element into a Method Enumeration type (which is
			// the desired webservice method that you want invoked).
			if ((args.length >= 1) && (args[0] != null)) {
				// The ServiceLocator, which essentially connects to the remote webservice, and provides
				// the ability to get a stub instance of the web service.
				MyCEServerService service = new MyCEServerServiceLocator();
				// Calls the sendRequest method, which receives a instantiated stub of the webservice, the method desired to be invoked,
				// and the arguments received from the CLI.
				System.out.println(sendRequest(service.getMyCEServer(), Method.valueOf(args[0]), args));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage()); // Any unexpected exception raised.
		}
	}
}