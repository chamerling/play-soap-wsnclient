package controllers;

import play.Play;
import play.libs.F.Promise;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.WS.WSRequest;
import play.mvc.Controller;
import play.vfs.VirtualFile;

public class Application extends Controller {

    public static void index() {
    	// TODO : need to render endpoints...
        render();
    }
    
    public static void postNotification(String content, String endpoint) {
    	VirtualFile file = Play.getVirtualFile("app/views/notify.xml");
    	// TODO : render notify using template engine
    	if (file == null) {
    		flash.error("Can not find content to send to service");
    		index();
    	}
    	
    	//System.out.println("Query : " + file.contentAsString());
		WSRequest request = WS.url(endpoint).setHeader("content-type", "application/soap+xml").body(file.contentAsString());
		request.postAsync();
		
        flash.success("Thanks for posting %s!", content);
        index();
    }

}