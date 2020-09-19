<template>
    <v-treeview selectable :items="info.filters" selected-color="primary"></v-treeview>
</template>

<script>

    // import Filter from "@/components/Filter";

    export default {
        props: ['info'],
        components: {
            // Filter
        }
    }
</script>

<style scoped>
</style>