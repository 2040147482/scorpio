package com.leslie.fastdfs.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * @author 20110
 * @description 导入FastDFS-Client组件
 */
@Configuration
@Import(FdfsClientConfig.class)
@PropertySource("application.yml")
public class FdfsConfiguration{

}
