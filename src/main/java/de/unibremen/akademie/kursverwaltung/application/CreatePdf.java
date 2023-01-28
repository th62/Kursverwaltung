package de.unibremen.akademie.kursverwaltung.application;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.properties.AreaBreakType;
import de.unibremen.akademie.kursverwaltung.domain.Person;
import static de.unibremen.akademie.kursverwaltung.domain.AnwendungsModel.kvModel;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;

import java.io.IOException;



public class CreatePdf {

    // Zu erzeugende Dateien
    String PERSONENLISTEPDF = "src/main/resources/de/unibremen/akademie/kursverwaltung/pdf/Personenliste.pdf";

    // Layout-Elemente


    public SolidLine line = new SolidLine(1f);
    public LineSeparator separator = new LineSeparator(line);

    public void createPersonenListePdf() throws IOException {
        String headline = "Liste aller gespeicherten Personen";
        String metaSubject = "Personenliste";
        String tabsAbstand = "\t\t\t\t\t\t\t\t\t\t";
        int counterPersonen = 0;
        int anzahlPersonenJeSeite = 10;
        int seiteAktuell = 1;
        int seitenGesamt = (kvModel.getPersonen().getPersonenListe().size() + 9) / anzahlPersonenJeSeite;

        PdfDocument pdf = new PdfDocument(
                new PdfWriter(PERSONENLISTEPDF,
                        new WriterProperties()
                                .addXmpMetadata()
                                .setPdfVersion(PdfVersion.PDF_1_6)));
        PdfDocumentInfo info = pdf.getDocumentInfo();
        addMetaData(info, metaSubject); //Metadaten hinzufuegen
        Document personenListePdf = new Document(pdf, PageSize.A4);
        if (seitenGesamt > 0) {
            personenListePdf.add(new Paragraph(headline + tabsAbstand + seiteAktuell + "/" + seitenGesamt).setFontSize(18));
            personenListePdf.add(separator);
            personenListePdf.add(new Paragraph("\n\n"));
            for (Object obj : kvModel.getPersonen().getPersonenListe()) {
                Person person = (Person) obj;
                counterPersonen++;
                if (counterPersonen <= anzahlPersonenJeSeite) {
                    personenListePdf.add(new Paragraph(personToPDF(person)));
                    personenListePdf.add(separator);
                } else {
                    personenListePdf.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                    seiteAktuell++;
                    personenListePdf.add(new Paragraph(headline + tabsAbstand + seiteAktuell + "/" + seitenGesamt).setFontSize(18));
                    personenListePdf.add(separator);
                    personenListePdf.add(new Paragraph("\n\n"));
                    personenListePdf.add(new Paragraph(personToPDF(person)));
                    personenListePdf.add(separator);
                    counterPersonen = 1;
                }
            }
        } else {
            personenListePdf.add(new Paragraph(headline).setFontSize(18));
            personenListePdf.add(new Paragraph("\nKeine sind keine Personen in der Kursverwaltung angelegt!").setFontSize(24));
        }
        personenListePdf.close();
    }

    public String personToPDF (Person person) {
        return person.getAnrede() + " " +
                person.getTitel() + " " +
                person.getVorname() + " " +
                person.getNachname() + "\r" +
                person.getPlz() + " " +
                person.getOrt() + " " +
                person.getStrasse() + "\r" +
                person.getEmail() + " " +
                person.getTelefon();
    }

    private static void addMetaData(PdfDocumentInfo info, String subject) {
            info.setTitle("Kursverwaltung");
            info.setAuthor("SWE 2022/2023");
            info.setSubject(subject);
            info.setKeywords("M. Dbour | F. Ennouali | A. Förster | T. Haberland | Y. Hanna | A. Karaköse | R. Lukas | M. Pawel | O. Thiel | T. Tomy");
            info.setCreator("SWE-M07 - DevelopmentGroup");
    }
}