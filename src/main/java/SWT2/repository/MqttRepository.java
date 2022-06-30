package SWT2.repository;

public interface MqttRepository {
    void publishMessage(String topic, String message);


    void disconnect();
}

