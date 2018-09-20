package dao0827;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import day0828.CustomerInfo;

public interface CustomerDao {
	public int insert2(Map cusInfo);
	public int insert3(CustomerInfo cusInfo);
	
	public int deleteById(long id);
	public int updateById(Map cusInfo);
	public Map findById(long id);
	public List<HashMap> findByName(String name);
	public List<HashMap> findByCond(Map cusInfo);
	
	
	public List<HashMap> findAll();
	public long findAllCount();
	public List<HashMap> findAllForPage(Map mp);
	
	
	public Map findByLogNameAndLogPass(Map map);
}
