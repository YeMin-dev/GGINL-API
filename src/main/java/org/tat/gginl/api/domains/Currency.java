/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.gginl.api.domains;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.gginl.api.common.FormatID;
import org.tat.gginl.api.common.TableName;

import lombok.Data;

@Data
@Entity
@Table(name = TableName.CUR)
@TableGenerator(name = "CURRENCY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "CURRENCY_GEN", allocationSize = 10)
@NamedQueries(value = {
		@NamedQuery(name = "Currency.findAll", query = "SELECT c FROM Currency c ORDER BY c.currencyCode ASC"),
		@NamedQuery(name = "Currency.findByCurrencyCode", query = "SELECT c FROM Currency c WHERE c.currencyCode = :currencyCode"),
		@NamedQuery(name = "Currency.findById", query = "SELECT c FROM Currency c WHERE c.id = :id") })
@Access(value = AccessType.FIELD)
public class Currency implements Serializable {
	private static final long serialVersionUID = -6992572646153666363L;
	@Transient
	private String id;
	@Transient
	private String prefix;
	@Column(name = "CUR")
	private String currencyCode;
	private String description;
	private String symbol;
	private String inwordDesp1;
	private String inwordDesp2;
	private String myanInwordDesp1;
	private String myanInwordDesp2;
	private Boolean isHomeCur;
	private float m1;
	private float m2;
	private float m3;
	private float m4;
	private float m5;
	private float m6;
	private float m7;
	private float m8;
	private float m9;
	private float m10;
	private float m11;
	private float m12;
	private float m13;

	@Version
	private int version;

	public Currency() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CURRENCY_GEN")
	@Access(value = AccessType.PROPERTY)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null) {
			this.id = FormatID.formatId(id, getPrefix(), 10);
		}
	}

	

}
