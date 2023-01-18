package de.unibremen.akademie.kursverwaltung.domain;

import javafx.beans.property.SimpleBooleanProperty;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class PersonKurs implements Externalizable {

    private Person person;
    private Kurs kurs;
    private SimpleBooleanProperty teilnehmer;

    public PersonKurs(Person person, Kurs kurs, boolean teilnehmer) {
        this.person = person;
        this.kurs = kurs;
        this.teilnehmer = new SimpleBooleanProperty(teilnehmer);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Kurs getKurs() {
        return kurs;
    }

    public void setKurs(Kurs kurs) {
        this.kurs = kurs;
    }

    public boolean isTeilnehmer() {
        return teilnehmer.get();
    }

    public void setTeilnehmer(boolean teilnehmer) {
        this.teilnehmer.set(teilnehmer);
    }

    public SimpleBooleanProperty teilnehmerProperty() {
        return teilnehmer;
    }

    @Override
    public void writeExternal(ObjectOutput stream) throws IOException {
        stream.writeObject(getPerson());
        stream.writeObject(getKurs());
        stream.writeUTF(String.valueOf(isTeilnehmer()));

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setPerson((Person) in.readObject());
        setKurs((Kurs) in.readObject());
        setTeilnehmer(Boolean.parseBoolean(in.readUTF()));

    }

}