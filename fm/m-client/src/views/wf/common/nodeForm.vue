<template>
  <van-form @submit="submit" @failed="failed">
    <div class="nodeForm">
      <van-popup v-model="dialogProps.visible" round position="bottom" @click-overlay="clickOverlay" :style="{ height: '80%' }">
        <div class="propsTitle">
          <van-button native-type="button" class="propsTitleButton" style="width: 80%">{{dialogProps.title}}</van-button>
          <van-button native-type="submit" class="propsTitleButton">确认</van-button>
        </div>
        <div class="scroll">
          <van-field class="scroll_field" name="validator" :rules="[{ validator, message: '' }]" :error-message="validatormessage">
            <template #input>
              <van-checkbox-group v-model="selectModel.checkNodes" required >
                <div class="checkboxNode" v-for="(node,index) in checkNodesList" :key="index" >
                  <van-cell-group>
                    <van-cell :title="'节点-' + node.name">
                      <template #right-icon>
                        <van-checkbox :disabled='inExclusion(node)' :name="node" ref="checkboxes" shape="square" icon-size="20px" ></van-checkbox>
                      </template>
                    </van-cell>
                    <van-radio-group v-if='node.type == "SingleSelect"' v-model='node.assignees[0]' :disabled='!inSelected(node)'>
                      <van-cell-group v-for='(user, index) in node.users' :key="index">
                        <van-cell :title="user.name" clickable>
                          <template #right-icon>
                            <van-radio :name="user" :label-disabled="true" />
                          </template>
                        </van-cell>
                      </van-cell-group>
                    </van-radio-group> 
                    <van-checkbox-group v-else v-model="node.assignees" :disabled='!inSelected(node)'>
                      <van-cell-group>
                        <van-cell v-for="(item, index) in node.users" clickable :key="index" :title="item.name">
                          <template #right-icon>
                            <van-checkbox :name="item" :label-disabled="true" :disabled='inExclusion(node)' ref="checkboxes" shape="square" icon-size="20px" />
                          </template>
                        </van-cell>
                      </van-cell-group>
                    </van-checkbox-group>
                  </van-cell-group>
                  <!-- <van-radio-group  v-if='node.type == "SingleSelect"' v-model='node.assignees[0]' :disabled='!inSelected(node)'>
                    <van-radio  icon-size="18px" :name="user" v-for='(user, index) in node.users' :key="index">{{user.name}}</van-radio>
                  </van-radio-group>
                  <van-checkbox-group  v-else v-model='node.assignees' :disabled='!inSelected(node)'>
                    <div v-for="(user, index) in node.users" :key="index">
                      <van-checkbox  icon-size="18px" :disabled='inExclusion(node)' :name="user">{{user.name}}</van-checkbox> 
                    </div>
                  </van-checkbox-group> -->
                </div>
              </van-checkbox-group>
            </template>
          </van-field>
        </div>
      </van-popup>
    </div>
  </van-form>
</template>

<script>
export default {
  name: 'nodeForm',
  data () {
    return {
    selectModel: {
      checkNodes: []
    },
    validatormessage: '',
    checkNodesList: [],
    dialogProps: {
      visible: false,
      action: '',
      title: '选择节点'
    },
    }
  },
  methods: {
    validator(value) {
      if(!value || value.length <= 0) {
        this.validatormessage = '请选退回的节点'
        return false
      } else if (value || value.length > 0) {
        for(let node of value) {
          if(!node.assignees || node.assignees.length <=0) {
            this.validatormessage =  `回退节点【 ${node.name} 】选择用户`
            return false
          } else {
            this.validatormessage =  ''
          }
        }
      } else {
        this.validatormessage =  ''
        return true
      }
    },
    clickOverlay() {
      this.validatormessage =  ''
      this.selectModel.checkNodes = []
    },
    submit() {
      this.$emit('select-node-finished', this.selectModel.checkNodes)
      this.dialogProps.visible = false
      this.selectModel.checkNodes = []
    },
    failed() {
      this.$toast.fail('包含必选项')
    },
    openUserDialogs(actNodes,checkNodes) {
      this.checkNodesList = actNodes
      if (checkNodes.length > 0) {
        this.selectModel.checkNodes = checkNodes
      }
      this.dialogProps.visible = true
    },
    inSelected(curNode) {
      if(this.selectModel.checkNodes.indexOf(curNode) >= 0) {
        return true
      }
      return false
    },
    inExclusion(curNode) {
      for(let node of this.selectModel.checkNodes) {
        if(node.excNodes.indexOf(curNode.id) >= 0) {
          return true
        }
      }
      return false
    },
  }
}
</script>

<style lang="scss" scoped>
.propsTitle {
  color: #969799 !important;
  font-size: 0.37333rem;
  line-height: 0.53333rem;
  text-align: center;
  // padding: 0.53333rem 0.42667rem;
  .propsTitleButton {
    color: #969799;
    border: none;
    padding: 0px;
    height: none
  }
}
.van-popup {
  height: 100vh;
  overflow: hidden;
  padding-bottom: 70px;
  box-sizing: border-box;
}
.checkboxNode {
  padding: 10px;
  margin: 5px;
  background-color: #f6f8fb;
  border-radius: 8px;
}
.scroll_field {
  /deep/ .van-field__control--custom {
    display: block;
  }
}
</style>