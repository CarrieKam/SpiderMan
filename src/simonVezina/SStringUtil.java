/**
 * 
 */
package simonVezina;

import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.stream.Stream;

/**
 * La classe <b>SStringUtil</b> permet d'effectuer quelques op�rations pratiques sur un String.
 * 
 * @author Simon V�zina
 * @since 2015-03-21
 * @version 2016-03-21
 */
public class SStringUtil {

	/**
	 * La constante <b>END_LINE_CARACTER</b> correspond � un caract�re de changement de ligne.
	 * Il est d�termin� � l'aide de l'instruction <i>System.getProperty("line.separator")</i> ce qui permet d'avoir le bon caract�re pour la bonne plateforme d'ex�cution. 
	 */
	public static final String END_LINE_CARACTER = System.getProperty("line.separator"); 	

	/**
	 * La constante <b>REMOVE_CARACTER_TOKENIZER</b> correspond � l'ensemble des caract�res � retirer lorsque l'on d�sire s�par� en sous-mot une longue cha�ne de caract�re.
	 * Les caract�res sont les suivants : {@value}.
	 */
	public static final String REMOVE_CARACTER_TOKENIZER = " \t\n\r\f,()[]<>";

	/**
	 * M�thode pour obtenir l'extension du nom du fichier. Ceci correspond aux derniers caract�res apr�s le dernier "." dans le nom du fichier.
	 * La valeur retourn�e sera en carat�re minuscule.
	 * 
	 * @param file_name - Le nom du fichier
	 * @return L'extension du fichier en carat�res minuscules.
	 */
	public static String extensionFileLowerCase(String file_name)
	{
		String extension = "";
		int i = file_name.lastIndexOf('.');

		if (i > 0)
			extension = file_name.substring(i+1);

		return extension.toLowerCase(Locale.ENGLISH);
	}

	/**
	 * M�thode pour obtenir le nom d'une fichier sans la r�f�rence � des r�pertoires de localisation inclus dans le nom du fichier.
	 * 
	 * @param file_name - Le nom du fichier (incluant peut-�tre des r�pertoires de localisation).
	 * @return Le nom du fichier uniquement.
	 */
	public static String getFileNameWithoutDirectory(String file_name)
	{
		StringTokenizer tokens = new StringTokenizer(file_name, "/\\");  //faire token avec s�parateur '/' et '\'

		String last_token = null;

		while(tokens.hasMoreTokens())
			last_token = tokens.nextToken();

		return last_token;
	}

	/**
	 * M�thode pour compter le nombre de fois qu'un caract�re particulier se retrouve dans un string.
	 * @param string - Le string en analyse.
	 * @param caracter - Le caract�re � identifier et � compter.
	 * @return Le nombre de fois qu'un caract�re pr�cis a �t� compt� dans le string.
	 */
	public static int countCaracterInString(String string, char caracter)
	{
		int count = 0;
		for(int i = 0; i < string.length(); i++)
			if(string.charAt(i) == caracter)
				count++;

		return count;
	}

	/**
	 * M�thode pour faire la jonction entre plusieurs String contenu dans un tableau de String. 
	 * L'expression finale comprendra un caract�re d'espacement entre les String. 
	 * @param strings - Le tableau de String.
	 * @param spacer - L'expression d'espacement.
	 * @return L'expression comprenant tous les String du tableau de String.
	 */
	public static String join(String[] strings, String spacer)
	{
		String expression = "";

		for(int i = 0; i < strings.length; i++)
		{
			if(!strings[i].equals(""))
				expression = expression + strings[i];

			if(i < strings.length-1)
				expression = expression + spacer;
		}

		return expression;
	}

	/**
	 * M�thode effectuant la fusions de deux tableaux de String. 
	 * L'ordre des �l�ments sera la m�me quand dans les tableaux pr�c�dent 
	 * o� le dernier �l�ment du tableau 1 sera suivit par le 1ier �l�ment du tableau 2.
	 *
	 * @param strings1 - Le 1ier tableau de String � fusionner.
	 * @param strings2 - Le 2i�me tableau de String � fusionner.
	 * @return Un tableau de String fusionn�.
	 */
	public static String[] merge(String[] strings1, String[] strings2)
	{
		/*
	  // Version traditionnelle :
	  String[] result = new String[strings1.length + strings2.length];

    // Mettre les �l�ments du 1ier tableau dans le tableau r�sultant
	  for(int i = 0; i < strings1.length; i++)
	    result[i] = strings1[i];

    // Mettre les �l�ments du 2ier tableau dans le tableau r�sultant
	  for(int i = 0; i < strings2.length; i++)
	    result[strings1.length + i] = strings2[i];
		 */

		// Version Stream :
		return Stream.concat(Arrays.stream(strings1), Arrays.stream(strings2)).toArray(String[]::new);
	}

	/**
	 * M�thode pour g�n�rer un String en retirant l'ensemble des carat�res d'espacement
	 * situ�s au d�but du String pass� en param�tre.
	 *   
	 * @param string - Le mot dont l'on d�sirer retirer les premiers caract�res d'espacement.
	 * @return Le mot sans les premiers caract�res d'espacement.
	 */
	public static String removeAllFirstSpacerCaracter(String string)
	{
		if(string == null)
			return null;

		// Retirer les 1ier caract�res uniquement faisant parti de la liste
		while(string.length() > 0)
			switch(string.charAt(0))
			{
			case ' '  : 
			case '\t' : 
			case '\n' :
			case '\r' : 
			case '\f' : string = string.substring(1); break;

			default : return string;
			}

		return string;   //le caract�re sera alors ""
	}

}//fin de la classe SStringUtil
