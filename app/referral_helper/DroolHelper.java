package referral_helper;
import javax.inject.*;
import java.util.Date;
//import org.drools.logger.KnowledgeRuntimeLogger;
//import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.compiler.kie.builder.impl.KieServicesImpl;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.*;

//import org.apache.commons.logging.impl.Log4JLogger;
//import org.apache.log4j.Logger.*;
import org.apache.logging.log4j.*;

import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
//import org.kie.api.event.rule.DebugWorkingMemoryEventListener;


import drools.*;
//import org.drools.logger;
import org.drools.compiler.kie.builder.impl.KieServicesImpl;
import play.Environment;
import org.kie.api.logger.*;
import play.inject.ApplicationLifecycle;
import play.libs.F;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.kie.api.logger.KieRuntimeLogger;
import org.apache.logging.log4j.LogManager;
//import org.kie.api.logger.KnowledgeRuntimeLogger; 
//import org.kie.api.logger.KnowledgeRuntimeLoggerFactory; 
//import org.kie.internal.logger.KnowledgeRuntimeLogger;
//import org.kie.internal.logger.KnowledgeRuntimeLoggerFactory;
//import org.kie.internal.logger.KnowledgeRuntimeLogger;

public class DroolHelper {
	private static final Logger log = LogManager.getLogger(DroolHelper.class.getName());
	//final static Logger logger = Logger.getLogger(HelloExample.class);
	public DroolHelper()
	{
		
	}
	
	public static void droolMessage(String actor,String type, String message1){
		//log.error("Drool Logging?");
		KieServices kieServices = new KieServicesImpl();;
		KieContainer kc = kieServices.getKieClasspathContainer(DroolHelper.class.getClassLoader());
		//Logger log1 = LogManager.getLogger(DroolHelper.class.getName()+"xyz");
		KieSession kieSession =  kc.newKieSession("ReferraldKS1");
		kieSession.setGlobal("logger", log);
		kieSession.addEventListener( new DebugAgendaEventListener() );
//
		kieSession.addEventListener( new DebugRuleRuntimeEventListener() );
    	Message message = new Message(new Date(), actor, type, message1);
    	//KieRuntimeLogger logger = kieServices.getLoggers().newFileLogger(kieSession, "audit");
//    	KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "log/helloworld");

    	kieSession.insert(message);

    	kieSession.fireAllRules();
    	//log1.close();
    	
    	//log.error("Drool Logging?");
    	kieSession.dispose();
    }
    
    
}
