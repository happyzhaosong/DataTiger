package com.food.common.dto;

import com.general.common.annotation.DBColumn;
import com.general.common.annotation.DBTable;
import com.general.common.dto.BaseDTO;


@DBTable(name="data_xiu_hao_chi")
public class FoodDTO extends BaseDTO {
	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	@DBColumn(name="shop_name")
	private String shopName = "";

	@DBColumn(name="shop_url")
	private String shopUrl = "";
	
	@DBColumn(name="shop_level_img_url")
	private String shopLevelImgUrl = "";
	
	@DBColumn(name="biao_ti")
	private String biaoTi = "";
	
	@DBColumn(name="jia_ge")
	private String jiaGe = "";
	
	@DBColumn(name="dan_jia")
	private String danJia = "";
	
	@DBColumn(name="cu_xiao_jia")
	private String cuXiaoJia = "";
	
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
