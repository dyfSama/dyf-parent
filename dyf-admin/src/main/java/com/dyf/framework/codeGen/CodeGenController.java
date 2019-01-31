package com.dyf.framework.codeGen;

import com.alibaba.druid.pool.DruidDataSource;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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

    @Resource
    private DruidDataSource dataSource;

    @ResponseBody
    @PostMapping("mysqlGenerator")
    public MsgInfo MysqlGenerator(StrategyConfig strategyConfig, PackageConfig packageConfig, GlobalConfig globalConfig) {
        //策略
        String[] include = {"sys_menu"}; //表名数组
//        //包配置
        strategyConfig.setInclude(include);//表名数组
        packageConfig.setModuleName("menu");//模块名
        String parentPackage = "com.dyf.system";//父包
        String parentModuleName = "system"; //父模块
        packageConfig.setParent(parentPackage);//父包
        strategyConfig.setSuperControllerClass(BaseController.class.getName());//自定义controller父类
        strategyConfig.setEntityLombokModel(true);//开启lombok
        strategyConfig.setSuperEntityClass(DataEntity.class.getName());//自定义entity父类
        String[] superEntityColumns = {"id", "status", "create_date", "create_by", "update_date", "update_by", "remarks"};
        strategyConfig.setSuperEntityColumns(superEntityColumns);//自定义父类公共字段

        //全局配置
        globalConfig.setAuthor("dyfSama");//作者
        String projectPath = System.getProperty("user.dir") + File.separator + "dyf-admin";
        log.info("输出目录:  " + projectPath);
        globalConfig.setOutputDir(projectPath + "/src/main/java");

        try {
            generator(strategyConfig, packageConfig, globalConfig, projectPath,parentPackage, parentModuleName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return MsgInfo.error(e.getMessage());
        }
        return MsgInfo.success();
    }

    private void generator(StrategyConfig strategy, PackageConfig pc, GlobalConfig gc, String projectPath,String parentPackage,String parentModuleName ) throws Exception {
        String mapperLocation = "/src/main/resources/mapper/" + parentPackage.replaceAll("\\.","/") ; //mapper位置
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        gc.setOpen(false);//是否打开目录
        gc.setEnableCache(false); //mapper添加二级缓存配置
        gc.setFileOverride(true);//是否覆盖已有文件
        gc.setBaseColumnList(true); //开启 baseColumnList
        gc.setBaseResultMap(true); //开启 BaseResultMap
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dataSource.getUrl());
        dsc.setDriverName(dataSource.getDriverClassName());
        dsc.setUsername(dataSource.getUsername());
        dsc.setPassword(dataSource.getPassword());
        mpg.setDataSource(dsc);

        // 包配置(配置父包,各层包的名称)
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】  ${cfg.abc}
        // 配置父模块名
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("parentmoduleName", parentModuleName);//父模块名
                map.put("parentPackage", parentPackage);//父包名
                System.out.println(map);
                this.setMap(map);
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        //自定义一些模板
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + mapperLocation + "/" + pc.getModuleName() + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
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
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//表字段转实体属性:转下划线转驼峰命名
        strategy.setControllerMappingHyphenStyle(false);
//        strategy.setTablePrefix("sys_");//表前缀, 有的话实体会去掉前缀

        mpg.setStrategy(strategy);
        mpg.execute();
    }
}
