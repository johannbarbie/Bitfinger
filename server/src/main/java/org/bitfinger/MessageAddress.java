package org.bitfinger;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MessageAddress {
	public static final String PHONE_REGEX = "^(\\+|\\d)[0-9]{7,16}$";
	public static final String EMAIL_REGEX = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
	
	

	
	public enum MsgType {
		SMS,
		EMAIL,
		UNKNOWN;
	}
	
	private InternetAddress email;
	
	private MsgType addressType;
	
	private String gateway;

	@JsonIgnore
	public String getAddress() {
		if (addressType==MsgType.EMAIL){
			return email.toString();
		}
		return null;
	}
	
	@JsonIgnore
	public Object getAddressObject(){
		if (addressType==MsgType.EMAIL){
			return email;
		}
		return null;
	}
	
	@JsonIgnore
	public MessageAddress setAddress(Object obj){
		if (obj instanceof InternetAddress){
			email = (InternetAddress)obj;
		}else if (obj instanceof String){
			String address = (String)obj;
			if (address.matches(EMAIL_REGEX)) {
				try {
					email = new InternetAddress(address);
					addressType = MsgType.EMAIL;
				} catch (AddressException e) {
					e.printStackTrace();
				}
			}
		}
		return this;
	}
	
	public MsgType getAddressType() {
		return addressType;
	}

	public MessageAddress setAddressType(MsgType addressType) {
		this.addressType = addressType;
		return this;
	}

	public String getGateway() {
		return gateway;
	}

	public MessageAddress setGateway(String gateway) {
		this.gateway = gateway;
		return this;
	}

	public InternetAddress getEmail() {
		return email;
	}

	public MessageAddress setEmail(InternetAddress email) {
		this.email = email;
		return this;
	}

}
