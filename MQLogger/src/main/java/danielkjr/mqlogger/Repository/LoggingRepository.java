package danielkjr.mqlogger.Repository;

import danielkjr.mqlogger.Model.LogMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoggingRepository extends JpaRepository<LogMessage, UUID> {
}
