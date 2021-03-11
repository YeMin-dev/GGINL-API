package org.tat.gginl.api.domains;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

import org.tat.gginl.api.common.TableName;
import org.tat.gginl.api.common.emumdata.ItemType;


@Table(name = TableName.WEBPAGE)
@Entity
@NamedQueries(value = { @NamedQuery(name = "ResourceItem.findAll", query = "SELECT a FROM ResourceItem a WHERE a.permission = TRUE ORDER BY a.itemType"),
		@NamedQuery(name = "ResourceItem.findNoPermissionItems", query = "SELECT a FROM ResourceItem a WHERE a.permission = FALSE"),
		@NamedQuery(name = "ResourceItem.findByType", query = "SELECT a FROM ResourceItem a WHERE a.permission = TRUE AND a.itemType = :itemType") })
public class ResourceItem implements Serializable {
	private static final long serialVersionUID = 966923059686092674L;
	@Id
	private String id;
	private String name;
	private String url;
	private boolean permission;
	@Enumerated(EnumType.STRING)
	private ItemType itemType;
	@Version
	private int version;

	public ResourceItem() {
	}

	public ResourceItem(String id, String name, String url, boolean permission, ItemType itemType) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.permission = permission;
		this.itemType = itemType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isPermission() {
		return permission;
	}

	public void setPermission(boolean permission) {
		this.permission = permission;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itemType == null) ? 0 : itemType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (permission ? 1231 : 1237);
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResourceItem other = (ResourceItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itemType != other.itemType)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (permission != other.permission)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
