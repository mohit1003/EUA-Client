package in.gov.abdm.uhi.EUABookingService.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import in.gov.abdm.uhi.EUABookingService.Entity.Categories;
import in.gov.abdm.uhi.EUABookingService.Entity.Orders;
import in.gov.abdm.uhi.EUABookingService.Entity.Payments;
import in.gov.abdm.uhi.EUABookingService.Entity.User;
import in.gov.abdm.uhi.EUABookingService.Service.SaveDataDB;
import in.gov.abdm.uhi.EUABookingService.beans.EuaRequest;
import in.gov.abdm.uhi.EUABookingService.repository.AddressRepository;
import in.gov.abdm.uhi.EUABookingService.repository.CategoriesRepository;
import in.gov.abdm.uhi.EUABookingService.repository.MessageRepository;
import in.gov.abdm.uhi.EUABookingService.repository.OrderRepository;
import in.gov.abdm.uhi.EUABookingService.repository.PaymentsRepository;
import in.gov.abdm.uhi.EUABookingService.repository.UserAbhaAddressRepository;
import in.gov.abdm.uhi.EUABookingService.repository.UserDeviceRepository;
import in.gov.abdm.uhi.EUABookingService.repository.UserRefreshTokenRepository;
import in.gov.abdm.uhi.EUABookingService.repository.UserReposotory;

@Repository
public class SaveInDb implements SaveDataDB {
	Logger LOGGER = LoggerFactory.getLogger(SaveInDb.class);
	final String INIT_STATE="INITIALIZED";

	@Autowired
	AddressRepository addressRepo;

	@Autowired
	CategoriesRepository catRepo;

	@Autowired
	MessageRepository msgRepo;


	@Autowired
	OrderRepository orderRepo;

	@Autowired
	PaymentsRepository paymentRepo;

	@Autowired
	UserAbhaAddressRepository userabhaaddress;

	@Autowired
	UserDeviceRepository userDeviceRepo;

	@Autowired
	UserRefreshTokenRepository userRefTokenRepo;

	@Autowired
	UserReposotory userRepo;

	private Map<String, Object> tags;

	
	@Override
	public Orders saveDataInDb(EuaRequest request,String action) {		
			
		
		try
		{
			Categories saveCategoriesData = saveCategoriesData(request);	
			Orders saveOrderData = saveOrderData(request,action);	
		
			if(action.equalsIgnoreCase("on_confirm"))
			{
				Payments savePaymentsData = savePaymentsData(request);
				
				if(getPaymentDetailsByTransactionId(request.getMessage().getOrder().getPayment().getParams().getTransactionId()).isEmpty())
				{
					saveOrderData.setPayment(savePaymentsData);			
				}
				else
				{
					Payments updated = savePaymentsData;
					saveOrderData.setPayment(updated);		
				}
			}		
			
			if(getOrderDetailsByOrderId(request.getMessage().getOrder().getId()).isEmpty())
			{
					Orders save = orderRepo.save(saveOrderData);	
					return save;
			}
			else
			{	Orders updated= saveOrderData;
				Orders save = orderRepo.save(updated);	
				return save;		
			}
		}
		catch(NullPointerException e)
		{
			LOGGER.error(request.getContext().getMessageId()+"  Null pointer Exception  "+e);
			
		}
		catch(Exception e)
		{
			LOGGER.error(request.getContext().getMessageId()+"  Something went wrong  "+e);
			
		}
		Orders order=null;
		return order;
		
		
	}

	public Categories saveCategoriesData(EuaRequest request) {
		try
			{
			Categories cat = new Categories();
			cat.setCategoryId(Long.parseLong(request.getMessage().getOrder().getItem().getId()));
			cat.setDescriptor(request.getMessage().getOrder().getItem().getDescriptor().getName());		
			if(getCategoriesDetails(cat.getCategoryId()).isEmpty())
			{
				Categories save = catRepo.save(cat);
			}		
			return cat;
		}
		catch(NullPointerException e)
		{
			LOGGER.error(request.getContext().getMessageId()+"  Null pointer Exception  "+e);
			
		}
		catch(Exception e)
		{
			LOGGER.error(request.getContext().getMessageId()+"  Something went wrong  "+e);
			
		}
		Categories save=null;
		return save;
		
	}

