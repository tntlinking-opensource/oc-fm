
package com.geeke.vouchermanager.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import com.geeke.vouchermanager.entity.TglGlobalPeriods;

import java.math.BigDecimal;

import com.geeke.vouchermanager.entity.SysUser;

import java.util.List;

import com.google.common.collect.Lists;

import com.fasterxml.jackson.annotation.JsonIgnore;


  
import com.geeke.vouchermanager.entity.TglGlobalPeriods;
  
import com.geeke.vouchermanager.entity.SysUser;
  
import com.geeke.vouchermanager.entity.TglGroupAccounts;
  
import com.geeke.vouchermanager.entity.TglVoucherDetails;
  
import com.geeke.vouchermanager.entity.TglVoucherDetailLedger;
  


import com.geeke.common.persistence.DataEntity;


/**
 * 制证Entity
 * @author 
 * @version 
 */
 
@ApiModel(description = "生成get和set方法")
public class TglVoucherMains extends DataEntity<TglVoucherMains> {


	@ApiModelProperty(hidden = true)
	private static final long serialVersionUID = 1457254743943432957L;
	
		
	       
        
  @ApiModelProperty("后台生成不用传 凭证ID")
  private String uqvoucherid ;		// 凭证ID
        
			
		
	
		
	       
        
  @ApiModelProperty("后台生成不用传 凭证编码")
  private String intvouchernum ;		// 凭证编码
        
			
		
	
		
	       
        
  @ApiModelProperty("后台生成不用传 凭证类型")
  private String uqnumbering ;		 // 凭证类型
  @ApiModelProperty("后台生成不用传 凭证类型名称")
  private String uqnumberingname ;		// 凭证类型
        
			
		
	
		
	       
        
  @ApiModelProperty("后台生成不用传 凭证核算单位")
  private String uqcompanyid ;		// 凭证核算单位
        
			
		
	
		
	       
        
  @ApiModelProperty("后台生成不用传 流水号")
  private Integer intcompanyseq ;		// 流水号
        
			
		
	
		
	       
        
  @ApiModelProperty("单据数")
  private Integer intaffix ;		// 单据数
        
			
		
	
		
	       
    @ApiModelProperty("会计期")
	private TglGlobalPeriods uqglobalperiodid ;      // 会计期
	        
		
	
		
	       
        
  @ApiModelProperty("借方金额合计")
  private BigDecimal mnydebitsum ;		// 借方金额合计
        
			
		
	
		
	       
        
  @ApiModelProperty("贷方金额合计")
  private BigDecimal mnycreditsum ;		// 贷方金额合计
        
			
		
	
		
	       
    @ApiModelProperty("后台生成不用传 会计主管")
	private SysUser uqfinacialmanagerid ;      // 会计主管
	        
		
	
		
	       
    @ApiModelProperty("后台生成不用传 记账人")
	private SysUser uqaccountantid ;      // 记账人
	        
		
	
		
	       
    @ApiModelProperty("后台生成不用传 出纳人")
	private SysUser uqcasherid ;      // 出纳人
	        
		
	
		
	       
    @ApiModelProperty("后台生成不用传 审核人")
	private SysUser uqcheckerid ;      // 审核人
	        
		
	
		
	       
    @ApiModelProperty("后台生成不用传 制单人")
	private SysUser uqfillerid ;      // 制单人
	        
		
	
		
	       
        
  @ApiModelProperty("后台生成不用传 记账日期")
  private String dtaccountant ;		// 记账日期
        
			
		
	
		
	       
        
  @ApiModelProperty("后台生成不用传 出纳日期")
  private String dtcasher ;		// 出纳日期
        
			
		
	
		
	       
        
  @ApiModelProperty("后台生成不用传 审核日期")
  private String dtchecker ;		// 审核日期
        
			
		
	
		
	       
        
  @ApiModelProperty("制单日期")
  private String dtfiller ;		// 制单日期
        
			
		
	
		
	       
        
  @ApiModelProperty("后台生成不用传 记账服务器时间")
  private String dtaccountantsrv ;		// 记账服务器时间
        
			
		
	
		
	       
        
  @ApiModelProperty("后台生成不用传 出纳服务器时间")
  private String dtcashersrv ;		// 出纳服务器时间
        
			
		
	
		
	       
        
  @ApiModelProperty("后台生成不用传 审核服务器时间")
  private String dtcheckersrv ;		// 审核服务器时间
        
			
		
	
		
	       
        
  @ApiModelProperty("后台生成不用传 制单服务器时间")
  private String dtfillersrv ;		// 制单服务器时间
        
			
		
	
		
	       
        
