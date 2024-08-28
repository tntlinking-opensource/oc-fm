package com.geeke.vouchermanager.entity;


import java.math.BigDecimal;

/**
 * 凭证明细信息
 * @author Administrator
 */

public class VoucherDetail 
{

	private String  uqvoucherdetailid;
	private String id;
	private String intisflow;
	private String  uqvoucherid;
	private int intseq;
	private String varabstract;
	private String uqcgsid;
	private String uqaccountid;
	private String accountcode;
	private String accountname;
	private int intisledge;
	private BigDecimal mnydebit;
	private BigDecimal mnycredit;
	private String uqpaytypeid;
	private String vartypeno;
	private String decnumber;
	private BigDecimal mnyprice;
	private BigDecimal mnyforeigncurr;
	private String decexchagerate;
	private String flowisbishu;

	public String getUqvoucherdetailid() {
		return uqvoucherdetailid;
	}
	public void setUqvoucherdetailid(String uqvoucherdetailid) {
		this.uqvoucherdetailid = uqvoucherdetailid;
	}
	public String getUqvoucherid() {
		return uqvoucherid;
	}
	public void setUqvoucherid(String uqvoucherid) {
		this.uqvoucherid = uqvoucherid;
	}
	public int getIntseq() {
		return intseq;
	}
	public void setIntseq(int intseq) {
		this.intseq = intseq;
	}
	public String getVarabstract() {
		return varabstract;
	}
	public void setVarabstract(String varabstract) {
		this.varabstract = varabstract;
	}
	public String getUqcgsid() {
		return uqcgsid;
	}
	public void setUqcgsid(String uqcgsid) {
		this.uqcgsid = uqcgsid;
	}
	public String getUqaccountid() {
		return uqaccountid;
	}
	public void setUqaccountid(String uqaccountid) {
		this.uqaccountid = uqaccountid;
	}
	public BigDecimal getMnydebit() {
		return mnydebit;
	}
	public void setMnydebit(BigDecimal mnydebit) {
		this.mnydebit = mnydebit;
	}
	public BigDecimal getMnycredit() {
		return mnycredit;
	}
	public void setMnycredit(BigDecimal mnycredit) {
		this.mnycredit = mnycredit;
	}
	public String getUqpaytypeid() {
		return uqpaytypeid;
	}
	public void setUqpaytypeid(String uqpaytypeid) {
		this.uqpaytypeid = uqpaytypeid;
	}
	public String getVartypeno() {
		return vartypeno;
	}
	public void setVartypeno(String vartypeno) {
		this.vartypeno = vartypeno;
	}
	public String getDecnumber() {
		return decnumber;
	}
	public void setDecnumber(String decnumber) {
		this.decnumber = decnumber;
	}
	public BigDecimal getMnyprice() {
		return mnyprice;
	}
	public void setMnyprice(BigDecimal mnyprice) {
		this.mnyprice = mnyprice;
	}
	public BigDecimal getMnyforeigncurr() {
		return mnyforeigncurr;
	}
	public void setMnyforeigncurr(BigDecimal mnyforeigncurr) {
		this.mnyforeigncurr = mnyforeigncurr;
	}
	public String getDecexchagerate() {
		return decexchagerate;
	}
	public void setDecexchagerate(String decexchagerate) {
		this.decexchagerate = decexchagerate;
	}
	public String getAccountcode() {
		return accountcode;
	}
	public void setAccountcode(String accountcode) {
		this.accountcode = accountcode;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public int getIntisledge() {
		return intisledge;
	}
	public void setIntisledge(int intisledge) {
		this.intisledge = intisledge;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIntisflow() {
		return intisflow;
	}

	public void setIntisflow(String intisflow) {
		this.intisflow = intisflow;
	}

	public String getFlowisbishu() {
		return flowisbishu;
	}

	public void setFlowisbishu(String flowisbishu) {
		this.flowisbishu = flowisbishu;
	}
}
