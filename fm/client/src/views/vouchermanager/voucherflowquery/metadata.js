




































            const conditions = [


              {
                name: 'UQVOUCHERID',
                comments: '凭证ID',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },

              {
                name: 'INTVOUCHERNUM',
                comments: '凭证编码',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },

              {
                name: 'UQNUMBERING',
                comments: '凭证类型',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },

              {
                name: 'UQCOMPANYID',
                comments: '凭证核算单位',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },

              {
                name: 'INTCOMPANYSEQ',
                comments: '流水号',
                tag: 'number-input',
                queryType: '=',
                operations: [{"value":"=","label":"等于"},{"value":"<>","label":"不等于"},{"value":"between","label":"介于"},{"value":"not between","label":"不介于"}],
                attribute: {":min":0,":max":999999999999,":precision":0}
              },

              {
                name: 'INTAFFIX',
                comments: '单据数',
                tag: 'number-input',
                queryType: '=',
                operations: [{"value":"=","label":"等于"},{"value":"<>","label":"不等于"},{"value":"between","label":"介于"},{"value":"not between","label":"不介于"}],
                attribute: {":min":0,":max":999999999999,":precision":0}
              },

              {
                name: 'UQGLOBALPERIODID',
                comments: '会计期',
                tag: 'el-select',
                queryType: '=',
                operations: [{"value":"=","label":"等于"},{"value":"<>","label":"不等于"},{"value":"in","label":"在列表"},{"value":"not in","label":"不在列表"}],
                attribute: {"labelField":"name","className":"TglGlobalPeriods","fieldId":"UQGLOBALPERIODID","tableId":"1457254743943432957","apiPath":"vouchermanager/tglGlobalPeriods","filter":""}
              },

              {
                name: 'MNYDEBITSUM',
                comments: '借方金额合计',
                tag: 'number-input',
                queryType: '=',
                operations: [{"value":"=","label":"等于"},{"value":"<>","label":"不等于"},{"value":"between","label":"介于"},{"value":"not between","label":"不介于"}],
                attribute: {":min":0,":max":999999999999,":precision":2}
              },

              {
                name: 'MNYCREDITSUM',
                comments: '贷方金额合计',
                tag: 'number-input',
                queryType: '=',
                operations: [{"value":"=","label":"等于"},{"value":"<>","label":"不等于"},{"value":"between","label":"介于"},{"value":"not between","label":"不介于"}],
                attribute: {":min":0,":max":999999999999,":precision":2}
              },

              {
                name: 'UQFINACIALMANAGERID',
                comments: '会计主管',
                tag: 'el-select',
                queryType: '=',
                operations: [{"value":"=","label":"等于"},{"value":"<>","label":"不等于"},{"value":"in","label":"在列表"},{"value":"not in","label":"不在列表"}],
                attribute: {"labelField":"name","className":"SysUser","fieldId":"id","tableId":"1457254743943432957","apiPath":"vouchermanager/sysUser","filter":""}
              },

              {
                name: 'UQACCOUNTANTID',
                comments: '记账人',
                tag: 'el-select',
                queryType: '=',
                operations: [{"value":"=","label":"等于"},{"value":"<>","label":"不等于"},{"value":"in","label":"在列表"},{"value":"not in","label":"不在列表"}],
                attribute: {"labelField":"name","className":"SysUser","fieldId":"id","tableId":"1457254743943432957","apiPath":"vouchermanager/sysUser","filter":""}
              },

              {
                name: 'UQCASHERID',
                comments: '出纳人',
                tag: 'el-select',
                queryType: '=',
                operations: [{"value":"=","label":"等于"},{"value":"<>","label":"不等于"},{"value":"in","label":"在列表"},{"value":"not in","label":"不在列表"}],
                attribute: {"labelField":"name","className":"SysUser","fieldId":"id","tableId":"1457254743943432957","apiPath":"vouchermanager/sysUser","filter":""}
              },

              {
                name: 'UQCHECKERID',
                comments: '审核人',
                tag: 'el-select',
                queryType: '=',
                operations: [{"value":"=","label":"等于"},{"value":"<>","label":"不等于"},{"value":"in","label":"在列表"},{"value":"not in","label":"不在列表"}],
                attribute: {"labelField":"name","className":"SysUser","fieldId":"id","tableId":"1457254743943432957","apiPath":"vouchermanager/sysUser","filter":""}
              },

              {
                name: 'UQFILLERID',
                comments: '制单人',
                tag: 'el-select',
                queryType: '=',
                operations: [{"value":"=","label":"等于"},{"value":"<>","label":"不等于"},{"value":"in","label":"在列表"},{"value":"not in","label":"不在列表"}],
                attribute: {"labelField":"name","className":"SysUser","fieldId":"id","tableId":"1457254743943432957","apiPath":"vouchermanager/sysUser","filter":""}
              },

              {
                name: 'DTACCOUNTANT',
                comments: '记账日期',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },

              {
                name: 'DTCASHER',
                comments: '出纳日期',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },

              {
                name: 'DTCHECKER',
                comments: '审核日期',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },

              {
                name: 'DTFILLER',
                comments: '制单日期',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },

              {
                name: 'DTACCOUNTANTSRV',
                comments: '记账服务器时间',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },

              {
                name: 'DTCASHERSRV',
                comments: '出纳服务器时间',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },

              {
                name: 'DTCHECKERSRV',
                comments: '审核服务器时间',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },

              {
                name: 'DTFILLERSRV',
                comments: '制单服务器时间',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },

              {
                name: 'INTFLAG',
                comments: '凭证标志',
                tag: 'number-input',
                queryType: '=',
                operations: [{"value":"=","label":"等于"},{"value":"<>","label":"不等于"},{"value":"between","label":"介于"},{"value":"not between","label":"不介于"}],
                attribute: {":min":0,":max":999999999999,":precision":0}
              },

              {
                name: 'INTCASHFLAG',
                comments: '出纳标志',
                tag: 'number-input',
                queryType: '=',
                operations: [{"value":"=","label":"等于"},{"value":"<>","label":"不等于"},{"value":"between","label":"介于"},{"value":"not between","label":"不介于"}],
                attribute: {":min":0,":max":999999999999,":precision":0}
              },

              {
                name: 'INTDELETEFLAG',
                comments: '删除标志(0是正常 1是删除)',
                tag: 'number-input',
                queryType: '=',
                operations: [{"value":"=","label":"等于"},{"value":"<>","label":"不等于"},{"value":"between","label":"介于"},{"value":"not between","label":"不介于"}],
                attribute: {":min":0,":max":999999999999,":precision":0}
              },

              {
                name: 'UQCANCELID',
                comments: '被冲销凭证ID',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },

              {
                name: 'FROMID',
                comments: '凭证接口所需字段',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },

              {
                name: 'SUBMITODD',
                comments: '报账单号（接口调用）限用于区分暂存凭证 （0-保存，1-暂存）',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },

              {
                name: 'CHARGEAGAINSTSUBMITODD',
                comments: '冲销报账单号（接口调用）//暂时用作存储交易时间',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },

              {
                name: 'tenant_id',
                comments: 'tenant_id',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },


            ]





























































































































































































































const metadata = [
	{
        id: '1457254743943432957',
        schemeId: '1457254743943432955',
        name: '凭证主表',
        conditionPanel: conditions,
        type: 'main',
        dataRules:[]
    }
]
export default metadata
