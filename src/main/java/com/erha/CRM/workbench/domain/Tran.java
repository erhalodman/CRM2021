package com.erha.CRM.workbench.domain;

public class Tran {
	
	private String id;
	private String owner;
	private String money;	//交易金额
	private String name;	//交易名称
	private String expectedDate;	//预计成交日期
	private String customerId;
	private String stage;	//交易阶段
	private String type;	//交易类型
	private String source;	//交易来源
	private String activityId;
	private String contactsId;
	private String createBy;
	private String createTime;
	private String editBy;
	private String editTime;
	private String description;
	private String contactSummary;	//联系纪要
	private String nextContactTime;	//下次联系时间
	
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
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExpectedDate() {
		return expectedDate;
	}
	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getContactsId() {
		return contactsId;
	}
	public void setContactsId(String contactsId) {
		this.contactsId = contactsId;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public String toString() {
		return "Tran{" +
				"id='" + id + '\'' +
				", owner='" + owner + '\'' +
				", money='" + money + '\'' +
				", name='" + name + '\'' +
				", expectedDate='" + expectedDate + '\'' +
				", customerId='" + customerId + '\'' +
				", stage='" + stage + '\'' +
				", type='" + type + '\'' +
				", source='" + source + '\'' +
				", activityId='" + activityId + '\'' +
				", contactsId='" + contactsId + '\'' +
				", createBy='" + createBy + '\'' +
				", createTime='" + createTime + '\'' +
				", editBy='" + editBy + '\'' +
				", editTime='" + editTime + '\'' +
				", description='" + description + '\'' +
				", contactSummary='" + contactSummary + '\'' +
				", nextContactTime='" + nextContactTime + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Tran tran = (Tran) o;

		if (id != null ? !id.equals(tran.id) : tran.id != null) return false;
		if (owner != null ? !owner.equals(tran.owner) : tran.owner != null) return false;
		if (money != null ? !money.equals(tran.money) : tran.money != null) return false;
		if (name != null ? !name.equals(tran.name) : tran.name != null) return false;
		if (expectedDate != null ? !expectedDate.equals(tran.expectedDate) : tran.expectedDate != null) return false;
		if (customerId != null ? !customerId.equals(tran.customerId) : tran.customerId != null) return false;
		if (stage != null ? !stage.equals(tran.stage) : tran.stage != null) return false;
		if (type != null ? !type.equals(tran.type) : tran.type != null) return false;
		if (source != null ? !source.equals(tran.source) : tran.source != null) return false;
		if (activityId != null ? !activityId.equals(tran.activityId) : tran.activityId != null) return false;
		if (contactsId != null ? !contactsId.equals(tran.contactsId) : tran.contactsId != null) return false;
		if (createBy != null ? !createBy.equals(tran.createBy) : tran.createBy != null) return false;
		if (createTime != null ? !createTime.equals(tran.createTime) : tran.createTime != null) return false;
		if (editBy != null ? !editBy.equals(tran.editBy) : tran.editBy != null) return false;
		if (editTime != null ? !editTime.equals(tran.editTime) : tran.editTime != null) return false;
		if (description != null ? !description.equals(tran.description) : tran.description != null) return false;
		if (contactSummary != null ? !contactSummary.equals(tran.contactSummary) : tran.contactSummary != null)
			return false;
		return nextContactTime != null ? nextContactTime.equals(tran.nextContactTime) : tran.nextContactTime == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (owner != null ? owner.hashCode() : 0);
		result = 31 * result + (money != null ? money.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (expectedDate != null ? expectedDate.hashCode() : 0);
		result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
		result = 31 * result + (stage != null ? stage.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (source != null ? source.hashCode() : 0);
		result = 31 * result + (activityId != null ? activityId.hashCode() : 0);
		result = 31 * result + (contactsId != null ? contactsId.hashCode() : 0);
		result = 31 * result + (createBy != null ? createBy.hashCode() : 0);
		result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
		result = 31 * result + (editBy != null ? editBy.hashCode() : 0);
		result = 31 * result + (editTime != null ? editTime.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (contactSummary != null ? contactSummary.hashCode() : 0);
		result = 31 * result + (nextContactTime != null ? nextContactTime.hashCode() : 0);
		return result;
	}
}
