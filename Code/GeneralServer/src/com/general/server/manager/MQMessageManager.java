package com.general.server.manager;

import java.util.logging.Logger;


import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.general.common.constants.GeneralConstants;
import com.general.common.dto.DBMQCfgInfoDTO;
import com.general.common.dto.JsonDTO;
import com.general.common.dto.MQMessageDTO;
import com.general.common.util.JsonTool;
import com.general.common.util.LogTool;



public class MQMessageManager extends BaseManager implements ExceptionListener {

	private static MQMessageManager instance = null;	
	private Connection connection = null;
	private Session session = null;
    private Destination destination = null;
	private MessageProducer producer = null;
	private MessageConsumer consumer = null;
	private MessageProducer replyProducer = null;
	
	//private String mqUrl = ActiveMQConnection.DEFAULT_BROKER_URL;
	private String mqUrl = "failover://tcp://10.228.66.15:61616";
	private String mqUser = ActiveMQConnection.DEFAULT_USER;
	private String mqPasswd = ActiveMQConnection.DEFAULT_PASSWORD;
    private String subject = "TOOL.DEFAULT";
    private boolean topic = false;
    private boolean transacted = false;
    private boolean persistent = false;
    private long timeToLive = 0;
    private int ackMode = Session.AUTO_ACKNOWLEDGE;
    private boolean durable;
    private String consumerName = "DataCollect Download and Parse";
    private MessageListener messageListener = null;
    
	public static final String CLIENT_ACKNOWLEDGE = "CLIENT_ACKNOWLEDGE";
	public static final String AUTO_ACKNOWLEDGE = "AUTO_ACKNOWLEDGE";
	public static final String DUPS_OK_ACKNOWLEDGE = "DUPS_OK_ACKNOWLEDGE";
	public static final String SESSION_TRANSACTED = "SESSION_TRANSACTED";


	private MQMessageManager() {
		try
		{
			DBMQCfgInfoDTO cfgDto = this.getDBMQConfig();
			if(cfgDto!=null)
			{
				this.setMqUrl(cfgDto.getMqUrl());
				this.setMqUser(cfgDto.getMqUser());
				this.setMqPasswd(cfgDto.getMqPasswd());
				this.setSubject(cfgDto.getMqSubject());
			}
		}catch(Exception ex)
		{
			LogTool.logError( ex);
		}
	}

	public boolean isDurable() {
		return durable;
	}

	public void setDurable(boolean durable) {
		this.durable = durable;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public int getAckMode() {
		return ackMode;
	}

	public void setAckMode(String ackMode) {
        if (MQMessageManager.CLIENT_ACKNOWLEDGE.equals(ackMode)) {
            this.ackMode = Session.CLIENT_ACKNOWLEDGE;
        }else if (MQMessageManager.AUTO_ACKNOWLEDGE.equals(ackMode)) {
            this.ackMode = Session.AUTO_ACKNOWLEDGE;
        }else if (MQMessageManager.DUPS_OK_ACKNOWLEDGE.equals(ackMode)) {
            this.ackMode = Session.DUPS_OK_ACKNOWLEDGE;
        }else if (MQMessageManager.SESSION_TRANSACTED.equals(ackMode)) {
            this.ackMode = Session.SESSION_TRANSACTED;
        }else
        {
        	this.ackMode = Session.AUTO_ACKNOWLEDGE;
        }
	}

	public MessageListener getMessageListener() {
		return messageListener;
	}

	public void setMessageListener(MessageListener messageListener) throws Exception {
		if(messageListener!=null)
		{
            this.getMessageConsumer().setMessageListener(messageListener);
            this.messageListener = messageListener;
		}else
		{
			throw new Exception("Please set message listener object first.");
		}
	}

	public String getSubject() {
		return subject;
	}

	public long getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public boolean isTopic() {
		return topic;
	}

	public void setTopic(boolean topic) {
		this.topic = topic;
	}

	public boolean isTransacted() {
		return transacted;
	}

	public void setTransacted(boolean transacted) {
		this.transacted = transacted;
	}

	public boolean isPersistent() {
		return persistent;
	}

	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}

