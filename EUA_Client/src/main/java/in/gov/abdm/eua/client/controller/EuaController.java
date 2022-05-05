package in.gov.abdm.eua.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import in.gov.abdm.eua.client.beans.Fulfillment;
import in.gov.abdm.eua.client.beans.Provider;
import in.gov.abdm.eua.client.constants.ConstantsUtils;
import in.gov.abdm.eua.client.dto.AckResponse;
import in.gov.abdm.eua.client.dto.EuaRequestBody;
import in.gov.abdm.eua.client.dto.EuaRequestBodyStatus;
import in.gov.abdm.eua.client.dto.MessageTO;
import in.gov.abdm.eua.client.service.EuaService;
import in.gov.abdm.eua.client.service.MQConsumerService;
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
import java.util.stream.Collectors;

@RequestMapping("api/v1/")
@RestController
@Slf4j
public class EuaController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EuaController.class);
	public static final String NACK_RESPONSE = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Something went wrong\" } }";
	private final MQConsumerService mqService;

	@Value("${abdm.gateway.url}")
	private String abdmGatewayURl;

	@Value("${abdm.registry.url}")
	private String abdmRegistryURl;

	@Value("${abdm.eua.url}")
	private String abdmEUAURl;

	private final EuaService euaService;
	private final RabbitTemplate template;

	public EuaController(WebClient webClient, RabbitTemplate template, MQConsumerService mqConsumerService, EuaService euaService) {
		this.template = template;


		LOGGER.info("ABDM Gateway :: " + abdmGatewayURl);

		LOGGER.info("ABDM Registry :: " + abdmRegistryURl);
		this.mqService = mqConsumerService;

		this.euaService = euaService;
	}


	@PostMapping(ConstantsUtils.ON_SEARCH_ENDPOINT)
	public ResponseEntity<AckResponse> onSearch(@RequestBody EuaRequestBody onSearchRequest) throws JsonProcessingException {

		LOGGER.info("Inside on_search API ");

		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		ResponseEntity<AckResponse> onSearchAck = checkIfMessageIsNull(onSearchRequest, objectMapper);
		if (onSearchAck != null) return onSearchAck;

		ResponseEntity<AckResponse> onSearchAck1 = checkIfContextIsNull(onSearchRequest, objectMapper);
		if (onSearchAck1 != null) return onSearchAck1;


		ResponseEntity<AckResponse> onSearchAck2 = checkIfMandatoryfieldsInContextAreNull(onSearchRequest, objectMapper);
		if (onSearchAck2 != null) return onSearchAck2;

		List<Provider> providers = onSearchRequest.getMessage().getCatalog().getProviders();
		List<Fulfillment> fulfillments = providers.stream().flatMap(provider -> provider.getFulfillments().stream()).collect(Collectors.toList());

		List<Fulfillment> fulfillmentPersonWithNoNameList = fulfillments.stream().filter(f -> null == f.getPerson().getName()).collect(Collectors.toList());

		ResponseEntity<AckResponse> onSearchAck3 = checkIfPersonNameIsNull(objectMapper, fulfillmentPersonWithNoNameList);
		if (onSearchAck3 != null) return onSearchAck3;

		try {
			String onRequestString = ow.writeValueAsString(onSearchRequest);
			String requestMessageId = onSearchRequest.getContext().getMessage_id();

			LOGGER.info("Request Body :" + onRequestString);
			MessageTO message = euaService.extractMessage(requestMessageId, onSearchRequest.getContext().getConsumer_id(), onRequestString, onSearchRequest.getContext().getAction());

			euaService.pushToMqAndSaveToDb(message);

			return euaService.getOnAckResponseResponseEntity(objectMapper, onRequestString, "on_search");
		} catch (Exception e) {

			LOGGER.error(e.toString());

			return returnNotKnownError(objectMapper);

		}
	}

	private ResponseEntity<AckResponse> returnNotKnownError(ObjectMapper objectMapper) throws JsonProcessingException {
		String onSearchAckJsonErrorString = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Something went wrong\" } }";
		AckResponse onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponse.class);
		return new ResponseEntity<>(onSearchAck, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<AckResponse> checkIfPersonNameIsNull(ObjectMapper objectMapper, List<Fulfillment> fulfillmentPersonWithNoNameList) throws JsonProcessingException {
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
				|| null == onSearchRequest.getContext().getCore_version()
				|| null == onSearchRequest.getContext().getConsumer_id()
				|| null == onSearchRequest.getContext().getConsumer_uri()
				|| null == onSearchRequest.getContext().getTransaction_id()
				||null == onSearchRequest.getContext().getTimestamp()
				||null == onSearchRequest.getContext().getMessage_id()) {

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


	@PostMapping("/on_select")
	public ResponseEntity<AckResponse> onSelect(@RequestBody EuaRequestBody onSelectRequest) throws JsonProcessingException {

		LOGGER.info("Inside on_select API ");

		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		try {

			String onRequestString = ow.writeValueAsString(onSelectRequest);
			String requestMessageId = onSelectRequest.getContext().getMessage_id();

			LOGGER.info("Request Body :" + onRequestString);

			template.convertAndSend(ConstantsUtils.EXCHANGE, ConstantsUtils.ROUTING_KEY_GATEWAY_TO_EUA, onSelectRequest);

			return euaService.getOnAckResponseResponseEntity(objectMapper, onRequestString, "on_select");

		} catch (Exception e) {

			LOGGER.error(e.toString());

			String onSearchAckJsonErrorString = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Something went wrong\" } }";
			AckResponse onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponse.class);
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
			String requestMessageId = onInitRequest.getContext().getMessage_id();

			LOGGER.info("Request Body :" + onRequestString);

			template.convertAndSend(ConstantsUtils.EXCHANGE, ConstantsUtils.ROUTING_KEY_GATEWAY_TO_EUA, onInitRequest);
//			List<Optional<Message>> messageData = messageRepository
//					.findByMessageIdAndDhpQueryType(requestMessageId, "on_init");
			return euaService.getOnAckResponseResponseEntity(objectMapper, onRequestString, "on_init");

		} catch (Exception e) {

			LOGGER.error(e.toString());

			String onSearchAckJsonErrorString = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Something went wrong\" } }";
			AckResponse onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponse.class);
			return new ResponseEntity<>(onSearchAck, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/on_confirm")
	public ResponseEntity<AckResponse> onConfirm(@RequestBody EuaRequestBodyStatus onConfirmRequest) throws JsonProcessingException {

		LOGGER.info("Inside on_confirm API ");

		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		try {

			String onRequestString = ow.writeValueAsString(onConfirmRequest);
			String requestMessageId = onConfirmRequest.getContext().getMessage_id();

			LOGGER.info("Request Body :" + onRequestString);

			template.convertAndSend(ConstantsUtils.EXCHANGE, ConstantsUtils.ROUTING_KEY_GATEWAY_TO_EUA, onConfirmRequest);
//			List<Optional<Message>> messageData = messageRepository
//					.findByMessageIdAndDhpQueryType(requestMessageId, "on_confirm");

			return euaService.getOnAckResponseResponseEntity(objectMapper, onRequestString, "on_confirm");

		} catch (Exception e) {

			LOGGER.error(e.toString());

			String onSearchAckJsonErrorString = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Something went wrong\" } }";
			AckResponse onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponse.class);
			return new ResponseEntity<>(onSearchAck, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/on_status")
	public ResponseEntity<AckResponse> onStatus(@RequestBody EuaRequestBodyStatus onStatusRequest) throws JsonProcessingException {
		LOGGER.info("Inside on_status API ");
		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String onRequestString = ow.writeValueAsString(onStatusRequest);
			String requestMessageId = onStatusRequest.getContext().getMessage_id();

			LOGGER.info("Request Body :" + onRequestString);

			template.convertAndSend(ConstantsUtils.EXCHANGE, ConstantsUtils.ROUTING_KEY_GATEWAY_TO_EUA, onStatusRequest);
//			List<Optional<Message>> messageData = messageRepository
//					.findByMessageIdAndDhpQueryType(requestMessageId, "on_status");
			return euaService.getOnAckResponseResponseEntity(objectMapper, onRequestString, "on_status");

		} catch (Exception e) {

			LOGGER.error(e.toString());

			String onSearchAckJsonErrorString = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Something went wrong\" } }";
			AckResponse onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponse.class);
			return new ResponseEntity<>(onSearchAck, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping(ConstantsUtils.SEARCH_ENDPOINT)
	public void search(@RequestBody EuaRequestBody searchRequest) throws JsonProcessingException {

		LOGGER.info("Inside Search API ");
		String url;
		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		searchRequest.getContext().setConsumer_uri(abdmEUAURl);
		String onRequestString = ow.writeValueAsString(searchRequest);
		String requestMessageId = searchRequest.getContext().getMessage_id();
		String clientId = searchRequest.getContext().getConsumer_id();

		try {
			LOGGER.info("Gateway URI :: " + abdmGatewayURl);


			LOGGER.info("Request Body :" + onRequestString);
			euaService.pushToMq(abdmGatewayURl, ConstantsUtils.SEARCH_ENDPOINT, searchRequest,onRequestString, requestMessageId, clientId);

			LOGGER.info("Request Body enqueued successfully:" + onRequestString);

		} catch (Exception e) {
			LOGGER.error(e.toString());
			String onSearchAckJsonErrorString = NACK_RESPONSE;
			AckResponse onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponse.class);
			mqService.sendNackResponse(onSearchAckJsonErrorString, requestMessageId);
		}

	}



	@PostMapping("/select")
	public void select(@RequestBody EuaRequestBody selectRequest) throws JsonProcessingException {

		LOGGER.info("Inside select API ");
		String url;
		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		selectRequest.getContext().setConsumer_uri(abdmEUAURl);
		String providerURI = selectRequest.getContext().getProvider_uri();
		String onRequestString = ow.writeValueAsString(selectRequest);
		String requestMessageId = selectRequest.getContext().getMessage_id();
		String clientId = selectRequest.getContext().getConsumer_id();

		try {
			LOGGER.info("Provider URI :" + providerURI);

			LOGGER.info("Request Body :" + onRequestString);
			euaService.pushToMq(providerURI, ConstantsUtils.SELECT_ENDPOINT, selectRequest, onRequestString, requestMessageId, clientId);


//			messageRepository
//					.save(new Message(requestMessageId, "", "on_select", Timestamp.from(ZonedDateTime.now().toInstant()), clientId));

		} catch (Exception e) {
			LOGGER.error(e.toString());
			String onSearchAckJsonErrorString = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Something went wrong\" } }";
			AckResponse onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponse.class);
			mqService.sendNackResponse(onSearchAckJsonErrorString, requestMessageId);

		}
	}


	@PostMapping("/init")
	public void init(@RequestBody EuaRequestBody initRequest) throws JsonProcessingException {
		String url;
		LOGGER.info("Inside init API ");
		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		initRequest.getContext().setConsumer_uri(abdmEUAURl);
		String providerURI = initRequest.getContext().getProvider_uri();
		String onRequestString = ow.writeValueAsString(initRequest);
		String requestMessageId = initRequest.getContext().getMessage_id();
		String clientId = initRequest.getContext().getConsumer_id();

		try {

			LOGGER.info("Provider URI :" + providerURI);
			LOGGER.info("Request Body :" + onRequestString);

			euaService.pushToMq(abdmGatewayURl, ConstantsUtils.INIT_ENDPOINT, initRequest, onRequestString, requestMessageId, clientId);


//			messageRepository
//					.save(new Message(requestMessageId, "", "on_init", Timestamp.from(ZonedDateTime.now().toInstant()), clientId));

			url = providerURI + ConstantsUtils.INIT_ENDPOINT;
			initRequest.getContext().setProvider_uri(url);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			String onSearchAckJsonErrorString = NACK_RESPONSE;
			AckResponse onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponse.class);
			mqService.sendNackResponse(onSearchAckJsonErrorString, requestMessageId);
		}
	}

	@PostMapping("/confirm")
	public void confirm(@RequestBody EuaRequestBody confirmRequest) throws JsonProcessingException {
		String url;
		LOGGER.info("Inside confirm API ");

		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();
		confirmRequest.getContext().setConsumer_uri(abdmEUAURl);
		String providerURI = confirmRequest.getContext().getProvider_uri();
		String onRequestString = ow.writeValueAsString(confirmRequest);
		String requestMessageId = confirmRequest.getContext().getMessage_id();
		String clientId = confirmRequest.getContext().getConsumer_id();

		try {
			LOGGER.info("Provider URI :" + providerURI);
			LOGGER.info("Request Body :" + onRequestString);

			euaService.pushToMq(providerURI, ConstantsUtils.CONFIRM_ENDPOINT, confirmRequest, onRequestString, requestMessageId, clientId);


//			messageRepository.save(
//					new Message(requestMessageId, "", "on_confirm", Timestamp.from(ZonedDateTime.now().toInstant()), clientId));


		} catch (Exception e) {
			LOGGER.error(e.toString());
			String onSearchAckJsonErrorString = NACK_RESPONSE;
			AckResponse onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponse.class);
			mqService.sendNackResponse(onSearchAckJsonErrorString, requestMessageId);
		}
	}

	@PostMapping("/status")
	public void status(@RequestBody EuaRequestBody statusRequest) throws JsonProcessingException {

		String url;
		LOGGER.info("Inside status API ");
		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		statusRequest.getContext().setConsumer_uri(abdmEUAURl);
		String providerURI = statusRequest.getContext().getProvider_uri();
		String onRequestString = ow.writeValueAsString(statusRequest);
		String requestMessageId = statusRequest.getContext().getMessage_id();
		String clientId = statusRequest.getContext().getConsumer_id();

		try {


			LOGGER.info("Provider URI :" + providerURI);
			LOGGER.info("Request Body :" + onRequestString);

			euaService.pushToMq(providerURI, ConstantsUtils.STATUS_ENDPOINT, statusRequest, onRequestString, requestMessageId, clientId);


//			messageRepository
//					.save(new Message(requestMessageId, "", "on_status", Timestamp.from(ZonedDateTime.now().toInstant()), clientId));


		} catch (Exception e) {
			LOGGER.error(e.toString());
			String onSearchAckJsonErrorString = NACK_RESPONSE;
			AckResponse onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponse.class);
			mqService.sendNackResponse(onSearchAckJsonErrorString, requestMessageId);
		}
	}

}
