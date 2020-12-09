package machines;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
	
	private int streetNumber;
	private String street;
	private String postalCode;
	private String city;
	private String country;
	
	public Address() {
		
	}
	
	public Address(int streetNumber, String street, String postalCode, String city, String country) {
		this.streetNumber = streetNumber;
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
	}
	
	public int getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(streetNumber).append(" ");
		sb.append(street).append("\n");
		sb.append(postalCode).append(", ");
		sb.append(city).append(", ");
		sb.append(country);
		return sb.toString();
	}

}
