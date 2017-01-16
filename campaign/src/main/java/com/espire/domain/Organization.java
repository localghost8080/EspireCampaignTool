package com.espire.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="organisation")
public class Organization  implements Serializable{

	
	private static final long serialVersionUID = 8110176111069725224L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long organisationID;
	
	@ManyToOne
	@JoinColumn(name="DomainID")
	private Domain domain;
	
	@ManyToOne
	@JoinColumn(name="GeographyID")
	private Geography geography;
	
	private String organisation;
	
	private String organisationRemark;
	
	@JsonIgnore
	private Integer softDelete =1 ;
	
	
	public Long getOrganisationID() {
		return organisationID;
	}
	public void setOrganisationID(Long organisationID) {
		this.organisationID = organisationID;
	}
	public Domain getDomain() {
		return domain;
	}
	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	public Geography getGeography() {
		return geography;
	}
	public void setGeography(Geography geography) {
		this.geography = geography;
	}
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	public String getOrganisationRemark() {
		return organisationRemark;
	}
	public void setOrganisationRemark(String organisationRemark) {
		this.organisationRemark = organisationRemark;
	}
	public Integer getSoftDelete() {
		return softDelete;
	}
	public void setSoftDelete(Integer softDelete) {
		this.softDelete = softDelete;
	}

	@Override
	public String toString() {
		return "Organization [organisationID=" + organisationID + ", domain=" + domain + ", geography=" + geography
				+ ", organisation=" + organisation + ", organisationRemark=" + organisationRemark + ", softDelete="
				+ softDelete + "]";
	}
}
