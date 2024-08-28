<template>
  <AppCard
    :panelSetIcon="panelSetIcon"
    appName="科目余额表"
    routerId="1534318517509611535"
    borderColor="#da9557"
    maskColor="#ffffff"
    bgIcon="ye1.png"
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
