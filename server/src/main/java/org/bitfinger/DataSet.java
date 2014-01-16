package org.bitfinger;

import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.ext.beans.ResourceBundleModel;

@JsonInclude(Include.NON_NULL)
public class DataSet {
	
	public enum Action {
		RESET("Reset"), // gateway reset password
		REGISTER("Register");

		private String text;

		Action(String text) {
			this.text = text;
		}

		@JsonValue
		final String value() {
			return this.text;
		}

		public String getText() {
			return this.text;
		}

		@JsonCreator
		public static Action fromString(String text) {
			if (text != null) {
				for (Action b : Action.values()) {
					if (text.equalsIgnoreCase(b.text)) {
						return b;
					}
				}
			}
			return null;
		}
	}
	
	public DataSet(){
		setService("37coins");
	}
	
	private Action action;
	
	private Locale locale;
	
	private String service;
	
	private MessageAddress to;
	
	private Object payload;
		
	private ResourceBundleModel resBundle;
	
	//########## UTILS

	@Override
	public boolean equals(Object obj) {
		if (obj==null)
			return false;
		JsonNode a = new ObjectMapper().valueToTree(this);
		JsonNode b = new ObjectMapper().valueToTree(obj);
		return a.equals(b);
	};	
	
	@Override
	public String toString(){
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}


	//########## GETTERS && SETTERS
	
	public Action getAction() {
		return action;
	}

	public DataSet setAction(Action action) {
		this.action = action;
		return this;
	}
	
	@JsonIgnore
	public String getLocaleString() {
		if (null!=locale)
			return locale.toString().replace("_", "-");
		return null;
	}

	public Locale getLocale() {
		return locale;
	}

	public DataSet setLocale(Locale locale) {
		this.locale = locale;
		return this;
	}
	
	@JsonIgnore
	public DataSet setLocaleString(String locale){
		if (null==locale)
			return this;
		String[] l = locale.split("[-_]");
		switch(l.length){
	        case 2: this.locale = new Locale(l[0], l[1]); break;
	        case 3: this.locale = new Locale(l[0], l[1], l[2]); break;
	        default: this.locale = new Locale(l[0]); break;
	    }
		return this;
	}

	public MessageAddress getTo() {
		return to;
	}

	public DataSet setTo(MessageAddress to) {
		this.to = to;
		return this;
	}
	
	public String getService() {
		return service;
	}

	public DataSet setService(String service) {
		this.service = service;
		return this;
	}

	public Object getPayload() {
		return payload;
	}

	public DataSet setPayload(Object payload) {
		this.payload = payload;
		return this;
	}

	public ResourceBundleModel getResBundle() {
		return resBundle;
	}

	public DataSet setResBundle(ResourceBundleModel resBundle) {
		this.resBundle = resBundle;
		return this;
	}

}
