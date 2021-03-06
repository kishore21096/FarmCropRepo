/*
 * ILocationDAO.java
 * Copyright (c) 2015-2016, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.eses.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ese.entity.traceability.HeapData;
import com.ese.entity.traceability.HeapDataDetail;
import com.sourcetrace.eses.entity.ColdStorage;
import com.sourcetrace.eses.entity.LocationHistory;
import com.sourcetrace.eses.entity.LocationHistoryDetail;
import com.sourcetrace.eses.entity.Warehouse;
import com.sourcetrace.eses.entity.WarehouseProduct;
import com.sourcetrace.eses.entity.WarehouseStorageMap;
import com.sourcetrace.eses.util.entity.Location;
import com.sourcetrace.eses.util.entity.LocationLevel;
import com.sourcetrace.esesw.entity.profile.BankInformation;
import com.sourcetrace.esesw.entity.profile.Country;
import com.sourcetrace.esesw.entity.profile.DataLevel;
import com.sourcetrace.esesw.entity.profile.GMO;
import com.sourcetrace.esesw.entity.profile.GramPanchayat;
import com.sourcetrace.esesw.entity.profile.Locality;
import com.sourcetrace.esesw.entity.profile.Municipality;
import com.sourcetrace.esesw.entity.profile.ServicePoint;
import com.sourcetrace.esesw.entity.profile.State;
import com.sourcetrace.esesw.entity.profile.Village;
import com.sourcetrace.esesw.entity.profile.WarehousePayment;

/**
 * The Interface ILocationDAO.
 */
public interface ILocationDAO extends IESEDAO {

	public List<Location> listLocations();

	public Location findLocationById(long id);

	public Location findLocationByCode(String code);

	public Location findLocationByName(String name);

	public LocationLevel findLevelByCode(String levelCode);

	public List<Object[]> listLocationsInfoByLevelCode(String levelCode);

	public List<Object[]> listLocationsInfoByParentCode(String parentCode);

	/**
	 * Find country by name.
	 *
	 * @param name
	 *            the name
	 * @return the country
	 */
	public Country findCountryByName(String name);

	/**
	 * Find country by code.
	 *
	 * @param code
	 *            the code
	 * @return the country
	 */
	public Country findCountryByCode(String code);

	/**
	 * Find locality by name.
	 *
	 * @param name
	 *            the name
	 * @return the locality
	 */
	public Locality findLocalityByName(String name);

	/**
	 * Find locality by code.
	 *
	 * @param code
	 *            the code
	 * @return the locality
	 */
	public Locality findLocalityByCode(String code);

	/**
	 * Find municipality by name.
	 *
	 * @param name
	 *            the name
	 * @return the municipality
	 */
	public Municipality findMunicipalityByName(String name);

	/**
	 * Find municipality.
	 *
	 * @param name
	 *            the name
	 * @param PostalCode
	 *            the postal code
	 * @return the municipality
	 */
	public Municipality findMunicipality(String name, String PostalCode);

	/**
	 * Find municipalities by postal code.
	 *
	 * @param postalCode
	 *            the postal code
	 * @return the list< municipality>
	 */
	public List<Municipality> findMunicipalitiesByPostalCode(String postalCode);

	/**
	 * Find municipality by coordinates.
	 *
	 * @param latitude
	 *            the latitude
	 * @param longitude
	 *            the longitude
	 * @return the municipality
	 */
	public Municipality findMunicipalityByCoordinates(String latitude, String longitude);

	/**
	 * Find state by name.
	 *
	 * @param name
	 *            the name
	 * @return the state
	 */
	public State findStateByName(String name);

	/**
	 * Find state by code.
	 *
	 * @param code
	 *            the code
	 * @return the state
	 */
	public State findStateByCode(String code);

	/**
	 * Find country by id.
	 *
	 * @param id
	 *            the id
	 * @return the country
	 */
	public Country findCountryById(Long id);

	/**
	 * Find locality by id.
	 *
	 * @param id
	 *            the id
	 * @return the locality
	 */
	public Locality findLocalityById(Long id);

	/**
	 * Find municipality by id.
	 *
	 * @param id
	 *            the id
	 * @return the municipality
	 */
	public Municipality findMunicipalityById(Long id);

