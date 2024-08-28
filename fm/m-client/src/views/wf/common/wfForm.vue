<template>
  <div class="dm-container">
    <van-popup
      v-model="dialogProps.visible"
      position="right"
      transition-appear
      style="height: 100%; width: 100%; background: #f6f8fb">
      <div class="dm-van-popup_Wrapper">
        <div class="dm-van-popup_VanPopup_header">
          <van-sticky>
            <van-nav-bar
              :title="dialogProps.title"
              :left-arrow="true"
              @click-left="onClickLeft"
            >
              <template #right>
                <van-icon
                  name="edit"
                  color="#333333"
                  size="20"
                  v-on:click="switchEdit"
                  v-if="action == 'view'"
                ></van-icon>
              </template>
            </van-nav-bar>
          </van-sticky>
          <van-overlay :show="loading">
            <div class="dm-van-overlay_Wrapper">
              <van-loading />
            </div>
          </van-overlay>
        </div>
        <div class="dm-van-popup_VanPopup_body">
          <div>
            <!-- 子表单 -->
            <component
              ref="bizCompnent"
              @handleResponse="handleResponse"
              @savefinished="onClickLeft"
              :is="bizCompnent"
              :wfDaiog="this"
              :wfAction="dialogProps.action"
              :task="task"
              :procDef="procDef"
              :formKey="formKey"
              :bizData="bizData"
              :options="bizOptions"
              v-on:after-load-data="afterLoadData"
              v-on:afer-step-change="afterStepChange"
            ></component>

            <!-- 流程附件 -->
            <div class="stepHistoic">
              <van-collapse v-model="activeaAttachments" :border="false">
                <van-collapse-item title="流程附件" name="1" :border="false">
                  <van-cell-group v-for="(item, index) in attachments" :border="false" :key="index">
                    <van-cell :title="item.name">
                      <template #label>
                        <van-row v-if="histoicComments && histoicComments.length > 0">
                          <van-col span="14">节点：{{
                            histoicComments.find(
                            (info) => info.taskId == item.taskId
                            ).activityName
                            }}
                          </van-col>
                          <van-col>上传人：{{
                            histoicComments.find(
                            (info) => info.taskId == item.taskId
                            ).commentBy
                            }}
                          </van-col>
                        </van-row>
                        <van-row>
                          <van-col>日期：{{ item.createTime }}</van-col>
                        </van-row>
                      </template>
                      <template #right-icon>
                        <van-button size="mini" plain type="info" @click="documentData(item)">下载</van-button>
                      </template>
                    </van-cell>
                  </van-cell-group>
                  <div>
                    <van-uploader
                      v-model="originalFileList"
                      v-if="dialogProps.action == 'create'"
                      accept=".jpg,.jpeg,.png,.doc,.docx,.pdf,.ppt,.pptx,.xlsx,.xls"
                      upload-icon="description">
                      <!--<van-button size="small" type="default" plain>上传文件</van-button>-->
                    </van-uploader>
                  </div>
                </van-collapse-item>
              </van-collapse>
            </div>

            <!-- 审批履历 histoicComments.length-->
            <div class="stepHistoic">
              <van-collapse v-model="activeName" :border="false">
                <van-collapse-item title="处理意见" name="1" :border="false">
                  <van-steps direction="vertical" :active="activeIndex" v-show="abeyanceShow">
                    <van-step v-for="comment in histoicComments" :key="comment.id">
                      <p style="display: flex; justify-content: space-between" v-if="comment.isTask">
                        <span style="color: #004d90">{{ comment.name }}: 待 {{ comment.extInfo.approverName }} 处理</span>
                        <span style="color: #969799; font-size: 12px">{{comment.time}}</span>
                      </p>
                      <div v-else>
                        <p style="display: flex; justify-content: space-between">
                        <span style="color: #004d90">{{ comment.activityName }}: 由{{ comment.commentBy }}
                          {{ comment.operation }}
                        </span>
                          <span style="color: #969799; font-size: 12px">{{
                          comment.time
                        }}</span>
                        </p>
                        <p>意见: {{ comment.message }}</p>
                      </div>
                    </van-step>
                  </van-steps>
                </van-collapse-item>
              </van-collapse>
            </div>

            <!-- 待办审批 -->
            <van-form @submit="submit">
              <div v-if="dialogProps.action == 'approve'" style="background-color: #fff">
                <div class="abeyanceTitle">待办审批</div>
                <div v-if="task.assignee == task.owner">
                  <div v-if="dialogProps.action == 'approve'">
                    <van-field
                      name="checkboxGroup"
                      label=""
                      required
                      :rules="[{ required: true, message: '请选择' }]"
                    >
                      <template #input>
                        <van-radio-group
                          v-model="onRadio"
                          direction="horizontal"
                          v-if="task.assignee == task.extInfo.applicantId && task.taskDefinitionKey == task.extInfo.appActId">
                          <van-radio name="onReApply" icon-size="16px">重申请</van-radio>
                          <van-radio name="onTerminate" icon-size="16px">终止</van-radio>
                        </van-radio-group>

                        <van-radio-group v-else v-model="onRadio" direction="horizontal">
                          <van-radio name="onAgree" icon-size="16px">同意</van-radio>
                          <van-radio name="onForward" icon-size="16px">转派</van-radio>
                          <van-radio name="onCommission" icon-size="16px">委派</van-radio>
                          <van-radio name="onReject" icon-size="16px">拒绝</van-radio>
                          <van-radio name="onBack" icon-size="16px">回退</van-radio>
                        </van-radio-group>
                      </template>
                    </van-field>
                  </div>
                </div>

                <div v-else>
                  <van-field
                    name="checkboxGroup"
                    label=""
                    required
                    :rules="[{ required: true, message: '请选择' }]"
                  >
                    <template #input>
                      <van-radio-group v-model="onRadio" direction="horizontal">
                        <van-radio name="onPropose" icon-size="16px">拟办</van-radio>
                      </van-radio-group>
                    </template>
                  </van-field>
                </div>
                <van-divider />

                <!-- 委派 转派 -->
                <van-field
                  label="办理人"
                  v-if="onRadio == 'onForward' || onRadio == 'onCommission'"
                  v-model="actNodes"
                  @click="openForwardDialog"
                  label-width="60px"
                  placeholder="点击选择办理人"
                  readonly
                  required
                  :rules="[{ required: true, message: '' }]"
                />
                <!-- 重申请 指派下一个审批节点 -->
                <van-field
                  label="办理人"
                  v-if="onRadio == 'onReApply' || (SelectUser && onRadio == 'onAgree')"
                  v-model="onReApplys"
                  @click="openUserDialog"
                  label-width="60px"
                  placeholder="点击选择办理人"
                  rows="1"
                  autosize
                  type="textarea"
                  readonly
                  required
                  :rules="[{ required: true, message: '' }]"
                />
                <!-- 回退 -->
                <div v-if="onRadio == 'onBack' && checkNodesList && checkNodesList.length > 0">
                  <van-field
                    label-width="60px"
                    label="回退节点"
                    v-model="checkNodeUser"
                    rows="1"
                    autosize
                    type="textarea"
                    readonly
                    required
                    @click="openNodeFrom"
                    placeholder="点击选择用户"
                    :rules="[{ required: true, message: '请选择用户' }]"
                  />
                </div>

                <van-field
                  label="意见"
                  v-if="dialogProps.action == 'approve'"
                  v-model.trim="approveModel.comment"
                  label-width="60px"
                  rows="4"
                  autosize
                  type="textarea"
                  required
                  placeholder="请输入处理意见"
                  :rules="[{ required: true, message: '' }]"
                ></van-field>
              </div>
              <span class="btnfooter" v-if="dialogProps.action == 'viewTask' && bizData && !bizData.approved">
                <van-button type="default" native-type="button" @click="onClickLeft">取消</van-button>
                <van-button type="info" @click="onReverse()">撤回</van-button>
              </span>

              <span class="btnfooter" v-else-if="dialogProps.action == 'approve' && task.assignee == task.owner" type="info">
                <van-button type="default" native-type="button" @click="onClickLeft">取消</van-button>
                <van-button type="info" native-type="submit">提交</van-button>
              </span>

              <span class="btnfooter" v-else-if="dialogProps.action == 'create'">
                <van-button type="default" native-type="button" @click="onClickLeft">取消</van-button>
                <van-button type="info" native-type="button" @click="onCreate">保存</van-button>
              </span>
              <span class="btnfooter" v-else>
                <van-button style="width: 100%" type="default" native-type="button" @click="onClickLeft">取消</van-button>
              </span>
            </van-form>
          </div>
        </div>
      </div>
    </van-popup>
    <user-form
      ref="userform"
      @select-user-finished="afterUserSelected"
    ></user-form>
    <forward-form
      ref="forwardForm"
      @forward-user-finished="afterForwardSelected"
    ></forward-form>
    <node-form
      ref="nodeForm"
      @select-node-finished="afterNodeSelected"
    ></node-form>
  </div>
