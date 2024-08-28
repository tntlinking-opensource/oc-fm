
package com.geeke.cardasset.credit.entity;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;



import com.geeke.common.persistence.DataEntity;


/**
 * 资产入账Entity
 * @author 
 * @version 
 */
 
public class TglVoucherMainsEntry extends DataEntity<TglVoucherMainsEntry> {


	private static final long serialVersionUID = 1641034040897358096L;
	
		
	       
        
  private String uqvoucherid ;		// 凭证ID
        
			
		
	
		
	       
        
  private String intvouchernum ;		// 凭证编码
        
			
		
	
		
	       
        
  private String uqnumbering ;		// 凭证类型
        
			
		
	
		
	       
        
  private String uqcompanyid ;		// 凭证核算单位
        
			
		
	
		
	       
        
  private Integer intcompanyseq ;		// 流水号
        
			
		
	
		
	       
        
  private Integer intaffix ;		// 单据数
        
			
		
	
		
	       
        
  private String uqglobalperiodid ;		// 会计期
        
			
		
	
		
	       
        
  private BigDecimal mnydebitsum ;		// 借方金额合计
        
			
		
	
		
	       
        
  private BigDecimal mnycreditsum ;		// 贷方金额合计
        
			
		
	
		
	       
        
  private String uqfinacialmanagerid ;		// 会计主管
        
			
		
	
		
	       
        
  private String uqaccountantid ;		// 记账人
        
			
		
	
		
	       
        
  private String uqcasherid ;		// 出纳人
        
			
		
	
		
	       
        
  private String uqcheckerid ;		// 审核人
        
			
		
	
		
	       
        
  private String uqfillerid ;		// 制单人
        
			
		
	
		
	       
        
  private String dtaccountant ;		// 记账日期
        
			
		
	
		
	       
        
  private String dtcasher ;		// 出纳日期
        
			
		
	
		
	       
        
  private String dtchecker ;		// 审核日期
        
			
		
	
		
	       
        
  private String dtfiller ;		// 制单日期
        
			
		
	
		
	       
        
  private String dtaccountantsrv ;		// 记账服务器时间
        
			
		
	
		
	       
        
  private String dtcashersrv ;		// 出纳服务器时间
        
			
		
	
		
	       
        
  private String dtcheckersrv ;		// 审核服务器时间
        
			
		
	
		
	       
        
  private String dtfillersrv ;		// 制单服务器时间
        
			
		
	
		
	       
        
  private Integer intflag ;		// 凭证标志 0：未审核 1：已经审核 2：已记账
        
			
		
	
		
	       
        
  private Integer intcashflag ;		// 出纳标志 0:不出纳 1：需要出纳 2：出纳审批完成了
        
			
		
	
		
	       
        
  private Integer intdeleteflag = 0;		// 删除标志(0是正常 1是删除)
        
			
		
	
		
	       
        
  private String uqcancelid ;		// 被冲销凭证ID
        
			
		
	
		
	       
        
  private String fromid ;		// 凭证接口所需字段
        
			
		
	
		
	       
        
  private String submitodd ;		// 报账单号（接口调用）限用于区分暂存凭证 （0-保存，1-暂存）

        
			
		
	
		
	       
        
  private String chargeagainstsubmitodd ;		// 冲销报账单号（接口调用）//暂时用作存储交易时间
        
			
		
	
		
	       
        
  private String tenantId ;		// tenant_id
        
			
		
	

	
  
	
	// 构造方法
	public TglVoucherMainsEntry() {
		super();
	}

	public TglVoucherMainsEntry(String id){
		super(id);
	}
	
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	
		
	

	// 生成get和set方法
	
		
			
	@Length(min=1, max=40, message="凭证ID长度必须介于 1 和 40 之间")
			
			
			    		     
	public String getUqvoucherid() {
		return uqvoucherid;
	}
                 
	public void setUqvoucherid(String uqvoucherid) {
		this.uqvoucherid = uqvoucherid;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=40, message="凭证编码长度必须介于 0 和 40 之间")
			
			
			    		     
	public String getIntvouchernum() {
		return intvouchernum;
	}
                 
