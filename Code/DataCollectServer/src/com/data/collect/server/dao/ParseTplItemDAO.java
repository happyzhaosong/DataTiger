package com.data.collect.server.dao;

import java.util.ArrayList;
import java.util.List;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.ParseTplItemActionDTO;
import com.data.collect.common.dto.ParseTplItemDTO;
import com.data.collect.common.util.StringTool;

public class ParseTplItemDAO extends BaseDAO {

    private List<ParseTplItemDTO> getParseTplItemListBy(String byKey, String byValue) throws Exception
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
		}else if(this.BY_PARSE_TPL_ID.equals(byKey))
		{
			this.whereBuf.append(" parse_id ");
			this.whereBuf.append(" = ");
			this.whereBuf.append(byValue);
		}   	
		
		this.orderByBuf.append(" save_to_column asc ");
		
		List<ParseTplItemDTO> ret = new ArrayList<ParseTplItemDTO>();
		ret = ret.getClass().cast(this.selectDtoList(ParseTplItemDTO.class));		
		return ret;
	}
    
    
	public List<ParseTplItemDTO> getParseTplItemListByParseId(int parseId) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" parse_id ");
		this.whereBuf.append(" = ");
		this.whereBuf.append(parseId);
		
		this.orderByBuf.append(" save_to_column asc ");
		
		List<ParseTplItemDTO> ret = new ArrayList<ParseTplItemDTO>();
		return ret.getClass().cast(this.selectDtoList(ParseTplItemDTO.class));  		
	}
	
	
	public List<ParseTplItemDTO> getParseTplItemListByParseItemId(int parseItemId) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" id ");
		this.whereBuf.append(" = ");
		this.whereBuf.append(parseItemId);
		
		List<ParseTplItemDTO> ret = new ArrayList<ParseTplItemDTO>();
		return ret.getClass().cast(this.selectDtoList(ParseTplItemDTO.class));  		
	}
	
	public List<ParseTplItemActionDTO> getParseTplItemActionListByParseItemId(int parseItemId) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" parse_item_id ");
		this.whereBuf.append(" = ");
		this.whereBuf.append(parseItemId);
		
		this.orderByBuf.append(" id asc ");
		
		List<ParseTplItemDTO> ret = new ArrayList<ParseTplItemDTO>();
		return ret.getClass().cast(this.selectDtoList(ParseTplItemActionDTO.class));
	}
	
	public List<ParseTplItemDTO> getParseTplItemListByParseIdAndItemName(int parseId, String saveToColumnName, String useThisSettingUrlCharactor) throws Exception
	{
		saveToColumnName = StringTool.isEmpty(saveToColumnName, "");
		useThisSettingUrlCharactor = StringTool.isEmpty(useThisSettingUrlCharactor, "");
		
		this.initStringBuffer();
		this.whereBuf.append(" parse_id ");
		this.whereBuf.append(" = ");
		this.whereBuf.append(parseId);
		
		this.whereBuf.append(" and save_to_column ");
		this.whereBuf.append(" = '");
		this.whereBuf.append(saveToColumnName);
		this.whereBuf.append("'");

		this.whereBuf.append(" and use_this_setting_url_charactor ");
		this.whereBuf.append(" = '");
		this.whereBuf.append(useThisSettingUrlCharactor);
		this.whereBuf.append("'");		
		
		this.orderByBuf.append(" save_to_column asc "); 
	    	
		List<ParseTplItemDTO> ret = new ArrayList<ParseTplItemDTO>();
		ret = ret.getClass().cast(this.selectDtoList(ParseTplItemDTO.class));		
		return ret;
	}
	
	public void saveParseTplItem(ParseTplItemDTO dto) throws Exception
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
	
	public void saveParseTplItemAction(ParseTplItemActionDTO dto) throws Exception
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
	
	public void deleteParseTplItemByIds(String ids) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" id in (");
		this.whereBuf.append(StringTool.removeLastCharactor(ids, Constants.SEPERATOR_COMMA));
		this.whereBuf.append(")");
		this.deleteDto(ParseTplItemDTO.class);
	}
	
	public void deleteParseTplItemActionByIds(String ids) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" id in (");
		this.whereBuf.append(StringTool.removeLastCharactor(ids, Constants.SEPERATOR_COMMA));
		this.whereBuf.append(")");
		this.deleteDto(ParseTplItemActionDTO.class);
	}
	
}
