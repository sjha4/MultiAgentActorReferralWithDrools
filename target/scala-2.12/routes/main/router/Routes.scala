
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/samir/Desktop/3rd sem/SOC/P3/referral/conf/routes
// @DATE:Tue Oct 24 19:59:26 EDT 2017

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  HomeController_0: controllers.HomeController,
  // @LINE:9
  Assets_1: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    HomeController_0: controllers.HomeController,
    // @LINE:9
    Assets_1: controllers.Assets
  ) = this(errorHandler, HomeController_0, Assets_1, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_0, Assets_1, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """graph""", """controllers.HomeController.post_graph()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """""" + "$" + """actor<[^/]+>/dump_states""", """controllers.HomeController.dump_states(actor:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """""" + "$" + """actor<[^/]+>/query/""" + "$" + """query<[^/]+>""", """controllers.HomeController.queryActor(actor:String, query:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """messages""", """controllers.HomeController.getMessage()"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_HomeController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index0_invoker = createInvoker(
    HomeController_0.index,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """ An example controller showing a sample home page""",
      Seq()
    )
  )

  // @LINE:9
  private[this] lazy val controllers_Assets_versioned1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned1_invoker = createInvoker(
    Assets_1.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )

  // @LINE:11
  private[this] lazy val controllers_HomeController_post_graph2_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("graph")))
  )
  private[this] lazy val controllers_HomeController_post_graph2_invoker = createInvoker(
    HomeController_0.post_graph(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "post_graph",
      Nil,
      "POST",
      this.prefix + """graph""",
      """""",
      Seq()
    )
  )

  // @LINE:13
  private[this] lazy val controllers_HomeController_dump_states3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), DynamicPart("actor", """[^/]+""",true), StaticPart("/dump_states")))
  )
  private[this] lazy val controllers_HomeController_dump_states3_invoker = createInvoker(
    HomeController_0.dump_states(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "dump_states",
      Seq(classOf[String]),
      "GET",
      this.prefix + """""" + "$" + """actor<[^/]+>/dump_states""",
      """""",
      Seq()
    )
  )

  // @LINE:15
  private[this] lazy val controllers_HomeController_queryActor4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), DynamicPart("actor", """[^/]+""",true), StaticPart("/query/"), DynamicPart("query", """[^/]+""",true)))
  )
  private[this] lazy val controllers_HomeController_queryActor4_invoker = createInvoker(
    HomeController_0.queryActor(fakeValue[String], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "queryActor",
      Seq(classOf[String], classOf[String]),
      "GET",
      this.prefix + """""" + "$" + """actor<[^/]+>/query/""" + "$" + """query<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:17
  private[this] lazy val controllers_HomeController_getMessage5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("messages")))
  )
  private[this] lazy val controllers_HomeController_getMessage5_invoker = createInvoker(
    HomeController_0.getMessage(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "getMessage",
      Nil,
      "GET",
      this.prefix + """messages""",
      """""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_HomeController_index0_route(params@_) =>
      call { 
        controllers_HomeController_index0_invoker.call(HomeController_0.index)
      }
  
    // @LINE:9
    case controllers_Assets_versioned1_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned1_invoker.call(Assets_1.versioned(path, file))
      }
  
    // @LINE:11
    case controllers_HomeController_post_graph2_route(params@_) =>
      call { 
        controllers_HomeController_post_graph2_invoker.call(HomeController_0.post_graph())
      }
  
    // @LINE:13
    case controllers_HomeController_dump_states3_route(params@_) =>
      call(params.fromPath[String]("actor", None)) { (actor) =>
        controllers_HomeController_dump_states3_invoker.call(HomeController_0.dump_states(actor))
      }
  
    // @LINE:15
    case controllers_HomeController_queryActor4_route(params@_) =>
      call(params.fromPath[String]("actor", None), params.fromPath[String]("query", None)) { (actor, query) =>
        controllers_HomeController_queryActor4_invoker.call(HomeController_0.queryActor(actor, query))
      }
  
    // @LINE:17
    case controllers_HomeController_getMessage5_route(params@_) =>
      call { 
        controllers_HomeController_getMessage5_invoker.call(HomeController_0.getMessage())
      }
  }
}
