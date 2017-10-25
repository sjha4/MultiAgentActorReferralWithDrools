package actors;

import java.util.LinkedList;

import com.fasterxml.jackson.databind.node.ObjectNode;

import akka.actor.ActorRef;
import controllers.formObject;
import akka.actor.ActorRef;
public class AnswerMessage {
	private ObjectNode message;
	public LinkedList<formObject> referralChain;
	public ActorRef ref;
	private double[] query;
	public double[] answer;
	public AnswerMessage()
    {
    }
	public void setAnswer(double[] ans){
		
		answer = ans;
	}
	public double[] getAnswer(){
		
		return answer;
	}
	 public ActorRef getRef(){
		 if(ref==null){
			 System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
			 		+ ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
			 		+ "NULL REF");
		 }
		 return ref;
	 }
//	 public void setRef(ActorRef ref1){
//		 ref = ref1;
//	 }
	 public void setRef(ActorRef ref1){
		 if(ref1==null){
			 System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
			 		+ ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
			 		+ "NULL REF SET");
		 }
		 ref = ref1;
		 
	 }
	public String toString(){
		String ans ="[";
		for(double d: answer){
			ans+=String.valueOf(d)+",";
		}
		return ans+"]";
			
	}
	 public AnswerMessage( ObjectNode message )
     {
          this.message = message;
          referralChain = new LinkedList<formObject>();
     }
	 
	 public ObjectNode getMessage()
     {
          return message;
     }
	 public void setQuery( double[] query)
	    {
	         this.query = query;
	    }
		 
	 public double[] getQuery(){
			 return query;
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
