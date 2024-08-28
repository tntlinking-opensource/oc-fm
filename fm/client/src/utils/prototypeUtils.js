~(function () {
  //比较字符
  function eq(t = "") {
    if ("" === t) throw "StringUtils:Function:eq() 对比值不能为空";
    return this == t;
  }
  //替换所有
  function replacAllS(r = "", l = "") {
    if ("" === r && "" === l)
      return console.error("StringUtils:Function:replacAllS() 参数丢失"), !1;
    let e = JSON.parse(JSON.stringify(this)),
      t = this,
      n = 0;
    for (let i = 0; i < t.length; i++)
      t[i] == r && ((e = e.replace(r, l)), (n += 1));
    return 0 === n
      ? (console.error("StringUtils:Function:replacAllS() 未找到要替换的字符"),
        !1)
      : e;
  }

  function replaceLastOf(str) {
    let s = this;
    return s.substring(0, s.lastIndexOf(str));
  }

  function replaceFind(str, eq) {
    return this.indexOf(str) == -1 ? false : this.replace(str, eq);
  }

  function hasStr(str) {
    return this.indexOf(str) > -1;
  }

  function RegExpChinese() {
    let re = new RegExp(/[\u4E00-\u9FA5\uFE30-\uFFA0]/);

    return re.test(this);
  }

  function isNull() {
    return this == "";
  }

  function toNumber() {
    return Number(this);
  }

  /**
   * 判断数据是否为空
   * @param {*} array
   * @returns
   */
  function isAryNull() {
    return this.length <= 0;
  }

  /**
   * 插入字符串
   * @param {} start 
   * @param {*} newStr 
   * @returns 
   */
  function insertStr(start, newStr) {
    return this.slice(0, start) + newStr + this.slice(start);
  }
  String.prototype.insertStr = insertStr;
  String.prototype.hasStr = hasStr;
  String.prototype.RegExpChinese = RegExpChinese;
  String.prototype.replaceFind = replaceFind;
  String.prototype.replacAllS = replacAllS;
  String.prototype.replaceLastOf = replaceLastOf;
  String.prototype.isNull = isNull;
  String.prototype.eq = eq;
  String.prototype.toNumber = toNumber;
  Number.prototype.eq = eq;
  Number.prototype.toNumber = toNumber;
  Array.prototype.isAryNull = isAryNull;
})();
