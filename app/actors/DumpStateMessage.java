package actors;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class DumpStateMessage {
	private ObjectNode message;
	
	public DumpStateMessage()
    {
    }
	
	 public DumpStateMessage( ObjectNode message )
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
