package model

data class ApiMenu(val MenuResult: List<ApiFood>)

data class ApiFood(
        val LanguageCode:       String,
        val AmountOfCalories:   Int,
        val Id:                 Int,
        val Image:              String?,
        val Length:             Int?,
        val Name:               String,
        val RawMaterial:        List<ApiIngredient>?,
        val Translations:       String?,
        val TypeId:             Int,
        val TypeName:           String?
)

data class ApiIngredient(
        val LanguageCode:   String?,
        val Alergen:        Boolean?,
        val Id:             Int?,
        val Jednotka:       String?,
        val Nazov:          String?
)

data class ApiFoodDetail(val FoodResult: ApiFood)