<template>
    <v-app>
        <v-card style="margin: 8% 30% 0 30%; padding: 1%" v-if="validateReceipt.status">
            <v-card-title>
                Чек №{{validateReceipt.id}}
                <v-spacer></v-spacer>
                <span style="font-size: 8pt; margin-top: -8%">{{validateReceipt.purchaseDate}}</span>
            </v-card-title>
            <v-divider></v-divider>
            <v-img width="230" height="230" style="margin: 3% 0 3% 31%;" :src="getQrCode"></v-img>
            <v-divider></v-divider>
            <v-card-text style="text-align: center">Чтоб забрать еду покажи код на кассе</v-card-text>
            <v-btn

                    color="green"
                    to="/products"
                    style="color: white; margin-left: 30%"
            >
                Вернуться к продуктам
            </v-btn>

        </v-card>
        <div style="margin-top: 8%; margin-left: 40%" v-else>
            <v-icon x-large style="margin-top: 22%; padding-left: 11%">
                warning
            </v-icon>
            <v-card-title>
                <v-icon>navigate_next</v-icon>Чек недоступен<v-icon>navigate_before</v-icon>
            </v-card-title>
        </div>
    </v-app>
</template>

<script>
    export default {
        name: "Receipt",
        computed: {
            validateReceipt() {
                // let idReceipt = this.$router.currentRoute.params.id.toString();
                try {
                    let receiptInfo = JSON.parse(document.cookie.split(' ')[1].split('=')[1]);
                    return {
                        status: true,
                        id: receiptInfo.code,
                        fname: receiptInfo.fname,
                        sname: receiptInfo.sname,
                        email: receiptInfo.email,
                        group: receiptInfo.group,
                        purchaseDate: receiptInfo.purchaseDate
                    };
                } catch (err) {
                    console.log(err);
                    return false;
                }
            },
            getQrCode() {
                let receiptInfo = JSON.parse(document.cookie.split(' ')[1].split('=')[1]);
                let fName = receiptInfo.fname;
                let sName = receiptInfo.sname;
                let email = receiptInfo.email;
                let group = receiptInfo.group;
                return 'https://api.qrserver.com/v1/create-qr-code/?size=150x150&data='+fName+';'+sName+';'+group+';'+email+';https://localhost:8080/admin/cart/'+this.validateReceipt.id+';';
            }
        }
    }
</script>

<style scoped>

</style>