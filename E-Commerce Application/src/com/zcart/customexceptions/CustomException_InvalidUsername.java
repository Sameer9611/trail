package com.zcart.customexceptions;

public class CustomException_InvalidUsername extends RuntimeException {
	public CustomException_InvalidUsername(String message){
	super(message);
}

}
