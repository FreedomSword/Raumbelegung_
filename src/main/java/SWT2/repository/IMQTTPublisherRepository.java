package SWT2.repository;

public interface IMQTTPublisherRepository {
    /**
     * @param topic
     * @param message
     */
    void publishMessage(String topic, String message);


    void disconnect();
}

