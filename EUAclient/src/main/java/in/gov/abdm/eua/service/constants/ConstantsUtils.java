package in.gov.abdm.eua.service.constants;

public class ConstantsUtils {

    // ENDPOINTS
    public static final String STATUS_ENDPOINT = "/status";
    public static final String CONFIRM_ENDPOINT = "/confirm";
    public static final String INIT_ENDPOINT = "/init";
    public static final String SELECT_ENDPOINT = "/select";
    public static final String SEARCH_ENDPOINT = "/search";
    public static final String WEBSOCKET_CONNECT_ENDPOINT = "/eua-client";
    public static final String EUA_TO_CLIENT_WEBSOCKET_CONNECT_ENDPOINT = "/euaToClient";
    public static final String ON_SEARCH_ENDPOINT = "/on_search";



    // BROKER CONFIG
    public static final String DESTINATION = "/client";
    public static final String BROKER = "/user/queue/specific-user";
    public static final String USER_DESTINATION_PREFIX = "/user";
    public static final String APPLICATION_DESTINATION_PREFIX = "/eua";


    // RABBIT MQ CONFIG
    public static final String QUEUE_EUA_TO_GATEWAY = "search_queue";
    public static final String QUEUE_GATEWAY_TO_EUA = "on_search_queue";
    public static final String EXCHANGE = "eua_exchange";
    public static final String ROUTING_KEY_EUA_TO_GATEWAY = "search_routingKey";
    public static final String ROUTING_KEY_GATEWAY_TO_EUA = "on_search_routingKey";


    public static final Object HEALTH_ID_NO_PATTERN = "^[0-9]{2}-*[0-9]{4}-*[0-9]{4}-*[0-9]{4}$";

    public static final String NACK_RESPONSE = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Something went wrong\" } }";

}
