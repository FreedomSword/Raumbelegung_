package SWT2.MQTT;


public abstract class MqttConfig {

    protected String ip = "127.0.0.1";


    protected int qos = 2;

    protected Boolean hasSSL = false; /* By default SSL is disabled */

    protected Integer port = 1883; /* Default port */

    protected String username = "admin";

    protected String password = "12345678";

    protected String TCP = "tcp://";

    protected String SSL = "ssl://";


    /**
     * Custom Configuration
     */
    protected abstract void config(String ip, Integer port, Boolean ssl, Boolean withUserNamePass);

    /**
     * Default Configuration
     */
    protected abstract void config();


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public Boolean getHasSSL() {
        return hasSSL;
    }

    public void setHasSSL(Boolean hasSSL) {
        this.hasSSL = hasSSL;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

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

    public String getTCP() {
        return TCP;
    }

    public void setTCP(String TCP) {
        this.TCP = TCP;
    }

    public String getSSL() {
        return SSL;
    }

    public void setSSL(String SSL) {
        this.SSL = SSL;
    }
}
