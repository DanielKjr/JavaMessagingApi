package danielkjr.javamessagingapi.Repositories;

import danielkjr.javamessagingapi.Model.PlaceHolderEntry;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface PlaceHolderEntryRepository extends JpaRepository<PlaceHolderEntry, UUID> {}

