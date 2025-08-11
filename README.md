Sprint boot api for simple triggering of endpoint through Postman.

API sends a message to MQListener that sends a log to MQLogger, using shared functionality from the MQLoggingClient package and then saves the entry.

JavaMessagingAPI: basic triggers through postman\
MQListener: Contains the listener(s?) consuming events\
MQLogger: Contains the listener/consumer that receives logs and saves them to a local PostgreSQL database.\
MQLoggingClient: Contains the complete setup of Beans used to contact the MQLogger, DTO's and routes/queues etc. \
- [x]  Client is also a package so that containers can access the functionality
- [x]  Docker orchestration
- [x]  Persistent messaging
