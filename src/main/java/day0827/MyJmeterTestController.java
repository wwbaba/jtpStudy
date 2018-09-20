package day0827;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class MyJmeterTestController {
	static int count ;
	@RequestMapping("test11.do")
	@ResponseBody
	public String test11(HttpServletRequest req){
		count++;
		System.out.println(count);
		System.out.println(req.getRemoteAddr());
		
		return "ok" + count;
	}
}
