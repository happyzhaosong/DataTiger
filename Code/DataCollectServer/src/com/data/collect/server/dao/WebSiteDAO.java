package com.data.collect.server.dao;

import java.util.ArrayList;
import java.util.List;
import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.WebSiteCategoryDTO;
import com.data.collect.common.dto.WebSiteContentPageCheckDTO;
import com.data.collect.common.dto.WebSiteDTO;
import com.data.collect.common.dto.WebSiteLoginAccountDTO;
import com.data.collect.common.dto.WebSitePageLinkParseDTO;
import com.general.common.dto.ParseTplItemDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.StringTool;
import com.general.server.dao.BaseDAO;

public class WebSiteDAO extends BaseDAO {
	
	private ParseTplItemDAO parseTplItemDao = new ParseTplItemDAO();

	public List<WebSiteCategoryDTO> getWebSiteCategoryListByName(String name) throws Exception
	{
		return this.getWebSiteCategoryListBy(BY_NAME, name);		
	}
	
	public List<WebSiteCategoryDTO> getWebSiteCategoryListById(int id) throws Exception
	{
		return this.getWebSiteCategoryListBy(BY_ID, String.valueOf(id));		
	}
	
	public List<WebSiteCategoryDTO> getWebSiteCategoryList() throws Exception
	{
		List<WebSiteCategoryDTO> list = this.getWebSiteCategoryListBy("", "");
		return list;
	}
	 
