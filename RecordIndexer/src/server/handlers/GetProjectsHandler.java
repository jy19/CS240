package server.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.GetProjectsIn;
import shared.communication.GetProjectsOut;
import shared.model.Project;
import shared.model.User;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetProjectsHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Database db = new Database();
		XStream xstream = new XStream(new DomDriver());
		GetProjectsIn input = (GetProjectsIn) xstream.fromXML(exchange.getRequestBody());
		String username = input.getUsername();
		String password = input.getPassword();
		db.startTransaction();
		User inDB = db.getUserDAO().getUser(username);
		ArrayList<Project> allProjects = null;
		GetProjectsOut output = null;
		if(inDB != null && inDB.getPassword().equals(password)){
			allProjects = db.getProjectDAO().getAll();
			output = new GetProjectsOut(true, allProjects);
			exchange.sendResponseHeaders(200, 0);
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
