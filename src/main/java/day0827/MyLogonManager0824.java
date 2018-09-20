package day0827;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

import dao0827.CustomerDao;
import day0828.CustomerInfo;

import services0827.CustomerServices;
/*
 * springMVC�ķ�����
 * 1�� ���������������ַ�������ֵ��������request,response,map
 * 	������Ҫ�����ж���
 * 2. �����ķ���ֵ
 * 	void/ModelAndView/json(String)
 * 3.����Ҫ����������һ�㲻��new�ģ�����ͨ��spring������װ��ioc/di����ע��
 * 
 * 
 * 
 * 
 */


@Controller
public class MyLogonManager0824 {
	static HashSet<String> sids = new HashSet<String>();
	
	// @Autowired�Զ�װ�䣬��spring������Ѱ�Ҹö��󣬰������ͻ�������ȥƥ�䣬����иö��󣬾��Զ�װ��
	@Autowired
	private Producer verifyCodeProducer ;// ����google�ṩ���Զ�����ͼƬ��֤���һ���㷨��
	
	/*�����Ƽ���Controller��ֱ�ӵ���dao������
	 * ������5��ṹ�� ���ֲ�html/���Ʋ�dispatchServlet/�����/�־ò�dao/db��
	 * ����㣺���ֱ�ӵ���dao������˷���㣬
	 * һ����Serviceȥ�ۺ϶��ҵ��������γɵ�����ҵ�����
	 * Ҳ�в���Service����ֻ����һ��dao����
	 * Service��Ӧ�ķ����࣬һ��Ҳ������Ϊ@Serviceע�⣬
	 * 	������Ҫ��xml��context:component-scan base-package="services0827"�ܹ�ɨ�赽����
	 * 
	 * 
	 * 
	@Autowired
	private CustomerDao customerDao ;
	*/
	@Autowired
	CustomerServices customerServices ;
	
	
	@RequestMapping("logCodeCheckold.do") // �����ַ
	@ResponseBody // ��ֱ�ӷ���һ����Ӧ�ַ���������һ����Ӧҳ��
	
	public String logCodeCheck(String code) { // ������һ������ֵ
		System.out.println(this);
		if (code == null) {
			return "no";
		}
		String codeOld = "ABCD";
		String msg = "success";
		if (code.equals(codeOld)) {

		} else {
			msg = "no" + code;
		}
		// ��ǰ��ModelAndView

		return msg;

	}
	@RequestMapping("logCodeCheck.do") // �����ַ
	@ResponseBody // ��ֱ�ӷ���һ����Ӧ�ַ���������һ����Ӧҳ��
	
	public String logCodeCheckNew(String code,HttpServletRequest req) { // ������һ������ֵ
		System.out.println(this);
		if (code == null) {
			return "no";
		}
		Object oc = req.getSession().getAttribute("Mcode0824");
		if (oc == null) {
			return "no";
		}
		
		String codeOld = oc.toString();
		System.out.println(codeOld);
		String msg = "success";
		if (code.equals(codeOld)) {

		} else {
			msg = "no" + code;
		}
		// ��ǰ��ModelAndView

		return msg;

	}

	
	@RequestMapping(path = "getVerifyCodeImage.do", method = RequestMethod.GET)
	//
	public void getVerifyCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();

		// ResponseUtils.noCache(response);
		response.setContentType("image/jpeg");
		String capText = verifyCodeProducer.createText();
		System.out.println(request.getRemoteAddr() + " " + capText);
		session.setAttribute("Mcode0824", capText);
		BufferedImage bi = verifyCodeProducer.createImage(capText);
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			out.flush();
		} catch (Exception ex) {
			// LOGGER.error("Failed to produce the verify code image: ", ex);
			throw new IOException("Cannot produce the verify code image.");
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
	@RequestMapping(path = "logCheck.do") // �����ַ
	@ResponseBody // ��ֱ�ӷ���һ����Ӧ�ַ���������һ����Ӧҳ��
	public String logCheck(@RequestParam (value = "logName" ) String name ,@RequestParam (value = "logPass" )String pass,HttpServletRequest req) { // ������һ������ֵ
//	public String logCheck(String name , String pass,HttpServletRequest req) { // ������һ������ֵ
		//�ж�������û����������Ƿ���ȷ
		
		System.out.println(pass);
		System.out.println(name);
		
		Map mp1 = new HashMap() ;
		mp1.put("logName", name) ;
		mp1.put("logPass", pass) ;
		
		
		Map rst = customerServices.findByLogNameAndLogPass(mp1) ;
		String msg = "success";
		if (rst!=null && rst.size() > 0){
			//��session��д���¼�ɹ�����Ϣ
			LogInfo li = new LogInfo(rst,req.getRemoteAddr()) ;
			req.getSession().setAttribute("logInfo", li);			
		}
		else {
			msg = "no" + name;
		}
		// ��ǰ��ModelAndView

		return msg;

	}
	@RequestMapping(path = "listCustomer.do") // �����ַ
	@ResponseBody // ��ֱ�ӷ���һ����Ӧ�ַ���������һ����Ӧҳ��
//	public  Object listCustomer( ) { // ������һ������ֵ
	public  Object listCustomer( @RequestParam (value = "start" ) @NumberFormat  int start , @RequestParam (value = "end" ) @NumberFormat  int end,HttpServletRequest req,HttpServletResponse resp) throws IOException { // ������һ������ֵ
		//���û�е�¼����ת����¼
		HttpSession sess = req.getSession();
		if (sess.getAttribute("logInfo")==null){
			//resp.sendRedirect("Logon0824.html");
			//return null ;
		}
		
		
		
		System.out.println(" in listCustomer " +  this + "	" + start + "," + end );
//		System.out.println(" in listCustomer " +  this + "	"  );
		
		String msg = "success";
//		List<HashMap> ls = customerServices.findAll();
		HashMap mp = new HashMap() ;
		
		mp.put("start", start) ;
		mp.put("end", end) ;
		
		HashMap ls = customerServices.findAllForPage(mp);
		
		System.out.println(ls);
//		JSONArray ja = JSONArray.fromObject(ls);
		return ls;

	}	
	

	@RequestMapping(path = "regCustomer0824.do", method = RequestMethod.POST) 
	@ResponseBody // ��ֱ�ӷ���һ����Ӧ�ַ���������һ����Ӧҳ��
//	public  Object regCustomer( HttpServletRequest req ) { // ������һ������ֵ
	public  Object regCustomer(CustomerInfo ci,  HttpServletRequest req ) { // ������һ������ֵ
		sids.add(req.getSession().getId()) ;
		System.out.println("-------in---" + this + " " + sids.size());
		
		System.out.println(req.getParameterMap());
//		System.out.println(ci);
		System.out.println(ci.getCstNo());
		System.out.println(ci.getCstName());
		System.out.println(ci.getCstBdt());
		System.out.println(ci.getCstBdtNew());
		System.out.println(ci.getLogName());
		System.out.println(ci.getLogPass());
		System.out.println(ci.getCstLevel());
//		System.out.println("in regCustomer " + this );
//		System.out.println("in regCustomer " + ci );
//		
		int x = customerServices.insert2(ci) ;
		if (x > 0){
			return "ok";
		}else{
			return "errors" ;
		}
//		return "ok" ;
	}
}
