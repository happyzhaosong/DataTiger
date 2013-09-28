package com.food.common.dto;

import java.net.URLEncoder;

import com.general.common.annotation.DBColumn;
import com.general.common.annotation.DBTable;
import com.general.common.constants.GeneralConstants;
import com.general.common.dto.BaseDTO;
import com.general.common.util.StringTool;


@DBTable(name="data_xiu_hao_chi")
public class FoodDTO extends BaseDTO {
	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	@DBColumn(name="shop_name")
	private String shopName = "";
	
	private String shopNameSummary = "";
	
	@DBColumn(name="shop_url")
	private String shopUrl = "";
	
	@DBColumn(name="shop_level_img_url")
	private String shopLevelImgUrl = "";
	
	@DBColumn(name="biao_ti")
	private String biaoTi = "";
	
	private String biaoTiSummary = "";
	
	@DBColumn(name="jia_ge")
	private String jiaGe = "";
	
	@DBColumn(name="dan_jia")
	private String danJia = "";
	
	private String danJiaSummary = "";
	
	@DBColumn(name="cu_xiao_jia")
	private String cuXiaoJia = "";
	
	private String cuXiaoJiaSummary = "";
	
	@DBColumn(name="ping_fen")
	private String pingFen = "";
	
	@DBColumn(name="img_url")
	private String imgUrl = "";
	
	@DBColumn(name="meta_search_keyword")
	private String metaSearchKeyword = "";
	
	@DBColumn(name="meta_desc")
	private String metaDesc = "";
	
	@DBColumn(name="item_url")
	private String itemUrl = "";
	
	@DBColumn(name="shang_pin_lai_yuan")
	private int shangPinLaiYuan = -1;
	
	@DBColumn(name="jiao_yi")
	private String jiaoYi = "0";
	
	@DBColumn(name="jiao_yi_success")
	private String jiaoYiSuccess = "0";
	
	@DBColumn(name="hao_ping_lv")
	private String haoPingLv = "0%";
	
	@DBColumn(name="wang_wang_url")
	private String wangWangUrl = "0";
	
	@DBColumn(name="click_count")
	private int clickCount = 0;
	
	@DBColumn(name="pin_pai_zhi_xiao")
	private int pinPaiZhiXiao = 0;

	@DBColumn(name="shang_cheng_pei_song")
	private int shangChengPeiSong = 0;
	
	public String getCuXiaoJiaSummary() {
		if(this.cuXiaoJia.length()>15)
		{
			cuXiaoJiaSummary = cuXiaoJia.substring(0, 15) + "...";
		}else
		{
			cuXiaoJiaSummary = cuXiaoJia;
		}
		
		if(!StringTool.isEmpty(cuXiaoJiaSummary))
		{
			cuXiaoJiaSummary = "促销:" + cuXiaoJiaSummary;
		}
		return cuXiaoJiaSummary;
	}

	public void setCuXiaoJiaSummary(String cuXiaoJiaSummary) {
		this.cuXiaoJiaSummary = cuXiaoJiaSummary;
	}

	public int getShangChengPeiSong() {
		return shangChengPeiSong;
	}

	public void setShangChengPeiSong(int shangChengPeiSong) {
		this.shangChengPeiSong = shangChengPeiSong;
	}

	public String getDanJiaSummary() {
		if(this.danJia.length()>15)
		{
			danJiaSummary = this.danJia.substring(0, 10) + "...";
		}else
		{
			danJiaSummary = this.danJia;
		}
		return danJiaSummary;
	}

	public void setDanJiaSummary(String danJiaSummary) {
		this.danJiaSummary = danJiaSummary;
	}

	public String getShopNameSummary() {
		if(this.shopName.length()>8)
		{
			shopNameSummary = this.shopName.substring(0, 8) + "...";
		}else
		{
			shopNameSummary = this.shopName;
		}
		return shopNameSummary;
	}

	public void setShopNameSummary(String shopNameSummary) {
		this.shopNameSummary = shopNameSummary;
	}

	public int getPinPaiZhiXiao() {
		return pinPaiZhiXiao;
	}

	public void setPinPaiZhiXiao(int pinPaiZhiXiao) {
		this.pinPaiZhiXiao = pinPaiZhiXiao;
	}

	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	public String getHaoPingLv() {		
		if(!StringTool.isEmpty(haoPingLv))
		{
			haoPingLv = "好评率:" + haoPingLv;
		}
		return haoPingLv;
	}

	public void setHaoPingLv(String haoPingLv) {
		this.haoPingLv = haoPingLv;
	}

	public String getWangWangUrl() {
		return wangWangUrl;
	}

	public void setWangWangUrl(String wangWangUrl) {
		this.wangWangUrl = wangWangUrl;
	}

	public String getJiaoYi() {
		return jiaoYi;
	}

	public void setJiaoYi(String jiaoYi) {
		this.jiaoYi = jiaoYi;
	}

	public String getJiaoYiSuccess() {
		if(!StringTool.isEmpty(jiaoYiSuccess))
		{
			jiaoYiSuccess = "销量:" + jiaoYiSuccess;
		}
		return jiaoYiSuccess;
	}

	public void setJiaoYiSuccess(String jiaoYiSuccess) {
		this.jiaoYiSuccess = jiaoYiSuccess;
	}

	public String getBiaoTiSummary() {
		if(this.biaoTi.length()>15)
		{
			biaoTiSummary = biaoTi.substring(0, 15) + "...";
		}else
		{
			biaoTiSummary = biaoTi;
		}
		return biaoTiSummary;
	}

	public void setBiaoTiSummary(String biaoTiSummary) {
		this.biaoTiSummary = biaoTiSummary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	public String getShopLevelImgUrl() {
		return shopLevelImgUrl;
	}

	public void setShopLevelImgUrl(String shopLevelImgUrl) {
		this.shopLevelImgUrl = shopLevelImgUrl;
	}

	public String getBiaoTi() {
		return biaoTi;
	}

	public void setBiaoTi(String biaoTi) {
		this.biaoTi = biaoTi;
	}

	public String getJiaGe() {
		return jiaGe;
	}

	public void setJiaGe(String jiaGe) {
		this.jiaGe = jiaGe;
	}

	public String getDanJia() {
		return danJia;
	}

	public void setDanJia(String danJia) {
		this.danJia = danJia;
	}

	public String getCuXiaoJia() {
		return cuXiaoJia;
	}

	public void setCuXiaoJia(String cuXiaoJia) {
		this.cuXiaoJia = cuXiaoJia;
	}

	public String getPingFen() {		
		if(!StringTool.isEmpty(pingFen))
		{
			pingFen = "评分:" + pingFen;
		}
		return pingFen;
	}

	public void setPingFen(String pingFen) {
		this.pingFen = pingFen;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getMetaSearchKeyword() {
		return metaSearchKeyword;
	}

	public void setMetaSearchKeyword(String metaSearchKeyword) {
		this.metaSearchKeyword = metaSearchKeyword;
	}

	public String getMetaDesc() {
		return metaDesc;
	}

	public void setMetaDesc(String metaDesc) {
		this.metaDesc = metaDesc;
	}

	public String getItemUrl() {
		//if(!StringTool.isEmpty(itemUrl))
		{
			//itemUrl = URLEncoder.encode(itemUrl);
		}
		itemUrl = StringTool.encodeStr(itemUrl);
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public int getShangPinLaiYuan() {
		return shangPinLaiYuan;
	}

	public void setShangPinLaiYuan(int shangPinLaiYuan) {
		this.shangPinLaiYuan = shangPinLaiYuan;
	}
}
