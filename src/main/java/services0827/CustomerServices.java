package services0827;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao0827.CustomerDao;
import day0828.CustomerInfo;
@Service
public class CustomerServices {
	@Autowired
	private CustomerDao customerDao ;	
	public Map findByLogNameAndLogPass(Map map){
		//添加其他服务
		System.out.println(customerDao);
		return customerDao.findByLogNameAndLogPass(map);
	}
	public List<HashMap> findAll(){
		//添加其他服务
		
		return customerDao.findAll();
	}	
	public HashMap findAllForPage(HashMap mp){
		//添加其他服务
		
		List<HashMap> data =  customerDao.findAllForPage(mp);
		long count = customerDao.findAllCount();
		HashMap hm = new HashMap() ;
		hm.put("allRowData", data) ;
		long count1  = count / 10 ;
		if (count %10 > 0){
			count1 ++ ;
		}
		hm.put("allRows", count1) ;
		return hm;
		
		
	}	
	public int insert(Map mp){
		//添加其他服务
		
		return customerDao.insert2(mp);
	}	
	public int insert2(CustomerInfo ci){
		//添加其他服务
		
		return customerDao.insert3(ci);
	}	
	
}
