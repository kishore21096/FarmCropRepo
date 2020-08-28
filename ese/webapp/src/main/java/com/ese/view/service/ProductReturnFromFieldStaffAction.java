/*
] * DistributionToFieldStaffAction.java
 * Copyright (c) 2015-2016, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.ese.view.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.ese.entity.util.ESESystem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.entity.AgentType;
import com.sourcetrace.eses.entity.BranchMaster;
import com.sourcetrace.eses.entity.Profile;
import com.sourcetrace.eses.entity.Warehouse;
import com.sourcetrace.eses.entity.WarehouseProduct;
import com.sourcetrace.eses.entity.WarehouseProductDetail;
import com.sourcetrace.eses.filter.ISecurityFilter;
import com.sourcetrace.eses.order.entity.profile.SubCategory;
import com.sourcetrace.eses.order.entity.txn.AgroTransaction;
import com.sourcetrace.eses.order.entity.txn.Distribution;
import com.sourcetrace.eses.order.entity.txn.DistributionDetail;
import com.sourcetrace.eses.order.entity.txn.ProductReturn;
import com.sourcetrace.eses.order.entity.txn.ProductReturnDetail;
import com.sourcetrace.eses.service.IAccountService;
import com.sourcetrace.eses.service.IAgentService;
import com.sourcetrace.eses.service.ICategoryService;
import com.sourcetrace.eses.service.IFarmerService;
import com.sourcetrace.eses.service.ILocationService;
import com.sourcetrace.eses.service.IProductDistributionService;
import com.sourcetrace.eses.service.IProductService;
import com.sourcetrace.eses.service.IReportService;
import com.sourcetrace.eses.service.IUniqueIDGenerator;
import com.sourcetrace.eses.util.CurrencyUtil;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.eses.util.profile.Product;
import com.sourcetrace.esesw.entity.profile.ESEAccount;
import com.sourcetrace.esesw.entity.profile.HarvestSeason;
import com.sourcetrace.esesw.entity.profile.Season;
import com.sourcetrace.esesw.entity.txn.ESETxn;
import com.sourcetrace.esesw.view.WebTransactionAction;

// TODO: Auto-generated Javadoc
/**
 * The Class DistributionToFieldStaffAction.listWarehouseProductByAgent
 */
public class ProductReturnFromFieldStaffAction extends WebTransactionAction {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7392928098354296987L;

    /** The product distribution service. */
    private IProductDistributionService productDistributionService;

    /** The location service. */
    private ILocationService locationService;

    /** The product service. */
    private IProductService productService;

    /** The agent service. */
    @Autowired
    private IAgentService agentService;

    @Autowired
    private ICategoryService categoryService;

    /** The id generator. */
    private IUniqueIDGenerator idGenerator;

    /** The account service. */
    private IAccountService accountService;
    private IFarmerService farmerService;

    /** The agent. */
    private Agent agent;

    /** The product. */
    private Product product;

    /** The distribution. */
    private ProductReturn productReturn;

    /** The distributiondetail. */
    private ProductReturnDetail productReturnDetail;

    /** The id. */
    private String id;

    /** The selected category. */
    private String selectedCategory;

    /** The selected sub category. */
    private String selectedSubCategory;

    /** The selected warehouse. */
    private String selectedWarehouse;

    /** The selected agent. */
    private String selectedAgent;

    /** The selected product. */
    private String selectedProduct;

    /** The selected season. */
    private String selectedSeason;

    /** The selected unit. */
    private String selectedUnit;

    /** The stock. */
    private double stock;

    /** The distribution product. */
    private String productReturnProduct;

    /** The product price. */
    private String productPrice;

    /** The units. */
    private String units;

    /** The distribution product list. */
    private String productReturnProductList;

    /** The start date. */
    private String startDate;
    
    private String sDate;

    /** The receipt number. */
    private String receiptNumber;

    /** The distribution description. */
    private String productReturnDescription;

    /** The product total string. */
    private String productTotalString;

    /** The reference no. */
    private String referenceNo;

    /** The serial no. */
    private String serialNo;

    /** The selected field staff. */
    private String selectedFieldStaff;

    /** The warehouse list. */
    private List<String> warehouseList = new ArrayList<String>();

    /** The units list. */
    private List<String> unitsList = new ArrayList<String>();

    /** The products list. */
    private List<Object[]> productsList = new ArrayList<Object[]>();

    /** The agent list. */
    Map<String, String> agentList = new LinkedHashMap<String, String>();

    /** The coopearative list. */
    Map<String, String> coopearativeList = new LinkedHashMap<String, String>();
    // private List<String> agentLists = new ArrayList<String>();
    /** The agent lists. */
    Map<String, String> agentLists = new LinkedHashMap<String, String>();

    Map<String, String> categoryList = new LinkedHashMap<String, String>();
    private Map<Long,String>batchNoList=new LinkedHashMap<Long,String>();
    
    private String costPriceArrayValue;
    private String seasonName;
    private String harvestSeasonEnabled;
    private String seasonCodeAndName;
    private String batchNo;
    private String enableBatchNo;
    // private List<String> agentLists = new ArrayList<String>();
     Map<String, String> harvestSeasonList = new LinkedHashMap<String, String>();
   
    private String selectedDate;
 	private List<ProductReturnDetail> productReturnDetailList;
 	private String productReturnDetails;
 	private Set<ProductReturnDetail> newProductReturnDetails;
 	private String approved;
    private  double WarehouseProductStock;
    private  double fieldStaffStock;
    private String seasonCode;
    private String productReturnTxnTime;
    private String updateTxnTime;
    private Map<String, Object> productReturnMap = new LinkedHashMap<String, Object>();
 	public String data() throws Exception {

 		Map<String, String> searchRecord = getJQGridRequestParam(); // get the
 		ProductReturnDetail filter = new ProductReturnDetail();

 		if (!StringUtil.isEmpty(searchRecord.get("branchId"))) {
			if (!getIsMultiBranch().equalsIgnoreCase("1")) {
				List<String> branchList = new ArrayList<>();
				branchList.add(searchRecord.get("branchId").trim());
				filter.setBranchesList(branchList);
			} else {
				List<String> branchList = new ArrayList<>();
				List<BranchMaster> branches = clientService.listChildBranchIds(searchRecord.get("branchId").trim());
				branchList.add(searchRecord.get("branchId").trim());
				branches.stream().filter(branch -> !StringUtil.isEmpty(branch)).forEach(branch -> {
					branchList.add(branch.getBranchId());
				});
				filter.setBranchesList(branchList);
			}
		}
 		if (!StringUtil.isEmpty(searchRecord.get("subBranchId"))) {
			filter.setBranchId(searchRecord.get("subBranchId").trim());
		}

 		if (!StringUtil.isEmpty(searchRecord.get("warehouse"))) {
 			filter.setWarehouseName(searchRecord.get("warehouse").trim());
 		}

     
     // private List<String> agentLists = new ArrayList<String>();
      Map<String, String> harvestSeasonList = new LinkedHashMap<String, String>();
 		if (!StringUtil.isEmpty(searchRecord.get("mobileUser"))) {
 			filter.setAgentName(searchRecord.get("mobileUser").trim());
 		}
 		if (!StringUtil.isEmpty(searchRecord.get("category"))) {
 			filter.setCategoryName(searchRecord.get("category").trim());
 		}
 		if (!StringUtil.isEmpty(searchRecord.get("product"))) {
 			filter.setProductName(searchRecord.get("product").trim());
 		}
 		 String approved=preferncesService.findPrefernceByName(ESESystem.ENABLE_APPROVED);
         if(approved.equalsIgnoreCase("1")){
       	 filter.setEnableApproved("1");
        }else{
       	 filter.setEnableApproved("0");
        }

         String isMultiBranch =(String) getIsMultiBranch();
         if(isMultiBranch!=null && isMultiBranch.equals("0")){
        	 if(!StringUtil.isEmpty(getBranchId())){
             ProductReturn d  =  new ProductReturn();
             d.setBranchId(getBranchId());
             filter.setProductReturn(d);
         }
        	 
         }
        

 		Map data = reportService.listWithEntityFiltering(getDir(), getSort(), getStartIndex(), getResults(), filter,
 				getPage());

 		return sendJQGridJSONResponse(data);
 	}

