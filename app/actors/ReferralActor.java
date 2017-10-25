package actors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import static akka.pattern.Patterns.ask;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import scala.compat.java8.FutureConverters;
import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import ch.qos.logback.core.net.SyslogOutputStream;
import controllers.formObject;
import play.libs.Json;
import referral_helper.*;
import akka.util.Timeout;
import static akka.dispatch.Futures.future;
import static java.util.concurrent.TimeUnit.SECONDS;
import scala.compat.java8.FutureConverters;
import scala.Int;
import scala.concurrent.Await;
import scala.concurrent.Future;
import java.util.concurrent.CompletableFuture;
import java.util.List;
import java.util.*;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.LinkedList;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
//import drools.*;
import java.util.Date;
//import org.drools.compiler.kie.builder.impl.KieServicesImpl;
//import org.kie.api.KieServices;
//import org.kie.api.runtime.KieContainer;
//import org.kie.api.runtime.KieSession;
import java.io.File;
import java.io.IOException;
import referral_helper.DroolHelper;
import javax.inject.Inject;
import javax.inject.Singleton;
import play.Environment;
import play.inject.ApplicationLifecycle;



public class ReferralActor extends AbstractActor{
	 public final String name;
	 public final formObject fObject;
	 private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	 
	 //@Inject
	 
	//	DroolHelper droolHelper = new DroolHelper();
	 //public final DroolHelper dh;
	 public final LinkedList<formObject> allfObjects;
	 QueryGenerator q = QueryGenerator.getInstance();
	 LinkedList<formObject> referralChain;
	 //float[] expertise;
	 //float[] needs;
	 //List<formObject> neighbors;
	 //List<formObject> acquaintance;
	
