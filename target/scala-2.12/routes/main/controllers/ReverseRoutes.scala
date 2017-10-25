
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/samir/Desktop/3rd sem/SOC/P3/referral/conf/routes
// @DATE:Tue Oct 24 19:59:26 EDT 2017

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers {

  // @LINE:6
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:11
    def post_graph(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "graph")
    }
  
    // @LINE:13
    def dump_states(actor:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("actor", actor)) + "/dump_states")
    }
  
    // @LINE:17
    def getMessage(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "messages")
    }
  
    // @LINE:15
    def queryActor(actor:String, query:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("actor", actor)) + "/query/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("query", query)))
    }
  
    // @LINE:6
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
  }

  // @LINE:9
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:9
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }


}
