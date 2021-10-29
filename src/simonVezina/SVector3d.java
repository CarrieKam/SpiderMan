package simonVezina;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <p>
 * La classe <b>SVector3d</b> reprÈsente une vecteur <i>x</i>, <i>y</i> et <i>z</i> ‡ trois dimensions. 
 * </p>
 * 
 * <p>
 * Les opÈrations mathÈmatiques supportÈes sont les suivantes : 
 * <ul>- L'addition.</ul>
 * <ul>- La soustraction.</ul>
 * <ul>- La multiplication par un scalaire.</ul>
 * <ul>- Le produit scalaire (<i>dot product</i>).</ul>
 * <ul>- Le produit vectoriel (<i>cross product</i>).</ul>
 * <ul>- La normalisation (vecteur de module 1).</ul>
 * </p>
 * Cette classe est une version 2d modifi�e de la classe SVector3d ecrite par Simon Vezina.
 * @author Simon Vezina, 
 * @author Emon Dhar
 * @since 2014-12-16
 * @version 2017-12-20 (version labo ñ Le ray tracer v2.1)
 */
public class SVector3d implements SVector, Comparable<SVector3d> {

  //---------------
  // CONSTANTES  //
  //---------------

  /**
   * La constante 'DEFAULT_COMPONENT' correspond ‡ la composante par dÈfaut des variables x,y et z du vecteur Ètant Ègale ‡ {@value}.
   */
	private static final double  DEFAULT_COMPONENT = 0.0;

	/**
	 * La constant <b>ZERO</b> correspond au vecteur nul correspondant ‡ la coordonnÈe (0.0, 0.0, 0.0).
	 */
	public static final SVector3d ZERO = new SVector3d(0.0, 0.0, 0.0);
	
	/**
	 * La constant <b>UNITARY_X</b> correspond au vecteur unitaire parallËle ‡ l'axe x correspondant ‡ (1.0, 0.0, 0.0).
	 * On utilise Ègalement la notation <b><i>i</i></b> pour le reprÈsenter.
	 */
	public static final SVector3d UNITARY_X = new SVector3d(1.0, 0.0, 0.0);
	
	/**
   * La constant <b>UNITARY_Y</b> correspond au vecteur unitaire parallËle ‡ l'axe y correspondant ‡ (0.0, 1.0, 0.0).
   * On utilise Ègalement la notation <b><i>j</i></b> pour le reprÈsenter.
   */
	public static final SVector3d UNITARY_Y = new SVector3d(0.0, 1.0, 0.0);
	
	/**
   * La constant <b>UNITARY_Z</b> correspond au vecteur unitaire parallËle ‡ l'axe z correspondant ‡ (0.0, 0.0, 1.0).
   * On utilise Ègalement la notation <b><i>k</i></b> pour le reprÈsenter.
   */
	public static final SVector3d UNITARY_Z = new SVector3d(0.0, 0.0, 1.0);
	
	/**
   * La constante <b>ORIGIN</b> reprÈsente un vecteur origine Ètant situÈ ‡ la coordonnÈe (0.0, 0.0, 0.0).
   * Il est Ègal au vecteur <b>ZERO</b>.
   */
	public static final SVector3d ORIGIN = ZERO;
	
	//--------------
	// VARIABLES  //
	//--------------
	
	/**
	 * La variable <b>x</b> correspond ‡ la composante x du vecteur 3d.
	 */
	public  double x;
	
	/**
   * La variable <b>y</b> correspond ‡ la composante y du vecteur 3d.
   */
	public  double y;	
	
	/**
   * La variable <b>z</b> correspond ‡ la composante z du vecteur 3d.
   */
	public  double z;	
	
	//-----------------
	// CONSTRUCTEURS //
	//-----------------
	/**
	 * Constructeur reprÈsentant un vecteur 3d ‡ l'origine d'un systËme d'axe xyz.
	 */
  public SVector3d()
	{
		this(DEFAULT_COMPONENT, DEFAULT_COMPONENT, DEFAULT_COMPONENT);
	}
	
