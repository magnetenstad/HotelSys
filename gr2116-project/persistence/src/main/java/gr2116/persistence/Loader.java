package gr2116.persistence;

import gr2116.core.Amenity;
import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomType;
import gr2116.core.Person;
import gr2116.core.Reservation;
import org.json.JSONObject;
import java.util.Collection;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Loader {
  // TODO: Invalid data handling

  private static final Path METADATA_FOLDER
      = Paths.get(".").toAbsolutePath()
      .normalize().getParent().getParent().resolve("data");
  private boolean loaded = false;
  private Collection<Person> persons = new HashSet<Person>();
  private Collection<HotelRoom> rooms = new HashSet<HotelRoom>();
  private Map<String, Reservation> reservationMap
      = new HashMap<String, Reservation>();

  private void loadPersons(final JSONObject personsData) {
    if (rooms.size() == 0) {
      throw new IllegalStateException("Reservations not yet loaded.");
    }

    personsData.keySet().forEach((String k) -> {
      JSONObject personData = personsData.getJSONObject(k);
      Person person = new Person(personData.getString("name"));
      person.setEmail(personData.getString("email"));
      person.addBalance(personData.getDouble("balance"));

      personData.getJSONArray("reservations").forEach((reservationId) -> {
        String id = Long.toString((Long) reservationId);
        if (reservationMap.containsKey(id)) {
          person.addReservation(reservationMap.get(id));
        } else {
          throw new IllegalStateException(
            "Reservation " + id + " was not created before person."
          );
        }
      });
      persons.add(person);
    });
  }

  private Reservation getReservation(final JSONObject reservationsData,
                                    final String id, final HotelRoom room) {
    JSONObject reservationData;

    if (reservationMap.containsKey(id)) {
      throw new IllegalStateException("Multiple rooms for reservation " + id);
    } else if (reservationsData.has(id)) {
      reservationData = reservationsData.getJSONObject(id);
    } else {
      throw new IllegalStateException("Missing data for reservation " + id);
    }

    Reservation reservation = new Reservation(Long.parseLong(id),
        room,
        LocalDate.parse(reservationData.getString("startDate")),
        LocalDate.parse(reservationData.getString("endDate")));
    reservationMap.put(id, reservation);
    return reservation;
  }

  private void loadRoomsAndReservations(final JSONObject roomsData,
                                        final JSONObject reservationsData) {
    roomsData.keySet().forEach((String k) -> {
      JSONObject roomData = roomsData.getJSONObject(k);

      HotelRoom room = new HotelRoom(
        HotelRoomType.valueOf(roomData.getString("type")),
        roomData.getInt("number")
      );
      room.setPrice(roomData.getInt("price"));
      roomData.getJSONArray("amenities").forEach((amenity) -> {
        room.addAmenity(Amenity.valueOf((String) amenity));
      });

      roomData.getJSONArray("reservations").forEach((reservationId) -> {
        String id = Long.toString((Long) reservationId);
        Reservation reservation = getReservation(reservationsData, id, room);
        room.addReservation(reservation);
      });

      rooms.add(room);
    });
  }

  public final void loadData(final JSONObject roomsData,
                            final JSONObject personsData,
                            final JSONObject reservationsData) {
    loaded = true;
    loadRoomsAndReservations(roomsData, reservationsData);
    loadPersons(personsData);
  }

  public final Collection<Person> getPersons() {
    if (!loaded) {
      throw new IllegalStateException(
        "Objects must be loaded using loadData() before getting.");
    }
    return persons;
  }

  public final Collection<HotelRoom> getRooms() {
    if (!loaded) {
      throw new IllegalStateException(
        "Objects must be loaded using loadData() before getting.");
    }
    return rooms;
  }

  public final JSONObject getAsJSON(final String filename) throws IOException {
    String text = Files.readString(
        Paths.get(new File(METADATA_FOLDER + filename).getAbsolutePath()));
    JSONObject json = new JSONObject(text);
    return json;
  }

  public final void loadData() throws IOException {
    JSONObject personData = getAsJSON("/personData.json");
    JSONObject roomData = getAsJSON("/roomsData.json");
    JSONObject reservationData = getAsJSON("/reservationData.json");

    loadData(roomData, personData, reservationData);
  }
}
