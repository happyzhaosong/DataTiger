package com.data.collect.server.dao;

import java.util.ArrayList;
import java.util.List;

import com.data.collect.common.dto.ByValueDTO;
import com.data.collect.common.dto.MQMessageDTO;
import com.data.collect.common.exception.InvalidIdException;
import com.data.collect.common.util.ClassTool;
import com.data.collect.common.util.StringTool;


public class DownloadMqMessageDAO extends BaseDAO {

	public void saveDownloadMqMessage(MQMessageDTO dto) throws Exception
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
	
	public boolean ifMqMessageReceived(MQMessageDTO dto) throws Exception
	{
		boolean ret = false;
		if(StringTool.isInteger(String.valueOf(dto.getId())))
		{
			MQMessageDTO tmpDto = this.getMqMessageById(dto.getId());
			if(!ClassTool.isNullObj(tmpDto))
			{
				if(!StringTool.isEmpty(tmpDto.getReceiveTime()))
				{
					ret = true;
				}else
				{
					ret = false;
				}
			}
		}
		return ret;
	}
	
    public MQMessageDTO getMqMessageById(int id) throws Exception
	{
    	MQMessageDTO ret = null;
    	List<MQMessageDTO> list = this.getMqMessageListById(id);
    	if(list!=null && list.size()>0)
    	{
    		ret = list.get(0);
    	}
    	return ret;
	}
	
    public List<MQMessageDTO> getMqMessageListById(int id) throws Exception
	{
    	if(id<0)
		{
			throw new InvalidIdException("Id can not be" + id);
		}
    	
    	return this.getMqMessageListBy(this.BY_ID, ByValueDTO.createInstance().setByValue1(String.valueOf(id)));
	}
    
    public List<MQMessageDTO> getUnReceivedMqMessageList() throws Exception
	{
    	return this.getMqMessageListBy(this.BY_UNRECEIVED_MQ_MESSAGE, null);
	}
    
    public List<MQMessageDTO> getMqMessageListByThreadTableIdAndSiteId(String threadTableId, String siteId) throws Exception
	{
    	return this.getMqMessageListBy(this.BY_THREAD_TABLE_ID_AND_SITE_ID, ByValueDTO.createInstance().setByValue1(String.valueOf(threadTableId)).setByValue2(siteId));
	}
    
    
    private List<MQMessageDTO> getMqMessageListBy(String byKey, ByValueDTO byValueDto) throws Exception
	{    	
    	this.initStringBuffer();
		if(this.BY_ID.equals(byKey))
		{
			if(!ClassTool.isNullObj(byValueDto) && !StringTool.isEmpty(byValueDto.getByValue1()))
			{
				this.whereBuf.append(" id ");
				this.whereBuf.append(" = ");
				this.whereBuf.append(byValueDto.getByValue1());
			}
		}else if(this.BY_THREAD_TABLE_ID_AND_SITE_ID.equals(byKey))
		{
			if(!ClassTool.isNullObj(byValueDto))
			{
				if(!StringTool.isEmpty(byValueDto.getByValue1()))
				{
					this.whereBuf.append(" thread_table_ids = ");
					this.whereBuf.append(byValueDto.getByValue1());
				}
				
				if(!StringTool.isEmpty(byValueDto.getByValue2()))
				{
					if(!StringTool.isEmpty(byValueDto.getByValue1()))
					{
						this.whereBuf.append(" and ");
					}
					this.whereBuf.append(" site_id = ");
					this.whereBuf.append(byValueDto.getByValue2());
				}

			}
		}else if(this.BY_UNRECEIVED_MQ_MESSAGE.equals(byKey))
		{
			this.whereBuf.append(" receive_time is null or receive_time='' ");
		}
		List<MQMessageDTO> ret = new ArrayList<MQMessageDTO>();
		ret = ret.getClass().cast(this.selectDtoList(MQMessageDTO.class));
		return ret;
	}

}
