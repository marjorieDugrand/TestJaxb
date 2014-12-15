import java.io.File;
import java.math.BigInteger;
import java.util.GregorianCalendar;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import model.Exchange;
import model.ResponseTimeT;
import model.TimeUnitType;


public class ResultsHandler {
	
	/*public static void printResults(Results results) {
		
		System.out.println("general results");
		System.out.println("lost messages : " + results.getGeneralResults().getLostMessages());
		System.out.println("averageResponseTime : " + results.getGeneralResults().getAverageResponseTime());
		System.out.println("minResponseTime : " + results.getGeneralResults().getMinResponseTime());
	    System.out.println("maxResponseTime : " + results.getGeneralResults().getMaxResponseTime());
	    System.out.println("averageMemoryUsed : " + results.getGeneralResults().getAverageMemoryUsed());
	}*/
	
	private static void printExchange(Exchange exchange) {
	    System.out.println("consumerId : " + exchange.getConsumerId());
	    System.out.println("producerId : " + exchange.getProducerId());
	    System.out.println("received : " + exchange.isReceived());
	    System.out.println("responseTime : " + exchange.getResponseTime());
	    System.out.println("date : " + exchange.getDate());
	}
	
	private static void exchangeUnmarshall() {
		try {
			JAXBContext jc = JAXBContext.newInstance("model");
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(new File("res/xsd/exchange.xsd"));
			unmarshaller.setSchema(schema);
			Exchange exchange = (Exchange) unmarshaller.unmarshal(new File("res/xml/exchange.xml"));
			printExchange(exchange);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void exchangeMarshall() {
		try {
			Exchange exchange = new Exchange();
			exchange.setConsumerId("c1");
			exchange.setProducerId("p1");
			GregorianCalendar gcal = new GregorianCalendar(2014, 12, 14, 14, 55, 30);
		    XMLGregorianCalendar xgcal;
			xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
			exchange.setDate(xgcal);
			exchange.setReceived(true);
			ResponseTimeT rTime = new ResponseTimeT();
			rTime.setValue(BigInteger.valueOf(10));
			rTime.setTimeUnit(TimeUnitType.MS);
			exchange.setResponseTime(rTime);
			JAXBContext jaxbContext = JAXBContext.newInstance(Exchange.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
			marshaller.marshal(exchange, new File("res/xml/result_exchange.xml"));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {	
		exchangeUnmarshall();
		exchangeMarshall();
	}
}
