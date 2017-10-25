package actors;

import java.util.LinkedList;

import com.fasterxml.jackson.databind.node.ObjectNode;

import controllers.formObject;
import akka.actor.ActorRef;
public class QueryMessage {
	private ObjectNode message;
	private double[] query;
	public ActorRef ref;
	public LinkedList<formObject> referralChain;
	public QueryMessage()
    {
		referralChain = new LinkedList<formObject>();
    }
	
	 public QueryMessage( ObjectNode message )
     {
          this.message = message;
          referralChain = new LinkedList<formObject>();
     }
	 
	 public ActorRef getRef(){
//		 if(ref==null){
//			 System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
//			 		+ ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
//			 		+ "NULL REF QUERY");
//		 }
		 return ref;
		 
	 }
//	 public void setRef(String ref1){
//		 ref = ref1;
//	 }
	 public void setRef(ActorRef ref1){
		 ref = ref1;
//		 if(ref==null){
//			 System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
//			 		+ ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
//			 		+ "NULL REF SET QUERY");
//		 }
	 }
	 public ObjectNode getMessage()
     {
          return message;
     }
	 
	 public void setMessage( ObjectNode message )
     {
          this.message = message;
     }
	 
	 public void setQuery( double[] query)
     {
          this.query = query;
     }
	 
	 public double[] getQuery(){
		 return query;
	 }
	 
	 public void addReferral(formObject fo){
		 referralChain.add(fo);
	 }
	 
	 public LinkedList<formObject> getReferral(){
		 return referralChain;
	 }
	 
	 public void setReferral(LinkedList<formObject> ref){
		 referralChain = ref;
	 }
}
