package project.data;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import project.domain.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    private final MongoCollection<Document> collection;

    public DoctorDAO(MongoDatabase database) {
        this.collection = database.getCollection("doctor");
    }

    public void create(Doctor doctor) {
        Document doc = new Document()
                .append("nombre", doctor.getNombre())
                .append("especialidad", doctor.getEspecialidad())
                .append("año_experiencia", doctor.getAñosexperiencia());
        collection.insertOne(doc);

        ObjectId id = doc.getObjectId("_id");
        doctor.setId(id.toString());
    }

    public List<Doctor> getAll() {
        List<Doctor> doctors = new ArrayList<>();
        for (Document doc : collection.find()) {
            doctors.add(convertToDoctor(doc));
        }
        return doctors;
    }

    public Doctor getById(String id) {
        Document doc = collection.find(new Document("_id", id)).first();
        return doc != null ? convertToDoctor(doc) : null;
    }

    public void update(String id, Doctor doctor) {
        Document updatedDoc = new Document("nombre", doctor.getNombre())
                .append("especialidad", doctor.getEspecialidad())
                .append("año_experiencia", doctor.getAñosexperiencia());
        collection.updateOne(new Document("_id", id), new Document("$set", updatedDoc));
    }

    public void delete(String id) {
        collection.deleteOne(new Document("_id", id));
    }

    private Doctor convertToDoctor(Document doc) {
        return new Doctor(
                doc.getObjectId("_id").toString(),
                doc.getString("nombre"),
                doc.getString("especialidad"),
                doc.getInteger("año_experiencia")
        );
    }
}
