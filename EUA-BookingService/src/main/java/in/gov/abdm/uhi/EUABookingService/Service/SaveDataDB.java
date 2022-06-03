package in.gov.abdm.uhi.EUABookingService.Service;

import java.util.List;

import in.gov.abdm.uhi.EUABookingService.Entity.Categories;
import in.gov.abdm.uhi.EUABookingService.Entity.Orders;
import in.gov.abdm.uhi.EUABookingService.Entity.Payments;
import in.gov.abdm.uhi.EUABookingService.Entity.User;
import in.gov.abdm.uhi.EUABookingService.beans.EuaRequest;

public interface SaveDataDB {
	
Orders saveDataInDb(EuaRequest request ,String action ) ;
	
	List<Orders> getOrderDetails();
	List<Categories> getCategoriesDetails();
	List<User> getUserDetails();
	List<User> getUserDetailsByAbhaId(String s);
	List<Categories> getCategoriesDetails(long s);
	List<Orders> getOrderDetailsByOrderId(String orderid);
	List<Orders> getOrderDetailsByAbhaId(String abhaid);
	List<Payments> getPaymentDetailsByTransactionId(String transactionid);
	

}
