package drools;

import java.util.Date;

public class Message {
	private Date date;
	private String ActorName;
	private String MessageType;
	private String message;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getActorName() {
		return ActorName;
	}
	public void setActorName(String actorName) {
		ActorName = actorName;
	}
	public String getMessageType() {
		return MessageType;
	}
	public void setMessageType(String messageType) {
		MessageType = messageType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Message(Date date, String actorName, String messageType, String message) {
		//super();
		this.date = date;
		ActorName = actorName;
		MessageType = messageType;
		this.message = message;
	}
	

}
