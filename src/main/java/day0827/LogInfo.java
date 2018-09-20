package day0827;

import java.util.Date;
import java.util.Map;

import tools.Tools;

public class LogInfo {
	public LogInfo(Map m,String ip){
		this.cstInfo = m;
		this.logIp = ip;
	}
	private Map cstInfo ;
	private Date dt1 = new Date() ;
	private String logIp ;
	public Map getCstInfo() {
		return cstInfo;
	}
	public void setCstInfo(Map cstInfo) {
		this.cstInfo = cstInfo;
	}
	public Date getDt1() {
		return dt1;
	}
	public String getDt12() {
		return Tools.datetimeToString(dt1);
	}
	


	
}
