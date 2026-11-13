package businessLogic;

import java.awt.Color;
import java.net.URL;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;

public class BLFactory implements IBLFactory {

	public BLFacade getBusinessLogicFactory(boolean local)
	{
		try
		{	
			if (local) {
				DataAccess da= new DataAccess();
				return new BLFacadeImplementation(da);
			}
	
			ConfigXML c=ConfigXML.getInstance();
			String serviceName= "http://"+c.getBusinessLogicNode() +":"+ c.getBusinessLogicPort()+"/ws/"+c.getBusinessLogicName()+"?wsdl";	 
			URL url = new URL(serviceName);
	
	 
	        //1st argument refers to wsdl document above
			//2nd argument is service name, refer to wsdl document above
	        QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
	 
	        Service service = Service.create(url, qname);
	
	        return service.getPort(BLFacade.class);
		}
		catch(Exception e)
		{			
			System.out.println("Error in getBusinessLogicFactory: "+e.toString());
			return null;
		}
	}
}
