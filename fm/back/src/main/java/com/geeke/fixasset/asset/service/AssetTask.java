package com.geeke.fixasset.asset.service;

import com.geeke.fixasset.asset.entity.TfamAsset;
import com.geeke.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName AssetTask
 * @Description TODO
 * @Author 任杰
 * @Date 2023/8/30 12:09
 * @Version 1.0
 */
@Component
public class AssetTask {
    @Autowired
    private TfamAssetService assetService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void updateAsset() throws Exception{
        List<TfamAsset> assetList = assetService.listAll(null, null);
        if (assetList !=null && assetList.size()>0){
            Iterator<TfamAsset> iterator = assetList.iterator();
            while (iterator.hasNext()){
                TfamAsset asset = iterator.next();
                String startupDate = asset.getStartupDate();
                Integer naturemonth = asset.getNaturemonth();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(startupDate);
                Calendar start = Calendar.getInstance();
                start.setTime(date);

                Calendar end = Calendar.getInstance();
                end.setTime(new Date());
                int monthDifference = calculateMonthDifference(start, end);
                int servicemonth = naturemonth - monthDifference;
                asset.setServicemonth(servicemonth);
                assetService.save(asset);
            }
        }
    }

    public static int calculateMonthDifference(Calendar startDate, Calendar endDate) {
        int startYear = startDate.get(Calendar.YEAR);
        int startMonth = startDate.get(Calendar.MONTH);
        int endYear = endDate.get(Calendar.YEAR);
        int endMonth = endDate.get(Calendar.MONTH);

        int monthDifference = (endYear - startYear) * 12 + endMonth - startMonth;
        return monthDifference;
    }

}
