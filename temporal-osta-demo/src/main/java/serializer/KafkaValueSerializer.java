package serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

@Slf4j
public class KafkaValueSerializer implements Serializer<Object> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String s, Object contact) {
        try {
            return objectMapper.writeValueAsBytes(contact);
        } catch (JsonProcessingException e) {
            log.error("Unable to serialize object {}", contact, e);
            return null;
        }
    }
}
