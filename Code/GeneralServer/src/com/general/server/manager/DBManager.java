package com.general.server.manager;

import java.sql.Connection;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import com.general.common.constants.GeneralConstants;
import com.general.common.dto.DBMQCfgInfoDTO;
import com.general.common.util.LogTool;

public class DBManager extends BaseManager{
		
	private static DBManager instance = null;		
	private DataSource mysqlDataSource = null;
	private DataSource mysqlInforSchemaDataSource = null;
	
	public DataSource getMysqlDataSource() throws Exception{
		if(mysqlDataSource==null)
		{
			mysqlDataSource = setupDataSource(GeneralConstants.SERVER_TYPE_MY_SQL, GeneralConstants.JDBC_MY_SQL_CLASS, false);
		}
		return mysqlDataSource;
	}

	public DataSource getMysqlInforSchemaDataSource() throws Exception{
		if(mysqlInforSchemaDataSource==null)
		{
			mysqlInforSchemaDataSource = setupDataSource(GeneralConstants.SERVER_TYPE_MY_SQL, GeneralConstants.JDBC_MY_SQL_CLASS, true);		
		}
		return mysqlInforSchemaDataSource;
	}

	private DBManager()
	{
		try
		{
			mysqlDataSource = setupDataSource(GeneralConstants.SERVER_TYPE_MY_SQL, GeneralConstants.JDBC_MY_SQL_CLASS, false);
		}catch(Exception ex)
		{
			LogTool.logError( ex);
		}
	}
	

	
	public static DBManager getInstance()
	{
		if(instance==null)
		{
			instance = new DBManager();
		}		
		return instance;
	}
	

    private DataSource setupDataSource(String serverType, String driverClass, boolean ifInformationSchema) throws Exception{
    	DBMQCfgInfoDTO cfgDto = this.getDBMQConfig();   	
    	
    	StringBuffer sqlUrlBuf = new StringBuffer();
    	sqlUrlBuf.append("jdbc:mysql://");
    	sqlUrlBuf.append(cfgDto.getDbIp());
    	sqlUrlBuf.append(":");
    	sqlUrlBuf.append(cfgDto.getDbPort());
    	sqlUrlBuf.append("/");
    	
    	if(ifInformationSchema)
    	{
    		sqlUrlBuf.append(cfgDto.getInformationShcemaDbName());
    	}else
    	{
    		sqlUrlBuf.append(cfgDto.getDbName());
    	}
    	
    	sqlUrlBuf.append("?autoReconnect=true&autoReconnectForPools=true&useUnicode=true&characterEncoding=UTF-8");
    	
    	String connectURL = sqlUrlBuf.toString();
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driverClass);
        
        if(ifInformationSchema)
    	{
	        ds.setUsername(cfgDto.getInformationShcemaDbUser());
	        ds.setPassword(cfgDto.getInformationShcemaDbPasswd());
    	}else
    	{
            ds.setUsername(cfgDto.getDbUser());
            ds.setPassword(cfgDto.getDbPasswd());    		
    	}
        
        ds.setUrl(connectURL); 
        ds.setTestOnBorrow(true);
        ds.setTestWhileIdle(true);
        //default 10 connection to db
        ds.setInitialSize(10);        
        
        if(GeneralConstants.SERVER_TYPE_MY_SQL.equals(serverType))
        {        	
        	ds.setValidationQuery("select 1 from dual");
        }
        
        return ds;
    }

    private void printDataSourceStats(DataSource ds) {
        BasicDataSource bds = (BasicDataSource) ds;
        System.out.println("NumActive: " + bds.getNumActive());
        System.out.println("NumIdle: " + bds.getNumIdle());
    }

    private void shutdownDataSource(DataSource ds) throws SQLException {
        BasicDataSource bds = (BasicDataSource) ds;
        bds.close();
    }
    
	public void closeDBConn(Connection conn) throws Exception
	{
		if(conn!=null)
		{
			try{
				if(!conn.isClosed())
				{
					conn.close();
				}
			}catch(Exception ex)
			{
				LogTool.logError(ex);
				throw ex;
			}finally
			{
				conn=null;
			}
		}
	}
	

}
