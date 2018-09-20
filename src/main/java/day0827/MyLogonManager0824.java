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
 * springMVC的方法：
 * 1、 参数，灵活，可以是字符串，数值，表单对象，request,response,map
 * 	根据需要来进行定义
 * 2. 方法的返回值
 * 	void/ModelAndView/json(String)
 * 3.其需要的其他对象，一般不是new的，而是通过spring容器来装配ioc/di进行注入
 * 
 * 
 * 
 * 
 */


@Controller
public class MyLogonManager0824 {
	static HashSet<String> sids = new HashSet<String>();
	
	// @Autowired自动装配，在spring容器中寻找该对象，按照类型或者名称去匹配，如果有该对象，就自动装配
	@Autowired
	private Producer verifyCodeProducer ;// 该类google提供的自动生成图片验证码的一个算法集
	
	/*并不推荐在Controller中直接调用dao操作，
	 * 不符合5层结构， 表现层html/控制层dispatchServlet/服务层/持久层dao/db层
	 * 服务层：如果直接调用dao则忽略了服务层，
	 * 一般用Service去聚合多个业务操作，形成单独的业务操作
	 * 也有部分Service操作只关联一个dao操作
	 * Service对应的服务类，一般也被声明为@Service注解，
	 * 	并且需要在xml的context:component-scan base-package="services0827"能够扫描到该类
	 * 
	 * 
	 * 
	@Autowired
	private CustomerDao customerDao ;
	*/
	@Autowired
	CustomerServices customerServices ;
	
	
	@RequestMapping("logCodeCheckold.do") // 请求地址
	@ResponseBody // 是直接返回一个响应字符串，不是一个响应页面
	
	public String logCodeCheck(String code) { // 参数是一个单个值
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
		// 以前是ModelAndView

		return msg;

	}
	@RequestMapping("logCodeCheck.do") // 请求地址
	@ResponseBody // 是直接返回一个响应字符串，不是一个响应页面
	
	public String logCodeCheckNew(String code,HttpServletRequest req) { // 参数是一个单个值
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
		// 以前是ModelAndView

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
	@RequestMapping(path = "logCheck.do") // 请求地址
	@ResponseBody // 是直接返回一个响应字符串，不是一个响应页面
	public String logCheck(@RequestParam (value = "logName" ) String name ,@RequestParam (value = "logPass" )String pass,HttpServletRequest req) { // 参数是一个单个值
//	public String logCheck(String name , String pass,HttpServletRequest req) { // 参数是一个单个值
		//判断输入的用户名、密码是否正确
		
		System.out.println(pass);
		System.out.println(name);
		
		Map mp1 = new HashMap() ;
		mp1.put("logName", name) ;
		mp1.put("logPass", pass) ;
		
		
		Map rst = customerServices.findByLogNameAndLogPass(mp1) ;
		String msg = "success";
		if (rst!=null && rst.size() > 0){
			//在session中写入登录成功的信息
			LogInfo li = new LogInfo(rst,req.getRemoteAddr()) ;
			req.getSession().setAttribute("logInfo", li);			
		}
		else {
			msg = "no" + name;
		}
		// 以前是ModelAndView

		return msg;

	}
	@RequestMapping(path = "listCustomer.do") // 请求地址
	@ResponseBody // 是直接返回一个响应字符串，不是一个响应页面
//	public  Object listCustomer( ) { // 参数是一个单个值
	public  Object listCustomer( @RequestParam (value = "start" ) @NumberFormat  int start , @RequestParam (value = "end" ) @NumberFormat  int end,HttpServletRequest req,HttpServletResponse resp) throws IOException { // 参数是一个单个值
		//如果没有登录，跳转到登录
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
	@ResponseBody // 是直接返回一个响应字符串，不是一个响应页面
//	public  Object regCustomer( HttpServletRequest req ) { // 参数是一个单个值
	public  Object regCustomer(CustomerInfo ci,  HttpServletRequest req ) { // 参数是一个单个值
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
