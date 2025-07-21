package danielkjr.javamessagingapi.Model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "MQLogs")
public class MQLog {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(name = "Message")
    public String message;

    @Column(name = "Created")
    public Date created;

    @Column(name = "LastEdited")
    public Date lastEdited;

    @Column(name = "EditedByBroker")
    public String editedByBroker;


    public MQLog(String message, String editby){
        this.message=message;
        this.editedByBroker=editby;
        created=new Date();
        lastEdited=new Date();

    }

    public MQLog() {

    }
}
