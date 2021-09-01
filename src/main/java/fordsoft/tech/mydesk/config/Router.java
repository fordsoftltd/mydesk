package fordsoft.tech.mydesk.config;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class Router {
    @Autowired
    @Lazy
    FxWeaver fxWeaver;
    public <T> void navigate(Class<T> cl, ActionEvent ae) {
        Parent root = fxWeaver.loadView(cl);
        Node source = (Node) ae.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public <T> Parent getController(Class<T> cl, ActionEvent ae) {
        Parent root = fxWeaver.loadView(cl);
       return root;
    }

}
