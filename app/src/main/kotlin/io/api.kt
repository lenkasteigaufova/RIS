package io

import model.ApiFoodDetail
import model.ApiMenu
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface RisApi {
    /***
     * Foods request
     */
    @GET("/food/{id}")
    fun getDish(@Path("id") id: Int): Observable<ApiFoodDetail>

    @GET("menu")
    fun getMenu(): Observable<ApiMenu>

    /***
     * Orders
     */

    @GET("/objednavky")
    fun getOrders(): Observable<Boolean>

    @GET("/objednavka/{id}")
    fun getOrder(@Path("id") id: Int): Observable<Boolean>

    @GET("/objednavka/{id}/polozky")
    fun getOrderItems(@Path("id") id: Int): Observable<Boolean>



}

//
//POST REQUESTS
//
//Vloží do databázy novú objednávku
//V requeste musia prís (int stol, int ucet, double suma)
//Vráti novú objednávku.
//- /objednavka/nova
//
//Vloží do databázy novú položku
//V requeste musia prís (int objednavka, int podnik, int menu, int jedlo)
//Vráti novú položku.
//- /objednavka/polozka/nova
//
//Zmení(update) množstvo položky s daným id
//V requeste musia prís (int id, int mnozstvo)
//- /objednavka/polozka