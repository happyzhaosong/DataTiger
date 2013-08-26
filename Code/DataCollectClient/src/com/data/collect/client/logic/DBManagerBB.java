package com.data.collect.client.logic;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.BasePageDTO;
import com.data.collect.common.dto.DBTableColumnDTO;
import com.data.collect.common.dto.JsonDTO;
import com.data.collect.common.dto.TypeDTO;
import com.data.collect.common.util.ClassTool;
import com.data.collect.common.util.JsonTool;
import com.data.collect.common.util.StringTool;
import com.data.collect.common.util.WebTool;
import com.data.collect.server.dao.DBManagerDAO;
import com.data.collect.server.dao.DownloadTaskDAO;
import com.data.collect.server.manager.DBManager;

public class DBManagerBB extends BaseBB {
	
	private DBManagerDAO dbManagerDao = new DBManagerDAO();
	private DownloadTaskDAO downloadTaskDao = new DownloadTaskDAO();

	public JsonDTO getDataTableListInJson(HttpServletRequest request) throws Exception {
		BasePageDTO pageDto = (BasePageDTO)ClassTool.extractValueFromRequest(BasePageDTO.class, request);					
		this.dbManagerDao.setPageDto(pageDto);
		
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_DATA_TABLE_LIST, dbManagerDao.getDataTableList());
	}
	
	
	public JsonDTO getDataTableDataListInJson(HttpServletRequest request) throws Exception {
		String tableName = WebTool.getStringParameterBeforeAttribute(Constants.DATA_DB_TABLE_NAME, request);
		StringTool.checkEmpty(tableName, "Please choose a data table then get it's data list.");

		BasePageDTO pageDto = (BasePageDTO)ClassTool.extractValueFromRequest(BasePageDTO.class, request);					
		this.dbManagerDao.setPageDto(pageDto);
		
		List<List<DBTableColumnDTO>> retDataColumnList = dbManagerDao.getDataTableDataList(tableName);
				
		String actionSub = WebTool.getStringParameterBeforeAttribute(Constants.ACTION_SUB, request);

		if(!StringTool.isEmpty(actionSub))
		{
			if(Constants.RESET_ALL.equalsIgnoreCase(actionSub))
			{
				String resetApplyTime = WebTool.getStringParameterBeforeAttribute(Constants.RESET_APPLY_TIME, request);

				String taskLevel = WebTool.getStringParameterBeforeAttribute(Constants.DOWNLOAD_TASK_LEVEL, request);
				if(!StringTool.isInteger(taskLevel))
				{
					taskLevel = "0";
				}
					
				String downloadTaskIdInWhereSql = "select download_task_id from " + tableName;
				String whereClause = dbManagerDao.getWhereBuf().toString().trim();
				if(whereClause.length()>0)
				{
					downloadTaskIdInWhereSql = downloadTaskIdInWhereSql + " where " + whereClause + " and download_task_level != '" + taskLevel + "'";
				}
				//reset all search condition result apply time to '' and task level to user input task level.
				String updateDownloadTaskIds = downloadTaskDao.resetDownloadTaskApplyTimeAndLevelByWebSiteId(downloadTaskIdInWhereSql, taskLevel, resetApplyTime);
				while(!StringTool.isEmpty(updateDownloadTaskIds))
				{
					String updateSql = "update "+tableName+" set download_task_level = '" + taskLevel + "' where download_task_id in(" + updateDownloadTaskIds + ")";
					dbManagerDao.executeUpdateOrDeleteSql(updateSql, DBManager.getInstance().getMysqlDataSource());
					
					updateDownloadTaskIds = downloadTaskDao.resetDownloadTaskApplyTimeAndLevelByWebSiteId(downloadTaskIdInWhereSql, taskLevel, resetApplyTime);
				}
			}else if(Constants.DELETE_ALL.equalsIgnoreCase(actionSub))
			{
				String deleteSql = "delete from " + tableName;
				String whereClause = dbManagerDao.getWhereBuf().toString().trim();
				if(whereClause.length()>0)
				{
					deleteSql = deleteSql + " where " + whereClause;
				}
				dbManagerDao.executeUpdateOrDeleteSql(deleteSql, DBManager.getInstance().getMysqlDataSource());				
			}
		}
		
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_DATA_TABLE_DATA_LIST, retDataColumnList);
	}
	
	
	public JsonDTO getDataTableColumnListInJson(HttpServletRequest request) throws Exception {
		String tableName = WebTool.getStringParameter(Constants.DATA_DB_TABLE_NAME, request);
		StringTool.checkEmpty(tableName, "Please choose a table then get it's column list.");
		
		BasePageDTO pageDto = (BasePageDTO)ClassTool.extractValueFromRequest(BasePageDTO.class, request);					
		this.dbManagerDao.setPageDto(pageDto);
		
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_DATA_TABLE_COLUMN_LIST, dbManagerDao.getDataTableColumnList(tableName));
	}
	
	public JsonDTO getDataTypeList(HttpServletRequest request) throws Exception
	{
		List<TypeDTO> dtoList = new ArrayList<TypeDTO>();
		String byTypeNameArr[] = new String[5];
		byTypeNameArr[0] = Constants.DATA_TYPE_STRING;
		byTypeNameArr[1] = Constants.DATA_TYPE_NUMBER;
		byTypeNameArr[2] = Constants.DATA_TYPE_BOOLEAN;
		//byTypeNameArr[3] = Constants.DATA_TYPE_DATE;
		//byTypeNameArr[4] = Constants.DATA_TYPE_DATE_TIME;
		
		String byTypeLabelArr[] = new String[5];
		byTypeLabelArr[0] = "String";
		byTypeLabelArr[1] = "Number";
		byTypeLabelArr[2] = "Boolean";
		//byTypeLabelArr[3] = "Date";
		//byTypeLabelArr[4] = "Date Time";

		
		int size = byTypeNameArr.length;
		for(int i=0;i<size;i++)
		{
			TypeDTO dto = new TypeDTO();
			dto.setTypeName(byTypeNameArr[i]);
			dto.setTypeLabel(byTypeLabelArr[i]);
			dtoList.add(dto);
		}
		
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_DATA_TYPE_LIST, dtoList);
	}
}
