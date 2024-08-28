
package com.geeke.vouchermanager.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

import java.util.List;

import com.google.common.collect.Lists;


import com.fasterxml.jackson.annotation.JsonIgnore;



import com.geeke.common.persistence.DataEntity;


/**
 * 科目期初余额Entity
 * @author 
 * @version 
 */
 
@ApiModel(description = "生成get和set方法")
public class TglPeriodAccounts extends DataEntity<TglPeriodAccounts> {


	@ApiModelProperty(hidden = true)
	private static final long serialVersionUID = 1457254743943431257L;
	
		
	       
        
  @ApiModelProperty("单位ID")
  private String uqcompanyid ;		// 单位ID
        
			
		
	
		
	       
        
  @ApiModelProperty("全局会计期ID")
  private String uqglobalperiodid ;		// 全局会计期ID
        
			
		
	
		
	       
	@ApiModelProperty("会计科目ID")
	private TglAccounts  uqaccountid;      // 会计科目ID
			
		
	
		
	       
        
  @ApiModelProperty("全部本期借方金额")
  private BigDecimal mnydebitperiodall ;		// 全部本期借方金额
        
			
		
	
		
	       
        
  @ApiModelProperty("全部本期贷方金额")
  private BigDecimal mnycreditperiodall ;		// 全部本期贷方金额
        
			
		
	
		
	       
        
  @ApiModelProperty("全部本期借方外币金额")
  private BigDecimal mnyfdebitperiodall ;		// 全部本期借方外币金额
        
			
		
	
		
	       
        
  @ApiModelProperty("全部本期贷方外币金额")
  private BigDecimal mnyfcreditperiodall ;		// 全部本期贷方外币金额
        
			
		
	
		
	       
        
  @ApiModelProperty("已入账本期借方金额")
  private BigDecimal mnydebitperiod ;		// 已入账本期借方金额
        
			
		
	
		
	       
        
  @ApiModelProperty("已入账本期贷方金额")
  private BigDecimal mnycreditperiod ;		// 已入账本期贷方金额
        
			
		
	
		
	       
        
  @ApiModelProperty("已入账本期借方外币金额")
  private BigDecimal mnyfdebitperiod ;		// 已入账本期借方外币金额
        
			
		
	
		
	       
        
  @ApiModelProperty("已入账本期贷方外币金额")
  private BigDecimal mnyfcreditperiod ;		// 已入账本期贷方外币金额
        
			
		
	
		
	       
        
  @ApiModelProperty("已出纳本期借方金额")
  private BigDecimal mnydebitcashed ;		// 已出纳本期借方金额
        
			
		
	
		
	       
        
  @ApiModelProperty("已出纳本期贷方金额")
  private BigDecimal mnycreditcashed ;		// 已出纳本期贷方金额
        
			
		
	
		
	       
        
  @ApiModelProperty("已出纳本期借方外币金额")
  private BigDecimal mnyfdebitcashed ;		// 已出纳本期借方外币金额
        
			
		
	
		
	       
        
  @ApiModelProperty("已出纳本期贷方外币金额")
  private BigDecimal mnyfcreditcashed ;		// 已出纳本期贷方外币金额
        
			
		
	

	
	
	// 构造方法
	public TglPeriodAccounts() {
		super();
	}

	public TglPeriodAccounts(String id){
		super(id);
	}
	
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	

	// 生成get和set方法
	
		
			
	@Length(min=1, max=40, message="单位ID长度必须介于 1 和 40 之间")
			
			
			    		     
	public String getUqcompanyid() {
		return uqcompanyid;
	}
                 
	public void setUqcompanyid(String uqcompanyid) {
		this.uqcompanyid = uqcompanyid;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=1, max=40, message="全局会计期ID长度必须介于 1 和 40 之间")
			
			
			    		     
	public String getUqglobalperiodid() {
		return uqglobalperiodid;
	}
                 
	public void setUqglobalperiodid(String uqglobalperiodid) {
		this.uqglobalperiodid = uqglobalperiodid;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=1, max=40, message="会计科目ID长度必须介于 1 和 40 之间")
			
			   
    public TglAccounts  getUqaccountid() {
        return uqaccountid;
    }