	public void setIntvouchernum(String intvouchernum) {
		this.intvouchernum = intvouchernum;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=1, max=40, message="凭证类型长度必须介于 1 和 40 之间")
			
			
			    		     
	public String getUqnumbering() {
		return uqnumbering;
	}
                 
	public void setUqnumbering(String uqnumbering) {
		this.uqnumbering = uqnumbering;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=1, max=40, message="凭证核算单位长度必须介于 1 和 40 之间")
			
			
			    		     
	public String getUqcompanyid() {
		return uqcompanyid;
	}
                 
	public void setUqcompanyid(String uqcompanyid) {
		this.uqcompanyid = uqcompanyid;
	}
	
			     		
	
			
		
	
		
			
			
			    		     
	public Integer getIntcompanyseq() {
		return intcompanyseq;
	}
                 
	public void setIntcompanyseq(Integer intcompanyseq) {
		this.intcompanyseq = intcompanyseq;
	}
	
			     		
	
			
		
	
		
			
	@NotNull(message="单据数不能为空")
			
			
			    		     
	public Integer getIntaffix() {
		return intaffix;
	}
                 
	public void setIntaffix(Integer intaffix) {
		this.intaffix = intaffix;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=1, max=40, message="会计期长度必须介于 1 和 40 之间")
			
			
			    		     
	public String getUqglobalperiodid() {
		return uqglobalperiodid;
	}
                 
	public void setUqglobalperiodid(String uqglobalperiodid) {
		this.uqglobalperiodid = uqglobalperiodid;
	}
	
			     		
	
			
		
	
		
			
	@NotNull(message="借方金额合计不能为空")
			
			
			    		     
	public BigDecimal getMnydebitsum() {
		return mnydebitsum;
	}
                 
	public void setMnydebitsum(BigDecimal mnydebitsum) {
		this.mnydebitsum = mnydebitsum;
	}
	
			     		
	
			
		
	
		
			
	@NotNull(message="贷方金额合计不能为空")
			
			
			    		     
	public BigDecimal getMnycreditsum() {
		return mnycreditsum;
	}
                 
	public void setMnycreditsum(BigDecimal mnycreditsum) {
		this.mnycreditsum = mnycreditsum;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=40, message="会计主管长度必须介于 0 和 40 之间")
			
			
			    		     
	public String getUqfinacialmanagerid() {
		return uqfinacialmanagerid;
	}
                 
	public void setUqfinacialmanagerid(String uqfinacialmanagerid) {
		this.uqfinacialmanagerid = uqfinacialmanagerid;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=40, message="记账人长度必须介于 0 和 40 之间")
			
			
			    		     
	public String getUqaccountantid() {
		return uqaccountantid;
	}
                 
	public void setUqaccountantid(String uqaccountantid) {
		this.uqaccountantid = uqaccountantid;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=40, message="出纳人长度必须介于 0 和 40 之间")
			
			
			    		     
	public String getUqcasherid() {
		return uqcasherid;
	}
                 
	public void setUqcasherid(String uqcasherid) {
		this.uqcasherid = uqcasherid;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=40, message="审核人长度必须介于 0 和 40 之间")
			
			
			    		     
	public String getUqcheckerid() {
		return uqcheckerid;
	}
                 
	public void setUqcheckerid(String uqcheckerid) {
		this.uqcheckerid = uqcheckerid;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=40, message="制单人长度必须介于 0 和 40 之间")
			
			
			    		     
	public String getUqfillerid() {
		return uqfillerid;
	}
                 
	public void setUqfillerid(String uqfillerid) {
		this.uqfillerid = uqfillerid;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=-1, message="记账日期长度必须介于 0 和 -1 之间")
			
			
			    		     
	public String getDtaccountant() {
		return dtaccountant;
	}
                 
	public void setDtaccountant(String dtaccountant) {
		this.dtaccountant = dtaccountant;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=-1, message="出纳日期长度必须介于 0 和 -1 之间")
			
			
			    		     
	public String getDtcasher() {
		return dtcasher;
	}
                 
	public void setDtcasher(String dtcasher) {
		this.dtcasher = dtcasher;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=-1, message="审核日期长度必须介于 0 和 -1 之间")
			
			
			    		     
