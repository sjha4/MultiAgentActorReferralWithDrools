package controllers;

import static akka.pattern.Patterns.ask;
import akka.util.Timeout;
import drools.*;
import akka.pattern.Patterns;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.util.concurrent.*;
import java.util.concurrent.CompletionStage;
import java.io.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import java.util.TreeSet;
import java.util.concurrent.*;
import actors.*;
import javax.inject.*;

import java.util.Date;
import java.util.Iterator;
//import org.drools.compiler.kie.builder.impl.KieServicesImpl;
//import org.kie.api.KieServices;
//import org.kie.api.runtime.KieContainer;
//import org.kie.api.runtime.KieSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.*;
import java.util.LinkedList;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import play.libs.Json;
import play.mvc.*;
import referral_helper.*;
import play.mvc.Results;
import scala.compat.java8.FutureConverters;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
//import controllers.formObject;
import referral_helper.DroolHelper;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
//@Singleton
public class HomeController extends Controller {
	ActorSystem system;
	LinkedList<formObject> myObjects;
//	@Inject
//    DroolsPlugin drools1;

	//DroolHelper drools=new DroolHelper();;
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
	 @Inject 
	    public HomeController (ActorSystem system) throws IOException{ 
		 this.system = system;
		 System.out.println("Here in Home COntroller");
		 //drools = 
		 Path newFilePath = Paths.get("logs/info.log");
//		 BufferedWriter writer = new BufferedWriter(new FileWriter("app/resources/message.txt"));
//		 writer.write("Here in Home COntroller");
//		 writer.close();
Files.deleteIfExists(newFilePath);
//
			Files.createFile(newFilePath);
		 	
	    }
	    
    public Result index() {
        return ok(views.html.index.render());
    }
    
