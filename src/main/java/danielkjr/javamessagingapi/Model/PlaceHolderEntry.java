package danielkjr.javamessagingapi.Model;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PlaceHolderEntry {

    private UUID id;

    private List<ActionTrack> actions = new ArrayList<>();

    public String message;


    public UUID getId() {return id;}
    public List<ActionTrack> getActions() {return actions;}
    public ActionTrack getAction() {return actions.getLast();}
    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}

    public void addAction(ActionTrack action) {
        action.setEntry(this);
        this.actions.add(action);
    }


    public PlaceHolderEntry(String message) {
        this.message = message;
    }
    public PlaceHolderEntry( ) {}

}
