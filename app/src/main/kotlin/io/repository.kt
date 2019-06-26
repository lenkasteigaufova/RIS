package io

import common.*
import common.extensions.log
import model.*
import rx.Observable
import java.util.concurrent.TimeUnit


interface Repository {
    fun observeFoods():                     Observable<RisState<List<Food>>>
    fun observeDailyMeals():                Observable<RisState<List<DayFood>>>
    fun observeDrinks():                    Observable<RisState<List<Drink>>>
    fun observeFood(id: FoodId):            Observable<RisState<Food>>
    fun observeDailyMeal(id: DailyFoodId):  Observable<RisState<DayFood>>
    fun observeDrink(id: DrinkId):          Observable<RisState<Drink>>

    fun fetchFoods()
    fun fetchDailyMeals()
    fun fetchDrinks()
    fun fetchFood(id: FoodId)
    fun fetchDailyMeal(id: DailyFoodId)
    fun fetchDrink(id: DrinkId)

    //Profile
    fun getUserProfile():                   Observable<UserProfile>
    //todo user orders history

    //KOSIK
    fun deleteFoodFromCart(id: FoodId)
    fun deleteDayFoodFromCart(id: DailyFoodId)
    fun deleteDrinkFromCart(id: DrinkId)

    fun addFoodToCart(food: Food)
    fun addDayFoodToCart(dayFood: DayFood)
    fun addDrinkToCart(drink: Drink)

    //USER
    fun logoutUser()
    fun getSession() : Observable<UserProfile>
    fun loginUser()
}

class RepositoryIml(val api: RisApi): Repository {

    private val foods       = cache<RisState<List<Food>>>(State.Initial)//.toSerialized()
    private val drinks      = cache<RisState<List<Drink>>>(State.Initial)
    private val dailyMeals  = cache<RisState<List<DayFood>>>(State.Initial)

    private val foodsDetails       = cache<Map<FoodId, RisState<Food>>>(emptyMap())
    private val drinksDetails      = cache<Map<DrinkId, RisState<Drink>>>(emptyMap())
    private val dailyMealsDetails  = cache<Map<DailyFoodId, RisState<DayFood>>>(emptyMap())


    override fun fetchFoods() {
    }

    override fun fetchDailyMeals() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchDrinks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchFood(id: FoodId) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchDailyMeal(id: DailyFoodId) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun fetchDrink(id: DrinkId) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserProfile() : Observable<UserProfile> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteFoodFromCart(id: FoodId) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteDayFoodFromCart(id: DailyFoodId) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteDrinkFromCart(id: DrinkId) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addFoodToCart(food: Food) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addDayFoodToCart(dayFood: DayFood) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addDrinkToCart(drink: Drink) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun logoutUser() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSession() : Observable<UserProfile> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loginUser() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun observeFoods()                     = foods.asObservable()
    override fun observeDailyMeals()                = dailyMeals.asObservable()
    override fun observeDrinks()                    = drinks.asObservable()

    override fun observeFood(id: FoodId)            = foodsDetails.map { it[id] ?: State.Initial }
    override fun observeDailyMeal(id: DailyFoodId)  = dailyMealsDetails.map { it[id] ?: State.Initial }
    override fun observeDrink(id: DrinkId)          = drinksDetails.map { it[id] ?: State.Initial }

}


class MockRepository: Repository {

