/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package constants;

/**
 *
 * @author Florin
 */
public class Constants {

    // questions
    public class Questions {
        public static final String Question_1 = "Care a fost primul tau animal de companie?";
        public static final String Question_2 = "Care este numele sotiei tale?";
        public static final String Question_3 = "Cum se numeste liceul unde ai terminat?";
        public static final String Question_4 = "Care a fost prima ta masina?";
        public static final String Question_5 = "Cum o cheama pe mama ta?";
    }
    
    public static String[] TipurCarti = {"Istorie", "Sci-fi", "Dragoste", "Actiune", "Romane", "Biografie", "Comedie", "Teatru"};
    
    public class ErrorMessages {
        public static final String TitluEroare = "Mesaj de eroare!";
        public static final String BlankFields = "Va rog completati toate casutele!";
        public static final String PasswordTooShort = "Parola trebuie sa aiba minim 8 caractere";
        public static final String WrongUsernameOrPassword = "Nume de utilizator sau parola gresite!";
        public static final String WrongInformations = "Informatii incorecte";
        public static final String PasswordDontMatch = "Parolele trebuie sa coincida";
        
        public static final String TitluInformatie = "Mesaj de informare";
        public static final String InregistrareCont = "Contul a fost inregistrat cu succes!";
        public static final String LoginCont = "Logarea a fost cu succes!";
        public static final String ParolaActualizata = "Parola a fost actualizata cu succes!";
        public static final String CarteaAdaugata = "Cartea a fost inregistrata cu succes!";
        
        public static final String SignOut = "Sunteti siguri ca doriti sa parasiti sesiunea?";
        public static final String Confirmare = "Mesaj de confirmare!";
    }
    
    public class Utils{
        public static final String EMPTY_STRING = "";
        public static final String APP_TITLE = "Manager Biblioteca";
    }
}
