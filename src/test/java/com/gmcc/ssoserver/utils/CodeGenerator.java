package com.gmcc.ssoserver.utils;

import java.util.Scanner;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerator {
	public static String scanner(String tip) {
		try (Scanner scanner = new Scanner(System.in);) {

			StringBuilder help = new StringBuilder();
			help.append("请输入" + tip + "：");
			System.out.println(help.toString());
			if (scanner.hasNext()) {
				String input = scanner.next();
				if (StringUtils.isNotEmpty(input)) {
					return input;
				}
			}
		} catch (Exception e) {
		}
		throw new MybatisPlusException("请输入正确的" + tip + "！");
	}

	public static void main(String[] args) {
		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
//		dsc.setSchemaName("public");
		dsc.setUrl(
				"jdbc:mysql://localhost:3306/sso_server?characterEncoding=UTF-8&serverTimezone=GMT&nullCatalogMeansCurrent=true");
		dsc.setDriverName("com.mysql.cj.jdbc.Driver");
		dsc.setUsername("root");
		dsc.setPassword("root");
		mpg.setDataSource(dsc);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent("com.gmcc");
//		pc.setModuleName(scanner("模块名"));
		pc.setModuleName("ssoserver");
		pc.setEntity("entity");
		pc.setService("service");
		pc.setServiceImpl("service.impl");
		pc.setMapper("dao");
		pc.setXml("mapper");
		pc.setController("controller");
//		pc.setPathInfo(null);
		mpg.setPackageInfo(pc);

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		String projectPath = System.getProperty("user.dir");
		gc.setOutputDir(projectPath + "/src/main/java");
		gc.setFileOverride(false);
		gc.setOpen(false);
		gc.setEnableCache(false);
		gc.setAuthor("Kelvin@Inspur");
		gc.setKotlin(false);
		gc.setSwagger2(false);
		gc.setActiveRecord(false);
		gc.setBaseResultMap(true);
		gc.setBaseColumnList(true);
		gc.setDateType(DateType.TIME_PACK);
		gc.setEntityName("%sEntity");
		gc.setMapperName("%sDao");
		gc.setXmlName("%sMapper");
		gc.setServiceName("I%sService");
		gc.setServiceImplName("%sServiceImpl");
		gc.setControllerName("%sController");
		gc.setIdType(IdType.INPUT);
		mpg.setGlobalConfig(gc);

//		// 自定义配置
//		InjectionConfig cfg = new InjectionConfig() {
//			@Override
//			public void initMap() {
//				// to do nothing
//			}
//		};
//		cfg.setMap(null);
//
//		// 如果模板引擎是 freemarker
//		String templatePath = "/templates/mapper.xml.ftl";
//		// 如果模板引擎是 velocity
//		// String templatePath = "/templates/mapper.xml.vm";
//
//		// 自定义输出配置
//		List<FileOutConfig> focList = new ArrayList<>();
//		// 自定义配置会被优先输出
//		focList.add(new FileOutConfig(templatePath) {
//			@Override
//			public String outputFile(TableInfo tableInfo) {
//				// 自定义输出文件名
//				return projectPath + "/src/main/resources/mapper/" + pc.getModuleName() + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//			}
//		});
//
//		cfg.setFileOutConfigList(focList);
//		cfg.setFileCreate(null);
//		mpg.setCfg(cfg);

//		// 配置模板
//		TemplateConfig templateConfig = new TemplateConfig();
//
//		// 配置自定义输出模板
//		templateConfig.setEntity();
//		templateConfig.setService();
//		templateConfig.setController();

//		templateConfig.setXml(null);
//		mpg.setTemplate(templateConfig);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
//		strategy.setCapitalMode(false); // 是否大写命名
		strategy.setSkipView(true); // 是否跳过视图
		strategy.setNaming(NamingStrategy.underline_to_camel); // 数据库表映射到实体的命名策略
		strategy.setColumnNaming(NamingStrategy.underline_to_camel); // 数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
		strategy.setTablePrefix(pc.getModuleName() + "_"); // 表前缀
//		strategy.setFieldPrefix(""); // 字段前缀
		strategy.setSuperEntityClass("com.gmcc.ssoserver.entity.BaseEntity"); // 自定义继承的Entity类全称，带包名
//		strategy.setSuperEntityColumns("create_time", "last_update_time"); // 自定义基础的Entity类，公共字段
//		strategy.setSuperMapperClass("com.cmcc.DataPortalSt.common.BaseDao"); // 自定义继承的Mapper类全称，带包名
//		strategy.setSuperServiceClass("com.cmcc.DataPortalSt.common.IService"); // 自定义继承的Service类全称，带包名
//		strategy.setSuperServiceImplClass("com.cmcc.DataPortalSt.common.ServiceImpl"); // 自定义继承的ServiceImpl类全称，带包名
		strategy.setSuperControllerClass("com.gmcc.ssoserver.controller.BaseController"); // 自定义继承的Controller类全称，带包名
		strategy.setInclude(scanner("表名")); // 需要包含的表名，允许正则表达式（与exclude二选一配置）
//		strategy.setExclude(""); // 需要排除的表名，允许正则表达式
		strategy.setEntityColumnConstant(true); // 【实体】是否生成字段常量
		strategy.setEntityBuilderModel(false); // 【实体】是否为构建者模型
		strategy.setEntityLombokModel(false); // 【实体】是否为lombok模型
		strategy.setEntityBooleanColumnRemoveIsPrefix(false); // Boolean类型字段是否移除is前缀
		strategy.setRestControllerStyle(false); // 生成 @RestController 控制器
		strategy.setControllerMappingHyphenStyle(true); // 驼峰转连字符
		strategy.setEntityTableFieldAnnotationEnable(true); // 是否生成实体时，生成字段注解
//		strategy.setVersionFieldName(""); // 乐观锁属性名称
//		strategy.setLogicDeleteFieldName("status"); // 逻辑删除属性名称
//		strategy.setTableFillList(null); // 表填充字段
		mpg.setStrategy(strategy);

//		mpg.setTemplateEngine(new FreemarkerTemplateEngine());
		mpg.execute();
	}

}