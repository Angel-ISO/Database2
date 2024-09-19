package project.data;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import project.domain.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    private final MongoCollection<Document> collection;

    public PatientDAO(MongoDatabase database) {
        this.collection = database.getCollection("paciente");
    }

    public void create(Patient patient) {
        Document doc = new Document()
                .append("nombre", patient.getNombre())
                .append("edad", patient.getEdad())
                .append("género", patient.getGenero());

        collection.insertOne(doc);

        ObjectId id = doc.getObjectId("_id");
        patient.setId(id.toString());
    }

    public List<Patient> getAll() {
        List<Patient> patients = new ArrayList<>();
        for (Document doc : collection.find()) {
            patients.add(convertToPatient(doc));
        }
        return patients;
    }

    public Patient getById(String id) {
        Document doc = collection.find(new Document("_id", id)).first();
        return doc != null ? convertToPatient(doc) : null;
    }

    public void update(String id, Patient patient) {
        Document updatedDoc = new Document("nombre", patient.getNombre())
                .append("edad", patient.getEdad())
                .append("género", patient.getGenero());
        collection.updateOne(new Document("_id", id), new Document("$set", updatedDoc));
    }

    public void delete(String id) {
        collection.deleteOne(new Document("_id", id));
    }

    private Patient convertToPatient(Document doc) {
        return new Patient(
                doc.getObjectId("_id").toString(),
                doc.getString("nombre"),
                doc.getInteger("edad"),
                doc.getString("género")
        );
    }
}
