<template>
  <van-row class="dm-container">
    <history ref="history" :bussObject="curentRow"></history>
    <edit-form
      ref="editForm"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
      :permission="permission">
    </edit-form>

    <div class="main" style="width: 100%; height: 100%; position: absolute">
      <div
        class="quarter-div"
        border
        style="width: 50%; height: 50%; float: left; ">
        <div id="myChart"
             style="width: 100%; height: 100%;"
        ></div>
      </div>
      <div
        class="quarter-div"
        border
        style="width: 50%; height: 50%; float: left;"
      >
        <div id="myChart2"
             style="width: 100%; height: 100%; "
        ></div>

      </div>
      <div
        class="quarter-div"
        border
        style="width: 50%; height: 50%; float: left;"
      >
        <div id="myChart3"
             style="width: 100%; height: 100%;"
        ></div>

      </div>
      <div
        class="quarter-div"
        border
        style="width: 50%; height: 50%; float: left;"
      >
        <div id="myChart4"
             style="width: 100%; height: 100%;"
        ></div>
      </div>
    </div>

  </van-row>
</template>
<script>
import {validatenull} from '@/utils/validate'

import {getListALL} from '@/api/fixasset/statistics/statistics_api.js'


import {listResourcePermission} from '@/api/admin/common/permission'

/** 根据用户界面配置import组件 开始 */
import History from '@/views/components/history'
import EditForm from './form'
import QueryTag from '@/views/components/queryTag'
import QueryConditionPanel from '@/views/components/queryConditionPanel'
import ViewColumnsSelect from '@/views/components/DcViewColumnsSelect'
/** 根据用户界面配置import组件 结束 */
import ExportExcelButton from '@/components/ExportExcelButton'
import DcMain from '@/views/components/dcMain'
import OperationIcon from '@/components/OperationIcon'

import axios from "axios"

