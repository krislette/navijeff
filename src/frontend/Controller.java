package frontend;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class Controller implements Initializable {

    @FXML
    private WebView webView;
    private WebEngine webEngine;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webEngine = webView.getEngine();

        System.setProperty("prism.forceGPU", "true");
        System.setProperty("sun.java2d.opengl", "true");

        String localUrl = Paths.get("src/backend/map.html").toUri().toString();
        webEngine.load(localUrl);

        // Log the current URL and any errors
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                System.out.println("Loaded: " + webEngine.getLocation());
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javaApp", this);
            } else if (newState == Worker.State.FAILED) {
                System.out.println("Failed to load: " + webEngine.getLocation());
                webEngine.getLoadWorker().getException().printStackTrace();
            }
        });
    }
}