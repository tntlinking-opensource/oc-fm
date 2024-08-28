/**
 * 两层循环实现建树 把list数据通过 id，和parentId够造成一个树
 */
export function bulidTree(list) {
  let trees = [];
  list.forEach(function(value, i) {
    if ("0" === value.parentId) {
      trees.push(value);
    }
    value.children = [];

    list.forEach(function(child, i) {
      if (value.id === child.parentId) {
        value.children.push(child);
      }
    });
  });
  return trees;
}

export function isLightOrDark(rgbColor) {
  let currentRgb = rgbColor.replace("rgb(", "").replace(")", "");
  let currentRgbArr = currentRgb.split(",");
  let grayLevel =
    currentRgbArr[0] * 0.299 +
    currentRgbArr[1] * 0.587 +
    currentRgbArr[2] * 0.114;
  return grayLevel >= 192;
}

// 数字金额转大写
export const numToCny = money => {
  // 汉字的数字
  var cnNums = new Array(
    "零",
    "壹",
    "贰",
    "叁",
    "肆",
    "伍",
    "陆",
    "柒",
    "捌",
    "玖"
  );
  // 基本单位
  var cnIntRadice = new Array("", "拾", "佰", "仟");
  // 对应整数部分扩展单位
  var cnIntUnits = new Array("", "万", "亿", "兆");
  // 对应小数部分单位
  var cnDecUnits = new Array("角", "分", "毫", "厘");
  // 整数金额时后面跟的字符
  var cnInteger = "整";
  // 整型完以后的单位
  var cnIntLast = "元";
  // 最大处理的数字
  var maxNum = 999999999999999.9999;
  // 金额整数部分
  var integerNum;
  // 金额小数部分
  var decimalNum;
  // 输出的中文金额字符串
  var chineseStr = "";
  // 分离金额后用的数组，预定义
  var parts;
  if (money == "") {
    return "";
  }
  money = parseFloat(money);
  if (money >= maxNum) {
    // 超出最大处理数字
    return "";
  }
  if (money == 0) {
    chineseStr = cnNums[0] + cnIntLast + cnInteger;
    return chineseStr;
  }
  // 转换为字符串
  money = money.toString();
  if (money.indexOf(".") == -1) {
    integerNum = money;
    decimalNum = "";
  } else {
    parts = money.split(".");
    integerNum = parts[0];
    decimalNum = parts[1].substr(0, 4);
  }
  // 获取整型部分转换
  if (parseInt(integerNum, 10) > 0) {
    var zeroCount = 0;
    var IntLen = integerNum.length;
    for (var i = 0; i < IntLen; i++) {
      var n = integerNum.substr(i, 1);
      var p = IntLen - i - 1;
      var q = p / 4;
      var m = p % 4;
      if (n == "0") {
        zeroCount++;
      } else {
        if (zeroCount > 0) {
          chineseStr += cnNums[0];
        }
        // 归零
        zeroCount = 0;
        chineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
      }
      if (m == 0 && zeroCount < 4) {
        chineseStr += cnIntUnits[q];
      }
    }
    chineseStr += cnIntLast;
  }
  // 小数部分
  if (decimalNum != "") {
    var decLen = decimalNum.length;
    for (var i = 0; i < decLen; i++) {
      var n = decimalNum.substr(i, 1);
      if (n != "0") {
        chineseStr += cnNums[Number(n)] + cnDecUnits[i];
      }
    }
  }
  if (chineseStr == "") {
    chineseStr += cnNums[0] + cnIntLast + cnInteger;
  } else if (decimalNum == "") {
    chineseStr += cnInteger;
  }
  return chineseStr;
};

/**
 * 静态搜索，非后端搜索
 * @param {Array} array ['张三'] / [{name:'张三'}]
 * @param {String | Object} keyword  '张三' / {name:'张三'}
 */
export function staticSearch(array, keyword) {
  let temp = [];
  if (array.length <= 0) {
    console.warn("array is null");
    return [];
  }
  let bol = typeof array[0] == "object";
  for (let i = 0; i < array.length; i++) {
    if (bol) {
      for (let key in keyword) {
        if (array[i][key].indexOf(keyword[key]) > -1) {
          temp.push(array[i]);
        }
        break;
      }
    } else {
      if (array[i].indexOf(keyword) > -1) {
        temp.push(array[i]);
      }
    }
  }
  return temp;
}

/**
 * 找出数组中的出现次数最多的数
 * @param {Array} arr
 * @returns
 */
export function findMost(arr) {
  if (!arr.length) return;
  if (arr.length === 1) return 1;
  let res = {};
  let maxName,
    maxNum = 0;
  // 遍历数组
  arr.forEach(item => {
    res[item] ? (res[item] += 1) : (res[item] = 1);
    if (res[item] > maxNum) {
      maxName = item;
      maxNum = res[item];
    }
  });
  return maxName;
}
