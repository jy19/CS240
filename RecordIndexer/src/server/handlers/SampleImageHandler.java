package server.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.SampleImageIn;
import shared.communication.SampleImageOut;
import shared.model.Image;
import shared.model.User;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SampleImageHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Database db = new Database();
		XStream xstream = new XStream(new DomDriver());
		SampleImageIn input = (SampleImageIn) xstream.fromXML(exchange.getRequestBody());
		String username = input.getUsername();
		String password = input.getPassword();
		int projectID = input.getProjectID();
		db.startTransaction();
		User inDB = db.getUserDAO().getUser(username);
		ArrayList<Image> imagesbyProject = null;
		SampleImageOut output = null;
		if(inDB != null && inDB.getPassword().equals(password)){
			imagesbyProject = db.getImageDAO().getImagesbyProjectID(projectID);
			if(imagesbyProject.size() != 0){
				output = new SampleImageOut(imagesbyProject.get(0));
				exchange.sendResponseHeaders(200, 0);
			}
			else{
				//failed
				exchange.sendResponseHeaders(400, 0);
				System.out.println("No images in project " + projectID);
			}
		}
		else{
			//failed
			exchange.sendResponseHeaders(400, 0);
			System.out.println("Wrong password");
		}
		OutputStream out = exchange.getResponseBody();
		xstream.toXML(output, out);
		out.close();
		db.endTransaction(false);
		
	}

}
