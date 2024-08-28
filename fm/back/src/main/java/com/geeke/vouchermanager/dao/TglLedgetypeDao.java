package com.geeke.vouchermanager.dao;

import com.geeke.common.data.PageRequest;
import com.geeke.common.persistence.CrudDao;
import com.geeke.vouchermanager.entity.TglLedgetype;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 分户DAO接口
 * @author
 * @version
 */
@Mapper
public interface TglLedgetypeDao extends CrudDao<TglLedgetype> {

    /**
     *  根据科目ID、名称查询类别列表
     * @param uqaccountid
     * @param varledgetypename
     * @return
     */
    List<TglLedgetype> getLedgerTypeEntityByName(@Param("uqaccountid")String uqaccountid, @Param("varledgetypename")String varledgetypename);

    /**
     * 查询分户类型，包含子表分户明细
     * @param pageRequest
     * @return
     */
    List<TglLedgetype> listLedgetypeAndChild(PageRequest pageRequest);
    void callProcedure(@Param("parameters") Map<String, Object> parameters);
    void callProcedureGroup(@Param("parameters") Map<String, Object> parameters);
}
