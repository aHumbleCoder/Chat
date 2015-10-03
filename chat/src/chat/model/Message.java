package chat.model;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 5432096974864448846L;

	private String message;

	public Message() {
	}
	
	public Message(String message) {
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
