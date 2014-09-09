package org.alfresco.module.org_alfresco_module_rm.model.rma.type;

import org.alfresco.service.namespace.QName;

/**
 * Helper bean that registers a custom RM site type.
 * 
 * NOTE:  this will be moved into RM 2.3.b and explains the RM core package
 * 
 * @author Roy Wetherall
 */
public class SiteTypeBootstrap 
{
	/** rm site component */
	private RmSiteType rmSiteComponent;
	
	/** site QName */
	private QName siteType;
	
	/** file plan QName */
	private QName filePlanType;
	
	/**
	 * @param rmSiteType	rm site compoent
	 */
	public void setRmSiteComponent(RmSiteType rmSiteType) 
	{
		this.rmSiteComponent = rmSiteType;
	}
	
	/**
	 * @param siteQName	site qname
	 */
	public void setSiteType(QName siteType) 
	{
		this.siteType = siteType;
	}
	
	/**
	 * @param filePlanQName	file plan qname
	 */
	public void setFilePlanType(QName filePlanType) 
	{
		this.filePlanType = filePlanType;
	}

	/**
	 * Init method
	 */
	public void init()
	{
		rmSiteComponent.registerFilePlanType(siteType, filePlanType);
	}
}