	/**
	 * Constructeur avec composante <i>x</i>,<i>y</i> et <i>z</i> du vecteur 3d.
	 * 
	 * @param x La composante <i>x</i> du vecteur.
	 * @param y La composante <i>y</i> du vecteur.
	 * @param z La composante <i>z</i> du vecteur.
	 */
	public SVector3d(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Constructeur avec composante <i>x</i>,<i>y</i> et <i>z</i> du vecteur 3d.
	 * 
	 * @param x La composante <i>x</i> du vecteur.
	 * @param y La composante <i>y</i> du vecteur.
	 * @param z La composante <i>z</i> du vecteur.
	 */
	
	public SVector3d(double x, double y)
	{
		this.x = x;
		this.y = y;
		this.z = 0;
	}
	
	/**
	 * Constructeur d'un vecteur 3d en utilisant un string dÈcrivant les paramËtres x, y et z du vecteur. 
	 * Une lecture autorisÈe peut prendre la forme suivante : "double x" "double y" "double z" (incluant la notation avec , ( ) [ ] comme dÈlimieur dans l'expression du string comme par exemple (2.3, 4.3, 5.7) ).
	 * 
	 * @param string - Le string de l'expression du vecteur en paramËtres x, y, et z.
	 * @throws SReadingException Si le format de la lecture n'est pas adÈquat.
	 */
	public SVector3d(String string)throws SReadingException
	{
		double[] tab = read(string);
		
		x = tab[0];
		y = tab[1];
		z = tab[2];
	}
	
	//------------
	// M…THODES //
	//------------
	
	/**
	 * MÈthode qui donne accËs ‡ la coordonnÈe x du vecteur.
	 * 
	 * @return La coordonnÈe x.
	 */
	public double getX()
	{ 
	  return x;
	}
	
	/**
	 * MÈthode qui donne accËs ‡ la coordonnÈe y du vecteur.
	 * 
	 * @return La coordonnÈe y.
	 */
	public double getY()
	{ 
	  return y;
	}
	
	/**
	 * MÈthode qui donne accËs ‡ la coordonnÈe z du vecteur.
	 * 
	 * @return La coordonnÈe z.
	 */
	public double getZ()
	{ 
	  return z;
	}
	
	
	/**
	 * MÈthode qui donne accËs ‡ la coordonnÈe z du vecteur.
	 * 
	 * @return La coordonnÈe z.
	 */
	public String getXY()
	{ 
	  return x+" , "+y;
	}
	
	/**
	 * Methode qui permet de modifier la composante x du vecteur.
	 * @param x La nouvelle composante x
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Methode qui permet de modifier la composante y du vecteur.
	 * @param y La nouvelle composante y
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Methode qui permet de modifier la composante y du vecteur.
	 * @param z La nouvelle composante z
	 */
	public void setZ(double z) {
		this.z = z;
	}
	
	/**
	 * Methode qui permet de modifier les composantes du vecteur.
	 * @param x La nouvelle composante x
	 * @param y La nouvelle composante y
	 */
	public void setXY(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * Methode qui permet de modifier les composantes du vecteur.
	 * @param x La nouvelle composante x
	 * @param y La nouvelle composante y
	 */
	public void setXYZ(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = y;
	}
	
	@Override
	public SVector3d add(SVector v)
	{
	  return add((SVector3d)v);
	}
	 
	/**
	 * MÈthode qui calcule <b>l'addition</b> de deux vecteurs. 
	 * 
	 * @param v Le vecteur ‡ ajouter.
	 * @return La somme des deux vecteurs.
	 */
	public SVector3d add(SVector3d v)
	{	
		//---------------------------------------------------------------------------
	  // VERSION LABORATOIRE :
	  // throw new SNoImplementationException("La mÈthode n'est pas implÈmentÈe.");
	  //---------------------------------------------------------------------------
	  
	  return new SVector3d(x + v.x, y + v.y, z + v.z);
	}
	
	/**
	 * MÈthode qui calcul la <b>soustraction</b> de deux vecteurs. 
	 * 
	 * @param v - Le vecteur ‡ soustraire au vecteur prÈsent.
	 * @return La soustraction des deux vecteurs.
	 */
	public SVector3d substract(SVector3d v)
	{
	  //---------------------------------------------------------------------------
    // VERSION LABORATOIRE :
    // throw new SNoImplementationException("La mÈthode n'est pas implÈmentÈe.");
    //---------------------------------------------------------------------------
    
	  return new SVector3d(x - v.x, y - v.y, z - v.z);
	}
	
	/**
	 * MÈthode qui effectue la <b>multiplication d'un vecteur par une scalaire</b>.
	 * 
	 * @param m Le scalaire.
	 * @return La multiplication d'un vecteur par un scalaire.
	 */
	public SVector3d multiply(double m)
	{
	  //---------------------------------------------------------------------------
    // VERSION LABORATOIRE :
    // throw new SNoImplementationException("La mÈthode n'est pas implÈmentÈe.");
    //---------------------------------------------------------------------------
    
	  return new SVector3d(m*x, m*y, m*z);
	}
	/**
	 * Methode de classe qui effectue la multiplication d'un vecteur quelconque par une scalaire.
	 * @param v Le vecteur
	 * @param m Le muliplicateur
	 * @return Le resultat de la multiplication par un scalaire m sur le vecteur.
	 */
	
	public static SVector3d multiply(SVector3d v, double m)
	{
	  //---------------------------------------------------------------------------
    // VERSION LABORATOIRE :
    // throw new SNoImplementationException("La mÈthode n'est pas implÈmentÈe.");
    //---------------------------------------------------------------------------
    
	  return v.multiply(m);
	}
	
	/**
	 * MÈthode pour obtenir le <b>module</b> d'un vecteur.
	 * 
	 * @return Le module du vecteur.
	 */
	public double modulus()
	{
	  //---------------------------------------------------------------------------
    // VERSION LABORATOIRE :
    // throw new SNoImplementationException("La mÈthode n'est pas implÈmentÈe.");
    //---------------------------------------------------------------------------
    
	  return Math.sqrt((x*x) + (y*y) + (z*z));
	}
	
	/**
	 * MÈthode pour <b>normaliser</b> un vecteur ‡ trois dimensions. 
	 * Un vecteur normalisÈ possËde la mÍme orientation, mais possËde une <b> longeur unitaire </b>.
	 * Si le <b>module du vecteur est nul</b>, le vecteur normalisÈ sera le <b> vecteur nul </b> (0.0, 0.0, 0.0).
	 * 
	 * @return Le vecteur normalisÈ.
	 * @throws SImpossibleNormalizationException Si le vecteur ne peut pas Ítre normalisÈ Ètant trop petit (modulus() <  SMath.EPSILON) ou de longueur nulle.
	 */
	public SVector3d normalize() throws SImpossibleNormalizationException
	{
	  //---------------------------------------------------------------------------
    // VERSION LABORATOIRE :
    // throw new SNoImplementationException("La mÈthode n'est pas implÈmentÈe.");
    //---------------------------------------------------------------------------
   
	  // Obtenir le module du vecteur
	  double mod = modulus();			
		
		// VÈrification du module. S'il est trop petit, nous ne pouvons pas numÈriquement normaliser ce vecteur
		if(mod < SMath.EPSILON)
		  throw new SImpossibleNormalizationException("Erreur SVector3d 002 : Le vecteur " + this.toString() + " Ètant nul ou prËsque nul ne peut pas Ítre numÈriquement normalisÈ.");
		else
			return new SVector3d(x/mod, y/mod, z/mod);
	}
	
	@Override
	public double dot(SVector v)
	{
	  return dot((SVector3d)v);
	}
	
	/**
	 * MÈthode pour effectuer le <b>produit scalaire</b> entre deux vecteurs.
	 * 
	 * @param v Le vecteur ‡ mettre en produit scalaire.
	 * @return Le produit scalaire entre les deux vecteurs.
	 */
	public double dot(SVector3d v)
	{
	  //---------------------------------------------------------------------------
    // VERSION LABORATOIRE :
    // throw new SNoImplementationException("La mÈthode n'est pas implÈmentÈe.");
    //---------------------------------------------------------------------------
    
	  return (x*v.x + y*v.y + z*v.z);
	}
	
	
	
	
	/**
	 * MÈthode pour effectuer le <b>produit vectoriel</b> avec un autre vecteur v.
	 * <p>
	 * Cette version du produit vectoriel est implÈmentÈ en respectant la <b> rËgle de la main droite </b>.
	 * Il est important de rappeler que le produit vectoriel est <b> anticommutatif </b> (AxB = -BxA) et que l'ordre des vecteurs doit Ítre adÈquat pour obtenir le rÈsultat dÈsirÈ.
	 * Si l'un des deux vecteurs est <b> nul </b> ou les deux vecteurs sont <b> parallËles </b>, le rÈsultat sera un <b> vecteur nul </b>.
	 * </p>
	 * 
	 * @param v Le second vecteur dans le produit vectoriel.
	 * @return Le rÈsultat du produit vectoriel.
	 */
	public SVector3d cross(SVector3d v)
	{
	  //---------------------------------------------------------------------------
    // VERSION LABORATOIRE :
    // throw new SNoImplementationException("La mÈthode n'est pas implÈmentÈe.");
    //---------------------------------------------------------------------------
    
	  return new SVector3d(	   y * v.z - z * v.y ,
				                 -1*(x * v.z - z * v.x),
					                   x * v.y - y * v.x );
	}

	
		
	/**
	 * MÈthode pour obtenir un vecteur avec les coordonnÈe (x,y,z) les plus petites (en considÈrant le signe) parmi un ensemble de vecteurs. 
	 * 
	 * @param value_list La liste des vecteurs ‡ analyser.
	 * @return Un vecteur ayant comme coordonnÈe les plus petites valeurs (x,y,z) trouvÈes.
	 */
	public static SVector3d findMinValue(List<SVector3d> value_list)
  {
	  return findMinValue(value_list.toArray(new SVector3d[value_list.size()]));
  }
	
	/**
	 * MÈthode pour obtenir un vecteur avec les coordonnÈe (x,y,z) les plus petites (en considÈrant le signe) parmi un ensemble de vecteurs. 
	 * 
	 * @param tab - Le tableau des vecteurs ‡ analyser.
	 * @return Un vecteur ayant comme coordonnÈe les plus petites valeurs (x,y,z) trouvÈes.
	 */
	public static SVector3d findMinValue(SVector3d[] tab)
	{
	  double x_min = tab[0].getX();
	  
	  for(int i = 1; i < tab.length; i++)
	    if(x_min > tab[i].getX())
	      x_min = tab[i].getX();
	  
	  double y_min = tab[0].getY();
    
    for(int i = 1; i < tab.length; i++)
      if(y_min > tab[i].getY())
        y_min = tab[i].getY();
    
    double z_min = tab[0].getZ();
    
    for(int i = 1; i < tab.length; i++)
      if(z_min > tab[i].getZ())
        z_min = tab[i].getZ();
    
    return new SVector3d(x_min, y_min, z_min);
	}
	
	/**
   * MÈthode pour obtenir un vecteur avec les coordonnÈe (x,y,z) les plus grandes (en considÈrant le signe) parmi un ensemble de vecteurs. 
   * 
   * @param value_list La liste des vecteurs ‡ analyser.
   * @return Un vecteur ayant comme coordonnÈe les plus grandes valeurs (x,y,z) trouvÈes.
   */
  public static SVector3d findMaxValue(List<SVector3d> value_list)
  {
    return findMaxValue(value_list.toArray(new SVector3d[value_list.size()]));
  }
  
	/**
   * MÈthode pour obtenir un vecteur avec les coordonnÈe (x,y,z) les plus grandes (en considÈrant le signe) parmi un ensemble de vecteurs. 
   * 
   * @param tab - Le tableau des vecteurs ‡ analyser.
   * @return Un vecteur ayant comme coordonnÈe les plus grandes valeurs (x,y,z) trouvÈes.
   */
  public static SVector3d findMaxValue(SVector3d[] tab)
  {
    double x_max = tab[0].getX();
    
    for(int i = 1; i < tab.length; i++)
      if(x_max < tab[i].getX())
        x_max = tab[i].getX();
    
    double y_max = tab[0].getY();
    
    for(int i = 1; i < tab.length; i++)
      if(y_max < tab[i].getY())
        y_max = tab[i].getY();
    
    double z_max = tab[0].getZ();
    
    for(int i = 1; i < tab.length; i++)
      if(z_max < tab[i].getZ())
        z_max = tab[i].getZ();
    
    return new SVector3d(x_max, y_max, z_max);
  }
  
	/**
	 * MÈthode pour Ècrire les paramËtres xyz du vecteur dans un fichier en format txt. Le format de l'Ècriture est "x"  "y"  "z" comme l'exemple suivant : 0.6  0.2  0.5
	 * 
	 * @param bw Le buffer d'Ècriture.
	 * @throws IOException S'il y a une erreur avec le buffer d'Ècriture.
	 * @see BufferedWriter
	 */
	public void write(BufferedWriter bw)throws IOException
	{
		bw.write(toString());
	}
	
	

	//----------------------------------------------------------------------------------------------
	//MÈthodes pour calcul spÈcialisÈ (afin de rÈduire l'allocation de mÈmoire lors des calculs) 
	//----------------------------------------------------------------------------------------------
 
  /**
   * MÈthode pour obtenir la distance entre deux points.
   * 
   * @param A Le premier point.
   * @param B Le deuxiËme point.
   * @return La distance entre le point A et B.
   */
  public static double distance(SVector3d A, SVector3d B)
  {
    throw new SNoImplementationException("La mÈthode n'est pas implÈmentÈe.");
  }
  
  /**
   * MÈthode permettant d'obtenir l'angle entre deux vecteurs A et B. L'angle sera obtenu en radian.
   * 
   * @param A Le premier vecteur.
   * @param B Le second vecteur. 
   * @return L'angle entre les deux vecteurs.
   */
  public static double angleBetween(SVector3d A, SVector3d B)
  {
    throw new SNoImplementationException("La mÈthode n'est pas implÈmentÈe.");
  }
  
  /**
   * MÈthode pour effectuer la <b>soustraction entre deux produits scalaires</b>. Cette opÈration vectorielle est Èquivalente ‡ l'expression
   * <ul>(A - B) . C = A . C - B . C </ul>
   * @param A - Le vecteur A de l'expression.
   * @param B - Le vecteur B de l'expression.
   * @param C - Le vecteur C de l'expression.
   * @return Le scalaire reprÈsentant la solution de l'Èquation.
   */
  public static double AdotCsubstractBdotC(SVector3d A, SVector3d B, SVector3d C)
  {
    throw new SNoImplementationException("La mÈthode n'est pas implÈmentÈe.");
  }
 
 /**
  * MÈthode qui effectue le <b>produit mixte</b> de trois vecteurs A, B et C. Cette opÈration vectorielle est Èquivalente ‡ l'expression
  * <ul>(A x B) . C = (Ax*By*Cz + Bx*Cy*Az + Cx*Ay*Bz) - (Az*By*Cx + Bx*Cz*Ay + Cy*Ax*Bz)</ul>
  * Il est ‡ remarquer que si deux des trois vecteurs sont Ègaux ou colinÈaires, le produit mixte est nul.
  * @param A - Le 1ier vecteur dans le produit mixte.
  * @param B - Le 2iËme vecteur dans le produit mixte.
  * @param C - Le 3iËme vecteur dans le produit mixte.
  * @return Le scalaire rÈsultant du produit mixte.
  */
  public static double AcrossBdotC(SVector3d A, SVector3d B, SVector3d C)
  {
    throw new SNoImplementationException("MÈthode non implÈmentÈe.");
  }
 
 /**
  * MÈthode qui effectue le <b>triple produit vectoriel</b> de trois vecteurs A, B et C. Cette opÈration vectorielle est Èquivalente ‡ l'expression
  * <ul>(A x B) x C = (A . C) B - (B . C) A</ul>  
  * <p>Il est important de prÈciser que les deux expressions
  * <ul>(A x B) x C != A x (B x C)</ul>
  * ne sont pas Ègaux. L'ordre de prioritÈ des opÈrations doit Ítre bien dÈfini.</p>
  *  
  * @param A - Le 1ier vecteur dans le double produit vectoriel.
  * @param B - Le 2iËme vecteur dans le double produit vectoriel.
  * @param C - Le 3iËme vecteur dans le double produit vectoriel.
  * @return Le vecteur rÈsultant du produit mixte.
  */
 public static SVector3d AcrossBcrossC(SVector3d A, SVector3d B, SVector3d C)
 {
   throw new SNoImplementationException("La mÈthode n'est pas implÈmentÈe.");
 }
 
 /**
  * <p>MÈthode qui effectue le <b>triple produit vectoriel</b> de trois vecteurs A, B et C avec l'ordre de prioritÈ</p>
  * <ul>D = A x (B x C)</ul>
  * <p>o˘ D est le rÈsultat du triple produit vectoriel. Cette opÈration vectorielle est Èquivalente ‡ l'expression</p>
  * <ul>A x (B x C) = (A . C) B - (A . B) C</ul>  
  * <p>et il est important de prÈciser que les deux expressions</p>
  * <ul>A x (B x C) != (A x B) x C</ul>
  * <p>ne sont pas Ègaux puisque le <b>produit vectoriel n'est pas commutatif</b>.</p>
  *  
  * @param A - Le 1ier vecteur dans le double produit vectoriel.
  * @param B - Le 2iËme vecteur dans le double produit vectoriel.
  * @param C - Le 3iËme vecteur dans le double produit vectoriel.
  * @return Le vecteur rÈsultant du triple produit vectoriel.
  */
 public static SVector3d Across_BcrossC(SVector3d A, SVector3d B, SVector3d C)
 {
   throw new SNoImplementationException("La mÈthode n'est pas implÈmentÈe.");
 }  
 
 /**
  * <p>MÈthode qui effectue le <b>triple produit vectoriel</b> de trois vecteurs A, B et C avec l'ordre de prioritÈ</p>
  * <ul>D = (A x B) x C</ul>
  * <p>o˘ D est le rÈsultat du triple produit vectoriel. Cette opÈration vectorielle est Èquivalente ‡ l'expression</p>
  * <ul>(A x B) x C = ?????</ul>  
  * <p>et il est important de prÈciser que les deux expressions</p>
  * <ul>(A x B) x C != A x (B x C)</ul>
  * <p>ne sont pas Ègaux puisque le <b>produit vectoriel n'est pas commutatif</b>.</p>
  *  
  * @param A - Le 1ier vecteur dans le double produit vectoriel.
  * @param B - Le 2iËme vecteur dans le double produit vectoriel.
  * @param C - Le 3iËme vecteur dans le double produit vectoriel.
  * @return Le vecteur rÈsultant du triple produit vectoriel.
  */
 public static SVector3d AcrossB_crossC(SVector3d A, SVector3d B, SVector3d C)
 {
   throw new SNoImplementationException("MÈthode non implÈmentÈe.");
 }
 
 /**
  * MÈthode qui effectue une opÈration spÈcialisÈe de multiplication par un scalaire et d'addition vectorielle Èquivalente ‡
  * <ul>V = a*B + C.</ul>
  * @param a - Le scalaire ‡ multiplier avec B.
  * @param B - Le 1ier vecteur dans l'expression vectorielle.
  * @param C - Le 2iËme vecteur dans l'expression vectorielle ‡ ajouter.
  * @return Le vecteur rÈsultant de cette opÈration spÈcialisÈe.
  */
 public static SVector3d AmultiplyBaddC(double a, SVector3d B, SVector3d C)
 {
	 throw new SNoImplementationException("La mÈthode n'est pas implÈmentÈe.");
 }

 //------------------------
 // M…THODES UTILITAIRES //
 //------------------------
 /**
  * MÈthode utilisant un string comme paramËtre pour dÈfinir les composantes x, y et z du vecteur. 
  * Une lecture autorisÈe peut prendre la forme suivante : "double x" "double y" "double z" 
  * (en incluant la notation avec , ( ) [ ] < > comme dÈlimieur dans l'expression du string comme par exemple (2.3, 4.3, 5.7) ).
  *  
  * @param string - Le string de l'expression du vecteur en paramËtres x, y, et z.
  * @return un tableau de trois ÈlÈments tel que x = [0], y = [1] et z = [2]. 
  * @throws SReadingException Si le format de lecture n'est pas adÈquat.
  */
 private double[] read(String string)throws SReadingException
 {
   StringTokenizer tokens = new StringTokenizer(string, SStringUtil.REMOVE_CARACTER_TOKENIZER);  
   
   if(tokens.countTokens() != 3)
     throw new SReadingException("Erreur SVector3d 003 : L'expression '" + string + "' ne contient pas exactement 3 paramËtres pour les composantes xyz du vecteur 3d.");
   else
   {
     String s = "";          //String ‡ convertir en double pour les composantes x, y et z.
     String comp = "";       //Nom de la composante en lecture utilisÈe pour l'envoie d'une Exception s'il y a lieu.
     
     try
     {
       double[] tab = new double[3]; //Tableau des 3 composantes
       
       comp = "x";
       s = tokens.nextToken();
       tab[0] = Double.valueOf(s);
       
       comp = "y";
       s = tokens.nextToken();
       tab[1] = Double.valueOf(s);
       
       comp = "z";
       s = tokens.nextToken();
       tab[2] = Double.valueOf(s);
       
       return tab;
       
     }catch(NumberFormatException e){ 
       throw new SReadingException("Erreur SVector3d 004 : L'expression '" + s +"' n'est pas valide pour dÈfinir la composante '" + comp + "' du vecteur en cours.");
     }
   } 
 }

 //----------------------
 // M…THODE OVERLOADED //
 //----------------------
 
 @Override
 public String toString()
 {
   return "[" + x + ", " + y + ", " + z + "]";   
 }

 @Override
 public int hashCode()
 {
   //hashcode autogÈnÈrÈ par Eclipse avec les paramËtres x, y et z
   final int prime = 31;
   int result = 1;
   long temp;
   temp = Double.doubleToLongBits(x);
   result = prime * result + (int) (temp ^ (temp >>> 32));
   temp = Double.doubleToLongBits(y);
   result = prime * result + (int) (temp ^ (temp >>> 32));
   temp = Double.doubleToLongBits(z);
   result = prime * result + (int) (temp ^ (temp >>> 32));
   return result;
 }

 @Override
 public boolean equals(Object obj)
 {
   if(this == obj)
     return true;
   
   if(obj == null)
     return false;
   
   if(!(obj instanceof SVector3d))
     return false;
   
   SVector3d other = (SVector3d) obj;
   
   //Comparaison des valeurs x,y et z en utilisant la mÈthode de comparaison de SMath
   if(!SMath.nearlyEquals(x, other.x))
     return false;
   
   if(!SMath.nearlyEquals(y, other.y))
     return false;
   
   if(!SMath.nearlyEquals(z, other.z))
     return false;
   
   return true;
 }
 
 @Override
 public int compareTo(SVector3d o) 
 {
   // Variable dÈterminant la comparaison 
   // ( comp < 0 ==> plus petit )
   // ( comp > 0 ==> plus grand )
   // ( comp = 0 ==> Ègal )
   int comp;
   
   // Comparaison sur les x
   comp= Double.compare(this.x, o.x);
   
   if(comp != 0)
     return comp;
   
   // Comparaison sur les y
   comp= Double.compare(this.y, o.y);
   
   if(comp != 0)
     return comp;
   
   // Comparaison sur les z
   return Double.compare(this.z, o.z);  
 }
 
}//fin de la classe SVector3d

