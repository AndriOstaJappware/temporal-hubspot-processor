package constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HubSpotConstants {

    public static final int MAX_HUB_SPOT_RECORDS_LIMIT = 100;
    public static final int MIN_LIMIT_REQUESTS_COUNT = 5;
    public static final int ONE_MINUTE = 60_000;
    public static final String HUB_SPOT_TILL_HUB_KAFKA_TOPIC = "TILL_HUB_TOPIC_V2";
}
