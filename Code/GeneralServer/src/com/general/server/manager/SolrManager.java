package com.general.server.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.general.common.constants.GeneralConstants;
import com.general.common.dto.DBMQCfgInfoDTO;
import com.general.common.dto.search.SolrSearchKeywordDTO;
import com.general.common.dto.search.SolrSearchOrderDTO;
import com.general.common.dto.search.SolrSearchParamsDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.StringTool;

public class SolrManager extends BaseManager {

	private static SolrManager instance = null;
	
	private HttpSolrServer solrServer = null;
	

	private SolrManager()
	{

	}
	
	public static SolrManager getInstance()
	{
		if(instance==null)
		{
			instance = new SolrManager();
		}		
		return instance;
	}

	public HttpSolrServer getSolrServer(String coreName) throws Exception {
		if(solrServer==null)
		{
			DBMQCfgInfoDTO cfgDto = this.getDBMQConfig();
			solrServer = this.setupSolrServer(cfgDto.getSolrIp(), cfgDto.getSolrPort(), coreName);
		}
		return solrServer;
	}
	
	
    private HttpSolrServer setupSolrServer(String solrIp, String solrPort, String coreName) throws Exception{
    	StringBuffer solrUrlBuf = new StringBuffer();    	
    	solrUrlBuf.append("http://");
    	solrUrlBuf.append(solrIp);
    	solrUrlBuf.append(GeneralConstants.SEPERATOR_COLON);
    	solrUrlBuf.append(solrPort);
    	solrUrlBuf.append(GeneralConstants.SEPERATOR_SLASH);
    	solrUrlBuf.append("solr");
    	solrUrlBuf.append(GeneralConstants.SEPERATOR_SLASH);
    	solrUrlBuf.append(coreName);
    	HttpSolrServer ret = new HttpSolrServer(solrUrlBuf.toString());
    	ret.setParser(new XMLResponseParser());
    	ret.setMaxRetries(1); // defaults to 0.  > 1 not recommended.
    	ret.setConnectionTimeout(5000); // 5 seconds to establish TCP
    	// The following settings are provided here for completeness.
    	// They will not normally be required, and should only be used 
    	// after consulting javadocs to know whether they are truly required.
    	ret.setSoTimeout(1000);  // socket read timeout
    	ret.setDefaultMaxConnectionsPerHost(100);
    	ret.setMaxTotalConnections(100);
    	ret.setFollowRedirects(false);  // defaults to false
    	// allowCompression defaults to false.
    	// Server side must support gzip or deflate for this to have any effect.
    	ret.setAllowCompression(false);
        return ret;
    }

	
    public void createDataIndex() throws Exception
    {
    	
    }
    
    
    public List<Object> searchDataIndex(String coreName, SolrSearchParamsDTO solrSearchParamsDto, Class objClass) throws Exception
    {
    	List<Object> ret = new ArrayList();
    	List<Map<String, String>> rowList = this.searchDataIndex(coreName, solrSearchParamsDto);
    	int rowSize = rowList.size();
    	for(int i=0;i<rowSize;i++)
    	{
    		Map rowMap = rowList.get(i);
    		ret.add(ClassTool.extractValueFromMap(objClass, rowMap));
    	}
    	return ret;    	
    }
    
	private List<Map<String, String>> searchDataIndex(String coreName, SolrSearchParamsDTO solrSearchParamsDto) throws Exception
	{
		List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
		HttpSolrServer solrServer = SolrManager.getInstance().getSolrServer(coreName);	
		SolrQuery query = SolrManager.getInstance().constructSolrQuery(solrSearchParamsDto);	
		
	    QueryResponse response = solrServer.query(query);
	    SolrDocumentList list = response.getResults();
	    
	    if(list!=null)
	    {
	    	int size = list.size();
	    	for(int i=0;i<size;i++)
	    	{
	    		SolrDocument sd = list.get(i);
	    		Collection<String> fieldNames = sd.getFieldNames();
	    		Iterator<String> it = fieldNames.iterator();
	    		
	    		Map<String, String> rowMap = new HashMap<String, String>();
	    		while(it.hasNext())
	    		{
	    			String fieldName = it.next();
	    			Object fieldValue = sd.getFieldValue(fieldName);
	    			rowMap.put(fieldName.toUpperCase(), fieldValue.toString());		
	    		}
	    		
	    		ret.add(rowMap);
	    	}
	    }	    
		return ret;
	}	
	
	public SolrQuery constructSolrQuery(SolrSearchParamsDTO solrSearchParamsDto) throws Exception
	{
		StringBuffer queryBuf = new StringBuffer();		
		List<SolrSearchKeywordDTO> solrKeywordList = solrSearchParamsDto.getSarchKeywordList();
		if(ClassTool.isListEmpty(solrKeywordList))
		{
			queryBuf.append("*:*");
		}else
		{
			int size = solrKeywordList.size();
			for(int i=0;i<size;i++)
			{
				SolrSearchKeywordDTO searchKeywordDto = solrKeywordList.get(i);
				if(!StringTool.isEmpty(searchKeywordDto.getColum()) && !StringTool.isEmpty(searchKeywordDto.getKeyword()))
				{
					queryBuf.append(searchKeywordDto.getColum());
					queryBuf.append(GeneralConstants.SEPERATOR_COLON);
					queryBuf.append(searchKeywordDto.getKeyword());
					queryBuf.append("^");
					queryBuf.append(searchKeywordDto.getPriority());
					queryBuf.append(" ");
				}				
			}
		}
		
		
		SolrQuery query = new SolrQuery();
		query.setStart(solrSearchParamsDto.getStartRow());
		query.setRows(solrSearchParamsDto.getPageSize());				
	    query.setQuery(queryBuf.toString());
	    
	    List<SortClause> sortList = new ArrayList<SortClause>();
	    SortClause sortScore = new SortClause("score", "desc");
	    sortList.add(sortScore);	    
	    
	    /*
	    List<SolrSearchOrderDTO> solrOrderList = solrSearchParamsDto.getSarchOrderList();
	    if(!ClassTool.isListEmpty(solrOrderList))
	    {
	    	int size = solrOrderList.size();
	    	for(int i=0;i<size;i++)
	    	{
	    		SolrSearchOrderDTO searchOrderDto = solrOrderList.get(i);
	    		if(!StringTool.isEmpty(searchOrderDto.getOrderColumn()) && !StringTool.isEmpty(searchOrderDto.getDirection()))
	    		{
	    			SortClause tmpSortScore = new SortClause(searchOrderDto.getOrderColumn(), searchOrderDto.getDirection());
	    			sortList.add(tmpSortScore);
	    		}
	    	}
	    }
	    */
	    query = query.setSorts(sortList);
	    
	    return query;
	}
}
