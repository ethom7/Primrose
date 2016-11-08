package companyA;

import java.util.ArrayList;
import java.util.HashMap;
import org.bson.Document;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import companyA.MongoConnector;
import companyA.Employee;
import companyA.PostalAddress;
import companyA.User;

public class App {
	
	static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		//TODO: password encryption
		
		System.out.println("Please select a function: ");
		System.out.println("1: Load users from .csv file");
		System.out.println("2: Set a user as inactive");
		System.out.println("0: Exit");
		int choice = input.nextInt();
		myMongoObject mmO = new myMongoObject();
		
		switch(choice)
		{
		case 1:
		{
			System.out.println("Loading users from .csv file");
			//setup, load, add
			myMongoObject current = MongoSetup(mmO);
			System.out.println("Back at case 1");
			System.out.println(current.toString());
			addToCollection(current);
			break;
		}
		case 2:
		{
			System.out.println("Setting a user as inactive");
			setInactive(mmO);
			break;
		}
		case 0:
		{
			System.out.println("Thanks for using Primrose software, goodbye!");
			System.exit(0);
		}
		default:
			System.out.println("Invalid input");
			main(args);
			break;
		}
		
		main(args);
		
		
	}
	
	private static void setInactive(myMongoObject mmO) {
		System.out.print("To set a user as INACTIVE, please enter the user/employee ID number: ");
		int inactiveID = input.nextInt();
		
		//TODO: Check if the ID exists in the document? or in what's already uploaded in Mongo?
		//if so, set it to inactive
		//if not, prompt user for new ID or allow to return to main menu
		
		Bson filter = eq("id", inactiveID); //filter by matching id
		Bson projection = Projections.exclude("_id"); //exclude the object id from the return
				
		MongoCursor<Document> itr = mmO.getUserCollection().find(filter).projection(projection).iterator();
		MongoCursor<Document> itr2 = mmO.getEmployeeCollection().find(filter).projection(projection).iterator();
		
		try
		{
			while (itr.hasNext())
			{
				itr.next();
				
			}
			while (itr2.hasNext())
			{
				
			}
		}
		finally
		{
			itr.close();
			itr2.close();
		}
				
	}

	public static User getUser(String employee) {
		   
	   User user = new User(employee, "TeamPrimrose!1");	  
	   return user;
	}
	
	private static myMongoObject MongoSetup(myMongoObject mmO)
	{
//TODO: prompt the user for the path and extension of their own csv file as String (tenK)
				
		//String tenK = "src/testdataN10K.csv";
		String tenK = "src/main/java/companyA/testdataN10K.csv";
		ArrayList<HashMap<String, String>> hm2 = ReadMethods.createListFromCSV(tenK, ",");  //readIn list
		
		String[] dataOrder = {"firstName", "middleName", "lastName", "socialSecurityNumber", "dateOfBirth", "postalAddress", "phoneNumber", "hireDate"};	//this is correct data order

//NOTE: hm2 is loaded properly!!
		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		mmO.setEmployeeList(employeeList);
		
		// instantiate a mongodb from MongoConnection class
		MongoDatabase db = MongoConnector.getInstance().getMongoDatabase();
				
		// instantiate a collection 
		MongoCollection<Document> employeeCollection = db.getCollection("employees");
		MongoCollection<Document> userCollection = db.getCollection("users"); 	
		mmO.setEmployeeCollection(employeeCollection);
		mmO.setUserCollection(userCollection);
		
		return loadEmployees(hm2, dataOrder, mmO);
	}
	
	private static myMongoObject loadEmployees(ArrayList<HashMap<String, String>> hm2, String[] dataOrder, myMongoObject mmO)
	{
		for (HashMap<String, String> row : hm2) {
			System.out.println("ROW: " +row);
			System.out.println("First name: " +row.get(dataOrder[0]));
			
			Employee emp = new Employee();
			emp.setActive(true);
			//Employee(, row.get(dataOrder[1]), row.get(dataOrder[2]), row.get(dataOrder[3]), row.get(dataOrder[4]), new PostalAddress(row.get(dataOrder[5])), row.get(dataOrder[6]), row.get(dataOrder[7]))
			
//NOTE: all rows are correct, same as hm2 above
			emp.setFirstName(row.get(dataOrder[0]));
			emp.setMiddleName(row.get(dataOrder[1]));
			emp.setLastName(row.get(dataOrder[2]));
			emp.setSocialSecurityNumber(Integer.parseInt(row.get(dataOrder[3])));
			emp.setDateOfBirth(row.get(dataOrder[4]));
			//emp.setPostalAddress(new PostalAddress(row.get(dataOrder[5])));
			emp.setPhoneNumber(row.get(dataOrder[6]));
			
			mmO.getEmployeeList().add(emp);		
		}
		return mmO;
	}
	
	private static void addToCollection(myMongoObject mmO)
	{
		//the StringIndexOutOfBoundsException is because line 64 first name Ken Marc App is not a long enough name
		
		for (int i = 0; i < mmO.getEmployeeList().size();i++) {
			System.out.println("Output:" +i);
			ObjectMapper mapper = new ObjectMapper();
			String employeeString;
			String userString;
			
			try {
				
				User user = getUser(mmO.getEmployeeList().get(i).fullNameAsString());
				employeeString = mapper.writeValueAsString(mmO.getEmployeeList().get(i));
				System.out.println("employee string: " +employeeString);
				
				//First name isn't being read or set, postalAddress isn't being set (given a full street address), emergencyContact isn't being set
				userString = mapper.writeValueAsString(user);
				
				Document employeeDoc = new Document("id", i).append("employee", employeeString);  //employeeDoc holds all the values of employeeString 
				System.out.println("***employeeString: " +employeeString);  //employeeString all the values from the csv
				mmO.getEmployeeCollection().insertOne(employeeDoc); //employeeDoc is what's loaded into the collection
				
				Document userDoc = new Document("id", i).append("employee", userString);
				System.out.println("***userString: " +userString);  //attributes are: givenName, userName, password
				System.out.println();
				mmO.getUserCollection().insertOne(userDoc);
				
			} catch (JsonProcessingException e) {
				
				e.printStackTrace();
			}
		}
			
		System.out.println(mmO.getEmployeeCollection().count());
		System.out.println(mmO.getUserCollection().count());
			
	}

}
