package com.data.collect.server.dao;

import java.util.ArrayList;

import java.util.List;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.ParseTplDTO;
import com.data.collect.common.dto.ParseTplCategoryDTO;
import com.general.common.dto.ParseTplItemDTO;
import com.general.common.util.StringTool;
import com.general.server.dao.BaseDAO;


public class ParseTplDAO extends BaseDAO {
	
	private ParseTplItemDAO parseTplItemDAO = new ParseTplItemDAO(); 

	public List<ParseTplDTO> getParseTplList() throws Exception
	{
		return this.getParseTplListBy("", "");
	}
	
	public List<ParseTplDTO> getParseTplListById(int id) throws Exception
	{
		return this.getParseTplListBy(this.BY_ID, String.valueOf(id));
	}
	
	public List<ParseTplDTO> getParseTplListByName(String name) throws Exception
	{
		return this.getParseTplListBy(this.BY_NAME, name);
	}
	
	public List<ParseTplDTO> getParseTplListByCategoryId(int id) throws Exception
	{
		return this.getParseTplListBy(this.BY_CATEGORY_ID, String.valueOf(id));
	}	
	
    private List<ParseTplDTO> getParseTplListBy(String byKey, String byValue) throws Exception
	{
    	this.initStringBuffer();
		if(this.BY_NAME.equals(byKey))
		{
			this.whereBuf.append(" parse_name ");
			this.whereBuf.append(" = '");
			this.whereBuf.append(byValue);
			this.whereBuf.append("'");					
		}else if(this.BY_ID.equals(byKey))
		{
			this.whereBuf.append(" id ");
			this.whereBuf.append(" = ");
			this.whereBuf.append(byValue);
		}else if(this.BY_CATEGORY_ID.equals(byKey))
		{
			this.whereBuf.append(" category_id ");
			this.whereBuf.append(" = ");
			this.whereBuf.append(byValue);
		}   	
		List<ParseTplDTO> ret = new ArrayList<ParseTplDTO>();
		ret = ret.getClass().cast(this.selectDtoList(ParseTplDTO.class));
		if(ret!=null)
		{
			int size = ret.size();
			for(int i=0;i<size;i++)
			{
				ParseTplDTO dto = ret.get(i);
				List<ParseTplItemDTO> ptiDtoList = this.parseTplItemDAO.getParseTplItemListByParseId(dto.getId());
				dto.setParseTplItemList(ptiDtoList);
			}
		}
		return ret;
	}
    
    
	public List<ParseTplCategoryDTO> getParseTplCategoryListByName(String name) throws Exception
	{
		return this.getParseTplCategoryListBy(this.BY_NAME, name);
	}
	
	public List<ParseTplCategoryDTO> getParseTplCategoryListById(String id) throws Exception
	{
		return this.getParseTplCategoryListBy(this.BY_ID, id);
	}
	
	public List<ParseTplCategoryDTO> getParseTplCategoryList() throws Exception
	{
		return this.getParseTplCategoryListBy("", "");
	}
    
	private List<ParseTplCategoryDTO> getParseTplCategoryListBy(String byKey, String byValue) throws Exception
	{
		this.initStringBuffer();
		if(this.BY_NAME.equals(byKey))
		{
			this.whereBuf.append(" cat_name ");
			this.whereBuf.append(" = '");
			this.whereBuf.append(byValue);
			this.whereBuf.append("'");					
		}else if(this.BY_ID.equals(byKey))
		{
			this.whereBuf.append(" id ");
			this.whereBuf.append(" = ");
			this.whereBuf.append(byValue);
		}		
		List<ParseTplCategoryDTO> ret = new ArrayList<ParseTplCategoryDTO>();
		return ret.getClass().cast(this.selectDtoList(ParseTplCategoryDTO.class));
	}


	public void saveParseTplCategory(ParseTplCategoryDTO dto) throws Exception
	{
		this.initStringBuffer();
		if(dto.getId()!=-1)
		{
			this.whereBuf.append(" id = ");
			this.whereBuf.append(dto.getId());
			this.updateDto(dto);
		}else
		{
			this.insertDto(dto);
		}
	}
	
	public void deleteParseTplCategoryByIds(String ids) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" id in (");		
		this.whereBuf.append(StringTool.removeLastCharactor(ids, Constants.SEPERATOR_COMMA));		
		this.whereBuf.append(")");
		this.deleteDto(ParseTplCategoryDTO.class);
	}

	public void deleteParseTplByIds(String ids) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" id in (");
		this.whereBuf.append(StringTool.removeLastCharactor(ids, Constants.SEPERATOR_COMMA));
		this.whereBuf.append(")");
		this.deleteDto(ParseTplDTO.class);
	}
	
	public void saveParseTpl(ParseTplDTO dto) throws Exception
	{
		this.initStringBuffer();
		if(dto.getId()!=-1)
		{
			this.whereBuf.append(" id = ");
			this.whereBuf.append(dto.getId());
			this.updateDto(dto);
		}else
		{
			this.insertDto(dto);
		}
	}
}
