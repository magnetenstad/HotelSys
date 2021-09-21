package gr2116.ui.components;

import gr2116.core.HotelRoom;
import gr2116.ui.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RoomItem extends VBox {
    private HotelRoom room;
    
    @FXML
    private Label numberLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Button button;

    public RoomItem(HotelRoom room) {
        this.room = room;
        FXMLUtils.loadFXML(this);
    }

    @FXML
    private void initialize() {
        numberLabel.setText("Room " + room.getNumber());
        typeLabel.setText(room.getRoomType().getDescription());
        button.setText("Make reservation.");
    }

    public void setRoom(HotelRoom room) {
        this.room = room;
    }

    public HotelRoom getRoom() {
        return room;
    }
}
