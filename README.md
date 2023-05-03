# Apache Airavata Data Catalog

## Getting started

Start the PostgreSQL database in a docker container

```
docker-compose up
```

Run the API server

```
mvn install
cd data-catalog-api/server/service
mvn spring-boot:run
```

Run the API client

```
mvn install
cd data-catalog-api/client
mvn exec:java -Dexec.mainClass=org.apache.airavata.datacatalog.api.client.DataCatalogAPIClient
```
