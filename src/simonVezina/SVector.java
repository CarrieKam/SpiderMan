package simonVezina;

import java.util.List;

/**
 * <p>
 * L'interface <b>SVector</b> reprÈsente un vecteur mathÈmatique o˘ des opÈrations mathÈmatiques de base peuvent Ítre effectuÈes.
 * </p>
 * 
 * <p>
 * Les opÈrations mathÈmatiques supportÈes sont les suivantes :
 * <ul>- l'addition de vecteurs.</ul>
 * <ul>- la multiplication par un scalaire d'un vecteur.</ul>
 * </p>
 * 
 * @author Simon VÈzina
 * @since 2015-09-22
 * @version 2017-12-20 (version labo ñ Le ray tracer v2.1)
 */
public interface SVector extends SWriteable {

  //---------------------------
  // MÈthodes de l'interface //
  //---------------------------
  
  /**
   * MÈthode permettant d'effectuer l'addition mathÈmatique entre deux vecteurs.
   * 
   * @param v Le vecteur ‡ additionner
   * @return Le vecteur rÈsultat de l'addition des deux vecteurs.
   */
  public SVector add(SVector v);
  
  /**
   * MÈthode permettant d'effectuer la multiplication par un scalaire d'un vecteur avec un scalaire.
   * 
   * @param cst La constante scalaire ‡ multiplier avec le vecteur.
   * @return Le vecteur rÈsultant de la multiplication par un scalaire du vecteur.
   */
  public SVector multiply(double cst);
  
  /**
   * MÈthode permettant d'effectuer le <b>produit scalaire</b> entre deux vecteurs.
   * 
   * @param v Le vecteur ‡ mettre en produit scalaire avec le vecteur courant.
   * @return Le rÈsultat du produit scalaire.
   */
  public double dot(SVector v);
  
  //------------
  // M…THODES //
  //------------
  
  /**
   * <p>
   * MÈthode effectuant le calcul de l'interpolation linÈaire entre deux vecteurs v0 et v1 par le facteur t.
   * L'Èquation mathÈmatique correspondant ‡ l'interpolation est
   * <ul> v = v0*(1 - t) + v1*t</ul>
   * o˘ v0 est le vecteur de rÈfÈrence et v1 est le vecteur pondÈrÈ par le facteur t.
   * </p>
   * 
   * @param v0 Le 1ier vecteur de rÈfÈrence pondÈrÈ par 1 - t.
   * @param v1 Le 2iËme vecteur pondÈrÈ par le facteur t.
   * @param t Le paramËtre de pondÈration.
   * @return Le vecteur interpolÈ.
   */
  public static SVector linearInterpolation(SVector v0, SVector v1, double t) 
  {
    throw new SNoImplementationException("La mÈthode n'a pas ÈtÈ implÈmentÈe.");
  }
  
  /**
   * <p>
   * MÈthode effectant le calcul de l'interpolation linÈaire en coordonnÈe barycentrique entre trois vecteurs v0, v1 et v2 par le facteur t1 et t2.
   * L'Èquation mathÈmatique correspondant ‡ l'interpolation est
   * <ul> v = v0*(1 - t1 - t2) + v1*t1 + v2*t2</ul>  
   * o˘ v0 est le vecteur de rÈfÈrence et v1 est le vecteur pondÈrÈ par le facteur t1 
   * et v2 est le vecteur pondÈrÈ par le facteur t2.
   * </p>
   * 
   * @param v0 Le 1ier vecteur de rÈfÈrence pondÈrÈ par 1 - t1 - t2.
   * @param v1 Le 2iËme vecteur pondÈrÈ par t1.
   * @param v2 Le 3iËme vecteur pondÈrÈ par t2.
   * @param t1 Le 1ier paramËtre de pondÈration.
   * @param t2 Le 2iËme paramËtre de pondÈration.
   * @return Le vecteur interpolÈ.
   */
  public static SVector linearBarycentricInterpolation(SVector v0, SVector v1, SVector v2, double t1, double t2)
  {
    throw new SNoImplementationException("La mÈthode n'a pas ÈtÈ implÈmentÈe.");
  }
  
  /**
   * <p>
   * MÈthode effectant le calcul de l'interpolation linÈaire en coordonnÈe barycentrique entre plusieurs vecteurs v0, v1, ... et v_n par les facteur t1, t2, ..., t_n.
   * L'Èquation mathÈmatique correspondant ‡ l'interpolation est
   * <ul> v = v0*(1 - t1 - t2 - ... - t_n) + v1*t1 + v2*t2 + ... + v_n*t_n</ul>  
   * o˘ v0 est le vecteur de rÈfÈrence et les vecteur v_i sont les vecteurs pondÈrÈs par les facteurs t_i.
   * </p>
   * 
   * @param vector_list La liste des vecteurs dans l'interpolation. Cette liste contient n+1 vecteur (v0 et les v_i Ètant de nombre n).
   * @param t_list La liste des facteurs de pondÈrations des vecteurs. Cette liste contient n facteurs (les n facteurs des n vecteur v_i).
   * @return Le vecteur interpolÈ.
   * @throws SRuntimeException Si le nombre d'ÈlÈment des listes n'est pas adÈquat (vector_list.size() != t_list.size()+1). 
   */
  public static SVector linearBarycentricInterpolation(List<SVector> vector_list, List<Double> t_list) throws SRuntimeException
  {
    // VÈrifier que les deux listes ont la mÍme taille
    if(vector_list.size() != t_list.size()+1)
      throw new SRuntimeException("Erreur SVectorUtil 007 : L'interpolation est impossible puisque les deux listes passÈes en paramËtre n'ont les bonnes tailles.");
    
    // VÈrification que la liste des vecteurs n'est pas vide
    if(vector_list.isEmpty())
      throw new SRuntimeException("Erreur SVectorUtil 008 : L'interpolation est impossible puisque la liste des vecteurs est vide.");
    
    throw new SNoImplementationException("La mÈthode n'a pas ÈtÈ implÈmentÈe.");
  }
  
}//fin de l'interface SVector
