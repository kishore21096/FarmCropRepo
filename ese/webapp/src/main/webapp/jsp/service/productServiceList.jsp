<%@ include file="/jsp/common/grid-assets.jsp"%>
<head>
<!-- add this meta information to select layout  -->
<META name="decorator" content="swithlayout">
</head>
<body>
	<script type="text/javascript">
	var tenant='<s:property value="getCurrentTenantId()"/>';
	var loadCategory='';
		var loadUnit='';
		var loadManufacture='';
		var loadIngredient='';
function reloadProdGrid()
{
	var lastSel;
	var category = $.ajax({url: 'productService_populateSubCategory.action', async: false, success: function(data, result) {if (!result) alert('Failure to retrieve the Countries.');}}).responseText;
	 loadCategory=category.replace("{","").replace("}","");
	var unit = $.ajax({url: 'productService_populateUnit.action', async: false, success: function(data, result) {if (!result) alert('Failure to retrieve the Countries.');}}).responseText;
	 loadUnit=unit.replace("{","").replace("}","");
	 var manu= $.ajax({url: 'productService_populateManufacture.action', async: false, success: function(data, result) {if (!result) alert('Failure to retrieve the Countries.');}}).responseText;
	 loadManufacture=manu.replace("{","").replace("}","");
	 var ingre = $.ajax({url: 'productService_populateIngredient.action', async: false, success: function(data, result) {if (!result) alert('Failure to retrieve the Countries.');}}).responseText;
	 loadIngredient=ingre.replace("{","").replace("}","");
	
	 
	 
	
	 jQuery("#detail")
	.jqGrid(
			{
			   	url:'productService_data.action',
			   	pager: '#pagerForDetail',	  
			   	datatype: "json",	
			 	styleUI : 'Bootstrap',
				mtype: 'POST',
				 editurl: 'productService_update.action',
			   	colNames:[	

						'<s:text name="product.code"/>',
		  				'<s:text name="subCategory"/>',
		  				'<s:text name="product.name"/>',
		  				'<s:text name="product.unit"/>', 
		  				'<s:text name="Price"/>', 
		  				<s:if test="currentTenantId=='griffith'">
			  			'<s:property value="getLocaleProperty('product.manufacture')"/>',
			  			'<s:property value="getLocaleProperty('product.ingredient')"/>',
			  			</s:if>
			  			'<s:text name="Actions"/>'
						 ],
			   	colModel:[
			   	
			   		{name:'code',index:'code',width:50,sortable:true},
			   		{name:'subCategoryId',index:'subCategoryId',sortable:false, width:70,editable:true,
			   			edittype: "select", editrules: { required: true } 	
					
					
					},
			   		{name:'selectedProductName',index:'selectedProductName',width:80,sortable:true,editable:true},
			   		{name:'selectedUnit',index:'selectedUnit',sortable:false, width:40,editable: true,
						 edittype: "select", editrules: { required: true } 	
					
					
					},
					{name:'selectedPrice',index:'selectedPrice',width:80,align:'right',sortable:true,editable:true},
			   		//{name:'unit',index:'unit',width:125,sortable:true},	
			   		<s:if test="currentTenantId=='griffith'">
			   		{name:'manufacture',index:'manufacture', sortable:true, width:50,editable: true,edittype: "select", editrules: { required: true } 	},
			   		{name:'ingredient',index:'ingredient', sortable:true, width:50,editable: true, edittype: "select", editrules: { required: true } 	},
			   		</s:if>
			   		{name:'act',index:'act',width:40,sortable:false,formatter: "actions",search:false,
                        formatoptions: {
                            keys: true, 
                         
                            delOptions: { 
                            	url: 'productService_delete.action',
                            	afterShowForm:function ($form) {
                            	    $form.closest('div.ui-jqdialog').position({
                            	        my: "center",
                            	        of: $("#detail").closest('div.ui-jqgrid')
                            	    });
                            	},
                            	
                              afterSubmit: function(data) 
                                {
                                	var json = JSON.parse(data.responseText);
									//document.getElementById("validateErrorCate").innerHTML=json.msg;
									jQuery("#detail").jqGrid('setGridParam',{url:"productService_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
									showPopup(json.msg,json.title);
									
									//	insertOptions("procurementVarietyId",JSON.parse(result));
									jQuery('.ui-jqdialog-titlebar-close').click();
                                }
			   		
                                
                                
                            },
                            
                            afterSave: function (id, response, options) {
                                var json = JSON.parse(response.responseText);
								jQuery("#detail").jqGrid('setGridParam',{url:"productService_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
								showPopup(json.msg,json.title);
								jQuery('.ui-jqdialog-titlebar-close').click();
                            }
                            
                            
                               
                           
                        }
			    	 }
						 ],
			   	height: 301, 
			    width: $("#baseDiv").width(), // assign parent div width
			    /* scrollOffset: 0, */
			   	rowNum:10,
			   	rowList : [10,25,50],
			    sortname:'id',			  
			    sortorder: "desc",
			    viewrecords: true, // for viewing noofrecords displaying string at the right side of the table
			  
				
		        onSortCol: function (index, idxcol, sortorder) {
			        if (this.p.lastsort >= 0 && this.p.lastsort !== idxcol
		                    && this.p.colModel[this.p.lastsort].sortable !== false) {
		                $(this.grid.headers[this.p.lastsort].el).find(">div.ui-jqgrid-sortable>span.s-ico").show();
		            }
		        },
		        loadComplete: function() {
		      		$('#detail').setColProp('subCategoryId', { editoptions: { value: loadCategory} });
		       		 $('#detail').setColProp('selectedUnit', { editoptions: { value: loadUnit} });
		       		$('#detail').setColProp('manufacture', { editoptions: { value: loadManufacture} });
		       	 $('#detail').setColProp('ingredient', { editoptions: { value: loadIngredient} });
		       		$(".ui-inline-save span").removeClass("glyphicon").removeClass("glyphicon-save");
		       		$(".ui-inline-save span").addClass("fa").addClass("fa-save").addClass("inline-gridSave");
		       		$(".ui-inline-cancel span").removeClass("glyphicon").removeClass("glyphicon-remove-circle");
		       		$(".ui-inline-cancel span").addClass("fa").addClass("fa-close").addClass("inline-gridSave");
						//$(".ui-inline-save span").html('<i class="fa fa-floppy-o fa-4 inline-gridSave" aria-hidden="true"></i>');
		       		
		        }
		       
		    });	
			
			jQuery("#detail").jqGrid('navGrid','#pagerForDetail',{edit:false,add:false,del:false,search:false,refresh:true})
			jQuery("#detail").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false});
			colModel = jQuery("#detail").jqGrid('getGridParam', 'colModel');
		    $('#gbox_' + $.jgrid.jqID(jQuery("#detail")[0].id) +
		        ' tr.ui-jqgrid-labels th.ui-th-column').each(function (i) {
		        var cmi = colModel[i], colName = cmi.name;

		        if (cmi.sortable !== false) {
		            $(this).find('>div.ui-jqgrid-sortable>span.s-ico').show();
		        } else if (!cmi.sortable && colName !== 'rn' && colName !== 'cb' && colName !== 'subgrid') {
		            $(this).find('>div.ui-jqgrid-sortable').css({cursor: 'default'});
		        }
					    

			});	
	
}

function reloadCateGrid()
{
jQuery("#detailCat").jqGrid(
			{
			   	url:'subCategoryService_data.action',
			   	pager: '#pagerForDetailCat',
				mtype: 'POST',
				editurl: 'subCategoryService_update.action',
				styleUI : 'Bootstrap',
			   	datatype: "json",	
			   	colNames:[	
							<s:if test='branchId==null'>
							'<s:text name="app.branch"/>',
							</s:if>
							<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
							<s:if test='branchId!=null'>
							'<s:text name="app.branch"/>',
							</s:if>
							'<s:text name="app.subBranch"/>',
						    </s:if>
			  		   	  '<s:text name="category.code"/>',
			  		      '<s:text name="category.name"/>',
			  		   	 '<s:text name="Actions"/>'
			      	 ],
			   	colModel:[	
							<s:if test='branchId==null'>
					   		{name:'branchId',index:'branchId',width:125,sortable: false,width :125,search:true,stype: 'select',searchoptions: {
					   		value: '<s:property value="parentBranchFilterText"/>',
					   		dataEvents: [ 
					   			          {
					   			            type: "change",
					   			            fn: function () {
					   			            	console.log($(this).val());
					   			             	getSubBranchValues($(this).val())
					   			            }
					   			        }]
					   			
					   			}},	   				   		
					   		</s:if>
					   		<s:if test='isMultiBranch=="1"&&(getIsParentBranch()==1||branchId==null)'>
					   		<s:if test='branchId!=null'>
					   		{name:'branchId',index:'branchId',width:125,sortable: false,width :125,search:true,stype: 'select',searchoptions: {
					   		value: '<s:property value="parentBranchFilterText"/>',
					   		dataEvents: [ 
					   			          {
					   			            type: "change",
					   			            fn: function () {
					   			            	console.log($(this).val());
					   			             	getSubBranchValues($(this).val())
					   			            }
					  }]
					   			
					 }},	 
					</s:if>
					{name:'subBranchId',index:'subBranchId',width:125,sortable: false,width :125,search:true,stype: 'select',searchoptions: { value: '<s:property value="childBranchFilterText"/>' }},	   				   		
					</s:if>
			   		{name:'code',index:'code', width:125,sortable:true},
			   		{name:'categoryName',index:'categoryName', width:125,sortable:true,editable:true},
			   		{name:'act',index:'act',width:60,sortable:false,formatter: "actions",search:false,
                        formatoptions: {
                            keys: true,   
                            delOptions: { 
                            	url: 'subCategoryService_delete.action',
                            	afterShowForm:function ($form) {
                            	    $form.closest('div.ui-jqdialog').position({
                            	        my: "center",
                            	        of: $("#detailCat").closest('div.ui-jqgrid')
                            	    });
                            	},
                            	
                              afterSubmit: function(data) 
                                {
                            	  var json = JSON.parse(data.responseText);
									//document.getElementById("validateErrorCate").innerHTML=json.msg;
									jQuery("#detailCat").jqGrid('setGridParam',{url:"subCategoryService_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
									showPopup(json.msg,json.title);
									 jQuery.post("subCategoryService_populatesubCategory.action",function(result){
										 insertOptions("subCategory",JSON.parse(result));  
									jQuery('.ui-jqdialog-titlebar-close').click();
									 });
                                }
			   		
                                
                                
                            },
                            
                            afterSave: function (id, response, options) {
                                var json = JSON.parse(response.responseText);
								jQuery("#detailCat").jqGrid('setGridParam',{url:"subCategoryService_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');

								showPopup(json.msg,json.title);
								 jQuery.post("subCategoryService_populatesubCategory.action",function(result){
									 insertOptions("subCategory",JSON.parse(result));  
								jQuery('.ui-jqdialog-titlebar-close').click();
								 });
                            }
                        }
			    	 }
			   	],
			   	height: 301, 
			    width: $("#baseDiv").width(),
			    scrollOffset: 0,
			   	rowNum:10,
			   	rowList : [10,25,50],
			    sortname:'id',			  
			    sortorder: "desc",
			    viewrecords: true,
			  
		        onSortCol: function (index, idxcol, sortorder) {
			        if (this.p.lastsort >= 0 && this.p.lastsort !== idxcol
		                    && this.p.colModel[this.p.lastsort].sortable !== false) {
		                $(this.grid.headers[this.p.lastsort].el).find(">div.ui-jqgrid-sortable>span.s-ico").show();
		            }
		        },
		        
		        loadComplete: function() {
		      		
		        	//alert($('#actionErrId').val());
		        	
		       		$(".ui-inline-save span").removeClass("glyphicon").removeClass("glyphicon-save");
		       		$(".ui-inline-save span").addClass("fa").addClass("fa-save").addClass("inline-gridSave");
		       		$(".ui-inline-cancel span").removeClass("glyphicon").removeClass("glyphicon-remove-circle");
		       		$(".ui-inline-cancel span").addClass("fa").addClass("fa-close").addClass("inline-gridSave");
						//$(".ui-inline-save span").html('<i class="fa fa-floppy-o fa-4 inline-gridSave" aria-hidden="true"></i>');
		       		
		        },
		   });
			
			jQuery("#detailCat").jqGrid('navGrid','#pagerForDetailCat',{edit:false,add:false,del:false,search:false,refresh:true})
			jQuery("#detailCat").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : false});

			colModel = jQuery("#detailCat").jqGrid('getGridParam', 'colModel');
		    $('#gbox_' + $.jgrid.jqID(jQuery("#detailCat")[0].id) +
		        ' tr.ui-jqgrid-labels th.ui-th-column').each(function (i) {
		        var cmi = colModel[i], colName = cmi.name;

		        if (cmi.sortable !== false) {
		            $(this).find('>div.ui-jqgrid-sortable>span.s-ico').show();
		        } else if (!cmi.sortable && colName !== 'rn' && colName !== 'cb' && colName !== 'subgrid') {
		            $(this).find('>div.ui-jqgrid-sortable').css({cursor: 'default'});
		        }
		    });
	
}

$(document).ready(function(){
	
	 if(tenant=='sagi'){
		 $('#proPrefix').val("0.00");
			jQuery(".sagi").hide();
	 }
	 
	resetPrdocut();
	reloadProdGrid();
	reloadCateGrid();
	$('.ui-jqgrid .ui-jqgrid-bdiv').css('overflow-x', 'hidden'); 
		});	           
		
		
function addProduct()
{
	var subCategory=$("#subCategory").val();
	var productId=$("#productId").val();
	var type=$("#type").val();
	var pricePrefix=$("#proPrefix").val();
	var manufacture=$("#manufacture").val();
	var ingredient=$("#ingredient").val();
		
	if(tenant=='griffith'){
		 if(manufacture=="" || manufacture==null){
				document.getElementById("validateError").innerHTML='<s:property value="%{getLocaleProperty('selectManufacture')}"/>';
			}
			if(ingredient=="" || ingredient==null){
				document.getElementById("validateError").innerHTML='<s:property value="%{getLocaleProperty('selectIngredient')}" />';
			}
		} 
	if(subCategory=="" || subCategory==null || subCategory=="-1")
	{
		document.getElementById("validateError").innerHTML='<s:text name="emptyCategory"/>';
	}
	else if(productId=="" || productId==null)
	{
		document.getElementById("validateError").innerHTML='<s:text name="empty.name"/>';
	}
	 
	else if(type=="" || type==null)
	{
		document.getElementById("validateError").innerHTML='<s:property value="%{getLocaleProperty('selectUnit')}"/>';
	}
	else if(pricePrefix=="" || pricePrefix==null)
	{
		document.getElementById("validateError").innerHTML='<s:text name="empty.price"/>';
	}
	else
	{
		
		var availableUnit;
		
		if(tenant!='griffith'){
		$.post("productService_create",{subCategoryId:subCategory,selectedProductName:productId,selectedUnit:type,selectedPrice:pricePrefix,manufacture:manufacture,ingredient:ingredient},function(data){
			resetPrdocut();
			jQuery("#detail").jqGrid('setGridParam',{url:"productService_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
			// var json = JSON.parse(data);
			showPopup(data.msg,data.title);
		});
		}else{
			$.post("productService_create",{subCategoryId:subCategory,selectedProductName:productId,selectedUnit:type,selectedPrice:pricePrefix,manufacture:manufacture,ingredient:ingredient},function(data){
				resetPrdocut();
				jQuery("#detail").jqGrid('setGridParam',{url:"productService_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
				 var json = JSON.parse(data);
				showPopup(json.msg,json.title);
			});
		}
	}
}

function resetPrdocut()
{
	
	$("#subCategory").val('-1');
	$("#productId").val('');
	$("#type").val('');
	$("#proPrefix").val('');
	$("#proSuffix").val('');
	$("#categoryName").val('');
	$("#manufacture").val('');
	$("#ingredient").val('');
	
	
	document.getElementById("validateError").innerHTML='';
	document.getElementById("validateErrorCate").innerHTML='';
}

function addCategory()
{
	var category=$("#categoryName").val();
	if(category=="" || category==null)
	{
		
		document.getElementById("validateErrorCate").innerHTML='<s:text name="empty.name"/>';
	}
	else
	{
		$.post("subCategoryService_create",{categoryName:category},function(data){
			resetPrdocut();
			jQuery("#detailCat").jqGrid('setGridParam',{url:"subCategoryService_data.action?",page:1,mtype: 'POST'}).trigger('reloadGrid');
			 //var json = JSON.parse(data);
			 showPopup(data.msg,data.title);
			
			 jQuery.post("subCategoryService_populatesubCategory.action",function(result){
				 insertOptions("subCategory",JSON.parse(result));  
				 //alert(JSON.stringify(JSON.parse(result)));
			 });
			 
			 var category = $.ajax({url: 'productService_populateSubCategory.action', async: false, success: function(data, result) {if (!result) alert('Failure to retrieve the Countries.');}}).responseText;
			 loadCategory=category.replace("{","").replace("}","");
			
			var unit = $.ajax({url: 'productService_populateUnit.action', async: false, success: function(data, result) {if (!result) alert('Failure to retrieve the Countries.');}}).responseText;
			 loadUnit=unit.replace("{","").replace("}","");
			 //showPopup(json.msg,json.title);
			
		});
	}
}

function isNumber(evt) {
	
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}
function isDecimal(evt,obj) {
   	
	    
	    var charCode = (evt.which) ? evt.which : event.keyCode
		 if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode!=46)
		        return false;
		    
		    return true;
}	
		
</script>

	<script>


</script>


	<div id="baseDiv" style="width: 97%"></div>
	<div class="appContentWrapper marginBottom">
		<ul class="nav nav-pills">
			<li class="active"><a data-toggle="pill" href="#categories"><s:property
						value="%{getLocaleProperty('categories')}" /></a></li>

			<li><a data-toggle="pill" href="#products"><s:property
						value="%{getLocaleProperty('product')}" /></a></li>
		</ul>
	</div>

	<div class="tab-content">


		<div id="products" class="tab-pane fade">
			<s:form name="productForm" id="productForm">
				<sec:authorize ifAllGranted="profile.products.create">
					<a data-toggle="collapse" data-parent="#accordion"
						href="#productAccordian" class="btn btn-sts margin-bottom10px">
						<s:text name="create.button" />
					</a>
				</sec:authorize>
				<div id="productAccordian" class="panel-collapse collapse">
					<div class="appContentWrapper marginBottom">
						<span id="validateError" style="color: red;"></span>
						<div class="flex-layout filterControls">
							<div class="flexItem">
								<label for="txt"><s:text name="product.subCategory" /><sup
									style="color: red;">*</sup></label>
								<div class="form-element">
									<s:select id="subCategory" name="product.subCategory.id"
										list="listSubCategory" headerKey="-1" listKey="id"
										listValue="name" headerValue="%{getText('txt.select')}"
										cssClass="form-control select2" />
								</div>
							</div>
							<div class="flexItem">
								<label for="txt"><s:text name="product.name" /><sup
									style="color: red;">*</sup></label>
								<div class="form-element">
									<s:textfield name="product.name" id="productId" theme="simple"
										maxlength="50" class="form-control" />
								</div>
							</div>
							<s:if test="currentTenantId=='griffith'">
							<div class="flexItem griffithField">
								<label for="txt"><s:property value="%{getLocaleProperty('product.manufacture')}" /><sup
									style="color: red;">*</sup></label>
								<div class="form-element">
									<s:select id="manufacture" name="product.manufacture"
										list="listManufacture" headerKey=""
										 headerValue="%{getText('txt.select')}" listKey="code"
										listValue="name"
										cssClass="form-control select2" />
								</div>
							</div>
							
							<div class="flexItem griffithField">
								<label for="txt"><s:property value="%{getLocaleProperty('product.ingredient')}" /><sup
									style="color: red;">*</sup></label>
								<div class="form-element">
									<s:select id="ingredient" name="product.ingredient"
										list="listIngredient" 
										listValue="name"
										headerKey="" listKey="code" headerValue="%{getText('txt.select')}"
										cssClass="form-control select2" />
								</div>
							</div>
							</s:if>
							<div class="flexItem">
								<label for="txt"><s:text name="product.unit" /><sup
									style="color: red;">*</sup></label>
								<div class="form-element">
									<s:select id="type" name="product.type.code" 
										headerKey="" headerValue="%{getText('txt.select')}" list="listUom" listKey="code" listValue="name"
										
										cssClass="form-control select2" />
								</div>
							</div>
							<div class="flexItem sagi">
								<label for="txt"><s:text name="product.price" /><sup
									style="color: red;">*</sup></label>
								<div class="form-element flexdisplay">
									<s:textfield name="product.pricePrefix" class="form-control"
										id="proPrefix" theme="simple" maxlength="5"
										onkeypress="return isDecimal(event)" />
									



								</div>

							</div>
							<div class="flexItem" style="margin-top: 24px;">
								<button type="button" class="btn btn-sts "
									data-toggle="modal" onclick="addProduct()">
									<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
									</font>
								</button>
							</div>
						</div>
					</div>
				</div>



				<div class="appContentWrapper ">
					<div id="baseDiv2">
						<table id="detail"></table>
						<div id="pagerForDetail"></div>
					</div>
				</div>
			</s:form>

		</div>

		<div id="categories" class="tab-pane fade in active">
			<s:form name="categroyForm" id="categroyForm">
				<sec:authorize ifAllGranted="profile.products.create">
					<a data-toggle="collapse" data-parent="#accordion"
						href="#categoryAccordian" class="btn btn-sts margin-bottom10px">
						<s:text name="create.button" />
					</a>
				</sec:authorize>
				<div id="categoryAccordian" class="panel-collapse collapse">

					<div class="appContentWrapper marginBottom">
						<span id="validateErrorCate" style="color: red;"></span>

						<div class="flex-layout filterControls">

							<div class="flexItem">
								<label for="txt"><s:text name="product.name" /><sup
									style="color: red;">*</sup></label>
								<div class="form-element flexdisplay">
									<s:textfield id="categoryName" name="categoryName"
										theme="simple" maxlength="50"
										cssClass="form-control" />


								</div>
							</div>

							<div class="flexItem" style="margin-top: 24px;">

								<button type="button" class="btn btn-sts"
									data-toggle="modal" onclick="addCategory()">
									<font color="#FFFFFF"> <b><s:text name="save.button" /></b>
									</font>
								</button>
							</div>
						</div>
					</div>
				</div>
			</s:form>

			<div class="appContentWrapper ">
				<div id="baseDiv1">
					<table id="detailCat"></table>
					<div id="pagerForDetailCat"></div>
				</div>
			</div>
		</div>
	</div>


	<s:form name="createform" action="customer_create">
	</s:form>
	<s:form name="updateform" action="customer_detail">
		<s:hidden name="id" />
		<s:hidden name="currentPage" />
	</s:form>
</body>
