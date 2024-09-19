package project.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Conex {

    private MongoClient mongoClient;
    private MongoDatabase database;

    public void conectar() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("hospital");
        System.out.println("Conexión establecida con MongoDB");
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void cerrar() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexión cerrada");
        }
    }
}
