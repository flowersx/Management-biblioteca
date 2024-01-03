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
        public static final String CevaNuAMersBine = "Ceva nu a mers bine!";
        public static final String NoStoc = "Ne pare rau stocul nostru este 0 sau prea mic!";
        
        public static final String TitluInformatie = "Mesaj de informare";
        public static final String InregistrareCont = "Contul a fost inregistrat cu succes!";
        public static final String LoginCont = "Logarea a fost cu succes!";
        public static final String ParolaActualizata = "Parola a fost actualizata cu succes!";
        public static final String CarteaAdaugata = "Cartea a fost inregistrata cu succes!";
        public static final String CarteActualizata = "Cartea a fost actualizata cu succes!";
        public static final String CarteaStearsa = "Cartea a fost stearsa cu succes!";
        public static final String AdaugatCuSucces = "A fost adaugat cu succes!";
        
        public static final String SignOut = "Sunteti sigur ca doriti sa parasiti sesiunea?";
        public static final String StergereCarte = "Suntetit sigur ca doriti sa stergeti aceasta carte?";
        public static final String ActualizareCarte = "Sunteti sigur ca doriti sa actualizati informatiile cartii?";
        public static final String Confirmare = "Mesaj de confirmare!";
        public static final String ActualizareAnulata = "Actiunea de actualizare a fost anulata!";
        public static final String StergereAnulata = "Actiunea de stergere a fost anualata!";
    }
    
    public class Utils{
        public static final String EMPTY_STRING = "";
        public static final String APP_TITLE = "Manager Biblioteca";
    }
}
