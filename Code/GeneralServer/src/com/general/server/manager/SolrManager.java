package com.general.server.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;

import com.general.common.constants.GeneralConstants;
import com.general.common.dto.DBMQCfgInfoDTO;
import com.general.common.dto.search.BaseSearchParamsDTO;
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
    
    
	public List<Map<String, String>> searchDataIndex(String coreName, BaseSearchParamsDTO searchParamsDto) throws Exception
	{
		List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
		HttpSolrServer solrServer = SolrManager.getInstance().getSolrServer(coreName);
				
		String searchColumn = searchParamsDto.getSearchColumn();
		if(StringTool.isEmpty(searchColumn))
		{
			searchColumn
		}
		
		SolrQuery query = new SolrQuery();
	    query.setQuery("*:*");
	    query.add.addSortField( "price", SolrQuery.ORDER.asc );
		
		return ret;
	}
	
	private 
}