	public static Props getProps(formObject fo,LinkedList<formObject> allfObjects) {
		System.out.println("Called getProps");
        return Props.create(ReferralActor.class, () -> new ReferralActor(fo,allfObjects) );
    }
	public ReferralActor(formObject o,LinkedList<formObject> allfObjects){
		this.name = o.name;
		this.fObject = o;
		System.out.println("Constructor Called");
		//this.dh = dh;//
		//DroolHelper drools;
		this.referralChain = new LinkedList<>();
		this.allfObjects = new LinkedList<>();
		System.out.println("All fobjects");
		for(formObject of: allfObjects){
			if(of.needs!=null){
				System.out.println("Initialize " + name + " fobject list with Actor: "+of.name);
				this.allfObjects.add(of);
			}
		}
		//System.out.println("Initialized Actor");
	}
	public Receive createReceive() {
		
		return receiveBuilder()
		.match(DumpStateMessage.class, message -> {
			DroolHelper.droolMessage(fObject.name,"Receive","dump_states");
			//log.error("Starting Trip Booking");
			ObjectNode msg = Json.newObject();
			ObjectNode responseJson1 =Json.newObject();
	    	//responseJson1.put("success", "success");
	    	//responseJson1.put("Message","messageResponse");
			ObjectNode responseJson = play.libs.Json.newObject();
	    	//responseJson.put("success", "success");
	    	//responseJson.put("Message","messageResponse");
			System.out.println("Actor :" + fObject.name + " called");
			TreeMap<Double,formObject> tm = new TreeMap<>();
			tm = sortNeighbourList(new double[]{1.0,1.0,1.0,1.0},true);
			int neigSize = Utils.getMaxNumOfNeighbors();
			fObject.neighbors.clear();
			while(tm.size()!=0 && neigSize!=0){
				System.out.println("-------Update neighbors-----TM Size: "+tm.size()+" NeigSize: "+neigSize);
				Map.Entry<Double,formObject> entry = tm.pollLastEntry();
				System.out.println("Added neighbor: "+ entry.getValue().name); 
				fObject.neighbors.add(entry.getValue());
				neigSize--;
			}
			//Message messageDr = new Message(new Date(), fObject.name,"Receive","Dump_state message");
			//DroolHelper dh = new DroolHelper();
			//dh.droolMessage(fObject.name,"Receive","Dump_state message");
			responseJson1.put("name",fObject.name);
			responseJson1.put("Expertise",java.util.Arrays.toString(fObject.expertise));
			//responseJson1.put("acquaintance","acq");
			
			String neig = "";
			String acq = ""; 
			
			ArrayList<ObjectNode> neigArray = new ArrayList();
			ArrayList<ObjectNode> acqArray = new ArrayList();
			for(formObject of: fObject.neighbors){
				ObjectNode formDetailsJson = Json.newObject();
				formDetailsJson.put("name",of.name);
				formDetailsJson.put("Expertise","["+of.expertise[0]+","+of.expertise[1]+","+of.expertise[2]+","+of.expertise[3]+"]");
				formDetailsJson.put("Sociability","["+of.sociability[0]+","+of.sociability[1]+","+of.sociability[2]+","+of.sociability[3]+"]");
				neigArray.add(formDetailsJson);
				System.out.println("Actor Dump :" + fObject.name + " called and size: "+fObject.neighbors.size());
				if(of.expertise!=null && of.sociability!=null)
				neig += of.name +" /nExpertise: {" +of.expertise.toString()+"}/nSocibility: { "+ of.sociability.toString()+"/n";
			}
			for(formObject of: fObject.acquaintance){
				ObjectNode formDetailsJson = Json.newObject();
				formDetailsJson.put("name",of.name);
				formDetailsJson.put("Expertise","["+of.expertise[0]+","+of.expertise[1]+","+of.expertise[2]+","+of.expertise[3]+"]");
				formDetailsJson.put("Sociability","["+of.sociability[0]+","+of.sociability[1]+","+of.sociability[2]+","+of.sociability[3]+"]");
				acqArray.add(formDetailsJson);
				System.out.println("Actor :" + fObject.name + " called 2");
//				if(of.expertise!=null && of1.sociability!=null)
//				acq += of1.name +" Expertise: {" +String.valueOf(of1.expertise)+"}/nSocibility: { "+ of1.sociability.toString()+"/n";
			}
			System.out.println("Actor :" + fObject.name + " called3");
			//ObjectMapper mapper = new ObjectMapper();
			//ArrayNode array = mapper.valueToTree(neigArray);
			//ObjectNode[] neighborA = neigArray.toArray(new ObjectNode[neigArray.size()]);
			responseJson1.putArray("Neighbors").addAll(neigArray);
			responseJson1.putArray("Acquaintance").addAll(acqArray);
			msg.put("neighbors", neig);
	    	msg.put("name", name);
	    	msg.put("acquaintance", acq);
	    	System.out.println("Actor :" + fObject.name + " called 4");
	    	DroolHelper.droolMessage(fObject.name,"Reply","Response of dump_states");
	    	getSender().tell(responseJson1,self());
	    	
			
	  })
		.match(HomeMessage.class, message ->{
			DroolHelper.droolMessage(fObject.name,"Receive","Query from Web/Mobile!");
			Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
			ObjectNode message1 = Json.newObject();
	    	//double[] query = q.genQuery(s,ref.fObject.needs);
			message1.put("query", name);
			message1.put("action", "manualQuery");
			message1.put("initiator", "Actor");
	    	QueryMessage qm = new QueryMessage(message1);
	    	qm.setQuery(message.getQuery());
	    	qm.setRef(sender());
	    	//System.out.println(qm.getRef().toString());
	    	//FutureConverters.toJava(ask(self(), qm, 1000));
	    	//sender().tell("Error",self());
	    	self().tell(qm,self());
//	    	Future<Object> askRequest = akka.pattern.Patterns.ask(self(), qm,timeout);
//	    	String resC = (String) Await.result(askRequest, timeout.duration());
        	//sender().tell(resC,self());
			
		})
		.match(QueryMessage.class, message -> {
			DroolHelper.droolMessage(fObject.name,"Receive","Query Message");
			ObjectNode responsemsg = Json.newObject();
			Timeout timeout = new Timeout(10, TimeUnit.SECONDS);
			//System.out.println("Query :" + message.getMessage().get("query").asText() + " called");
			if(message.getMessage().get("action").asText().equals("autoQuery")){
				for(int i=0;i<25;i++)
				{
					double[] query = q.genQuery(name, fObject.needs);
					ObjectNode message1 = Json.newObject();
			    	//double[] query = q.genQuery(s,ref.fObject.needs);
					message1.put("query", name);
					message1.put("action", "manualQuery");
					message1.put("initiator", "Actor");
			    	QueryMessage qm = new QueryMessage(message1);
			    	qm.setQuery(query);
			    	qm.setRef(message.getRef());
			    	System.out.println("********************NULL REF?*****" + qm.getRef().toString());
			    	//FutureConverters.toJava(ask(self(), qm, 1000));
			    	self().tell(qm,self());
		                
				}
				responsemsg.put("name", name);
				getSender().tell(responsemsg,self());
			}
			//double[] query = Double.parseDouble(message.getMessage().get("query").asText());
	    	//msg.put("acquaintance", fObject.acquaintance==null?null:fObject.acquaintance.toString());
			if(message.getMessage().get("action").asText().equals("manualQuery")){
				System.out.println("Manual query called");
				responsemsg.put("name", "name");
				double[] query = message.getQuery();
				for(double s:query){
					//System.out.println(s);
				}
				Boolean match = Utils.isExpertiseMatch(fObject.expertise,query);
				if(fObject.name.equals("A")){
					System.out.println("A match:"+ match);
					System.out.println("A Expertise:"+ java.util.Arrays.toString(fObject.expertise));
					System.out.println("Query:"+ java.util.Arrays.toString(query));
					
				}
				if(match)
				{
					ObjectNode ansmessage = Json.newObject();
					ansmessage.put("answer",name + " : " + Utils.genAnswer(fObject.expertise,query).toString());
					DroolHelper.droolMessage(fObject.name,"Got Answer","Sending Answer");
					
					System.out.println("***********ANSWER BY**********"+ fObject.name);
					AnswerMessage am = new AnswerMessage(ansmessage);
					am.setReferral(message.getReferral());
					am.addReferral(fObject);
					am.setQuery(message.getQuery());
					am.setRef(message.getRef());
					//System.out.println("********************NULL REF?*****" + message.getRef());
					am.setAnswer(Utils.genAnswer(fObject.expertise,query));
					//System.out.println("SENDING TELL");
					getSender().tell(am,self());
					//System.out.println(am.toString());
					//System.out.println(getSender().toString());
					//responsemsg.put("Result",Utils.genAnswer(fObject.expertise,query).toString());
				}
				else{
					System.out.println("***********IN ELSE**********");
					TreeMap<Double,formObject> tm = new TreeMap<>();
					tm = sortNeighbourList(query,false);
					if(tm==null || tm.size()==0){
						//System.out.println("Empty Neighbour List");
						getSender().tell("Empty",self());
					}
					else{
						Boolean ans = false;
						System.out.println("Neighbour chosen:" + tm.get(tm.lastKey()).name);
						ObjectNode message2 = Json.newObject();
				    	//double[] query = q.genQuery(s,ref.fObject.needs);
						message2.put("refered",tm.get(tm.lastKey()).name );
						ReferralMessage rm = new ReferralMessage(message2);
						rm.setReferral(message.getReferral());
						rm.addReferral(fObject);
						rm.setRef(message.getRef());
						rm.setQuery(message.getQuery());
//						if(message.getMessage().get("initiator").asText().equals("Home")){
//							self().tell(rm,self());
//						}
//						else
						DroolHelper.droolMessage(fObject.name,"Sending referral for :"+ java.util.Arrays.toString(message.getQuery())+" to "+ tm.get(tm.lastKey()).name,"referral_states");
						getSender().tell(rm,self());
						//System.out.println("***********IN ELSE after tell**********"+
						//" Referrs: " + tm.get(tm.lastKey()).name + " toActor: " +getSender().toString());
												
					}
				}
				getSender().tell("error",self());
			}
	    	
			
	  })
		.match(AnswerMessage.class, message -> {
			//System.out.println("##########***********IN ANSWER********** " + getSender().toString());
			System.out.println("------------------------Referral chain:");
			
			//System.out.println(message.getRef());
			DroolHelper.droolMessage(fObject.name,"Receive with chain:" + message.getRef().path().name(),"Answer Message");
			//DroolHelper.droolMessage(fObject.name,"Receive","Answer Message: "+message.getAnswer().toString());
			
			addReferred(message);
			String res="";
			for(double d:message.getAnswer())
			{
				res+=d+",";
			}
			res = res.substring(0,res.length()-1);
			System.out.println(res);
			DroolHelper.droolMessage(fObject.name,"Receive","Answer Message: "+res);
			message.printReferal();
			message.getRef().tell(res,self());
			formObject fo = null;
			String foName="";
			for(formObject of: message.getReferral()){
				System.out.println("Referral change loop: of object "+ of.name);
				foName = of.name;
			}
			System.out.println("outside Referral change loop of object "+ foName);
			if(foName!="")
			{
				System.out.println("---------------Update Expertise of --------------"+ foName);
				fo = getFormObject(foName);
				
			}
			
			if(fo!=null){
				System.out.println("---------------Update Expertise of --------------"+ fo.name);
				if(fo.expertise==null)
				{
					fo.expertise = new double[]{0.5, 0.5, 0.5, 0.5};
				}
				//fo.expertise = message.getAnswer();
				String s ="";
				for(double d1:fo.expertise){
					s+=""+d1+",";
				}
				System.out.println("Expertise before updating:"+s);
				s="";
				Utils.updateExpertise(message.getQuery(),message.getAnswer(),fo.expertise);
				for(double d1:fo.expertise){
					s+=""+d1+",";
				}
				System.out.println("Expertise after updating:"+s);
				
			}
			for(int j=message.getReferral().size()-2;j>=0;j--){
				formObject os = getFormObject(message.getReferral().get(j).name);
				int dist2Tail = message.getReferral().size() -j-1;
				if(os!=null){
					String s ="";
					for(double d1:os.sociability){
						s+=""+d1+",";
					}
					System.out.println(os.name + " Sociability before updating:"+s);
					s="";
					Utils.updateSociability(message.getQuery(),message.getAnswer(),dist2Tail,os.sociability);
					for(double d11:os.sociability){
						s+=""+d11+",";
					}
					System.out.println(os.name + "Sociability after updating:"+s);
				}
				System.out.println("ReferralChain size:"+message.getReferral().size());
			}
		})
		.match(ReferralMessage.class, message -> {
			//System.out.println("##########***********IN REFERRAL********** " + getSender().toString());
			//System.out.println("Referral chain:");
			//message.printReferal();
			//System.out.println("Refers :" + message.getMessage().get("refered").asText());
			/*
			 * Add referred to Acquaintance List
			 */
			
			
			/*
			 * Add referred to Acquaintance List
			 */
			DroolHelper.droolMessage(fObject.name,"Receive referral of: " + message.getMessage().get("refered").asText(),"Referral Message");
			ObjectNode message1 = Json.newObject();
	    	//double[] query = q.genQuery(s,ref.fObject.needs);
			//message1.put("query", name);
			message1.put("action", "manualQuery");
			message1.put("initiator", "Actor");
	    	QueryMessage qm = new QueryMessage(message1);
	    	qm.setQuery(message.getQuery());
	    	qm.setReferral(message.getReferral());
	    	qm.setRef(message.getRef());
	    	try
	    	{
	    		//System.out.println("_____________Sending ref query to "+ message.getMessage().get("refered").asText());
	    		ActorRef ref = FutureConverters
					.toJava(getContext().getSystem().actorSelection("akka://application/user/"+ message.getMessage().get("refered").asText()).resolveOne(new Timeout(1, TimeUnit.MINUTES)))
					.thenApply(result -> (ActorRef) result).toCompletableFuture().get();
	    		if(ref!=self())//&& !message.getReferral().contains(message.getMessage().get("refered").asText()))
				{
	    			//System.out.println("Success!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	    			ref.tell(qm,self());
				}
	    		else{
	    			message.getRef().tell("No results",self());
	    			
	    		}
	    	}
	    	catch(Exception e)
	    	{
	    		//System.out.println("Couldn't find Actor ref from reference");
	    		getSender().tell("Error", self());
	    	}
				
		})
		.build();
	}
	