	public Orders saveOrderData(EuaRequest request,String action) {
		try
		{
		Orders order = new Orders();
		order.setOrderId(request.getMessage().getOrder().getId());
		order.setCategoryId(request.getMessage().getOrder().getItem().getId());
		//order.setOrderDate(null);
		order.setHealthcareServiceName(request.getMessage().getOrder().getItem().getDescriptor().getName());
		order.setHealthcareServiceId(request.getMessage().getOrder().getItem().getId());
		//order.setHealthcareProviderName(request.getMessage().getOrder().getProvider().getDescriptor().getName());
		//order.setHealthcareProviderId(request.getMessage().getOrder().getProvider().getId());
		//order.setHealthcareServiceProviderEmail(null);
		//order.setHealthcareServiceProviderPhone(null);
		order.setHealthcareProfessionalName(request.getMessage().getOrder().getFulfillment().getAgent().getName());
		order.setHealthcareProfessionalImage(request.getMessage().getOrder().getFulfillment().getAgent().getImage());
		order.setHealthcareProfessionalEmail(request.getMessage().getOrder().getFulfillment().getAgent().getEmail());
		order.setHealthcareProfessionalPhone(request.getMessage().getOrder().getFulfillment().getAgent().getPhone());
		order.setHealthcareProfessionalId(request.getMessage().getOrder().getFulfillment().getAgent().getId());
		order.setHealthcareProfessionalGender(request.getMessage().getOrder().getFulfillment().getAgent().getGender());
		order.setServiceFulfillmentStartTime(request.getMessage().getOrder().getFulfillment().getStart().getTime().getTimestamp());
		order.setServiceFulfillmentEndTime(request.getMessage().getOrder().getFulfillment().getEnd().getTime().getTimestamp());
		order.setServiceFulfillmentType(request.getMessage().getOrder().getFulfillment().getType());
		order.setHealthcareProviderUrl(request.getContext().getProviderUri());
		order.setMessage(request.toString());
		
		Map<String, Object> tags = request.getMessage().getOrder().getFulfillment().getTags();		
		if(tags!=null)
		{
			//String spoken =String.join(",", (List)(tags.get("@abdm/gov/in/spoken_langs")));			
			//String educations =String.join(",",(List) tags.get("@abdm/gov/in/education"));
			//String speciality =tags.get("@abdm/gov/in/speciality").toString();
			//String yoe = tags.get("@abdm/gov/in/exp").toString();
			String slotid = tags.get("@abdm/gov.in/slot_id").toString();
			//order.setLanguagesSpokenByHealthcareProfessional(spoken);
			order.setSlotId(slotid);
			//order.setHealthcareProfessionalExperience(yoe);	
		}		
		//order.setSymptoms(null);
		if(action.equalsIgnoreCase("on_confirm"))
		{
			order.setIsServiceFulfilled(request.getMessage().getOrder().getState());
		}
		else
			order.setIsServiceFulfilled(INIT_STATE);
			
		
		//order.setHealthcareProfessionalDepartment(null);	
		order.setCategoryId(request.getMessage().getOrder().getItem().getId());
		order.setAbhaId(request.getMessage().getOrder().getCustomer().getId());	
		return order;
		}
		catch(NullPointerException e)
		{
			LOGGER.error(request.getContext().getMessageId()+"  Null pointer Exception  "+e);
			
		}
		catch(Exception e)
		{
			LOGGER.error(request.getContext().getMessageId()+"  Something went wrong  "+e);
			
		}
		Orders save=null;
		return save;
		
	}

	public Payments savePaymentsData(EuaRequest request) {		
		try
		{
		Payments p=new Payments();
		p.setTransactionId(request.getMessage().getOrder().getPayment().getParams().getTransactionId());	
		p.setMethod(request.getMessage().getOrder().getPayment().getUri());
		p.setCurrency(request.getMessage().getOrder().getQuote().getPrice().getCurrency());
		//p.setTransactionTimestamp(null);
		p.setConsultationCharge(request.getMessage().getOrder().getQuote().getBreakup().get(0).getPrice().getValue());
		p.setPhrHandlingFees(request.getMessage().getOrder().getQuote().getBreakup().get(3).getPrice().getValue());
		p.setSgst(request.getMessage().getOrder().getQuote().getBreakup().get(2).getPrice().getValue());
		p.setCgst(request.getMessage().getOrder().getQuote().getBreakup().get(1).getPrice().getValue());
		p.setTransactionState(request.getMessage().getOrder().getPayment().getStatus());
		p.setUserAbhaId(request.getMessage().getOrder().getCustomer().getId());
		Payments save = paymentRepo.save(p);		
		return save;
		}
		catch(NullPointerException e)
		{
			LOGGER.error(request.getContext().getMessageId()+"  Null pointer Exception  "+e);
			
		}
		catch(Exception e)
		{
			LOGGER.error(request.getContext().getMessageId()+"  Something went wrong  "+e);
			
		}
		Payments save=null;
		return save;
	}

	@Override
	public List<User> getUserDetails() {	
		try {
			return userRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return (List<User>) e;
		}
	}

	@Override
	public List<User> getUserDetailsByAbhaId(String abha_id) {		
		try {			
			List<User> findByAbhaid = userRepo.findByHealthIdNumber(abha_id);			
			return findByAbhaid;
		} catch (Exception e) {
			e.printStackTrace();
			return (List<User>) e;
		}
	}

	@Override
	public List<Orders> getOrderDetails() {		
		try {
			return orderRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return (List<Orders>) e;
		}
	}
	
	@Override
	public List<Orders> getOrderDetailsByOrderId(String orderid) {	
	try {		
		List<Orders> findByOrderid= orderRepo.findByOrderId(orderid);		
		return findByOrderid;
	} catch (Exception e) {
		e.printStackTrace();
		return (List<Orders>) e;
	}
	}

	@Override
	public List<Categories> getCategoriesDetails(long categoryid) {
		try {		
			List<Categories> findByCategoryid = catRepo.findByCategoryId(categoryid);		
			return findByCategoryid;
		} catch (Exception e) {
			e.printStackTrace();
			return (List<Categories>) e;
		}
	}

	@Override
	public List<Categories> getCategoriesDetails() {		
		try {
			return catRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return (List<Categories>) e;
		}
	}

	@Override
	public List<Orders> getOrderDetailsByAbhaId(String abhaid) {
		try {		
			List<Orders> findByAbhaid = orderRepo.findByAbhaId(abhaid);		
			return findByAbhaid;
		} catch (Exception e) {
			e.printStackTrace();
			return (List<Orders>) e;
		}
	}

	@Override
	public List<Payments> getPaymentDetailsByTransactionId(String transactionid) {
		try {		
			List<Payments> findByTransactionid = paymentRepo.findByTransactionId(transactionid);		
			return findByTransactionid;
		} catch (Exception e) {
			e.printStackTrace();
			return (List<Payments>) e;
		}
	}
}
