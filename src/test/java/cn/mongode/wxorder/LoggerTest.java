package cn.mongode.wxorder;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

//    通过添加@Slf4j注解对log进行初始化
//    需要手动安装lombok插件，然后手动配置pom.xml中的依赖。
//    也可尝试直接在pom.xml中添加依赖，然后利用maven的重新导入功能安装组件。
//    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);
    
    @Test
    public void test1() {
        String name = "mongode";
        String password = "zxcvasdf";
        log.debug("logger debug ...");
        log.info("\nname:{}\npassword:{}",name,password);
        log.error("logger error ...");
        log.warn("logger warn ...");
    }
}
