package cn.edu.tju;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 家庭财务管理系统
 */
@MapperScan("cn.edu.tju.mapper")
@EnableTransactionManagement
@SpringBootApplication
public class FamilyFundsApplication {

    private static final Logger log = LoggerFactory.getLogger(FamilyFundsApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(FamilyFundsApplication.class, args);
        printAddress(ctx);
    }

    public static void printAddress(ApplicationContext ctx) {
        try {
            String host = InetAddress.getLocalHost().getHostAddress();
            TomcatServletWebServerFactory tomcat = (TomcatServletWebServerFactory) ctx.getBean("tomcatServletWebServerFactory");
            int port = tomcat.getPort();
            String contextPath = tomcat.getContextPath();
            log.info("######## 启动成功, 访问 http://{}:{}{}/", host, port, contextPath);
        } catch (UnknownHostException ignored) {
        }
    }
}
