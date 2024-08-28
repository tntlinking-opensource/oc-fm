package com.geek.fixasset.asset;

import com.geeke.MyApplication;
import com.geeke.common.data.Parameter;
import com.geeke.fixasset.asset.entity.TfamAsset;
import com.geeke.fixasset.asset.service.TfamAssetService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AssetTest
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/2 11:32
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class)
@Slf4j
public class AssetTest {

    @Autowired
    private TfamAssetService tfamAssetService;

    @Test
    public void assetFunctionTest(){
        List<Parameter> parameters =new ArrayList<>();
        List<TfamAsset> tfamAssets = tfamAssetService.listAll(parameters, null);
        System.out.println(tfamAssets);
    }
}
