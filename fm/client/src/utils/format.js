
/**
* 实现千分位过滤器
* decimal 保留小数位数  默认为两位小数
**/
export function nowDate() {
  let date = new Date();
  let year = date.getFullYear(); //月份从0~11，所以加一
  let dateArr = [date.getMonth() + 1,date.getDate(),date.getHours(),date.getMinutes(),date.getSeconds()];
  for(var i=0;i<dateArr.length;i++){
    if (dateArr[i] >= 1 && dateArr[i] <= 9) { 
        dateArr[i] = "0" + dateArr[i];
    }
  }
  let strDate = year+'-'+dateArr[0]+'-'+dateArr[1];
  return strDate
}

export function nowDateall() {
  let date = new Date();
  let year = date.getFullYear(); //月份从0~11，所以加一
  let dateArr = [date.getMonth() + 1,date.getDate(),date.getHours(),date.getMinutes(),date.getSeconds()];
  for(var i=0;i<dateArr.length;i++){
    if (dateArr[i] >= 1 && dateArr[i] <= 9) { 
        dateArr[i] = "0" + dateArr[i];
    }
  }
  let strDate = year+'-'+dateArr[0]+'-'+dateArr[1]+' '+dateArr[2]+':'+dateArr[3]+':'+dateArr[4];
  return strDate
}

export function dateMonths(date1, date2) {
    // 拆分年月日
    date1 = date1.split('-');
    // 得到月数
    date1 = parseInt(date1[0]) * 12 + parseInt(date1[1]);
    // 拆分年月日
    date2 = date2.split('-');
    // 得到月数
    date2 = parseInt(date2[0]) * 12 + parseInt(date2[1]);
    var m = Math.abs(date1 - date2);
    return m;
}


