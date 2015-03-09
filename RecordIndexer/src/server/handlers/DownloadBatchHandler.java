package server.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import server.database.Database;
import shared.communication.DownloadBatchIn;
import shared.communication.DownloadBatchOut;
import shared.communication.ValidateUserOut;
import shared.model.Field;
import shared.model.Image;
import shared.model.Project;
import shared.model.User;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class DownloadBatchHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Database db = new Database();
		XStream xstream = new XStream(new DomDriver());
		DownloadBatchIn input = (DownloadBatchIn) xstream.fromXML(exchange.getRequestBody());
		String username = input.getUsername();
		String password = input.getPassword();
		int projectID = input.getProjectID();
		db.startTransaction();
		User user = db.getUserDAO().getUser(username);
		DownloadBatchOut output = null;
		if(user != null && user.getPassword().equals(password)){
			ArrayList<Image> images = db.getImageDAO().getImagesbyProjectID(projectID);
			if(images.size() != 0 && user.getImageID() == -1){
				Image newImage = null;
				for(Image image: images){
					if(image.getUserID() == -1){
						newImage = image;
						break;
					}
				}
				if(newImage != null){
					Project project = db.getProjectDAO().getProject(projectID);
					ArrayList<Field> fields = db.getFieldDAO().getFieldsbyProjectID(projectID);
					output = new DownloadBatchOut(project, newImage, fields);
					System.out.println(user.toString());
					newImage.setUserID(user.getUserID());
					newImage.setCurrentState(0);
					user.setImageID(newImage.getId());
					db.getImageDAO().update(newImage);
					db.getUserDAO().update(user);
					
					exchange.sendResponseHeaders(200, 0);
					OutputStream out = exchange.getResponseBody();
					xstream.toXML(output, out);
					out.close();
					db.endTransaction(true);
				}
				else{
					exchange.sendResponseHeaders(400, 0);
					db.endTransaction(false);
					System.out.println("No images available");
				}
			}
			else{
				exchange.sendResponseHeaders(400, 0);
				db.endTransaction(false);
				System.out.println("Wrong projec id or user already has an image");
			}
		}
		else{
			exchange.sendResponseHeaders(400, 0);
			db.endTransaction(false);
			System.out.println("Wrong password");
		}
	}

}
