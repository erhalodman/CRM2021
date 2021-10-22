package com.erha.CRM.workbench.dao;

import com.erha.CRM.workbench.domain.Customer;
import org.apache.ibatis.annotations.Param;

public interface CustomerDao {

    Customer selectCustomer(@Param("name")String name,@Param("website")String website,@Param("phone")String phone);

    Integer insertCustomer(Customer customer);
}