	public String getMqUrl() {
		return mqUrl;
	}

	public void setMqUrl(String mqUrl) {
		this.mqUrl = mqUrl;
	}

	public String getMqUser() {
		return mqUser;
	}

	public void setMqUser(String mqUser) {
		this.mqUser = mqUser;
	}

	public String getMqPasswd() {
		return mqPasswd;
	}

	public void setMqPasswd(String mqPasswd) {
		this.mqPasswd = mqPasswd;
	}


	public static MQMessageManager getInstance() throws Exception
	{
		if(instance==null)
		{
			instance = new MQMessageManager();
		}		
		return instance;
	}	
	
	public Connection getConnection() throws Exception
	{
		if(connection==null)
		{
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.getMqUser(), this.getMqPasswd(), this.getMqUrl());
			connectionFactory.setSendTimeout(GeneralConstants.TIME_OUT_ACTIVEMQ_CLOSE_SECOND_300);
			connectionFactory.setCloseTimeout(GeneralConstants.TIME_OUT_ACTIVEMQ_CLOSE_SECOND_300);
		    connection = connectionFactory.createConnection();		    
		    connection.setExceptionListener(this);
		    connection.start();
		}
		return connection;
	}
	
	public void onException(JMSException ex) {
		LogTool.logError(ex);
	}

	public Session getSession() throws Exception
	{
		if(session==null)
		{
	        session = this.getConnection().createSession(transacted, ackMode);
		}
		return session;
	}
	
	public Destination getDestination() throws Exception
	{	  
		if(destination==null)
		{
			if (topic) {
		        destination = this.getSession().createTopic(subject);
		    } else {
		        destination = this.getSession().createQueue(subject);
		    }
		}
		return destination;
	}
	
	private MessageProducer getMessageProducer() throws Exception
	{
		if(producer==null)
		{
		    producer = this.getSession().createProducer(this.getDestination());
		    if (persistent) {
		       producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		    } else {
		       producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		    }
		    
		    if (timeToLive != 0) {
		       producer.setTimeToLive(timeToLive);
		    }
		}
		return producer;
	}
	
	private MessageConsumer getMessageConsumer() throws Exception
	{
		if(consumer==null)
		{
	        if (durable && topic) {
	            consumer = this.getSession().createDurableSubscriber((Topic) this.getDestination(), consumerName);
	        } else {
	            consumer = this.getSession().createConsumer(this.getDestination());
	        }
		}
		return consumer;
	}
	
	private MessageProducer getReplyProducer() throws Exception
	{
		if(replyProducer==null)
		{
			replyProducer = this.getSession().createProducer(null);
	        replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		}
		return replyProducer;
	}
	
	public void sendDownloadThreadMessage(MQMessageDTO msgDto) throws Exception
	{
		JsonDTO jsonDto = JsonTool.getJsonDtoByObj(GeneralConstants.JSON_ROOT_ACTIVE_MQ_MESSAGE, msgDto);
		String textMessage = JsonTool.getJsonString(jsonDto);
		this.sendTextMessage(textMessage);
	}
	
	private void sendTextMessage(String textMessage) throws Exception
	{
		TextMessage message = this.getSession().createTextMessage(textMessage);
		this.sendMessage(message);
	}

	private void sendMessage(Message message) throws Exception
	{	
		this.getMessageProducer().send(message);
	}
	
	public void closeAcitiveMq() throws Exception
	{
		if(this.destination!=null)
		{
			this.destination = null;
		}
		
		if(this.producer!=null)
		{
			this.producer.close();
			this.producer=null;
		}
		
		if(this.replyProducer!=null)
		{
			this.replyProducer.close();
			this.replyProducer=null;
		}
		
		if(this.consumer!=null)
		{
			this.consumer.close();
			this.consumer=null;
		}
		
		if(this.session!=null)
		{
			this.session.close();
			this.session=null;
		}
		
		if(this.connection!=null)
		{
			this.connection.close();
			this.connection=null;
		}
	}

}
