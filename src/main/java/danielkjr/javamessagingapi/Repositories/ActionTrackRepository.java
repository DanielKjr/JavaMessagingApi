package danielkjr.javamessagingapi.Repositories;

import danielkjr.javamessagingapi.Model.ActionTrack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActionTrackRepository extends JpaRepository<ActionTrack, UUID> {}