	/**
	 * Find state by id.
	 *
	 * @param id
	 *            the id
	 * @return the state
	 */
	public State findStateById(Long id);

	/**
	 * List countries.
	 *
	 * @return the list< country>
	 */
	public List<Country> listCountries();

	/**
	 * List localities.
	 *
	 * @param state
	 *            the state
	 * @return the list< locality>
	 */
	public List<Locality> listLocalities(String state);

	/**
	 * List municipalities.
	 *
	 * @param locality
	 *            the locality
	 * @return the list< municipality>
	 */
	public List<Municipality> listMunicipalities(String locality);

	/**
	 * List states.
	 *
	 * @param country
	 *            the country
	 * @return the list< state>
	 */
	public List<State> listStates(String country);

	/**
	 * Find municipality by code.
	 *
	 * @param code
	 *            the code
	 * @return the municipality
	 */
	public Municipality findMunicipalityByCode(String code);

	/**
	 * Find village by code.
	 *
	 * @param villageCode
	 *            the village code
	 * @return the village
	 */
	public Village findVillageByCode(String villageCode);

	/**
	 * Find village by id.
	 *
	 * @param id
	 *            the id
	 * @return the village
	 */
	public Village findVillageById(long id);

	/**
	 * Find village by name.
	 *
	 * @param name
	 *            the name
	 * @return the village
	 */
	public Village findVillageByName(String name);

	/**
	 * List villages.
	 *
	 * @param selectedCity
	 *            the selected city
	 * @return the list< village>
	 */
	public List<Village> listVillages(String selectedCity);

	/**
	 * Find warehouse by code.
	 *
	 * @param warehouseCode
	 *            the warehouse code
	 * @return the warehouse
	 */
	public Warehouse findWarehouseByCode(String warehouseCode);

	/**
	 * Find warehouse by name.
	 *
	 * @param name
	 *            the name
	 * @return the warehouse
	 */
	public Warehouse findWarehouseByName(String name);

	/**
	 * Find warehouse by id.
	 *
	 * @param id
	 *            the id
	 * @return the warehouse
	 */
	public Warehouse findWarehouseById(long id);

	/**
	 * List city.
	 *
	 * @return the list
	 */
	public List listCity();

	/**
	 * List village.
	 *
	 * @return the list
	 */
	public List listVillage();

	/**
	 * List warehouse by city.
	 *
	 * @param id
	 *            the id
	 * @return the list< warehouse>
	 */
	public List<Warehouse> listWarehouseByCity(long id);

	/**
	 * List warehouse.
	 *
	 * @return the list< warehouse>
	 */
	public List<Warehouse> listWarehouse();

	/**
	 * Checks if is city mapping exists with warehouse.
	 *
	 * @param cityId
	 *            the city id
	 * @return true, if is city mapping exists with warehouse
	 */
	public boolean isCityMappingExistsWithWarehouse(long cityId);

	/**
	 * List village by city id.
	 *
	 * @param id
	 *            the id
	 * @return the list< village>
	 */
	public List<Village> listVillageByCityId(long id);

	/**
	 * List village by city.
	 *
	 * @param id
	 *            the id
	 * @return the list< village>
	 */
	public List<Village> listVillageByCity(long id);

	/**
	 * Find municipality by village code.
	 *
	 * @param villageCode
	 *            the village code
	 * @return the municipality
	 */
	public Municipality findMunicipalityByVillageCode(String villageCode);

	/**
	 * List device location history.
	 *
	 * @param serialNumber
	 *            the serial number
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the list< location history>
	 */
	public List<LocationHistory> listDeviceLocationHistory(String serialNumber, Date startDate, Date endDate);

	/**
	 * Find threshold exceeded location history.
	 *
	 * @param serialNumber
	 *            the serial number
	 * @param thresholdValue
	 *            the threshold value
	 * @return the long
	 */
	public long findThresholdExceededLocationHistory(String serialNumber, int thresholdValue);

	/**
	 * Delete location history by id.
	 *
	 * @param id
	 *            the id
	 */
	public void deleteLocationHistoryById(long id);

	/**
	 * List device location history by agent id.
	 *
	 * @param agentId
	 *            the agent id
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the list< location history>
	 */
	public List<LocationHistory> listDeviceLocationHistoryByAgentId(String agentId, Date startDate, Date endDate);

