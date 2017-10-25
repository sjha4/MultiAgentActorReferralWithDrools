package actors;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class GenerateQueryMessage {
	private ObjectNode message;
	
	public GenerateQueryMessage()
    {
    }
	
	 public GenerateQueryMessage( ObjectNode message )
     {
          this.message = message;
     }
	 
	 public ObjectNode getMessage()
     {
          return message;
     }
	 
	 public void setMessage( ObjectNode message )
     {
          this.message = message;
     }
}
