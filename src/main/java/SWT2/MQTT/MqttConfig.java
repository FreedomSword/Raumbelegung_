package SWT2.MQTT;


public abstract class MqttConfig {

    protected String ip = "127.0.0.1";

    protected int qos = 2;

    protected Integer port = 1883; /* Default port */

    protected String username = "admin";

    protected String password = "12345678";

    protected String TCP = "tcp://";

    protected String SSL = "ssl://";


    protected abstract void config(String ip, Integer port, Boolean ssl, Boolean withUserNamePass);


    protected abstract void config();


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

