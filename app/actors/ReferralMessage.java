package actors;

import java.util.LinkedList;

import com.fasterxml.jackson.databind.node.ObjectNode;

import controllers.formObject;
import akka.actor.ActorRef;
public class ReferralMessage {
	private ObjectNode message;
	private double[] query;
	public LinkedList<formObject> referralChain;
	public ActorRef ref;
	public ReferralMessage()
    {
		
    }
	public void setQuery( double[] query)
    {
         this.query = query;
    }
	 
	 public double[] getQuery(){
		 return query;
	 }
	 public ActorRef getRef(){
		 if(ref==null){
			 System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
			 		+ ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
			 		+ "NULL REF");
		 }
		 return ref;
	 }
//	 public void setRef(String ref1){
//		 ref = ref1;
//	 }
//	 
	 public void setRef(ActorRef ref1){
		 ref = ref1;
		 if(ref==null){
			 System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
			 		+ ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
			 		+ "NULL REF SET Refereal");
		 }
	 }
	 public ReferralMessage( ObjectNode message )
     {
          this.message = message;
          referralChain = new LinkedList<formObject>();
     }
	 
	 public ObjectNode getMessage()
     {
          return message;
     }
	 
	 public void setMessage( ObjectNode message )
     {
          this.message = message;
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
	 public void printReferal(){
		 for(formObject f: referralChain)
			 System.out.println(f.name);
	 }
}
