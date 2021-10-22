package com.erha.CRM.workbench.domain;

public class Customer {
	
	private String id;
	private String owner;
	private String name;
	private String website;
	private String phone;
	private String createBy;
	private String createTime;
	private String editBy;
	private String editTime;
	private String contactSummary;
	private String nextContactTime;
	private String description;
	private String address;

	public Customer(String id, String owner, String name, String website, String phone, String createBy, String createTime, String editBy, String editTime, String contactSummary, String nextContactTime, String description, String address) {
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.website = website;
		this.phone = phone;
		this.createBy = createBy;
		this.createTime = createTime;
		this.editBy = editBy;
		this.editTime = editTime;
		this.contactSummary = contactSummary;
		this.nextContactTime = nextContactTime;
		this.description = description;
		this.address = address;
	}

	public Customer() {
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getEditBy() {
		return editBy;
	}
	public void setEditBy(String editBy) {
		this.editBy = editBy;
	}
	public String getEditTime() {
		return editTime;
	}
	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
	public String getContactSummary() {
		return contactSummary;
	}
	public void setContactSummary(String contactSummary) {
		this.contactSummary = contactSummary;
	}
	public String getNextContactTime() {
		return nextContactTime;
	}
	public void setNextContactTime(String nextContactTime) {
		this.nextContactTime = nextContactTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Customer customer = (Customer) o;

		if (id != null ? !id.equals(customer.id) : customer.id != null) return false;
		if (owner != null ? !owner.equals(customer.owner) : customer.owner != null) return false;
		if (name != null ? !name.equals(customer.name) : customer.name != null) return false;
		if (website != null ? !website.equals(customer.website) : customer.website != null) return false;
		if (phone != null ? !phone.equals(customer.phone) : customer.phone != null) return false;
		if (createBy != null ? !createBy.equals(customer.createBy) : customer.createBy != null) return false;
		if (createTime != null ? !createTime.equals(customer.createTime) : customer.createTime != null) return false;
		if (editBy != null ? !editBy.equals(customer.editBy) : customer.editBy != null) return false;
		if (editTime != null ? !editTime.equals(customer.editTime) : customer.editTime != null) return false;
		if (contactSummary != null ? !contactSummary.equals(customer.contactSummary) : customer.contactSummary != null)
			return false;
		if (nextContactTime != null ? !nextContactTime.equals(customer.nextContactTime) : customer.nextContactTime != null)
			return false;
		if (description != null ? !description.equals(customer.description) : customer.description != null)
			return false;
		return address != null ? address.equals(customer.address) : customer.address == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (owner != null ? owner.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (website != null ? website.hashCode() : 0);
		result = 31 * result + (phone != null ? phone.hashCode() : 0);
		result = 31 * result + (createBy != null ? createBy.hashCode() : 0);
		result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
		result = 31 * result + (editBy != null ? editBy.hashCode() : 0);
		result = 31 * result + (editTime != null ? editTime.hashCode() : 0);
		result = 31 * result + (contactSummary != null ? contactSummary.hashCode() : 0);
		result = 31 * result + (nextContactTime != null ? nextContactTime.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		return result;
	}
}
