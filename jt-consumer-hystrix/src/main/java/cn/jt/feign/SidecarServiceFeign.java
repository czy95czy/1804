package cn.jt.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "sidecar")
public interface SidecarServiceFeign {
	// 访问sidecar，调用node.js欢迎页面
	@RequestMapping("/")
	public String node();
}
