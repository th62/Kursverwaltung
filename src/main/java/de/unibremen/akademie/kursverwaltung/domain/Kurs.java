package de.unibremen.akademie.kursverwaltung.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Kurs implements Serializable {

    private String name;
    private int anzahlTage;
    private int zyklus;
    private Date startDatum;
    private Date endeDatum;
    private int aktuelleTnZahl;
    private int minTnZahl;
    private int maxTnZahl;
    private int freiePlaetze;
    private double gebuehrBrutto;
    private double gebuehrNetto;
    private double mwstEuro;
    private double mwstProzent;
    private String kursBeschreibung;
    private List<Person> interessentenListe = new ArrayList<>();
    private List<Person> teilnehmerListe=new ArrayList<>();


    public Kurs() {
    }






    public String getName() {
        return name;
    }

    public boolean setName(String name) {

        if(name!=null && name.length()>0) {
            this.name = name;
            return true;
        }
        return false;
    }

    public int getAnzahlTage() {
        return anzahlTage;
    }

    public boolean  setAnzahlTage(int anzahlTage) {
        if(anzahlTage >0){
            this.anzahlTage = anzahlTage;
            return true;
        }
        return false;

    }

    public int getZyklus() {
        return zyklus;
    }

    public boolean setZyklus(int zyklus) {
        if(zyklus>0 && zyklus<8) {
            this.zyklus = zyklus;
            return true;
        }
        return false;

    }

    public Date getStartDatum() {
        return startDatum;
    }

    public boolean setStartDatum(Date startDatum) {
        Date date = new Date();
        if(startDatum.before(date)){
            return false;
        }
        this.startDatum = startDatum;
        return true;
    }

    public Date getEndeDatum() {
        return endeDatum;
    }

    public void setEndeDatum(Date startDatum,int zyklus,int anzahlTage) {
        long dat = startDatum.getTime() + ((Math.round((float) anzahlTage / zyklus)) * 7 * 86400000L);
        this.endeDatum = new Date(dat);

    }

    public int getAktuelleTnZahl() {
        return aktuelleTnZahl;
    }

    public void setAktuelleTnZahl() {
        this.aktuelleTnZahl = this.teilnehmerListe.size();
    }

    public int getMinTnZahl() {
        return minTnZahl;
    }

    public boolean setMinTnZahl(int minTnZahl) {
        if (minTnZahl > 0 && minTnZahl < this.maxTnZahl) {
            this.minTnZahl = minTnZahl;
            return true;
        }
        return false;
    }

    public int getMaxTnZahl() {
        return maxTnZahl;
    }

    public boolean setMaxTnZahl(int maxTnZahl) {
        if (maxTnZahl > 0 && maxTnZahl > this.minTnZahl) {
            this.maxTnZahl = maxTnZahl;
            return true;
        }
        return false;

    }

    public int getFreiePlaetze() {
        return freiePlaetze;
    }

    public boolean setFreiePlaetze() {
        if (this.maxTnZahl-this.aktuelleTnZahl>0) {
            this.freiePlaetze = this.maxTnZahl - this.aktuelleTnZahl;
            return true;
        }
        return false;
    }

    public double getGebuehrBrutto() {
        return gebuehrBrutto;
    }

    public boolean setGebuehrBrutto(double gebuehrBrutto) {
        if(gebuehrBrutto>0){
            this.gebuehrBrutto = gebuehrBrutto;
            return true;
        }
        return false;

    }

    public double getGebuehrNetto() {
        return gebuehrNetto;
    }

    public void setGebuehrNetto(double gebuehrBrutto, double mwstProzent) {
        this.gebuehrNetto = gebuehrBrutto * ((100 - mwstProzent) / 100);
    }

    public double getMwstEuro() {
        return mwstEuro;
    }

    public void setMwstEuro(double mwstProzent, double gebuehrBrutto) {
        this.mwstEuro = gebuehrBrutto * (mwstProzent / 100);
    }

    public double getMwstProzent() {
        return mwstProzent;
    }

    public boolean setMwstProzent(double mwstProzent) {
        if (mwstProzent >= 0) {
            this.mwstProzent = mwstProzent;
            return true;
        }
        return false;

    }

    public String getKursBeschreibung() {
        return kursBeschreibung;
    }

    public boolean setKursBeschreibung(String kursBeschreibung) {
        if (kursBeschreibung != null) {
            this.kursBeschreibung = kursBeschreibung;
            return true;
        }
        return false;
    }

    public List<Person> getInteressentenListe() {
        return interessentenListe;
    }

    public boolean setInteressentenListe(Person interessant) {
        if (interessant != null) {
            this.interessentenListe.add(interessant);
            return true;
        }
        return false;
    }

    public List<Person> getTeilnehmerListe() {
        return teilnehmerListe;
    }

    public boolean setTeilnehmerListe(Person teilnehmer) {
        if(teilnehmer!=null) {
            this.teilnehmerListe.add(teilnehmer);
            return true;
        }
        return false;
    }

}
