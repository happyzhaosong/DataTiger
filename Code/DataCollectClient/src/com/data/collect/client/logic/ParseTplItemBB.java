package com.data.collect.client.logic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.util.WebTool;
import com.data.collect.server.dao.ParseTplItemDAO;
import com.general.client.logic.BaseBB;
import com.general.common.dto.JsonDTO;
import com.general.common.dto.ParseTplItemActionDTO;
import com.general.common.dto.ParseTplItemDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.JsonTool;
import com.general.common.util.StringTool;

public class ParseTplItemBB extends BaseBB {

	private ParseTplItemDAO parseTplItemDao = new ParseTplItemDAO();	
	
	public JsonDTO saveParseTplItem(HttpServletRequest request) throws Exception
	{
		ParseTplItemDTO dto = (ParseTplItemDTO)ClassTool.extractValueFromRequest(ParseTplItemDTO.class, request);
		StringTool.checkEmpty(dto.getSaveToColumn(), "Parse template item save to column name can not be empty.");			

		
		List<ParseTplItemDTO> dtoList = parseTplItemDao.getParseTplItemListByParseIdAndItemName(dto.getParseId(), dto.getSaveToColumn(), dto.getUseThisSettingUrlCharactor());		
		if(this.ifAnotherDtoObjExist(dtoList, dto.getId()))
		{
			throw new Exception("Parse template item exist, please add another one. Save to column name + Use this setting url charactor should be unique.");
		}		
		
		
		if(StringTool.isEmpty(dto.getDataType()))
		{
			dto.setDataType(Constants.DATA_TYPE_STRING);
		}
		
		parseTplItemDao.saveParseTplItem(dto);
		return JsonTool.getJsonDtoByObj(Constants.JSON_ROOT_PARSE_TEMPLATE_ITEM_LIST, dto);
	}
	
	public JsonDTO saveParseTplItemAction(HttpServletRequest request) throws Exception
	{
		ParseTplItemActionDTO dto = (ParseTplItemActionDTO)ClassTool.extractValueFromRequest(ParseTplItemActionDTO.class, request);
		parseTplItemDao.saveParseTplItemAction(dto);
		return JsonTool.getJsonDtoByObj(Constants.JSON_ROOT_PARSE_TEMPLATE_ITEM_ACTION_LIST, dto);
	}
	
	public JsonDTO getParseTplItemListByParseId(HttpServletRequest request) throws Exception
	{
		String parseId = WebTool.getStringAttributeBeforeParameter(Constants.ID, request);
		StringTool.checkEmpty(parseId, "Please choose a parse template category to list it's parse template.");
		StringTool.checkInteger(parseId, "Parse template category id must be an integer.");
			
		List<ParseTplItemDTO> dtoList = this.parseTplItemDao.getParseTplItemListByParseId(Integer.parseInt(parseId));
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_PARSE_TEMPLATE_ITEM_LIST, dtoList);		
	}
	
	public JsonDTO getParseTplItemActionListByParseItemId(HttpServletRequest request) throws Exception
	{
		String parseItemId = WebTool.getStringAttributeBeforeParameter(Constants.ID, request);
		StringTool.checkEmpty(parseItemId, "Please choose a parse template item to list it's parse template item action.");
		StringTool.checkInteger(parseItemId, "Parse template item id must be an integer.");
			
		List<ParseTplItemActionDTO> dtoList = this.parseTplItemDao.getParseTplItemActionListByParseItemId(Integer.parseInt(parseItemId));
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_PARSE_TEMPLATE_ITEM_ACTION_LIST, dtoList);		
	}
	

	public JsonDTO deleteParseTplItem(HttpServletRequest request) throws Exception
	{
		String delIds = this.getDeleteIdsRetString(request);
		this.parseTplItemDao.deleteParseTplItemByIds(delIds);
		return JsonTool.getJsonDtoByMessage("");
	}
	
	public JsonDTO copyParseTplItem(HttpServletRequest request) throws Exception
	{
		String ids = this.getDataIdsRetString(request);
		
		if(!StringTool.isEmpty(ids))
		{
			String idsArr[] = ids.split(Constants.SEPERATOR_COMMA);
			int size = idsArr.length;
			for(int i=0;i<size;i++)
			{
				String id = idsArr[i];
				List<ParseTplItemDTO> parseTplItemDtoList = this.parseTplItemDao.getParseTplItemListByParseItemId(Integer.parseInt(id));
				
				if(!ClassTool.isListEmpty(parseTplItemDtoList))
				{
					ParseTplItemDTO dto = parseTplItemDtoList.get(0);
					dto.setId(-1);
					dto.setByEleVal(Constants.TEXT_COPY_OF + dto.getByEleVal());
					this.parseTplItemDao.saveParseTplItem(dto);
				}
			}
		}
		return JsonTool.getJsonDtoByMessage("");
	}
	
	public JsonDTO deleteParseTplItemAction(HttpServletRequest request) throws Exception
	{
		String delIds = this.getDeleteIdsRetString(request);
		this.parseTplItemDao.deleteParseTplItemActionByIds(delIds);
		return JsonTool.getJsonDtoByMessage("");
	}
	
}