  @ApiModelProperty("后台生成不用传 凭证标志")
  private Integer intflag ;		// 凭证标志
        
			
		
	
		
	       
        
  @ApiModelProperty("后台生成不用传 出纳标志")
  private Integer intcashflag ;		// 出纳标志
        
			
		
	
		
	       
        
  @ApiModelProperty("后台生成不用传 删除标志(0是正常 1是删除)")
  private Integer intdeleteflag = 0;		// 删除标志(0是正常 1是删除)
        
			
		
	
		
	       
        
  @ApiModelProperty("被冲销凭证ID")
  private String uqcancelid ;		// 被冲销凭证ID
        
			
		
	
		
	       
        
  @ApiModelProperty("凭证接口所需字段")
  private String fromid ;		// 凭证接口所需字段
        
			
		
	
		
	       
        
  @ApiModelProperty("报账单号（接口调用）限用于区分暂存凭证 （0-保存，1-暂存）")
  private String submitodd ;		// 报账单号（接口调用）限用于区分暂存凭证 （0-保存，1-暂存）

        
			
		
	
		
	       
        
  @ApiModelProperty("交易时间  冲销报账单号（接口调用）//暂时用作存储交易时间")
  private String chargeagainstsubmitodd ;		// 冲销报账单号（接口调用）//暂时用作存储交易时间
        
			
		
	
		
	       
        
  @ApiModelProperty("后台生成不用传 tenant_id")
  private String tenantId ;		// tenant_id


  @ApiModelProperty("后台生成不用传 companyname")
  private String companyname ;		// companyname
        
			
		
	

	
    
  @ApiModelProperty(hidden = true)
  private TglGlobalPeriods tglGlobalPeriodsList ;		// 子表列表
    

    
  @ApiModelProperty("凭证分录")
  private List<TglVoucherDetails> tglVoucherDetailsList = Lists.newArrayList();		// 子表列表
    
	// 子表列表




	// 构造方法
	public TglVoucherMains() {
		super();
	}

	public TglVoucherMains(String id){
		super(id);
	}

	public String getUqnumberingname() {
		return uqnumberingname;
	}

	public void setUqnumberingname(String uqnumberingname) {
		this.uqnumberingname = uqnumberingname;
	}


	// 生成get和set方法


	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
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
	
			     		
	
			
		
	
		
			
	@NotNull(message="会计期不能为空")
			
			
	public TglGlobalPeriods getUqglobalperiodid() {
		return uqglobalperiodid;
	}

	public void setUqglobalperiodid(TglGlobalPeriods uqglobalperiodid) {
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
	
			     		
	
			
		
	
		
			
			
	public SysUser getUqfinacialmanagerid() {
		return uqfinacialmanagerid;
	}

	public void setUqfinacialmanagerid(SysUser uqfinacialmanagerid) {
		this.uqfinacialmanagerid = uqfinacialmanagerid;
	}
           
		
	
		
			
			
	public SysUser getUqaccountantid() {
		return uqaccountantid;
	}

	public void setUqaccountantid(SysUser uqaccountantid) {
		this.uqaccountantid = uqaccountantid;
	}
           
		
	
		
			
			
	public SysUser getUqcasherid() {
		return uqcasherid;
	}

	public void setUqcasherid(SysUser uqcasherid) {
		this.uqcasherid = uqcasherid;
	}
           
		
	
		
			
			
	public SysUser getUqcheckerid() {
		return uqcheckerid;
	}

	public void setUqcheckerid(SysUser uqcheckerid) {
		this.uqcheckerid = uqcheckerid;
	}
           
		
	
		
			
			
	public SysUser getUqfillerid() {
		return uqfillerid;
	}

	public void setUqfillerid(SysUser uqfillerid) {
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
	
			     		
	
			
		
	
  
    
	public TglGlobalPeriods getTglGlobalPeriodsList() {
		return tglGlobalPeriodsList;
	}

	public void setTglGlobalPeriodsList(TglGlobalPeriods tglGlobalPeriodsList) {
		this.tglGlobalPeriodsList = tglGlobalPeriodsList;
	}
	  

	public List<TglVoucherDetails> getTglVoucherDetailsList() {
		return tglVoucherDetailsList;
	}

	public void setTglVoucherDetailsList(List<TglVoucherDetails> tglVoucherDetailsList) {
		this.tglVoucherDetailsList = tglVoucherDetailsList;
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
        return "1457254743943432957";
    }
    
    /**
     * 获取实体对应描述
    */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "凭证主表";
    }
    
    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        
            return false;
        
    }
}