 	public JSONObject toJSON(Object obj) {

 		ProductReturnDetail productReturnDetail = (ProductReturnDetail) obj;
 		JSONObject jsonObject = new JSONObject();
 		JSONArray rows = new JSONArray();
 		 if ((getIsMultiBranch().equalsIgnoreCase("1") && (getIsParentBranch().equals("1")||StringUtil.isEmpty(branchIdValue)))) {

	          if (StringUtil.isEmpty(branchIdValue)) {
					rows.add(!StringUtil.isEmpty(getBranchesMap().get(getParentBranchMap().get(productReturnDetail.getProductReturn().getBranchId())))
							? getBranchesMap().get(getParentBranchMap().get(productReturnDetail.getProductReturn().getBranchId()))
							: getBranchesMap().get(productReturnDetail.getProductReturn().getBranchId()));
				}
				rows.add(getBranchesMap().get(productReturnDetail.getProductReturn().getBranchId()));
	     } else {
	         if (StringUtil.isEmpty(branchIdValue)) {
	             rows.add(branchesMap.get(productReturnDetail.getProductReturn().getBranchId()));
	         }
	     }
 		
 		rows.add(productReturnDetail.getProductReturn().getServicePointName());
 	/*	List<Agent> agentList=(List<Agent>) agentService.listAgentProfileIdByAgentName(distributionDetail.getDistribution().getAgentName());*/
 		
 			
 			 rows.add(productReturnDetail.getProductReturn().getAgentName());
 		
 		
 		rows.add(productReturnDetail.getProduct().getSubcategory().getName());
 		rows.add(productReturnDetail.getProduct().getName());
 		jsonObject.put("id", productReturnDetail.getId());
 		jsonObject.put("cell", rows);
 		return jsonObject;
 	}
	/**
     * Creates the.
     * @return the string
     * @throws Exception the exception
     */
    public String create() throws Exception {

        if (id == null) {
            command = CREATE;
            if (!StringUtil.isEmpty(agentId)) {
                request.setAttribute("agentId", agentId);
                agent = agentService.findAgentByProfileId(agentId);
                if (!ObjectUtil.isEmpty(agent) && ESETxn.WEB_BOD == agent.getBodStatus()) {
                    Calendar currentDate = Calendar.getInstance();
                    //DateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT);
                    DateFormat df = new SimpleDateFormat(getGeneralDateFormat());
                    startDate = df.format(currentDate.getTime());
                    sDate=DateUtil.convertDateToString(currentDate.getTime(), getGeneralDateFormat());
                    request.setAttribute(HEADING, getText("create.page"));
                    setReceiptNumber(null);
                    setProductReturnDescription(null);
                    setCurrentSeason();
                    return INPUT;
                }
            }
            ESESystem preferences = preferncesService.findPrefernceById("1");
        	setEnableBatchNo(preferncesService.findPrefernceByName(ESESystem.ENABLE_BATCH_NO));
        	return INPUT;
        } else {
            // agentId = (String) request.getAttribute("agentId");
            Agent agent = new Agent(); // agentService.findAgentByProfileId(agentId);
            AgentType agentType = new AgentType();
            agentType.setCode(AgentType.COOPERATIVE_MANAGER);
            agent.setAgentType(agentType);
            agent.setStatus(Profile.ACTIVE);
            String[] warehouseArray = selectedWarehouse.split("-");
            Warehouse warehouse = locationService.findCoOperativeByCode(warehouseArray[1].trim());
            Set<Warehouse> warehouseSet = new HashSet<Warehouse>();
            warehouseSet.add(warehouse);
            agent.setWareHouses(warehouseSet);

            /*
             * if (ObjectUtil.isEmpty(agent) || ESETxn.EOD == agent.getBodStatus() || Agent.ACTIVE
             * != agent.getStatus()) { return REDIRECT; }
             */

            // Added for Handling Form ReSubmit - Please See at populateFarmerAccount() Method
            /*
             * if (ObjectUtil.isEmpty(request.getSession().getAttribute(agent.getProfileId() + "_" +
             * Distribution.PRODUCT_DISTRIBUTION_TO_FIELDSTAFF+ "_" +
             * WebTransactionAction.IS_FORM_RESUBMIT))) { setDistributionDescription(null);
             * setCurrentSeason(); setAgent(agent); return INPUT; }
             */
            Agent fieldStaff = agentService.findAgent(Long.parseLong(selectedAgent));
            if (ObjectUtil.isEmpty(fieldStaff)) {
                // reset();
                setProductReturnDescription(getText("fs.unavailable"));
                return INPUT;
            }

            // Forming FieldStaff Manager Distribution Object
            ProductReturn comProductReturn = new ProductReturn();
            comProductReturn.setAgroTransaction(getAgroTransaction(agent,
                    Profile.CO_OPEARATIVE_MANAGER));
            // Forming Co-Operative Manager Agro Transaction Object
            AgroTransaction fsAgroTransaction = getAgroTransaction(fieldStaff, Profile.AGENT);

            // PROCT DETAILS FOR DISTRIBUTION_DETAIL
            Set<ProductReturnDetail> productReturnDetailsSet = new HashSet<ProductReturnDetail>();
            ProductReturnDetail productReturnDetail = new ProductReturnDetail();
            if (productReturnProductList != null && productReturnProductList.length() > 0) {
                String[] products = productReturnProductList.split(",");
                for (int i = 0; i < products.length; i++) {
                    String[] product = products[i].split("\\|");
                    productReturnDetail = new ProductReturnDetail();
                    productReturnDetail.setProductReturn(productReturn);
                    // String[] productValues = product[0].split("-");
                    Product productDetails = productService.findProductByNameAndUnit(product[0]
                            .trim(), product[2].trim());
                    WarehouseProduct fieldStaffStock = productDistributionService
                    .findAgentAvailableStock(fieldStaff.getProfileId(),productDetails
                            .getId());
                    productReturnDetail.setProduct(productDetails);
                    try {
                        productReturnDetail.setQuantity(Double.valueOf(product[1]));
                        productReturnDetail.setUnit(product[2].trim());
                        //distributionDetail.setExistingQuantity(fieldStaffStock.getStock());
                        //distributionDetail.setCurrentQuantity(fieldStaffStock.getStock() - Double.valueOf(product[1]));
                        // distributionDetail.setPricePerUnit(Double.valueOf(product[3]));
                        // distributionDetail.setSubTotal(Double.valueOf(product[3]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    productReturnDetailsSet.add(productReturnDetail);
                }
            }

            // CREATING RECEIPT NUMBER
            String receiptnumberSeq = idGenerator.getProductReturnFromFieldStaffSeq();
            receiptNumber = receiptnumberSeq;
            comProductReturn.getAgroTransaction().setReceiptNo(receiptnumberSeq);
            comProductReturn.setSeason(productDistributionService.findCurrentSeason());
            comProductReturn.setProductReturnDetail(productReturnDetailsSet);
            comProductReturn.getAgroTransaction().setRefAgroTransaction(fsAgroTransaction);
            comProductReturn.getAgroTransaction().getRefAgroTransaction().setReceiptNo(
                    receiptnumberSeq);
            productDistributionService.saveProductReturnAndProductReturnDetail(comProductReturn);
            // reset();
            setProductReturnDescription(getText("receiptNumber") + " : " + receiptnumberSeq + " "
                    + getText("productreturnSucess"));
            return INPUT;
        }
    }

    /*
     * public String populateFieldStaff() throws Exception { if
     * (!selectedWarehouse.equalsIgnoreCase("null") && (!StringUtil.isEmpty(selectedWarehouse))) {
     * List<String> fielstaff = new ArrayList<String>(); Warehouse wa =
     * locationService.findWarehouseByCode(selectedWarehouse); List<Agent> agentList =
     * agentService.findFieldStaffByCooperativeId(Long.valueOf(wa.getId())); if
     * (!StringUtil.isEmpty(agentList)) { for (Agent agent : agentList) {
     * agentLists.add(agent.getPersonalInfo().getAgentName()); } } } sendResponse(agentLists);
     * return String.valueOf(agentLists); }
     */

    /*
     * public String populateFieldStaff() throws Exception { if
     * (!StringUtil.isEmpty(selectedWarehouse)) { String[] warehouse = selectedWarehouse.split("-");
     * String wa; List<Agent> agentList
     * =agentService.findFieldStaffByCooperativeId(Long.valueOf(wa.getId())); if
     * (!ObjectUtil.isListEmpty(agentList)) { for (Agent agent : agentList) {
     * agentLists.add(agent.getPersonalInfo().getAgentName()); } } } sendResponse(agentLists);
     * return null; }
     */
    /**
     * Reset.
     */
	public String detail() {

		String view = "";
		ESESystem preferences = preferncesService.findPrefernceById("1");
        DateFormat genDate=new SimpleDateFormat(preferences.getPreferences().get(ESESystem.GENERAL_DATE_FORMAT));
		if (id != null && !id.equals("")) {
			productReturnDetail = productDistributionService.findProductReturnDetailById(Long.valueOf(id));
			if (productReturnDetail == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
		HarvestSeason season = farmerService.findSeasonNameByCode(productReturnDetail.getProductReturn().getSeasonCode());
		setSeasonCodeAndName(!ObjectUtil.isEmpty(season) ? season.getName() : "");
	    //setSeasonCodeAndName(!ObjectUtil.isEmpty(season) ?season.getCode()+ "-" +season.getName() : "");
		Warehouse warehouse = locationService.findCoOperativeByCode(productReturnDetail.getProductReturn().getServicePointId());
		WarehouseProduct warehouseProduct = productDistributionService.findAvailableStockByWarehouseIdSelectedProductSeasonBatchNo(warehouse.getId(), productReturnDetail.getProduct().getId(),productReturnDetail.getProductReturn().getSeasonCode(),productReturnDetail.getBatchNo());
		WarehouseProductStock=warehouseProduct.getStock();
		if(!ObjectUtil.isEmpty(preferences)){
			if(!ObjectUtil.isEmpty(productReturnDetail)&& !(ObjectUtil.isEmpty(productReturnDetail.getProductReturn()))&& (!ObjectUtil.isEmpty(productReturnDetail.getProductReturn().getTxnTime()))){
				productReturnTxnTime=DateUtil.convertDateToString(productReturnDetail.getProductReturn().getTxnTime(),getGeneralDateFormat());
			}
			if(!ObjectUtil.isEmpty(productReturnDetail)&& !(ObjectUtil.isEmpty(productReturnDetail.getProductReturn()))&& (!ObjectUtil.isEmpty(productReturnDetail.getProductReturn().getUpdateTime()))){
				updateTxnTime=DateUtil.convertDateToString(productReturnDetail.getProductReturn().getUpdateTime(), getGeneralDateFormat());
			}
		}
		setEnableBatchNo(preferncesService.findPrefernceByName(ESESystem.ENABLE_BATCH_NO));
			setCurrentPage(getCurrentPage());
			command = UPDATE;
			view = DETAIL;
			request.setAttribute(HEADING, getText(DETAIL));
		} else {
			request.setAttribute(HEADING, getText(LIST));
			return LIST;
		}
		return view;

	}

	public String update() throws Exception {
		if (id != null && !id.equals("")) {
			productReturnDetail = productDistributionService.findProductReturnDetailById(Long.valueOf(id));
			if (productReturnDetail == null) {
				addActionError(NO_RECORD);
				return REDIRECT;
			}
			productReturnDetail.getProductReturn().setTransactionTime(DateUtil.convertDateToString(productReturnDetail.getProductReturn().getTxnTime(),getGeneralDateFormat()));
			HarvestSeason season = farmerService.findSeasonNameByCode(productReturnDetail.getProductReturn().getSeasonCode());
		    setSeasonCodeAndName(!ObjectUtil.isEmpty(season) ?season.getCode()+ "-" +season.getName() : "");
			productReturnDetailList = new LinkedList<ProductReturnDetail>();
			Warehouse warehouse = locationService.findCoOperativeByCode(productReturnDetail.getProductReturn().getServicePointId());
			WarehouseProduct warehouseProduct = productDistributionService.findAvailableStockByWarehouseIdSelectedProductSeasonBatchNo(warehouse.getId(), productReturnDetail.getProduct().getId()
					,productReturnDetail.getProductReturn().getSeasonCode(),productReturnDetail.getBatchNo());
			WarehouseProduct fiedlStaffStock = productDistributionService.findAvailableStockByAgentIdProductIdBatchNoSeason(productReturnDetail.getProductReturn().getAgentId(), productReturnDetail.getProduct().getId()
					,productReturnDetail.getProductReturn().getSeasonCode(),productReturnDetail.getBatchNo());
			
			if(fiedlStaffStock!=null){
				setFieldStaffStock(fiedlStaffStock.getStock());
			}
			WarehouseProductStock=warehouseProduct.getStock();
			productReturnDetailList.add(productReturnDetail);
			approved=preferncesService.findPrefernceByName(ESESystem.ENABLE_APPROVED);
			setEnableBatchNo(preferncesService.findPrefernceByName(ESESystem.ENABLE_BATCH_NO));
			setCurrentPage(getCurrentPage());
			id = null;
			command = UPDATE;
			request.setAttribute(HEADING, getText(UPDATE));
			return UPDATE;
		} else {
			approved=preferncesService.findPrefernceByName(ESESystem.ENABLE_APPROVED);
			newProductReturnDetails = formProductReturnDetail();
			if (!ObjectUtil.isListEmpty(newProductReturnDetails)) {
				ProductReturnDetail existingProductReturnDetail = productDistributionService
						.findProductReturnDetailById(newProductReturnDetails.iterator().next().getId());
				// AgroTransaction object information
				AgroTransaction refAgroTransaction = new AgroTransaction();
				AgroTransaction agroTransaction = new AgroTransaction();
				refAgroTransaction = getAgroTransaction(null, Profile.AGENT);
				refAgroTransaction.setReceiptNo(existingProductReturnDetail.getProductReturn().getReceiptNumber());
				agroTransaction = getAgroTransaction(null, Profile.CO_OPEARATIVE_MANAGER);
				agroTransaction.setReceiptNo(existingProductReturnDetail.getProductReturn().getReceiptNumber());
				agroTransaction.setRefAgroTransaction(refAgroTransaction);
				productDistributionService.saveAgroTransaction(refAgroTransaction);
				productDistributionService.saveAgroTransaction(agroTransaction);
				if(newProductReturnDetails.iterator().next().getStatus()==1){
				existingProductReturnDetail.getProductReturn().setStatus(newProductReturnDetails.iterator().next().getStatus());
				}
				
				existingProductReturnDetail.getProductReturn().setAgroTransaction(refAgroTransaction);
				for (ProductReturnDetail productReturnDetail : newProductReturnDetails) {
					 approved=preferncesService.findPrefernceByName(ESESystem.ENABLE_APPROVED);
				Double extistingDist = existingProductReturnDetail.getQuantity();
				Double finalQty = productReturnDetail.getQuantity() - extistingDist;
					// Distribution and DistributionDetail calculation
				existingProductReturnDetail.setExistingQuantity((productReturnDetail.getAvlQty()));
				existingProductReturnDetail.setQuantity(productReturnDetail.getQuantity());
					Double currentQty=Double.valueOf(productReturnDetail.getAvlQty())-Double.valueOf(productReturnDetail.getQuantity());;
					existingProductReturnDetail.setCurrentQuantity(String.valueOf(currentQty));
					existingProductReturnDetail.getProductReturn().setUpdatedUserName(getUsername());
					 Calendar currentDate = Calendar.getInstance();
                     //DateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT);
					 DateFormat df = new SimpleDateFormat(getGeneralDateFormat());
                     startDate = df.format(currentDate.getTime());
                     //existingDistributionDetail.getDistribution().setUpdateTime(DateUtil.convertStringToDate(startDate, "MM/dd/yyyy"));
                     existingProductReturnDetail.getProductReturn().setUpdateTime(DateUtil.convertStringToDate(startDate, getGeneralDateFormat()));
					
					productDistributionService.updateProductReturnDetail(existingProductReturnDetail);
					// calculation for fieldStaff stock
					Agent fieldStaff = agentService.findAgentByAgentId(productReturnDetail.getAgentId());
					WarehouseProduct fieldStaffStock = productDistributionService
							.findAvailableStockByAgentIdProductIdBatchNoSeason(fieldStaff.getProfileId(), productReturnDetail.getProductId(),existingProductReturnDetail.getProductReturn().getSeasonCode(),existingProductReturnDetail.getBatchNo());
					if (!ObjectUtil.isEmpty(fieldStaffStock)) {
						 if(approved.equals("1")){
						if (productReturnDetail.getStatus() == 1) {
							double stock =fieldStaffStock.getStock();
							//WarehouseProduct fieldStaffStocks = productDistributionService.findFieldStaffAvailableStock(distributionDetail.getAgentId(),distributionDetail.getProductId());
							fieldStaffStock.setStock(fieldStaffStock.getStock() + finalQty);
							 Set<WarehouseProductDetail>productDetails=new LinkedHashSet<WarehouseProductDetail>();
							  //  List<WarehouseProductDetail> productDetail= productDistributionService.findWarehouseproductDetailByWarehouseproductIdAndReceiptNo(warehouseProduct.getId(),existingDistributionDetail.getDistribution().getReceiptNumber());
							    WarehouseProductDetail warehouseProductDetail=new WarehouseProductDetail();
		                        warehouseProductDetail=formWarehouseProductDetail(warehouseProductDetail,fieldStaffStock,productReturnDetail);
		                        warehouseProductDetail.setPrevStock(stock);
		                        warehouseProductDetail.setTxnStock(Double.valueOf(productReturnDetail.getQuantity()));
		                        warehouseProductDetail.setFinalStock(stock+Double.valueOf(productReturnDetail.getQuantity()));
		                        productDetails.add(warehouseProductDetail);
		                        fieldStaffStock.setWarehouseDetails(productDetails);
						
							productDistributionService.updateWarehouseProduct(fieldStaffStock);
							
						}
						 }else{//if approved not is enabled
							 double stock =fieldStaffStock.getStock();
								//WarehouseProduct fieldStaffStocks = productDistributionService.findFieldStaffAvailableStock(distributionDetail.getAgentId(),distributionDetail.getProductId());
							 fieldStaffStock.setStock(fieldStaffStock.getStock() + finalQty);
								 Set<WarehouseProductDetail>productDetails=new LinkedHashSet<WarehouseProductDetail>();
								  //  List<WarehouseProductDetail> productDetail= productDistributionService.findWarehouseproductDetailByWarehouseproductIdAndReceiptNo(warehouseProduct.getId(),existingDistributionDetail.getDistribution().getReceiptNumber());
								    WarehouseProductDetail warehouseProductDetail=new WarehouseProductDetail();
			                        warehouseProductDetail=formWarehouseProductDetail(warehouseProductDetail,fieldStaffStock,productReturnDetail);
			                        warehouseProductDetail.setPrevStock(stock);
			                        warehouseProductDetail.setTxnStock(Double.valueOf(productReturnDetail.getQuantity()));
			                        warehouseProductDetail.setFinalStock(stock+Double.valueOf(productReturnDetail.getQuantity()));
			                        productDetails.add(warehouseProductDetail);
			                        fieldStaffStock.setWarehouseDetails(productDetails);
							
								productDistributionService.updateWarehouseProduct(fieldStaffStock);
						 }
					}else{//if field staff account is not exists-creating field staff account
						if(approved.equals("1")){
							if (productReturnDetail.getStatus() == 1) {
							fieldStaffStock=new WarehouseProduct();
							Product product =productService.findProductById(Long.valueOf(productReturnDetail.getProductId()));
							double stock=fieldStaffStock.getStock();
							Agent agent=agentService.findAgent(Long.valueOf(fieldStaff.getId()));
							fieldStaffStock.setCostPrice(Double.valueOf(product.getPrice()));
							fieldStaffStock.setProduct(product);
							fieldStaffStock.setAgent(agent);
							fieldStaffStock.setSeasonCode(existingProductReturnDetail.getProductReturn().getSeasonCode());
							fieldStaffStock.setRevisionNo(DateUtil.getRevisionNumber());
							fieldStaffStock.setBranchId(existingProductReturnDetail.getProductReturn().getBranchId());
							fieldStaffStock.setStock(Double.valueOf(productReturnDetail.getQuantity()));
							 Set<WarehouseProductDetail>productDetails=new LinkedHashSet<WarehouseProductDetail>();
							  //  List<WarehouseProductDetail> productDetail= productDistributionService.findWarehouseproductDetailByWarehouseproductIdAndReceiptNo(warehouseProduct.getId(),existingDistributionDetail.getDistribution().getReceiptNumber());
							    WarehouseProductDetail warehouseProductDetail=new WarehouseProductDetail();
		                        warehouseProductDetail=formWarehouseProductDetail(warehouseProductDetail,fieldStaffStock,productReturnDetail);
		                        warehouseProductDetail.setPrevStock(stock);
		                        warehouseProductDetail.setTxnStock(Double.valueOf(productReturnDetail.getQuantity()));
		                        warehouseProductDetail.setFinalStock(stock+Double.valueOf(productReturnDetail.getQuantity()));
		                        warehouseProductDetail.setWarehouseProduct(fieldStaffStock);
		                        productDetails.add(warehouseProductDetail);
		                        
		                        fieldStaffStock.setWarehouseDetails(productDetails);
						
							productDistributionService.save(fieldStaffStock);
							}
						}else{
							fieldStaffStock=new WarehouseProduct();
							Product product =productService.findProductById(Long.valueOf(productReturnDetail.getProductId()));
							double stock=fieldStaffStock.getStock();
							Agent agent=agentService.findAgent(Long.valueOf(fieldStaff.getId()));
							fieldStaffStock.setCostPrice(Double.valueOf(product.getPrice()));
							fieldStaffStock.setProduct(product);
							fieldStaffStock.setAgent(agent);
							fieldStaffStock.setSeasonCode(existingProductReturnDetail.getProductReturn().getSeasonCode());
							fieldStaffStock.setRevisionNo(DateUtil.getRevisionNumber());
							fieldStaffStock.setBranchId(existingProductReturnDetail.getProductReturn().getBranchId());
							fieldStaffStock.setStock(Double.valueOf(productReturnDetail.getQuantity()));
							 Set<WarehouseProductDetail>productDetails=new LinkedHashSet<WarehouseProductDetail>();
							  //  List<WarehouseProductDetail> productDetail= productDistributionService.findWarehouseproductDetailByWarehouseproductIdAndReceiptNo(warehouseProduct.getId(),existingDistributionDetail.getDistribution().getReceiptNumber());
							    WarehouseProductDetail warehouseProductDetail=new WarehouseProductDetail();
		                        warehouseProductDetail=formWarehouseProductDetail(warehouseProductDetail,fieldStaffStock,productReturnDetail);
		                        warehouseProductDetail.setPrevStock(stock);
		                        warehouseProductDetail.setTxnStock(Double.valueOf(productReturnDetail.getQuantity()));
		                        warehouseProductDetail.setFinalStock(stock+Double.valueOf(productReturnDetail.getQuantity()));
		                        warehouseProductDetail.setWarehouseProduct(fieldStaffStock);
		                        productDetails.add(warehouseProductDetail);
		                        
		                        fieldStaffStock.setWarehouseDetails(productDetails);
						
							productDistributionService.save(fieldStaffStock);
						}
					}


 
					// calculation for warehouse stock
					Warehouse warehouse = locationService.findCoOperativeByCode(productReturnDetail.getWarehouseCode());
					if (!ObjectUtil.isEmpty(warehouse)) {
						if(approved.equals("1")){
						if (productReturnDetail.getStatus() == 1) {
								WarehouseProduct warehouseStock = productDistributionService
										.findAvailableStockByWarehouseIdSelectedProductSeasonBatchNo(warehouse.getId(), productReturnDetail.getProductId(),existingProductReturnDetail.getProductReturn().getSeasonCode(),existingProductReturnDetail.getBatchNo());
								if (!ObjectUtil.isEmpty(warehouseStock)) {
									warehouseStock.setStock(Double.valueOf(currentQty) );
									 Set<WarehouseProductDetail>productDetails=new LinkedHashSet<WarehouseProductDetail>();
									  //  List<WarehouseProductDetail> productDetail= productDistributionService.findWarehouseproductDetailByWarehouseproductIdAndReceiptNo(warehouseProduct.getId(),existingDistributionDetail.getDistribution().getReceiptNumber());
									    WarehouseProductDetail warehouseProductDetail=new WarehouseProductDetail();
				                        warehouseProductDetail=formWarehouseProductDetail(warehouseProductDetail,warehouseStock,productReturnDetail);
				                        warehouseProductDetail.setPrevStock(Double.valueOf(productReturnDetail.getAvlQty()));
				                        warehouseProductDetail.setTxnStock(Double.valueOf(productReturnDetail.getQuantity()));
				                        warehouseProductDetail.setFinalStock(Double.valueOf(productReturnDetail.getAvlQty())-Double.valueOf(productReturnDetail.getQuantity()));
				                        productDetails.add(warehouseProductDetail);
				                        warehouseStock.setWarehouseDetails(productDetails);
									
									warehouseStock.setWarehouseDetails(productDetails);
									productDistributionService.updateWarehouseProduct(warehouseStock);
								}
	
							}
						}//for approved is not enabled
						else{
							WarehouseProduct warehouseStock = productDistributionService
									.findAvailableStockByWarehouseIdSelectedProductSeasonBatchNo(warehouse.getId(), productReturnDetail.getProductId(),existingProductReturnDetail.getProductReturn().getSeasonCode(),existingProductReturnDetail.getBatchNo());
							if (!ObjectUtil.isEmpty(warehouseStock)) {
								warehouseStock.setStock(Double.valueOf(currentQty));
								 Set<WarehouseProductDetail>productDetails=new LinkedHashSet<WarehouseProductDetail>();
								  //  List<WarehouseProductDetail> productDetail= productDistributionService.findWarehouseproductDetailByWarehouseproductIdAndReceiptNo(warehouseProduct.getId(),existingDistributionDetail.getDistribution().getReceiptNumber());
								    WarehouseProductDetail warehouseProductDetail=new WarehouseProductDetail();
			                        warehouseProductDetail=formWarehouseProductDetail(warehouseProductDetail,warehouseStock,productReturnDetail);
			                        warehouseProductDetail.setPrevStock(Double.valueOf(productReturnDetail.getAvlQty()));
			                        warehouseProductDetail.setTxnStock(Double.valueOf(productReturnDetail.getQuantity()));
			                        warehouseProductDetail.setFinalStock(Double.valueOf(productReturnDetail.getAvlQty())-Double.valueOf(productReturnDetail.getQuantity()));
			                        productDetails.add(warehouseProductDetail);
			                        warehouseStock.setWarehouseDetails(productDetails);
								
								warehouseStock.setWarehouseDetails(productDetails);
								productDistributionService.updateWarehouseProduct(warehouseStock);
							}
						}
					}
				}
			}
			command = UPDATE;
			request.setAttribute(HEADING, getText(LIST));
			return LIST;
		}

	}
	private WarehouseProductDetail formWarehouseProductDetail(WarehouseProductDetail warehouseProductDetail,
			WarehouseProduct warehouseProduct, ProductReturnDetail detail) {
		WarehouseProductDetail productDetail=new WarehouseProductDetail();
		// TODO Auto-generated method stub
		productDetail.setReceiptNo(detail.getReceiptNo());
		Calendar currentDate = Calendar.getInstance();
		//DateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT);
		DateFormat df = new SimpleDateFormat(getGeneralDateFormat());
		startDate = df.format(currentDate.getTime());
		productDetail.setDate(DateUtil.convertStringToDate(startDate, getGeneralDateFormat()));
		productDetail.setDesc(ProductReturn.PRODUCT_RETURN_FROM_FIELDSTAFF_DESCRIPTION);
		productDetail.setCostPrice(warehouseProduct.getCostPrice());
		/*productDetail.setPrevStock(warehouseProduct.getStock());
		productDetail.setTxnStock(Double.valueOf(detail.getQuantity()));
		productDetail.setFinalStock((warehouseProduct.getStock()-Double.valueOf(detail.getQuantity())));*/
		productDetail.setWarehouseProduct(warehouseProduct);
		return productDetail;
	}
	private Set<ProductReturnDetail> formProductReturnDetail() {
		Set<ProductReturnDetail> productReturnDetailSet = new LinkedHashSet<>();
		if (!StringUtil.isEmpty(productReturnDetails)) {
			Type productReturnDetailType = new TypeToken<List<ProductReturnDetail>>() {
			}.getType();
			List<ProductReturnDetail>productReturnList = new Gson().fromJson(productReturnDetails,
					productReturnDetailType);

			productReturnDetailSet.addAll(productReturnList);
		}

		return productReturnDetailSet;
	}

    public Object getData() {
        ESESystem preferences = preferncesService.findPrefernceById("1");
        setHarvestSeasonEnabled(preferences.getPreferences().get(ESESystem.ENABLE_HARVEST_SEASON_INFO));
    	setSeasonName(getCurrentSeasonsCode()+"-"+getCurrentSeasonsName());
    	 return productReturn;
    }
    
    private void reset() {

        AgroTimerTask productReturnTask = (AgroTimerTask) request.getSession().getAttribute(
                agentId + "_timer");
        setReStartTxn(true);
        productReturnTask.cancelTimer(false);
        request.setAttribute("agentId", agentId);
        request.getSession().removeAttribute(
                agentId + "_" + ProductReturn.PRODUCT_RETURN_FROM_FIELDSTAFF + "_"
                        + WebTransactionAction.IS_FORM_RESUBMIT);
        setCurrentSeason();
    }

    /**
     * Gets the agro transaction.
     * @param agent the agent
     * @param profileType the profile type
     * @return the agro transaction
     */
    private AgroTransaction getAgroTransaction(Agent agent, String profileType) {

		Calendar currentDate = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(getGeneralDateFormat());
		startDate = df.format(currentDate.getTime());
		AgroTransaction agroTransaction = new AgroTransaction();
		agroTransaction.setTxnTime(DateUtil.convertStringToDate(startDate, getGeneralDateFormat()));

		if (StringUtil.isEmpty(productReturnDetail)) {
			agroTransaction.setAgentId(StringUtil.isEmpty(agent.getProfileId()) ? null : agent.getProfileId());
			agroTransaction.setAgentName(
					ObjectUtil.isEmpty(agent.getPersonalInfo()) ? null : agent.getPersonalInfo().getName());
			Warehouse cooperative=null;
			if(!getCurrentTenantId().equalsIgnoreCase(ESESystem.PRATIBHA_TENANT_ID)){
			String[] warehouseArray = selectedWarehouse.split("-");
			cooperative = locationService.findCoOperativeByCode(warehouseArray[1].trim());
			}
			else{
				cooperative = locationService.findCoOperativeByCode(selectedWarehouse.trim());
			}
			if (cooperative!=null && !ObjectUtil.isEmpty(cooperative)) {
				agroTransaction.setServicePointId(cooperative.getCode());
				agroTransaction.setServicePointName(cooperative.getName());
			} else {
				agroTransaction.setServicePointId(NOT_APPLICABLE);
				agroTransaction.setServicePointName(NOT_APPLICABLE);
			}
		} else {
			agroTransaction.setAgentId(StringUtil.isEmpty(newProductReturnDetails.iterator().next().getAgentId()) ? null
					: newProductReturnDetails.iterator().next().getAgentId());
			agroTransaction.setAgentName(StringUtil.isEmpty(newProductReturnDetails.iterator().next().getAgentName())
					? null : newProductReturnDetails.iterator().next().getAgentName());
			if (!ObjectUtil.isEmpty(newProductReturnDetails.iterator().next().getWarehouseCode())) {
				agroTransaction.setServicePointId(newProductReturnDetails.iterator().next().getWarehouseCode());
				agroTransaction.setServicePointName(newProductReturnDetails.iterator().next().getWarehouseCode());
			} else {
				agroTransaction.setServicePointId(NOT_APPLICABLE);
				agroTransaction.setServicePointName(NOT_APPLICABLE);
			}
		}
		agroTransaction.setDeviceId(NOT_APPLICABLE);
		agroTransaction.setDeviceName(NOT_APPLICABLE);
		agroTransaction.setOperType(ESETxn.ON_LINE);
		agroTransaction.setProfType(profileType);
		agroTransaction.setTxnType(ProductReturn.PRODUCT_RETURN_FROM_FIELDSTAFF);
		agroTransaction.setTxnDesc(ProductReturn.PRODUCT_RETURN_FROM_FIELDSTAFF_DESCRIPTION);
		return agroTransaction;
	}

    /**
     * Load available stock.
     * @throws Exception the exception
     */
    public void loadAvailableStock() throws Exception {
  	Double avbleStock = 0.0;
  	Warehouse warehouse=null;
        String result = NOT_APPLICABLE;
        if (!StringUtil.isEmpty(selectedWarehouse)) {
            if(!getCurrentTenantId().equalsIgnoreCase(ESESystem.PRATIBHA_TENANT_ID)){
        	String[] warehouseArr = selectedWarehouse.split("-");
             warehouse = locationService.findCoOperativeByCode(warehouseArr[1].trim());
            }
            else{
            	warehouse = locationService.findCoOperativeByCode(selectedWarehouse.trim());
            }
            Product product = productService.findProductByCode(selectedProduct);
            if(!StringUtil.isEmpty(selectedAgent)){
	            Agent agent = agentService.findAgent(Long.valueOf(selectedAgent));
	           
	            if (!ObjectUtil.isEmpty(warehouse) && !ObjectUtil.isEmpty(product) && agent!=null) {
	               
	            	avbleStock =productDistributionService.findAvailableStockByAgentIdProductIdSeasonBatchNo(
	    					agent.getProfileId(), product.getId(), selectedSeason, batchNo);
	                /*WarehouseProduct warehouseProduct = productDistributionService.findAvailableStock(
	                        warehouse.getId(), product.getId());*/
	            } 
         
            if (!ObjectUtil.isEmpty(avbleStock)) {
                	stock = avbleStock;
        			units = product.getUnit();
        			productPrice = product.getPrice();
      			result = stock + "," + productPrice + "," + units;        			
                    
                } else {
                    stock = 0.00;
                    productPrice = "0";
                    units = NOT_APPLICABLE;
                }
                result = stock + "," + productPrice + "," + units;
           

        }   } 
        if(getCurrentTenantId().equalsIgnoreCase(ESESystem.PRATIBHA_TENANT_ID)){
        	if(!StringUtil.isEmpty(selectedAgent) && !StringUtil.isEmpty(selectedProduct)){
        		Product product = productService.findProductByCode(selectedProduct);
        		 Agent agent = agentService.findAgent(Long.valueOf(selectedAgent));
        		 if(product!=null && !ObjectUtil.isEmpty(product) && agent!=null && !ObjectUtil.isEmpty(agent) && batchNo!=null && selectedSeason!=null){
        			 avbleStock =productDistributionService.findAvailableStockByAgentIdProductIdSeasonBatchNo(
 	    					agent.getProfileId(), product.getId(), selectedSeason, batchNo);
        		 }
        		
        	}
        }
        response.getWriter().print(result);
    }
    
    public void loadAvailableStockLalteer() throws Exception {

        String result = NOT_APPLICABLE;
      if (!StringUtil.isEmpty(selectedWarehouse)) {
            String[] warehouseArr = selectedWarehouse.split("-");
            Warehouse warehouse = locationService.findCoOperativeByCode(warehouseArr[1].trim());
             Product product = productService.findProductById(Long.valueOf(selectedProduct));
            if (!ObjectUtil.isEmpty(warehouse) && !StringUtil.isEmpty(selectedProduct)) {
                Double avbStock=productDistributionService.findAvailableStockByWarehouseIdProductIdBatchNum(Long.valueOf(warehouse.getId()),Long.valueOf(product.getId()),batchNo);
              WarehouseProduct warehouseProduct=productDistributionService.findAvailableStockByWarehouseIdSelectedProductBatchNo(warehouse.getId(),Long.valueOf(selectedProduct),batchNo);
                /*WarehouseProduct warehouseProduct = productDistributionService.findAvailableStock(
                        warehouse.getId(), product.getId());*/
                if (!ObjectUtil.isEmpty(warehouseProduct)) {
                    stock = Double.valueOf(avbStock);
                    units = product.getUnit();
                    productPrice = String.valueOf(warehouseProduct.getCostPrice());
                    result = stock + "," + productPrice + "," + units;
                } else {
                    stock = 0.00;
                    productPrice = "0";
                    units = NOT_APPLICABLE;
                }
                result = stock + "," + productPrice + "," + units;
            }

        }
        response.getWriter().print(result);
    }
    
    
    public void populateBatchNo(){
    	 String[] warehouseArr = selectedWarehouse.split("-");
         Warehouse warehouse = locationService.findCoOperativeByCode(warehouseArr[1].trim());
         Product product = productService.findProductByCode(selectedProduct);
         WarehouseProduct warehouseProduct=productDistributionService.findAvailableStockByWarehouseIdSelectedProductSeason(warehouse.getId(),product.getId(),selectedSeason);
         if (!ObjectUtil.isEmpty(warehouse) && !ObjectUtil.isEmpty(product)) {
        	 if(Math.abs(warehouseProduct.getStock())>0.0){
        			List<Object[]>batchNoList=productDistributionService.findBatchNoListByWarehouseIdProductIdSeason(warehouse.getId(),product.getId(),selectedSeason);
        		       
                	JSONArray jsonArray=new JSONArray();
                	 if (!ObjectUtil.isListEmpty(batchNoList)) {
                         for (Object[] productObj : batchNoList) {
                        	 if(!productObj[1].toString().equalsIgnoreCase("NA"))
                        	 jsonArray.add(getJSONObject(productObj[1].toString(), productObj[1].toString()));
                         }
                     }
                     sendAjaxResponse(jsonArray);
        	 }
    
        }
         
    }

    /**
     * Gets the product list.
     * @return the product list
     */
    public Map<String, String> getProductList() {

        Map<String, String> returnMap = new LinkedHashMap<String, String>();
        if (!StringUtil.isEmpty(agentId)) {
            List<Object[]> productList = productService
                    .listProductNameUnitByCooperativeManagerProfileId(agentId);
            if (!ObjectUtil.isListEmpty(productList)) {
                for (Object[] productObj : productList) {
                    returnMap.put(productObj[0] + "=" + productObj[1].toString(), productObj[0]
                            .toString());
                }
            }
        }
        return returnMap;
    }

    /**
     * Form error.
     * @param error the error
     * @return the string
     */
    public String formError(String error) {

        addActionError(getText(error));
        request.setAttribute(HEADING, getText(INPUT));
        return INPUT;
    }

    /**
     * Populate farmer account.
     * @throws Exception the exception
     */
    public void populateProductReturn() throws Exception {
      
        String result = "";
        WarehouseProduct warehouseProduct =  new WarehouseProduct();
        if (!StringUtil.isEmpty(selectedAgent)) {
            agent = agentService.findAgent(Long.parseLong(selectedAgent));
            if (ObjectUtil.isEmpty(agent)) {
                result = "invalid.agent";
            } else if (agent.getStatus() == Profile.INACTIVE) {
                result = "agent.inactive";
            } else {
                ESEAccount agentAccount = accountService.findAccountByProfileIdAndProfileType(
                        agent.getProfileId(), ESEAccount.AGENT_ACCOUNT);
                if (ObjectUtil.isEmpty(agentAccount))
                    result = "account.unavailable";
                else if (ESEAccount.ACTIVE != agentAccount.getStatus())
                    result = "account.inactive";
            }
        }

        if (StringUtil.isEmpty(result)) {
            if (!StringUtil.isEmpty(selectedAgent) && !selectedWarehouse.isEmpty()) {
                agent = agentService.findAgent(Long.parseLong(selectedAgent));
                if (!StringUtil.isEmpty(productTotalString) && !ObjectUtil.isEmpty(agent)) {
                    String[] productTotalArray = productTotalString.trim().split("\\|\\|");
                    for (int i = 0; i < productTotalArray.length; i++) {
                        String[] productDetail = productTotalArray[i].split("##");
                        double quantity = Double.valueOf(productDetail[1]);
                        String[] productInfo = productDetail[0].split("=");
                        if(!getCurrentTenantId().equalsIgnoreCase("lalteer")){
                             product = productService.findProductByCode(productInfo[0]);
                       }else{
                            product = productService.findProductById(Long.valueOf(productInfo[0]));
                       }
                        Double avbleStock = 0.0;
                        String[] warehouseArr =null;
                        Warehouse warehouse;
                        if(!getCurrentTenantId().equalsIgnoreCase(ESESystem.PRATIBHA_TENANT_ID)){
                        warehouseArr=selectedWarehouse.split("-");
                        warehouse = locationService.findCoOperativeByCode(warehouseArr[1].trim());
                        }
                        else{
                        	warehouse = locationService.findCoOperativeByCode(selectedWarehouse.trim());
                        }
                        
                        if (!ObjectUtil.isEmpty(warehouse) && !ObjectUtil.isEmpty(product)) {
                           /* warehouseProduct = productDistributionService
                                    .findAvailableStock(warehouse.getId(), product.getId());*/
                            if(getCurrentTenantId().equalsIgnoreCase("lalteer")){
                               // warehouseProduct=productDistributionService.findAvailableStockByWarehouseIdSelectedProductBatchNo(warehouse.getId(),product.getId(),productDetail[3]);
                            	avbleStock=productDistributionService.findAvailableStockByWarehouseIdProductIdBatchNum(Long.valueOf(warehouse.getId()),Long.valueOf(product.getId()),batchNo);
                            }else{
                        	//warehouseProduct=productDistributionService.findAvailableStockByWarehouseIdSelectedProductSeasonBatchNo(warehouse.getId(),product.getId(),selectedSeason,productDetail[3]);
                            	avbleStock =productDistributionService.findAvailableStockByAgentIdProductIdSeasonBatchNo(
              	    					agent.getProfileId(), product.getId(), selectedSeason, batchNo);
                            } /*if (avbleStock < quantity) {
                                result = getText("insufficientstockfor") + " " + product.getName()
                                        + " " + product.getUnit();
                                break;
                            }*/
                        }

                    }
                }

                if (StringUtil.isEmpty(result)) {
                    // Forming FieldStaff Manager Distribution Object
                    ProductReturn comProductReturn = new ProductReturn();
                    comProductReturn.setAgroTransaction(getAgroTransaction(agent,
                            Profile.CO_OPEARATIVE_MANAGER));
                    // Forming Co-Operative Manager Agro Transaction Object
                    AgroTransaction fsAgroTransaction = getAgroTransaction(agent, Profile.AGENT);

                    // PROCT DETAILS FOR DISTRIBUTION_DETAIL
                    Set<ProductReturnDetail> productReturnDetailsSet = new HashSet<ProductReturnDetail>();
                    ProductReturnDetail productReturnDetail = new ProductReturnDetail();

                    if (productReturnProductList != null && productReturnProductList.length() > 0) {
                        String[] products = productReturnProductList.split(",");
                        for (int i = 0; i < products.length; i++) {
                            String[] product = products[i].split("\\|");

                        }
                    }
                    if (!StringUtil.isEmpty(productTotalString)) {
                        String[] productTotalArray = productTotalString.trim().split("\\|\\|");
                        Double avbleStock = 0.0;
                        for (int j = 0; j < productTotalArray.length; j++) {
                            String[] productDetail = productTotalArray[j].split("##");
                            String[] productInfo = productDetail[0].split("=");
                            Warehouse warehouse=null;
                            if(!getCurrentTenantId().equalsIgnoreCase(ESESystem.PRATIBHA_TENANT_ID)){
                            String[] warehouseArr = selectedWarehouse.split("-");
                            warehouse = locationService.findCoOperativeByCode(warehouseArr[1].trim());
                            }
                            else{
                            	warehouse = locationService.findCoOperativeByCode(selectedWarehouse.trim());
                            }
                            if(!getCurrentTenantId().equalsIgnoreCase("lalteer")){
                              product = productService.findProductByCode(productInfo[0]);
                              avbleStock =productDistributionService.findAvailableStockByAgentIdProductIdSeasonBatchNo(
          	    					agent.getProfileId(), product.getId(), selectedSeason, batchNo);
                             
                              //warehouseProduct=productDistributionService.findAvailableStockByWarehouseIdSelectedProductSeasonBatchNo(warehouse.getId(),product.getId(),selectedSeason,productDetail[3]);
                        }else{
                             product = productService.findProductById(Long.valueOf(productInfo[0]));                                                  
                             avbleStock=productDistributionService.findAvailableStockByWarehouseIdProductIdBatchNum(Long.valueOf(warehouse.getId()),Long.valueOf(product.getId()),batchNo);
                             // warehouseProduct=productDistributionService.findAvailableStockByWarehouseIdSelectedProductBatchNo(warehouse.getId(),product.getId(),productDetail[3]);  
                        }   
                            Product productDetails = productService.findProductByCode(productInfo[0]);
                            double quantity = Double.valueOf(productDetail[1]);
                            // double pricePerUnit = Double.valueOf(productDetail[2]);
                            // double subTotal = Double.valueOf(productDetail[2]);
                            productReturnDetail = new ProductReturnDetail();
                           
                            productReturnDetail.setBatchNo(productDetail[3]);
                            productReturnDetail.setCostPriceArray(costPriceArrayValue);
                            productReturnDetail.setProduct(product);
                            try {
                                productReturnDetail.setQuantity(quantity);
                                //productReturnDetail.setUnit(productInfo[1].trim());
                                productReturnDetail.setUnit(product.getUnit());
                                 //distributionDetail.setPricePerUnit(Double.valueOf(costPriceArrayValue));
                                 productReturnDetail.setExistingQuantity(String.valueOf(avbleStock));
                                 productReturnDetail.setCurrentQuantity(String.valueOf(avbleStock - quantity));
                                // distributionDetail.setSubTotal(subTotal);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            productReturnDetailsSet.add(productReturnDetail);

                        }
                    }
                    // CREATING RECEIPT NUMBER
                    String receiptnumberSeq = idGenerator.getDistributionToFieldStaffSeq();
                    receiptNumber = receiptnumberSeq;
                    HarvestSeason harvestSeason = productDistributionService.findHarvestSeasonBySeasonCode(selectedSeason);
                    comProductReturn.setHarvestSeason(harvestSeason);
                    comProductReturn.getAgroTransaction().setReceiptNo(receiptnumberSeq);
                    //comProductReturn.getAgroTransaction().setTxnTime(DateUtil.convertStringToDate(startDate, getGeneralDateFormat().concat(" HH:mm:ss")));
                    comProductReturn.getAgroTransaction().setTxnTime(DateUtil.convertStringToDate(selectedDate, getGeneralDateFormat()));
                    //comProductReturn.setSeason(productDistributionService.findCurrentSeason());
                   
                    comProductReturn.setProductReturnDetail(productReturnDetailsSet);
                    comProductReturn.getAgroTransaction().setRefAgroTransaction(fsAgroTransaction);
                    comProductReturn.getAgroTransaction().getRefAgroTransaction().setReceiptNo(
                            receiptnumberSeq);
                    comProductReturn.setTxnType(fsAgroTransaction.getTxnType());
                    
                    
                    comProductReturn.setReceiptNumber(fsAgroTransaction.getReceiptNo());
                    comProductReturn.setServicePointId(fsAgroTransaction.getServicePointId());
                    comProductReturn.setServicePointName(fsAgroTransaction.getServicePointName());
                    comProductReturn.setAgentId(fsAgroTransaction.getAgentId());
                    comProductReturn.setAgentName(fsAgroTransaction.getAgentName());
                    comProductReturn.setTxnTime(DateUtil.convertStringToDate(selectedDate, getGeneralDateFormat()));
                    comProductReturn.setBranchId(getBranchId());
                    comProductReturn.setTenantId(getCurrentTenantId());
                    if(harvestSeasonEnabled.equals("1")){
                        
                        comProductReturn.setSeasonCode(comProductReturn.getHarvestSeason().getCode());
                    }else{
                        comProductReturn.setSeasonCode(getCurrentSeasonsCode());
                    }
                   
                    productReturnDetail.setProductReturn(comProductReturn);
                 
                  productDistributionService
                            .saveProductReturnAndProductReturnDetail(comProductReturn);
                }

            }
            
            else {
            	if(getCurrentTenantId().equals(ESESystem.PRATIBHA_TENANT_ID)){

                    agent = agentService.findAgent(Long.parseLong(selectedAgent));
                    if (!StringUtil.isEmpty(productTotalString) && !ObjectUtil.isEmpty(agent)) {
                        String[] productTotalArray = productTotalString.trim().split("\\|\\|");
                        for (int i = 0; i < productTotalArray.length; i++) {
                            String[] productDetail = productTotalArray[i].split("##");
                            double quantity = Double.valueOf(productDetail[1]);
                            String[] productInfo = productDetail[0].split("=");
                            
                                 product = productService.findProductByCode(productInfo[0]);
                           
                            Double avbleStock = 0.0;
                            if (!ObjectUtil.isEmpty(agent.getProcurementCenter()) && !ObjectUtil.isEmpty(product)) {
                                	avbleStock =productDistributionService.findAvailableStockByAgentIdProductIdSeasonBatchNo(
                  	    					agent.getProfileId(), product.getId(), selectedSeason, batchNo);
                            }
                        }
                    }

                    if (StringUtil.isEmpty(result)) {
                        ProductReturn comProductReturn = new ProductReturn();
                        comProductReturn.setAgroTransaction(getAgroTransaction(agent,
                                Profile.CO_OPEARATIVE_MANAGER));
                        AgroTransaction fsAgroTransaction = getAgroTransaction(agent, Profile.AGENT);
                        Set<ProductReturnDetail> productReturnDetailsSet = new HashSet<ProductReturnDetail>();
                        ProductReturnDetail productReturnDetail = new ProductReturnDetail();

                        if (productReturnProductList != null && productReturnProductList.length() > 0) {
                            String[] products = productReturnProductList.split(",");
                            for (int i = 0; i < products.length; i++) {
                                String[] product = products[i].split("\\|");

                            }
                        }
                        if (!StringUtil.isEmpty(productTotalString)) {
                            String[] productTotalArray = productTotalString.trim().split("\\|\\|");
                            Double avbleStock = 0.0;
                            for (int j = 0; j < productTotalArray.length; j++) {
                                String[] productDetail = productTotalArray[j].split("##");
                                String[] productInfo = productDetail[0].split("=");
                                  product = productService.findProductByCode(productInfo[0]);
                                  avbleStock =productDistributionService.findAvailableStockByAgentIdProductIdSeasonBatchNo(
              	    					agent.getProfileId(), product.getId(), selectedSeason, batchNo);
                                 
                             
                                Product productDetails = productService.findProductByCode(productInfo[0]);
                                double quantity = Double.valueOf(productDetail[1]);
                                productReturnDetail = new ProductReturnDetail();
                               
                                productReturnDetail.setBatchNo(productDetail[3]);
                                productReturnDetail.setCostPriceArray(costPriceArrayValue);
                                productReturnDetail.setProduct(product);
                                try {
                                    productReturnDetail.setQuantity(quantity);
                                    productReturnDetail.setUnit(product.getUnit());
                                     productReturnDetail.setExistingQuantity(String.valueOf(avbleStock));
                                     productReturnDetail.setCurrentQuantity(String.valueOf(avbleStock - quantity));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                productReturnDetailsSet.add(productReturnDetail);

                            }
                        }
                        String receiptnumberSeq = idGenerator.getDistributionToFieldStaffSeq();
                        receiptNumber = receiptnumberSeq;
                        HarvestSeason harvestSeason = productDistributionService.findHarvestSeasonBySeasonCode(selectedSeason);
                        comProductReturn.setHarvestSeason(harvestSeason);
                        comProductReturn.getAgroTransaction().setReceiptNo(receiptnumberSeq);
                        comProductReturn.getAgroTransaction().setTxnTime(DateUtil.convertStringToDate(selectedDate, getGeneralDateFormat()));
                        comProductReturn.setProductReturnDetail(productReturnDetailsSet);
                        comProductReturn.getAgroTransaction().setRefAgroTransaction(fsAgroTransaction);
                        comProductReturn.getAgroTransaction().getRefAgroTransaction().setReceiptNo(
                                receiptnumberSeq);
                        comProductReturn.setTxnType(fsAgroTransaction.getTxnType());
                        comProductReturn.setReceiptNumber(fsAgroTransaction.getReceiptNo());
                        comProductReturn.setServicePointId(fsAgroTransaction.getServicePointId());
                        comProductReturn.setServicePointName(fsAgroTransaction.getServicePointName());
                        comProductReturn.setAgentId(fsAgroTransaction.getAgentId());
                        comProductReturn.setAgentName(fsAgroTransaction.getAgentName());
                        comProductReturn.setTxnTime(DateUtil.convertStringToDate(selectedDate, getGeneralDateFormat()));
                        comProductReturn.setBranchId(getBranchId());
                        comProductReturn.setTenantId(getCurrentTenantId());
                        if(harvestSeasonEnabled.equals("1")){
                            
                            comProductReturn.setSeasonCode(comProductReturn.getHarvestSeason().getCode());
                        }else{
                            comProductReturn.setSeasonCode(getCurrentSeasonsCode());
                        }
                       
                        productReturnDetail.setProductReturn(comProductReturn);
                     
                      productDistributionService
                                .saveProductReturnAndProductReturnDetail(comProductReturn);
                    }

                
            	}
                result = getText(result);
            }
            if (StringUtil.isEmpty(result)) {
                 /* String receiptHtml = "<a href=\"javascript:printReceipt(\\'" + receiptNumber +
                  "\\')\" onclick='printReceipt(\"" + receiptNumber + "\")'>" +
                  getText("printReceipt") + "</button>";
                 
                setProductReturnDescription(getText("productreturnSucess") + "</br>"
                        + getText("receiptNumber") + " : " + receiptNumber+ "</br>" + receiptHtml);
                */
                String receiptHtml = "<a href=\"javascript:printReceipt(\\'" + receiptNumber + 
  	                  "\\')\" class ='btn btn-default btnBorderRadius' onclick='printReceipt(\"" + receiptNumber + "\")'>" +
  	                  getText("printReceipt") + "</button>";
  	                 
                setProductReturnDescription("<h5>"
  	                        + getText("receiptNumber") + " : " + receiptNumber+ "</h5>" + receiptHtml + "</br>");


                printAjaxResponse(getProductReturnDescription(), "text/html");
            } else {
                setProductReturnDescription(getText(result));
                printAjaxResponse(getProductReturnDescription(), "text/html");
            }
             response.getWriter().print(result);
        }

        else {
            setProductReturnDescription(getText(result));
            printAjaxResponse(getProductReturnDescription(), "text/html");
        }
    }

    /**
     * Populate product.
     * @return the string
     * @throws Exception the exception
     */
    public void populateProduct() throws Exception {

        if (!StringUtil.isEmpty(selectedWarehouse)) {
            String[] warehouse = selectedWarehouse.split("-");
            JSONArray productArr = new JSONArray();
            List<Object[]> productList = productService.listProductNameUnitByWarehouse(warehouse[1]
                    .trim());
            if (!ObjectUtil.isListEmpty(productList)) {
                for (Object[] productObj : productList) {
                	productArr.add(getJSONObject(productObj[0].toString(), productObj[1].toString()));
                }
            }
            sendAjaxResponse(productArr);
        }
       

    }
    
    
    @SuppressWarnings("unchecked")
	protected JSONObject getJSONObject(Object id, Object name) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("name", name);
		return jsonObject;
	}

    public void populateSubCategory() throws Exception {

        if (!StringUtil.isEmpty(selectedWarehouse) && !StringUtil.isEmpty(seasonCode)) {
        	List<SubCategory> categoryList=new ArrayList<>();
            if(!getCurrentTenantId().equalsIgnoreCase(ESESystem.PRATIBHA_TENANT_ID)){
        	String[] warehouseDetail = selectedWarehouse.split("-");
           categoryList=productService.lisCategoryByWarehouseIdSeasonCode(warehouseDetail[1].trim(),seasonCode);
        }
            else{
                categoryList=productService.lisCategoryByWarehouseIdSeasonCodeAgent(Long.parseLong(selectedWarehouse),seasonCode);
            }
            /*List<SubCategory> categoryList = productService
                    .findSubCategorybyWarehouseId(warehouseDetail[1].trim());*/
            JSONArray categoryArr = new JSONArray();
            for (SubCategory subCategory : categoryList) {
            	categoryArr.add(getJSONObject(subCategory.getCode(), subCategory.getName()));
            }
            
            sendAjaxResponse(categoryArr);
        }


    }
    
    public void populateLalteerSubCategory() throws Exception {

        if (!StringUtil.isEmpty(selectedWarehouse)) {
            String[] warehouseDetail = selectedWarehouse.split("-");
            List<SubCategory> categoryList=productService.lisCategoryByWarehouseId(warehouseDetail[1].trim());
            /*List<SubCategory> categoryList = productService
                    .findSubCategorybyWarehouseId(warehouseDetail[1].trim());*/
            JSONArray categoryArr = new JSONArray();
            for (SubCategory subCategory : categoryList) {
                categoryArr.add(getJSONObject(subCategory.getCode(), subCategory.getName()));
            }
            
            sendAjaxResponse(categoryArr);
        }


    }
    public void populateProductBasedOnCategory() throws Exception {
    	Warehouse warehouse=null;
    	WarehouseProduct warehouseProduct = null;
    	if(selectedWarehouse!=null && !StringUtil.isEmpty(selectedWarehouse)){
       if(!getCurrentTenantId().equalsIgnoreCase(ESESystem.PRATIBHA_TENANT_ID)){
    	   String[] warehouseCode = selectedWarehouse.split("-");
    	   warehouse = productService.findWarehouseByCode(warehouseCode[1].trim());
       }
       else{
    	   warehouse = productService.findWarehouseByCode(selectedWarehouse.trim()); 
       }
    	}
		if (!StringUtil.isEmpty(selectedCategory) && warehouse!=null && !ObjectUtil.isEmpty(warehouse) ) {
			// String[] product = selectedCategory.split("-");
			//List<Product> productList = productService.findProductBySubCategoryCode(selectedCategory.trim());
			if (!StringUtil.isEmpty(selectedAgent)&& !StringUtil.isEmpty(seasonCode)){
			
			List<Product> productList=new ArrayList<>();
			
			productList=productService.listofProductBySubCategoryAndSelectedAgent(selectedCategory,Long.parseLong(selectedAgent),seasonCode);
			
			 JSONArray productArr = new JSONArray();
			if (!ObjectUtil.isListEmpty(productList)) {
				for (Product productObj : productList) {
					warehouseProduct = productDistributionService.findAvailableStock(warehouse.getId(),
							productObj.getId());
					if (!ObjectUtil.isEmpty(warehouseProduct)) {
						productArr.add(getJSONObject(productObj.getCode(), productObj.getName()));
						
					}
				}
			}
			  sendAjaxResponse(productArr);
		}
    }
       

    }
    public void populateProductBasedOnCategoryLalteer() throws Exception {

        
        String[] warehouseCode = selectedWarehouse.split("-");
        WarehouseProduct warehouseProduct = null;

        Warehouse warehouse = productService.findWarehouseByCode(warehouseCode[1].trim());

        if (!StringUtil.isEmpty(selectedCategory)) {
            // String[] product = selectedCategory.split("-");
            List<Product> productList = productService.findProductBySubCategoryCode(selectedCategory.trim());
             JSONArray productArr = new JSONArray();
            if (!ObjectUtil.isListEmpty(productList)) {
                for (Product productObj : productList) {
                    warehouseProduct = productDistributionService.findAvailableStock(warehouse.getId(),
                            productObj.getId());
                    if (!ObjectUtil.isEmpty(warehouseProduct)) {
                        productArr.add(getJSONObject(productObj.getId(), productObj.getName()));
                        
                    }
                }
            }
              sendAjaxResponse(productArr);
        }
       

    }

    /**
     * Populate group.
     * @return the string
     * @throws Exception the exception
     */
    public String populateGroup() throws Exception {

        String result = "";
        StringBuffer sb = new StringBuffer();
        if (!StringUtil.isEmpty(selectedAgent)) {
            Agent agent = agentService.findAgent(Long.parseLong(selectedAgent));
            for (Warehouse groupObj : agent.getWareHouses()) {
                if (!StringUtil.isEmpty(groupObj)) {
                    sb.append(groupObj.getName()).append(",");
                }
            }

            result = sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "NA";
        }
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            out = response.getWriter();
            out.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String populateAgentWarehouse() throws Exception {
    	 String result = "";
         StringBuffer sb = new StringBuffer();
         if (!StringUtil.isEmpty(selectedAgent)) {
             Agent agent = agentService.findAgent(Long.parseLong(selectedAgent));
/*             for (Warehouse groupObj : agent.getWareHouses()) {
                 if (!StringUtil.isEmpty(groupObj)) {
                     sb.append(groupObj.getName()).append(",");
                 }
             }
*/
             sb.append(agent.getProcurementCenter().getCode()+"~"+agent.getProcurementCenter().getName());
             result = sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "NA";
         }
         PrintWriter out = null;
         try {
             response.setCharacterEncoding("UTF-8");
             response.setContentType("text/html");
             out = response.getWriter();
             out.print(result);
         } catch (IOException e) {
             e.printStackTrace();
         }
         return null;
    }
    public String populateUnit() throws Exception {

        String result = "";
        StringBuffer sb = new StringBuffer();
        if (!StringUtil.isEmpty(selectedCategory)) {
            SubCategory subCategory = categoryService.findSubCategoryByCode(selectedCategory);
            Product product = productDistributionService.findProductBySubCategoryId(subCategory.getId());
            //List<Product> product = productDistributionService.findProductBySubCategoryId(subCategory.);
            
           // Agent agent = agentService.findAgent(Long.parseLong(selectedAgent));
            //for (Product productObj : product) {
                if (!ObjectUtil.isEmpty(product)) {
                    sb.append(product.getUnit());
                }
          //  }

            result = sb.length() > 0 ? sb.substring(0, sb.length() - 0) : "";
        }
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            out = response.getWriter();
            out.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String populateUnitLalteer() throws Exception {

        String result = "";
        StringBuffer sb = new StringBuffer();
        if (!StringUtil.isEmpty(selectedCategory)) {
            SubCategory subCategory = categoryService.findSubCategoryByCode(selectedCategory);
            Product product = productDistributionService.findProductBySubCategoryId(subCategory.getId());
          
            //List<Product> product = productDistributionService.findProductBySubCategoryId(subCategory.);
            
           // Agent agent = agentService.findAgent(Long.parseLong(selectedAgent));
            //for (Product productObj : product) {
                if (!ObjectUtil.isEmpty(product)) {
                    sb.append(product.getUnit());
                }
          //  }

            result = sb.length() > 0 ? sb.substring(0, sb.length() - 0) : "";
        }
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            out = response.getWriter();
            out.print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Populate agent.
     * @return the string
     * @throws Exception the exception
     */
    public String populateAgent() throws Exception {

        Map<String, String> returnMap = new LinkedHashMap<String, String>();
        if (!StringUtil.isEmpty(selectedWarehouse)) {
            String[] warehouseDetail = selectedWarehouse.split("-");
            List<Agent> agentList = agentService.listOfAgentByArea(warehouseDetail[1].trim());

            for (Agent agent : agentList) {
                returnMap.put(agent.getProfileId(), (agent.getPersonalInfo().getFirstName() + " "
                        + agent.getPersonalInfo().getLastName() + "-" + agent.getProfileId()));
            }
        }
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            out = response.getWriter();
            out.print(returnMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Sets the current season.
     */
    private void setCurrentSeason() {

        ESESystem preference = preferncesService.findPrefernceById("2");
        String currentSeasonCode = preference.getPreferences().get(ESESystem.CURRENT_SEASON_CODE);
        if (!StringUtil.isEmpty(currentSeasonCode)) {
            Season currentSeason = productDistributionService
                    .findSeasonBySeasonCode(currentSeasonCode);
            setSelectedSeason(String.valueOf(currentSeason.getId()));
        }

    }

    /**
     * Sets the product distribution service.
     * @param productDistributionService the new product distribution service
     */
    public void setProductDistributionService(IProductDistributionService productDistributionService) {

        this.productDistributionService = productDistributionService;
    }

    /**
     * Gets the products list.
     * @return the products list
     */
    public List<Object[]> getProductsList() {

        return productsList;
    }

    /**
     * Sets the products list.
     * @param productsList the new products list
     */
    public void setProductsList(List<Object[]> productsList) {

        this.productsList = productsList;
    }

    /**
     * Sets the selected category.
     * @param selectedCategory the new selected category
     */
    public void setSelectedCategory(String selectedCategory) {

        this.selectedCategory = selectedCategory;
    }

    /**
     * Gets the selected category.
     * @return the selected category
     */
    public String getSelectedCategory() {

        return selectedCategory;
    }

    /**
     * Sets the selected sub category.
     * @param selectedSubCategory the new selected sub category
     */
    public void setSelectedSubCategory(String selectedSubCategory) {

        this.selectedSubCategory = selectedSubCategory;
    }

    /**
     * Gets the selected sub category.
     * @return the selected sub category
     */
    public String getSelectedSubCategory() {

        return selectedSubCategory;
    }

    /**
     * Sets the selected product.
     * @param selectedProduct the new selected product
     */
    public void setSelectedProduct(String selectedProduct) {

        this.selectedProduct = selectedProduct;
    }

    /**
     * Gets the selected product.
     * @return the selected product
     */
    public String getSelectedProduct() {

        return selectedProduct;
    }

    /**
     * Sets the product service.
     * @param productService the new product service
     */
    public void setProductService(IProductService productService) {

        this.productService = productService;
    }

    

    public double getStock() {
		return stock;
	}

	public void setStock(double stock) {
		this.stock = stock;
	}

	/**
     * Sets the product price.
     * @param productPrice the new product price
     */
    public void setProductPrice(String productPrice) {

        this.productPrice = productPrice;
    }

    /**
     * Gets the product price.
     * @return the product price
     */
    public String getProductPrice() {

        return productPrice;
    }  

    public ProductReturn getProductReturn() {
		return productReturn;
	}

	public void setProductReturn(ProductReturn productReturn) {
		this.productReturn = productReturn;
	}

	public ProductReturnDetail getProductReturnDetail() {
		return productReturnDetail;
	}

	public void setProductReturnDetail(ProductReturnDetail productReturnDetail) {
		this.productReturnDetail = productReturnDetail;
	}

	public String getProductReturnProduct() {
		return productReturnProduct;
	}

	public void setProductReturnProduct(String productReturnProduct) {
		this.productReturnProduct = productReturnProduct;
	}

	public String getProductReturnProductList() {
		return productReturnProductList;
	}

	public void setProductReturnProductList(String productReturnProductList) {
		this.productReturnProductList = productReturnProductList;
	}

	public String getProductReturnDescription() {
		return productReturnDescription;
	}

	public void setProductReturnDescription(String productReturnDescription) {
		this.productReturnDescription = productReturnDescription;
	}

	public List<ProductReturnDetail> getProductReturnDetailList() {
		return productReturnDetailList;
	}

	public void setProductReturnDetailList(List<ProductReturnDetail> productReturnDetailList) {
		this.productReturnDetailList = productReturnDetailList;
	}

	public String getProductReturnDetails() {
		return productReturnDetails;
	}

	public void setProductReturnDetails(String productReturnDetails) {
		this.productReturnDetails = productReturnDetails;
	}

	public Set<ProductReturnDetail> getNewProductReturnDetails() {
		return newProductReturnDetails;
	}

	public void setNewProductReturnDetails(Set<ProductReturnDetail> newProductReturnDetails) {
		this.newProductReturnDetails = newProductReturnDetails;
	}

	public String getProductReturnTxnTime() {
		return productReturnTxnTime;
	}

	public void setProductReturnTxnTime(String productReturnTxnTime) {
		this.productReturnTxnTime = productReturnTxnTime;
	}

	/**
     * Gets the agent.
     * @return the agent
     */
    public Agent getAgent() {

        return agent;
    }

    /**
     * Sets the agent.
     * @param agent the new agent
     */
    public void setAgent(Agent agent) {

        this.agent = agent;
    }

    /**
     * Gets the product.
     * @return the product
     */
    public Product getProduct() {

        return product;
    }

    /**
     * Sets the product.
     * @param product the new product
     */
    public void setProduct(Product product) {

        this.product = product;
    }

    

    /**
     * Gets the id.
     * @return the id
     */
    public String getId() {

        return id;
    }

    /**
     * Sets the id.
     * @param id the new id
     */
    public void setId(String id) {

        this.id = id;
    }

    /**
     * Sets the selected season.
     * @param selectedSeason the new selected season
     */
    public void setSelectedSeason(String selectedSeason) {

        this.selectedSeason = selectedSeason;
    }

    /**
     * Gets the selected season.
     * @return the selected season
     */
    public String getSelectedSeason() {

        return selectedSeason;
    }

    /**
     * Sets the units.
     * @param units the new units
     */
    public void setUnits(String units) {

        this.units = units;
    }

    /**
     * Gets the units.
     * @return the units
     */
    public String getUnits() {

        return units;
    }

    /**
     * Sets the start date.
     * @param startDate the new start date
     */
    public void setStartDate(String startDate) {

        this.startDate = startDate;
    }

    /**
     * Gets the start date.
     * @return the start date
     */
    public String getStartDate() {

        return startDate;
    }

    /**
     * Sets the id generator.
     * @param idGenerator the new id generator
     */
    public void setIdGenerator(IUniqueIDGenerator idGenerator) {

        this.idGenerator = idGenerator;
    }

    /**
     * Gets the id generator.
     * @return the id generator
     */
    public IUniqueIDGenerator getIdGenerator() {

        return idGenerator;
    }

    /**
     * Gets the current date.
     * @return the current date
     */
    public String getCurrentDate() {

        Calendar currentDate = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat(getGeneralDateFormat().concat(" HH:mm:ss"));
        return df.format(currentDate.getTime());

    }

    /**
     * Sets the selected unit.
     * @param selectedUnit the new selected unit
     */
    public void setSelectedUnit(String selectedUnit) {

        this.selectedUnit = selectedUnit;
    }

    /**
     * Gets the selected unit.
     * @return the selected unit
     */
    public String getSelectedUnit() {

        return selectedUnit;
    }

    /**
     * Sets the receipt number.
     * @param receiptNumber the new receipt number
     */
    public void setReceiptNumber(String receiptNumber) {

        this.receiptNumber = receiptNumber;
    }

    /**
     * Gets the receipt number.
     * @return the receipt number
     */
    public String getReceiptNumber() {

        return receiptNumber;
    }

    /**
     * Sets the product total string.
     * @param productTotalString the new product total string
     */
    public void setProductTotalString(String productTotalString) {

        this.productTotalString = productTotalString;
    }

    /**
     * Gets the product total string.
     * @return the product total string
     */
    public String getProductTotalString() {

        return productTotalString;
    }

    

    /**
     * Sets the reference no.
     * @param referenceNo the new reference no
     */
    public void setReferenceNo(String referenceNo) {

        this.referenceNo = referenceNo;
    }

    /**
     * Gets the reference no.
     * @return the reference no
     */
    public String getReferenceNo() {

        return referenceNo;
    }

    /**
     * Sets the serial no.
     * @param serialNo the new serial no
     */
    public void setSerialNo(String serialNo) {

        this.serialNo = serialNo;
    }

    /**
     * Gets the serial no.
     * @return the serial no
     */
    public String getSerialNo() {

        return serialNo;
    }

    /**
     * Gets the units list.
     * @return the units list
     */
    public List<String> getUnitsList() {

        return unitsList;
    }

    /**
     * Sets the units list.
     * @param unitsList the new units list
     */
    public void setUnitsList(List<String> unitsList) {

        this.unitsList = unitsList;
    }

    /**
     * Gets the location service.
     * @return the location service
     */
    public ILocationService getLocationService() {

        return locationService;
    }

    /**
     * Sets the location service.
     * @param locationService the new location service
     */
    public void setLocationService(ILocationService locationService) {

        this.locationService = locationService;
    }

    /**
     * Gets the product distribution service.
     * @return the product distribution service
     */
    public IProductDistributionService getProductDistributionService() {

        return productDistributionService;
    }

    /**
     * Gets the product service.
     * @return the product service
     */
    public IProductService getProductService() {

        return productService;
    }

    /**
     * Gets the warehouse list.
     * @return the warehouse list
     */
    public List<String> getWarehouseList() {

        List<WarehouseProduct> returnValue = locationService.listOfProductWarehouse();
        for (WarehouseProduct warehouseProduct : returnValue) {
            warehouseList.add(warehouseProduct.getWarehouse().getName() + " - "
                    + warehouseProduct.getWarehouse().getCode());
        }
        return warehouseList;
    }

    /**
     * Sets the warehouse list.
     * @param warehouseList the new warehouse list
     */
    public void setWarehouseList(List<String> warehouseList) {

        this.warehouseList = warehouseList;
    }

    /**
     * Gets the selected warehouse.
     * @return the selected warehouse
     */
    public String getSelectedWarehouse() {

        return selectedWarehouse;
    }

    /**
     * Sets the selected warehouse.
     * @param selectedWarehouse the new selected warehouse
     */
    public void setSelectedWarehouse(String selectedWarehouse) {

        this.selectedWarehouse = selectedWarehouse;
    }

    /**
     * Gets the selected agent.
     * @return the selected agent
     */
    public String getSelectedAgent() {

        return selectedAgent;
    }

    /**
     * Sets the selected agent.
     * @param selectedAgent the new selected agent
     */
    public void setSelectedAgent(String selectedAgent) {

        this.selectedAgent = selectedAgent;
    }

    /**
     * Gets the coopearative list.
     * @return the coopearative list
     */
    /*
     * public Map<String, String> getAgentList() { Map<String, String> returnMap = new
     * LinkedHashMap<String, String>(); if (!StringUtil.isEmpty(agentId)) { List<Agent> agentList =
     * agentService .listFieldStaffsByCoopetiveManagerProfileId(agentId); if
     * (!ObjectUtil.isListEmpty(agentList)) { for (Agent agent : agentList) {
     * returnMap.put(agent.getProfileId(), (agent.getPersonalInfo().getFirstName() + " " +
     * agent.getPersonalInfo().getLastName() + "-" + agent .getProfileId())); } } } return
     * returnMap; }
     */

    public Map<String, String> getCoopearativeList() {

        Map<String, String> returnMap = new LinkedHashMap<String, String>();
        List<Warehouse> coopearativeList = locationService.listOfCooperatives();
        if (!ObjectUtil.isListEmpty(coopearativeList)) {
            for (Warehouse warehouse : coopearativeList) {
         /*       returnMap.put(warehouse.getName() + " - " + warehouse.getCode(), warehouse
                        .getName()
                        + " - " + warehouse.getCode());*/
                returnMap.put(warehouse.getName() + " - " + warehouse.getCode(), warehouse
                        .getName());
            }
        }
        return returnMap;
    }

    /**
     * Gets the agent lists.
     * @return the agent lists
     */
    public Map<Long, String> getAgentLists() {

        Map<Long, String> returnMap = new LinkedHashMap<Long, String>();
        List<Agent> agentLists = (List<Agent>) agentService.
        		listAgentAndOperator();
        
        if (!ObjectUtil.isListEmpty(agentLists)) {
          returnMap=agentLists.stream().collect(Collectors.toMap(Agent::getId,ag->String.join(" ",ag.getPersonalInfo().getFirstName(),ag.getPersonalInfo().getLastName(),ag.getProfileId())));
        }

        return returnMap;
    }
    
    public Map<Long, String> getAgentListDatas() {

        Map<Long, String> returnMap = new LinkedHashMap<Long, String>();
        List<Agent> agentLists = (List<Agent>) agentService
                .listAgentByAgentTypeWithStatus(AgentType.FIELD_STAFF);
        
        if (!ObjectUtil.isListEmpty(agentLists)) {
          returnMap=agentLists.stream().collect(Collectors.toMap(Agent::getId,ag->String.join(" ",ag.getPersonalInfo().getFirstName(),ag.getPersonalInfo().getLastName(),ag.getProfileId())));
        }

        return returnMap;
    }
    

    public Map<String, String> getSubCategoryList() {

        Map<String, String> returnMap = new LinkedHashMap<String, String>();
        List<SubCategory> subCategoryList = categoryService.listSubCategory();
        if (!ObjectUtil.isListEmpty(subCategoryList)) {
            for (SubCategory subCategory : subCategoryList) {
/*                returnMap.put(subCategory.getName() + " - " + subCategory.getCode(), subCategory
                        .getName()
                        + " - " + subCategory.getCode());*/
                returnMap.put(subCategory.getName() + " - " + subCategory.getCode(), subCategory
                        .getName());
            }
        }
        return returnMap;
    }

    /**
     * Sets the agent list.
     * @param agentList the agent list
     */
    public void setAgentList(Map<String, String> agentList) {

        this.agentList = agentList;
    }

    /**
     * Gets the account service.
     * @return the account service
     */
    public IAccountService getAccountService() {

        return accountService;
    }

    /**
     * Sets the account service.
     * @param accountService the new account service
     */
    public void setAccountService(IAccountService accountService) {

        this.accountService = accountService;
    }

    /**
     * Sets the coopearative list.
     * @param coopearativeList the coopearative list
     */
    public void setCoopearativeList(Map<String, String> coopearativeList) {

        this.coopearativeList = coopearativeList;
    }

    /**
     * Sets the agent lists.
     * @param agentLists the agent lists
     */
    public void setAgentLists(Map<String, String> agentLists) {

        this.agentLists = agentLists;
    }

    /**
     * Gets the agent list.
     * @return the agent list
     */
    public Map<String, String> getAgentList() {

        return agentList;
    }

    /**
     * Gets the agent service.
     * @return the agent service
     */
    public IAgentService getAgentService() {

        return agentService;
    }

    /**
     * @see com.sourcetrace.esesw.view.WebTransactionAction#setAgentService(com.ese.service.profile.IAgentService)
     */
    public void setAgentService(IAgentService agentService) {

        this.agentService = agentService;
    }

    public ICategoryService getCategoryService() {

        return categoryService;
    }

    public void setCategoryService(ICategoryService categoryService) {

        this.categoryService = categoryService;
    }

    public Map<String, String> getCategoryList() {

        return categoryList;
    }

    public void setCategoryList(Map<String, String> categoryList) {

        this.categoryList = categoryList;
    }


    public void setCostPriceArrayValue(String costPriceArrayValue) {

        this.costPriceArrayValue = costPriceArrayValue;
    }

    public String getCostPriceArrayValue() {

        return costPriceArrayValue;
    }

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}
	 public Map<String, String> getHarvestSeasonList() {

	        Map<String, String> returnMap = new LinkedHashMap<String, String>();
	        List<HarvestSeason> harvestSeasonList = productDistributionService.listHarvestSeason();
	        if (!ObjectUtil.isListEmpty(harvestSeasonList)) {

	            for (HarvestSeason harvestSeason : harvestSeasonList) {
	                /*returnMap.put(harvestSeason.getCode(),
	                        harvestSeason.getName() + " - " + harvestSeason.getCode());*/
	                returnMap.put(harvestSeason.getCode(),
	                        harvestSeason.getName());
	            }
	        }
	        return returnMap;
	    }

    public String getHarvestSeasonEnabled() {
    
        return harvestSeasonEnabled;
    }

    public void setHarvestSeasonEnabled(String harvestSeasonEnabled) {
    
        this.harvestSeasonEnabled = harvestSeasonEnabled;
    }

    public void setHarvestSeasonList(Map<String, String> harvestSeasonList) {
    
        this.harvestSeasonList = harvestSeasonList;
    }

	public String getSelectedFieldStaff() {
		return selectedFieldStaff;
	}

	public void setSelectedFieldStaff(String selectedFieldStaff) {
		this.selectedFieldStaff = selectedFieldStaff;
	}
	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public double getWarehouseProductStock() {
		return WarehouseProductStock;
	}

	public void setWarehouseProductStock(double warehouseProductStock) {
		WarehouseProductStock = warehouseProductStock;
	}

	public String getSeasonCode() {
		return seasonCode;
	}

	public void setSeasonCode(String seasonCode) {
		this.seasonCode = seasonCode;
	}

	public IFarmerService getFarmerService() {
		return farmerService;
	}

	public void setFarmerService(IFarmerService farmerService) {
		this.farmerService = farmerService;
	}

	public String getSeasonCodeAndName() {
		return seasonCodeAndName;
	}

	public void setSeasonCodeAndName(String seasonCodeAndName) {
		this.seasonCodeAndName = seasonCodeAndName;
	}

	
	public Map<Long, String> getBatchNoList() {
		return batchNoList;
	}

	public void setBatchNoList(Map<Long, String> batchNoList) {
		this.batchNoList = batchNoList;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getEnableBatchNo() {
		return enableBatchNo;
	}

	public void setEnableBatchNo(String enableBatchNo) {
		this.enableBatchNo = enableBatchNo;
	}

	public double getFieldStaffStock() {
		return fieldStaffStock;
	}

	public void setFieldStaffStock(double fieldStaffStock) {
		this.fieldStaffStock = fieldStaffStock;
	}

	public String getsDate() {
		return sDate;
	}

	public void setsDate(String sDate) {
		this.sDate = sDate;
	}

	public String getUpdateTxnTime() {
		return updateTxnTime;
	}

	public void setUpdateTxnTime(String updateTxnTime) {
		this.updateTxnTime = updateTxnTime;
	}
	
	public String populatePrintHTML() {
		initializeDistributionPrintMap();
		if (!StringUtil.isEmpty(receiptNumber)) {
			ProductReturn productReturn = productDistributionService.findProductReturnFarmerByRecNoAndTxnType(receiptNumber, ProductReturn.PRODUCT_RETURN_FROM_FIELDSTAFF);
			if (!StringUtil.isEmpty(productReturn)) {
				buildTransactionPrintMap(productReturn);
			}

			/*
			 * Distribution distribution = productDistributionService
			 * .findDistributionByRecNoAndTxnType(receiptNumber,
			 * Distribution.PRODUCT_DISTRIBUTION_TO_FARMER);
			 */
		}
		return "html";
	}
	private void initializeDistributionPrintMap() {

		this.productReturnMap = new HashMap<String, Object>();
		List<Map<String, Object>> productMapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> totalMap = new LinkedHashMap<String, Object>();
		this.productReturnMap.put("recNo", "");
		this.productReturnMap.put("fId", "");
		this.productReturnMap.put("fName", "");
		this.productReturnMap.put("village", "");
		this.productReturnMap.put("date", "");
		this.productReturnMap.put("distributionAmt", "");
		this.productReturnMap.put("paymentAmout", "");
		this.productReturnMap.put("productMapList", productMapList);
		this.productReturnMap.put("totalInfo", totalMap);
		this.productReturnMap.put("agentId", "");
		this.productReturnMap.put("agentName", "");
		this.productReturnMap.put("openingBal", "0.00");
		this.productReturnMap.put("finalBal", "0.00");
		this.productReturnMap.put("samithi", "N/A");
		this.productReturnMap.put("freeDist", "0");
		this.productReturnMap.put("oBal", 0.00);
		this.productReturnMap.put("fBal", 0.00);
		this.productReturnMap.put("isAgent", true);
		this.productReturnMap.put("payMode", "");
		this.productReturnMap.put("payAmt", "");
		this.productReturnMap.put("credAmt", "");
		this.productReturnMap.put("finPayBal", 0.00);
		this.productReturnMap.put("amntTax", 0.00);
		String batchaNo = preferncesService.findPrefernceByName(ESESystem.ENABLE_BATCH_NO);
		if(batchaNo.equalsIgnoreCase("1")){
		this.productReturnMap.put("enableBatchNo", "");
		}
		this.productReturnMap.put("warehouseCode", "");
		this.productReturnMap.put("warehouseName", "");
	}
	private void buildTransactionPrintMap(ProductReturn productReturn) {


		List<Map<String, Object>> productMapList = new ArrayList<Map<String, Object>>();
		if (!ObjectUtil.isEmpty(productReturn)) {
			double totalQuantity = 0d;
			double totalPricePerUnit = 0d;
			double totalAmount = 0d;
			DateFormat df = new SimpleDateFormat(getGeneralDateFormat());
			// if (!ObjectUtil.isEmpty(distribution.getAgroTransaction())) {
			if (!StringUtil.isEmpty(productReturn.getReceiptNumber())) {
				this.productReturnMap.put("recNo", productReturn.getReceiptNumber());
			}
			if (!StringUtil.isEmpty(productReturn.getFarmerId())) {
				this.productReturnMap.put("fId", productReturn.getFarmerId());
			}
			if (!StringUtil.isEmpty(productReturn.getFarmerName())) {
				this.productReturnMap.put("fName", productReturn.getFarmerName());
			}
			if (!ObjectUtil.isEmpty(productReturn.getVillage())
					&& !StringUtil.isEmpty(productReturn.getVillage().getName())) {
				this.productReturnMap.put("village", productReturn.getVillage().getName());
			}
			if (!ObjectUtil.isEmpty(productReturn.getTxnTime())) {
				this.productReturnMap.put("date", df.format(productReturn.getTxnTime()));
			}
			if (!StringUtil.isEmpty(productReturn.getAgentId())) {
				this.productReturnMap.put("agentId", productReturn.getAgentId());
				Agent agent = agentService.findAgentByProfileId(productReturn.getAgentId());
				if (!ObjectUtil.isEmpty(agent))
					this.productReturnMap.put("isAgent", true);
			}
			if (!StringUtil.isEmpty(productReturn.getAgentName())) {
				this.productReturnMap.put("agentName", productReturn.getAgentName());
			}
			if (!StringUtil.isEmpty(productReturn.getSamithiName())) {
				this.productReturnMap.put("samithi", productReturn.getSamithiName());
			}

			if (!StringUtil.isEmpty(productReturn.getFreeDistribution())) {
				this.productReturnMap.put("freeDist", productReturn.getFreeDistribution());
			}
			
			if (!StringUtil.isEmpty(productReturn.getTotalAmount())) {
				this.productReturnMap.put("distributionAmt",
						CurrencyUtil.thousandSeparator(productReturn.getTotalAmount()));
			}

			if (!StringUtil.isEmpty(productReturn.getFinalAmount())) {
				this.productReturnMap.put("distributionFinAmt",
						CurrencyUtil.thousandSeparator(productReturn.getFinalAmount()));
			}

			this.productReturnMap.put("openingBal",
					CurrencyUtil.thousandSeparator(Math.abs(productReturn.getIntBalance())));
			this.productReturnMap.put("oBal", productReturn.getIntBalance());
			
				this.productReturnMap.put("finalBal",
						CurrencyUtil.thousandSeparator(Math.abs(productReturn.getBalAmount())));
				this.productReturnMap.put("fBal", productReturn.getBalAmount());
	
			if (!ObjectUtil.isEmpty(productReturn.getPaymentMode())) {
				if (productReturn.getPaymentMode().equals("CR")) {
					this.productReturnMap.put("payMode", "Credit");
					Double outstandingBal = productReturn.getFinalAmount() + (Math.abs(productReturn.getIntBalance()));
					this.productReturnMap.put("outstandingBal", outstandingBal);
				}
			}
			if (!ObjectUtil.isEmpty(productReturn.getFinalAmount())) {
				this.productReturnMap.put("credAmt",
						CurrencyUtil.getDecimalFormat(productReturn.getFinalAmount(), "##.00"));
			}
			if (!ObjectUtil.isEmpty(productReturn.getTax())) {
				this.productReturnMap.put("amntTax", CurrencyUtil.getDecimalFormat(productReturn.getTax(), "##.00"));
			}
			if (!StringUtil.isEmpty(productReturn.getServicePointId())) {
				this.productReturnMap.put("warehouseCode", productReturn.getServicePointId());
			}
			if (!StringUtil.isEmpty(productReturn.getServicePointName())) {
				this.productReturnMap.put("warehouseName", productReturn.getServicePointName());
			}
			String batchaNo = preferncesService.findPrefernceByName(ESESystem.ENABLE_BATCH_NO);
			if(batchaNo.equalsIgnoreCase("1")){
			this.productReturnMap.put("enableBatchNo", batchaNo);
			}
			if (!ObjectUtil.isEmpty(productReturn.getTxnType())) {
				if ("344".equalsIgnoreCase(productReturn.getTxnType())) {
					this.productReturnMap.put("isAgent", false);
				} else {
					this.productReturnMap.put("isAgent", true);
				}
			}

			this.productReturnMap.put("paymentAmout", CurrencyUtil.thousandSeparator(productReturn.getPaymentAmount()));
			this.productReturnMap.put("productMapList", productMapList);
			if (!ObjectUtil.isListEmpty(productReturn.getProductReturnDetail())) {
				for (ProductReturnDetail productReturnDetail : productReturn.getProductReturnDetail()) {
					Map<String, Object> productMap = new LinkedHashMap<String, Object>();
					String productName = "";
					String categoryName = "";

					if (!ObjectUtil.isEmpty(productReturnDetail.getProduct().getSubcategory()))
						categoryName = !StringUtil.isEmpty(productReturnDetail.getProduct().getSubcategory().getName())
								? productReturnDetail.getProduct().getSubcategory().getName() : "";
					if (!ObjectUtil.isEmpty(productReturnDetail.getProduct()))
						productName = !StringUtil.isEmpty(productReturnDetail.getProduct().getName())
								? productReturnDetail.getProduct().getName() : "";

					productMap.put("category", categoryName);
					productMap.put("product", productName);
					if(batchaNo.equalsIgnoreCase("1")){
					productMap.put("batchNo", productReturnDetail.getBatchNo());
					}
					productMap.put("sellingPrice",
							CurrencyUtil.getDecimalFormat(productReturnDetail.getSellingPrice(), "##.0000"));
					productMap.put("quantity",
							CurrencyUtil.getDecimalFormat(productReturnDetail.getQuantity(), "##.000"));
					// productMap.put("pricePerUnit", CurrencyUtil
					// .thousandSeparator(distributionDetail.getPricePerUnit()));
					productMap.put("amount", CurrencyUtil.thousandSeparator(productReturnDetail.getSubTotal()));

					totalQuantity += productReturnDetail.getQuantity();
					totalPricePerUnit += productReturnDetail.getPricePerUnit();
					totalAmount += productReturnDetail.getSubTotal();

					productMapList.add(productMap);
				}
			}
			Map<String, Object> totalMap = new LinkedHashMap<String, Object>();
			totalMap.put("totalQuantity", CurrencyUtil.getDecimalFormat(totalQuantity, "##.000"));
			totalMap.put("totalPricePerUnit", CurrencyUtil.thousandSeparator(totalPricePerUnit));
			totalMap.put("totalAmount", CurrencyUtil.thousandSeparator(totalAmount));
			this.productReturnMap.put("totalInfo", totalMap);
		}
	
	}

	public Map<String, Object> getProductReturnMap() {
		return productReturnMap;
	}

	public void setProductReturnMap(Map<String, Object> productReturnMap) {
		this.productReturnMap = productReturnMap;
	}

	public String getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}
	
}
