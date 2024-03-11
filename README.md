# Proyecto practicas CQRS con WEBFLUX y CDC

## Pasos a ejecutar
1. Ejecutar docker-compose up sobre la raiz deployment
2. Ejecutar el siguiente comando para crear el conector de debezium
```sh
curl -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d '
{
 "name": "cqrs-test-connector",
 "config": {
   "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
   "database.hostname": "postgres",
   "database.port": "5432",
   "database.user": "postgres",
   "database.password": "postgres",
   "database.dbname" : "postgresdb",
   "database.server.name": "dbserver1",
   "plugin.name": "pgoutput"
 }
}'
```
O podrias hacerlo por postman
imagen de ejemplo
![alt text](https://media.discordapp.net/attachments/381652327247118337/1216538016114540634/image.png?ex=6600c051&is=65ee4b51&hm=08f67c96b5016206d6a7caa8088e2b0c120cfd214fefb88e9b4faa6fe792bd5b&=&format=webp&quality=lossless&width=1440&height=506)
3. Para probar que este funcionando y estes recibiendo eventos en el kafka que levantas con el docker-compose, puedes ejecutar el siguiente comando
puedes ejecutar:
```sh 
1. docker exec -it kafka /bin/bash  # te conectas al kafka 
2. kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic dbserver1.public.customer --from-beginning
```
si hacemos un insert o ya empezamos a mover las cosas con este ejemplo podremos ver como funciona tal y como se mostro en la charla

# Consideraciones- proximos pasos
Puedes juguetear con esto cambiarlo, alterarlo, incluirle nuevos listener, la idea es que entiendas como es este proceso, podrias incluir para tratar los evewntos que se emiten 
en la cola de rabbit, si quieres y incluir el adaptador para otra base de datos que persista esos eventos