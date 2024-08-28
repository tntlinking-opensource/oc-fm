<template>
  <AppCard
    :panelSetIcon="panelSetIcon"
    appName="资产负债表"
    routerId="1507632854904283150"
    borderColor="#cb674f"
    maskColor="#ffffff"
    bgIcon="fz1.png"
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