    private val foodsItems = listOf(
            Food(FoodId(1), "Losos so špargľou", "https://img.mediacentrum.sk/gallery/nwo/maxwidth/650/1412739.jpg", 5.50F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Food(FoodId(2), "Kura so špargľou", "https://medlicker.com/files/2014/04/16/zdrave-ranajky-1397677183-f2eef982.jpg", 5.60F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Food(FoodId(3), "Prasa so špargľou", "http://www.esutaze.sk/wp-content/uploads/2015/06/Wanda.sk-„Naj-ľahké-jedlo-leta“.jpg", 4.50F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Food(FoodId(4), "Zelenina so špargľou", "https://ipravda.sk/res/2016/04/17/thumbs/eintopf-clanokW.jpg", 5.70F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Food(FoodId(5), "Halušky so špargľou", "https://cdn.webnoviny.sk/sites/32/2013/07/ryba-losos-brokolica-jedlo.jpg", 3.50F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Food(FoodId(6), "Cestoviny so špargľou", "http://www.pluska.sk/images/dobre-jedlo/rady-tipy/tesite-dalsie-dobre-jedlo-toto-vsetko-vas-marcovom-cisle-caka/shutterstock_435306208.jpg", 5.90F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Food(FoodId(7), "Syr so špargľou", "https://media-cdn.tripadvisor.com/media/photo-s/0e/ae/22/8a/hlavne-jedlo.jpg", 6.50F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Food(FoodId(8), "Pstruh so špargľou", "https://cloudia.hnonline.sk/r740x/8f78a608e6355df8e21544f127a9b561.jpg", 5.10F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Food(FoodId(9), "Kura so špargľou", "https://cdn.pixabay.com/photo/2015/04/08/13/13/food-712665_960_720.jpg", 4.90F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Food(FoodId(10), "Losos so špargľou", "https://i.imgur.com/TcsaCek.jpg", 8.50F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, ")
    )

    private val dailyFoodsItems = listOf(
            DayFood(DailyFoodId(11), "losos so špargľou", "https://img.mediacentrum.sk/gallery/nwo/maxwidth/650/1412739.jpg", 5.50F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            DayFood(DailyFoodId(12), "kura so špargľou", "https://medlicker.com/files/2014/04/16/zdrave-ranajky-1397677183-f2eef982.jpg", 5.60F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            DayFood(DailyFoodId(13), "Prasa so špargľou", "http://www.esutaze.sk/wp-content/uploads/2015/06/Wanda.sk-„Naj-ľahké-jedlo-leta“.jpg", 4.50F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            DayFood(DailyFoodId(14), "Zelenina so špargľou", "https://ipravda.sk/res/2016/04/17/thumbs/eintopf-clanokW.jpg", 5.70F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            DayFood(DailyFoodId(15), "Halušky so špargľou", "https://cdn.webnoviny.sk/sites/32/2013/07/ryba-losos-brokolica-jedlo.jpg", 3.50F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            DayFood(DailyFoodId(16), "Cestoviny so špargľou", "http://www.pluska.sk/images/dobre-jedlo/rady-tipy/tesite-dalsie-dobre-jedlo-toto-vsetko-vas-marcovom-cisle-caka/shutterstock_435306208.jpg", 5.90F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            DayFood(DailyFoodId(17), "Syr so špargľou", "https://media-cdn.tripadvisor.com/media/photo-s/0e/ae/22/8a/hlavne-jedlo.jpg", 6.50F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            DayFood(DailyFoodId(18), "Pstruh so špargľou", "https://cloudia.hnonline.sk/r740x/8f78a608e6355df8e21544f127a9b561.jpg", 5.10F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            DayFood(DailyFoodId(19), "Kura so špargľou", "https://cdn.pixabay.com/photo/2015/04/08/13/13/food-712665_960_720.jpg", 4.90F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            DayFood(DailyFoodId(20), "Losos so špargľou", "https://i.imgur.com/TcsaCek.jpg", 8.50F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, ")
    )

    private val drinksItems = listOf(
            Drink(DrinkId(21), "Coca-cola", "http://www.coca-cola.sk/content/dam/GO/one-brand/SK/updates/33538/cc_vianoce2017_flase_web_banner_1800x930_sk.jpg", 1.20F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Drink(DrinkId(22), "Sprite", "https://www.retaildetail.eu/sites/default/files/styles/news/public/news/Sprite.jpg?itok=ZM4Wjvkz", 1.20F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Drink(DrinkId(23), "Fanta", "http://static.adweek.com/adweek.com-prod/wp-content/uploads/2017/06/fanta-spiral-bottle-hed-2017.jpg", 1.20F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Drink(DrinkId(24), "Pepsi", "https://static.seekingalpha.com/uploads/2017/2/15/40779175-1487169419486396_origin.jpg", 1.20F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Drink(DrinkId(25), "Káva", "https://cdn.pixabay.com/photo/2016/04/26/16/58/coffe-1354786_960_720.jpg", 1.10F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Drink(DrinkId(26), "Čaj", "http://www.juvalis-versandapotheke.de/wp-content/uploads/2013/02/500x300xSchwarzer-Tee.jpg.pagespeed.ic.eSg2jbJrTQ.jpg", 1.10F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Drink(DrinkId(27), "Nestea", "https://i.pinimg.com/474x/39/6d/60/396d609541090d1bb7cba23c98b180ed--spain.jpg", 1.20F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Drink(DrinkId(28), "Cappy", "https://hu.coca-colahellenic.com/media/2776/cappy.jpg?anchor=center&mode=crop&quality=80&width=600&rnd=131172119260000000", 1.20F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Drink(DrinkId(29), "Kinley", "https://hu.coca-colahellenic.com/media/2777/kinley.jpg?anchor=center&mode=crop&quality=80&rnd=131172119710000000", 1.20F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, "),
            Drink(DrinkId(30), "Vinea", "http://www.butterflies-hurricanes.cz/content/1200/vinea-449.jpg", 1.20F, "350g, zemiakove lupienky, cesnak, bazalka, dalsie, dalsie, dalsie, dalsie, dalsei, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalsie, dalesie, dalsie, dalsie, dalsie, dalsie, ")
    )

    private val drinks              = cache<RisState<List<Drink>>>(State.Success(drinksItems)).toSerialized()
    private val dailyMeals          = cache<RisState<List<DayFood>>>(State.Success(dailyFoodsItems)).toSerialized()
    private val foods               = cache<RisState<List<Food>>>(State.Success(foodsItems)).toSerialized()

    private val foodsDetails        = cache<Map<FoodId, RisState<Food>>>(emptyMap())
    private val drinksDetails       = cache<Map<DrinkId, RisState<Drink>>>(emptyMap())
    private val dailyMealsDetails   = cache<Map<DailyFoodId, RisState<DayFood>>>(emptyMap())

    private val profileMock by lazy {
        val userOrders = listOf(
            Order(
                emptyList(),
                drinksItems.take(3),
                foodsItems.take(2),
                969660000000L,
                drinksItems.take(3).sumByDouble { it.price.toDouble() }.toFloat() +
                    foodsItems.take(2).sumByDouble { it.price.toDouble() }.toFloat()
            ),
            Order(
                dailyFoodsItems.drop(2).take(1),
                drinksItems.take(3),
                emptyList(),
                969860000000L,
                dailyFoodsItems.drop(2).take(1).sumByDouble { it.price.toDouble() }.toFloat() +
                    drinksItems.take(3).sumByDouble { it.price.toDouble() }.toFloat()
            ),
            Order(
                dailyFoodsItems.drop(3).take(2),
                emptyList(),
                foodsItems.drop(5).take(2),
                979860000000L,
                dailyFoodsItems.drop(3).take(2).sumByDouble { it.price.toDouble() }.toFloat() +
                    foodsItems.drop(5).take(2).sumByDouble { it.price.toDouble() }.toFloat()
            )
        )
        UserProfile(
            "Joe Doe",
            "joe.doe@gmail.com",
            "https://www.w3schools.com/w3images/avatar3.png",
            userOrders,
            Order(
            dailyFoodsItems,
            drinksItems.take(3),
            foodsItems.take(2),
            969660000000L,
            drinksItems.take(3).sumByDouble { it.price.toDouble() }.toFloat() +
                foodsItems.take(2).sumByDouble { it.price.toDouble() }.toFloat())
        )
    }

    private val profile   = safeCache(profileMock)

    private val sessionMock by lazy {
        UserProfile(
            "", "", "", emptyList(), Order(emptyList(), emptyList(), emptyList(), 0L, 0F)
        )
    }

    private val session = safeCache(sessionMock)

    override fun fetchFoods() {
        foods.delay(1, TimeUnit.SECONDS).take(1).subscribe {
            it.map(List<Food>::shuffled).also {
                foods.onNext(it)
            }
        }
    }

    override fun fetchDailyMeals() {
        dailyMeals.delay(1, TimeUnit.SECONDS).take(1).subscribe {
            it.map(List<DayFood>::shuffled).also {
                dailyMeals.onNext(it)
            }
        }
    }

    override fun fetchDrinks() {
        drinks.delay(1, TimeUnit.SECONDS).take(1).subscribe {
            it.map(List<Drink>::shuffled).also {
                drinks.onNext(it)
            }
        }
    }

    override fun fetchFood(id: FoodId) {
    }

    override fun fetchDailyMeal(id: DailyFoodId) {

    }

    override fun fetchDrink(id: DrinkId) {

    }

    override fun getUserProfile() = profile.asObservable()


    override fun deleteFoodFromCart(id: FoodId) {
        profile.updateValue {
            it.copy(actualOrder = it.actualOrder.copy(foodsList = it.actualOrder.foodsList.filter { it.id != id }))
        }
    }

    override fun deleteDayFoodFromCart(id: DailyFoodId) {
        profile.updateValue {
            it.copy(actualOrder = it.actualOrder.copy(dayFoodsList = it.actualOrder.dayFoodsList.filter { it.id != id }))
        }
    }

    override fun deleteDrinkFromCart(id: DrinkId) {
        profile.updateValue {
            it.copy(actualOrder = it.actualOrder.copy(drinksList = it.actualOrder.drinksList.filter { it.id != id }))
        }
    }

    override fun addFoodToCart(food: Food) {
        profile.updateValue {
            it.copy(actualOrder = it.actualOrder.copy(foodsList = it.actualOrder.foodsList.plus(food)))
        }
    }

    override fun addDayFoodToCart(dayFood: DayFood) {
        profile.updateValue {
            it.copy(actualOrder = it.actualOrder.copy(dayFoodsList = it.actualOrder.dayFoodsList.plus(dayFood)))
        }
    }

    override fun addDrinkToCart(drink: Drink) {
        profile.updateValue {
            it.copy(actualOrder = it.actualOrder.copy(drinksList = it.actualOrder.drinksList.plus(drink)))
        }
    }

    override fun logoutUser() {
        session.updateValue {
            sessionMock
//            it.copy(actualOrder = it.actualOrder.copy(dayFoodsList = emptyList(), drinksList = emptyList(), foodsList = emptyList()))
        }
    }

    override fun getSession() = session.asObservable()

    override fun loginUser() {
        session.updateValue {
            profileMock
        }
    }

    /**
     *     Cached State
     */
    override fun observeFoods(): Observable<RisState<List<Food>>>           = foods.asObservable()//Observable.just(foodsItems)
    override fun observeDailyMeals(): Observable<RisState<List<DayFood>>>   = dailyMeals.asObservable()//Observable.just(State.Success(dailyFoodsItems))
    override fun observeDrinks(): Observable<RisState<List<Drink>>>         = drinks.asObservable()//Observable.just(State.Success(drinksItems))

    override fun observeFood(id: FoodId): Observable<RisState<Food>> = Observable.fromCallable {
        foodsItems.find { it.id == id }!!
    }.map { State.Success(it) }

    override fun observeDailyMeal(id: DailyFoodId): Observable<RisState<DayFood>> = Observable.fromCallable {
        dailyFoodsItems.find { it.id == id }!!
    }.map { State.Success(it) }

    override fun observeDrink(id: DrinkId): Observable<RisState<Drink>> = Observable.fromCallable {
        drinksItems.find { it.id == id }!!
    }.map { State.Success(it) }
}