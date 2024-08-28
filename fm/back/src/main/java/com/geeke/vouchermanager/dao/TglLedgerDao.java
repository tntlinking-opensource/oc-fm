package com.geeke.vouchermanager.dao;

import com.geeke.common.data.PageRequest;
import com.geeke.common.persistence.CrudDao;
import com.geeke.vouchermanager.entity.TglLedger;
import com.geeke.vouchermanager.entity.TglLedgetype;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分户DAO接口
 * @author
 * @version
 */
@Mapper
public interface TglLedgerDao extends CrudDao<TglLedger> {

    /**
     * 查询分户、分户初期余额集合（分页）
     * @param map
     * @return
     */
    List<TglLedger> listCompanyLedgerByLedgety(@Param("map") Map<String, Object> map);

    /**
     * 查询分户、分户初期余额数量
     * @param map
     * @return
     */
    int countAndCompanyLedger(@Param("map") Map<String, Object> map);
    int isDeleteByUqledgerid(TglLedger tglLedger);
    int isDeleteByUqledgetypeid(TglLedgetype tglLedgetype);
    void deleteGroup(String Uqledgeid);
    void insertLastLevelToGroupTable(List<TglLedger> entitys);

    void deleteLEDGERCOMPANY(String uqledgeid);

    void insertLEDGERCOMPANY(ArrayList<Map<String, String>> entitys);
}