export default {
  extends: DcMain,
  components: {
    /** 根据用户界面配置组件 开始 */
    History,
    EditForm,
    QueryTag,
    QueryConditionPanel,
    ViewColumnsSelect,
    /** 根据用户界面配置组件 结束 */
    ExportExcelButton,
    OperationIcon
  },
  data() {
    return {
      dept_data:[],
      asset_category_data:[],
      asset_scrap_data:[],
      asset_data:[]
    }
  },
  computed: {},
  methods: {

    drawChart2() {


      var asset_category = this.asset_category_data;
      var deptName = [];
      var deptId = [];
      var accudep = [];
      var netvalue = [];
      var num = [];
      var value = [];


      for (let i = 0; i < asset_category.length; i++) {
        var v = asset_category[i];
        deptName.push(v.name||"");
        deptId.push(v.id);
        accudep.push(v.accudep);
        netvalue.push(v.netvalue);
        num.push(v.num);
        value.push(v.value)
      }

      var xValue = [];
      // $.each(deptName,function(k,v){
      //   xValue.push({
      //     value : num[k],
      //     name : deptName[k]
      //   })
      // })
      for (let i = 0; i < deptName.length; i++) {
        xValue.push({
          value: num[i],
          name: deptName[i]
        })
      }

      let myChart = this.$echarts.init(document.getElementById("myChart2"));
      let option = {
        title: {
          text: '资产类别统计报表',
          // left: 'center'
        },
        grid: {
          top: '2%'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          left: 'right',
          // top: '25',
          data: deptName
        },
        /*
                color: ['#8378ea','#ffdb5c','#9fe6b8','#32c5e9','#fb7293','#f0d876','#FFEBCD',' #79CDCD','#54FF9F','#FFEC8B','#8B8B7A'],
        */
        toolbox: {
          show: false,
          orient: 'vertical',
          left: 'right',
          top: 'center',
          feature: {
            mark: {show: false},
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'stack']},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        series: [
          {
            name: '数量',
            type: 'pie',
            radius: [20, 110],
            center: ['45%', '50%'],
            data: xValue,
            roseType: 'radius',
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      myChart.setOption(option);


    },
    drawChart() {


      var deptName = [];
      var deptId = [];
      var accudep = [];
      var netvalue = [];
      var num = [];
      var value = [];

      for (let i = 0; i < this.dept_data.length; i++) {
        var v = this.dept_data[i];
        deptName.push(v.name || "");
        deptId.push(v.id);
        accudep.push(v.accudep);
        netvalue.push(v.netvalue);
        num.push(v.num);
        value.push(v.value)
      }

      let myChart = this.$echarts.init(document.getElementById("myChart"));
      let option = {
        title: {
          text: '部门资产统计报表',
          // left: 'center'
        }, grid: {
          left: '18%',
          bottom: '38%'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          left: 'right',
          // top: '25',
          data: ['数量', '原值', '累计折旧', '净值']
        },
        toolbox: {
          show: false,
          orient: 'vertical',
          left: 'right',
          top: 'center',
          feature: {
            mark: {show: true},
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'stack']},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        xAxis: [
          {
            type: 'category',
            name: '部门',
            axisTick: {show: false},
            data: deptName,
            axisLabel: {
              interval: 0, //坐标刻度之间的显示间隔，默认就可以了（默认是不重叠）
              rotate: 38   //调整数值改变倾斜的幅度（范围-90到90）
            }
          }
        ],
        yAxis: [
          {
            // axisLabel: function () {
            //     return "";
            // }
          }
        ],
        series: [
          {
            name: '数量',
            type: 'bar',
            barGap: 0,
            data: num
          },
          {
            name: '原值',
            type: 'bar',
            data: value
          },
          {
            name: '累计折旧',
            type: 'bar',
            data: accudep
          },
          {
            name: '净值',
            type: 'bar',
            data: netvalue
          }
        ]
      };

      myChart.setOption(option);
    },
    drawChart4() {
      var asset_data1 = this.asset_data;
      var transNum = [];
      var transTime = [];
      for (let i = 0; i < asset_data1.length; i++) {
        var v = asset_data1[i];
        transNum.push(v.num);
        transTime.push(v.time);
      }
      let myChart = this.$echarts.init(document.getElementById("myChart4"));
      let option = {
        grid: {
          bottom: '38%'
        },
        title: {
          text: '月度资产调拨情况报表',
          left: 'center'
        },
        legend: {
          right: 10,
          data: ['调拨次数'],
          top: '25',
          left: 'center'
        },
        xAxis: {
          splitLine: {
            lineStyle: {
              type: 'dashed'
            }
          },
          data: transTime,
          axisLabel: {
            interval: 0, //坐标刻度之间的显示间隔，默认就可以了（默认是不重叠）
            rotate: 38,  //调整数值改变倾斜的幅度（范围-90到90）
            formatter: function (val) {
              return val.substring(0, 4) + "年" + val.substring(4) + "月"

            }
          }
        },
        yAxis: {
          splitLine: {
            lineStyle: {
              type: 'dashed'
            }
          },
          min: 0,
          scale: true
        },
        series: [{
          name: '调拨次数',
          data: transNum,
          type: 'scatter',
          symbolSize: function (data) {

            return data + 20;
          },
          emphasis: {
            label: {
              show: true,
              formatter: function (param) {
                var html = '';
                html += '调拨年月：' + transTime[param.dataIndex];
                html += "\n"
                html += '调拨次数：' + transNum[param.dataIndex];
                html += ''
                return html;
              },
              position: 'top',
              align: 'left'
            }
          }
        }],
      };

      myChart.setOption(option);
      let that = this;
      myChart.on('click', function (params) {
        // 控制台打印数据的名称
        console.log('1111', params.name);
        that.$refs.editForm.$emit('openViewDialog', params.name)
      });
    },
    drawChart3() {
      var asset_scrap = this.asset_scrap_data;

      var deptName = [];
      var addnum = [];
      var addvalue = [];
      var scrapnum = [];
      var scrapvalue = [];


      for (let i = 0; i < asset_scrap.length; i++) {
        let v = asset_scrap[i];
        deptName.push(v.adItem.substring(0, 4) + "年" + v.adItem.substring(4) + "月");
        addnum.push(v.adNum);
        addvalue.push(v.adValue);
        scrapnum.push(v.snum);
        scrapvalue.push(v.svalue);
      }


      let myChart = this.$echarts.init(document.getElementById("myChart3"));
      var that = this;
      let option = {
        title: {
          text: '月度资产增加及报废情况报表',
          // left:'center'
        },
        legend: {
          show: true,
          data: ['增加数量', '报废数量'],
          // left: 'center',
          // top: '25'
        },
        xAxis: {
          show: false
        },
        grid: {
          left: '10%',
          bottom: '38%'
        },
        angleAxis: {
          type: 'category',
          data: deptName
        },
        tooltip: {
          show: true,
          formatter: function (params) {
            var id = params.dataIndex;
            return deptName[id] + '<br>增加数量：' + that.convert(addnum[id]) + '<br>增加原值：' + that.convert(addvalue[id]) +
              '<br>报废数量：' + that.convert(scrapnum[id]) + '<br>报废原值：' + that.convert(scrapvalue[id]);
          }
        },
        radiusAxis: {},
        polar: {
          center: ['50%', '45%'],
          radius: [15, 120]
        },
        series: [

          {
            type: 'bar',
            itemStyle: {
              color: 'transparent'
            },
            data: addnum.map(function (d) {
              return 0;
            }),
            coordinateSystem: 'polar',
            stack: '增加数量',
            silent: true,

          }, {
            type: 'bar',
            data: addnum.map(function (d) {
              return d;
            }),
            coordinateSystem: 'polar',
            name: '增加数量',
            stack: '增加数量',
            barGap: '-100%',
          },
          {
            type: 'bar',
            itemStyle: {
              color: 'transparent'
            },
            data: scrapnum.map(function (d) {

              return 0;
            }),
            coordinateSystem: 'polar',
            stack: '报废数量',
            silent: true,
          }, {
            type: 'bar',
            data: scrapnum.map(function (d) {
              return d;
            }),
            coordinateSystem: 'polar',
            name: '报废数量',
            stack: '报废数量',
            barGap: '-100%',
          },
        ]
      };
      myChart.setOption(option);
    },

    convert(ori) {
      return typeof ori == 'undefined' ? 0 : ori;
    },

    // 初始化自定义类型选择框选项变量
    initOptions(This) {

    }
    ,
    getData(){
      getListALL().then((responseData) => {
        if (responseData.code == 100) {
          var deptDataDtoList = responseData.data.deptDataDtoList;
          // console.log("11111", deptDataDtoList);
          this.dept_data = deptDataDtoList;
          this.drawChart();
          var categoryDataDtoList = responseData.data.categoryDataDtoList;

          this.asset_category_data = categoryDataDtoList;
          this.drawChart2();

          var graphAddScrapDataDtoList = responseData.data.graphAddScrapDataDtoList;
          this.asset_scrap_data = graphAddScrapDataDtoList;
          this.drawChart3();

          var transferDataDtoList = responseData.data.transferDataDtoList;
          this.asset_data = transferDataDtoList;
          this.drawChart4();

        } else {
          this.showMessage(responseData.msg);
        }
      });
    }
  },
  watch: {},
  mounted() {
    this.getData();
    this.drawChart();
    this.drawChart2();
    this.drawChart3();
    this.drawChart4();
  },
}
</script>
