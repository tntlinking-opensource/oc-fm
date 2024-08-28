'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // BASE_API: '"http://61.172.179.26:8099/organisation-back-test"',
  // BASE_API: '"http://localhost:9999"', // 开发测试微应用时 需要设置该路径，且 index.js 去掉 proxyTable
  BASE_API: '"/api"',	//proxy table启用时，只需要指定访问的路径， 开发测试非微应用时 开启，且 index.js 开启 proxyTable

  // 企业微信授权登录
  REQUIRE_AUTH: true,
    //企业微信项目包路径
    CONTEXT_PATH: '""',

  // 报表地址配置
  REPORT_CLIENT_URL: '"http://59.80.34.149:8099/dataease-dev/frontend/"',
  REPORT_SERVER_URL: '"http://59.80.34.149:8099/dataease-dev/backend/"',

  VUE_APP_API_OAUTH2_ENABLE: 'true',
  VUE_APP_API_OAUTH2_GRANTTYPE: '"password, client_credentials"', // 客户端授权码模式（授权码：authorization_code；密码模式：password；客户端模式：client_credentials）

})
