package com.alereavila.store

interface OnClickListener {
    //aqui tendria que ser storeEntity no stor
    fun onClick (stor:StoreEntity)
    fun onFavoriteStore (stor:StoreEntity)
    fun onDeleteStore(stor:StoreEntity)
}