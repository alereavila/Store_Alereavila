package com.alereavila.store

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alereavila.store.databinding.ItemStoreBinding

class StoreAdapter(private var stores :MutableList<StoreEntity>, private var listener: OnClickListener):
        RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

        private lateinit var mContext : Context

    //ctrl + i    para sobreescribir los metodos
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        mContext=parent.context

        val view = LayoutInflater.from(mContext).inflate(R.layout.item_store,parent,false)

        return ViewHolder (view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val store = stores[position]

        with(holder){
            setListener(store)
            binding.tvName.text= store.name
            binding.cbFavorite.isChecked=store.isFavorite
        }
    }

    override fun getItemCount(): Int = stores.size

    //se implementa meto para agregar tienda
    fun add(storeEntity: StoreEntity) {
        //mejores practicas validar si existe
        if(!stores.contains(storeEntity)){
            stores.add(storeEntity)
            //para refrescar la pantalla
            //notificar que ha sido insertado hay que mandarle la posicion
            notifyItemInserted(stores.size-1)
        }

    }

    fun setStores(stores: MutableList<StoreEntity>) {
        this.stores=stores
        notifyDataSetChanged()
    }

    fun update(storeEntity: StoreEntity) {
        val index = stores.indexOf(storeEntity)
        if (index!=-1){
            stores.set(index,storeEntity)
            notifyItemChanged(index)
        }
    }

    fun delete(storeEntity: StoreEntity) {
        val index = stores.indexOf(storeEntity)
        if (index!=-1){
            stores.removeAt(index)
            notifyItemRemoved(index)
        }
    }


    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val binding = ItemStoreBinding.bind(view)
        fun setListener (storeEntity: StoreEntity){
            binding.root.setOnClickListener { listener.onClick(storeEntity) }

            binding.root.setOnLongClickListener { listener.onDeleteStore(storeEntity)
            true}

            binding.cbFavorite.setOnClickListener { listener.onFavoriteStore(storeEntity) }

        }
    }
}