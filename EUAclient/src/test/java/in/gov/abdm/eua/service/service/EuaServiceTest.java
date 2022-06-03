package in.gov.abdm.eua.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.gov.abdm.eua.service.constants.ConstantsUtils;
import in.gov.abdm.eua.service.dto.dhp.AckResponse;
import in.gov.abdm.eua.service.dto.dhp.EuaRequestBody;
import in.gov.abdm.eua.service.dto.dhp.MessageTO;
import in.gov.abdm.eua.service.model.Message;
import in.gov.abdm.eua.service.repository.EuaRepository;
import in.gov.abdm.eua.service.service.impl.EuaServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EuaServiceTest {

    RabbitTemplate rabbitTemplate;

    @Mock
    EuaRepository euaRepository;

    @Mock
    ModelMapper modelMapper;

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    EuaServiceImpl euaService;

    MessageTO message;

    @BeforeEach
    public void setUp() {

        rabbitTemplate = Mockito.mock(RabbitTemplate.class);
        MockitoAnnotations.openMocks(this);

        String dateTime = LocalDateTime.now().toString();
        message = new MessageTO();
        message.setResponse("response");
        message.setMessageId("129iedjed");
        message.setCreatedAt(dateTime);
        message.setDhpQueryType("search");
        message.setConsumerId("121223");
    }

    @Test
    void testGetOnAckResponseResponseEntityForAck() throws JsonProcessingException {
        String ack = "{\n" +
                "    \"message\": {\n" +
                "        \"ack\": {\n" +
                "            \"status\": \"ACK\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"error\": null\n" +
                "}";
        AckResponse ackResponse = objectMapper.readValue(ack, AckResponse.class);
        ResponseEntity<AckResponse> ans = ResponseEntity.status(HttpStatus.OK).body(ackResponse);

        Assertions.assertThat(euaService.getOnAckResponseResponseEntity(objectMapper, "sdvsdvsdvsdvsd", "search")).isEqualTo(ans);

    }

    @Test
    void testGetOnAckResponseResponseEntityForNack() throws JsonProcessingException {
        String ack = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Something went wrong\" } }";
        AckResponse ackResponse = objectMapper.readValue(ack, AckResponse.class);
        ResponseEntity<AckResponse> ans = ResponseEntity.status(HttpStatus.OK).body(ackResponse);

        Assertions.assertThat(euaService.getOnAckResponseResponseEntity(objectMapper, null, "search")).isEqualTo(ans);

    }
    @Test
    void testExtractMessage() {
        String dateTime = LocalDateTime.now().toString();
        MessageTO message = new MessageTO();
        message.setResponse("response");
        message.setMessageId("129iedjed");
        message.setCreatedAt(dateTime);
        message.setDhpQueryType("search");
        message.setConsumerId("121223");

        MessageTO testMesssage = euaService.extractMessage("129iedjed", "121223", "response", "search");
        testMesssage.setCreatedAt(dateTime);

        Assertions.assertThat(testMesssage).isEqualTo(message);
    }

    @Test
    public void testRabbitMq() throws JsonProcessingException {
        final EuaRequestBody requestBody;
        ObjectMapper objectMapper = new ObjectMapper();

        requestBody = objectMapper.readValue("{\n" +
                "  \"context\": {\n" +
                "    \"domain\": \"nic2004:mumm\",\n" +
                "    \"country\": \"IND\",\n" +
                "    \"city\": \"std:080\",\n" +
                "    \"provider_uri\": \"http://localhost:9090\",\n" +
                "    \"action\": \"search\",\n" +
                "    \"consumer_id\":\"1221\",\n" +
                "    \"core_version\": \"0.7.1\",\n" +
                "    \"message_id\": \"85a422c4-2867-4d72-b5f5-d31588e2f7c1552\",\n" +
                "    \"timestamp\": \"2021-03-23T10:00:40.065Z\"\n" +
                "  },\n" +
                "  \"message\": {\n" +
                "    \"catalog\": {\n" +
                "      \"descriptor\": {\n" +
                "        \"name\": \"Yonro\"\n" +
                "      },\n" +
                "      \"providers\": [\n" +
                "        {\n" +
                "          \"id\": \"289edce4-d002-4962-b311-4c025e22b4f6\",\n" +
                "          \"descriptor\": {\n" +
                "            \"name\": \"BAPP Hospitals\"\n" +
                "          },\n" +
                "          \"categories\": [\n" +
                "            {\n" +
                "              \"id\": \"1\",\n" +
                "              \"descriptor\": {\n" +
                "                \"name\": \"OPD\"\n" +
                "              }\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": \"2\",\n" +
                "              \"descriptor\": {\n" +
                "                \"name\": \"Diagnostics\"\n" +
                "              }\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": \"3\",\n" +
                "              \"descriptor\": {\n" +
                "                \"name\": \"Emergency\"\n" +
                "              }\n" +
                "            }\n" +
                "          ],\n" +
                "          \"fulfillments\": [\n" +
                "            {\n" +
                "              \"id\": \"1\",\n" +
                "              \"type\": \"DIGITAL-OPD\",\n" +
                "              \"person\": {\n" +
                "                \"id\": \"1\",\n" +
                "                \"name\": \"Dr Asthana\",\n" +
                "                \"gender\": \"male\",\n" +
                "                \"image\": \"https://image/of/person.png\",\n" +
                "                \"cred\": \"uhiId:237402938409485039850935\"\n" +
                "              },\n" +
                "              \"start\": {\n" +
                "                \"time\": {\n" +
                "                  \"timestamp\": \"T10:00Z\"\n" +
                "                }\n" +
                "              },\n" +
                "              \"end\": {\n" +
                "                \"time\": {\n" +
                "                  \"timestamp\": \"T10:15Z\"\n" +
                "                }\n" +
                "              }\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": \"2\",\n" +
                "              \"type\": \"DIGITAL-OPD\",\n" +
                "              \"person\": {\n" +
                "                \"id\": \"1\",\n" +
                "                \"name\": \"Dr Asthana\",\n" +
                "                \"gender\": \"male\",\n" +
                "                \"image\": \"https://image/of/person.png\",\n" +
                "                \"cred\": \"uhiId:237402938409485039850935\"\n" +
                "              },\n" +
                "              \"start\": {\n" +
                "                \"time\": {\n" +
                "                  \"timestamp\": \"T10:15Z\"\n" +
                "                }\n" +
                "              },\n" +
                "              \"end\": {\n" +
                "                \"time\": {\n" +
                "                  \"timestamp\": \"T10:30Z\"\n" +
                "                }\n" +
                "              }\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": \"3\",\n" +
                "              \"type\": \"DIGITAL-OPD\",\n" +
                "              \"person\": {\n" +
                "                \"id\": \"1\",\n" +
                "                \"name\": \"Dr Bhargava\",\n" +
                "                \"gender\": \"female\",\n" +
                "                \"image\": \"https://image/of/person.png\",\n" +
                "                \"cred\": \"uhiId:237402938409485039850935\"\n" +
                "              },\n" +
                "              \"start\": {\n" +
                "                \"time\": {\n" +
                "                  \"timestamp\": \"T10:00Z\"\n" +
                "                }\n" +
                "              },\n" +
                "              \"end\": {\n" +
                "                \"time\": {\n" +
                "                  \"timestamp\": \"T10:15Z\"\n" +
                "                }\n" +
                "              }\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": \"4\",\n" +
                "              \"type\": \"DIGITAL-OPD\",\n" +
                "              \"person\": {\n" +
                "                \"id\": \"1\",\n" +
                "                \"name\": \"Dr Bhargava\",\n" +
                "                \"gender\": \"female\",\n" +
                "                \"image\": \"https://image/of/person.png\",\n" +
                "                \"cred\": \"uhiId:237402938409485039850935\"\n" +
                "              },\n" +
                "              \"start\": {\n" +
                "                \"time\": {\n" +
                "                  \"timestamp\": \"T10:15Z\"\n" +
                "                }\n" +
                "              },\n" +
                "              \"end\": {\n" +
                "                \"time\": {\n" +
                "                  \"timestamp\": \"T10:30Z\"\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          ],\n" +
                "          \"items\": [\n" +
                "            {\n" +
                "              \"id\": \"1\",\n" +
                "              \"descriptor\": {\n" +
                "                \"name\": \"Consultation\"\n" +
                "              },\n" +
                "              \"category_id\": \"1\",\n" +
                "              \"fulfillment_id\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": \"1\",\n" +
                "              \"descriptor\": {\n" +
                "                \"name\": \"Consultation\"\n" +
                "              },\n" +
                "              \"category_id\": \"1\",\n" +
                "              \"fulfillment_id\": \"2\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": \"1\",\n" +
                "              \"descriptor\": {\n" +
                "                \"name\": \"Consultation\"\n" +
                "              },\n" +
                "              \"category_id\": \"1\",\n" +
                "              \"fulfillment_id\": \"3\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": \"1\",\n" +
                "              \"descriptor\": {\n" +
                "                \"name\": \"Consultation\"\n" +
                "              },\n" +
                "              \"category_id\": \"1\",\n" +
                "              \"fulfillment_id\": \"4\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}", EuaRequestBody.class);
        MessageTO message = new MessageTO();
        message.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
        message.setMessageId("Test");
        message.setResponse("Test");
        message.setConsumerId("Test");
        message.setDhpQueryType("search");

        Assertions.assertThatCode(() -> this.euaService.pushToMq("Test", "Test", requestBody,"Test","Test","Test")).doesNotThrowAnyException();
        Mockito.verify(this.rabbitTemplate)
                .convertAndSend(ConstantsUtils.EXCHANGE, ConstantsUtils.ROUTING_KEY_EUA_TO_GATEWAY, message);
    }

}