	/**
	 * List villages by city code.
	 *
	 * @param cityCode
	 *            the city code
	 * @return the list< village>
	 */
	public List<Village> listVillagesByCityCode(String cityCode);

	/**
	 * List city by farmer city.
	 *
	 * @return the list< municipality>
	 */
	public List<Municipality> listCityByFarmerCity();

	/**
	 * Checks if is warehouse product city mapping exist.
	 *
	 * @param code
	 *            the code
	 * @return true, if is warehouse product city mapping exist
	 */
	public boolean isWarehouseProductCityMappingExist(String code);

	/**
	 * List warehouse for mtnt receipt no.
	 *
	 * @return the list< warehouse>
	 */
	public List<Warehouse> listWarehouseForMTNTReceiptNo();

	/**
	 * List of product warehouse.
	 *
	 * @return the list< warehouse product>
	 */
	public List<WarehouseProduct> listOfProductWarehouse();

	/**
	 * List warehouse for product exist.
	 *
	 * @return the list< warehouse>
	 */
	public List<Warehouse> listWarehouseForProductExist();

	/**
	 * List warehouse by trip sheet chart no city warehouse stock.
	 *
	 * @return the list< warehouse>
	 */
	public List<Warehouse> listWarehouseByTripSheetChartNoCityWarehouseStock();

	/**
	 * Find warehouse by city id.
	 *
	 * @param cityId
	 *            the city id
	 * @return the warehouse
	 */
	public Warehouse findWarehouseByCityId(long cityId);

	/**
	 * Find village.
	 *
	 * @param id
	 *            the id
	 * @return the village
	 */
	public Village findVillage(long id);

	/**
	 * List service point.
	 *
	 * @return the list< service point>
	 */
	public List<ServicePoint> listServicePoint();

	/**
	 * List active farmers samithi by co operative id.
	 *
	 * @param coOperativeId
	 *            the co operative id
	 * @return the list< warehouse>
	 */
	public List<Warehouse> listActiveFarmersSamithiByCoOperativeId(long coOperativeId);

	/**
	 * List active farmers samithi by agent id.
	 *
	 * @param id
	 *            the id
	 * @return the list< warehouse>
	 */
	public List<Warehouse> listActiveFarmersSamithiByAgentId(long id);

	/**
	 * List active farmers village by samithi id.
	 *
	 * @param samithiId
	 *            the samithi id
	 * @return the list< village>
	 */
	public List<Village> listActiveFarmersVillageBySamithiId(long samithiId);

	/**
	 * Find gram panchayat by id.
	 *
	 * @param id
	 *            the id
	 * @return the gram panchayat
	 */
	public GramPanchayat findGramPanchayatById(long id);

	/**
	 * Find gram panchayat by name.
	 *
	 * @param name
	 *            the name
	 * @return the gram panchayat
	 */
	public GramPanchayat findGramPanchayatByName(String name);

	/**
	 * Find gram panchayat by code.
	 *
	 * @param code
	 *            the code
	 * @return the gram panchayat
	 */
	public GramPanchayat findGramPanchayatByCode(String code);

	/**
	 * List gram panchayats.
	 *
	 * @param selectedCity
	 *            the selected city
	 * @return the list< gram panchayat>
	 */
	public List<GramPanchayat> listGramPanchayats(String selectedCity);

	/**
	 * List active farmers village by samithi code.
	 *
	 * @param samithiCode
	 *            the samithi code
	 * @return the list< village>
	 */
	public List<Village> listActiveFarmersVillageBySamithiCode(String samithiCode);

	/**
	 * List of village code name by city code.
	 *
	 * @param cityCode
	 *            the city code
	 * @return the list< object[]>
	 */
	public List<Object[]> listOfVillageCodeNameByCityCode(String cityCode);

	/**
	 * List of villages by names.
	 *
	 * @param villageNames
	 *            the village names
	 * @return the list< village>
	 */
	public List<Village> listOfVillagesByNames(List<String> villageNames);

	/**
	 * List of villages by codes.
	 *
	 * @param villageCodes
	 *            the village codes
	 * @return the list< village>
	 */
	public List<Village> listOfVillagesByCodes(List<String> villageCodes);