    public void setUqaccountid(TglAccounts uqaccountid) {
        this.uqaccountid = uqaccountid;
    }
            
		
	
		
			
			
			    		     
	public BigDecimal getMnydebitperiodall() {
		return mnydebitperiodall;
	}
                 
	public void setMnydebitperiodall(BigDecimal mnydebitperiodall) {
		this.mnydebitperiodall = mnydebitperiodall;
	}
	
			     		
	
			
		
	
		
			
			
			    		     
	public BigDecimal getMnycreditperiodall() {
		return mnycreditperiodall;
	}
                 
	public void setMnycreditperiodall(BigDecimal mnycreditperiodall) {
		this.mnycreditperiodall = mnycreditperiodall;
	}
	
			     		
	
			
		
	
		
			
			
			    		     
	public BigDecimal getMnyfdebitperiodall() {
		return mnyfdebitperiodall;
	}
                 
	public void setMnyfdebitperiodall(BigDecimal mnyfdebitperiodall) {
		this.mnyfdebitperiodall = mnyfdebitperiodall;
	}
	
			     		
	
			
		
	
		
			
			
			    		     
	public BigDecimal getMnyfcreditperiodall() {
		return mnyfcreditperiodall;
	}
                 
	public void setMnyfcreditperiodall(BigDecimal mnyfcreditperiodall) {
		this.mnyfcreditperiodall = mnyfcreditperiodall;
	}
	
			     		
	
			
		
	
		
			
			
			    		     
	public BigDecimal getMnydebitperiod() {
		return mnydebitperiod;
	}
                 
	public void setMnydebitperiod(BigDecimal mnydebitperiod) {
		this.mnydebitperiod = mnydebitperiod;
	}
	
			     		
	
			
		
	
		
			
			
			    		     
	public BigDecimal getMnycreditperiod() {
		return mnycreditperiod;
	}
                 
	public void setMnycreditperiod(BigDecimal mnycreditperiod) {
		this.mnycreditperiod = mnycreditperiod;
	}
	
			     		
	
			
		
	
		
			
			
			    		     
	public BigDecimal getMnyfdebitperiod() {
		return mnyfdebitperiod;
	}
                 
	public void setMnyfdebitperiod(BigDecimal mnyfdebitperiod) {
		this.mnyfdebitperiod = mnyfdebitperiod;
	}
	
			     		
	
			
		
	
		
			
			
			    		     
	public BigDecimal getMnyfcreditperiod() {
		return mnyfcreditperiod;
	}
                 
	public void setMnyfcreditperiod(BigDecimal mnyfcreditperiod) {
		this.mnyfcreditperiod = mnyfcreditperiod;
	}
	
			     		
	
			
		
	
		
			
			
			    		     
	public BigDecimal getMnydebitcashed() {
		return mnydebitcashed;
	}
                 
	public void setMnydebitcashed(BigDecimal mnydebitcashed) {
		this.mnydebitcashed = mnydebitcashed;
	}
	
			     		
	
			
		
	
		
			
			
			    		     
	public BigDecimal getMnycreditcashed() {
		return mnycreditcashed;
	}
                 
	public void setMnycreditcashed(BigDecimal mnycreditcashed) {
		this.mnycreditcashed = mnycreditcashed;
	}
	
			     		
	
			
		
	
		
			
			
			    		     
	public BigDecimal getMnyfdebitcashed() {
		return mnyfdebitcashed;
	}
                 
	public void setMnyfdebitcashed(BigDecimal mnyfdebitcashed) {
		this.mnyfdebitcashed = mnyfdebitcashed;
	}
	
			     		
	
			
		
	
		
			
			
			    		     
	public BigDecimal getMnyfcreditcashed() {
		return mnyfcreditcashed;
	}
                 
	public void setMnyfcreditcashed(BigDecimal mnyfcreditcashed) {
		this.mnyfcreditcashed = mnyfcreditcashed;
	}
	
			     		
	
			
		
	
	
	
	
	/**
     * 获取实体对应表名
    */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_period_accounts";
    }
    
    /**
     * 获取实体对应Id
    */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943431257";
    }
    
    /**
     * 获取实体对应描述
    */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "科目发生数汇总表";
    }
    
    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        
            return false;
        
    }
}
