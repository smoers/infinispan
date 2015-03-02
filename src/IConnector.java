package org.bird.db.connectors;

import java.util.UUID;

/**
 * Interface utilisé pour la définition des classes de type connecteur
 * @author smoers
 *
 */
public interface IConnector {
	
	/**
	 * Get ID
	 * @return ID int
	 */
	public UUID getID();
	
	/**
	 * Set le nom du connecteur
	 * @param name String
	 */
	public void setName(String name);
	
	/**
	 * Retourne le nom attribué au connecteur
	 * @return Name String
	 */
	public String getName();
	
	/**
	 * Retourne le FQCN de la classe à instancifié pour onbtenir l'adapter 
	 * lié à cette adapter
	 * @return BAdapter String
	 */
	public String getBAdapter();
	
	/**
	 * Retourne le FQCN de la classe de cette instance de connecteur
	 * @return BDBC String
	 */
	public String getBDBC();
	


}
