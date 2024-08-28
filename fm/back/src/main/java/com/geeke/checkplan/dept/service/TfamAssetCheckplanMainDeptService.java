package com.geeke.checkplan.dept.service;

import com.geeke.checkplan.dept.dao.TfamAssetCheckplanMainDeptDao;
import com.geeke.checkplan.dept.entity.TfamAssetCheckplanMainDept;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 资产盘点部门Service
 * @author
 * @version
 */

@Service("tfamAssetCheckplanMainDeptService")
@Transactional(readOnly = true)
public class TfamAssetCheckplanMainDeptService extends CrudService<TfamAssetCheckplanMainDeptDao, TfamAssetCheckplanMainDept> {
    public List<TfamAssetCheckplanMainDept> getByMainId(String mainId) {

        return this.dao.getByMainId(mainId);
    }

    public void deleteByMainId(String mainId) {
        this.dao.deleteByMainId(mainId);
    }

    public TfamAssetCheckplanMainDept selectVisable(String mainId, String deptId) {
        return this.dao.selectVisable(mainId,deptId);
    }
}
