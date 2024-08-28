<template>
  <AppCard
    :panelSetIcon="panelSetIcon"
    appName="查看凭证"
    routerId="1494253464840634373"
    borderColor="#7aba84"
    maskColor="#ffffff"
    bgIcon="view1.png"
    bgSize="50px"
  />
</template>

<script>
import AppCard from "./AppCard";

export default {
  props: ["id", "panelSetIcon"],
  components: {
    AppCard
  }
};
</script>
