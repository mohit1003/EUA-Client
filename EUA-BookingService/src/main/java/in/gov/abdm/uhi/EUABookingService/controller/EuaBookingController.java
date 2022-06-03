package in.gov.abdm.uhi.EUABookingService.controller;

import in.gov.abdm.uhi.EUABookingService.Entity.Categories;
import in.gov.abdm.uhi.EUABookingService.Entity.Orders;
import in.gov.abdm.uhi.EUABookingService.Service.SaveDataDB;
import in.gov.abdm.uhi.EUABookingService.beans.EuaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping(value = "/api/v1/bookingService")
public class EuaBookingController {
	Logger LOGGER = LoggerFactory.getLogger(EuaBookingController.class);
	
	@Autowired
	SaveDataDB savedatadb;

	@PostMapping(path = "/on_init")
	public ResponseEntity<String> savedata(@RequestBody @Valid EuaRequest request){
		LOGGER.info("Received request"+request);
		
		Orders saveDataInDb = savedatadb.saveDataInDb(request);
		if(saveDataInDb!=null)
		{
			return new ResponseEntity<String>("true",HttpStatus.OK);	
		}
		else		
		return new ResponseEntity<String>("false",HttpStatus.INTERNAL_SERVER_ERROR);	
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
