package danielkjr.mqlistener.Repository;


import danielkjr.mqlistener.Model.PlaceHolderEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlaceHolderEntryRepository extends JpaRepository<PlaceHolderEntry, UUID> {}

