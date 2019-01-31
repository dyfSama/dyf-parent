package com.dyf.tools.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.dyf.common.controller.BaseController;
import com.dyf.common.msg.MsgInfo;
import com.dyf.common.persistence.DataEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: CodeGeneratorController
 * @description: TODO
 * @auther: duyafei
 * @date: 2018/10/29 14:49
 */
@Slf4j
@Controller
@RequestMapping("generator")
public class CodeGenController extends BaseController {


    @ResponseBody
    @RequestMapping("mysqlGenerator")
    public MsgInfo MysqlGenerator(StrategyConfig sc) {

        return MsgInfo.success();
    }

    private void generator(StrategyConfig strategyConfig) {
        String[] include = {"sys_log"}; //表名数组
        String moduleName = "log"; //模块名
        String author = "dyfSama";  //作者
        String parentPacakge = "com.dyf.system"; //父包
        String mapperLocation = "/src/main/resources/mapper/com/dyf/system/"; //mapper位置
        String superControllerClass = BaseController.class.getName();//父controller
        String superEntityClass = DataEntity.class.getName();//父类实体
        //父类公共字段
        String[] superEntityColumns = {"id", "status", "create_date", "create_by", "update_date", "update_by", "remarks"};
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir") + File.separator + "dyf-admin";
        log.info("projectPath:  " + projectPath);
        gc.setOutputDir(projectPath + "/src/main/java");
//        gc.setOutputDir("/src/main/java");//输出路径
        gc.setAuthor(author);//作者
        gc.setOpen(false);//是否打开目录
        gc.setEnableCache(false); //mapper添加二级缓存配置
        gc.setFileOverride(true);//是否覆盖已有文件
        gc.setBaseColumnList(true); //开启 baseColumnList
        gc.setBaseResultMap(true); //开启 BaseResultMap
        gc.setDateType(DateType.ONLY_DATE);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
//        gc.setEntityName(moduleName);
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        // gc.setServiceName("MP%sService");
        // gc.setServiceImplName("%sServiceDiy");
        // gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.56.201:3306/dyfdb?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置(配置父包,各层包的名称)
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);//父包模块名
        pc.setParent(parentPacakge);//父包名
        mpg.setPackageInfo(pc);

        //  注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】  ${cfg.abc}
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("parentmoduleName", parentPacakge.substring(parentPacakge.lastIndexOf(".")+1,parentPacakge.length()+0));
                this.setMap(map);
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        //自定义一些模板
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + mapperLocation + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        TemplateConfig tc = new TemplateConfig();
        tc.setController("/templates/codeGen/controller.java.vm");
//        tc.setService("/templatesMybatis/service.java.vm");
//        tc.setServiceImpl("/templatesMybatis/serviceImpl.java.vm");
//        tc.setEntity("/templatesMybatis/entity.java.vm");
//        tc.setMapper("/templatesMybatis/mapper.java.vm");
//        tc.setXml("/templatesMybatis/mapper.xml.vm");

        mpg.setTemplate(tc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//表字段转实体属性:转下划线转驼峰命名
        strategy.setEntityLombokModel(true);//开启lombok
        strategy.setSuperControllerClass(superControllerClass);//自定义controller父类
        strategy.setInclude(include);//需要生成的表,多个用数组
        strategy.setSuperEntityClass(superEntityClass);//自定义entity父类
        strategy.setSuperEntityColumns(superEntityColumns);//自定义父类公共字段
        strategy.setControllerMappingHyphenStyle(false);
//        strategy.setTablePrefix("sys_");//表前缀, 有的话实体会去掉前缀
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    public static void main(String[] args) {
//        System.out.println(BaseController.class.getName());
        new CodeGenController().generator(null);
    }

}
