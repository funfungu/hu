package com.sunny.maven;

import com.sunny.maven.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBatisTest {

    private SqlSession getSession() throws IOException {
        // 从classpath目录加载配置文件
        InputStream ips = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 获取Builder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 根据ips配置构建sessionFactory
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(ips);
        // sessionFactory打开一个会话， 进行数据库连接操作
        SqlSession session = sqlSessionFactory.openSession();
        return session;
    }

    @Test
    public void testSelect() throws IOException {
        SqlSession sqlSession = this.getSession();
        // 通过namespace 和 id 调用select SQL语句，设置占位参数，获取查询结果
        User user = sqlSession.selectOne("com.sunny.maven.pojo.User.selectUserById", 22);
        System.out.println(user.getId() + " : " + user.getUsername() + " : " + user.getAddress());
        // 释放资源
        sqlSession.close();
    }

    @Test
    public void testSelectByUsername() throws IOException {
        SqlSession sqlSession = this.getSession();
        List<User> users = sqlSession.selectList("com.sunny.maven.pojo.User.selectUserByUsername", "小");
        for (User user : users) {
            System.out.println(user.getId() + " : " + user.getUsername() + " : " + user.getAddress());
        }
        sqlSession.close();
    }
}