    public CompletionStage<Result> post_graph() throws Exception{
    	
    	System.out.println("Started");
    	//DroolHelper.droolMessage("Every Actor","Send","post_graph");
    	
    	JsonNode json = request().body().asJson();
//    	while(json.hasNext()){
//    		
//    	}
    	DroolHelper.droolMessage("Every Actor","Send","post_graph");
    	myObjects = new LinkedList<>();
    	TreeSet<String> actorsName = new TreeSet<>();
//      	ObjectMapper mapper = new ObjectMapper();
//      	myObjects = mapper.readValue(json.toString(), new TypeReference<LinkedList<formObject>>(){});
    	Iterator<JsonNode> it = json.elements();
    	while (it.hasNext()) {
            //JsonNode actor = it.next().get("neighbors").get(1);
            JsonNode actor = it.next();
            String acName = actor.get("name").toString();
            String actorName = acName.substring(1, acName.length() - 1);
            double[] expertise = new double[actor.get("expertise").size()];
            double[] needs = new double[actor.get("needs").size()];
            ArrayList<formObject> acquaintances = new ArrayList();


            System.out.println("\nActor Name Hi=" + actorName);
            for (int i = 0; i < actor.get("expertise").size(); i++) {
                expertise[i] = Double.parseDouble(actor.get("expertise").get(i).toString());
                System.out.print(" " + expertise[i]);
            }
            System.out.println();
            for (int i = 0; i < actor.get("needs").size(); i++) {
                needs[i] = Double.parseDouble(actor.get("needs").get(i).toString());
                System.out.print(" " + needs[i]);
            }
			
            if (actor.get("neighbors") != null) {
                //acquaintances = new Acquaintance[actor.get("neighbors").size()];
                for (int i = 0; i < actor.get("neighbors").size(); i++) {
                    String neighborName = actor.get("neighbors").get(i).get("name").toString();
                    neighborName = neighborName.substring(1, neighborName.length() - 1);
                    int neighborExpertiseCount = actor.get("neighbors").get(i).get("expertise").size();
                    int neighborSociabiltyCount = actor.get("neighbors").get(i).get("sociability").size();
                    double[] neighborExpertise = new double[neighborExpertiseCount];
                    double[] neighborSociability = new double[neighborSociabiltyCount];
                    for (int j = 0; j < neighborExpertiseCount; j++) {
                        neighborExpertise[j] = actor.get("neighbors").get(i).get("expertise").get(j).asDouble();
                    }

                    for (int j = 0; j < neighborSociabiltyCount; j++)
                        neighborSociability[j] = actor.get("neighbors").get(i).get("sociability").get(j).asDouble();
                    acquaintances.add(new formObject(neighborName, neighborExpertise, neighborSociability));
                }
				formObject actorref = new formObject(actorName,expertise,needs,acquaintances);
				myObjects.add(actorref);
            }
    	}
			
    	System.out.println("Back to home before string called!");
      	for(formObject o: myObjects){
    	  System.out.println("Object details [i]:");
    	 System.out.println("*********");
    	  System.out.println(o.name);
    	  for(formObject obj:o.neighbors)
    	  System.out.println(obj.name);
    	  System.out.println("##########");
    	  if(o.needs!=null){
    		  ActorRef ref = system.actorOf(ReferralActor.getProps(o,myObjects), o.name);
    		  actorsName.add(o.name);
    		  System.out.println("*********" + o.name +o.neighbors);
    	  }
      }
      
      String name = json.toString();
      ActorRef ref = system.actorOf(QueryHelperActor.getProps(actorsName,system));
      ObjectNode message = Json.newObject();
  		message.put("action", "dump_states");
  		return  FutureConverters.toJava(ask(ref, new GenerateQueryMessage(message), 10000))
              .thenApply(response -> ok((String) response));
  		//System.out.println("Done!!!");
  		//return rf;//CompletableFuture.completedFuture(ok("{Status: Error},"
				//+ "{message: "));
    	
    }
    public Result dump_states(String actor) throws Exception{
    	Timeout timeout = new Timeout(2, TimeUnit.SECONDS);
    	System.out.println("Done!!!");
    	DroolHelper.droolMessage(actor,"Send","dump_states");
    	ActorRef ref = null;
    	try{
    		 ref = FutureConverters
				.toJava(system.actorSelection("akka://application/user/"+ actor).resolveOne(new Timeout(1, TimeUnit.MINUTES)))
				.thenApply(result -> (ActorRef) result).toCompletableFuture().get();
    		 System.out.println("------------"+ref.path().toString());
    	}
    	catch(Exception e){
    		//System.out.println("------------Error?"+ref.path().toString()); 
    		return (ok("{Status: Error},"
    				+ "{message: "+e.getMessage()+" }"));
    		 
    	}
    	ObjectNode message = Json.newObject();
    	message.put("action", "dump_states");
    	//DroolHelper.droolMessage("Every Actor","Send","post_graph");
    	//droolMessage(actor,"Recieved","dump_states");
    	ObjectNode responseJson = Json.newObject();
    	responseJson.put("success", "success");
    	//responseJson.put("Message","messageResponse");
    	//Patterns.ask(bookingActor, new BookingActorProtocol.Operators(),timeout);
    	Future<Object> askOperatorts = akka.pattern.Patterns.ask(ref, new DumpStateMessage(message), new Timeout(1, TimeUnit.MINUTES));
    	try{
    		responseJson.put("Message",Json.toJson(Await.result(askOperatorts,timeout.duration())));       //.thenApply(response -> ok((play.libs.Json)(response)));
    	}
    	catch(Exception e){
    		return (ok("{Status: Error},"
    				+ "{message:"+ "Timeout }"));
    	}
    	//droolMessage(actor,"Recieved","dump_states");
    	return ok(responseJson);
    	
    }
    public CompletionStage<Result> postGraph(ObjectNode graph){
    	ObjectNode message = graph;
    	//System.out.println("Graph:" + message.toString());
//    	if(airline.equals("AA"))
//    		op = AActor;
//    	else if(airline.equals("BA"))
//    		op = BActor;
//    	else if(airline.equals("CA"))
//    		op = CActor;
//    	return FutureConverters.toJava(ask(op, new DebugMessage(message), 10000))
//                .thenApply(response -> resp((String) response));
    	return null;
    }
    public Result queryActor(String actor,String query) throws Exception{
    	Timeout timeout = new Timeout(10, TimeUnit.SECONDS);
    	//Path newFilePath = Paths.get("app/resources/message.txt");
//		 BufferedWriter writer = new BufferedWriter(new FileWriter("app/resources/message.txt"));
//		 writer.write("Here in Home COntroller");
//		 writer.close();
		 //Files.deleteIfExists(newFilePath);
//
		//Files.createFile(newFilePath);
    	//givenWritingStringToFile_whenUsingPrintWriter_thenCorrect();
    	ActorRef ref = null;
    	try{
   		 ref = FutureConverters
				.toJava(system.actorSelection("akka://application/user/"+ actor).resolveOne(new Timeout(10, TimeUnit.SECONDS)))
				.thenApply(result -> (ActorRef) result).toCompletableFuture().get();
   		 System.out.println("------------"+ref.path().toString());
   	}
   	catch(Exception e){
   		//System.out.println("------------Error?"+ref.path().toString()); 
   		return (ok("{Status: Error},"
   				+ "{message: "+e.getMessage()+" }"));
   		 
   	}
    	double[] needsArr = new ObjectMapper().readValue("[" + query + "]", double[].class);
    	ObjectNode message1 = Json.newObject();
    	message1.put("query", "Home");
		message1.put("action", "manualQuery");
		message1.put("initiator", "Home");
    	HomeMessage qm = new HomeMessage(message1);
    	qm.setQuery(needsArr);
    	//qm.setRef(self());
    	ObjectNode responseJson = play.libs.Json.newObject();
    	
    	//responseJson.put("Message","messageResponse");
    	
    		Future<Object> askOperatorts = akka.pattern.Patterns.ask(ref, qm, 10000);
//    		CompletionStage<Result> rf = FutureConverters.toJava(ask(ref, qm, 1000))
//    				.exceptionally(throwable -> {
//    	                   System.out.println("Unrecoverable error");
//    	                   return CompletableFuture.completedFuture(ok(Json.toJson("{Status: Error},"
//    	           				+ "{message:  No Result found}")));
//    	               })
//               .thenApply(response -> ok((String)(response)));
    		
    		DroolHelper.droolMessage(actor,"send_result","query_actor");
    		try{
    			responseJson.put("Success","Success");
        		//responseJson.put("Message",(String)(Await.result(askOperatorts,timeout.duration())));
        		String response = (String)(Await.result(askOperatorts,timeout.duration()));
    			//.thenApply(response -> ok((play.libs.Json)(response)));
        		return ok(response);
        	}
        	catch(Exception e){
        		return (ok("{Status: Error},"
        				+ "{message:"+ "No result from query }"));
        	}	
    		//return rf;
//    		responseJson.put("Timeout", "Error");
//    		responseJson.put("Message", "No result from query");
//    		return ok(responseJson);
        	
    }
    
//    public void droolMessage(String actor,String type, String message1){
//
//    	KieServices kieServices = new KieServicesImpl();;
//    	KieContainer kc = kieServices.getKieClasspathContainer(HomeController.class.getClassLoader());
//    	KieSession kieSession =  kc.newKieSession("ReferraldKS");
//    	Message message = new Message(new Date(), actor, type, message1);
//
//	kieSession.insert(message);
//
//	kieSession.fireAllRules();
//
//	kieSession.dispose();
//    }
//    
//    public void givenWritingStringToFile_whenUsingPrintWriter_thenCorrect() 
//    		  throws IOException {
////    		    FileWriter fileWriter = new FileWriter("resources/message.txt");
////    		    PrintWriter printWriter = new PrintWriter(fileWriter);
////    		    printWriter.print("Some String");
////    		    printWriter.printf("Product name is %s and its price is %d $", "iPhone", 1000);
////    		    printWriter.close();
////    		    
//    			String str = "World";
//    		    BufferedWriter writer = new BufferedWriter(new FileWriter("app/resources/message.txt", true));
//    		    writer.append(' ');
//    		    writer.append(str);
//    		     
//    		    writer.close();
//    		}
    public Result getMessage(){
    	Path newFilePath = Paths.get("logs/info.log");

		return ok(new File(newFilePath.toString()));
    }
}
