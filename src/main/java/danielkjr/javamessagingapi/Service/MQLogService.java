package danielkjr.javamessagingapi.Service;

import danielkjr.javamessagingapi.MessageBroker.dbOperationMessage;
import danielkjr.javamessagingapi.Model.MQLog;
import danielkjr.javamessagingapi.Model.MQLogDto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MQLogService {

    private final EntityManager em;

    public MQLogService(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void addLog(dbOperationMessage msg) {
        MQLog  mqLog = new MQLog(msg.getData().message, msg.getBroker());
        em.persist(mqLog);
    }

    public List<MQLog> getLogs(){
        return em.createQuery("Select a from MQLog a", MQLog.class).getResultList();
    }


    public void patchLog(dbOperationMessage message) {
        MQLog existing = em.find(MQLog.class, message.getData().message);
        existing.editedByBroker = message.getBroker();
        existing.message = message.getData().toString();
        em.persist(existing);
    }
}
