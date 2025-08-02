package danielkjr.javamessagingapi.Utility;


import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class NameProvider {

    private final Environment environment;

    public NameProvider(Environment environment) {
        this.environment = environment;
    }

    public String getName(){
        return environment.getProperty("spring.application.name");
    }
}
