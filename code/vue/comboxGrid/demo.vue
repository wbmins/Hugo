<template>
  <div class="home">
    <combox-grid
      :dataSource="list"
      :visible="visible"
      @selectItem="selectItem"
      :columnName="columnName"
      :columns="columns"
    >
      <div slot="hello">
        <el-input placeholder="请输入内容" v-model="name" @focus="handleFocus" @blur="handleBlur"></el-input>
      </div>
    </combox-grid>
    <div>parent value: {{name}}</div>
  </div>
</template>

<script>
import comboxGrid from "@/components/comboxGrid";
export default {
  name: "Home",
  components: {
    comboxGrid
  },
  data() {
    return {
      name: undefined,
      columnName: "price",
      visible: false,
      list: [
        { name: "阿莫西林11", spec: "盒", price: "1.1" },
        { name: "感冒灵颗粒22", spec: "盒", price: "1.2" },
        { name: "阿莫西林33", spec: "盒", price: "1.3" },
        { name: "感冒灵颗粒44", spec: "盒", price: "1.4" },
        { name: "阿莫西林55", spec: "盒", price: "1.5" },
        { name: "感冒灵颗粒66", spec: "盒", price: "1.6" },
        { name: "阿莫西林77", spec: "盒", price: "1.7" },
        { name: "感冒灵颗粒88", spec: "盒", price: "1.8" },
        { name: "阿莫西林99", spec: "盒", price: "1.9" },
        { name: "感冒灵颗粒1010", spec: "盒", price: "1.10" },
        { name: "阿莫西林1111", spec: "瓶", price: "1.11" },
        { name: "感冒灵颗粒1212", spec: "盒", price: "1.12" },
        { name: "阿莫西林1313", spec: "盒", price: "1.13" },
        { name: "感冒灵颗粒1414", spec: "盒", price: "1.14" },
        { name: "阿莫西林1515", spec: "盒", price: "1.15" }
      ],
      columns: [
        {
          field: "name",
          title: "名称"
        },
        {
          field: "spec",
          title: "规格"
        },
        {
          field: "price",
          title: "价格"
        }
      ]
    };
  },
  methods: {
    handleFocus() {
      this.visible = true;
    },
    handleBlur() {
      this.visible = false;
    },
    selectItem(value) {
      this.name = value;
    }
  }
};
</script>
