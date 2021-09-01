package fordsoft.tech.mydesk.dto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

 public class Countries {

    static ObservableList<String> obsList;

    // Returns an ObservableList populated by Countries
    public static ObservableList<String> obsList() {
        ArrayList<String> items = new ArrayList<>();

        items.add("Afghanistan");
        items.add("Albania");
        items.add("Algeria");
        items.add("Belgium");
        items.add("Bolivia");
        items.add("Brazil");
        items.add("Cuba");
        items.add("Denmark");
        items.add("Estonia");
        items.add("France");
        items.add("Germany");
        items.add("India");
        items.add("Iran");
        items.add("Isreal");
        items.add("Japan");
        items.add("Nigeria");
        items.add("Pakistan");
        items.add("Singapore");
        items.add("Syria");
        items.add("Turkey");

        return obsList = FXCollections.observableArrayList(items);

    }

}

