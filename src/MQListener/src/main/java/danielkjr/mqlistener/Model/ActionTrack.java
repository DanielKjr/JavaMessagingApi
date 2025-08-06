package danielkjr.mqlistener.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


import java.util.UUID;

@Entity
@Table(name = "Actions", schema = "Messaging")
public class ActionTrack {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entry_id")
    @JsonBackReference
    private PlaceHolderEntry entry;

    @Column(name = "Status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "CurrentAction")
    @Enumerated(EnumType.STRING)
    private MQAction currentAction;

    public MQAction getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(MQAction currentAction) {
        this.currentAction = currentAction;
    }

    public UUID getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PlaceHolderEntry getEntry() {
        return entry;
    }

    public void setEntry(PlaceHolderEntry entry) {
        this.entry = entry;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public ActionTrack() {
    }

    public ActionTrack(StoreCommand command) {
        this.status = Status.CREATED;
        this.currentAction = command.action();
    }

}
