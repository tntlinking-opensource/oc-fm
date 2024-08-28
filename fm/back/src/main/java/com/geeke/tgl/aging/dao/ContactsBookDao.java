package com.geeke.tgl.aging.dao;

import com.geeke.common.data.PageRequest;
import com.geeke.tgl.aging.entity.Aging;
import com.geeke.tgl.aging.entity.ContactsBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 往来账龄报表DAO接口
 * @author
 * @version
 */
@Mapper
public interface ContactsBookDao {

    /**
     * 查询数据条数(用在分页查询)
     * @param map
     * @return
     */
    public int count(@Param("map")Map<String, Object> map, @Param("agingList") List<Aging> agingList);

    /**
     * 分页查询数据列表
     * @param map
     * @return
     */
    public List<Map<String, Object>> listPage(@Param("map")Map<String, Object> map, @Param("agingList") List<Aging> agingList);

    /**
     * 查询数据列表
     * @param map
     * @return
     */
    public List<Map<String, Object>> listAll(@Param("map")Map<String, Object> map, @Param("agingList") List<Aging> agingList);

    public int getContactsAccouts(@Param("map")Map<String, Object> map);
}
