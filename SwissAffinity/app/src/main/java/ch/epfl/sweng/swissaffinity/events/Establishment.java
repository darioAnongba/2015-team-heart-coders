package ch.epfl.sweng.swissaffinity.events;

import java.util.IllegalFormatCodePointException;

/**
 * The class that represents an establishment or more or less a partner
 */
public class Establishment {

    private String type;
    private String address;
    private String phoneNumber;
    private String contactName;

    /**
     * the setter of the Estabmishment
     * @param type (EstablishmentType) the type of establishment
     * @param address the adress of the Establishment
     * @param phoneNumber the phone number of the establishment
     * @param contactName the contact's name of the establishment
     * @throws IllegalArgumentException if there is no phone number , no type , no adress or no contact name
     */
    public Establishment(String type, String address, String phoneNumber, String contactName) throws IllegalArgumentException {
        if(type==null || address.isEmpty() || phoneNumber.isEmpty() || contactName.isEmpty() ) {
            throw new IllegalArgumentException("Un des arguments est vide ou sans valeur");
        }
        this.type = type;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.contactName = contactName;
    }

    /**
     * The getter for the establishment's type
     * @return the establisment's type ( in EstablishmentType)
     */
    public String getType() {
        return type;
    }

    /**
     * The setter fot the establishment's type
     * @param type the new establishment's type (EstablishmentType)
     * @throws IllegalArgumentException if the new EstablishmentType is null
     */
    public void setType(String type) throws IllegalArgumentException {
        if(type==null)  {
            throw new IllegalArgumentException("The type must be non-null" + type);
        }
        this.type = type;
    }


    /**
     * The getter for the establishment's address
     * @return the establishment's adress
     */
    public String getAddress() {
        return address;
    }

    /**
     * The setter for the establishment's address
     * @param address
     * @throws IllegalArgumentException when the address is empty
     */
    public void setAddress(String address) throws IllegalArgumentException {
        if(address.isEmpty()) {
            throw new IllegalArgumentException("The adress is empty" + address);
        }
        this.address = address;
    }

    /**
     * The getter for the establishment's phone number
     * @return the establishment's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * The setter for the establishment's phone number
     * @param phoneNumber the new establishment's phone number
     * @throws IllegalArgumentException if the phone number is empty
     */
    public void setPhoneNumber(String phoneNumber) throws IllegalArgumentException{
        if(phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("The phone number is empty" + phoneNumber);
        }
        this.phoneNumber = phoneNumber;
    }

    /**
     * The getter for the name of the contact for the establishment
     * @return the name of the contact of the establishment
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * The setter for the name of the contact for the establishment
     * @param contactName the new contact's name for the establishment
     * @throws IllegalArgumentException if the contact name is empty
     */
    public void setContactName(String contactName) throws IllegalArgumentException {
        if(contactName.isEmpty()) {
            throw new IllegalArgumentException("The contact's name is empty"+contactName);
        }
        this.contactName = contactName;
    }
}
