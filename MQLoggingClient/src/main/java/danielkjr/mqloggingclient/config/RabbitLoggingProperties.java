package danielkjr.mqloggingclient.config;



public class RabbitLoggingProperties {

    private String queue = "log.queue";
    private String exchange = "log.exchange";
    private String routingKey = "log.route";

    public String getQueue() { return queue; }
    public void setQueue(String queue) { this.queue = queue; }

    public String getExchange() { return exchange; }
    public void setExchange(String exchange) { this.exchange = exchange; }

    public String getRoutingKey() { return routingKey; }
    public void setRoutingKey(String routingKey) { this.routingKey = routingKey; }
}
