package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Controller {
    @FXML
    public TextField searchField;
    @FXML
    public TextArea outputField;
    @FXML
    private int i = 0;
    private String[] historyItems = new String[10];
    private String searchTerm;

    public void handleButtonClick(ActionEvent actionEvent) throws IOException {
        outputField.clear();
        searchTerm = changeToUmlaut(searchField.getText());
        //Search in Google if space inserted as prefix
        if (searchTerm.charAt(0) == ' ') {
            searchTerm = searchTerm.replaceFirst(" ", "");
            Document docGoogle = Jsoup.connect("https://www.google.de/search?q=" + searchTerm).get();
            Elements docGoogleTitles = docGoogle.select("div[class=\"wCIBKb\"]");
            Elements docGoogleList = docGoogle.select("span[class=\"st\"]");
            for (Element GoogleEl : docGoogleTitles) {
                outputField.appendText(GoogleEl.text().replaceAll(searchTerm, "▐" + searchTerm + "▌") + "\n\n");
            }
            for (Element GoogleEl : docGoogleList) {
                outputField.appendText(GoogleEl.text().replaceFirst(searchTerm, "▐" + searchTerm + "▌") + "\n\n");
            }
        }
        //IF NO PREFIX SEARCH IN DICT.CC DE-EN
        else {
            Document doc = Jsoup.connect("https://www.dict.cc/?s=" + searchTerm).get();
            Elements translated = doc.select("td.td7nl");
            for (Element transl : translated)
            {
                if (i % 2 == 0) {
                    outputField.appendText(transl.text() + " ――― ");
                }
                else {
                    outputField.appendText(transl.text().replaceAll("[0-9]","") + "\n\n");
                }
                i++;
            }
        }
        //Save search term in historyItems
        for (i = 0; i < historyItems.length; i++) {
            if (historyItems[i] == null) {
                historyItems[i] = searchField.getText();
                i = historyItems.length;
            }
            if (historyItems[historyItems.length - 1] != null) {
                for (int k = 0; k < (historyItems.length); k++) {
                    if (k == historyItems.length - 1) {
                        historyItems[historyItems.length - 1] = searchField.getText();
                    }
                    else {
                        historyItems[k] = historyItems [k + 1];
                    }
                }
                i = historyItems.length;
            }
        }
        i = 0;
        searchField.selectAll();
        outputField.selectPositionCaret(0);
    }

    public void spellCorrection() throws IOException {
        Document GoogleSpell = Jsoup.connect("https://www.google.de/search?q=" + searchField.getText()).get();
        searchField.setText(GoogleSpell.selectFirst("i").text());
        searchField.selectAll();
    }

    //HOTKEYS ALT + [1-9] //copy from outputfield
    public void fetchItemFromOutputField(String KeyCode) {
        String[] KeyCodeL = KeyCode.split("\\+");
        int number = Integer.parseInt(KeyCodeL[1]);
        String[] Lines = outputField.getText().split("\n");
        searchField.requestFocus();
        searchField.setText(Lines[number-1].replaceAll("[0-9]. | $", ""));
        searchField.selectAll();
    }

    public void clear() {
        outputField.clear();
        searchField.selectAll();
    }

    public void showHistoryItems() {
        outputField.clear();
        for (int i = historyItems.length - 1; i >= 0; i--) {
            if (historyItems[i] != null) {
                outputField.appendText(historyItems[i].replaceAll("\\|", "") + "\n");
            }
        }
    }

    public void addDefinition() {
        if (searchTerm.charAt(0) == '-') {
            searchField.setText(searchTerm.replaceFirst("-", ""));
        }
        searchField.appendText(" definition");
        searchField.insertText(0, " ");
    }

    private String changeToUmlaut(String searchTerm) {
        searchTerm = searchTerm.replaceAll("ae", "ä"); searchTerm = searchTerm.replaceAll("Ae", "Ä");
        searchTerm = searchTerm.replaceAll("ue", "ü"); searchTerm = searchTerm.replaceAll("Ue", "Ü");
        searchTerm = searchTerm.replaceAll("oe", "ö"); searchTerm = searchTerm.replaceAll("Oe", "Ö");
        searchTerm = searchTerm.replaceAll("sS", "ß");
        searchField.setText(searchTerm);
        return searchTerm;
    }
}