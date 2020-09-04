package com.board.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//@Configuration : 자바 기반의 설정 파일로 인식
//@PropertySource : 해당 클래스에서 참조할 properties파일의 위치 설정
@Configuration
@PropertySource("classpath:/application.properties")
public class DBConfiguration {

	//@Autowired : 빈으로 등록된 인스턴스를 클래스에 주입하는 데에 사용
	//ApplicationContext :스프링 컨테이너 중 하나. 빈의 생성과 사용, 관계, 생명주기 등을 관리
	@Autowired
	private ApplicationContext applicationContext;
	
	//@Bean : 컨테이너에 의해 관리되는 빈으로 등록
	//		  Configuration클래스의 메서드 레벨에서만 지정 가능
	//@ConfigutaionProperties : 인자에 prefix 속성 지정 가능
	//	 	  prefix : @PropertySource에 지정된 파일에서 prefix에 해당하는 spring.datasource.hikari로 시작하는 설정을 모두 읽어들여 해당 메서드에 매핑!
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	//hikariCongif : 히카리 CP객체 생성(Connection Pool)
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean
	public DataSource dataSource() {
		//dataSource : 데이터소스 객체 생성
		//커넥션풀은 커넥션 객체를 미리 생성해두고, 데이터베이스에 접근하는 사용자에게 미리 생성해둔 커넥션을 제공했다가 다시 돌려받음
		//데이터소스는 커넥션 풀을 지원하기 위한 인터페이스
		return new HikariDataSource(hikariConfig());
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		//SqlSessionFacroty객체 생성
		//SqlSessionFactoryBean은 마이바티스와 스프링의 연동 모듈로 사용
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/**/*Mapper.xml"));
		factoryBean.setTypeAliasesPackage("com.board.domain");
		factoryBean.setConfiguration(mybatisConfg());
		return factoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSession() throws Exception {
		//SqlSessionTemplate : SqlSession 구현
		//SQL의 실행에 필요한 모든 메서드를 갖는 객체
		return new SqlSessionTemplate(sqlSessionFactory());
	}
	
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfg() {
		return new org.apache.ibatis.session.Configuration();
	}
	

}