</template>

<script>
import { listUserAll } from "@/api/admin/user";
import userForm from "./userForm.vue";
import forwardForm from "./forwardForm.vue";
import nodeForm from "./nodeForm.vue";
import { getAttachments } from '@/api/wf/wfDraft'
import { validatenull } from '@/utils/validate'
import { getProcessBpmnById } from '@/api/wf/processdefinition'
import { histoicFlow, getProcInstComments, getProcInstAttachments } from '@/api/wf/procinst'
import { listTask } from '@/api/wf/task'
import { parseExtInfoForList } from '@/views/wf/utils/wfUtil'
import { downloadAttachment } from '@/api/wf/attachment'
import { frtCommentIcon } from '@/views/wf/utils/wfUtil'
import BaseUI from '@/views/components/baseUI'
export default {
  name: "wfForm",
  extends: BaseUI,
  components: {
    userForm,
    nodeForm,
    forwardForm,
  },
  props: {
    // 表单参数,用于设置工作流表单默认值
    bizParams: {
      type: Object,
      default: () => { return null }
    }
  },
  data() {
    return {
      SelectUser: false, // 判断是否需要指定下一个审批节点
      users: [], // 转派 委派用数据
      actNodes: "", // 办理人value显示
      actNodesList: [], // 选中的办理人信息
      checkNodesList: [], // 回退节点数组
      checkNodeUser: "", // 回退用户value显示
      nodeSelectModel: {},
      traceConfig: null, // {bpmnModel: bpmn模型对象，histoicFlow: 跟踪节点和顺序流, }
      onReApplys: "",
      onReApplysList: [],
      abeyanceShow: true, // 审批履历显示隐藏
      requestType: "", //请求类型 create agree
      procDef: null, // 流程定义对象，发起流程时保存流程定义（仅在创建流程时使用）
      bizCompnent: null,
      bizOptions: null,
      formKey: null, // 表单form key
      bizData: null, // 业务对象
      // 分步骤填写的表单
      steps: 1, // 总步骤数
      step: 1, // 单前步骤数
      task: {},
      histoicComments: [], // 流程审批历史
      originalFileList: [],
      // uploadFiles: [], // 附件列表
      fileList: [], // upload上传加载的文件列表
      attachments: [], // 流程附件列表
      dialogProps: {
        visible: false,
        action: "",
        title: "",
      },
      approveModel: {
        comment: "", // 审批意见
      },
      onRadio: "",
      activeName: ["1"],
      activeaAttachments: ['1'],
      activeIndex: null,

      draft: null,      // 单据草稿
      definitionId: null,  // 业务数据的审批流程id  （仅在查看业务对象时使用）
      currentPage: 1,   // 历史审批当前页数
      pageSize: 5,      // 每页显示条数
      pageComments: [],     // 当前页的审批记录
      formRules: {
        'comment': [
          { required: true, message: '请输入意见', trigger: 'blur' }
        ]
      }
    };
  },
  watch: {
    onRadio(val, oldval) {
      if (val) {
        this.approveModel.comment = "";
        this.checkNodeUser = "";
        this.nodeSelectModel = {};
        this.actNodes = "";
        this.actNodesList = [];
        this.onReApplys = "";
        this.onReApplysList = [];
      }
      if (val == "onBack") {
        this.requestType = "back";
        this.$refs.bizCompnent.doListActivity();
      } else if (val == "onReApply") {
        this.requestType = "reapply";
        this.$refs.bizCompnent.doReApply(
          this.approveModel.comment,
          this.uploadFiles
        );
      } else if (val == "onCommission" || val == "onForward") {
        this.getlistUserAll();
      }
    },
  },
  created() {},
  computed: {
    uploadFiles() {
      // 需测试
      return this.fileList
    }
  },
  methods: {
    // 提交
    submit() {
      if (this.onRadio == "onAgree") {
        // 同意
        this.onAgree();
      } else if (this.onRadio == "onForward") {
        // 转派
        this.onForward();
      } else if (this.onRadio == "onCommission") {
        // 委派
        this.onCommission();
      } else if (this.onRadio == "onReject") {
        // 拒绝
        this.onReject();
      } else if (this.onRadio == "onBack") {
        // 回退
        this.onBack();
      } else if (this.onRadio == "onTerminate") {
        // 终止
        this.onTerminate();
      } else if (this.onRadio == "onReApply") {
        // 重申请
        this.onReApply();
      } else if (this.onRadio == "onReverse") {
        // 撤回
        this.onReverse();
      } else if (this.onRadio == "onPropose") {
        // 拟办
        this.onPropose();
      } else {
        return;
      }
    },
    // 审批同意
    onAgree() {
      this.requestType = "agree";
      if (this.onReApplysList && this.onReApplysList.length > 0) {
        this.$refs.bizCompnent.doAgree(
          this.approveModel.comment,
          this.uploadFiles,
          this.onReApplysList
        );
      } else {
        this.$refs.bizCompnent.doAgree(
          this.approveModel.comment,
          this.uploadFiles
        );
      }
    },
    //下载附件
    documentData(attachment) {
      downloadAttachment(attachment.taskId, attachment.id).then(
        (responseData) => {
          const link = document.createElement("a");
          var url = window.URL.createObjectURL(responseData);
          link.href = url;
          link.download = attachment.name;
          link.click();
        }
      );
    },
    // 转派
    onForward() {
      this.requestType = "forward";
      if (this.requestType == "forward") {
        this.$refs.bizCompnent.doForward(
          this.approveModel.comment,
          this.uploadFiles,
          this.actNodesList
        );
      } else {
        this.$refs.bizCompnent.doCommission(
          this.approveModel.comment,
          this.uploadFiles,
          this.actNodesList
        );
      }
    },
    // 委派
    onCommission() {
      this.requestType = "commission";
      if (this.requestType == "forward") {
        this.$refs.bizCompnent.doForward(
          this.approveModel.comment,
          this.uploadFiles,
          this.actNodesList
        );
      } else {
        this.$refs.bizCompnent.doCommission(
          this.approveModel.comment,
          this.uploadFiles,
          this.actNodesList
        );
      }
    },
    // 打开转派、委派弹框
    openForwardDialog() {
      this.$refs.forwardForm.openForwardDialog(this.users); // 打开弹框
    },
    // 重申请-选择办理人弹框
    openUserDialog() {
      this.$refs.userform.$emit('openUserDialog', this.userFormList)
    },
    // 拒绝
    onReject() {
      this.requestType = "reject";
      this.$refs.bizCompnent.doReject(
        this.approveModel.comment,
        this.uploadFiles
      );
    },
    // 回退
    onBack() {
      this.$refs.bizCompnent.doBack(
        this.approveModel.comment,
        this.uploadFiles,
        this.nodeSelectModel
      );
    },
    // 拟办（办理委派任务）
    onPropose() {
      this.requestType = "propose";
      this.$refs.bizCompnent.doPropose(
        this.approveModel.comment,
        this.uploadFiles
      );
    },
    // 终止流程 -
    onTerminate() {
      this.requestType = "terminate";
      this.$refs.bizCompnent.doTerminate(
        this.approveModel.comment,
        this.uploadFiles
      );
    },
    // 重新申请
    onReApply() {
      this.$refs.bizCompnent.doReApply(
        this.approveModel.comment,
        this.uploadFiles,
        this.onReApplysList
      );
    },
    // 撤回
    onReverse() {
      this.requestType = "reverse";
      this.$refs.bizCompnent.doReverse(this.task);
    },
    // 返回
    onClickLeft() {
      this.dialogProps.visible = false
    },
    // 新增
    onCreate() {
      let fileList = this.originalFileList.map((item) => {
        let obj = {
          name: item.file.name,
          percentage: 0,
          raw: item.file,
          size: item.file.size,
          status: "ready",
        };
        return obj;
      });
      this.fileList = fileList
      this.requestType = 'create'
      this.$refs.bizCompnent.doCreate(fileList)
    },
    reset() {
      this.abeyanceShow = true;
      this.requestType = "";
      this.onRadio = "";
      this.activeName = ["1"];
      this.SelectUser = false;
    },
    handleResponse(responseData) {
      if (responseData.code == 100) {
        if (this.requestType == "savedraft") {
          this.dialogProps.visible = false
          this.$emit("save-finished");
          this.showMessage(responseData);
        } else if (responseData.data.code == "Done" || responseData.data.code == "AutoSubmit") {
          if (responseData.data && responseData.data.msg) {
            responseData.msg = responseData.data.msg;
          }
          this.dialogProps.visible = false
          this.$emit("save-finished");
          this.showMessage(responseData);
        } else if (responseData.data.code == "SelectUser") {
          // 显示用户选择框
          this.userFormList = responseData.data.actNodes;
          if (this.requestType == "agree") {
            this.SelectUser = true;
            this.$toast("需指定下一节点办理人");
          }
          this.openUserDialog();
        } else if (responseData.data.code == "SelectNode") {
          // 显示节点选择框
          this.checkNodesList = responseData.data.actNodes;
          this.openNodeFrom();
        } else {
          this.showMessage({
            type: responseData.data.code == "Warning" ? "warning" : "error",
            msg: responseData.data.msg,
          });
        }
      } else {
        this.showMessage(responseData);
      }
    },
    afterForwardSelected(actUser) {
      this.actNodesList = [
        {
          assignees: [actUser],
        },
      ];
      this.actNodes = actUser.name;
    },
    afterUserSelected(actNodes) {
      this.onReApplysList = actNodes;
      this.onReApplys = this.namelistjoin(actNodes);
      if(this.requestType == 'create') {
        this.$refs.bizCompnent.doCreate(this.fileList, actNodes)
      }else if(this.requestType == 'agree') {
        this.$refs.bizCompnent.doAgree(this.approveModel.comment, this.fileList, actNodes)
      }else if(this.requestType == 'reapply') {
        this.$refs.bizCompnent.doReApply(this.approveModel.comment, this.fileList, actNodes)
      }
    },
    afterNodeSelected(actNodes) {
      this.nodeSelectModel = actNodes;
      this.checkNodeUser = this.namelistjoin(actNodes);
      this.$refs.bizCompnent.doBack(this.approveModel.comment, this.uploadFiles, actNodes)
    },
    namelistjoin(list) {
      var nameList = [];
      var actNodesName = "";
      list.forEach((info) => {
        actNodesName = info.name;
        var assigneesName = "";
        info.assignees.forEach((item) => {
          assigneesName = `${assigneesName} ${item.name}`;
        });
        nameList.push(`【${actNodesName}: ${assigneesName}】`);
      });
      nameList = nameList.join(",");
      return nameList;
    },
    // 打开回退节点-用户弹框
    openNodeFrom() {
      this.$refs.nodeForm.openUserDialogs(
        this.checkNodesList,
        this.nodeSelectModel
      );
    },
    // 获取转派 委派用数据
    getlistUserAll() {
      this.openForwardDialog();
      if (this.users.length > 0) return;
      this.setLoad();
      listUserAll({})
        .then((responseData) => {
          if (responseData.code == 100) {
            this.users = responseData.data;
            this.$nextTick(() => {
              this.openForwardDialog();
            });
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch((error) => {
          this.outputError(error);
        });
    },
    // 触发子表单
    afterLoadData(bizData) {
      this.bizData = bizData;
    },
    afterStepChange(step, steps) {
      this.step = step;
      this.steps = steps;
    },
    // 触发审批
    openApproveDialog(task) {
      this.task = task;
      this.initTaskInfo(task.formKey, task.processInstanceId);
      this.dialogProps.action = "approve";
      this.dialogProps.title = "审批-" + (task.name ? task.name : task.key);
      this.tracePorcInst(
        this.task.processDefinitionId,
        this.task.processInstanceId,
        this.task.taskDefinitionKey
      );
    },
    // 触发查看
    openViewDialog(task) {
      this.task = task;
      this.initTaskInfo(task.formKey, task.processInstanceId);
      this.dialogProps.action = "viewTask";
      this.dialogProps.title = "查看";
      this.tracePorcInst(
        this.task.processDefinitionId,
        this.task.processInstanceId,
        ""
      );
    },

    initTaskInfo(formKey, procInstId) {
      let subFormKey = formKey.split("$")[1]  // 子表表单
      this.formKey = subFormKey
      this.traceConfig = null
      this.histoicComments = []
      this.currentPage = 1
      this.activeName = ['1']
      this.fileList = []
      this.attachments = []
      // 获取流程审批备注
      getProcInstComments(procInstId).then(responseData => {
        if(responseData.code == 100) {
          this.histoicComments = responseData.data
          this.currentPage = Math.max(Math.ceil(this.histoicComments.length/this.pageSize), 1)
          // this.onCurrentChange(this.currentPage)
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch(error => {
        this.outputError(error)
      })

      // 获取流程附件
      getProcInstAttachments(procInstId).then(responseData => {
        if(responseData.code == 100) {
          this.attachments = responseData.data
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch(error => {
        this.outputError(error)
      })

      if(subFormKey && subFormKey.substr(0,1) == '/') {
        this.bizCompnent = () => import('@/views' + subFormKey)
      }else{
        this.bizCompnent = () => import('@/views/' + subFormKey)
      }
      this.approveModel = {
        comment: ''   // 审批意见
      }
    },
    // 审批历史
    async tracePorcInst(procDefId, procInstId, curTaskKey) {
      this.setLoad()
      try {
        let [bpmnRespData, histoicFlowRespData, activityTasks] = await Promise.all([
          getProcessBpmnById(procDefId),
          histoicFlow(procInstId),
          listTask({processInstanceId: procInstId})
        ])

        if(bpmnRespData.bpmn20Xml && histoicFlowRespData.code == 100 && activityTasks instanceof Array) {
          this.traceConfig = {
            bpmnModel: bpmnRespData,
            histoicFlow: histoicFlowRespData.data,
            currentEl: curTaskKey,
            activityTask: parseExtInfoForList(activityTasks)
          }
        } else {
          this.showMessage(bpmnRespData.code != 100 ? bpmnRespData : histoicFlowRespData)
        }
        this.resetLoad()
      } catch (error) {
        this.outputError(error)
      }
    },
  },
  mounted: function() {
    this.$nextTick(() => {
      this.$on('openCreateDialog', function(procDef, formKey, options) {
        this.bizOptions = options
        let subFormKey = formKey.split("$")[1]   // 子表表单
        this.formKey = subFormKey
        this.traceConfig = null
        this.histoicComments = []
        this.activeName = ['1']
        this.procDef = procDef
        this.fileList = []

        if(subFormKey.substr(0,1) == '/') {
          this.bizCompnent = () => import('@/views' + subFormKey)
        }else{
          this.bizCompnent = () => import('@/views/' + subFormKey)
        }
        this.approveModel = {
          comment: ''   // 审批意见
        }

        this.dialogProps.action = 'create'
        this.dialogProps.title = '创建流程--' + (procDef.name ? procDef.name : procDef.key)
        this.dialogProps.visible = true
      })


      this.$on('openDraft', function(draft, formKey) {
        let subFormKey = formKey.split("$")[0].split("#")[1]   // 子表表单
        this.formKey = subFormKey
        this.traceConfig = null
        this.histoicComments = []
        this.activeName = ['1']
        this.draft = draft
        this.fileList = []
        this.setLoad()
        getAttachments(draft.id).then(responseData => {
          if(responseData.code == 100) {
            for(let idx in responseData.data) {
              let attachment = responseData.data[idx]
              let file = this.b64toFile(attachment.fileContent, attachment.name, attachment.type)
              this.fileList.push({
                name: file.name,
                percentage: 0,
                raw: file,
                size: file.size,
                status: "ready"
              })
            }
            this.uploadFiles = this.fileList
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        }).catch(error => {
          this.outputError(error)
        })

        // 构建流程定义对象和业务对象
        this.procDef = {id: draft.procdefId , name: draft.procdefName}
        this.bizData = JSON.parse(draft.formData)

        if(subFormKey.substr(0,1) == '/') {
          this.bizCompnent = () => import('@/views' + subFormKey)
        }else{
          this.bizCompnent = () => import('@/views/' + subFormKey)
        }
        this.approveModel = {
          comment: ''   // 审批意见
        }

        this.dialogProps.action = 'create'
        this.dialogProps.title = '创建流程--' + draft.procdefName
        this.dialogProps.visible = true
      })

      this.$on('openApproveDialog', function(task) {
        this.task = task
        this.initTaskInfo(task.formKey, task.processInstanceId)
        this.dialogProps.action = 'approve'
        this.dialogProps.title = '审批--' + (task.name ? task.name : task.key)
        this.dialogProps.visible = true
      })

      this.$on('openViewDialog', function(task) {
        this.task = task
        this.initTaskInfo(task.formKey, task.processInstanceId)
        this.dialogProps.action = 'viewTask'
        this.dialogProps.title = '查看'
        this.dialogProps.visible = true
      })

      this.$on('openViewBizDialog', function(formKey, definitionId, biz) {
        this.bizData = biz
        this.definitionId = definitionId
        this.initTaskInfo(formKey, biz.procInst)
        this.dialogProps.action = 'viewBiz'
        this.dialogProps.title = '查看'
        this.dialogProps.visible = true
      })
      // 加载完成
      this.$emit('after-wfForm-load')
    })
  }
};
</script>

<style lang="scss" scoped>
.van-popup {
  display: flex;
  flex-direction: column;
  max-height: calc(100%);
}
.van-step--vertical {
  padding: 0.1rem 0.26667rem 0.1rem 0;
}
.comment {
  margin: 8px;
}
.btnfooter {
  display: flex;
  justify-content: space-around;
  .van-button {
    width: 50%;
    margin: 10px;
    box-sizing: border-box;
  }
}
.information {
  background-color: #fff;
  color: #909399;
  padding: 6px 15px;
}
.navbarZindex {
  z-index: 2;
}
</style>
