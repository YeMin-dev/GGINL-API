package org.tat.gginl.api.domains;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.gginl.api.common.TableName;

import lombok.Data;

@Entity
@Table(name = TableName.GGINLAPP)
@Data
@Access(value = AccessType.FIELD)
public class GginlApp {
	
	private String id;
	private String appName;
	
	@Version
	private int version;

}
