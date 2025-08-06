package danielkjr.mqlistener.Model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "PlaceHolderEntries", schema = "Messaging")
public class PlaceHolderEntry {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ActionTrack> actions = new ArrayList<>();

    @Column(name = "Message")
    public String message;


    public UUID getId() {
        return id;
    }

    public List<ActionTrack> getActions() {
        return actions;
    }

    public ActionTrack getAction() {
        return actions.getLast();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addAction(ActionTrack action) {
        action.setEntry(this);
        this.actions.add(action);
    }


    public PlaceHolderEntry(String message) {
        this.message = message;
    }

    public PlaceHolderEntry() {
    }

}
