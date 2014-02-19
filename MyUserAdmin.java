//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// Student name: Lance Baker 
// Course: SENG3400 (Network & Distributed Computing)
// Student number: c3128034
// Assignment title: SENG3400 Assignment 2 
// File name: MyUserAdmin.java
// Created: 12-09-2010
// Last Change: 12-09-2010
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

import localhost.axis.ce.MyCEAdmin_jws.*;
public class MyUserAdmin {

	public enum Method {addCurr, setRate, callCount}; // The MyCEAdmin webservice methods.
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
	// The method receives a MyCEAdmin stub instance, the service method (Converted to a Enumeration type), 
	// and the String[] args inputted from the CLI.
	// It returns a String value, which is the output from the webservice requests. 
	// Throws a Exception in events where a communication error has occurred (required by the MyCEAdmin stub).
	public static String sendRequest(MyCEAdmin server, Method method, String[] args) throws Exception {
		switch(method) { // The desired service method.
		
			// The addCurr webservice method requires; the username (args[1]), password (args[2]), the currency code (args[3]),
			// and the currency rate (args[4]) - as arguments.
			case addCurr: 
				// Checks whether the arguments received from the CLI are correct length,
				// and checks whether the currency rate is a valid Double.
				if ((args.length == 5) && (isDouble(args[4]))) { 
					// Invokes the webservices stub addCurr method (forwarding the arguments).
					// Returns the output from the webservice as a boolean (converted to a String).
					return new Boolean(server.addCurr(args[1], args[2], args[3], Double.parseDouble(args[4]))).toString();
				}
				break;
				
			// The setRate webservice method requires the same arguments as the addCurr method.
			case setRate:
				// Checks whether the arguments received are valid.
				if ((args.length == 5) && (isDouble(args[4]))) {
					// Invokes the webservices stub setRate method (which also forwards the arguments).
					// Returns the output from the webservice as a boolean (converted to a String).
					return new Boolean(server.setRate(args[1], args[2], args[3], Double.parseDouble(args[4]))).toString();
				}
				break;
				
			// The callCount webservice method requires the username (args[1]), and password (args[2]) for invocation.
			case callCount:
				// Checks whether the arguments have been correctly entered.
				if (args.length == 3) {
					// Invokes the webservice stub callCount; forwarding the user credentials.
					// Returns a Integer response (converted to a String).
					return Integer.toString(server.callCount(args[1], args[2]));
				}
				break;
		}
		return DEFAULT_ERROR; // The CLI arguments were incorrectly entered.
	}
	
	public static void main(String[] args) {
		try {
			// Checks whether the received arguments are correct length; having at least the webservice method, 
			// and the user credentials being specified.
			if ((args.length >= 3) && (args[0] != null)) {
				// The ServiceLocator, which essentially connects to the remote webservice, and provides
				// the ability to get a stub instance of the web service.
				MyCEAdminService service = new MyCEAdminServiceLocator();
				
				// Converts the first element into a Method Enumeration type (which is the desired webservice method that you want invoked).
				// Calls the sendRequest method, which receives a instantiated stub of the webservice, the method desired to be invoked,
				// and the arguments received from the CLI. Prints the output of the sendRequest method to the console.
				System.out.println(sendRequest(service.getMyCEAdmin(), Method.valueOf(args[0]), args));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage()); // Any unexpected exception raised.
		}
	}
}