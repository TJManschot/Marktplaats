package nl.belastingdienst.services;

import nl.belastingdienst.app.accounts.*;
import nl.belastingdienst.database.GebruikerDao;
import nl.belastingdienst.services.printer.Printer;

import java.util.Locale;

public class GebruikerValidator {
    private final GebruikerDao gebruikerDao;
    private final Printer printer;

    private static final String OPHALEN = "ophalen";

    public GebruikerValidator(GebruikerDao gebruikerDao, Printer printer) {
        this.gebruikerDao = gebruikerDao;
        this.printer = printer;
    }

    public boolean valideer(Gebruiker gebruiker) {
        if (gebruiker == null) {
            printer.printErrorln("Gebruiker is niet geïnstantieerd!");
            return false;
        }

        boolean adresVerplicht = false;

        for (Bezorgwijze bezorgwijze : gebruiker.getBezorgwijzen()) {
            if (bezorgwijze.getNaam().toLowerCase(Locale.ROOT).equals(OPHALEN)) {
                adresVerplicht = true;
                break;
            }
        }

        return ((!adresVerplicht && gebruiker.getAdres() == null) || valideer(gebruiker.getAdres()))
                && valideer(gebruiker.getEmail())
                && valideer(gebruiker.getGebruikersnaam());
    }

    public boolean valideer(Adres adres) {
        if (adres == null
                || adres.getPostcode() == null
                || adres.getStad() == null
                || adres.getStraat() == null
                || adres.getHuisnummer() == null) {
            printer.printErrorln("Het adres is niet volledig opgegeven!");
            return false;
        }

        boolean result = true;

        if (adres.getPostcode().isBlank()) {
            printer.printErrorln("Postcode mag niet leeg zijn!");
            result = false;
        }

        if (adres.getStad().isBlank()) {
            printer.printErrorln("Stad mag niet leeg zijn!");
            result = false;
        }

        if (adres.getStraat().isBlank()) {
            printer.printErrorln("Straat mag niet leeg zijn!");
            result = false;
        }

        if (adres.getHuisnummer().isBlank()) {
            printer.printErrorln("Huisnummer mag niet leeg zijn!");
            result = false;
        }

        // Nu gaan we testen of de postcode bestaat uit 4 cijfers gevolgd door 2 letters.
        char[] postcodeArray = adres.getPostcode().toCharArray();
        int aantalCijfers = 0;
        int aantalLetters = 0;

        for (char c : postcodeArray) {
            if (c == ' ')
                continue;

            if (aantalCijfers < 4) {
                if (!Character.isDigit(c)) {
                    printer.printErrorln("Een postcode moet met 4 cijfers beginnen!");
                    result = false;
                    break;
                }

                aantalCijfers++;
                continue;
            }

            if (aantalLetters < 2) {
                if (!Character.isLetter(c)) {
                    printer.printErrorln("Een postcode mag niet meer dan 4 cijfers bevatten!");
                    result = false;
                    break;
                }

                aantalLetters++;
                continue;
            }

            printer.printErrorln("Dit adres is te lang!");
            return false;
        }

        return result;
    }

    public boolean valideer(Email email) {
        if (email == null
                || email.getEmail() == null
                || email.getEmail().isBlank()) {
            printer.printErrorln("Email-adres mag niet leeg zijn.");
            return false;
        }

        boolean atFound = false;
        boolean dotFound = false;
        boolean regularCharacterFound = false;

        char[] chars = email.getEmail().toCharArray();

        for (char c : chars) {
            if (Character.isLetterOrDigit(c)) {
                regularCharacterFound = true;
                continue;
            }

            if (c == '@') {
                if (!regularCharacterFound || atFound) {
                    printer.printErrorln("Ongeldig emailadres!");
                    return false;
                }

                atFound = true;
                dotFound = false;
                regularCharacterFound = false;
                continue;
            }

            if (c == '.') {
                if (!regularCharacterFound) {
                    printer.printErrorln("Ongeldig emailadres!");
                    return false;
                }

                dotFound = true;
                regularCharacterFound = false;
                continue;
            }

            printer.println("Ongeldig emailadres!");
            return false;
        }

        return atFound && dotFound && regularCharacterFound;
    }

    public boolean valideer(Gebruikersnaam gebruikersnaam) {
        if (gebruikersnaam == null
                || gebruikersnaam.getGebruikersnaam() == null
                || gebruikersnaam.getGebruikersnaam().isBlank()) {
            printer.printErrorln("Gebruikersnaam mag niet leeg zijn.");
            return false;
        }

        if (gebruikerDao.checkIfGebruikersnaamExists(gebruikersnaam)) {
            printer.printErrorln("Deze naam is reeds in gebruik.");
            return false;
        }

        return true;
    }
}