	private void addReferred(AnswerMessage message){
		System.out.println("In add referred: acq size"+ fObject.name);
		message.printReferal();
		
		//formObject fo = getFormObject(message.getMessage().get("refered").asText());
		if(fObject.acquaintance==null) fObject.acquaintance = new LinkedList<formObject>();
//		if(fObject!=null && fObject.acquaintance!=null 
//				&& !fObject.acquaintance.contains(fo) && fo!=null)
//			{fObject.acquaintance.add(fo);
//			System.out.println("-------Added acquaintance-----"+fo.name);
//			}
		for(formObject of:message.getReferral()){
			Boolean present = false;
			System.out.println("Trying to add: "+of.name);
			formObject af = getFormObject(of.name);
			for(formObject ofcheck:fObject.acquaintance){
				if(af==null || ofcheck.name.equals(af.name)) 
					present = true;
			}
			for(formObject ofcheck:fObject.neighbors){
				if(af==null || ofcheck.name.equals(af.name)) 
					present = true;
			}
			
			System.out.println(of.name +"Present? " + present);
			if((fObject.acquaintance==null) || (!fObject.acquaintance.contains(af) && af!=null && !present))
			{
				if (fObject.acquaintance==null){
					fObject.acquaintance = new LinkedList<formObject>();
					}
				
				if(!fObject.acquaintance.contains(af) && !present){ 
					fObject.acquaintance.add(af);
					System.out.println("-------Added acquaintance-----"+of.name);
				}
				else{
					
					System.out.println("-------Already in acquaintance-----"+of.name);
				}
				
			}
		}
		TreeMap<Double,formObject> tm = new TreeMap<>();
		tm = sortNeighbourList(new double[]{1.0,1.0,1.0,1.0},true);
		int neigSize = Utils.getMaxNumOfNeighbors();
		fObject.neighbors.clear();
		while(tm.size()!=0 && neigSize!=0){
			System.out.println("-------Update neighbors-----");
			Map.Entry<Double,formObject> entry = tm.pollLastEntry();
			fObject.neighbors.add(entry.getValue());
			neigSize--;
		}
		
		
		
	}
	private TreeMap<Double,formObject> sortNeighbourList(double[] query,Boolean dummy){
		double w = Utils.getWeightOfSociability();
		TreeMap<Double,formObject> treeMap = new TreeMap<>();
		Boolean match = false;
		TreeSet<formObject> neigAcq = new TreeSet<>(new formObjectComparator());
		if(fObject.neighbors!=null)
			neigAcq.addAll(fObject.neighbors);
		if(fObject.acquaintance!=null && dummy)
			neigAcq.addAll(fObject.acquaintance);
		System.out.println("NeigAcquaintance List of Actor:" + fObject.name);
		for(formObject f: neigAcq){
			System.out.println(f.name + " :AcqOf: "+fObject.name);
			match = Utils.isExpertiseMatch(f.expertise,query)||Utils.isExpertiseMatch(f.sociability,query) ;
			if(match || dummy){
				//Replace with list of objects and comparator
				System.out.println("Put value;");
				double val = w*innerProd(query,f.sociability) + (1-w)*innerProd(query,f.expertise);
				while(treeMap.containsKey(val))
					val+=0.00001;
				treeMap.put(val,f);
			}
		}
		return treeMap;
	}
	
	private double innerProd(double[] query,double[] score){
		double res = 0;
		for(int i=0;i<query.length;i++){
			//System.out.println("innerProd Variable: "+ i);
			res+= query[i]*score[i];
		}
		return res;
	}
	
	private formObject getFormObject(String name){
		if(name.equals(fObject.name)) return null;
		for(formObject of:fObject.acquaintance){
			if(name.equals(of.name)&& of.needs==null)
				return of;
		}
		for(formObject of:fObject.neighbors){
			if(name.equals(of.name)&& of.needs==null)
				return of;
		}
		formObject fo = new formObject();
		fo.name = name;
		fo.expertise = new double[]{0.5, 0.5, 0.5, 0.5};
		fo.sociability = new double[]{0.5, 0.5, 0.5, 0.5};
		return fo;
		
	}
	 
}
class formObjectComparator implements Comparator<formObject>{
	 
    @Override
    public int compare(formObject e1, formObject e2) {
        if((e1.needs!=null && e2.needs!=null)||(e1.needs==null && e2.needs==null))
        	return e1.name.compareTo(e2.name);
        else return -1;
    }
}   
