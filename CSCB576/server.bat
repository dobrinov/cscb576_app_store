set CLASSPATH=src;build\jar\CSCB576.jar
start rmiregistry

java -Djava.security.policy=src/server/server.policy -Djava.rmi.server.hostname=localhost server.AppStoreServer