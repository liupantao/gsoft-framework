package com.gsoft.framework.job;

import com.gsoft.framework.context.annotation.Module;
/**
 * 
 * @author liupantao
 *
 */
@Module(
	name="job",
	scanPackages= { "conf", "convert" ,"service", "web.attr" },
	caption = "定时调度"
)
public class ModuleConfig {
	
}