	/**
	 * Find village name by code.
	 *
	 * @param code
	 *            the code
	 * @return the string
	 */
	public String findVillageNameByCode(String code);

	/**
	 * Find city name by code.
	 *
	 * @param code
	 *            the code
	 * @return the string
	 */
	public String findCityNameByCode(String code);

	/**
	 * Find samithi by code.
	 *
	 * @param samithiCode
	 *            the samithi code
	 * @return the warehouse
	 */
	public Warehouse findSamithiByCode(String samithiCode);

	/**
	 * Find co operative by code.
	 *
	 * @param code
	 *            the code
	 * @return the warehouse
	 */
	public Warehouse findCoOperativeByCode(String code);

	/**
	 * Find cooperative mapped with samithi.
	 *
	 * @param code
	 *            the code
	 * @return the warehouse
	 */
	public Warehouse findCooperativeMappedWithSamithi(String code);

	/**
	 * List of cooperatives.
	 *
	 * @return the list< warehouse>
	 */
	public List<Warehouse> listOfCooperatives();

	/**
	 * List of samithi.
	 *
	 * @return the list< warehouse>
	 */
	public List<Warehouse> listOfSamithi();

	/**
	 * Find samithi by name.
	 *
	 * @param samithiName
	 *            the samithi name
	 * @return the warehouse
	 */
	public Warehouse findSamithiByName(String samithiName);

	/**
	 * Find farmer mapped with samithi.
	 *
	 * @param id
	 *            the id
	 * @return true, if successful
	 */
	public boolean findFarmerMappedWithSamithi(long id);

	/**
	 * List co operative and samithi.
	 *
	 * @return the list< warehouse>
	 */
	public List<Warehouse> listCoOperativeAndSamithi();

	/**
	 * Find samithi and co operative by village.
	 *
	 * @param villageId
	 *            the village id
	 * @return the warehouse
	 */
	public Warehouse findSamithiAndCoOperativeByVillage(long villageId);

	/**
	 * Find cooperative by name.
	 *
	 * @param cooperativeName
	 *            the cooperative name
	 * @return the warehouse
	 */
	public Warehouse findCooperativeByName(String cooperativeName);

	/**
	 * Find cooperative village mapped wtih samithi village.
	 *
	 * @param cooperId
	 *            the cooper id
	 * @param villageCode
	 *            the village code
	 * @return the string
	 */
	public String findCooperativeVillageMappedWtihSamithiVillage(long cooperId, String villageCode);

	/**
	 * Find co operative by name.
	 *
	 * @param name
	 *            the name
	 * @return the warehouse
	 */
	public Warehouse findCoOperativeByName(String name);

	/**
	 * List farmer mappped smithi villages.
	 *
	 * @param id
	 *            the id
	 * @return the list< village>
	 */
	public List<Village> listFarmerMapppedSmithiVillages(Long id);

	/**
	 * List samithi by village id.
	 *
	 * @param id
	 *            the id
	 * @return the list< warehouse>
	 */
	public List<Warehouse> listSamithiByVillageId(long id);

	/**
	 * List villages by panchayat.
	 *
	 * @param selectedPanchayat
	 *            the selected panchayat
	 * @return the list< village>
	 */
	public List<Village> listVillagesByPanchayat(String selectedPanchayat);

	/**
	 * Checks if is village mapped with cooperative.
	 *
	 * @param id
	 *            the id
	 * @return true, if is village mapped with cooperative
	 */
	public boolean isVillageMappedWithCooperative(long id);

	/**
	 * List co operative manager village by co operative manager id.
	 *
	 * @param id
	 *            the id
	 * @return the list< village>
	 */
	public List<Village> listCoOperativeManagerVillageByCoOperativeManagerId(long id);

	/**
	 * List field staff village by field staff id.
	 *
	 * @param id
	 *            the id
	 * @return the list< village>
	 */
	public List<Village> listFieldStaffVillageByFieldStaffId(long id);

	/**
	 * List samithies.
	 *
	 * @return the list< warehouse>
	 */
	public List<Warehouse> listSamithies();

	/**
	 * List farmer count by samithi and gender.
	 *
	 * @param id
	 *            the id
	 * @return the list< object[]>
	 */
	public List<Object[]> listFarmerCountBySamithiAndGender(long id);

