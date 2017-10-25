package actors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static akka.pattern.Patterns.ask;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.mvc.*;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.util.Timeout;
import static akka.dispatch.Futures.future;
import static java.util.concurrent.TimeUnit.SECONDS;
import ch.qos.logback.core.net.SyslogOutputStream;
import controllers.formObject;
import play.libs.Json;
import referral_helper.QueryGenerator;
import scala.compat.java8.FutureConverters;
import scala.Int;
import scala.concurrent.Await;
import scala.concurrent.Future;
import java.util.concurrent.CompletableFuture;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public class QueryHelperActor extends AbstractActor{
	  static TreeSet<String> ts;
	  static ActorSystem system;
	 //float[] expertise;
	 //float[] needs;
	 //List<formObject> neighbors;
	 //List<formObject> acquaintance;
	
	public static Props getProps(TreeSet<String> ts, ActorSystem system) {
        return Props.create(QueryHelperActor.class, () -> new QueryHelperActor(ts,system) );
    }
	public QueryHelperActor(TreeSet<String> ts, ActorSystem system){
		this.ts = ts;
		this.system = system;
		System.out.println("Initialized QueryHelper Actor");
	}
	public Receive createReceive() {
		System.out.println("Called QueryHelper Actor");
		return receiveBuilder()
		.match(GenerateQueryMessage.class, message -> {
			ObjectNode msg = Json.newObject();
			System.out.println("Actor QueryHelper called");
			System.out.println("Actor size: " + ts.size());
			QueryGenerator q = QueryGenerator.getInstance();
			for(String s:ts){
				ActorRef ref = FutureConverters
						.toJava(system.actorSelection("akka://application/user/"+ s).resolveOne(new Timeout(1, TimeUnit.MINUTES)))
						.thenApply(result -> (ActorRef) result).toCompletableFuture().get();
				System.out.println("Called Actor: "+ ref.path().toString());
				generateAndFireQuery(q,s,ref);
			}
			System.out.println("Done with queries");
			getSender().tell("Done",self());
	    	
			
	  }).build();
	}

	private String generateAndFireQuery(QueryGenerator q, String s, ActorRef ref){
    	ObjectNode message = Json.newObject();
    	Timeout timeout = new Timeout(100, TimeUnit.SECONDS);
    	//double[] query = q.genQuery(s,ref.fObject.needs);
    	message.put("query", s);
    	message.put("action", "autoQuery");
    	QueryMessage qm = new QueryMessage(message);
    	qm.setRef(self());
    	//System.out.println(qm.getRef());
//    	FutureConverters.toJava(ask(ref, new QueryMessage(message), 1000))
//                .thenApply(response -> play.mvc.Results.ok((String) response));
    	ref.tell(qm,self());// akka.pattern.Patterns.ask(ref, qm,100000);
    	String resultTrans="";
    	try{
    	//resultTrans =Await.result(confirmCompletion, timeout.duration()).toString();
    	System.out.println("Waiting for Actor: "+ ref.path().toString());
    	//System.out.println("call made with await?");
    	return "";//***************"+ resultTrans + "******************";
		}
		catch(Exception e){
			System.out.println("Exception from 25 calls" + e.getMessage() + e.getStackTrace()[0].getLineNumber());// + " " +e.getStackTrace().getLineNumber());
			for(StackTraceElement st:e.getStackTrace()){
				System.out.println("Line: " +st.getLineNumber()+" Class: " +st.	getClassName());
			}
			return "";//********************************************Over**********************";
		}
	}
}
