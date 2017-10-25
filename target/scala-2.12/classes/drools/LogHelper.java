package drools;

import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import akka.actor.AbstractActor;


public class LogHelper {
	
	 public static void logMessage(){
		 final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), "Drools");
		 log.error("Starting Trip Booking");
	 }
		
}
