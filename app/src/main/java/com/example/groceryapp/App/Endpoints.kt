package com.example.groceryapp.App

class Endpoints {

    companion object {

        private val URL_CATEGORY = "category"
        private val URL_SUBCATEGORY = "subcategory/"
        private val URL_PRODUCTBYSUBID = "products/sub/"
        private val URL_REGISTER = "auth/register"
        private val URL_LOGIN = "auth/login"
        private val URL_ADDRESS = "address"


        fun getCategory(): String {
            return "${Config.BASE_URL + URL_CATEGORY}"
        }

        fun getSubCategoryByCatId(catId: Int): String {
            return "${Config.BASE_URL + URL_SUBCATEGORY + catId}"
        }

        fun getProductBySubId(subId: Int): String {
            return "${Config.BASE_URL + URL_PRODUCTBYSUBID + subId}"
        }

        fun getRegister(): String {

            return "${Config.BASE_URL + URL_REGISTER}"

        }

        fun getLogin(): String {

            return "${Config.BASE_URL + URL_LOGIN}"

        }

        fun getAddress(): String {

            return "${Config.BASE_URL + URL_ADDRESS}"

        }
        fun getAddressById(userId: String): String {

            return "${Config.BASE_URL + URL_ADDRESS + "/" + userId}"
        }


    }
}