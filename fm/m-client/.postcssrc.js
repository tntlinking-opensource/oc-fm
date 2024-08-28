/**
 * 忽略不要转换成 rem 的单位属性的技巧
 * 意思是 使用 'Px' 或 'PX' 就不会 => rem, 而又能被 浏览器 识别
 * 暂不支持 内联样式 => rem，样式尽量写在样式表里
 */
// `px` is converted to `rem`
// .convert {
// 	font-size: 16px; // converted to 1rem
// }

// `Px` or `PX` is ignored by `postcss-pxtorem` but still accepted by browsers
// .ignore {
// 	border: 1Px solid; // ignored
// 	border-width: 2PX; // ignored
// }

module.exports = {
  "plugins": {
    "autoprefixer": {
      "browsers": [
        "Android >= 4.0",
        "iOS >= 7"
      ]
    },
    "postcss-pxtorem": {
      "rootValue": 37.5,
      "propList": [
        "*"
      ]
    }
  }
}
