<template>
  <div class="charge-container" id="chargeContainer">
    <div class="charge">
      <!--<h1 class="charge-title">记账凭证</h1>-->
      <!-- <div class="charge-header">
        <div>
          凭证字
          <el-select style="width: 80px;" v-model="voucher.word">
            <el-option
              v-for="item in wordList"
              :key="item.name"
              :label="item.name"
              :value="item.name"
            >
            </el-option>
          </el-select>
        </div>

        <div>
          凭证号
          <el-input
            type="number"
            style="width: 80px;"
            min="1"
            v-model="voucher.no"
          ></el-input>
        </div>

        <div>
          日期
          <el-date-picker
            type="date"
            style="width: 145px;"
            v-model="voucher.date"
          ></el-date-picker>
        </div>

        <div style="font-size: 24px;">记账凭证</div>

        <div style="float: right;position: relative;">
          附单据
          <el-input
            type="number"
            style="width: 80px;"
            min="0"
            v-model="voucher.bill"
          ></el-input>
          张
          <i class="el-icon-info"></i>
          <div class="tip-box">
            <table class="tip-box-table" border="1">
              <tr style="background-color: #f5f4f4;">
                <td>操作</td>
                <td>快捷键</td>
              </tr>
              <tr>
                <td>选择列表</td>
                <td>F7</td>
              </tr>
              <tr>
                <td>自动平衡</td>
                <td>=</td>
              </tr>
              <tr>
                <td>新增</td>
                <td>F4</td>
              </tr>
              <tr>
                <td>保存</td>
                <td>Ctrl+S</td>
              </tr>
              <tr>
                <td>保存并审核</td>
                <td>F10</td>
              </tr>
              <tr>
                <td>保存并新增</td>
                <td>F11</td>
              </tr>
              <tr>
                <td>复制上一行</td>
                <td>//</td>
              </tr>
              <tr>
                <td>复制上一行摘要</td>
                <td>..</td>
              </tr>
              <tr>
                <td>金额自动切换借贷方</td>
                <td>空格键</td>
              </tr>
              <tr>
                <td>单据头与分录快速切换</td>
                <td>Tab</td>
              </tr>
            </table>
          </div>
        </div>
      </div> -->
      <table class="charge-table" border="1">
        <tr>
          <td width="6%">操作</td>
          <td :width="isAuxiliary ? '14%' : '22%'">摘要</td>
          <td :width="isAuxiliary ? '15%' : '22%'">会计科目</td>
          <td width="14%" v-if="isAuxiliary">辅助核算</td>
          <td width="50%">
            <table style="height: 50px;">
              <tr style="border-bottom: 1px solid #bab9b9;">
                <td width="50%" style="border-right: 1px solid #bab9b9;">
                  借方金额
                </td>
                <td width="50%">贷方金额</td>
              </tr>
              <tr>
                <td style="border-right: 1px solid #bab9b9;">
                  <table class="debtor-lender-table" style="height: 100%;">
                    <tr>
                      <td>亿</td>
                      <td>千</td>
                      <td>百</td>
                      <td>十</td>
                      <td>万</td>
                      <td>千</td>
                      <td>百</td>
                      <td>十</td>
                      <td>元</td>
                      <td>角</td>
                      <td>分</td>
                    </tr>
                  </table>
                </td>
                <td>
                  <table class="debtor-lender-table" style="height: 100%;">
                    <tr>
                      <td>亿</td>
                      <td>千</td>
                      <td>百</td>
                      <td>十</td>
                      <td>万</td>
                      <td>千</td>
                      <td>百</td>
                      <td>十</td>
                      <td>元</td>
                      <td>角</td>
                      <td>分</td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr v-for="(item, index) in list">
          <td>
            <div @click="addList" class="charge-table-icon">
              <i class="el-icon-plus" style="color: orangered;"></i>
            </div>
            <div @click="removeList(index)" class="charge-table-icon">
              <i class="el-icon-close" style="color: #4a90e2;"></i>
            </div>
          </td>
          <td>
            <div
              @click="showInput(index, 'main')"
              v-if="!item.isShowMainInput"
              class="main-subject"
            >
              <!--{{item.main}}-->
              <textarea v-model="item.main"></textarea>
            </div>
            <div class="main-subject" v-if="item.isShowMainInput">
              <!--<input type="text" v-model="item.main" @blur="hideInput(index,'main')" v-focus @keyup="keyupEvents(index,$event,1)">-->
              <textarea
                v-model="item.main"
                @blur="hideInput(index, 'main')"
                v-focus
                @keyup="keyupEvents(index, $event, 1)"
              ></textarea>

              <div
                class="dropdown-menu suggest-list special-elements"
                style="top: 60px;"
              >
                <ul class="item-list">
                  <li
                    v-for="main in mainList"
                    v-if="main.name.indexOf(item.main.trim()) != -1"
                    :class="main.name == item.main ? 'hover' : ''"
                    @click="voluationInput(index, 'main', main.name)"
                  >
                    {{ main.name }}
                  </li>
                </ul>
              </div>
            </div>
            <i
              v-if="item.isShowMainInput"
              class="el-icon-more special-elements"
              @click="selectionList(index, 'main')"
            ></i>
          </td>
          <td>
            <div
              @click="showInput(index, 'subject')"
              v-if="!item.isShowSubjectInput"
              class="main-subject"
            >
              <!--{{item.subject}}-->
              <textarea v-model="item.subject.name"></textarea>
            </div>
            <div class="main-subject" v-if="item.isShowSubjectInput">
              <!--<input type="text" v-model="item.subject" @blur="hideInput(index,'subject')" v-focus @keyup="keyupEvents(index,$event,2)">-->
              <textarea
                v-model="item.subject.name"
                @blur="hideInput(index, 'subject')"
                v-focus
                @keyup="keyupEvents(index, $event, 2)"
              ></textarea>

              <div
                class="dropdown-menu suggest-list special-elements"
                style="top: 60px;"
              >
                <ul class="item-list">
                  <li
                    v-for="subject in subjectList"
                    :class="subject.varaccountfullname == item.subject.varaccountfullname ? 'hover' : ''"
                    @click="voluationInput(index, 'subject', subject)"
                  >
                    [{{subject.varaccountcode}}]{{ subject.varaccountfullname }}
                  </li>
                </ul>
              </div>
            </div>
            <i
              v-if="item.isShowSubjectInput"
              class="el-icon-more special-elements"
              @click="selectionList(index, 'subject')"
            ></i>
          </td>
          <td
            v-if="isAuxiliary"
            :disabled="!item.isAuxiliary"
            :class="item.isAuxiliary ? '' : 'td-auxiliary-dis'"
          >
            <div
              class="auxiliary-accounting"
              v-if="item.isAuxiliary"
              @click="selectionList(index, 'auxiliary')"
            >
              <div
                v-if="item.auxiliary && item.auxiliary != ''"
                class="auxiliary-single"
              >
                <span>{{ item.auxiliary }}</span>
                <i
                  class="el-icon-close"
                  @click.stop="clearAuxiliary(index, $event)"
                ></i>
              </div>
            </div>
          </td>
          <td>
            <table>
              <tr>
                <td width="50%" style="border-right: 1px solid #bab9b9;">
                  <table class="debtor-tbale debtor-lender-table">
                    <tr
                      @click="showInput(index, 'debtor')"
                      v-if="!item.isShowDebtorInput"
                      :class="item.debtor * 1 < 0 ? 'tr-negative' : ''"
                    >
                      <td v-for="debtor in item.debtorList">{{ debtor }}</td>
                    </tr>

                    <tr v-if="item.isShowDebtorInput">
                      <input
                        @blur="hideInput(index, 'debtor')"
                        @keyup="debtorInputKeyUp(index, $event, 3)"
                        v-model="item.debtor"
                        maxlength="12"
                        v-focus
                        onkeypress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d\d$/.test(value))event.returnValue=false"
                      />
                    </tr>
                  </table>
                </td>
                <td width="50%">
                  <table class="lender-tbale debtor-lender-table">
                    <tr
                      v-if="!item.isShowLenderInput"
                      @click="showInput(index, 'lender')"
                      :class="item.lender * 1 < 0 ? 'tr-negative' : ''"
                    >
                      <td v-for="lender in item.lenderList">{{ lender }}</td>
                    </tr>

                    <tr v-if="item.isShowLenderInput">
                      <input
                        @blur="hideInput(index, 'lender')"
                        @keyup="lenderInputKeyUp(index, $event, 4)"
                        v-model="item.lender"
                        maxlength="11"
                        v-focus
                        onkeypress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;"
                      />
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td
            :colspan="isAuxiliary ? 4 : 3"
            style="text-align: left;padding-left: 10px;"
          >
            合计：
          </td>
          <td>
            <table>
              <tr>
                <td width="50%" style="border-right: 1px solid #bab9b9;">
                  <table class="debtor-tbale debtor-lender-table">
                    <tr>
                      <td v-for="debtor in debtorTotalList">{{ debtor }}</td>
                    </tr>
                  </table>
                </td>
                <td width="50%">
                  <table class="lender-tbale debtor-lender-table">
                    <tr>
                      <td v-for="lender in lenderTotalList">{{ lender }}</td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>

      <div style="height: 50px;line-height: 50px;">制单人：</div>
      <div style="text-align: right;" v-show="saveVisible">
        <el-button @click="preservation(1)">暂存</el-button>
        <el-button type="primary" @click="preservation(2)">保存</el-button>
      </div>
    </div>

    <!--<el-dialog title="摘要列表" width="30%" :visible.sync="dialogMainVisible">
        <main-selector @closeMainDialog="closeMainDialog" ref="mainSelector"></main-selector>
        <span slot="footer" class="dialog-footer">
              <el-button @click="closeMainDialog">取消</el-button>
              <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="edit">确认</el-button>
            </span>
      </el-dialog>
      <el-dialog title="会计科目" width="30%" :visible.sync="dialogSubjectVisible">
        <subject-selector @closeSubjectDialog="closeSubjectDialog" ref="subjectSelector"></subject-selector>
        <span slot="footer" class="dialog-footer">
              <el-button @click="closeSubjectDialog">取消</el-button>
              <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="edit">确认</el-button>
            </span>
      </el-dialog>
      <el-dialog title="请选择" width="30%" :visible.sync="dialogAuxiliaryVisible">
        <auxiliary-selector @closeAuxiliaryDialog="closeAuxiliaryDialog" ref="auxiliarySelector"></auxiliary-selector>
        <span slot="footer" class="dialog-footer">
              <el-button @click="closeAuxiliaryDialog">取消</el-button>
              <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="edit">确认</el-button>
            </span>
      </el-dialog>-->
  </div>
