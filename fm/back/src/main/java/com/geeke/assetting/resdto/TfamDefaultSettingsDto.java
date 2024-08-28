package com.geeke.assetting.resdto;

import com.geeke.assetting.entity.TfamDefaultSettings;
import com.geeke.vouchermanager.entity.TglAccounts;
import lombok.Data;

/**
 * @ClassName TfamDefaultSettingsDto
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/8 10:08
 * @Version 1.0
 */
@Data
public class TfamDefaultSettingsDto extends TfamDefaultSettings {
    /**
     * info的名称
     */
    private TglAccounts tglAccounts;
}
