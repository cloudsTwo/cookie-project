<template>
    <div class="ueditor">
      <div class="con">
        新增内容：
        <quill-editor 
          class="editor"
          v-model="content" 
          ref="myQuillEditor" 
          :options="editorOption" 
          @blur="onEditorBlur($event)" @focus="onEditorFocus($event)"
          @change="onEditorChange($event)">
        </quill-editor>
      </div>

      
    </div>
</template>
<script>
import { quillEditor } from 'vue-quill-editor'
 export default {
    name: "Editor",
    data() {
      return {
        content:null,
        editorOption:{}
        };
    },
    methods:{
      onEditorBlur(){
      },
      onEditorFocus(){//获得焦点事件
      },
      onEditorChange(){//内容改变事件
        this.$emit("commitArt",this.content)
      },
      
    }
}
</script>
 
<style scoped>
  .ueditor{
    padding-top:10px;
  }

  .editor{
    width:60%;
    height:300px;
  }

  .con{
    display: flex;
  }
</style>