package org.tat.gginl.api.domains;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.persistence.Version;

import lombok.Data;

@Entity
@Data
@Access(value = AccessType.FIELD)
public class GginlApp {
	
	@Transient
	private String id;
	
	private String appName;
	
	@Version
	private int version;

}
