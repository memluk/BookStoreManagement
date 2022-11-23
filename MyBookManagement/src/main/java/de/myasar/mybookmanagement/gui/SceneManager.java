package de.myasar.mybookmanagement.gui;

import de.myasar.mybookmanagement.Main;
import de.myasar.mybookmanagement.model.Book;
import de.myasar.mybookmanagement.settings.AppTexts;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Singleton, which is responsible for changing the scenes.
 */
public class SceneManager {
    //region Constants
    //endregion

    //region Attributes
    private static SceneManager instance;
    private Stage mainStage;
    //endregion

    //region Constructors
    private SceneManager() {}
    //endregion

    //region Methods
    public static synchronized SceneManager getInstance() {
        if (instance == null) instance = new SceneManager();
        return instance;
    }

    public void setAndConfigureMainStage(Stage stage) {
        mainStage = stage;
        mainStage.setTitle(AppTexts.APP_NAME);
        switchToOverviewScene();
    }

    /**
     * Loads and switches to the Overview scene (main Scene of book-list)
     */
    public void switchToOverviewScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/overview-layout.fxml"));
        try {
            Scene overviewScene = new Scene(fxmlLoader.load());
            switchScene(overviewScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and switches to the Detail scene (details of the selected book)
     */
    public void switchToDetailScene(Book selectedBook) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/detail-layout.fxml"));
        try {
            Scene detailScene = new Scene(fxmlLoader.load());

            // Get controller object via FXMLLoader instance
            DetailController detailController = fxmlLoader.getController();

            // Set selected book in the controller
            detailController.setSelectedBookAndDetails(selectedBook);

            switchScene(detailScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and switches to the Confirmation scene if user wants to change or delete the selected book record
     */
    public void switchToConfirmationScene(Book selectedBook) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/confirmation-layout.fxml"));
        try {
            Scene confirmationScene = new Scene(fxmlLoader.load());

            // Get controller object via FXMLLoader instance
            ConfirmationController confirmationController = fxmlLoader.getController();

            // Set selected book in the controller
            confirmationController.setSelectedBookAndDetails(selectedBook);

            switchScene(confirmationScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set up the new scene, when it is called
     */
    private void switchScene(Scene scene) {
        mainStage.setScene(scene);
        mainStage.show();
    }
    //endregion
}