	/**
	 * List field staff id and name by samithi id.
	 *
	 * @param id
	 *            the id
	 * @return the string
	 */
	public String listFieldStaffIdAndNameBySamithiId(long id);

	/**
	 * List countries by revision no.
	 *
	 * @param revisionNo
	 *            the revision no
	 * @return the list< country>
	 */
	public List<Country> listCountriesByRevisionNo(long revisionNo);

	/**
	 * List states by revision no.
	 *
	 * @param revisionNo
	 *            the revision no
	 * @return the list< state>
	 */
	public List<State> listStatesByRevisionNo(long revisionNo);

	/**
	 * List localities by revision no.
	 *
	 * @param revisionNo
	 *            the revision no
	 * @return the list< locality>
	 */
	public List<Locality> listLocalitiesByRevisionNo(long revisionNo);

	/**
	 * List municipalities by revision no.
	 *
	 * @param revisionNo
	 *            the revision no
	 * @return the list< municipality>
	 */
	public List<Municipality> listMunicipalitiesByRevisionNo(long revisionNo);

	/**
	 * List gram panchayats by revision no.
	 *
	 * @param revisionNo
	 *            the revision no
	 * @return the list< gram panchayat>
	 */
	public List<GramPanchayat> listGramPanchayatsByRevisionNo(long revisionNo);

	/**
	 * List villages by revision no.
	 *
	 * @param revisionNo
	 *            the revision no
	 * @return the list< village>
	 */
	public List<Village> listVillagesByRevisionNo(long revisionNo);

	/**
	 * List village by co operative code.
	 *
	 * @param selectedCooperative
	 *            the selected cooperative
	 * @return the list< village>
	 */
	public List<Village> listVillageByCoOperativeCode(String selectedCooperative);

	/**
	 * List co operative and samithi by revision no.
	 *
	 * @param revisionNo
	 *            the revision no
	 * @param typez
	 *            the typez
	 * @return the list< warehouse>
	 */
	public List<Warehouse> listCoOperativeAndSamithiByRevisionNo(long revisionNo, int typez);

	/**
	 * List warehouse by village id.
	 *
	 * @param villageId
	 *            the village id
	 * @return the list< warehouse>
	 */
	public List<Warehouse> listWarehouseByVillageId(long villageId);

	/**
	 * List co operative by agent.
	 *
	 * @param agentId
	 *            the agent id
	 * @return the list< warehouse>
	 */
	public List<Warehouse> listCoOperativeByAgent(String agentId);

	/**
	 * List co operative with managers.
	 *
	 * @return the list< warehouse>
	 */
	public List<Warehouse> listCoOperativeWithManagers();

	/**
	 * List of ware house product cooperatives.
	 *
	 * @return the list< warehouse>
	 */
	public List<Warehouse> listOfWareHouseProductCooperatives();

	// public List<Village> listActiveFarmersVillageId(long id);
	/**
	 * Find locality by state id.
	 *
	 * @param selectedState
	 *            the selected state
	 * @return the list< locality>
	 */
	public List<Locality> findLocalityByStateId(long selectedState);

	/**
	 * List gram panchayats by city id.
	 *
	 * @param selectedCity
	 *            the selected city
	 * @return the list< gram panchayat>
	 */
	public List<GramPanchayat> listGramPanchayatsByCityId(long selectedCity);

	/**
	 * List municipalities by locality id.
	 *
	 * @param selectedLocality
	 *            the selected locality
	 * @return the list< municipality>
	 */
	public List<Municipality> listMunicipalitiesByLocalityId(long selectedLocality);

	/**
	 * List villages by panchayat id.
	 *
	 * @param selectedPanchayat
	 *            the selected panchayat
	 * @return the list< village>
	 */
	public List<Village> listVillagesByPanchayatId(long selectedPanchayat);

	/**
	 * Find village by selected village id.
	 *
	 * @param selectedVillage
	 *            the selected village
	 * @return the village
	 */
	public Village findVillageBySelectedVillageId(long selectedVillage);

	// list panchayat by city
	public List<GramPanchayat> listPanchayatBycityId(long selectedCity);

	/**
	 * List of warehouse count.
	 *
	 * @return the long
	 */
	public long listOfWarehouseCount();

