package com.alereavila.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.alereavila.store.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), OnClickListener, MainAux {

    private lateinit var mBinding : ActivityMainBinding

    private lateinit var mAdapter : StoreAdapter

    private lateinit var mGridLaoyout : GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        //Temporal
        /*
        mBinding.btnSave.setOnClickListener {
            val store = StoreEntity(name=mBinding.etName.text.toString().trim())
            //se debe de agregar primero a la Base
            //hay que instanciarlo en el manifest
            //android:name=".StoreApplication"
            //Se inserta el registro en segundo plano por eso se crea el hilo
            Thread{
                StoreApplication.dataBase.storeDao().addStore(store)
            }.start()
            mAdapter.add(store)
        }*/

        mBinding.fab.setOnClickListener {
            launchEditFragmente()
        }

        setUpRecyclerView()

    }


    private fun launchEditFragmente() {
        //se debe de instanciar
        val fragment = EditStoreFragment()

        //se debe de gestionar el fragment, siempre se debe de hacer
        //lo trae ya android para controlar los fragments y fragment transaction decide como se va a ejecutar
        val fragmentManager= supportFragmentManager
        val fragmentTransaction =fragmentManager.beginTransaction()

        // ya se puede configurar
        fragmentTransaction.add(R.id.containerMain, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        //mBinding.fab.hide()
        hideFab(false)
    }

    private fun setUpRecyclerView() {
        mAdapter = StoreAdapter(mutableListOf(), this)

        mGridLaoyout = GridLayoutManager(this, 2)
        getAllStores()
        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager=mGridLaoyout
            adapter=mAdapter
        }
    }
    private fun getAllStores(){
        //se hara la consulta asincronamente porque es recomendable hacerlo de esta manera
        doAsync {
            val stores= StoreApplication.dataBase.storeDao().getAllStore()
            //cuando este lista la consulta seteara la consulta
            uiThread {
                mAdapter.setStores(stores)
            }
        }



    }

    /* OnClickListener*/
    override fun onClick(stor: StoreEntity) {

    }

    override fun onFavoriteStore(storeEntity: StoreEntity) {
        storeEntity.isFavorite= !storeEntity.isFavorite
        doAsync {
            StoreApplication.dataBase.storeDao().updateStore(storeEntity)
            uiThread {
                mAdapter.update(storeEntity)
            }
        }
    }

    override fun onDeleteStore(stor: StoreEntity) {
        doAsync {
            StoreApplication.dataBase.storeDao().deleteStore(stor)
            uiThread {
                mAdapter.delete(stor)
            }
        }
    }

    override fun hideFab(isVisible: Boolean) {
        if(isVisible){
            mBinding.fab.show()
        }
        else{
            mBinding.fab.hide()
        }
    }

    override fun addStore(storeEntity: StoreEntity) {
        mAdapter.add(storeEntity)
    }

    override fun updateStore(storeEntity: StoreEntity) {
        TODO("Not yet implemented")
    }
}