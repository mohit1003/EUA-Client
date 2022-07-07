package in.gov.abdm.eua.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import in.gov.abdm.eua.service.constants.ConstantsUtils;
import in.gov.abdm.eua.service.dto.dhp.AckResponseDTO;
import in.gov.abdm.eua.service.dto.dhp.EuaRequestBody;
import in.gov.abdm.eua.service.dto.dhp.MqMessageTO;
import in.gov.abdm.eua.service.service.impl.EuaServiceImpl;
import in.gov.abdm.eua.service.service.impl.MQConsumerServiceImpl;
import in.gov.abdm.uhi.common.dto.Fulfillment;
import in.gov.abdm.uhi.common.dto.MessageAck;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Tag(name = "EUA service", description = "These APIs are intended to be used for service discovery and booking. Subsequent calls shall be redirected to UHI gateway and/or HSPA")
@RequestMapping("api/v1/euaService/")
@RestController
@Slf4j
public class EuaController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EuaController.class);
	private final MQConsumerServiceImpl mqService;

	private final String abdmEUAURl;

	private final String bookignServiceUrl;

	private final String abdmGatewayUrl;

	private final EuaServiceImpl euaService;

	private ObjectMapper objectMapper;

	public EuaController(EuaServiceImpl euaService, ObjectMapper objectMapper,
						@Value("${spring.abdm_eua_url}")
						String abdmEUAURl,
						 @Value("${spring.abdm_bookingService_url}")
						 String bookignServiceUrl,
						 @Value("${spring.abdm_gateway_url}")
						 String abdmGatewayUrl,
						 MQConsumerServiceImpl mqConsumerServiceImpl
						 ) {

		this.mqService = mqConsumerServiceImpl;

		this.euaService = euaService;
		this.objectMapper = objectMapper;

		this.abdmGatewayUrl = abdmGatewayUrl;
		this.abdmEUAURl = abdmEUAURl;
		this.bookignServiceUrl = bookignServiceUrl;

		LOGGER.info("EUA url "+ abdmEUAURl);
		LOGGER.info("Gateway url "+ abdmGatewayUrl);
		LOGGER.info("bookignServiceUrl "+ bookignServiceUrl);
	}


	@PostMapping(ConstantsUtils.ON_SEARCH_ENDPOINT)
	@Operation(
			summary = "on-search",
			description = "Gets the response from the gateway or HSPA to reveal search results to EUA",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = AckResponseDTO.class))
					),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<AckResponseDTO> onSearch(@RequestBody EuaRequestBody onSearchRequest) throws JsonProcessingException {

		LOGGER.info("Inside on_search API");

		ObjectWriter ow = new ObjectMapper().writer();
		try {
			ResponseEntity<AckResponseDTO> onSearchAck = getResponseEntityForErrorCases(onSearchRequest, objectMapper);
			if (onSearchAck != null) return onSearchAck;

			LOGGER.info("Message ID is "+ onSearchRequest.getContext().getMessageId());
			String onRequestString = ow.writeValueAsString(onSearchRequest);
			String requestMessageId = onSearchRequest.getContext().getMessageId();

			LOGGER.info("Request Body :" + onRequestString);
			MqMessageTO message = euaService.extractMessage(requestMessageId, onSearchRequest.getContext().getConsumerId(), onRequestString, onSearchRequest.getContext().getAction());

			euaService.pushToMqGatewayTOEua(message, requestMessageId);

			return euaService.getOnAckResponseResponseEntity(objectMapper, onRequestString, "on_search", requestMessageId);
		} catch (Exception e) {

			LOGGER.error(e.toString());

			return getNackResponseResponseEntityWithoutMono();
		}
	}


	@PostMapping("/on_select")
	public ResponseEntity<AckResponseDTO> onSelect(@RequestBody EuaRequestBody onSelectRequest) throws JsonProcessingException {

		LOGGER.info("Inside on_select API ");

		ObjectWriter ow = new ObjectMapper().writer();

		try {
			ResponseEntity<AckResponseDTO> onSelectAck = getResponseEntityForErrorCases(onSelectRequest, objectMapper);
			if (onSelectAck != null) return onSelectAck;

			String onRequestString = ow.writeValueAsString(onSelectRequest);
			String requestMessageId = onSelectRequest.getContext().getMessageId();

			LOGGER.info("Request Body :" + onRequestString);
			MqMessageTO message = euaService.extractMessage(requestMessageId, onSelectRequest.getContext().getConsumerId(), onRequestString, onSelectRequest.getContext().getAction());
			euaService.pushToMqGatewayTOEua(message, requestMessageId);

			return euaService.getOnAckResponseResponseEntity(objectMapper, onRequestString, "on_select", requestMessageId);

		} catch (Exception e) {

			LOGGER.error(e.toString());

			return getNackResponseResponseEntityWithoutMono();

		}

	}

	@PostMapping("/on_init")
	@Operation(
			summary = "on-init",
			description = "Gets the response from the HSPA to reveal service initialization and payment initialization results to EUA",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageAck.class))
					),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<Mono<AckResponseDTO>> onInit(@RequestBody EuaRequestBody onInitRequest) throws JsonProcessingException {

		LOGGER.info("Inside on_init API ");

		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		try{
			ResponseEntity<AckResponseDTO> onInitAckResp = getResponseEntityForErrorCases(onInitRequest, objectMapper);
			if (onInitAckResp != null) {
				ResponseEntity<AckResponseDTO> bodyError = ResponseEntity.status(onInitAckResp.getStatusCode()).body(Objects.requireNonNull(onInitAckResp.getBody()));
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Mono.just(Objects.requireNonNull(bodyError.getBody())));
			}

			String onRequestString = ow.writeValueAsString(onInitRequest);
			String requestMessageId = onInitRequest.getContext().getMessageId();

			LOGGER.info("Request Body :" + onRequestString);
			final MqMessageTO message = euaService.extractMessage(requestMessageId, onInitRequest.getContext().getConsumerId(), onRequestString, onInitRequest.getContext().getAction());

			Mono<AckResponseDTO> callToBookingService = mqService.getAckResponseResponseEntity(onInitRequest,  bookignServiceUrl);

				LOGGER.info("printing response from booking service :: "+callToBookingService);
				euaService.pushToMqGatewayTOEua(message, requestMessageId);

			return ResponseEntity.status(HttpStatus.OK).body(callToBookingService);


		} catch (Exception e) {
			LOGGER.error(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Mono.just(Objects.requireNonNull(getNackResponseResponseEntityWithoutMono().getBody())));
		}
	}

	@PostMapping("/on_confirm")
	@Operation(
			summary = "on-confirm",
			description = "Gets the response from the HSPA to reveal service confirmation results to EUA",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageAck.class))
					),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<Mono<AckResponseDTO>> onConfirm(@RequestBody EuaRequestBody onConfirmRequest) throws JsonProcessingException {

		LOGGER.info("Inside on_confirm API ");

		ObjectWriter ow = new ObjectMapper().writer();

		try {

			ResponseEntity<AckResponseDTO> onConfirmRequestAck = getResponseEntityForErrorCases(onConfirmRequest, objectMapper);
			if (onConfirmRequestAck != null) {
				ResponseEntity<AckResponseDTO> bodyError = ResponseEntity.status(onConfirmRequestAck.getStatusCode()).body(Objects.requireNonNull(onConfirmRequestAck.getBody()));
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Mono.just(Objects.requireNonNull(bodyError.getBody())));

			}
			String onRequestString = ow.writeValueAsString(onConfirmRequest);
			String requestMessageId = onConfirmRequest.getContext().getMessageId();

			LOGGER.info("Request Body :" + onRequestString);
			MqMessageTO message = euaService.extractMessage(requestMessageId, onConfirmRequest.getContext().getConsumerId(), onRequestString, onConfirmRequest.getContext().getAction());

			Mono<AckResponseDTO> callToBookingService = mqService.getAckResponseResponseEntity(onConfirmRequest,  bookignServiceUrl);

				LOGGER.info("printing response from booking service :: "+callToBookingService);
				euaService.pushToMqGatewayTOEua(message, requestMessageId);

			return ResponseEntity.status(HttpStatus.OK).body(callToBookingService);

		} catch (Exception e) {
			LOGGER.error(e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Mono.just(Objects.requireNonNull(getNackResponseResponseEntityWithoutMono().getBody())));
		}

	}

	@PostMapping("/on_status")
	@Operation(
			summary = "on-status",
			description = "Gets the response from the HSPA to reveal latest status of service to EUA",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageAck.class))
					),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<AckResponseDTO> onStatus(@RequestBody EuaRequestBody onStatusRequest) throws JsonProcessingException {
		LOGGER.info("Inside on_status API ");
		ObjectWriter ow = new ObjectMapper().writer();

		try {
			String onRequestString = ow.writeValueAsString(onStatusRequest);
			String requestMessageId = onStatusRequest.getContext().getMessageId();

			ResponseEntity<AckResponseDTO> onSelectAck = getResponseEntityForErrorCases(onStatusRequest, objectMapper);
			if (onSelectAck != null) return onSelectAck;

			LOGGER.info("Request Body :" + onRequestString);
			MqMessageTO message = euaService.extractMessage(requestMessageId, onStatusRequest.getContext().getConsumerId(), onRequestString, onStatusRequest.getContext().getAction());

			euaService.pushToMqGatewayTOEua(message, requestMessageId);

			return euaService.getOnAckResponseResponseEntity(objectMapper, onRequestString, "on_status", requestMessageId);

		} catch (Exception e) {
			LOGGER.error(e.toString());
			return getNackResponseResponseEntityWithoutMono();

		}

	}

	@PostMapping("/on_cancel")
	@Operation(
			summary = "on-status",
			description = "Gets the response from the HSPA to reveal cancellation status of service to EUA",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageAck.class))
					),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<AckResponseDTO> onCancel(@RequestBody EuaRequestBody onCancelRequest) throws JsonProcessingException {
		LOGGER.info("Inside on_cancel API ");
		ObjectWriter ow = new ObjectMapper().writer();

		try {
			String onRequestString = ow.writeValueAsString(onCancelRequest);
			String requestMessageId = onCancelRequest.getContext().getMessageId();

			ResponseEntity<AckResponseDTO> onCancelAck = getResponseEntityForErrorCases(onCancelRequest, objectMapper);
			if (onCancelAck != null) return onCancelAck;

			LOGGER.info("Request Body :" + onRequestString);
			MqMessageTO message = euaService.extractMessage(requestMessageId, onCancelRequest.getContext().getConsumerId(), onRequestString, onCancelRequest.getContext().getAction());

			euaService.pushToMqGatewayTOEua(message, requestMessageId);

			return euaService.getOnAckResponseResponseEntity(objectMapper, onRequestString, "on_cancel", requestMessageId);

		} catch (Exception e) {
			LOGGER.error(e.toString());
			return getNackResponseResponseEntityWithoutMono();

		}

	}

	private ResponseEntity<AckResponseDTO> getNackResponseResponseEntityWithoutMono() throws JsonProcessingException {
		AckResponseDTO onSearchAck = objectMapper.readValue(ConstantsUtils.NACK_RESPONSE, AckResponseDTO.class);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(onSearchAck);
	}

	@PostMapping(ConstantsUtils.SEARCH_ENDPOINT)
	@Operation(
			summary = "search",
			description = "This API call would be used in two phases. <br> " +
					"1. 1st search call to enable service discovery for e.g. to get list of doctors matching the search criteria specified in this search call. This call is forwarded to UHI gateway <br>" +
					"2. 2nd search call to get information about specified doctor selected in 2nd search call.",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageAck.class))
					),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<AckResponseDTO> search(@RequestBody EuaRequestBody searchRequest) throws JsonProcessingException {

		LOGGER.info("Inside Search API ");
		ObjectWriter ow = new ObjectMapper().writer();

		ResponseEntity<AckResponseDTO> searchAck = getResponseEntityForErrorCases(searchRequest, objectMapper);
		if (searchAck != null)
			return searchAck;
		else
			searchAck = ResponseEntity.status(HttpStatus.OK).build();

		searchRequest.getContext().setConsumerUri(abdmEUAURl);
		String onRequestString = ow.writeValueAsString(searchRequest);
		String requestMessageId = searchRequest.getContext().getMessageId();
		String clientId = searchRequest.getContext().getConsumerId();
		String action = searchRequest.getContext().getAction();


		try {
			LOGGER.info("Request Body :" + onRequestString);
			euaService.pushToMq( onRequestString,clientId, action, requestMessageId);

			LOGGER.info("Request Body enqueued successfully:" + onRequestString);

		}
		catch (Exception e) {
			LOGGER.error(e.toString());

			mqService.prepareAndSendNackResponse(ConstantsUtils.NACK_RESPONSE, requestMessageId);
			return getNackResponseResponseEntityWithoutMono();
		}

		return searchAck;
	}



	@PostMapping("/select")
	public ResponseEntity<AckResponseDTO> select(@RequestBody EuaRequestBody selectRequest) throws JsonProcessingException {

		LOGGER.info("Inside select API ");
		ObjectWriter ow = new ObjectMapper().writer();

		ResponseEntity<AckResponseDTO> searchAck = getResponseEntityForErrorCases(selectRequest, objectMapper);
		if (searchAck != null)
			return searchAck;
		else
			searchAck = ResponseEntity.status(HttpStatus.OK).build();


		selectRequest.getContext().setConsumerUri(abdmEUAURl);
		String providerURI = selectRequest.getContext().getProviderUri();
		String onRequestString = ow.writeValueAsString(selectRequest);
		String requestMessageId = selectRequest.getContext().getMessageId();
		String clientId = selectRequest.getContext().getConsumerId();
		String action = selectRequest.getContext().getAction();


		try {
			LOGGER.info("Provider URI :" + providerURI);

			LOGGER.info("Request Body :" + onRequestString);
			euaService.pushToMq(onRequestString, clientId, action, requestMessageId);
			LOGGER.info("Request Body enqueued successfully:" + onRequestString);


		} catch (Exception e) {
			LOGGER.error(e.toString());
			mqService.prepareAndSendNackResponse(ConstantsUtils.NACK_RESPONSE, requestMessageId);
			return getNackResponseResponseEntityWithoutMono();
		}
		return searchAck;

	}


	@PostMapping("/init")
	@Operation(
			summary = "init",
			description = "Initialize the service and payment for the selected doctor or service in second search call",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageAck.class))
					),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<AckResponseDTO> init(@RequestBody EuaRequestBody initRequest) throws JsonProcessingException {
		String url;
		LOGGER.info("Inside init API ");
		ObjectWriter ow = new ObjectMapper().writer();

		ResponseEntity<AckResponseDTO> searchAck = getResponseEntityForErrorCases(initRequest, objectMapper);
		if (searchAck != null)
			return searchAck;
		else
			searchAck = ResponseEntity.status(HttpStatus.OK).build();

		initRequest.getContext().setConsumerUri(abdmEUAURl);
		String providerURI = initRequest.getContext().getProviderUri();
		String onRequestString = ow.writeValueAsString(initRequest);
		String requestMessageId = initRequest.getContext().getMessageId();
		String clientId = initRequest.getContext().getConsumerId();
		String action = initRequest.getContext().getAction();


		try {
			LOGGER.info("Provider URI :" + providerURI);
			LOGGER.info("Request Body :" + onRequestString);

			euaService.pushToMq(onRequestString, clientId,action, requestMessageId);

			LOGGER.info("Request Body enqueued successfully:" + onRequestString);

			url = providerURI + ConstantsUtils.INIT_ENDPOINT;
			initRequest.getContext().setProviderUri(url);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			mqService.prepareAndSendNackResponse(ConstantsUtils.NACK_RESPONSE, requestMessageId);
			return getNackResponseResponseEntityWithoutMono();
		}
		return searchAck;

	}

	@PostMapping("/confirm")
	@Operation(
			summary = "confirm",
			description = "This API is used for confirmation of the services offered by HSPA",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageAck.class))
					),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<AckResponseDTO> confirm(@RequestBody EuaRequestBody confirmRequest) throws JsonProcessingException {
		LOGGER.info("Inside confirm API ");

		ObjectWriter ow = new ObjectMapper().writer();
		confirmRequest.getContext().setConsumerUri(abdmEUAURl);
		String providerURI = confirmRequest.getContext().getProviderUri();
		String onRequestString = ow.writeValueAsString(confirmRequest);
		String requestMessageId = confirmRequest.getContext().getMessageId();
		String clientId = confirmRequest.getContext().getConsumerId();
		String action = confirmRequest.getContext().getAction();


		ResponseEntity<AckResponseDTO> searchAck = getResponseEntityForErrorCases(confirmRequest, objectMapper);
		if (searchAck != null)
			return searchAck;
		else
			searchAck = ResponseEntity.status(HttpStatus.OK).build();

		try {
			LOGGER.info("Provider URI :" + providerURI);
			LOGGER.info("Request Body :" + onRequestString);

			euaService.pushToMq(onRequestString, clientId, action, requestMessageId);

			LOGGER.info("Request Body enqueued successfully:" + onRequestString);


		} catch (Exception e) {
			LOGGER.error(e.toString());
			mqService.prepareAndSendNackResponse(ConstantsUtils.NACK_RESPONSE, requestMessageId);
			return getNackResponseResponseEntityWithoutMono();
		}
		return searchAck;

	}

	@PostMapping("/status")
	@Operation(
			summary = "status",
			description = "This Api request enables the latest status from the revealed from the HSPA",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageAck.class))
					),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<AckResponseDTO> status(@RequestBody EuaRequestBody statusRequest) throws JsonProcessingException {

		LOGGER.info("Inside status API ");
		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		statusRequest.getContext().setConsumerUri(abdmEUAURl);
		String providerURI = statusRequest.getContext().getProviderUri();
		String onRequestString = ow.writeValueAsString(statusRequest);
		String requestMessageId = statusRequest.getContext().getMessageId();
		String clientId = statusRequest.getContext().getConsumerId();
		String action = statusRequest.getContext().getAction();


		ResponseEntity<AckResponseDTO> searchAck = getResponseEntityForErrorCases(statusRequest, objectMapper);
		if (searchAck != null)
			return searchAck;
		else
			searchAck = ResponseEntity.status(HttpStatus.OK).build();
		try {

			LOGGER.info("Provider URI :" + providerURI);
			LOGGER.info("Request Body :" + onRequestString);

			euaService.pushToMq(onRequestString, clientId, action, requestMessageId);

			LOGGER.info("Request Body enqueued successfully:" + onRequestString);

		} catch (Exception e) {
			LOGGER.error(e.toString());
			mqService.prepareAndSendNackResponse(ConstantsUtils.NACK_RESPONSE, requestMessageId);
			return getNackResponseResponseEntityWithoutMono();
		}
		return searchAck;
	}

	@PostMapping("/cancel")
	@Operation(
			summary = "status",
			description = "This Api request cancels currently confirmed service request",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageAck.class))
					),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<AckResponseDTO> cancel(@RequestBody EuaRequestBody cancelRequest) throws JsonProcessingException {

		LOGGER.info("Inside cancel API ");
		ObjectWriter ow = new ObjectMapper().writer();
		ObjectMapper objectMapper = new ObjectMapper();

		cancelRequest.getContext().setConsumerUri(abdmEUAURl);
		String providerURI = cancelRequest.getContext().getProviderUri();
		String onRequestString = ow.writeValueAsString(cancelRequest);
		String requestMessageId = cancelRequest.getContext().getMessageId();
		String clientId = cancelRequest.getContext().getConsumerId();
		String action = cancelRequest.getContext().getAction();

		ResponseEntity<AckResponseDTO> searchAck = getResponseEntityForErrorCases(cancelRequest, objectMapper);
		if (searchAck != null)
			return searchAck;
		else
			searchAck = ResponseEntity.status(HttpStatus.OK).build();
		try {

			LOGGER.info("Provider URI :" + providerURI);
			LOGGER.info("Request Body :" + onRequestString);

			euaService.pushToMq(onRequestString, clientId, action, requestMessageId);

			LOGGER.info("Request Body enqueued successfully:" + onRequestString);

		} catch (Exception e) {
			LOGGER.error(e.toString());
			mqService.prepareAndSendNackResponse(ConstantsUtils.NACK_RESPONSE, requestMessageId);
			return getNackResponseResponseEntityWithoutMono();
		}
		return searchAck;
	}



	private ResponseEntity<AckResponseDTO> getResponseEntityForErrorCases(EuaRequestBody onSearchRequest, ObjectMapper objectMapper) throws JsonProcessingException {
		ResponseEntity<AckResponseDTO> onSearchAck = checkIfMessageIsNull(onSearchRequest, objectMapper);
		if (onSearchAck != null) return onSearchAck;

		ResponseEntity<AckResponseDTO> onSearchAck1 = checkIfContextIsNull(onSearchRequest, objectMapper);
		if (onSearchAck1 != null) return onSearchAck1;


		return checkIfMandatoryfieldsInContextAreNull(onSearchRequest, objectMapper);
	}


	private ResponseEntity<AckResponseDTO> checkIfPersonNameIsNull(ObjectMapper objectMapper, List<Fulfillment> fulfillmentPersonWithNoNameList) throws JsonProcessingException {
		if(!fulfillmentPersonWithNoNameList.isEmpty()) {
			String onSearchAckJsonErrorString = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Mandatory field person name in one of the result is null\" } }";
			AckResponseDTO onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponseDTO.class);
			return new ResponseEntity<>(onSearchAck, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}

	private ResponseEntity<AckResponseDTO> checkIfMandatoryfieldsInContextAreNull(EuaRequestBody onSearchRequest, ObjectMapper objectMapper) throws JsonProcessingException {
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
			AckResponseDTO onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponseDTO.class);
			return new ResponseEntity<>(onSearchAck, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}

	private ResponseEntity<AckResponseDTO> checkIfContextIsNull(EuaRequestBody onSearchRequest, ObjectMapper objectMapper) throws JsonProcessingException {
		if(null == onSearchRequest.getContext()) {
			String onSearchAckJsonErrorString = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Context is Null\" } }";
			AckResponseDTO onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponseDTO.class);
			return new ResponseEntity<>(onSearchAck, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}

	private ResponseEntity<AckResponseDTO> checkIfMessageIsNull(EuaRequestBody onSearchRequest, ObjectMapper objectMapper) throws JsonProcessingException {
		if(null == onSearchRequest.getMessage()) {
			String onSearchAckJsonErrorString = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Message is Null\" } }";
			AckResponseDTO onSearchAck = objectMapper.readValue(onSearchAckJsonErrorString, AckResponseDTO.class);
			return new ResponseEntity<>(onSearchAck, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}
}
