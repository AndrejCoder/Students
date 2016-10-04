package lanit.dao;

import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * Класс для упрощения вызовов запросов к БД.
 * 
 * @author Белов А.В.
 */
public class SqlMapTemplate {

    /** Фабрика для получения сессий подключения. */
    private final SqlSessionFactory sqlMapper;

    /** Конструктор. */
    /**
     * @param sqlMapper - фабрика сессий подключения.
     */


    public SqlMapTemplate( SqlSessionFactory sqlMapper ) {
        this.sqlMapper = sqlMapper;
    }

    protected final List< ? > queryForList( final String query, final Object parameters ) {
        try (SqlSession sqlSession = sqlMapper.openSession()) {
            return sqlSession.selectList(query, parameters);
        }
    }

    protected final Map< ?, ? > queryForMap( final String query, final Object parameters,
            final String key ) {
        try (SqlSession sqlSession = sqlMapper.openSession()) {
            return sqlSession.selectMap(query, parameters, key);
        }

    }

    protected final List< ? > queryForList( final String query, final Object parameters,
            final int first, final int count ) {
        try (SqlSession sqlSession = sqlMapper.openSession()) {
            return sqlSession.selectList(query, parameters, new RowBounds(first, count));
        }
    }

    protected final int queryForCount( final String query, final Object parameters ) {
        try (SqlSession sqlSession = sqlMapper.openSession()) {
            return (Integer) sqlSession.selectOne(query, parameters);
        }
    }

    protected final Object queryForObject( final String query, final Object parameters ) {
        try (SqlSession sqlSession = sqlMapper.openSession()) {
            return sqlSession.selectOne(query, parameters);
        }
    }

    protected final Object insert( final String query, final Object parameters ) {
        try (SqlSession sqlSession = sqlMapper.openSession()) {
            return sqlSession.insert(query, parameters);
        }
    }

    protected final int update( final String query, final Object parameters ) {
        try (SqlSession sqlSession = sqlMapper.openSession()) {
            return sqlSession.update(query, parameters);
        }
    }

    protected final int delete( final String query, final Object parameters ) {
        try (SqlSession sqlSession = sqlMapper.openSession()) {
            return sqlSession.delete(query, parameters);
        }
    }
}
