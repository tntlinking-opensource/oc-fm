'use strict'
module.exports = {
  NODE_ENV: '"test"',
  // BASE_API: '"https://wx.lys.tntlinking.com/lease-backend-test"',
  BASE_API: '"/fm-back-test"',
  WEB_SOCKET_URL:'"/fm-back-test/websocket/"',

  // 企业微信授权登录
  REQUIRE_AUTH: true,
  //企业微信项目包路径
  CONTEXT_PATH: '"/lease-epwechat-test"',

  // 报表地址配置
  REPORT_CLIENT_URL: '"https://wx.lys.tntlinking.com/dataease-test/frontend/"',
  REPORT_SERVER_URL: '"https://wx.lys.tntlinking.com/dataease-test/backend/"',

  VUE_APP_API_OAUTH2_ENABLE: 'true',
  VUE_APP_API_OAUTH2_GRANTTYPE: '"password, client_credentials"', // 客户端授权码模式（授权码：authorization_code；密码模式：password；客户端模式：client_credentials）

}
