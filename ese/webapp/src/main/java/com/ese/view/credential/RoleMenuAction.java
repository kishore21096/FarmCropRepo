/*
 * RoleMenuAction.java
 * Copyright (c) 2008-2009, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.ese.view.credential;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.ese.view.ESEAction;
import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.service.IRoleService;
import com.sourcetrace.eses.service.IUserService;
import com.sourcetrace.eses.umgmt.entity.Menu;
import com.sourcetrace.eses.umgmt.entity.Role;
import com.sourcetrace.eses.util.JsonUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.ReflectUtil;
import com.sourcetrace.eses.util.StringUtil;

/**
 * The Class RoleMenuAction.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class RoleMenuAction extends ESEAction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(RoleMenuAction.class);
	private IRoleService roleService;
	private long selectedParentMenu;
	private long selectedRole;
	private String command;
	private String id;
	private List<Long> selected = new ArrayList<Long>();
	private List<Menu> availableMenu;
	private List<Menu> selectedMenu;
	private List<Menu> allSubMenu = new ArrayList<Menu>();
	private List<Role> roles = new ArrayList<Role>();
	// private List<Menu> parentMenus;
	private Map<Long, String> parentMenus = new LinkedHashMap<Long, String>();
	private String roleName;

	private String branchId_F;
	private String subBranchId_F;
	
	private String selectedRoleId;
	private String selectedParentMenuId;
	
	@Autowired
	private IUserService userService;

	/**
	 * Sets the role service.
	 * 
	 * @param roleService
	 *            the new role service
	 */
	public void setRoleService(IRoleService roleService) {

		this.roleService = roleService;
	}

	/**
	 * Gets the available menu.
	 * 
	 * @return the available menu
	 */
	public List<Menu> getAvailableMenu() {

		return availableMenu;
	}

	/**
	 * Gets the selected menu.
	 * 
	 * @return the selected menu
	 */
	public List<Menu> getSelectedMenu() {

		return selectedMenu;
	}

	/**
	 * Gets the selected parent menu.
	 * 
	 * @return the selected parent menu
	 */
	public long getSelectedParentMenu() {

		return selectedParentMenu;
	}

	/**
	 * Sets the selected parent menu.
	 * 
	 * @param selectedParentMenu
	 *            the new selected parent menu
	 */
	public void setSelectedParentMenu(long selectedParentMenu) {

		this.selectedParentMenu = selectedParentMenu;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {

		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {

		this.id = id;
	}

	/**
	 * Gets the command.
	 * 
	 * @return the command
	 */
	public String getCommand() {

		return command;
	}

	/**
	 * Sets the command.
	 * 
	 * @param command
	 *            the new command
	 */
	public void setCommand(String command) {

		this.command = command;
	}

	/**
	 * Gets the role name.
	 * 
	 * @return the role name
	 */
	public String getRoleName() {

		return roleName;
	}

	/**
	 * Sets the role name.
	 * 
	 * @param roleName
	 *            the new role name
	 */
	public void setRoleName(String roleName) {

		this.roleName = roleName;
	}

	/**
	 * Gets the selected.
	 * 
	 * @return the selected
	 */
	public List<Long> getSelected() {

		return selected;
	}

	/**
	 * Sets the selected.
	 * 
	 * @param selected
	 *            the new selected
	 */
	public void setSelected(List<Long> selected) {

		this.selected = selected;
	}

	/**
	 * Gets the selected role.
	 * 
	 * @return the selected role
	 */
	public long getSelectedRole() {

		return selectedRole;
	}

	/**
	 * Sets the selected role.
	 * 
	 * @param selectedRole
	 *            the new selected role
	 */
	public void setSelectedRole(long selectedRole) {

		this.selectedRole = selectedRole;
	}

	/**
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {

		return super.execute();
	}

	/**
	 * List.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String list() throws Exception {
		roles = new ArrayList<Role>();
		if (!StringUtil.isEmpty(getBranchId())) {
			roles = roleService.listRoles();
		}
		
		if(getIsMultiBranch().equals("1") &&!StringUtil.isEmpty(getIsParentBranch())){
			roles = roleService.listRoles();
		}
		
		// loading parent menus here due to it does only acquire the en or es
		// property keys while
		// loading it some actions
		List<Menu> parentMenuList = roleService.listParentMenus();
		if (!ObjectUtil.isListEmpty(parentMenuList)) {
			for (Menu menu : parentMenuList) {
				
				if (menu.getName().contains("service.dynamicCertification")
						|| menu.getName().contains("report.dynmaicCertification")) {
					Object[] obj = new Object[3];
					obj[0] = menu.getId();
					obj[1] = menu.getLabel();
					obj[2] = menu.getUrl();
					parentMenus.put(menu.getId(), getMenuLabel(obj));
				
				} else {
					parentMenus.put(menu.getId(), getLocaleProperty(menu.getLabel()));
				}
				
				
			}
		}
		request.setAttribute(HEADING, getLocaleProperty("rolemenu.list"));
		return LIST;
	}

	public void populateRoles() {
		roles = new ArrayList<Role>();
		if (!branchId_F.equals("-1")) {
			roles = roleService.listRolesByTypeAndBranchId(roleService.listFilters().get(0).getId(), getBranchId_F());
		}
		Map<String, String> map = ReflectUtil.buildMap(roles, new String[] { "id", "name" });
		sendAjaxResponse(JsonUtil.maptoJSONArrayMap(map));
	}

	/**
	 * Data.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String data() throws Exception {

        PrintWriter out = null;
        response.setCharacterEncoding("UTF-8");
        List<String> menuList = new ArrayList<String>();
        List<Menu> menus = roleService.getSelectedSubMenus(getSelectedRole(), getSelectedParentMenu());
         String branchId    =  request.getSession().getAttribute(ISecurityFilter.CURRENT_BRANCH) ==null ? "" : (String) request.getSession().getAttribute(ISecurityFilter.CURRENT_BRANCH);
         Integer isMultibranchApp = (Integer) request.getSession().getAttribute(ISecurityFilter.IS_MULTI_BRANCH_APP);
         branchId = StringUtil.isEmpty(branchId) ? branchId_F : branchId;
         if(StringUtil.isEmpty(branchId) && !StringUtil.isEmpty(selectedRole) && selectedRole > -1){
             Role role = userService.findRoleExcludeBranch(selectedRole);
             branchId = role==null ? "" : role.getBranchId();
         }
         
         boolean isBranchMaster = false;
              if(isMultibranchApp==1 ){
             if(!StringUtil.isEmpty(branchId)){
             BranchMaster fm  = clientService.findBranchMasterByBranchId(branchId);
           
             if(fm.getParentBranch()!=null){
                 isBranchMaster = true;
             
         
         }
        }
              }
            Map<String,Menu> menusMap = new HashMap<>();
        if (menus != null) {
            Iterator<Menu> iterator = menus.iterator();
            while (iterator.hasNext()) {
                Menu men = iterator.next();
                
                
                if (men.getName().contains("service.dynamicCertification")
						|| men.getName().contains("report.dynmaicCertification")) {
					Object[] obj = new Object[3];
					obj[0] = men.getId();
					obj[1] = men.getLabel();
					obj[2] = men.getUrl();
					   menusMap.put(getMenuLabel(obj),men);
					   menuList.add(getMenuLabel(obj));
				
				} else {
					   menusMap.put(men.getLabel(),men);
					   menuList.add(getLocaleProperty(men.toString()));
				}
               
            }
            if(isBranchMaster && isMultibranchApp==1 && !menus.isEmpty()){
                
                Menu id = menusMap.get("profile.branchMaster");
                if(id!=null){
                menus.remove(id);
                }
               
            }

            try {
                out = response.getWriter();
                out.print(menuList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      
        return null;

    }

	/**
	 * Update.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String update() throws Exception {

        String view = "input";
        /*
         * if ((selectedRole == -1) && (selectedParentMenu == -1)) {
         * addActionError(getText("emptyRoleParentMenu"));
         * request.setAttribute(HEADING, getText("rolemenu.list")); return
         * list(); }
         */
        if ("-1".equalsIgnoreCase(branchId_F)) {
            addActionError(getLocaleProperty("emptyOrganiztion"));
            request.setAttribute(HEADING, getLocaleProperty("rolemenu.list"));
            return list();
        } else if (selectedRole == -1) {
            addActionError(getLocaleProperty("emptyRole"));
            request.setAttribute(HEADING, getLocaleProperty("rolemenu.list"));
            return list();
        } else if (selectedParentMenu == -1) {
            addActionError(getLocaleProperty("emptyParentMenu"));
            request.setAttribute(HEADING, getLocaleProperty("rolemenu.list"));
            return list();
        } else {
            setRoles(roleService.listRoles());
            Role rName = roleService.findRole(getSelectedRole());
            if (rName != null) {
                setRoleName(rName.getName());
            } else {
                request.setAttribute(HEADING, getLocaleProperty("rolemenu.list"));
                return list();
            }
            List<Menu> parentMenuList = roleService.listParentMenus();
            if (!ObjectUtil.isListEmpty(parentMenuList)) {
                for (Menu menu : parentMenuList) {
                	if (menu.getName().contains("service.dynamicCertification")
    						|| menu.getName().contains("report.dynmaicCertification")) {
    					Object[] obj = new Object[3];
    					obj[0] = menu.getId();
    					obj[1] = menu.getLabel();
    					obj[2] = menu.getUrl();
    					parentMenus.put(menu.getId(), getMenuLabel(obj));
    				
    				} else {
    					parentMenus.put(menu.getId(), getLocaleProperty(menu.getLabel()));
    				}
                }
            }
            if (StringUtil.isEmpty(getId())) {
                String branchId = "";
                if(!StringUtil.isEmpty(selectedRole) && selectedRole > -1){
                    Role role = userService.findRoleExcludeBranch(selectedRole);
                    branchId = role==null ? "" : role.getBranchId();
                }
                if(StringUtil.isEmpty(branchId)){
                branchId    =  request.getSession().getAttribute(ISecurityFilter.CURRENT_BRANCH) ==null ? "" : (String) request.getSession().getAttribute(ISecurityFilter.CURRENT_BRANCH);
                branchId = StringUtil.isEmpty(branchId) ? branchId_F : branchId;
                }
                boolean isBranchMaster = false;
                if(getIsMultiBranch().equals("1") && !StringUtil.isEmpty(branchId)){
                    BranchMaster fm  = clientService.findBranchMasterByBranchId(branchId);
                  
                    if(fm.getParentBranch()!=null){
                        isBranchMaster = true;
                    
                
                }
               }
                   Map<String,Menu> menusMap = new HashMap<>();
                Map<String, List<Menu>> menus = roleService.getAvailableSelectedSubMenus(getSelectedRole(),
                        getSelectedParentMenu());
                
                
                availableMenu = menus.get(IRoleService.AVAILABLE);
                for (Menu aMenu : availableMenu) {
                    menusMap.put(aMenu.getLabel(), aMenu);
                    if (aMenu.getName().contains("service.dynamicCertification")
    						|| aMenu.getName().contains("report.dynmaicCertification")) {
    					Object[] obj = new Object[3];
    					obj[0] = aMenu.getId();
    					obj[1] = aMenu.getLabel();
    					obj[2] = aMenu.getUrl();
    					aMenu.setName(getMenuLabel(obj));
    				
    				
    				} else {
    					aMenu.setName(getLocaleProperty(aMenu.getName()));
    				}
                    
                }
                selectedMenu = menus.get(IRoleService.SELECTED);
                
                allSubMenu = new ArrayList<Menu>();
                allSubMenu.addAll(menus.get(IRoleService.AVAILABLE));
                allSubMenu.addAll(menus.get(IRoleService.SELECTED));
                
                Object[] roleInfo = roleService.findRoleInfo(selectedRole);
                if (!ObjectUtil.isEmpty(roleInfo[2])) {
                    branchId_F = roleInfo[2].toString();
                } else {
                    branchId_F = "";
                }
                for (Menu sMenu : selectedMenu) {
                    if(!menusMap.containsKey("profile.branchMaster")){
                    menusMap.put(sMenu.getLabel(), sMenu);
                    }
                    if (sMenu.getName().contains("service.dynamicCertification")
    						|| sMenu.getName().contains("report.dynmaicCertification")) {
    					Object[] obj = new Object[3];
    					obj[0] = sMenu.getId();
    					obj[1] = sMenu.getLabel();
    					obj[2] = sMenu.getUrl();
    					sMenu.setName(getMenuLabel(obj));
    				
    				
    				} else {
    					sMenu.setName(getLocaleProperty(sMenu.getName()));
    				}
                }
                 if(isBranchMaster && getIsMultiBranch().equals("1") ){
                        Menu id = menusMap.get("profile.branchMaster");
                        availableMenu.remove(id);
                        selectedMenu.remove(id);
                    }
                
            } else {
                roleService.editSubMenusForRole(getSelectedRole(), getSelectedParentMenu(), getSelected());
                view = list();
            }
        }

        request.setAttribute(HEADING, getLocaleProperty("rolemenu.list"));
        return view;
    }

	/**
	 * Sets the parent menus.
	 * 
	 * @param parentMenus
	 *            the parent menus
	 */
	public void setParentMenus(Map<Long, String> parentMenus) {

		this.parentMenus = parentMenus;
	}

	/**
	 * Gets the parent menus.
	 * 
	 * @return the parent menus
	 */
	public Map<Long, String> getParentMenus() {

		return parentMenus;
	}

	/**
	 * Sets the roles.
	 * 
	 * @param roles
	 *            the new roles
	 */
	public void setRoles(List<Role> roles) {

		this.roles = roles;
	}

	/**
	 * Gets the roles.
	 * 
	 * @return the roles
	 */
	public List<Role> getRoles() {

		return roles;
	}

	public String getBranchId_F() {
		return branchId_F;
	}

	public void setBranchId_F(String branchId_F) {
		this.branchId_F = branchId_F;
	}

	public String getIsMultiBranch() {

		if (ObjectUtil.isEmpty(request)) {
			request = ReflectUtil.getCurrentHttpRequest();
		}
		Object biObj = ObjectUtil.isEmpty(request) ? null
				: request.getSession().getAttribute(ISecurityFilter.IS_MULTI_BRANCH_APP);
		return ObjectUtil.isEmpty(biObj) ? null : biObj.toString();
	}
	
	

	public String getSubBranchId_F() {
		return subBranchId_F;
	}

	public void setSubBranchId_F(String subBranchId_F) {
		this.subBranchId_F = subBranchId_F;
	}
	
	  public Map<String, String> getSubBranchesMap() {
			return clientService.listChildBranchIds(getBranchId()).stream().filter(branch -> !ObjectUtil.isEmpty(branch))
					.collect(Collectors.toMap(BranchMaster::getBranchId, BranchMaster::getName));
		}

	public List<Menu> getAllSubMenu() {
		return allSubMenu;
	}

	public void setAllSubMenu(List<Menu> allSubMenu) {
		this.allSubMenu = allSubMenu;
	}
	
	public void populateAvailableSubMenusByRole() {
		Map<String, List<Menu>> menus = roleService.getAvailableSelectedSubMenus(Long.valueOf(getSelectedRoleId()),
				Long.valueOf(getSelectedParentMenuId()));
		List<Menu> existingMenusForRole = menus.get(IRoleService.SELECTED);
		List<String> existingMenuIds = existingMenusForRole.stream().map(m -> String.valueOf(m.getId()))
				.collect(Collectors.toList());

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("existingMenuIds", existingMenuIds);
		sendAjaxResponse(jsonObject);

	}

	public String getSelectedRoleId() {
		return selectedRoleId;
	}

	public void setSelectedRoleId(String selectedRoleId) {
		this.selectedRoleId = selectedRoleId;
	}

	public String getSelectedParentMenuId() {
		return selectedParentMenuId;
	}

	public void setSelectedParentMenuId(String selectedParentMenuId) {
		this.selectedParentMenuId = selectedParentMenuId;
	}
	
}
