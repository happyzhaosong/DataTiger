package com.data.collect.client.logic;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.ParseTplCategoryDTO;
import com.data.collect.common.dto.ParseTplDTO;
import com.data.collect.common.dto.WebSiteDTO;
import com.data.collect.common.util.WebTool;
import com.data.collect.server.dao.ParseTplDAO;
import com.data.collect.server.dao.ParseTplItemDAO;
import com.data.collect.server.dao.WebSiteDAO;
import com.general.client.logic.BaseBB;
import com.general.common.dto.JsonDTO;
import com.general.common.dto.ParseTplItemDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.JsonTool;
import com.general.common.util.StringTool;


public class ParseTplBB extends BaseBB {

	private ParseTplDAO parseTplDao = new ParseTplDAO();
	private ParseTplItemDAO parseTplItemDao = new ParseTplItemDAO();
	private WebSiteDAO webSiteDao = new WebSiteDAO();
	
	public JsonDTO getParseTplListByCategoryId(HttpServletRequest request) throws Exception
	{
		String catId = WebTool.getStringAttributeBeforeParameter(Constants.ID, request);
		StringTool.checkEmpty(catId, "Please choose a parse template category to list it's parse template.");
		StringTool.checkInteger(catId, "Parse template category id must be an integer.");
			
		List<ParseTplDTO> dtoList = this.parseTplDao.getParseTplListByCategoryId(Integer.parseInt(catId));
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_PARSE_TEMPLATE_LIST, dtoList);		
	}
	
	public JsonDTO getAllParseTplList(HttpServletRequest request) throws Exception
	{
		List<ParseTplDTO> dtoList = this.parseTplDao.getParseTplList();		
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_PARSE_TEMPLATE_LIST, dtoList);
	}
	
	public JsonDTO getParseTplCategoryList(HttpServletRequest request) throws Exception
	{
		List<ParseTplCategoryDTO> dtoList = this.parseTplDao.getParseTplCategoryList();
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_PARSE_TEMPLATE_CATEGORY_LIST, dtoList);		
	}
	
	public JsonDTO saveParseTplCategory(HttpServletRequest request) throws Exception
	{
		ParseTplCategoryDTO dto = (ParseTplCategoryDTO)ClassTool.extractValueFromRequest(ParseTplCategoryDTO.class, request);			
		StringTool.checkEmpty(dto.getName(), "Parse template categoty name can not be empty.");
		
		List<ParseTplCategoryDTO> list = parseTplDao.getParseTplCategoryListByName(dto.getName());
		
		if(this.ifAnotherDtoObjExist(list, dto.getId()))
		{
			throw new Exception("Parse template category name exist, please add another one.");
		}
		
		parseTplDao.saveParseTplCategory(dto);		
		return JsonTool.getJsonDtoByObj(Constants.JSON_ROOT_PARSE_TEMPLATE_CATEGORY_LIST, dto); 		
	}
	
	
	public JsonDTO deleteParseTplCategory(HttpServletRequest request) throws Exception
	{
		StringBuffer canDelBuf = new StringBuffer();
		StringBuffer canNotDelBuf = new StringBuffer();
		String delIdArr[] = this.getDeleteIdsRetArray(request);		
		int len = delIdArr.length;
		for(int i=0;i<len;i++)
		{
			String id = delIdArr[i];
			List<ParseTplDTO> list = this.parseTplDao.getParseTplListByCategoryId(Integer.parseInt(id));
			if(list==null || list.size()==0)
			{
				canDelBuf.append(id);
				if(i<(len-1))
				{
					canDelBuf.append(Constants.SEPERATOR_COMMA);
				}
			}else
			{
				canNotDelBuf.append(id);						
				if(i<(len-1))
				{
					canNotDelBuf.append(Constants.SEPERATOR_COMMA);
				}

			}			
		}

		if(canDelBuf.length()>0)
		{
			parseTplDao.deleteParseTplCategoryByIds(canDelBuf.toString());			
		}
		
		if(canNotDelBuf.length()>0)
		{
			return JsonTool.getJsonDtoByCanNotDelIds(canNotDelBuf.toString());
		}else
		{
			return JsonTool.getJsonDtoByMessage("");	
		}	
	}

	
	public JsonDTO copyParseTpl(HttpServletRequest request) throws Exception
	{
		String ids = this.getDataIdsRetString(request);
		if(!StringTool.isEmpty(ids))
		{
			String idArr[] = ids.split(Constants.SEPERATOR_COMMA);
			int len = idArr.length;
			for(int i=0;i<len;i++)
			{
				String id = idArr[i];
				if(StringTool.isInteger(id))
				{
					List<ParseTplDTO> parseTplDtoList = this.parseTplDao.getParseTplListById(Integer.parseInt(id));
					if(!ClassTool.isListEmpty(parseTplDtoList))
					{
						ParseTplDTO parseTplDto = parseTplDtoList.get(0);
						parseTplDto.setId(-1);
						parseTplDto.setName(Constants.TEXT_COPY_OF + parseTplDto.getName());
						this.parseTplDao.saveParseTpl(parseTplDto);
												
						List<ParseTplItemDTO> parseTplItemList = parseTplDto.getParseTplItemList();
						if(!ClassTool.isListEmpty(parseTplItemList))
						{
							int itemSize = parseTplItemList.size();
							for(int j=0;j<itemSize;j++)
							{
								ParseTplItemDTO parseTplItemDto = parseTplItemList.get(j);
								parseTplItemDto.setParseId(parseTplDto.getId());
								parseTplItemDto.setId(-1);								
								this.parseTplItemDao.saveParseTplItem(parseTplItemDto);
							}
						}
					}
				}				
			}
		}
		return JsonTool.getJsonDtoByMessage("");
	}
	
	
	public JsonDTO deleteParseTpl(HttpServletRequest request) throws Exception
	{
		StringBuffer canDelBuf = new StringBuffer();
		StringBuffer canNotDelBuf = new StringBuffer();
		String delIdArr[] = this.getDeleteIdsRetArray(request);
		int len = delIdArr.length;
		for(int i=0;i<len;i++)
		{
			String id = delIdArr[i];
			List<WebSiteDTO> usedWebSiteList = this.webSiteDao.getWebSiteListByParseTplId(Integer.parseInt(id));
			List<ParseTplItemDTO> childParseTplItemList = this.parseTplItemDao.getParseTplItemListByParseId(Integer.parseInt(id));
					
			if((usedWebSiteList==null || usedWebSiteList.size()==0) && (childParseTplItemList==null || childParseTplItemList.size()==0))
			{
				canDelBuf.append(id);						
				if(i<(len-1))
				{
					canDelBuf.append(Constants.SEPERATOR_COMMA);
				}
			}else
			{
				canNotDelBuf.append(id);						
				if(i<(len-1))
				{
					canNotDelBuf.append(Constants.SEPERATOR_COMMA);
				}

			}
		}
			
		if(canDelBuf.length()>0)
		{
			this.parseTplDao.deleteParseTplByIds(canDelBuf.toString());
				
		}
		
		if(canNotDelBuf.length()>0)
		{
			return JsonTool.getJsonDtoByCanNotDelIds(canNotDelBuf.toString()); 
		}else
		{
			return JsonTool.getJsonDtoByMessage("");
		}			
	}

	
	public JsonDTO saveParseTpl(HttpServletRequest request) throws Exception
	{
		ParseTplDTO dto = (ParseTplDTO)ClassTool.extractValueFromRequest(ParseTplDTO.class, request);
		StringTool.checkEmpty(dto.getName(), "Parse template name can not be empty.");
		
		List<ParseTplDTO> list = this.parseTplDao.getParseTplListByName(dto.getName());
		
		if(this.ifAnotherDtoObjExist(list, dto.getId()))
		{
			throw new Exception("Parse template name exist, please add another one.");
		}
		
		this.parseTplDao.saveParseTpl(dto);		
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_PARSE_TEMPLATE_LIST, this.parseTplDao.getParseTplListById(dto.getId())); 	
	}
}
