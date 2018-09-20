package day0828;

import java.util.Date;



import org.springframework.format.annotation.DateTimeFormat;

import tools.Tools;


public class CustomerInfo {
	private Long cstNo ;
	private String cstName ;
	private String cstSex ;
	private String cstAds ;
//	@DateTimeFormat(pattern="yyyy-MM-dd")
//	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private Date cstBdt ;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date cstBdtNew;
	
	private String logName ;
	private String logPass ;
	private String logCode ;
	private String cstDesc ;
	private Integer cstLevel ;
	{
		System.out.println("in creating.......");
		System.out.println(cstName);
		System.out.println(cstSex);
		System.out.println(cstAds);
		System.out.println(cstName);
		System.out.println(cstName);
		
		
	}
	
	public Long getCstNo() {
		return cstNo;
	}
	public void setCstNo(Long cstNo) {
		this.cstNo = cstNo;
	}
	public String getCstName() {
		return cstName;
	}
	public void setCstName(String cstName) {
		this.cstName = cstName;
	}
	public String getCstSex() {
		return cstSex;
	}
	public void setCstSex(String cstSex) {
		this.cstSex = cstSex;
	}
	public String getCstAds() {
		return cstAds;
	}
	public void setCstAds(String cstAds) {
		this.cstAds = cstAds;
	}
 
	public String getLogName() {
		return logName;
	}
	public void setLogName(String logName) {
		this.logName = logName;
	}
	public String getLogPass() {
		return logPass;
	}
	public void setLogPass(String logPass) {
		this.logPass = logPass;
	}
	public String getLogCode() {
		return logCode;
	}
	public void setLogCode(String logCode) {
		this.logCode = logCode;
	}

	public Integer getCstLevel() {
		return cstLevel;
	}
	public void setCstLevel(Integer cstLevel) {
		this.cstLevel = cstLevel;
	}
	public Date getCstBdt() {
		return cstBdt;
	}
	 
	public void setCstBdt(String cstBdt) {
		this.cstBdt = Tools.stringToDatetime(cstBdt);
	}
	 

	public String getCstDesc() {
		return cstDesc;
	}
	public void setCstDesc(String cstDesc) {
		this.cstDesc = cstDesc;
	}
	public Date getCstBdtNew() {
		return cstBdtNew;
	}
	public void setCstBdtNew(Date cstBdtNew) {
		this.cstBdtNew = cstBdtNew;
	}	
	 
	
}
