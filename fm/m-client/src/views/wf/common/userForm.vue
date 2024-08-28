<template>
  <van-form @submit="submit" @failed="failed">
    <div class="userForm">
      <van-popup v-model="dialogProps.visible" round position="bottom" @click-overlay="clickOverlay" :style="{ height: '80%' }">
        <div class="propsTitle" >
          <van-button native-type="button" class="propsTitleButton" style="width: 80%">{{dialogProps.title}}</van-button>
          <van-button native-type="submit" class="propsTitleButton">确认</van-button>
        </div>
        <div class="scroll">
            <van-field class="scroll_field" required v-for='(node, idxNode) in selectModel.actNodes' :key='idxNode' :rules="[{ required: true, message: '请选择办理人' }]">
              <template #input>
                <div class="checkboxNode" >
                  <van-cell :title="'节点-' + node.name"></van-cell>
                  <van-radio-group v-if='node.type == "SingleSelect"' v-model='node.assignees[0]'>
                    <van-cell-group v-for='(user, index) in node.users' :key="index">
                      <van-cell :title="user.name" clickable>
                        <template #right-icon>
                          <van-radio :name="user" />
                        </template>
                      </van-cell>
                    </van-cell-group>
                  </van-radio-group>
                  <van-checkbox-group v-else v-model="node.assignees">
                    <van-cell-group>
                      <van-cell v-for="(item, index) in node.users" clickable :key="index" :title="item.name">
                        <template #right-icon>
                          <van-checkbox :name="item" ref="checkboxes" shape="square" icon-size="20px" />
                        </template>
                      </van-cell>
                    </van-cell-group>
                  </van-checkbox-group>
                </div>
              </template>
            </van-field>
        </div>
      </van-popup>
    </div>
  </van-form>
</template>

<script>
export default {
  name: 'userForm',
  data () {
    return {
    selectModel: {
      actNodes: []
    },
    dialogProps: {
      visible: false,
      action: '',
      title: '选择办理人'
    },
    }
  },
  methods: {
    submit() {
      this.$emit('select-user-finished', this.selectModel.actNodes)
      this.dialogProps.visible = false
    },
    failed() {
      this.$toast.fail('包含必选项')
    }
  },
  mounted: function() {
    this.$nextTick(() => {
      this.$on('openUserDialog', function(actNodes) {
        this.selectModel.actNodes = actNodes
        this.dialogProps.visible = true
      })
    })
  }
}
</script>

<style lang="scss" scoped>
.van-popup {
  height: 100vh;
  overflow: hidden;
  padding-bottom: 100px;
  box-sizing: border-box;
}
.propsTitle {
  // padding: 0.53333rem 0.42667rem;
  color: #969799;
  font-size: 0.37333rem;
  line-height: 0.53333rem;
  text-align: center;
  display: flex;
  .propsTitleButton {
    color: #969799;
    border: none;
    padding: 0px;
    height: none
  }
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
