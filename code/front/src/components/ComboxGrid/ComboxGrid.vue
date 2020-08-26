<template>
  <div class="search-box" style="width:200px;">
    <el-popover placement="bottom-start" :width="width" v-model="visible" trigger="manual">
      <el-table
        :data="dataSource"
        :height="height"
        @row-click="onClickItem"
        @row-dblclick="onDblClickSelect"
      >
        <el-table-column
          v-for="item in columns"
          :key="item.field"
          :prop="item.field"
          :label="item.title"
        />
      </el-table>
      <slot name="hello" slot="reference"></slot>
    </el-popover>
  </div>
</template>
<script>
export default {
  name: "comboxGrid",
  props: {
    dataSource: {
      //表格数据
      type: Array,
      default() {
        return [];
      }
    },
    width: {
      //表格的宽
      type: Number,
      default: 600
    },
    height: {
      //表格高
      type: Number,
      default: 300
    },
    columnName: {
      //取值列
      type: String,
      default: undefined
    },
    visible: {
      //是否显示下拉表格
      type: Boolean,
      default: false
    },
    columns: {
      //显示表格列名
      type: Array,
      required: true
    }
  },
  methods: {
    onClickItem(row) {
      this.$emit("selectItem", row[this.columnName]);
      this.visible = false;
    },
    onDblClickSelect(row) {
      this.$emit("selectItem", row[this.columnName]);
      this.visible = false;
    }
  }
};
</script>
