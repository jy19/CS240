package server.handlers;

import java.io.IOException;
import java.io.OutputStream;

import server.database.Database;
import shared.communication.ValidateUserIn;
import shared.communication.ValidateUserOut;
import shared.model.User;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ValidateUserHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Database db = new Database();
		XStream xstream = new XStream(new DomDriver()); //Xstream possible parameter: new DomDriver()
		ValidateUserIn input = (ValidateUserIn) xstream.fromXML(exchange.getRequestBody());
		String username = input.getUsername();
		String password = input.getPassword();
		db.startTransaction();
		User inDB = db.getUserDAO().getUser(username);
		//validate username/password
		ValidateUserOut output = null;
		if(inDB != null && inDB.getPassword().equals(password)){
			output = new ValidateUserOut(true, inDB);
			exchange.sendResponseHeaders(200, 0);
		}
		else{
			output = new ValidateUserOut(false, null);
			exchange.sendResponseHeaders(401, 0);
			System.out.println("Wrong password/user");
		}
		
		OutputStream out = exchange.getResponseBody();
		xstream.toXML(output, out);
		out.close();
		db.endTransaction(false);
	}

}
