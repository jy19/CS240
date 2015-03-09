package server.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.SubmitBatchIn;
import shared.communication.SubmitBatchOut;
import shared.model.Field;
import shared.model.Image;
import shared.model.Rvalue;
import shared.model.User;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SubmitBatchHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Database db = new Database();
		XStream xstream = new XStream(new DomDriver());
		SubmitBatchIn input = (SubmitBatchIn) xstream.fromXML(exchange.getRequestBody());
		String username = input.getUsername();
		String password = input.getPassword();
		int imageID = input.getImageID();
		ArrayList<String> values = input.getRecordValues();
		
		db.startTransaction();
		User user = db.getUserDAO().getUser(username);
		Image image = db.getImageDAO().getUserImage(user.getUserID());
		
		SubmitBatchOut output = null;
		
		if(user != null && user.getPassword().equals(password) && image != null && imageID == image.getId()){
			ArrayList<Field> fields = db.getFieldDAO().getFieldsbyProjectID(image.getProjectID());
			double indexedRecords = values.size()/fields.size();
			if(indexedRecords > db.getProjectDAO().getProject(image.getProjectID()).getRecordsperimage()){
				exchange.sendResponseHeaders(400, 0);
				db.endTransaction(false);
				System.out.println("Too many values!");
			}
			else if(indexedRecords != (int) indexedRecords){
				exchange.sendResponseHeaders(400, 0);
				db.endTransaction(false);
				System.out.println("Wrong number of values!");
			}
			else{
				int fieldIDcount = 0;
				int rowsCount = 1;
				for(String rval: values){
					Rvalue newRVal = new Rvalue(-1, rowsCount, fields.get(fieldIDcount).getId(), imageID, rval);
					
					db.getValueDAO().add(newRVal);
					fieldIDcount++;
					if(fieldIDcount == fields.size()){
						fieldIDcount = 0;
						rowsCount++;
					}
					
				}
				System.out.println("submitted!!");
				output = new SubmitBatchOut(true);
				exchange.sendResponseHeaders(200, 0);
				OutputStream out = exchange.getResponseBody();
				xstream.toXML(output, out);
				out.close();
				user.setImageID(-1);
				image.setCurrentState(1);
				image.setUserID(-1);
				user.incrementRecords((int) indexedRecords);
				db.getImageDAO().update(image);
				db.getUserDAO().update(user);
				System.out.println("submitted: " + user);
				db.endTransaction(true);
			}
		}
		else{
			exchange.sendResponseHeaders(400, 0);
			db.endTransaction(false);
			System.out.println("Wrong password or trying to submit an image you do not owned. >:(");
		}
		
	}

}
