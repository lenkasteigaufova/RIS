package model

import common.State
import io.RisError


typealias RisState<V> = State<V, RisError>

data class DayFood(
    val id:             DailyFoodId,
    val name:           String,
    val photo:          String,
    val price:          Float,
    val description:    String
)

data class Drink(
    val id:             DrinkId,
    val name:           String,
    val photo:          String,
    val price:          Float,
    val description:    String
)

data class Food(
    val id:             FoodId,
    val name:           String,
    val photo:          String,
    val price:          Float,
    val description:    String
)

data class UserProfile(
    val name:           String,
    val email:          String,
    val photo:          String,
    val ordersList:     List<Order>,
    val actualOrder:    Order
)

data class Order(
    val dayFoodsList: List<DayFood>,
    val drinksList: List<Drink>,
    val foodsList: List<Food>,
    val date: Long,
    val sum: Float
)

data class FoodId(val id: Int)
data class DailyFoodId(val id: Int)
data class DrinkId(val id: Int)