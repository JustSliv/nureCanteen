<template>
    <li>
        {{info.filters}}
    </li>
</template>

<script>
    export default {
        props: {
            info: {
                type: Object,
                required: true
            }
        }
    }
</script>

<style scoped>

</style>