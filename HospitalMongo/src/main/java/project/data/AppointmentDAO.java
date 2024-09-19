package project.data;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import project.domain.Appointment;
import org.bson.types.ObjectId;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
public class AppointmentDAO {
    private final MongoCollection<Document> collection;

    public AppointmentDAO(MongoDatabase database) {
        this.collection = database.getCollection("citas");
    }

    public void create(Appointment appointment) {
        Document doc = new Document()
                .append("fecha", appointment.getFecha().toString())
                .append("especialidad", appointment.getEspecialidad())
                .append("idDoctor", new ObjectId(appointment.getIdDoctor()))  // Convertimos el idDoctor a ObjectId
                .append("idPaciente", new ObjectId(appointment.getIdPaciente()))  // Convertimos el idPaciente a ObjectId
                .append("observaciones", appointment.getObservaciones());

        collection.insertOne(doc);

        ObjectId id = doc.getObjectId("_id");
        appointment.setId(id.toString());
    }

    public List<Appointment> getAll() {
        List<Appointment> appointments = new ArrayList<>();
        for (Document doc : collection.find()) {
            appointments.add(convertToAppointment(doc));
        }
        return appointments;
    }

    public Appointment getById(String id) {
        Document doc = collection.find(eq("_id", new ObjectId(id))).first();
        return doc != null ? convertToAppointment(doc) : null;
    }

    public void update(String id, Appointment updatedAppointment) {
        Document updatedDoc = new Document("fecha", updatedAppointment.getFecha().toString())
                .append("especialidad", updatedAppointment.getEspecialidad())
                .append("idDoctor", new ObjectId(updatedAppointment.getIdDoctor()))
                .append("idPaciente", new ObjectId(updatedAppointment.getIdPaciente()))
                .append("observaciones", updatedAppointment.getObservaciones());

        collection.updateOne(eq("_id", new ObjectId(id)), new Document("$set", updatedDoc));
        System.out.println("Cita actualizada con éxito.");
    }

    public void delete(String id) {
        collection.deleteOne(eq("_id", new ObjectId(id)));
        System.out.println("Cita eliminada con éxito.");
    }

    private Appointment convertToAppointment(Document doc) {
        LocalDate fecha = LocalDate.parse(doc.getString("fecha"));
        String especialidad = doc.getString("especialidad");
        String idDoctor = doc.getObjectId("idDoctor").toString();
        String idPaciente = doc.getObjectId("idPaciente").toString();
        String observaciones = doc.getString("observaciones");
        String id = doc.getObjectId("_id").toString();

        return new Appointment(id, fecha, especialidad, idDoctor, idPaciente, observaciones);
    }
}

