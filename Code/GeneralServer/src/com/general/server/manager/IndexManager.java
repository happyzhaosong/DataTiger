package com.general.server.manager;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;

import com.general.common.constants.GeneralConstants;
import com.general.common.dto.DBMQCfgInfoDTO;

public class IndexManager extends BaseManager {

	private static IndexManager instance = null;
	
	private HttpSolrServer solrServer = null;
	

	private IndexManager()
	{

	}
	
	public static IndexManager getInstance()
	{
		if(instance==null)
		{
			instance = new IndexManager();
		}		
		return instance;
	}

	public SolrServer getSolrServer() throws Exception {
		if(solrServer==null)
		{
			DBMQCfgInfoDTO cfgDto = this.getDBMQConfig();
			solrServer = this.setupSolrServer(cfgDto.getSolrIp(), cfgDto.getSolrPort(), cfgDto.getSolrWebAppName());
		}
		return solrServer;
	}
	
	
    private HttpSolrServer setupSolrServer(String solrIp, String solrPort, String solrInstanceName) throws Exception{
    	StringBuffer solrUrlBuf = new StringBuffer();    	
    	solrUrlBuf.append("http://");
    	solrUrlBuf.append(solrIp);
    	solrUrlBuf.append(GeneralConstants.SEPERATOR_COLON);
    	solrUrlBuf.append(solrPort);
    	solrUrlBuf.append(GeneralConstants.SEPERATOR_SLASH);
    	solrUrlBuf.append(solrInstanceName);
    	HttpSolrServer ret = new HttpSolrServer(solrUrlBuf.toString());
    	ret.setParser(new XMLResponseParser());
    	//ret.set
        return ret;
    }

	
    public void createIndex() throws Exception
    {
    	
    }
	
	
}