</template>

<script>
import Vue from "vue";
import {
  listTglAccountsPage,
  deleteTglAccounts
} from "@/api/vouchermanager/InitLedgePeriod/tglAccounts";
// import {
//   addObj,
//   getObj,
//   putObj,
//   getWordList,
//   getMainList,
//   getSubjectList
// } from "@/api";

Vue.directive("focus", {
  // 当绑定元素插入到 DOM 中。
  inserted: function(el) {
    // 聚焦元素
    el.focus();
  }
});

export default {
  name: "voucherForm",
  props: ["id"],
  // components: {
  //   'main-selector': () => import('./components/mainSelector'),
  //   'subject-selector': () => import('./components/subjectSelector'),
  //   'auxiliary-selector': () => import('./components/auxiliarySelector'),
  // },
  data() {
    return {
      voucher: {
        word: "记",
        no: 1,
        date: new Date(),
        bill: 0
      },
      list: [
        {
          main: "",
          isShowMainInput: false,
          subject: {
            number: "",
            name: "",
            detailJson: ""
          },
          isShowSubjectInput: false,
          debtor: "",
          debtorList: ["", "", "", "", "", "", "", "", 0, 0, 0],
          isShowDebtorInput: false,
          lender: "",
          lenderList: ["", "", "", "", "", "", "", "", 0, 0, 0],
          isShowLenderInput: false,
          isAuxiliary: false,
          auxiliary: ""
        },
        {
          main: "",
          isShowMainInput: false,
          subject: {
            number: "",
            name: "",
            detailJson: ""
          },
          isShowSubjectInput: false,
          debtor: "",
          debtorList: ["", "", "", "", "", "", "", "", 0, 0, 0],
          isShowDebtorInput: false,
          lender: "",
          lenderList: ["", "", "", "", "", "", "", "", 0, 0, 0],
          isShowLenderInput: false,
          isAuxiliary: false,
          auxiliary: ""
        },
        {
          main: "",
          isShowMainInput: false,
          subject: {
            number: "",
            name: "",
            detailJson: ""
          },
          isShowSubjectInput: false,
          debtor: "",
          debtorList: ["", "", "", "", "", "", "", "", 0, 0, 0],
          isShowDebtorInput: false,
          lender: "",
          lenderList: ["", "", "", "", "", "", "", "", 0, 0, 0],
          isShowLenderInput: false,
          isAuxiliary: false,
          auxiliary: ""
        },
        {
          main: "",
          isShowMainInput: false,
          subject: {
            number: "",
            name: "",
            detailJson: ""
          },
          isShowSubjectInput: false,
          debtor: "",
          debtorList: ["", "", "", "", "", "", "", "", 0, 0, 0],
          isShowDebtorInput: false,
          lender: "",
          lenderList: ["", "", "", "", "", "", "", "", 0, 0, 0],
          isShowLenderInput: false,
          isAuxiliary: false,
          auxiliary: ""
        }
      ],
      debtorTotal: 0,
      debtorTotalList: ["", "", "", "", "", "", "", "", 0, 0, 0],
      lenderTotal: 0,
      lenderTotalList: ["", "", "", "", "", "", "", "", 0, 0, 0],
      wordList: [],
      mainList: [],
      subjectList: [],
      dialogMainVisible: false,
      dialogSubjectVisible: false,
      dialogAuxiliaryVisible: false,
      dialogMainIndex: 0,
      dialogSubjectIndex: 0,
      dialogAuxiliaryIndex: 0,
      isAuxiliary: false,
      saveVisible: true,
      billNo: "",
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [
          {
            columnName: "varaccountcode",
            queryType: "like",
            value: "0000"
          }
        ]
      }
    };
  },
  mounted() {
    document.addEventListener("keydown", this.handleEvent);
    // getWordList().then(response => {
    //   this.wordList = response.data.records;
    // });
    // getMainList().then(response => {
    //   this.mainList = response.data.records;
    // });
    listTglAccountsPage(this.search).then(response => {
      this.subjectList = response.data.rows;
    });

    this.init();
  },
  updated() {
    //给特定区域添加ID   绑定onmousedown 事件
    var chargeContainer = document.getElementById("chargeContainer");
    var outDiv = chargeContainer.getElementsByClassName("special-elements");
    for (var i = 0; i < outDiv.length; i++) {
      outDiv[i].onmousedown = function(e) {
        //现代浏览器阻止默认事件
        if (e && e.preventDefault) e.preventDefault();
        //IE阻止默认事件
        else window.event.returnValue = false;
        return false;
      };
    }
  },
  methods: {
    judgeIsAuxiliary() {
      var flag = false;
      for (var i in this.list) {
        var detailJson = this.list[i].subject.detailJson;
        if (detailJson !== "" && detailJson !== undefined) {
          flag = true;
          this.list[i].isAuxiliary = true;
        } else {
          this.list[i].isAuxiliary = false;
          this.list[i].auxiliary = "";
        }
      }
      this.isAuxiliary = flag;
    },
    openMainDialog() {
      this.dialogMainVisible = true;
    },
    closeMainDialog(main) {
      if (!main.isTrusted) {
        this.list[this.dialogMainIndex].main = main;
      }
      this.dialogMainVisible = false;
    },
    openSubjectDialog() {
      this.dialogSubjectVisible = true;
    },
    closeSubjectDialog(sub) {
      if (!sub.isTrusted) {
        this.list[this.dialogSubjectIndex].subject = sub;
      }
      this.dialogSubjectVisible = false;
      this.judgeIsAuxiliary();
    },
    openAuxiliaryDialog() {
      this.dialogAuxiliaryVisible = true;
    },
    closeAuxiliaryDialog(aux) {
      if (!aux.isTrusted) {
        this.list[this.dialogAuxiliaryIndex].auxiliary = aux;
      }
      this.dialogAuxiliaryVisible = false;
    },
    clearAuxiliary(index, e) {
      this.list[index].auxiliary = "";
    },

    handleEvent(event) {
      //console.log(event);
      if (window.location.hash == "#/general_ledger/voucher_entry") {
        if (event.keyCode === 83 && event.ctrlKey) {
          //console.log('拦截到83+ctrl');
          this.preservation(2);
          event.preventDefault();
          event.returnValue = false;
          return false;
        } else if (event.keyCode === 115) {
          //console.log('拦截到115');//F4
          this.addList();
          event.preventDefault();
          event.returnValue = false;
          return false;
        } else if (event.keyCode === 121) {
          //console.log('拦截到121');//F10
          event.preventDefault();
          event.returnValue = false;
          return false;
        } else if (event.keyCode === 122) {
          //console.log('拦截到122');//F11
          event.preventDefault();
          event.returnValue = false;
          return false;
        }
      } else {
        //需要销毁事件 防止全局生效
        //document.removeEventListener('keydown', this.handleEvent);
      }
    },
    showInput(index, type) {
      for (var i in this.list) {
        this.list[i].isShowDebtorInput = false;
        this.list[i].isShowLenderInput = false;
        this.list[i].isShowMainInput = false;
        this.list[i].isShowSubjectInput = false;

        if (i == index && type == "debtor") {
          this.list[index].isShowDebtorInput = true;
        } else if (i == index && type == "lender") {
          this.list[index].isShowLenderInput = true;
        } else if (i == index && type == "main") {
          this.list[index].isShowMainInput = true;
        } else if (i == index && type == "subject") {
          this.list[index].isShowSubjectInput = true;
        }
      }
    },
    hideInput(index, type) {
      if (type == "debtor") {
        //判断是否有小数点
        var inx = this.list[index].debtor.indexOf(".");
        if (inx != -1 && this.list[index].debtor.length - 1 != inx) {
          this.list[index].debtor = (this.list[index].debtor * 1).toFixed(4);
        }
        this.list[index].isShowDebtorInput = false;
      } else if (type == "lender") {
        //判断是否有小数点
        var inx = this.list[index].lender.indexOf(".");
        if (inx != -1 && this.list[index].lender.length - 1 != inx) {
          this.list[index].lender = (this.list[index].lender * 1).toFixed(4);
        }
        this.list[index].isShowLenderInput = false;
      } else if (type == "main") {
        this.list[index].isShowMainInput = false;
      } else if (type == "subject") {
        this.list[index].isShowSubjectInput = false;
      }
    },
    voluationInput(index, type, val) {
      if (type == "main") {
        this.list[index].main = val;
        this.list[index].isShowMainInput = false;
      } else if (type == "subject") {
        this.list[index].subject.number = val.number;
        this.list[index].subject.name = val.name;
        this.list[index].subject.detailJson = val.detailJson;
        this.list[index].isShowSubjectInput = false;
      }
      this.judgeIsAuxiliary();
    },
    selectionList(index, type) {
      //console.log('弹出选择列表');
      if (type == "main") {
        this.dialogMainIndex = index;
        this.openMainDialog();
      } else if (type == "subject") {
        this.dialogSubjectIndex = index;
        this.openSubjectDialog();
      } else if (type == "auxiliary") {
        this.dialogAuxiliaryIndex = index;
        this.openAuxiliaryDialog();
      }
    },

    keyupEvents(index, e, remaind) {
      if (e.keyCode == 37) {
        //console.log('拦截到37');//左
        this.keyboardEvents("left", index * 4 + remaind);
        return;
      } else if (e.keyCode == 38) {
        //console.log('拦截到38');//上
        this.keyboardEvents("up", index * 4 + remaind);
        return;
      } else if (e.keyCode == 39) {
        //console.log('拦截到39');//右
        this.keyboardEvents("right", index * 4 + remaind);
        return;
      } else if (e.keyCode == 40) {
        //console.log('拦截到40');//下
        this.keyboardEvents("down", index * 4 + remaind);
        return;
      } else if (e.keyCode == 13) {
        //console.log('拦截到13');//enter键
        this.keyboardEvents("enter", index * 4 + remaind);
        return;
      } else if (e.keyCode == 118) {
        //console.log('拦截到118');//F7
        if (remaind == 1) {
          this.selectionList(index, "main");
        } else if (remaind == 2) {
          this.selectionList(index, "subject");
        }
        return;
      }

      var main = this.list[index].main;
      var subject = this.list[index].subject.name;
      if (index - 1 >= 0) {
        if (main.indexOf("//") != -1 || subject.indexOf("//") != -1) {
          this.list[index].main = this.list[index - 1].main;
          this.list[index].subject = this.list[index - 1].subject;
          this.list[index].debtor = this.list[index - 1].debtor;
          this.list[index].debtorList = this.list[index - 1].debtorList;
          this.list[index].lender = this.list[index - 1].lender;
          this.list[index].lenderList = this.list[index - 1].lenderList;
          this.list[index].auxiliary = this.list[index - 1].auxiliary;
          this.calcDebtorTotal();
          this.calcLenderTotal();
        }
        if (main.indexOf("..") != -1) {
          this.list[index].main = this.list[index - 1].main;
        }
      }
      //判断是否显示辅助核算
      this.judgeIsAuxiliary();
    },
    keyboardEvents(type, number) {
      var total = this.list.length * 4;
      if (type == "enter") {
        number++;
      } else if (type == "left" && number - 1 > 0) {
        number--;
      } else if (type == "right" && number + 1 <= total) {
        number++;
      } else if (type == "up" && number - 4 > 0) {
        number = number - 4;
      } else if (type == "down" && number + 4 <= total) {
        number = number + 4;
      }
      if (type == "enter" && number > total) {
        this.addList();
      }
      var index = parseInt(number / 4);
      var remaind = number % 4;
      if (remaind == 1) {
        this.showInput(index, "main");
      } else if (remaind == 2) {
        this.showInput(index, "subject");
      } else if (remaind == 3) {
        this.showInput(index, "debtor");
      } else if (remaind == 0) {
        this.showInput(index - 1, "lender");
      }
    },
    debtorInputKeyUp(index, e, remaind) {
      //console.log(e);
      //this.list[index].debtor=this.list[index].debtor.replace(/\D/g,'');
      if (e.keyCode === 187) {
        this.calcDebtorTotal(index);
        this.calcLenderTotal(index);
        var cha = this.lenderTotal - this.debtorTotal;

        if (cha == 0) {
          cha = "";
        }
        this.list[index].debtor = cha + "";
      } else if (e.keyCode === 32) {
        this.list[index].isShowLenderInput = true;
        this.list[index].isShowDebtorInput = false;
        this.list[index].lender = this.list[index].debtor.trim();
        this.list[index].debtor = "";
        this.list[index].debtorList = ["", "", "", "", "", "", "", "", 0, 0, 0];
        var lenderList = ["", "", "", "", "", "", "", "", 0, 0, 0];
        this.list[index].lenderList = this.collatingData(
          this.list[index].lender,
          lenderList
        );
        this.calcDebtorTotal();
        this.calcLenderTotal();
        return;
      } else if ((e.keyCode >= 37 && e.keyCode <= 40) || e.keyCode == 13) {
        this.keyupEvents(index, e, remaind);
        return;
      }
      this.list[index].lender = "";
      this.list[index].lenderList = ["", "", "", "", "", "", "", "", 0, 0, 0];
      var debtor = this.list[index].debtor;
      var debtorList = ["", "", "", "", "", "", "", "", 0, 0, 0];
      this.list[index].debtorList = this.collatingData(debtor, debtorList);
      this.calcDebtorTotal();
      this.calcLenderTotal();
    },
    calcDebtorTotal(index) {
      var debtorTotal = 0;
      for (var i in this.list) {
        if (this.list[i].debtor != null && this.list[i].debtor != "") {
          if (!(index && index == i)) {
            debtorTotal += this.list[i].debtor * 1;
          }
        }
      }
      this.debtorTotal = debtorTotal;
      debtorTotal = debtorTotal + "";
      var debtorTotalList = ["", "", "", "", "", "", "", "", 0, 0, 0];
      this.debtorTotalList = this.collatingData(debtorTotal, debtorTotalList);
    },
    lenderInputKeyUp(index, e, remaind) {
      //this.list[index].lender=this.list[index].lender.replace(/\D/g,'');
      if (e.keyCode === 187) {
        this.calcDebtorTotal(index);
        this.calcLenderTotal(index);
        var cha = this.debtorTotal - this.lenderTotal;
        if (cha == 0) {
          cha = "";
        }
        this.list[index].lender = cha + "";
      } else if (e.keyCode === 32) {
        this.list[index].isShowDebtorInput = true;
        this.list[index].isShowLenderInput = false;
        this.list[index].debtor = this.list[index].lender.trim();
        this.list[index].lender = "";
        this.list[index].lenderList = ["", "", "", "", "", "", "", "", 0, 0, 0];
        var debtorList = ["", "", "", "", "", "", "", "", 0, 0, 0];
        this.list[index].debtorList = this.collatingData(
          this.list[index].debtor,
          debtorList
        );
        this.calcLenderTotal();
        this.calcDebtorTotal();
        return;
      } else if ((e.keyCode >= 37 && e.keyCode <= 40) || e.keyCode == 13) {
        this.keyupEvents(index, e, remaind);
        return;
      }
      this.list[index].debtor = "";
      this.list[index].debtorList = ["", "", "", "", "", "", "", "", 0, 0, 0];
      var lender = this.list[index].lender;
      var lenderList = ["", "", "", "", "", "", "", "", 0, 0, 0];
      this.list[index].lenderList = this.collatingData(lender, lenderList);
      this.calcLenderTotal();
      this.calcDebtorTotal();
    },
    calcLenderTotal(index) {
      var lenderTotal = 0;
      for (var i in this.list) {
        if (this.list[i].lender != null && this.list[i].lender != "") {
          if (!(index && index == i)) {
            lenderTotal += this.list[i].lender * 1;
          }
        }
      }
      this.lenderTotal = lenderTotal;
      lenderTotal = lenderTotal + "";
      var lenderTotalList = ["", "", "", "", "", "", "", "", 0, 0, 0];
      this.lenderTotalList = this.collatingData(lenderTotal, lenderTotalList);
    },
    addList() {
      var obj = {
        main: "",
        isShowMainInput: false,
        subject: {
          number: "",
          name: "",
          detailJson: ""
        },
        isShowSubjectInput: false,
        debtor: "",
        debtorList: ["", "", "", "", "", "", "", "", 0, 0, 0],
        isShowDebtorInput: false,
        lender: "",
        lenderList: ["", "", "", "", "", "", "", "", 0, 0, 0],
        isShowLenderInput: false,
        isAuxiliary: false,
        auxiliary: ""
      };
      this.list.push(obj);
    },
    removeList(index) {
      if (this.list.length <= 2) {
        this.$message.error("至少保存两行");
        return;
      }
      this.list.splice(index, 1);
      this.calcDebtorTotal();
      this.calcLenderTotal();
    },

    collatingData(debtor, debtorList) {
      debtor = debtor.replace(/-/g, "");
      //判断是否有小数点
      if (debtor.indexOf(".") != -1) {
        debtor = (debtor * 1).toFixed(4);
        debtor = (debtor + "").replace(/\./g, "");
      } else {
        debtor = debtor + "00";
      }
      if (debtor.length <= 11) {
        var cha = debtorList.length - debtor.length;
        for (var i = 0; i < debtor.length; i++) {
          debtorList[i + cha] = debtor.charAt(i);
        }
      } else {
        for (var i = 0; i < debtor.length; i++) {
          debtorList[i] = debtor.charAt(i);
        }
      }
      return debtorList;
    },
    checkListData() {
      if (
        (this.debtorTotal != 0 || this.lenderTotal != 0) &&
        this.lenderTotal * 1 != this.debtorTotal * 1
      ) {
        this.$message.error("凭证借贷不平衡，请检查");
        return;
      }
      var mainFlag = true;
      for (var i in this.list) {
        if (this.list[i].main != null && this.list[i].main != "") {
          mainFlag = false;
        }
        if (
          this.list[i].subject.name == null ||
          this.list[i].subject.name == ""
        ) {
          this.$message.error(
            "第" + (i * 1 + 1) + "行中的会计科目为必填项，请填写了再提交"
          );
          return;
        }
        if (
          (this.list[i].debtor == null || this.list[i].debtor == "") &&
          (this.list[i].lender == null || this.list[i].lender == "")
        ) {
          this.$message.error(
            "第" +
              (i * 1 + 1) +
              "行中借方金额、贷方金额必须填一个，请填写了再提交"
          );
          return;
        }
      }
      if (mainFlag) {
        this.$message.error("必须填写一个摘要，请填写了再提交");
        return;
      }

      var list = [];
      for (var a = 0; a < this.list.length; a++) {
        var obj = {
          explanation: this.list[a].main,
          accountNumber: this.list[a].subject.number,
          accountName: this.list[a].subject.name,
          auxiliaryType: this.list[a].subject.detailJson,
          itemObjectKeys: this.list[a].auxiliary,
          basicDebitAmount: this.list[a].debtor,
          basicCreditAmount: this.list[a].lender
        };
        list.push(obj);
      }
      var reqObj = {
        id: this.id,
        billNo: this.billNo,
        billStatus: 2,
        voucherType: this.voucher.word,
        voucherNumber: this.voucher.no,
        year: this.formatDate("yyyy-MM-dd hh:mm:ss", this.voucher.date),
        creditTotal: this.lenderTotal,
        debitTotal: this.debtorTotal,
        attachments: this.voucher.bill,
        list: list
      };

      return reqObj;
    },
    formatDate(fmt, date) {
      if (typeof date === "string") {
        date = new Date(date);
      }

      var o = {
        "M+": date.getMonth() + 1, //月份
        "d+": date.getDate(), //日
        "h+": date.getHours(), //小时
        "m+": date.getMinutes(), //分
        "s+": date.getSeconds(), //秒
        "q+": Math.floor((date.getMonth() + 3) / 3), //季度
        S: date.getMilliseconds() //毫秒
      };
      if (/(y+)/.test(fmt))
        fmt = fmt.replace(
          RegExp.$1,
          (date.getFullYear() + "").substr(4 - RegExp.$1.length)
        );
      for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
          fmt = fmt.replace(
            RegExp.$1,
            RegExp.$1.length == 1
              ? o[k]
              : ("00" + o[k]).substr(("" + o[k]).length)
          );
      return fmt;
    },
    preservation(status) {
      var reqObj = this.checkListData();
      //console.log(reqObj);

      if (reqObj) {
        // addObj(reqObj).then(response => {
        //   if (response.code == "2000") {
        //     this.$notify({
        //       title: "成功",
        //       message: "创建成功",
        //       type: "success",
        //       duration: 2000
        //     });
        //     this.init();
        //   } else {
        //     this.$notify({
        //       title: "失败",
        //       message: response.msg,
        //       type: "warning",
        //       duration: 2000
        //     });
        //   }
        // });
      }
    },
    onSubmit() {
      var reqObj = this.checkListData();
      //console.log(reqObj);

      if (reqObj) {
        // putObj(this.id, reqObj).then(response => {
        //   if (response.code == "2000") {
        //     this.$notify({
        //       title: "成功",
        //       message: "修改成功",
        //       type: "success",
        //       duration: 2000
        //     });
        //     this.$emit("submitSuccess", response);
        //   } else {
        //     this.$notify({
        //       title: "失败",
        //       message: response.msg,
        //       type: "warning",
        //       duration: 2000
        //     });
        //   }
        // });
      }
    },
    init() {
      if (this.id !== "" && this.id !== undefined) {
        // getObj(this.id).then(response => {
        //   const data = response.data;
        //   this.voucher = {
        //     word: data.voucherType,
        //     no: data.voucherNumber,
        //     date: data.year,
        //     bill: data.attachments
        //   };
        //   this.list = [];
        //   var isAuxiliary = false;
        //   for (var i = 0; i < data.list.length; i++) {
        //     var debtor =
        //       data.list[i].basicDebitAmount == 0
        //         ? ""
        //         : data.list[i].basicDebitAmount + "";
        //     var debtorList =
        //       debtor == ""
        //         ? ["", "", "", "", "", "", "", "", 0, 0, 0]
        //         : this.collatingData(debtor, [
        //             "",
        //             "",
        //             "",
        //             "",
        //             "",
        //             "",
        //             "",
        //             "",
        //             0,
        //             0,
        //             0
        //           ]);
        //     var lender =
        //       data.list[i].basicCreditAmount == 0
        //         ? ""
        //         : data.list[i].basicCreditAmount + "";
        //     var lenderList =
        //       lender == ""
        //         ? ["", "", "", "", "", "", "", "", 0, 0, 0]
        //         : this.collatingData(lender, [
        //             "",
        //             "",
        //             "",
        //             "",
        //             "",
        //             "",
        //             "",
        //             "",
        //             0,
        //             0,
        //             0
        //           ]);
        //     if (
        //       data.list[i].itemObjectKeys != null &&
        //       data.list[i].itemObjectKeys != ""
        //     ) {
        //       isAuxiliary = true;
        //     }
        //     var obj = {
        //       main: data.list[i].explanation,
        //       isShowMainInput: false,
        //       subject: {
        //         number: data.list[i].accountNumber,
        //         name: data.list[i].accountName,
        //         detailJson: data.list[i].auxiliaryType
        //       },
        //       isShowSubjectInput: false,
        //       debtor: debtor,
        //       debtorList: debtorList,
        //       isShowDebtorInput: false,
        //       lender: lender,
        //       lenderList: lenderList,
        //       isShowLenderInput: false,
        //       isAuxiliary: data.list[i].itemObjectKeys == "" ? false : true,
        //       auxiliary: data.list[i].itemObjectKeys
        //     };
        //     this.list.push(obj);
        //   }
        //   this.debtorTotal = data.debitTotal + "";
        //   this.debtorTotalList = this.collatingData(this.debtorTotal, [
        //     "",
        //     "",
        //     "",
        //     "",
        //     "",
        //     "",
        //     "",
        //     "",
        //     0,
        //     0,
        //     0
        //   ]);
        //   this.lenderTotal = data.creditTotal + "";
        //   this.lenderTotalList = this.collatingData(this.lenderTotal, [
        //     "",
        //     "",
        //     "",
        //     "",
        //     "",
        //     "",
        //     "",
        //     "",
        //     0,
        //     0,
        //     0
        //   ]);
        //   this.dialogMainVisible = false;
        //   this.dialogSubjectVisible = false;
        //   this.dialogAuxiliaryVisible = false;
        //   this.saveVisible = false;
        //   this.dialogMainIndex = 0;
        //   this.dialogSubjectIndex = 0;
        //   this.dialogAuxiliaryIndex = 0;
        //   this.isAuxiliary = isAuxiliary;
        //   this.billNo = data.billNo;
        // });
      } else {
        this.voucher = { word: "记", no: 1, date: new Date(), bill: 0 };
        this.list = [
          {
            main: "",
            isShowMainInput: false,
            subject: {
              number: "",
              name: "",
              detailJson: ""
            },
            isShowSubjectInput: false,
            debtor: "",
            debtorList: ["", "", "", "", "", "", "", "", 0, 0, 0],
            isShowDebtorInput: false,
            lender: "",
            lenderList: ["", "", "", "", "", "", "", "", 0, 0, 0],
            isShowLenderInput: false,
            isAuxiliary: false,
            auxiliary: ""
          },
          {
            main: "",
            isShowMainInput: false,
            subject: {
              number: "",
              name: "",
              detailJson: ""
            },
            isShowSubjectInput: false,
            debtor: "",
            debtorList: ["", "", "", "", "", "", "", "", 0, 0, 0],
            isShowDebtorInput: false,
            lender: "",
            lenderList: ["", "", "", "", "", "", "", "", 0, 0, 0],
            isShowLenderInput: false,
            isAuxiliary: false,
            auxiliary: ""
          },
          {
            main: "",
            isShowMainInput: false,
            subject: {
              number: "",
              name: "",
              detailJson: ""
            },
            isShowSubjectInput: false,
            debtor: "",
            debtorList: ["", "", "", "", "", "", "", "", 0, 0, 0],
            isShowDebtorInput: false,
            lender: "",
            lenderList: ["", "", "", "", "", "", "", "", 0, 0, 0],
            isShowLenderInput: false,
            isAuxiliary: false,
            auxiliary: ""
          },
          {
            main: "",
            isShowMainInput: false,
            subject: {
              number: "",
              name: "",
              detailJson: ""
            },
            isShowSubjectInput: false,
            debtor: "",
            debtorList: ["", "", "", "", "", "", "", "", 0, 0, 0],
            isShowDebtorInput: false,
            lender: "",
            lenderList: ["", "", "", "", "", "", "", "", 0, 0, 0],
            isShowLenderInput: false,
            isAuxiliary: false,
            auxiliary: ""
          }
        ];
        this.debtorTotal = 0;
        this.debtorTotalList = ["", "", "", "", "", "", "", "", 0, 0, 0];
        this.lenderTotal = 0;
        this.lenderTotalList = ["", "", "", "", "", "", "", "", 0, 0, 0];
        this.dialogMainVisible = false;
        this.dialogSubjectVisible = false;
        this.dialogAuxiliaryVisible = false;
        this.dialogMainIndex = 0;
        this.dialogSubjectIndex = 0;
        this.dialogAuxiliaryIndex = 0;
        this.isAuxiliary = false;
      }
    }
  }
};
</script>

