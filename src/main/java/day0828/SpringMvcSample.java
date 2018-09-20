package day0828;
/*
 * springMvc开发总结：
 * 1. 需要一个控制器，在web.xml中配置
 * DispatcherServlet:
 * 2. 需要一个spring的web配置文件
 * [dispatch-servlet]-servlet.xml文件，在WEB-INF目录下，和web.xml同目录
 * 3. 该spring配置文件中需要配置的选项：
 * viewResolver：视图解析器
 * 	指定未来的jsp/html页面的解析方式/ jsp+model，构成需要返回的数据
 * <mvc:default-servlet-handler />	
 * 针对非springmvc控制的内容，例如js/css/img等进行默认处理，配置适配器
 * 
 * <context:component-scan base-package="day0827,day0828" /> 
 * 指定了所有的controller类@Controller注解需要扫描的包路径,
 * 扫描该包中的所有的类，如果有@Controller注解，则springMVC自动生成该类的实例并载入到springweb容器中
 * 
 * src/applicationContext.xml配置
 * 
 * 
 */

public class SpringMvcSample {

}
