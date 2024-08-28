<template>
  <div>
    <el-row :gutter="18">
      <edit-form
        ref="editForm"
      ></edit-form>
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-col :span="12">
          <el-form-item label="预测" prop="num">
            <el-cascader v-model="formData.num" :options="numOptions" :props="numProps"
                         :style="{width: '100%'}" placeholder="按年预测" clearable></el-cascader>
          </el-form-item>
        </el-col>
        <el-col :span="3">
          <el-form-item label-width="0" prop="field104">
            <el-button type="primary" icon="el-icon-search" @click="submitForm" size="medium"> 开始预测</el-button>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label-width="0" prop="field105">
            <el-button type="primary" icon="el-icon-search" @click="onView" size="medium"> 查看明细</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
    <el-table
      :data="tableData"
      highlight-current-row
      @current-change="handleCurrentChange1"
      style="width: 100%">
      <el-table-column
        prop="date_text"
        label="时间"
        width="180">
      </el-table-column>
      <el-table-column
        prop="value"
        label="原值"
        width="180">

      </el-table-column>
      <el-table-column
        prop="accdep"
        label="累计折旧"
        width="180">

      </el-table-column>
      <el-table-column
        prop="currdep"
        label="计提额"
        width="180">

      </el-table-column>
      <el-table-column
        prop="netvalue"
        label="净值"
        width="180">

      </el-table-column>
      <el-table-column
        prop="oldervalue"
        label="愈龄资产"
        width="180">

      </el-table-column>
      <el-table-column
        prop="number"
        label="卡片张数"
        width="180">
      </el-table-column>

    </el-table>
    <el-pagination
      :total="tableDataTotal"
      :page-size="search.limit"
      background
      :current-page="tableDataPage"
      :page-sizes="[10, 20, 30, 40, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      v-on:size-change="onSizeChange"
      v-on:current-change="onCurrentChange"
      class="dc-el-pagination_ElPagination"
    ></el-pagination>
  </div>
</template>
<script>
import { proj,projlist } from '@/api/cardasset/projection/projection'
import DcMain from "@/views/components/dcMain";
import EditForm from './form'
export default {
  extends: DcMain,
  components: {EditForm},
  props: [],
  data() {
    return {
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      formData: {
        num: [0, 2],
        field104: undefined,
        field105: undefined,
      },
      rules: {
        num: [{
          required: true,
          type: 'array',
          message: '请至少选择一个预测',
          trigger: 'change'
        }],
      },
      numOptions: [{
        "label": "按年预测",
        "value": 0,
        "id": 100,
        "children": [{
          "label": "1年",
          "value": 1,
          "id": 102
        }, {
          "label": "2年",
          "value": 2,
          "id": 103
        }, {
          "label": "3年",
          "value": 3,
          "id": 104
        }, {
          "label": "4年",
          "value": 4,
          "id": 105
        }, {
          "label": "5年",
          "value": 5,
          "id": 106
        }, {
          "label": "6年",
          "value": 6,
          "id": 107
        }, {
          "label": "7年",
          "value": 7,
          "id": 108
        }, {
          "label": "8年",
          "value": 8,
          "id": 109
        }, {
          "label": "9年",
          "value": 9,
          "id": 110
        }, {
          "label": "10年",
          "value": 10,
          "id": 111
        }, {
          "label": "11年",
          "value": 11,
          "id": 112
        }, {
          "label": "12年",
          "value": 12,
          "id": 113
        }, {
          "label": "13年",
          "value": 13,
          "id": 114
        }, {
          "label": "14年",
          "value": 14,
          "id": 115
        }, {
          "label": "15年",
          "value": 15,
          "id": 116
        }, {
          "label": "16年",
          "value": 16,
          "id": 117
        }, {
          "label": "17年",
          "value": 17,
          "id": 118
        }, {
          "label": "18年",
          "value": 18,
          "id": 119
        }, {
          "label": "19年",
          "value": 19,
          "id": 120
        }, {
          "label": "20年",
          "value": 20,
          "id": 121
        }]
      }, {
        "label": "按月预测",
        "value": 1,
        "id": 101,
        "children": [
          {
          "label": "1月",
          "value": 1,
          "id": 131
        },  {
          "label": "2月",
          "value": 2,
          "id": 132
        },  {
          "label": "3月",
          "value": 3,
          "id": 133
        },  {
          "label": "4月",
          "value": 4,
          "id": 134
        },  {
          "label": "5月",
          "value": 5,
          "id": 135
        },  {
          "label": "6月",
          "value": 6,
          "id": 136
        },  {
          "label": "7月",
          "value": 7,
          "id": 137
        },  {
          "label": "8月",
          "value": 8,
          "id": 138
        },  {
          "label": "9月",
          "value": 9,
          "id": 139
        },  {
          "label": "10月",
          "value": 10,
          "id": 140
        },  {
          "label": "11月",
          "value": 11,
          "id": 141
        },  {
          "label": "12月",
          "value": 12,
          "id": 142
        },
        ]
      }],
      numProps: {
        "multiple": false,
        "label": "label",
        "value": "value",
        "children": "children"
      },
      tableData: [],
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      currentRow:''
    }
  },
  computed: {},
  watch: {},
  created() {
  },
  mounted() {
  },
  methods: {
    onSizeChange(val) {
      this.tableDataPage = 1
      this.search.limit = val
      this.search.offset = (this.tableDataPage - 1) * val
      this.getList()
    },
    onCurrentChange(val) {
      this.search.offset = (val - 1) * this.search.limit
      this.tableDataPage = val
      this.getList()
    },
    onView() {
      this.$refs.editForm.$emit("openViewDialog", this.currentRow);
    },
    handleCurrentChange1(val){
      this.currentRow = val;
    },
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        // TODO 提交表单
        console.log(this.formData.num)
        let data={
          type:this.formData.num[0],
          num:this.formData.num[1]
        }
        console.log(data)
        proj(data)
          .then((responseData) => {
            if (responseData.code == 100) {
              this.search.params = []
              this.search.params.push({
                columnName: 'card_code',

                queryType: 'like',

                value: this.formData.num[0],
              })
              projlist(this.search)
                .then((responseData) => {
                  if (responseData.code == 100) {
                    this.tableDataTotal = responseData.data.total
                    this.tableData = responseData.data.rows ? responseData.data.rows : []
                  } else {
                    this.showMessage(responseData)
                  }
                  this.resetLoad()
                })
                .catch((error) => {
                  this.outputError(error)
                })
            } else {
              this.showMessage(responseData)
            }
            this.resetLoad()
          })
          .catch((error) => {
            this.outputError(error)
          })
      })
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
    },
  }
}

</script>
<style>
</style>