<style scoped>
table {
  width: 100%;
  border-spacing: 0;
  border-collapse: collapse;
  padding: 0;
  margin: 0;
}

table td {
  text-align: center;
  table-layout: fixed;
  padding: 0px;
  position: relative;
}

.main-subject {
  position: relative;
  height: 60px;
  line-height: 30px;
  text-align: left;
}

.charge-container {
  padding: 10px;
  font-size: 14px;
  color: #444;
  font-weight: 400;
  background-color: white;
}

.charge {
  width: 1124px;
  margin: auto;
}

.charge-header {
  margin-bottom: 10px;
}

.charge-header > div {
  display: inline-block;
  margin-right: 15px;
}

.tip-box {
  width: 330px;
  padding: 10px;
  position: absolute;
  top: 27px;
  right: -15px;
  z-index: 1005;
  background-color: #fff;
  box-shadow: 0 0 6px rgba(170, 170, 170, 0.73);
  display: none;
}

.tip-box-table tr {
  height: 25px;
}

.el-icon-info {
  font-size: 18px;
  margin-left: 30px;
  cursor: pointer;
}

.el-icon-info:hover + .tip-box {
  display: inline-block;
}

.el-icon-more {
  position: absolute;
  top: 22px;
  right: 10px;
  z-index: 2;
  color: #666;
  cursor: pointer;
  font-size: 16px;
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  left: 0px;
  float: left;
  padding: 5px 0;
  margin: 2px 0 0;
  text-align: left;
  list-style: none;
  background-color: #fff;
  background-clip: padding-box;
}