	private List<WebSiteCategoryDTO> getWebSiteCategoryListBy(String byKey, String byValue) throws Exception
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
		List<WebSiteCategoryDTO> ret = new ArrayList<WebSiteCategoryDTO>();
		return ret.getClass().cast(this.selectDtoList(WebSiteCategoryDTO.class));		
	}

	
	public void saveWebSiteCategory(WebSiteCategoryDTO dto) throws Exception
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
	
	
	public void saveWebSitePageLinkParseItem(WebSitePageLinkParseDTO dto) throws Exception
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
	
	
	public void saveWebSiteContentPageCheckItem(WebSiteContentPageCheckDTO dto) throws Exception
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
	
	
	public void saveWebSiteLoginAccountItem(WebSiteLoginAccountDTO dto) throws Exception
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
	
		
	public void deleteWebSiteCategoryByIds(String ids) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" id in (");
		this.whereBuf.append(StringTool.removeLastCharactor(ids, Constants.SEPERATOR_COMMA));
		this.whereBuf.append(")");
		this.deleteDto(WebSiteCategoryDTO.class);
	}
	
	public void deleteWebSiteByIds(String ids) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" id in (");
		this.whereBuf.append(StringTool.removeLastCharactor(ids, Constants.SEPERATOR_COMMA));
		this.whereBuf.append(")");
		this.deleteDto(WebSiteDTO.class);
	}

	
	public void deleteWebSitePageLinkParseItemByIds(String ids) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" id in (");
		this.whereBuf.append(StringTool.removeLastCharactor(ids, Constants.SEPERATOR_COMMA));
		this.whereBuf.append(")");
		this.deleteDto(WebSitePageLinkParseDTO.class);
	}
	
	
	public void deleteWebSiteContentPageCheckItemByIds(String ids) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" id in (");
		this.whereBuf.append(StringTool.removeLastCharactor(ids, Constants.SEPERATOR_COMMA));
		this.whereBuf.append(")");
		this.deleteDto(WebSiteContentPageCheckDTO.class);
	}
	
	
	public void deleteWebSiteLoginAccountItemByIds(String ids) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" id in (");
		this.whereBuf.append(StringTool.removeLastCharactor(ids, Constants.SEPERATOR_COMMA));
		this.whereBuf.append(")");
		this.deleteDto(WebSiteLoginAccountDTO.class);
	}
        
	public void saveWebSite(WebSiteDTO dto) throws Exception
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
	
    public List<WebSiteDTO> getWebSiteListByCategoryId(int id) throws Exception
	{
    	return this.getWebSiteListBy(BY_CATEGORY_ID, String.valueOf(id));
	}
    
    public List<WebSiteDTO> getWebSiteListByCategoryIdAndName(int id, String name) throws Exception
	{
    	return this.getWebSiteListBy(this.BY_CATEGORY_ID_AND_NAME, String.valueOf(id) + Constants.SEPERATOR_SEMICOLON + name);
	}
    
    public List<WebSiteDTO> getWebSiteListByName(String siteName) throws Exception
	{
    	return this.getWebSiteListBy(this.BY_NAME, siteName);
	}
    
    
    public List<WebSiteDTO> getWebSiteListById(int id) throws Exception
	{
    	return this.getWebSiteListBy(this.BY_ID, String.valueOf(id));
	}
    
    public WebSiteDTO getWebSiteById(int id) throws Exception
	{
    	WebSiteDTO retDto = null;
    	List<WebSiteDTO> webSiteDtoList = this.getWebSiteListById(id);
		if(webSiteDtoList!=null && webSiteDtoList.size()>0)
		{
			retDto = webSiteDtoList.get(0);
		}
		return retDto;
	}
    
    public List<WebSiteDTO> getWebSiteListByParseTplId(int id) throws Exception
	{
    	return this.getWebSiteListBy(this.BY_PARSE_TPL_ID, String.valueOf(id));
	}
    
    
    private List<WebSiteDTO> getWebSiteListBy(String byKey, String byValue) throws Exception
	{    	
    	this.initStringBuffer();
		if(this.BY_NAME.equals(byKey))
		{
			this.whereBuf.append(" site_name ");
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
		}else if(this.BY_PARSE_TPL_ID.equals(byKey))
		{
			this.whereBuf.append(" parse_id ");
			this.whereBuf.append(" = ");
			this.whereBuf.append(byValue);
		}else if(this.BY_CATEGORY_ID_AND_NAME.equals(byKey))
		{
			String valArr[] = StringTool.splitString(byValue, Constants.SEPERATOR_SEMICOLON);
			if(valArr.length==2)
			{
				this.whereBuf.append(" category_id ");
				this.whereBuf.append(" = ");
				this.whereBuf.append(valArr[0]);
				
				this.whereBuf.append(" and site_name ");
				this.whereBuf.append(" = '");
				this.whereBuf.append(valArr[1]);
				this.whereBuf.append("'");
			}else
			{
				throw new Exception("Parameter number is "+valArr.length+", this is not correct for search web site by category id and site name.");
			}
		}
		List<WebSiteDTO> ret = new ArrayList<WebSiteDTO>();
		ret = ret.getClass().cast(this.selectDtoList(WebSiteDTO.class));
		if(ret!=null)
		{
			int size = ret.size();
			for(int i=0;i<size;i++)
			{
				WebSiteDTO dto = ret.get(i);
				
				//this way can reduce get page link parse list time
				List<WebSitePageLinkParseDTO> tmpList = this.getWebSitePageLinkParseListByWebSiteId(dto.getId());				
				dto.setPageLinkParseDtoList(tmpList);

				//this way can reduce get content page check list time
				List<WebSiteContentPageCheckDTO> cpcList = this.getWebSiteContentPageCheckListByWebSiteId(dto.getId());				
				dto.setContentPageCheckDtoList(cpcList);
				
				//this way can reduce get login account list time
				List<WebSiteLoginAccountDTO> tmpList1 = this.getWebSiteLoginAccountListByWebSiteId(dto.getId());
				dto.setSiteLoginAccountDtoList(tmpList1);	
				
				int parseId = dto.getParseId();
				if(parseId >= 0)
				{
					//this way can reduce get parse item list time
					List<ParseTplItemDTO> parseItemList = this.parseTplItemDao.getParseTplItemListByParseId(parseId);
					dto.setParseItemList(parseItemList);
				}
			}
		}
		return ret;
	}
    
    /*
    private List<WebSitePageLinkParseDTO> getWebSitePageLinkParseListBy(String byKey, String byValue) throws Exception
	{    	
    	this.initStringBuffer();
    	if(this.BY_ID.equals(byKey))
		{
			this.whereBuf.append(" id ");
			this.whereBuf.append(" = ");
			this.whereBuf.append(byValue);
		}else if(this.BY_WEB_SITE_ID.equals(byKey))
		{
			this.whereBuf.append(" site_id ");
			this.whereBuf.append(" = ");
			this.whereBuf.append(byValue);
		}   	
		List<WebSitePageLinkParseDTO> ret = new ArrayList<WebSitePageLinkParseDTO>();
		return ret.getClass().cast(this.selectDtoList(WebSitePageLinkParseDTO.class));	
	}
	*/
    
    private List<WebSiteLoginAccountDTO> getWebSiteLoginAccountListBy(String byKey, String byValue) throws Exception
	{    	
    	this.initStringBuffer();
    	if(this.BY_ID.equals(byKey))
		{
			this.whereBuf.append(" id ");
			this.whereBuf.append(" = ");
			this.whereBuf.append(byValue);
		}else if(this.BY_WEB_SITE_ID.equals(byKey))
		{
			this.whereBuf.append(" site_id ");
			this.whereBuf.append(" = ");
			this.whereBuf.append(byValue);
		}   	
		List<WebSiteLoginAccountDTO> ret = new ArrayList<WebSiteLoginAccountDTO>();
		return ret.getClass().cast(this.selectDtoList(WebSiteLoginAccountDTO.class));	
	}
    
    public List<WebSiteContentPageCheckDTO> getWebSiteContentPageCheckListByWebSiteId(int id) throws Exception
	{
    	this.initStringBuffer();
		this.whereBuf.append(" site_id ");
		this.whereBuf.append(" = ");
		this.whereBuf.append(id);
		List<WebSiteContentPageCheckDTO> ret = new ArrayList<WebSiteContentPageCheckDTO>();
		return ret.getClass().cast(this.selectDtoList(WebSiteContentPageCheckDTO.class));
	}
    
    
    public List<WebSitePageLinkParseDTO> getWebSitePageLinkParseListByWebSiteId(int id) throws Exception
	{
    	this.initStringBuffer();
		this.whereBuf.append(" site_id ");
		this.whereBuf.append(" = ");
		this.whereBuf.append(id);
		List<WebSitePageLinkParseDTO> ret = new ArrayList<WebSitePageLinkParseDTO>();
		return ret.getClass().cast(this.selectDtoList(WebSitePageLinkParseDTO.class));
	}
    
    
    public List<WebSiteLoginAccountDTO> getWebSiteLoginAccountListByWebSiteId(int id) throws Exception
	{
    	return this.getWebSiteLoginAccountListBy(this.BY_WEB_SITE_ID, String.valueOf(id));
	}
    
    public WebSitePageLinkParseDTO getWebSitePageLinkParseDtoById(int id) throws Exception
	{
    	List<WebSitePageLinkParseDTO> list = null;
    	
    	this.initStringBuffer();
		this.whereBuf.append(" id ");
		this.whereBuf.append(" = ");
		this.whereBuf.append(id);
		List<WebSitePageLinkParseDTO> ret = new ArrayList<WebSitePageLinkParseDTO>();
		list = ret.getClass().cast(this.selectDtoList(WebSitePageLinkParseDTO.class));
    	
    	if(!ClassTool.isListEmpty(list))
    	{
    		return list.get(0);
    	}else
    	{
    		return new WebSitePageLinkParseDTO();
    	}
	}
    
    public WebSiteLoginAccountDTO getWebSiteLoginAccountDtoById(int id) throws Exception
	{
    	List<WebSiteLoginAccountDTO> list = this.getWebSiteLoginAccountListBy(this.BY_ID, String.valueOf(id));
    	if(list!=null && list.size()>0)
    	{
    		return list.get(0);
    	}else
    	{
    		return new WebSiteLoginAccountDTO();
    	}
	}
}
