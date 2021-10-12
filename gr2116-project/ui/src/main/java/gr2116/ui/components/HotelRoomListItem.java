package gr2116.ui.components;

import gr2116.core.HotelRoom;
import gr2116.ui.utils.FXMLUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * A gui element for displaying hotel rooms in a list.
 */
public class HotelRoomListItem extends HBox {
    private HotelRoom room;

    @FXML
    private Label numberLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label amenitiesLabel;

    @FXML
    private Button makeReservationButton;

    /**
     * Constructs a HotelRoomListItem for the given HotelRoom.
     * @param room
     */
    public HotelRoomListItem(final HotelRoom room) {
        this.room = room;
        FXMLUtils.loadFXML(this);
    }

    @FXML
    private void initialize() {
        numberLabel.setText("HotelRoom " + room.getNumber());
        typeLabel.setText(room.getRoomType().getDescription());
        makeReservationButton.setText("Make reservation.");
        makeReservationButton.setDisable(true);
        String amenitiesText = "";
        for (String amenity : room.getAmenities()) {
            amenitiesText += amenity + ", ";
        }
        amenitiesLabel.setText(amenitiesText);
    }

    /**
     * Sets the action of the makeReservationButton.
     * @param eventHandler
     */
    public final void setOnMakeReservationButtonAction(
            final EventHandler<ActionEvent> eventHandler) {
        makeReservationButton.setOnAction(eventHandler);
        makeReservationButton.setDisable(eventHandler == null);
    }
}
