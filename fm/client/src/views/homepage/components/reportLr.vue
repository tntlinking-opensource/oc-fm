<template>
  <AppCard
    :panelSetIcon="panelSetIcon"
    appName="利润表"
    routerId="1507257131165106194"
    borderColor="#018cb7"
    maskColor="#ffffff"
    bgIcon="lr1.png"
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
