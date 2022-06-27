package SWT2.repository;

public interface MqttRepository {
    /**
     * @param topic
     * @param message
     */
    void publishMessage(String topic, String message);


    void disconnect();
}

