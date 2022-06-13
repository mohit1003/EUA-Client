package in.gov.abdm.uhi.EUABookingService.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.gov.abdm.uhi.EUABookingService.Entity.Categories;
import in.gov.abdm.uhi.EUABookingService.Entity.Orders;
import in.gov.abdm.uhi.EUABookingService.Service.SaveDataDB;
import in.gov.abdm.uhi.EUABookingService.beans.AckResponse;
import in.gov.abdm.uhi.EUABookingService.beans.AckTO;
import in.gov.abdm.uhi.EUABookingService.beans.AcknowledementTO;
import in.gov.abdm.uhi.EUABookingService.beans.ErrorTO;
import in.gov.abdm.uhi.EUABookingService.beans.EuaRequest;
import in.gov.abdm.uhi.EUABookingService.beans.MessageTO;
@RestController
@RequestMapping(value = "/api/v1/bookingService")
public class EuaBookingController {
	Logger LOGGER = LoggerFactory.getLogger(EuaBookingController.class);
	
	@Autowired
	SaveDataDB savedatadb;


	@PostMapping(path = "/on_init")
	public ResponseEntity<AcknowledementTO> savedataForInit(@RequestBody @Valid EuaRequest request){
		LOGGER.info(request.getContext().getMessageId()+"Received request inside on_init "+request);
		AckResponse onSaveAck=new AckResponse() ;
		Orders saveDataInDb = savedatadb.saveDataInDb(request,"on_init");
		if(saveDataInDb!=null && null!=request.getContext().getProviderUri())
		{
			if(request.getContext().getProviderUri().isBlank())
			{
				ErrorTO e=new ErrorTO();
				e.setMessage("Provider url is blank");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createNacknowledgementTO(e));
			}
				
			return ResponseEntity.status(HttpStatus.OK).body(createAcknowledgementTO());			
		}
		else
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createNacknowledgementTO(null));		 
		}
		 
	}
	@PostMapping(path = "/on_confirm")
	public ResponseEntity<AcknowledementTO> savedataForConfirm(@RequestBody @Valid EuaRequest request){
		LOGGER.info(request.getContext().getMessageId()+"Received request inside on_confirm "+request);
		AckResponse onSaveAck=new AckResponse() ;
		Orders saveDataInDb = savedatadb.saveDataInDb(request,"on_confirm");
		if(saveDataInDb!=null  && null!=request.getContext().getProviderUri())
		{
			if(request.getContext().getProviderUri().isBlank())
			{
				ErrorTO e=new ErrorTO();
				e.setMessage("Provider url is blank");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createNacknowledgementTO(e));
			}
			return ResponseEntity.status(HttpStatus.OK).body(createAcknowledgementTO());			
		}
		else
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createNacknowledgementTO(null));		 
		}
		 
	}
	private AcknowledementTO createAcknowledgementTO() {
		AckTO ack = new AckTO("ACK");
		MessageTO ackMessage = new MessageTO(ack);
		return new AcknowledementTO(ackMessage, null);
		}
	
	private AcknowledementTO createNacknowledgementTO(ErrorTO error) {
		AckTO ack = new AckTO("NACK");
		MessageTO ackMessage = new MessageTO(ack);
		return new AcknowledementTO(ackMessage, error);
		}
	
	
	
	@GetMapping(path = "/getOrders")
	public ResponseEntity<List<Orders>> getOrders(){	
		LOGGER.info("inside Get Orders");
		List<Orders> getOrderDetails = savedatadb.getOrderDetails();		
		return new ResponseEntity<>(getOrderDetails,HttpStatus.OK);		
	}
	
	@GetMapping(path = "/getOrdersByOrderid/{orderid}")
	public ResponseEntity<List<Orders>> getOrderByOrderid(@PathVariable("orderid") String orderid){	
		LOGGER.info("inside Get order by orderid");
		List<Orders> getOrderDetails = savedatadb.getOrderDetailsByOrderId(orderid);		
		return new ResponseEntity<>(getOrderDetails,HttpStatus.OK);		
	}
	
	@GetMapping(path = "/getOrdersByAbhaId/{abhaid}")
	public ResponseEntity<List<Orders>> getOrderByAbhaid(@PathVariable("abhaid") String abhaid){	
		LOGGER.info("inside Get order by abhaid");
		List<Orders> getOrderDetails = savedatadb.getOrderDetailsByAbhaId(abhaid);		
		return new ResponseEntity<>(getOrderDetails,HttpStatus.OK);		
	}
	
	@GetMapping(path = "/getCategories/{categoryid}")
	public ResponseEntity<List<Categories>> getCategoriesByCategoryid(@PathVariable("categoryid") long categoryid){
		LOGGER.info("Get categories by categories id");
		List<Categories> getCategoriesDetails = savedatadb.getCategoriesDetails(categoryid);		
		return new ResponseEntity<>(getCategoriesDetails,HttpStatus.OK);		
	}
	
	@GetMapping(path = "/getCategories")
	public ResponseEntity<List<Categories>> getCategories(){	
		LOGGER.info("inside Get Categories");
		List<Categories> getCategoriesDetails = savedatadb.getCategoriesDetails();		
		return new ResponseEntity<>(getCategoriesDetails,HttpStatus.OK);		
	}
	
	
	
	

}
