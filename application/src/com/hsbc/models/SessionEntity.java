package com.hsbc.models;

public class SessionEntity {
	private int personId;
	private String sessionToken;// keep varchar(300);

	public SessionEntity(int personId, String sessionToken) {
		this.personId = personId;
		this.sessionToken = sessionToken;
	}

	public SessionEntity() {
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int customerId) {
		this.personId = customerId;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	@Override
	public String toString() {
		return "SessionEntity [personId=" + personId + ", sessionToken=" + sessionToken + "]";
	}

}
