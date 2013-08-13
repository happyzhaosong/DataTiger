package com.data.collect.common.dto;

import com.data.collect.common.annotation.DBColumn;
import com.data.collect.common.annotation.DBTable;

@DBTable(name="role")
public class RoleDTO extends BaseDTO {

	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	@DBColumn(name="role_name")
	private String roleName;
	
	@DBColumn(name="role_desc")
	private String roleDesc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
}
