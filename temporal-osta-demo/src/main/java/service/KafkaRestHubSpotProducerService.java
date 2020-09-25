package service;


import constant.HubSpotConstants;
import lombok.extern.slf4j.Slf4j;
import model.Company;
import model.HubSpotEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class KafkaRestHubSpotProducerService {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Created using the following
     * 1) zookeeper-server-start ..\..\config\zookeeper.properties
     * 2) kafka-server-start ..\..\config\server.properties
     * 3) Create topic:
     * 		kafka-topics --create --zookeeper localhost:2181
     * 	   --replication-factor 1 --partitions 1 --topic KAFKA_EXAMPLE_TOPIC
     * 4) Consume from console:
     * 		kafka-console-consumer --bootstrap-server localhost:9092 --topic KAFKA_EXAMPLE_TOPIC
     * 		--from-beginning
     * 5) Produce from console:
     * 		 kafka-console-producer --broker-list localhost:9092 --topic KAFKA_EXAMPLE_TOPIC
     * @param data
     */
    public void publishData(Object data){

        kafkaTemplate.send(HubSpotConstants.HUB_SPOT_TILL_HUB_KAFKA_TOPIC, getKey(data), data).addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info("Error publishing " + data);
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringUserSendResult) {
                log.info("\u001B[32m"+"Published successfully to Kafka " + "\u001B[35m"+ stringUserSendResult.toString());
            }
        });

    }

    private String getKey(Object data) {
        String key;
        if(data instanceof Company){
            key = HubSpotEntities.COMPANIES.name();
        } else {
            key = HubSpotEntities.DEALS.name();
        }
        return key;
    }

}