	/**
	 * Find samithi by id.
	 *
	 * @param id
	 *            the id
	 * @return the warehouse
	 */
	public Warehouse findSamithiById(long id);

	public List<Village> listVillagesByCityId(long selectedCity);

	public WarehousePayment findCooperativeMappedWithWarhousePayment(long warhouseId);

	public Warehouse findWarehouseByNameAndType(String name, int type);

	//public Map<String, String> listVillageIdAndName();

	public List<Municipality> listMunicipality();

	public List<State> listStatesByCountryID(Long countryID);

	public List<Locality> listLocalitiesByStateID(Long stateId);

	public List<Village> listVillagesByCityID(Long cityId);

	public long listOfWarehouseCountBasedOnBranch(String branchIdValue);

	public List<Municipality> listMunicipalityBasedOnBranch(String branchIdValue);

	public List<Warehouse> listSamithiesBasedOnType();

	public Integer findWarehouseCount();

	public Integer findWarehouseCountByMonth(Date sDate, Date eDate);

	public List<Object> listWarehouseProductAndStock();

	public Village findVillageByCode(String villageCode, String tenantId);

	public Warehouse findSamithiByCode(String samithiCode, String tenantId);

	public Warehouse findCoOperativeByCode(String servicePointId, String tenantId);

	public List<Municipality> listOfCitysByIds(List<Long> municipality);

	public List<Object> listWarehouseProductAndStockByWarehouseId(Long warehouseId);

	public List<Object[]> listCityCodeAndName();

	public List<Object[]> listVillageCodeAndNameByCityCode(String cityCode);

	public List<Warehouse> listSamithiBySangham(String groupCode, String villageCode);

	public List<Object[]> listLocalityIdCodeAndName();

	public List<Object[]> listCityCodeAndNameByDistrictId(Long id);

	public List<Object[]> listGramPanchayatIdCodeName();

	public List<Object[]> listGramPanchayatByCityId(Long id);

	public List<Object[]> listVillageByPanchayatId(Long id);

	public Integer findFarmerCountBySamtihi(Long id);

	public List<Object[]> listOfCooperativesByDistribution();

	public List<Object[]> listOfVillageInfoByProcurement();

	public List<Object[]> listOfCooperativeByProcurement();

	public List<Object[]> listOfWarehouseByStock();

	public List<Object[]> listOfWarehouseByStockEntry();

	public List<Object[]> listOfWarehousePMT();

	public GramPanchayat findGrampanchaythByCode(String code);

	public List<GramPanchayat> listGramPanchayatsByCityCode(String selectedCity);

	public List<Municipality> listMunicipalitiesByCode(String locality);

	public List<GramPanchayat> listGramPanchayatsByCode(String selectedCity);

	public List<State> listStatesByCode(String country);

	public Village findDuplicateVillageName(long id, String name);

	public GramPanchayat findDuplicateGramPanchayatName(long id, String name);

	public Municipality findDuplicateMunicipalityName(long id, String name);

	public List<Warehouse> listSamithiBySanghamType(String groupCode);

	public List<State> listOfStates();

	public List<Object[]> listOfCooperativesByProductReturn();

	public List<Object[]> listWarehouses();

	public List<Object[]> listOfGroup();

	public List<Object[]> listOfAgentsByAgroTxn();

	public List<Object[]> listOfFarmersByAgroTxn();

	public List<Municipality> listCityByProcurement();

	public List<Village> listVillageByProcurementAndCityId(String cityId);

	public Village findVillageByNameAndCity(String village, String city);

	public GramPanchayat findGramPanchayatByNameAndCity(String gp, String city);

	public Municipality findMunicipalityByNameAndDistrict(String taluk, String districtz);

	public DataLevel findDataLevelByName(String name);

	public DataLevel findDataLevelByCode(String code);

	public List<LocationHistory> listDeviceLocationHistoryByAgentId(String agentId, Date startDate, Date endDate,
			String tenantId);

	public Map tableFields();

	public Integer findGroupCount();

	public Integer findGroupCountByMonth(Date sDate, Date eDate);

	public Warehouse findWarehouseById(long id, String tenantId);

	/* public Warehouse findWarehouseWithoutTypeById(Long id); */

	public List<BankInformation> findWarehouseBankinfo(long id);

	public List<Warehouse> listProcurementCenter();

