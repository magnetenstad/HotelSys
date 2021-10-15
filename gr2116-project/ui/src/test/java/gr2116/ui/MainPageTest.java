package gr2116.ui;


import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.api.FxAssert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import gr2116.core.Person;
import gr2116.ui.controller.AppController;


public class MainPageTest extends ApplicationTest{
    AppController appController;
    /**
     * Start the app, load FXML and show scene.
     * @throws IOException
     */
  
    @Start
    public void start(Stage stage) throws IOException {
        //Parent parent = FXMLLoader.load(
        //getClass().getClassLoader().getResource("App.fxml")); //MainPage.fxml
        //AppController appController = parent.getController();
        //System.out.println(appController);
        
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("App.fxml"));
        //Pane p = fxmlLoader.load(getClass().getResource("App.fxml").openStream());
        Parent parent = fxmlLoader.load();
        appController = (AppController) fxmlLoader.getController();

        
        //AppController appController = loader.getController();
        Person person = new Person("Richard Wilkens");
        person.setEmail("RichardWilkins@gmail.com");
        person.addBalance(100.0);
        appController.moveToMainPage(person);
        

        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    void checkUserPane() {
        FxAssert.verifyThat("#nameLabel", LabeledMatchers.hasText("Richard Wilkens"));
        FxAssert.verifyThat("#emailLabel", LabeledMatchers.hasText("RichardWilkins@gmail.com"));
        FxAssert.verifyThat("#balanceLabel", LabeledMatchers.hasText("100.0"));
    }

    @Test
    void checkBookHotel() {
        clickOn((lookup("#startDatePicker").queryAs(DatePicker.class)).getEditor()).write("12/01/2021\n");
        clickOn((lookup("#endDatePicker").queryAs(DatePicker.class)).getEditor()).write("12/02/2021\n");
        clickOn("#amenityTelevision");

        FxAssert.verifyThat("#roomItemContainer", NodeMatchers.hasChild("#hotelRoom101listItem"));
        clickOn("#hotelRoom101Button");
        FxAssert.verifyThat("#reservationListView", NodeMatchers.hasChild("#hotelRoom101reservation2021-12-01to2021-12-02"));
    }

}
    
