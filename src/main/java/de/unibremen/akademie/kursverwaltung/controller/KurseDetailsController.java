package de.unibremen.akademie.kursverwaltung.controller;

import de.unibremen.akademie.kursverwaltung.domain.Kurs;
import de.unibremen.akademie.kursverwaltung.domain.KvModel;
import de.unibremen.akademie.kursverwaltung.domain.Meldung;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class KurseDetailsController {

    @FXML
    private Tab fxmlKurseDetails;
    @FXML
    private TextField kursname;
    @FXML
    private TextField anzahlTage;
    @FXML
    private TextField zyklus;
    @FXML
    private DatePicker startDatum;
    @FXML
    private DatePicker endeDatum;
    @FXML
    private TextField minTnZahl;
    @FXML
    private TextField maxTnZahl;
    @FXML
    private TextField aktuelleTnZahl;
    @FXML
    private TextField freiePlaetze;
    @FXML
    private TextField gebuehrBrutto;
    @FXML
    private TextField gebuehrNetto;
    @FXML
    private TextField mtwsEuro;
    @FXML
    public TextField mtwsProzent;
    @FXML
    private TextArea kursBeschreibung;
    @FXML
    private ComboBox status;

    private MainController main;

    public static boolean checkIsDouble(String wert) {
        return wert.matches("\\d+\\.\\d+");
    }

    public void abbrechen(ActionEvent actionEvent) {
        kursname.clear();
        status.setValue(status.getPromptText());
        anzahlTage.clear();
        zyklus.clear();
        startDatum.setValue(null);
        minTnZahl.clear();
        maxTnZahl.clear();
        gebuehrBrutto.clear();
        mtwsProzent.clear();
        kursBeschreibung.clear();
        endeDatum.setValue(null);
        freiePlaetze.clear();
        aktuelleTnZahl.clear();
        mtwsEuro.clear();
        gebuehrNetto.clear();
        if( KvModel.aktuellerKurs !=null){
            for (Tab tabPaneKursListe : fxmlKurseDetails.getTabPane().getTabs()) {
                if (tabPaneKursListe.getText().equals("Kurse-Liste")) {
                    tabPaneKursListe.getTabPane().getSelectionModel().select(tabPaneKursListe);
                }
            }
        }
    }

    public void teilnehmerlist(ActionEvent actionEvent) {
        for (Tab tabPaneKursListe : fxmlKurseDetails.getTabPane().getTabs()) {
            if (tabPaneKursListe.getText().equals("Personen-Liste")) {
                tabPaneKursListe.getTabPane().getSelectionModel().select(tabPaneKursListe);
            }
        }
    }

    public void anzeigeZumAendern(Kurs kurs) {
        if(kurs!=null){
            kursname.setText(kurs.getName());
            status.setValue(kurs.getStatus());
            anzahlTage.setText(String.valueOf(kurs.getAnzahlTage()));
            zyklus.setText(String.valueOf(kurs.getZyklus()));
            LocalDate datetolocal = LocalDate.ofInstant(kurs.getStartDatum().toInstant(), ZoneId.of("CET"));
            startDatum.setValue(datetolocal);
            minTnZahl.setText(String.valueOf(kurs.getMinTnZahl()));
            maxTnZahl.setText(String.valueOf(kurs.getMaxTnZahl()));
            gebuehrBrutto.setText(String.valueOf(kurs.getGebuehrBrutto()));
            mtwsProzent.setText(String.valueOf(kurs.getMwstProzent()));
            kursBeschreibung.setText(kurs.getKursBeschreibung());
            LocalDate datelocal = LocalDate.ofInstant(kurs.getEndeDatum().toInstant(), ZoneId.of("CET"));
            endeDatum.setValue(datelocal);
            freiePlaetze.setText(String.valueOf(kurs.getFreiePlaetze()));
            aktuelleTnZahl.setText(String.valueOf(kurs.getAktuelleTnZahl()));
            mtwsEuro.setText(String.valueOf(kurs.getMwstEuro()));
            gebuehrNetto.setText(String.valueOf(kurs.getGebuehrNetto()));
        }
    }

    public void interessentenlist(ActionEvent actionEvent) {
    }

    public void onDatePickerAction(ActionEvent actionEvent) {
    }

    public void show() {
        fxmlKurseDetails.getTabPane().getSelectionModel().select(fxmlKurseDetails);
    }

    public void init(MainController mainController) {
        main = mainController;
    }

    // check fuer die Umwandlungen beim Auslesen und Zuweisen der GUI-Felder
    public static boolean checkIsInt(String wert) {
        return wert.matches("\\d+");
    }

    public void apply(ActionEvent actionEvent) {
        if (KvModel.aktuellerKurs != null) {
            // Bestehenden Kurs aendern
            try {
                KvModel.aktuellerKurs.setName(kursname.getText());
                KvModel.aktuellerKurs.setAnzahlTage((Integer.parseInt(anzahlTage.getText())));
                KvModel.aktuellerKurs.setZyklus((Integer.parseInt(zyklus.getText())));
                LocalDate localDate = startDatum.getValue();
                KvModel.aktuellerKurs.setStartDatum(Date.from(localDate.atStartOfDay(ZoneId.of("CET")).toInstant()));
                KvModel.aktuellerKurs.setMinTnZahl((Integer.parseInt(minTnZahl.getText())));
                KvModel.aktuellerKurs.setMaxTnZahl((Integer.parseInt(maxTnZahl.getText())));
                KvModel.aktuellerKurs.setGebuehrBrutto((Double.parseDouble(gebuehrBrutto.getText())));
                KvModel.aktuellerKurs.setMwstProzent((Double.parseDouble(mtwsProzent.getText())));
                KvModel.aktuellerKurs.setKursBeschreibung(kursBeschreibung.getText());
                KvModel.aktuellerKurs.setEndeDatum();
                KvModel.aktuellerKurs.setGebuehrNetto();
                KvModel.aktuellerKurs.setFreiePlaetze();
                KvModel.aktuellerKurs.setMwstEuro();
                KvModel.aktuellerKurs.setAktuelleTnZahl();
                KvModel.aktuellerKurs.setStatus(status.getValue().toString());
            } catch (Exception e) {
                Meldung.eingabeFehler(e.getMessage());
                return;
            }
            main.fxmlKurseListeController.tableView.refresh();
            main.fxmlPersonenDetailsController.tableViewKurse.refresh();

        }else {
            int anzahl = 0, zykls = 0, minTn = 0, maxTn = 0;
            double gebuhrB = 0, mwstPro = 0;
            LocalDate localDate;
            Date startDate = null;

            String name = kursname.getText();
            String kursBesch = kursBeschreibung.getText();

            try {
                if (!checkIsInt(anzahlTage.getText()) || !checkIsInt(zyklus.getText()) || !checkIsInt(minTnZahl.getText()) || !checkIsInt(maxTnZahl.getText())) {
                    throw new IllegalArgumentException("Bitte nur ganze Zahlen (1) eingeben!");
                } else {
                    anzahl = Integer.parseInt(anzahlTage.getText());
                    zykls = Integer.parseInt(zyklus.getText());
                    minTn = Integer.parseInt(minTnZahl.getText());
                    maxTn = Integer.parseInt(maxTnZahl.getText());
                }

                if (!checkIsDouble(gebuehrBrutto.getText()) || !checkIsDouble(mtwsProzent.getText())) {
                    throw new IllegalArgumentException("Bitte nur Zahlen mit Nachkommastelle (1.0) eingeben!");
                } else {
                    gebuhrB = Double.parseDouble(gebuehrBrutto.getText());
                    mwstPro = Double.parseDouble(mtwsProzent.getText());
                }

                if (!checkIsDate(String.valueOf(startDatum.getValue()))) {
                    throw new IllegalArgumentException("Bitte datum mit dem DatePicker wählen!");
                } else {
                    localDate = startDatum.getValue();
                    startDate = Date.from(localDate.atStartOfDay(ZoneId.of("CET")).toInstant());
                }
            } catch (Exception e) {
                Meldung.eingabeFehler(e.getMessage());
            }


            try {
                Kurs kurs = Kurs.addNewKurs(name, anzahl, zykls, startDate, minTn, maxTn, gebuhrB, mwstPro, kursBesch);
            } catch (Exception e) {
                Meldung.eingabeFehler(e.getMessage());
                return;
            }
            LocalDate datetolocal = LocalDate.ofInstant(KvModel.aktuellerKurs.getEndeDatum().toInstant(), ZoneId.of("CET"));
            endeDatum.setValue(datetolocal);
            aktuelleTnZahl.setText(String.valueOf(KvModel.aktuellerKurs.getAktuelleTnZahl()));
            freiePlaetze.setText(String.valueOf(KvModel.aktuellerKurs.getFreiePlaetze()));
            mtwsEuro.setText(String.valueOf(KvModel.aktuellerKurs.getMwstEuro()));
            gebuehrNetto.setText(String.valueOf(KvModel.aktuellerKurs.getGebuehrNetto()));
        }
        for (Tab tabPaneKursListe : fxmlKurseDetails.getTabPane().getTabs()) {
            if (tabPaneKursListe.getText().equals("Kurse-Liste")) {
                tabPaneKursListe.getTabPane().getSelectionModel().select(tabPaneKursListe);
            }
        }
        abbrechen(actionEvent);
    }

    public static boolean checkIsDate(String wert) {
        return wert.matches("^\s*((?:20)\\d{2})\\-(1[012]|0?[1-9])\\-(3[01]|[12][0-9]|0?[1-9])\s*$");
    }

}
