package controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import actors.*;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
public class formObject{
	public String name;
	public double[] expertise;
	public double[] needs;
	public double[] sociability;
	public LinkedList<formObject> neighbors;
	public LinkedList<formObject> acquaintance;
	public formObject(String name,double[] expertise,double[] sociability){
		this.name = name;
		this.expertise = expertise;
		this.sociability = sociability;
		this.neighbors = new LinkedList<>();
		this.acquaintance = new LinkedList<>();
		
	}
	public formObject(String name,double[] expertise,double[] needs,ArrayList<formObject> acquaintances){
		this.name = name;
		this.expertise = expertise;
		this.needs = needs;
		this.neighbors = new LinkedList<>();
		this.acquaintance = new LinkedList<>();
		for(formObject fo1:acquaintances){
			this.neighbors.add(fo1);
		}
		
		
	}
	public String toString(){
		
////		if(needs!=null)
////			system.actorOf(ReferralActor.getProps(this),name);
//		String res = name;
//		//System.out.println(name);
//		//System.out.print("expertise: ");
//		for(double f:expertise){
//  		  //System.out.print(f+",");
//  	  }
//		//System.out.println();
//		//System.out.print("Needs: ");
//  	  if(needs!=null){for(double f:needs){
//  		  //System.out.print(f+",");
//  	  }}
//  	System.out.println();
//	System.out.print("Neighbours: ");
//	if(neighbors!=null){
//  	  for(formObject f: neighbors){
//  		System.out.println("Neighbour[i]: ");
//  		System.out.println(f.name);
//  		System.out.println();
//  		System.out.print("Neighbour expertise: ");
//  		for(double f1:f.expertise){
//    		  System.out.print(f1+",");
//    	  }
//  		System.out.print("Neighbour sociability: ");
//  		for(double f1:f.sociability){
//  		  System.out.print(f1+",");
//  	  }
//  		
//  	  }
//	}
		return "";
	}
	public formObject(){
		System.out.println("Initialized formObject");
		
		//System.out.println(this.name);
	}
/*	public ActorRef ActorCreation(ActorSystem system,formObject fo){
		System.out.println("In actorCreation: ");
		System.out.println("This: "+ fo);
		System.out.println("System: "+system);
		System.out.println("this.name: "+fo.name);
		ActorRef ref = system.actorOf(ReferralActor.getProps(fo), fo.name);
		System.out.print("Path: "+ ref.path().toString());
		return ref;
	}*/
	
}
