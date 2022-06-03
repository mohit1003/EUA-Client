package in.gov.abdm.eua.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import in.gov.abdm.eua.service.constants.ConstantsUtils;
import in.gov.abdm.eua.service.dto.dhp.AckResponse;
import in.gov.abdm.eua.service.dto.dhp.EuaRequestBody;
import in.gov.abdm.eua.service.dto.dhp.EuaRequestBodyStatus;
import in.gov.abdm.eua.service.dto.dhp.MessageTO;
import in.gov.abdm.eua.service.service.impl.EuaServiceImpl;
import in.gov.abdm.eua.service.service.impl.MQConsumerServiceImpl;
import in.gov.abdm.uhi.common.dto.FulfillmentTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RequestMapping("api/v1/euaService/")
@RestController
@Slf4j
public class EuaController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EuaController.class);
	private final MQConsumerServiceImpl mqService;

	@Value("${abdm.gateway.url}")
	private String abdmGatewayURl;

	@Value("${abdm.registry.url}")
	private String abdmRegistryURl;

	@Value("${abdm.eua.url}")
	private String abdmEUAURl;

	private final EuaServiceImpl euaService;
	private final RabbitTemplate template;

	public EuaController(WebClient webClient, RabbitTemplate template, MQConsumerServiceImpl mqConsumerServiceImpl, EuaServiceImpl euaService) {
		this.template = template;


		LOGGER.info("ABDM Gateway :: " + abdmGatewayURl);

		LOGGER.info("ABDM Registry :: " + abdmRegistryURl);
		this.mqService = mqConsumerServiceImpl;

		this.euaService = euaService;
	}


	@PostMapping(ConstantsUtils.ON_SEARCH_ENDPOINT)
	public ResponseEntity<AckResponse> onSearch(@RequestBody EuaRequestBody onSearchRequest) throws JsonProcessingException {

		LOGGER.info("Inside on_search API ");

		ObjectMapper objectMapper = new ObjectMapper();

		ObjectWriter ow = new ObjectMapper().writer();
		try {
			ResponseEntity<AckResponse> onSearchAck = getResponseEntityForErrorCases(onSearchRequest, objectMapper, "on_search");
			if (onSearchAck != null) return onSearchAck;

			String onRequestString = ow.writeValueAsString(onSearchRequest);
			String requestMessageId = onSearchRequest.getContext().getMessageId();

			LOGGER.info("Request Body :" + onRequestString);
			MessageTO message = euaService.extractMessage(requestMessageId, onSearchRequest.getContext().getConsumerId(), onRequestString, onSearchRequest.getContext().getAction());

			euaService.pushToMqGatewayTOEua(message);

			return euaService.getOnAckResponseResponseEntity(objectMapper, onRequestString, "on_search");
		} catch (Exception e) {

			LOGGER.error(e.toString());

			return returnNotKnownError(objectMapper);

		}
	}


	@PostMapping("/on_select")
	public ResponseEntity<AckResponse> onSelect(@RequestBody EuaRequestBody onSelectRequest) throws JsonProcessingException {

		LOGGER.info("Inside on_select API ");

		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		try {

			String onRequestString = ow.writeValueAsString(onSelectRequest);
			String requestMessageId = onSelectRequest.getContext().getMessageId();

			LOGGER.info("Request Body :" + onRequestString);
			MessageTO message = euaService.extractMessage(requestMessageId, onSelectRequest.getContext().getConsumerId(), onRequestString, onSelectRequest.getContext().getAction());

			euaService.pushToMqGatewayTOEua(message);

			return euaService.getOnAckResponseResponseEntity(objectMapper, onRequestString, "on_select");

		} catch (Exception e) {

			LOGGER.error(e.toString());

			AckResponse onSearchAck = objectMapper.readValue(ConstantsUtils.NACK_RESPONSE, AckResponse.class);
			return new ResponseEntity<>(onSearchAck, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/on_init")
	public ResponseEntity<AckResponse> onInit(@RequestBody EuaRequestBodyStatus onInitRequest) throws JsonProcessingException {

		LOGGER.info("Inside on_init API ");

		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		try {

			String onRequestString = ow.writeValueAsString(onInitRequest);
			String requestMessageId = onInitRequest.getContext().getMessageId();

			LOGGER.info("Request Body :" + onRequestString);
			MessageTO message = euaService.extractMessage(requestMessageId, onInitRequest.getContext().getConsumerId(), onRequestString, onInitRequest.getContext().getAction());

			euaService.pushToMqGatewayTOEua(message);

			return euaService.getOnAckResponseResponseEntity(objectMapper, onRequestString, "on_init");

		} catch (Exception e) {

			LOGGER.error(e.toString());

			AckResponse onNAck = objectMapper.readValue(ConstantsUtils.NACK_RESPONSE, AckResponse.class);
			return new ResponseEntity<>(onNAck, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/on_confirm")
	public ResponseEntity<AckResponse> onConfirm(@RequestBody EuaRequestBodyStatus onConfirmRequest) throws JsonProcessingException {

		LOGGER.info("Inside on_confirm API ");

		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		try {

			String onRequestString = ow.writeValueAsString(onConfirmRequest);
			String requestMessageId = onConfirmRequest.getContext().getMessageId();

			LOGGER.info("Request Body :" + onRequestString);
			MessageTO message = euaService.extractMessage(requestMessageId, onConfirmRequest.getContext().getConsumerId(), onRequestString, onConfirmRequest.getContext().getAction());

			euaService.pushToMqGatewayTOEua(message);

			return euaService.getOnAckResponseResponseEntity(objectMapper, onRequestString, "on_confirm");

		} catch (Exception e) {

			LOGGER.error(e.toString());

			AckResponse onNAck = objectMapper.readValue(ConstantsUtils.NACK_RESPONSE, AckResponse.class);
			return new ResponseEntity<>(onNAck, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/on_status")
	public ResponseEntity<AckResponse> onStatus(@RequestBody EuaRequestBodyStatus onStatusRequest) throws JsonProcessingException {
		LOGGER.info("Inside on_status API ");
		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String onRequestString = ow.writeValueAsString(onStatusRequest);
			String requestMessageId = onStatusRequest.getContext().getMessageId();

			LOGGER.info("Request Body :" + onRequestString);
			MessageTO message = euaService.extractMessage(requestMessageId, onStatusRequest.getContext().getConsumerId(), onRequestString, onStatusRequest.getContext().getAction());

			euaService.pushToMqGatewayTOEua(message);

			return euaService.getOnAckResponseResponseEntity(objectMapper, onRequestString, "on_status");

		} catch (Exception e) {

			LOGGER.error(e.toString());


			AckResponse onNAck = objectMapper.readValue(ConstantsUtils.NACK_RESPONSE, AckResponse.class);
			return new ResponseEntity<>(onNAck, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping(ConstantsUtils.SEARCH_ENDPOINT)
	public ResponseEntity<AckResponse> search(@RequestBody EuaRequestBody searchRequest) throws JsonProcessingException {

		LOGGER.info("Inside Search API ");
		String url;
		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		ResponseEntity<AckResponse> searchAck = getResponseEntityForErrorCases(searchRequest, objectMapper, "search");
		if (searchAck != null)
			return searchAck;
		else
			searchAck = ResponseEntity.status(HttpStatus.OK).build();

		searchRequest.getContext().setConsumerUri(abdmEUAURl);
		String onRequestString = ow.writeValueAsString(searchRequest);
		String requestMessageId = searchRequest.getContext().getMessageId();
		String clientId = searchRequest.getContext().getConsumerId();



		try {
			LOGGER.info("Gateway URI :: " + abdmGatewayURl);
			LOGGER.info("Request Body :" + onRequestString);
			euaService.pushToMq(abdmGatewayURl, ConstantsUtils.SEARCH_ENDPOINT, searchRequest,onRequestString, requestMessageId, clientId);

			LOGGER.info("Request Body enqueued successfully:" + onRequestString);

		}
		catch (Exception e) {
			LOGGER.error(e.toString());
			mqService.sendNackResponse(ConstantsUtils.NACK_RESPONSE, requestMessageId);
			AckResponse onSearchAck = objectMapper.readValue(ConstantsUtils.NACK_RESPONSE, AckResponse.class);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(onSearchAck);
		}

		return searchAck;
	}



	@PostMapping("/select")
	public ResponseEntity<AckResponse> select(@RequestBody EuaRequestBody selectRequest) throws JsonProcessingException {

		LOGGER.info("Inside select API ");
		String url;
		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		ResponseEntity<AckResponse> searchAck = getResponseEntityForErrorCases(selectRequest, objectMapper, "select");
		if (searchAck != null)
			return searchAck;
		else
			searchAck = ResponseEntity.status(HttpStatus.OK).build();


		selectRequest.getContext().setConsumerUri(abdmEUAURl);
		String providerURI = selectRequest.getContext().getProviderUri();
		String onRequestString = ow.writeValueAsString(selectRequest);
		String requestMessageId = selectRequest.getContext().getMessageId();
		String clientId = selectRequest.getContext().getConsumerId();

		try {
			LOGGER.info("Provider URI :" + providerURI);

			LOGGER.info("Request Body :" + onRequestString);
			euaService.pushToMq(providerURI, ConstantsUtils.SELECT_ENDPOINT, selectRequest, onRequestString, requestMessageId, clientId);
			LOGGER.info("Request Body enqueued successfully:" + onRequestString);


		} catch (Exception e) {
			LOGGER.error(e.toString());
			mqService.sendNackResponse(ConstantsUtils.NACK_RESPONSE, requestMessageId);
			AckResponse onSearchAck = objectMapper.readValue(ConstantsUtils.NACK_RESPONSE, AckResponse.class);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(onSearchAck);
		}
		return searchAck;

	}


	@PostMapping("/init")
	public ResponseEntity<AckResponse> init(@RequestBody EuaRequestBody initRequest) throws JsonProcessingException {
		String url;
		LOGGER.info("Inside init API ");
		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		ResponseEntity<AckResponse> searchAck = getResponseEntityForErrorCases(initRequest, objectMapper, "init");
		if (searchAck != null)
			return searchAck;
		else
			searchAck = ResponseEntity.status(HttpStatus.OK).build();

		initRequest.getContext().setConsumerUri(abdmEUAURl);
		String providerURI = initRequest.getContext().getProviderUri();
		String onRequestString = ow.writeValueAsString(initRequest);
		String requestMessageId = initRequest.getContext().getMessageId();
		String clientId = initRequest.getContext().getConsumerId();

		try {
			LOGGER.info("Provider URI :" + providerURI);
			LOGGER.info("Request Body :" + onRequestString);

			euaService.pushToMq(abdmGatewayURl, ConstantsUtils.INIT_ENDPOINT, initRequest, onRequestString, requestMessageId, clientId);

			LOGGER.info("Request Body enqueued successfully:" + onRequestString);

			url = providerURI + ConstantsUtils.INIT_ENDPOINT;
			initRequest.getContext().setProviderUri(url);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			mqService.sendNackResponse(ConstantsUtils.NACK_RESPONSE, requestMessageId);
			AckResponse onSearchAck = objectMapper.readValue(ConstantsUtils.NACK_RESPONSE, AckResponse.class);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(onSearchAck);
		}
		return searchAck;

	}

	@PostMapping("/confirm")
	public ResponseEntity<AckResponse> confirm(@RequestBody EuaRequestBody confirmRequest) throws JsonProcessingException {
		String url;
		LOGGER.info("Inside confirm API ");

		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();
		confirmRequest.getContext().setConsumerUri(abdmEUAURl);
		String providerURI = confirmRequest.getContext().getProviderUri();
		String onRequestString = ow.writeValueAsString(confirmRequest);
		String requestMessageId = confirmRequest.getContext().getMessageId();
		String clientId = confirmRequest.getContext().getConsumerId();

		ResponseEntity<AckResponse> searchAck = getResponseEntityForErrorCases(confirmRequest, objectMapper, "confirm");
		if (searchAck != null)
			return searchAck;
		else
			searchAck = ResponseEntity.status(HttpStatus.OK).build();

		try {
			LOGGER.info("Provider URI :" + providerURI);
			LOGGER.info("Request Body :" + onRequestString);

			euaService.pushToMq(providerURI, ConstantsUtils.CONFIRM_ENDPOINT, confirmRequest, onRequestString, requestMessageId, clientId);

			LOGGER.info("Request Body enqueued successfully:" + onRequestString);


		} catch (Exception e) {
			LOGGER.error(e.toString());
			mqService.sendNackResponse(ConstantsUtils.NACK_RESPONSE, requestMessageId);
			AckResponse onSearchAck = objectMapper.readValue(ConstantsUtils.NACK_RESPONSE, AckResponse.class);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(onSearchAck);
		}
		return searchAck;

	}

	@PostMapping("/status")
	public ResponseEntity<AckResponse> status(@RequestBody EuaRequestBody statusRequest) throws JsonProcessingException {

		String url;
		LOGGER.info("Inside status API ");
		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		statusRequest.getContext().setConsumerUri(abdmEUAURl);
		String providerURI = statusRequest.getContext().getProviderUri();
		String onRequestString = ow.writeValueAsString(statusRequest);
		String requestMessageId = statusRequest.getContext().getMessageId();
		String clientId = statusRequest.getContext().getConsumerId();

		ResponseEntity<AckResponse> searchAck = getResponseEntityForErrorCases(statusRequest, objectMapper, "status");
		if (searchAck != null)
			return searchAck;
		else
			searchAck = ResponseEntity.status(HttpStatus.OK).build();
		try {

			LOGGER.info("Provider URI :" + providerURI);
			LOGGER.info("Request Body :" + onRequestString);

			euaService.pushToMq(providerURI, ConstantsUtils.STATUS_ENDPOINT, statusRequest, onRequestString, requestMessageId, clientId);

			LOGGER.info("Request Body enqueued successfully:" + onRequestString);

		} catch (Exception e) {
			LOGGER.error(e.toString());
			mqService.sendNackResponse(ConstantsUtils.NACK_RESPONSE, requestMessageId);
			AckResponse onSearchAck = objectMapper.readValue(ConstantsUtils.NACK_RESPONSE, AckResponse.class);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(onSearchAck);
		}
		return searchAck;
	}


	private ResponseEntity<AckResponse> getResponseEntityForErrorCases(EuaRequestBody onSearchRequest, ObjectMapper objectMapper, String action) throws JsonProcessingException {
		ResponseEntity<AckResponse> onSearchAck = checkIfMessageIsNull(onSearchRequest, objectMapper);
		if (onSearchAck != null) return onSearchAck;

		ResponseEntity<AckResponse> onSearchAck1 = checkIfContextIsNull(onSearchRequest, objectMapper);
		if (onSearchAck1 != null) return onSearchAck1;


		return checkIfMandatoryfieldsInContextAreNull(onSearchRequest, objectMapper);

//		if(action.equalsIgnoreCase("on_search")) {
//			List<ProviderTO> providers = onSearchRequest.getMessage().getCatalog().getProviders();
//			List<FulfillmentTO> fulfillments = providers.stream().flatMap(provider -> provider.getFulfillments().stream()).toList();
//
//			List<FulfillmentTO> fulfillmentPersonWithNoNameList = fulfillments.stream().filter(f -> null == f.getPerson().getName()).collect(Collectors.toList());
//
//			return checkIfPersonNameIsNull(objectMapper, fulfillmentPersonWithNoNameList);
//		}
	}

	private ResponseEntity<AckResponse> returnNotKnownError(ObjectMapper objectMapper) throws JsonProcessingException {
		String onSearchAckJsonErrorString = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Something went wrong\" } }";
		AckResponse onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponse.class);
		return new ResponseEntity<>(onSearchAck, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<AckResponse> checkIfPersonNameIsNull(ObjectMapper objectMapper, List<FulfillmentTO> fulfillmentPersonWithNoNameList) throws JsonProcessingException {
		if(!fulfillmentPersonWithNoNameList.isEmpty()) {
			String onSearchAckJsonErrorString = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Mandatory field person name in one of the result is null\" } }";
			AckResponse onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponse.class);
			return new ResponseEntity<>(onSearchAck, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}

	private ResponseEntity<AckResponse> checkIfMandatoryfieldsInContextAreNull(EuaRequestBody onSearchRequest, ObjectMapper objectMapper) throws JsonProcessingException {
		if(null == onSearchRequest.getContext().getAction()
				||null == onSearchRequest.getContext().getDomain()
				|| null == onSearchRequest.getContext().getCity()
				|| null == onSearchRequest.getContext().getCountry()
				|| null == onSearchRequest.getContext().getCoreVersion()
				|| null == onSearchRequest.getContext().getConsumerId()
				|| null == onSearchRequest.getContext().getConsumerUri()
				|| null == onSearchRequest.getContext().getTransactionId()
				|| null == onSearchRequest.getContext().getTimestamp()
				|| null == onSearchRequest.getContext().getMessageId()) {

			String onSearchAckJsonErrorString = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Mandatory fields on context are Null\" } }";
			AckResponse onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponse.class);
			return new ResponseEntity<>(onSearchAck, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}

	private ResponseEntity<AckResponse> checkIfContextIsNull(EuaRequestBody onSearchRequest, ObjectMapper objectMapper) throws JsonProcessingException {
		if(null == onSearchRequest.getContext()) {
			String onSearchAckJsonErrorString = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Context is Null\" } }";
			AckResponse onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponse.class);
			return new ResponseEntity<>(onSearchAck, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}

	private ResponseEntity<AckResponse> checkIfMessageIsNull(EuaRequestBody onSearchRequest, ObjectMapper objectMapper) throws JsonProcessingException {
		if(null == onSearchRequest.getMessage()) {
			String onSearchAckJsonErrorString = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Message is Null\" } }";
			AckResponse onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponse.class);
			return new ResponseEntity<>(onSearchAck, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}
}
