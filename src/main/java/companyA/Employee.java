package companyA;

import java.util.Date;

public class Employee {
	
	private int employeeId;
	private String firstName;
	private String middleName;
	private String lastName;
	private int socialSecurityNumber;
	private String dateOfBirth;
	private PostalAddress postalAddress;
	private EmergencyContact emergencyContact;
	private String phoneNumber;
	private boolean isActive;
	
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Employee() {  }
	
	public Employee(String firstName, String middleName, String lastName, int socialSecurityNumber, String dateOfBirth,
			PostalAddress postalAddress, EmergencyContact emergencyContact, String phoneNumber) {
		
		
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.socialSecurityNumber = socialSecurityNumber;
		this.dateOfBirth = dateOfBirth;
		this.postalAddress = postalAddress;
		this.emergencyContact = emergencyContact;
		this.phoneNumber = phoneNumber;
	}


	public String getFirstName() {
		return firstName;
	}


	public String getMiddleName() {
		return middleName;
	}


	public String getLastName() {
		return lastName;
	}


	public int getSocialSecurityNumber() {
		return socialSecurityNumber;
	}


	public String getDateOfBirth() {
		return dateOfBirth;
	}


	public PostalAddress getPostalAddress() {
		return postalAddress;
	}


	public EmergencyContact getEmergencyContact() {
		return emergencyContact;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


	void setLastName(String lastName) {
		this.lastName = lastName;
	}


	void setSocialSecurityNumber(int socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}


	void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	void setPostalAddress(PostalAddress postalAddress) {
		this.postalAddress = postalAddress;
	}


	private void setEmergencyContact(EmergencyContact emergencyContact) {
		this.emergencyContact = emergencyContact;
	}


	void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String fullNameAsString() {
		return String.format("%s %s %s", firstName, middleName, lastName);
	}
		
	
	@Override
	public String toString() {
		return String.format("%s %s %s: phone number: %s\n%s\nEmergency Contact: %s", firstName, middleName, lastName, phoneNumber, postalAddress,emergencyContact);
	}

	


	
	
	
}