.suggest-list {
  width: 100%;
  height: auto;
  z-index: 1015;
  min-width: inherit;
  display: block;
  overflow: hidden;
  border: none;
  box-shadow: 0 1px 6px rgba(0, 0, 0, 0.2);
}

.suggest-list .item-list {
  max-height: 375px;
  overflow: auto;
  list-style: none;
  margin: 0px;
  padding: 0px;
}

.item-list li {
  display: flex;
  padding: 0 10px;
  height: 28px;
  line-height: 28px;
  cursor: pointer;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.item-list li:hover {
  background: #ecebeb;
}

.item-list li.hover {
  background: #ecebeb;
}

.charge-table,
.tip-box-table {
  border: 1px solid #bab9b9;
}

.charge-table,
.debtor-tbale,
.lender-tbale > tr {
  height: 60px;
}

.charge-table > tr:first-child {
  height: 50px;
}

.td-auxiliary-dis {
  background-color: #f7f7f7;
}

.auxiliary-accounting {
  height: 60px;
  overflow: auto;
  padding: 15px 0 0 30px;
}

.auxiliary-accounting:before {
  content: "+";
  font-size: 30px;
  color: #4a90e2;
  cursor: pointer;
  padding: 0 11px;
  position: absolute;
  top: 0;
  left: 0;
  line-height: 60px;
}

.auxiliary-single {
  display: flex;
  float: left;
  height: 28px;
  line-height: 28px;
  margin-right: 5px;
  cursor: pointer;
  background: #eee;
  padding: 0 8px;
  border-radius: 2px;
}

.auxiliary-single span {
  max-width: 90px;
  overflow: hidden;
  height: 28px;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.auxiliary-single i {
  color: red;
  margin: 8px 0 8px 7px;
  font-size: 10px;
  visibility: hidden;
}

.auxiliary-single:hover i {
  visibility: inherit;
}

.charge-table-icon {
  cursor: pointer;
  display: inline-block;
}

.debtor-lender-table tr td {
  width: 9%;
  border-right: 1px solid #bab9b9;
}

.debtor-lender-table tr td:nth-child(3) {
  border-right-color: rgba(74, 144, 226, 0.5);
}

.debtor-lender-table tr td:nth-child(6) {
  border-right-color: rgba(74, 144, 226, 0.5);
}

.debtor-lender-table tr td:nth-child(9) {
  border-right-color: rgba(226, 106, 74, 0.5);
}

.debtor-lender-table tr td:last-child {
  border-right: none;
}

.tr-negative {
  color: red;
}

.charge-table input,
select {
  width: 100%;
  height: 60px;
}

.charge-table textarea {
  width: 100%;
  height: 60px;
  padding: 9px 14px 9px 10px;
  overflow: auto;
  resize: none;
  border: none;
  border-radius: 0px;
  margin: 0;
  color: #444;
  box-sizing: border-box;
}
</style>