	public String getDtchecker() {
		return dtchecker;
	}
                 
	public void setDtchecker(String dtchecker) {
		this.dtchecker = dtchecker;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=-1, message="制单日期长度必须介于 0 和 -1 之间")
			
			
			    		     
	public String getDtfiller() {
		return dtfiller;
	}
                 
	public void setDtfiller(String dtfiller) {
		this.dtfiller = dtfiller;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=-1, message="记账服务器时间长度必须介于 0 和 -1 之间")
			
			
			    		     
	public String getDtaccountantsrv() {
		return dtaccountantsrv;
	}
                 
	public void setDtaccountantsrv(String dtaccountantsrv) {
		this.dtaccountantsrv = dtaccountantsrv;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=-1, message="出纳服务器时间长度必须介于 0 和 -1 之间")
			
			
			    		     
	public String getDtcashersrv() {
		return dtcashersrv;
	}
                 
	public void setDtcashersrv(String dtcashersrv) {
		this.dtcashersrv = dtcashersrv;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=-1, message="审核服务器时间长度必须介于 0 和 -1 之间")
			
			
			    		     
	public String getDtcheckersrv() {
		return dtcheckersrv;
	}
                 
	public void setDtcheckersrv(String dtcheckersrv) {
		this.dtcheckersrv = dtcheckersrv;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=-1, message="制单服务器时间长度必须介于 0 和 -1 之间")
			
			
			    		     
	public String getDtfillersrv() {
		return dtfillersrv;
	}
                 
	public void setDtfillersrv(String dtfillersrv) {
		this.dtfillersrv = dtfillersrv;
	}
	
			     		
	
			
		
	
		
			
			
			    		     
	public Integer getIntflag() {
		return intflag;
	}
                 
	public void setIntflag(Integer intflag) {
		this.intflag = intflag;
	}
	
			     		
	
			
		
	
		
			
			
			    		     
	public Integer getIntcashflag() {
		return intcashflag;
	}
                 
	public void setIntcashflag(Integer intcashflag) {
		this.intcashflag = intcashflag;
	}
	
			     		
	
			
		
	
		
			
			
			    		     
	public Integer getIntdeleteflag() {
		return intdeleteflag;
	}
                 
	public void setIntdeleteflag(Integer intdeleteflag) {
		this.intdeleteflag = intdeleteflag;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=40, message="被冲销凭证ID长度必须介于 0 和 40 之间")
			
			
			    		     
	public String getUqcancelid() {
		return uqcancelid;
	}
                 
	public void setUqcancelid(String uqcancelid) {
		this.uqcancelid = uqcancelid;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=40, message="凭证接口所需字段长度必须介于 0 和 40 之间")
			
			
			    		     
	public String getFromid() {
		return fromid;
	}
                 
	public void setFromid(String fromid) {
		this.fromid = fromid;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=40, message="报账单号（接口调用） 限用于区分暂存凭证 （0-保存，1-暂存）长度必须介于 0 和 40 之间")
			
			
			    		     
	public String getSubmitodd() {
		return submitodd;
	}
                 
	public void setSubmitodd(String submitodd) {
		this.submitodd = submitodd;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=0, max=-1, message="冲销报账单号（接口调用）//暂时用作存储交易时间长度必须介于 0 和 -1 之间")
			
			
			    		     
	public String getChargeagainstsubmitodd() {
		return chargeagainstsubmitodd;
	}
                 
	public void setChargeagainstsubmitodd(String chargeagainstsubmitodd) {
		this.chargeagainstsubmitodd = chargeagainstsubmitodd;
	}
	
			     		
	
			
		
	
		
			
	@Length(min=1, max=100, message="tenant_id长度必须介于 1 和 100 之间")
			
			
			    		     
	public String getTenantId() {
		return tenantId;
	}
                 
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
			     		
	
			
		
	
  
	
	
	
	/**
     * 获取实体对应表名
    */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tgl_voucher_mains";
    }
    
    /**
     * 获取实体对应Id
    */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897358096";
    }
    
    /**
     * 获取实体对应描述
    */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "凭证主表入账使用";
    }
    
    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        
            return false;
        
    }
}
