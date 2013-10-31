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
	
	//存储 oracle 数据表的数据源对象
	private DataSource oracleDataSource = null;
	
	//存储mysql数据表的数据源对象
	private DataSource mysqlDataSource = null;
	
	//存储解析后的数据表的数据源对象
	private DataSource dataTblDataSource = null;
	
	//存储mysql数据库表基本信息数据源对象
	//private DataSource mysqlInforSchemaDataSource = null;
	
	public DataSource getDataSource() throws Exception {
		return DBManager.getInstance().getOracleDataSource();
	}
	
	private DataSource getOracleDataSource() throws Exception {
		if(oracleDataSource==null)
		{
			DBMQCfgInfoDTO cfgDto = this.getDBMQConfig();
			oracleDataSource = setupDataSource(GeneralConstants.SERVER_TYPE_ORACLE, GeneralConstants.JDBC_ORACLE_CLASS, cfgDto.getDbIp(), cfgDto.getDbPort(), cfgDto.getDbName(), cfgDto.getDbUser(), cfgDto.getDbPasswd());
		}		
		return oracleDataSource;
	}
	
	private DataSource getMysqlDataSource() throws Exception{
		if(mysqlDataSource==null)
		{
			DBMQCfgInfoDTO cfgDto = this.getDBMQConfig();
			mysqlDataSource = setupDataSource(GeneralConstants.SERVER_TYPE_MY_SQL, GeneralConstants.JDBC_MY_SQL_CLASS, cfgDto.getDbIp(), cfgDto.getDbPort(), cfgDto.getDbName(), cfgDto.getDbUser(), cfgDto.getDbPasswd());
		}
		return mysqlDataSource;
	}

	public DataSource getDataTblDataSource() throws Exception {
		if(dataTblDataSource==null)
		{
			DBMQCfgInfoDTO cfgDto = this.getDBMQConfig();
			dataTblDataSource = setupDataSource(GeneralConstants.SERVER_TYPE_ORACLE, GeneralConstants.JDBC_ORACLE_CLASS, cfgDto.getDataTblDbIp(), cfgDto.getDataTblDbPort(), cfgDto.getDataTblDbName(), cfgDto.getDataTblDbUser(), cfgDto.getDataTblDbPasswd());
		}
		return dataTblDataSource;
	}

	/*
	public DataSource getMysqlInforSchemaDataSource() throws Exception{
		if(mysqlInforSchemaDataSource==null)
		{
			DBMQCfgInfoDTO cfgDto = this.getDBMQConfig();  
			mysqlInforSchemaDataSource = setupDataSource(GeneralConstants.SERVER_TYPE_MY_SQL, GeneralConstants.JDBC_MY_SQL_CLASS, cfgDto.getDbIp(), cfgDto.getDbPort(), cfgDto.getInformationShcemaDbName(), cfgDto.getInformationShcemaDbUser(), cfgDto.getInformationShcemaDbPasswd());		
		}
		return mysqlInforSchemaDataSource;
	}
	*/

	private DBManager()
	{

	}
	

	
	public static DBManager getInstance()
	{
		if(instance==null)
		{
			instance = new DBManager();
		}		
		return instance;
	}
	

    private DataSource setupDataSource(String serverType, String driverClass, String dbIp, String dbPort, String dbName, String dbUser, String dbPasswd) throws Exception{
    	StringBuffer sqlUrlBuf = new StringBuffer();
    	
    	if(GeneralConstants.SERVER_TYPE_MY_SQL.equals(serverType))
        { 
	    	sqlUrlBuf.append("jdbc:mysql://");
	    	sqlUrlBuf.append(dbIp);
	    	sqlUrlBuf.append(":");
	    	sqlUrlBuf.append(dbPort);
	    	sqlUrlBuf.append("/");
	    	
	    	sqlUrlBuf.append(dbName);	    	
	    	sqlUrlBuf.append("?autoReconnect=true&autoReconnectForPools=true&useUnicode=true&characterEncoding=UTF-8");	    	
        }else if(GeneralConstants.SERVER_TYPE_ORACLE.equals(serverType))
        {
	    	sqlUrlBuf.append("jdbc:oracle:thin:@");
	    	sqlUrlBuf.append(dbIp);
	    	sqlUrlBuf.append(":");
	    	sqlUrlBuf.append(dbPort);
	    	sqlUrlBuf.append(":");	    	
	    	sqlUrlBuf.append(dbName);   
        }
    	
    	BasicDataSource ds = new BasicDataSource();
	    ds.setDriverClassName(driverClass);
	        
	    ds.setUsername(dbUser);
	    ds.setPassword(dbPasswd);
	        
	    String connectURL = sqlUrlBuf.toString();
	    ds.setUrl(connectURL); 
	    ds.setTestOnBorrow(true);
	    ds.setTestWhileIdle(true);
	    //default 10 connection to db
	    ds.setInitialSize(10);  
	        
	    LogTool.logText("DbConnUrl = " + connectURL , this.getClass().getName());
	    LogTool.logText("DbUserName = " + dbUser , this.getClass().getName());
	    LogTool.logText("DbPasswd = " + dbPasswd , this.getClass().getName());
	        
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
				LogTool.logError(ex, this.getClass().getName());
				throw ex;
			}finally
			{
				conn=null;
			}
		}
	}
	

}
