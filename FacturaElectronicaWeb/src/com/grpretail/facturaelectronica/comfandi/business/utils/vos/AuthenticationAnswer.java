package com.grpretail.facturaelectronica.comfandi.business.utils.vos;

public class AuthenticationAnswer {
	boolean successfulFlag;
	String token;
	String state;
	
	
	public AuthenticationAnswer(){
		successfulFlag = false;
		token = "";
		state = "";
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}

	public boolean isSuccessful() {
		return successfulFlag;
	}
	
	public void setSuccessfulFlag(boolean successfulFlag) {
		this.successfulFlag = successfulFlag;
	}
	
}