	public Warehouse findWarehouseWithoutTypeById(Long id);

	public List<Object[]> listOfWarehouses();

	public Warehouse findProcurementWarehouseById(long id);

	public List<Warehouse> listCoOperativeAndSamithiByRevisionNo(Long revNo, Integer[] warehousetypesarray);

	public void deleteCultivationDetailByCultivationId(long cultiId, String type);

	public void updateCultivationCost(long id, String cost, String type);

	public List<GMO> listGMOBySeason(String seasonCode);

	public HeapData isHeapSpaceAvailableByIcsCode(String ics);

	public HeapData findHeapDataByICS(String icsCode);

	public boolean isStockMovedToHeap(long pmtDetId);

	public List<Object[]> listOfGiningFromHeap();

	public List<State> listOfStatesByBranch(String selectedBranch);
	
	public List<Object[]> listOfGinningFromHeap(String season);
	
	public List<Object[]> listOfProductFromHeapWithHeap(String selectedGinning, String selectedHeap);
	
	public List<Object[]> listOfICSFromHeapByGinningCodeAndProdut(String ginningCode,String selectedProduct);
	
	public List<Object[]> listOfHeapsByGinningCodeProdutICS(String ginningCode,String season);
	
	public String findHeapQtyByHeapDataId(String selectedHeap);
	
	public HeapData findHeapDataById(String heapId);
	
	public Object[] findHeapQtyICSAndProductByHeapAndGinning(String selectedHeap, String selectedGinning, String selectedProduct,String season);
	
	public List<Object[]> listOfCooperativesByGinningProcess();
	
	public List<Object[]> listOfHeapNameByGinningProcess();
	
	public Double findTxnStockByGinningId(long id);
	
	public List<Object[]> findStockByGinningId(long id);
	
	public Warehouse findWarehouseByCodeAndType(String code, int type);
	
	public List<Village> listVillageByBranch(String selectedBranch,Long selectedState);

	public Integer findFacilitiesCount();
	
	public Warehouse findWarehouseByCodeAndType(String spinnerCode, int type, String tenantId);

	public List<Object[]> findCityNames();

	public List<Object[]> listVillageIdAndName();

	public Village findVillageAndCityByVillName(String otherVi, Long cityId);

	public List<Object[]> listStates();

	public List<Locality> listOfLocalities();
	
	public List<Object[]> listLocalities();
	
	public List<LocationHistory> listDeviceLocationHistoryByAgentIdWithAccuracy(String agentId, Date startDate, Date endDate, Long accuracy );
	
	public List<Object[]> listWarehousesWithoutBranch();
	
	public Warehouse findWarehouseByIdWithoutBranch(long id);

	public List<Object[]> listOfMobileUserByDistribution();
	
	public LocationHistory findLocationHistoryByTxnTimeSerialNoBranch(Date date,String serialNo,String branch);
	
	public List<LocationHistoryDetail> listDeviceLocationHistoryDetailByAgentId(String agentId, Date startDate, Date endDate,
			String tenantId);
	
	public List<LocationHistoryDetail> findLocationHistoryDetailById(long id);
	
	public List<WarehouseStorageMap> listWarehouseStorageMapByWarehouseID(Long warehouseId);
	

	public WarehouseStorageMap findMaxBinHoldByWarehouseIdAndColdStorageName(long id, String coldStorageName);

	public List<Object[]> listWarehouseIdAndName();

	public Warehouse findGinnerByGinningId(Long ginningId);
	
	public List<Object[]> listOfGinningFromBaleGeneration(String seasonsCode);
	
	public List<Object[]> listOfSpinning();
	
	public List<Object[]> listOfHeapByGinningFromBaleGeneration(String selectedGinning,String season);
	
	public Warehouse findGinningById(long id);
	
	public Warehouse findSpinningById(long id);
	
	public Warehouse findWarehouseByIdAndTenantId(long id, String tenantId);
	
	public Village findVillageByIdAndTenant(long id, String tenantId);
	
	public List<Warehouse> listWarehouseByWarehouseIds(String idsArr);
		public List<Country> listCountriesWithAll();
		
		public List<Object[]> findLocalityByBranch(String Branch);
		
		public void updateLocHistory(Long object, String lat, String lon);
}
