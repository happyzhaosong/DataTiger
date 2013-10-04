package com.data.collect.client.logic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.WebSiteCategoryDTO;
import com.data.collect.common.dto.WebSiteContentPageCheckDTO;
import com.data.collect.common.dto.WebSiteDTO;
import com.data.collect.common.dto.WebSiteLoginAccountDTO;
import com.data.collect.common.dto.WebSitePageLinkParseDTO;
import com.data.collect.common.util.WebTool;
import com.data.collect.server.dao.WebSiteDAO;
import com.general.client.logic.BaseBB;
import com.general.common.dto.JsonDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.JsonTool;
import com.general.common.util.LogTool;
import com.general.common.util.StringTool;

public class WebSiteBB extends BaseBB {

	private WebSiteDAO webSiteDao = new WebSiteDAO();
	
	public JsonDTO saveWebSite(HttpServletRequest request) throws Exception
	{
		WebSiteDTO dto = (WebSiteDTO)ClassTool.extractValueFromRequest(WebSiteDTO.class, request);	
		StringTool.checkEmpty(dto.getName(), "Web site name can not be empty.");
		StringTool.checkEmpty(dto.getTopUrl(), "Web site top urls can not be empty.");
		StringTool.checkEmpty(String.valueOf(dto.getParseId()), "Web site must have one parse template.");
			
		List<WebSiteDTO> dtoList = getWebSiteDtoInCat(dto.getCategoryId(),dto.getName());		
		if(this.ifAnotherDtoObjExist(dtoList, dto.getId()))
		{
			throw new Exception("Web site name exist, please add another one.");
		}
		
		webSiteDao.saveWebSite(dto);		
		return JsonTool.getJsonDtoByObj(Constants.JSON_ROOT_SITE_LIST, dto);
	}
	
		
	public JsonDTO getWebSiteListByCategoryId(HttpServletRequest request) throws Exception
	{
		WebSiteCategoryDTO catDto = (WebSiteCategoryDTO)ClassTool.extractValueFromRequest(WebSiteCategoryDTO.class, request);
		StringTool.checkEmpty(String.valueOf(catDto.getId()), "Please choose a web site category to  list it's web site.");
		StringTool.checkInteger(String.valueOf(catDto.getId()), "Web site category id must be an integer.");
		List<WebSiteDTO> dtoList = this.webSiteDao.getWebSiteListByCategoryId(catDto.getId());
		
		if(!ClassTool.isListEmpty(dtoList))
		{
			int size = dtoList.size();
			for(int i=0;i<size;i++)
			{
				WebSiteDTO dto = dtoList.get(i);
				dto.setParseItemList(null);
			}
		}
		
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_SITE_LIST, dtoList);
	}
	
	public JsonDTO getWebSiteListByCategoryIdAndName(HttpServletRequest request) throws Exception
	{
		WebSiteDTO siteDto = (WebSiteDTO)ClassTool.extractValueFromRequest(WebSiteDTO.class, request);			
		StringTool.checkEmpty(String.valueOf(siteDto.getCategoryId()), "Please choose a web site category to list it's web site.");
		StringTool.checkInteger(String.valueOf(siteDto.getCategoryId()), "Web site category id must be an integer.");
		List<WebSiteDTO> dtoList = this.webSiteDao.getWebSiteListByCategoryIdAndName(siteDto.getCategoryId(), siteDto.getName());
	
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_SITE_LIST, dtoList);
	}

	
	public JsonDTO getWebSiteLoginAccountListBySiteId(HttpServletRequest request) throws Exception
	{
		String webSiteId = WebTool.getStringParameter(Constants.ID, request);
		StringTool.checkEmpty(webSiteId, "Web site id can not be empty.");
		StringTool.checkInteger(webSiteId, "Web site id should be an integer.");
		List<WebSiteLoginAccountDTO> dtoList = this.webSiteDao.getWebSiteLoginAccountListByWebSiteId(Integer.parseInt(webSiteId));
	
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_SITE_LOGIN_ACCOUNT_LIST, dtoList);
	}

	
	public JsonDTO getWebSitePageLinkParseListBySiteId(HttpServletRequest request) throws Exception
	{	
		String webSiteId = WebTool.getStringParameter(Constants.ID, request);
		StringTool.checkEmpty(webSiteId, "Web site id can not be empty.");
		StringTool.checkInteger(webSiteId, "Web site id should be an integer.");
		List<WebSitePageLinkParseDTO> dtoList = this.webSiteDao.getWebSitePageLinkParseListByWebSiteId(Integer.parseInt(webSiteId));

		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_SITE_PAGE_LINK_PARSE_LIST, dtoList);
	}
	
	
	public JsonDTO getWebSiteContentPageCheckListBySiteId(HttpServletRequest request) throws Exception
	{	
		String webSiteId = WebTool.getStringParameter(Constants.ID, request);
		StringTool.checkEmpty(webSiteId, "Web site id can not be empty.");
		StringTool.checkInteger(webSiteId, "Web site id should be an integer.");
		List<WebSiteContentPageCheckDTO> dtoList = this.webSiteDao.getWebSiteContentPageCheckListByWebSiteId(Integer.parseInt(webSiteId));

		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_SITE_CONTENT_PAGE_CHECK_LIST, dtoList);
	}
	
	public JsonDTO getWebSiteCategoryList(HttpServletRequest request) throws Exception
	{	
		List<WebSiteCategoryDTO> dtoList = this.webSiteDao.getWebSiteCategoryList();
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_SITE_CATEGORY_LIST, dtoList);
	}
	
	
	public JsonDTO saveWebSiteCategory(HttpServletRequest request) throws Exception
	{
		WebSiteCategoryDTO dto = (WebSiteCategoryDTO)ClassTool.extractValueFromRequest(WebSiteCategoryDTO.class, request);			
		StringTool.checkEmpty(dto.getName(), "Web site categoty name can not be empty.");
		
		List<WebSiteCategoryDTO> list = webSiteDao.getWebSiteCategoryListByName(dto.getName());
		if(this.ifAnotherDtoObjExist(list, dto.getId()))		
		{
			throw new Exception("Web site category name exist, please add another one.");
		}
		webSiteDao.saveWebSiteCategory(dto);		
		return JsonTool.getJsonDtoByObj(Constants.JSON_ROOT_SITE_CATEGORY_LIST, dto);		 		
	}
	
	
	public JsonDTO saveWebSitePageLinkParseItem(HttpServletRequest request) throws Exception
	{
		WebSitePageLinkParseDTO dto = (WebSitePageLinkParseDTO)ClassTool.extractValueFromRequest(WebSitePageLinkParseDTO.class, request);			
		webSiteDao.saveWebSitePageLinkParseItem(dto);
		
		return JsonTool.getJsonDtoByObj(Constants.JSON_ROOT_SITE_PAGE_LINK_PARSE_LIST, dto);		
	}
	
	
	public JsonDTO saveWebSiteContentPageCheckItem(HttpServletRequest request) throws Exception
	{
		WebSiteContentPageCheckDTO dto = (WebSiteContentPageCheckDTO)ClassTool.extractValueFromRequest(WebSiteContentPageCheckDTO.class, request);			
		webSiteDao.saveWebSiteContentPageCheckItem(dto);
		
		return JsonTool.getJsonDtoByObj(Constants.JSON_ROOT_SITE_CONTENT_PAGE_CHECK_LIST, dto);		
	}
	
	
	public JsonDTO saveWebSiteLoginAccountItem(HttpServletRequest request) throws Exception
	{
		WebSiteLoginAccountDTO dto = (WebSiteLoginAccountDTO)ClassTool.extractValueFromRequest(WebSiteLoginAccountDTO.class, request);
		StringTool.checkEmpty(dto.getLoginPageUrl(), "Login account login page url can not be empty.");
		webSiteDao.saveWebSiteLoginAccountItem(dto);
		
		return JsonTool.getJsonDtoByObj(Constants.JSON_ROOT_SITE_LOGIN_ACCOUNT_LIST, dto);
	}

	
	public JsonDTO deleteWebSitePageLinkParseItem(HttpServletRequest request) throws Exception
	{
		String delIds = this.getDeleteIdsRetString(request);
		webSiteDao.deleteWebSitePageLinkParseItemByIds(delIds);		
		return JsonTool.getJsonDtoByMessage("");   
	}
	
	
	public JsonDTO copyWebSitePageLinkParseItem(HttpServletRequest request) throws Exception
	{
		String ids = this.getDataIdsRetString(request);
		if(!StringTool.isEmpty(ids))
		{
			String idArr[] = ids.split(Constants.SEPERATOR_COMMA);
			int size = idArr.length;
			for(int i=0;i<size;i++)
			{
				String id = idArr[i];
				if(StringTool.isInteger(id))
				{
					WebSitePageLinkParseDTO dto = webSiteDao.getWebSitePageLinkParseDtoById(Integer.parseInt(id));
					dto.setId(-1);
					dto.setByEleVal(Constants.TEXT_COPY_OF + dto.getByEleVal());
					webSiteDao.saveWebSitePageLinkParseItem(dto);
				}
			}
		}		
		return JsonTool.getJsonDtoByMessage("");   
	}
	
	
	public JsonDTO deleteWebSiteContentPageCheckItem(HttpServletRequest request) throws Exception
	{
		String delIds = this.getDeleteIdsRetString(request);
		webSiteDao.deleteWebSiteContentPageCheckItemByIds(delIds);		
		return JsonTool.getJsonDtoByMessage("");
	}
	
	public JsonDTO deleteWebSiteLoginAccountItem(HttpServletRequest request) throws Exception
	{
		String delIds = this.getDeleteIdsRetString(request);
		webSiteDao.deleteWebSiteLoginAccountItemByIds(delIds);		
		return JsonTool.getJsonDtoByMessage("");
	}
	
	
	public JsonDTO deleteWebSiteCategory(HttpServletRequest request) throws Exception
	{	
		StringBuffer canDelBuf = new StringBuffer();
		StringBuffer canNotDelBuf = new StringBuffer();
		String delIdArr[] = this.getDeleteIdsRetArray(request);		
		int len = delIdArr.length;
		for(int i=0;i<len;i++)
		{
			String id = delIdArr[i];
			List<WebSiteDTO> list = webSiteDao.getWebSiteListByCategoryId(Integer.parseInt(id));
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
			webSiteDao.deleteWebSiteCategoryByIds(canDelBuf.toString());
		}
		
		if(canNotDelBuf.length()>0)
		{
			return JsonTool.getJsonDtoByCanNotDelIds(canNotDelBuf.toString());
		}else
		{
			return JsonTool.getJsonDtoByMessage("");
		}
	}
	
	public JsonDTO deleteWebSite(HttpServletRequest request) throws Exception
	{			
		StringBuffer canDelBuf = new StringBuffer();
		StringBuffer canNotDelBuf = new StringBuffer();
		String delIdArr[] = this.getDeleteIdsRetArray(request);
		int len = delIdArr.length;
		for(int i=0;i<len;i++)
		{
			String id = delIdArr[i];
			List<WebSiteLoginAccountDTO> siteLoginAccountList = webSiteDao.getWebSiteLoginAccountListByWebSiteId(Integer.parseInt(id));
			List<WebSitePageLinkParseDTO> sitePageLinkList = webSiteDao.getWebSitePageLinkParseListByWebSiteId(Integer.parseInt(id));
					
			if((siteLoginAccountList==null || siteLoginAccountList.size()==0) && (sitePageLinkList==null || sitePageLinkList.size()==0))
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
			webSiteDao.deleteWebSiteByIds(canDelBuf.toString());	
		}
		
		if(canNotDelBuf.length()>0)
		{
			return JsonTool.getJsonDtoByCanNotDelIds(canNotDelBuf.toString());
		}else
		{
			return JsonTool.getJsonDtoByMessage("");
		}
	}	
	
	
	public JsonDTO copyWebSiteCategory(HttpServletRequest request) throws Exception
	{			
		String ids = this.getDataIdsRetString(request);
		if(!StringTool.isEmpty(ids))
		{
			String idArr[] = ids.split(Constants.SEPERATOR_COMMA);
			int len = idArr.length;
			for(int i=0;i<len;i++)
			{
				String id = idArr [i];				
				List<WebSiteCategoryDTO> webSiteCatDtoList = webSiteDao.getWebSiteCategoryListById(Integer.parseInt(id));
				if(!ClassTool.isListEmpty(webSiteCatDtoList))
				{
					WebSiteCategoryDTO catDto = webSiteCatDtoList.get(0);
					int srcCatId = catDto.getId();
					catDto.setId(-1);
					catDto.setName(Constants.TEXT_COPY_OF + catDto.getName() + System.currentTimeMillis());
					this.webSiteDao.saveWebSiteCategory(catDto);
					int newCatId = catDto.getId();
					
					List<WebSiteDTO> dtoList = this.webSiteDao.getWebSiteListByCategoryId(srcCatId);
					this.copyWebSiteDtoList(dtoList, newCatId);
				}						
			}
		}
		
		return JsonTool.getJsonDtoByMessage("");		
	}	

	
	public JsonDTO copyWebSite(HttpServletRequest request) throws Exception
	{			
		String ids = this.getDataIdsRetString(request);
		if(!StringTool.isEmpty(ids))
		{
			String idArr[] = ids.split(Constants.SEPERATOR_COMMA);
			int len = idArr.length;
			for(int i=0;i<len;i++)
			{
				String id = idArr [i];
				WebSiteDTO webSiteDto = webSiteDao.getWebSiteById(Integer.parseInt(id));
				this.copyWebSiteDto(webSiteDto, webSiteDto.getCategoryId());		
			}
		}
		
		return JsonTool.getJsonDtoByMessage("");		
	}	
	
	
	private void copyWebSiteDto(WebSiteDTO webSiteDto, int newWebSiteCatId) throws Exception
	{
		webSiteDto.setCategoryId(newWebSiteCatId);
		webSiteDto.setId(-1);
		webSiteDto.setName(Constants.TEXT_COPY_OF + webSiteDto.getName() + System.currentTimeMillis());		
		this.webSiteDao.saveWebSite(webSiteDto);
		
		List<WebSiteLoginAccountDTO> siteLoginAccountList = webSiteDto.getSiteLoginAccountDtoList();
		if(!ClassTool.isListEmpty(siteLoginAccountList))
		{
			int laSize = siteLoginAccountList.size();
			for(int ila=0; ila<laSize; ila++)
			{
				WebSiteLoginAccountDTO laDto = siteLoginAccountList.get(ila);
				laDto.setId(-1);
				laDto.setSiteId(webSiteDto.getId());
				this.webSiteDao.saveWebSiteLoginAccountItem(laDto);
			}
		}
		
		List<WebSitePageLinkParseDTO> sitePageLinkList = webSiteDto.getPageLinkParseDtoList();
		if(!ClassTool.isListEmpty(sitePageLinkList))
		{
			int plSize = sitePageLinkList.size();
			for(int ipl=0; ipl<plSize; ipl++)
			{
				WebSitePageLinkParseDTO plDto = sitePageLinkList.get(ipl);
				plDto.setId(-1);
				plDto.setSiteId(webSiteDto.getId());
				this.webSiteDao.saveWebSitePageLinkParseItem(plDto);
			}
		}

		List<WebSiteContentPageCheckDTO> siteContentPageCheckList = webSiteDto.getContentPageCheckDtoList();
		if(!ClassTool.isListEmpty(siteContentPageCheckList))
		{
			int cpcSize = siteContentPageCheckList.size();
			for(int icpc=0; icpc<cpcSize; icpc++)
			{
				WebSiteContentPageCheckDTO cpcDto = siteContentPageCheckList.get(icpc);
				cpcDto.setId(-1);
				cpcDto.setSiteId(webSiteDto.getId());
				this.webSiteDao.saveWebSiteContentPageCheckItem(cpcDto);
			}
		}	
	}
	
	
	private void copyWebSiteDtoList(List<WebSiteDTO> webSiteDtoList, int newWebSiteCatId) throws Exception
	{
		if(!ClassTool.isListEmpty(webSiteDtoList))
		{
			int len = webSiteDtoList.size();
			for(int i=0;i<len;i++)
			{
				WebSiteDTO dto = webSiteDtoList.get(i);
				this.copyWebSiteDto(dto, newWebSiteCatId);
			}
		}
	}

	
	public List<WebSiteDTO> getWebSiteDtoInCat(int catId, String siteName) throws Exception
	{
		List<WebSiteDTO> ret = null;
		try
		{
			ret = this.webSiteDao.getWebSiteListByCategoryIdAndName(catId, siteName);
		}catch(Exception ex)
		{
			LogTool.logError(ex, this.getClass().getName());
			throw ex;
		}finally
		{
			return ret;
		}
	}
	
	public boolean ifWebSiteExist(int siteId) throws Exception
	{
		boolean ret = false;
		List<WebSiteDTO> list = this.webSiteDao.getWebSiteListById(siteId);
		if(list!=null && list.size()>0)
		{
			ret = true;
		}
		return ret;
	}
}
