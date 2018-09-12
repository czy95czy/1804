package cn.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cn.jt.feign.EurekaServiceFeign;
import cn.jt.feign.SidecarServiceFeign;

@RestController
public class HelloController {
	@Autowired
	private EurekaServiceFeign eurekaServiceFeign;
	@Autowired
	private SidecarServiceFeign sidecarServiceFeign;

	// 用户访问restful url请求
	@RequestMapping("/hello/{name}")
	// 支持断路器Hystrix,fallbackMethod参数就是失败时方法名
	@HystrixCommand(fallbackMethod = "fallbackHello")
	public String hello(@PathVariable("name") String name) {
		return eurekaServiceFeign.hello(name);
	}

	// 写一个方法业务访问失败时调用的方法,要求:参数和返回值要调用微服务一致
	public String fallbackHello(String name) {
		return "hey!!!!!! error!!!!!!";// 设置默认的返回值
	}
	
	//实现sidecar和nodejs封装,返回欢迎页面
	@RequestMapping("/")
	public String node() {
		return sidecarServiceFeign.node();//在feign接口中定义
	}
}
