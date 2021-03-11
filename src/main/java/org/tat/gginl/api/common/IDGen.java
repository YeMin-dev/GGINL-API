package org.tat.gginl.api.common;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.tat.gginl.api.domains.Branch;

import lombok.Data;


@Data
@Entity
@Table(name = "CUSTOM_ID_GEN")
public class IDGen implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String generateItem;
	private int maxValue;
	private String prefix;
	private String suffix;
	private String description;
	private int length;
	private boolean isDateBased;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;
	@Version
	private int version;

	public IDGen() {
	}

	public IDGen(String id, String generateItem, int maxValue, String prefix, int length, boolean isDateBased) {
		this.id = id;
		this.generateItem = generateItem;
		this.maxValue = maxValue;
		this.prefix = prefix;
		this.length = length;
		this.isDateBased = true;
//		this.branch = branch;
	}

	public IDGen(String id, String generateItem, int maxValue, String prefix, String suffix, String description, int length, boolean isDateBased) {
		super();
		this.id = id;
		this.generateItem = generateItem;
		this.maxValue = maxValue;
		this.prefix = prefix;
		this.suffix = suffix;
		this.description = description;
		this.length = length;
		this.isDateBased = isDateBased;
//		this.branch = branch;
	}

	

}