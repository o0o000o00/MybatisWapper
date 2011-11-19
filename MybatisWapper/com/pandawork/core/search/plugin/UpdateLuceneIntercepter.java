package com.pandawork.core.search.plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
@Intercepts({@Signature(type=Executor.class,method="update",args={MappedStatement.class,Object.class})})
public class UpdateLuceneIntercepter implements Interceptor {

	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		MappedStatement ms = (MappedStatement)args[0];
		Map<String, Object> param = (HashMap<String, Object>)args[1];
		System.out.println("sql@@@@@:"+ms.getBoundSql(param).getSql());
		Object result = invocation.proceed();
		System.out.println(result);
		return result;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {

	}

}
