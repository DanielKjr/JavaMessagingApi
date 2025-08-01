package danielkjr.mqlistener.Model;

import java.util.UUID;

public class ActionTrackDto {


    private UUID id;
    private Status status;


    private MQAction currentAction;

    public UUID getId() {return id;}

    public Status getStatus() {return status;}
    public MQAction getCurrentAction() {return currentAction;}
}
